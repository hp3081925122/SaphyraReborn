package com.hp.saphyrareborn.boss.moth;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.BossAttributes;
import com.hp.saphyrareborn.boss.BossCastLock;
import com.hp.saphyrareborn.boss.BossConfig;
import com.hp.saphyrareborn.boss.BossMusic;
import com.hp.saphyrareborn.entity.MothbossEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class MothCombat {
    private static final int[] POINTS = {20, 120, 240};
    private static final int RESET = 320;
    private static final int[] THEME_TICKS = {20, 320, 620, 920, 1220, 1520};
    private static final int MUSIC_RESET = 1780;
    private static final int PHASE_SHIFT_TICKS = 60;
    private static final int PHASE_BURST_TICK = 30;
    private static final int DASH_NONE = 0;
    private static final int DASH_FORTH = 1;
    private static final int DASH_BACK = 2;
    private static final int STAB_LOCK = 9;
    private static final int STAB_START = 12;
    private static final int STAB_END = 18;
    private static final int BLOCK_WINDOW = 60;
    private static final int COUNTER_DELAY = 3;
    private static final int COUNTER_HIT = 20;
    private static final double STORM_WAVE_SPEED = 1.25;
    private static final int STORM_WAVE_TICKS = 16;
    private static final double STORM_WAVE_RADIUS = 2.1;

    private final MothbossEntity boss;
    private final List<Strike> strikes = new ArrayList<>();
    private final List<StormWave> waves = new ArrayList<>();
    private final List<Integer> skyTargets = new ArrayList<>();
    private final Set<Integer> stabHit = new HashSet<>();
    private int clock;
    private int timeline;
    private MothSkill skill;
    private int skillTick;
    private int pendingPoint = -1;
    private boolean phase2;
    private int phaseShiftTicks;
    private boolean followDash;
    private int dashDelay = -1;
    private double dirX;
    private double dirZ;
    private int counterTarget = -1;
    private int counterTick = -1;

    private record Strike(double x, double y, double z, int fireAt, double radius, double height, double multiplier, double grief) {
    }

    private static final class StormWave {
        double x;
        double y;
        double z;
        final double dx;
        final double dz;
        final double radius;
        final double multiplier;
        final double grief;
        int ticksLeft;
        final Set<Integer> hit = new HashSet<>();

        StormWave(Vec3 pos, double dx, double dz, double radius, double multiplier, double grief, int ticksLeft) {
            this.x = pos.x;
            this.y = pos.y;
            this.z = pos.z;
            this.dx = dx;
            this.dz = dz;
            this.radius = radius;
            this.multiplier = multiplier;
            this.grief = grief;
            this.ticksLeft = ticksLeft;
        }
    }

    public MothCombat(MothbossEntity boss) {
        this.boss = boss;
    }

    public boolean isInvulnerable() {
        return this.phaseShiftTicks > 0;
    }

    public void resetFight() {
        this.cancelSkill();
        this.strikes.clear();
        this.waves.clear();
        this.phase2 = false;
        this.phaseShiftTicks = 0;
        this.pendingPoint = -1;
        this.timeline = 0;
        this.boss.setPhase2Visual(false);
        this.boss.stopProcedureAnimation();
    }

    public void tick() {
        if (!(this.boss.level() instanceof ServerLevel level) || !this.boss.isAlive()) {
            return;
        }
        this.clock++;
        LivingEntity target = this.boss.getTarget();
        this.tickStrikes(level);
        this.tickWaves(level);
        this.tickDash(level, target);
        this.tickMusic(level, target);
        this.faceTarget(target);
        this.debugLog(target);
        if (!this.phase2 && BossConfig.MOTH_PHASE2_ENABLED.get()
                && this.boss.getHealth() <= this.boss.getMaxHealth() * BossConfig.MOTH_PHASE2_RATIO.get()) {
            this.startPhaseShift(level);
        }
        if (this.phaseShiftTicks > 0) {
            this.tickPhaseShift(level);
            return;
        }
        boolean stunned = this.boss.hasEffect(MokelsBossfightSaphyraModMobEffects.STUN);
        if (stunned && this.skill != null) {
            this.cancelSkill();
            this.boss.stopProcedureAnimation();
        }
        if (target != null) {
            this.timeline++;
            int point = this.pointAt(this.timeline);
            if (point >= 0 && !stunned) {
                if (this.skill == null) {
                    this.firePoint(level, point);
                } else {
                    this.pendingPoint = point;
                }
            }
            if (this.timeline >= this.scaled(RESET)) {
                this.timeline = 0;
            }
        }
        if (this.skill != null) {
            this.tickSkill(level);
        } else if (this.pendingPoint >= 0 && !stunned && target != null) {
            int point = this.pendingPoint;
            this.pendingPoint = -1;
            this.firePoint(level, point);
        }
    }

    private float turnRate() {
        if (this.phaseShiftTicks > 0) {
            return 0.0f;
        }
        if (this.skill == null) {
            return 30.0f;
        }
        if (this.skill == MothSkill.STAB && this.skillTick >= STAB_LOCK) {
            return 0.0f;
        }
        if (this.skill == MothSkill.BLOCK && this.counterTick >= 0) {
            return 0.0f;
        }
        return 10.0f;
    }

    private void faceTarget(LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return;
        }
        this.boss.getLookControl().setLookAt(target, 30.0f, 30.0f);
        float rate = this.turnRate();
        double dx = target.getX() - this.boss.getX();
        double dz = target.getZ() - this.boss.getZ();
        if (rate <= 0.0f || dx * dx + dz * dz < 1.0E-4) {
            return;
        }
        float want = (float) (Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0f;
        float yaw = Mth.approachDegrees(this.boss.getYRot(), want, rate);
        this.boss.setYRot(yaw);
        this.boss.setYBodyRot(yaw);
    }

    private void faceInstantly(Entity target) {
        double dx = target.getX() - this.boss.getX();
        double dz = target.getZ() - this.boss.getZ();
        float yaw = (float) (Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0f;
        this.boss.setYRot(yaw);
        this.boss.setYBodyRot(yaw);
        this.boss.setYHeadRot(yaw);
    }

    private void stepIn(double speed) {
        Vec3 look = this.flatLook();
        double vy = Math.max(-0.08, this.boss.getDeltaMovement().y);
        this.boss.setDeltaMovement(new Vec3(look.x * speed, vy, look.z * speed));
        this.boss.hurtMarked = true;
    }

    private void debugLog(LivingEntity target) {
        if (target == null || this.boss.tickCount % 100 != 0) {
            return;
        }
        MokelsBossfightSaphyraMod.LOGGER.info("[MothDebug] id={} onGround={} yRot={} skill={} skillTick={} timeline={} phase2={} strikes={} waves={} health={}/{}",
                this.boss.getId(), this.boss.onGround(), String.format("%.1f", Mth.wrapDegrees(this.boss.getYRot())), this.skill, this.skillTick,
                this.timeline, this.phase2, this.strikes.size(), this.waves.size(), this.boss.getHealth(), this.boss.getMaxHealth());
    }

    public boolean onBlockedHit(DamageSource source) {
        if (!(this.boss.level() instanceof ServerLevel level)) {
            return true;
        }
        if (this.skill == MothSkill.BLOCK && this.counterTick < 0 && source.getEntity() instanceof LivingEntity attacker
                && attacker != this.boss && attacker.isAlive() && attacker.distanceToSqr(this.boss) < 16.0 * 16.0) {
            this.counterTarget = attacker.getId();
            this.counterTick = this.skillTick + COUNTER_DELAY;
            this.sound(level, "item.shield.block", SoundSource.HOSTILE, 1.6f, 1.3f);
            this.sound(level, "entity.lightning_bolt.impact", SoundSource.HOSTILE, 1.2f, 1.6f);
            MothVfx.send(level, MothVfx.LIGHTNING, this.boss.position(), this.boss.getYRot(), 6.0f, this.boss.getRandom().nextInt(1000));
            MokelsBossfightSaphyraMod.LOGGER.info("[MothDebug] counter triggered by {} at skillTick={}", attacker.getName().getString(), this.skillTick);
        }
        return true;
    }

    private void startPhaseShift(ServerLevel level) {
        this.phase2 = true;
        this.cancelSkill();
        this.pendingPoint = -1;
        this.timeline = 0;
        this.phaseShiftTicks = PHASE_SHIFT_TICKS;
        BossCastLock.lock(this.boss, PHASE_SHIFT_TICKS);
        this.boss.restartAnimation("phaseshift");
        this.sound(level, "entity.phantom.death", SoundSource.HOSTILE, 2.0f, 0.5f);
        MothVfx.send(level, MothVfx.PHASE_SHIFT, this.boss.position(), this.boss.getYRot(), 6.0f, PHASE_SHIFT_TICKS);
        MothVfx.send(level, MothVfx.WINGS, this.boss.position(), this.boss.getYRot(), 5.4f, PHASE_SHIFT_TICKS);
    }

    private void tickPhaseShift(ServerLevel level) {
        int age = PHASE_SHIFT_TICKS - this.phaseShiftTicks;
        if (age == PHASE_BURST_TICK) {
            this.boss.setPhase2Visual(true);
            this.sound(level, "entity.lightning_bolt.thunder", SoundSource.HOSTILE, 2.0f, 0.8f);
            this.sound(level, "entity.lightning_bolt.impact", SoundSource.HOSTILE, 2.0f, 0.6f);
            Vec3 c = this.boss.position();
            level.sendParticles(MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get(), c.x, c.y + 1.5, c.z, 40, 2.0, 1.5, 2.0, 0.05);
            MothVfx.send(level, MothVfx.LIGHTNING, c, 0.0f, 28.0f, this.boss.getRandom().nextInt(1000));
            for (int i = 0; i < 8; i++) {
                this.spawnStormWave(level, this.boss.getYRot() + i * 45.0f, 0.7, 10.0);
            }
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, this.boss.getBoundingBox().inflate(6.0), e -> e != this.boss && !(e instanceof MothbossEntity))) {
                e.knockback(0.8, this.boss.getX() - e.getX(), this.boss.getZ() - e.getZ());
                e.hurtMarked = true;
            }
        }
        this.phaseShiftTicks--;
    }

    private void cancelSkill() {
        this.skill = null;
        this.skillTick = 0;
        this.followDash = false;
        this.dashDelay = -1;
        this.counterTarget = -1;
        this.counterTick = -1;
        this.skyTargets.clear();
        this.stabHit.clear();
        BossCastLock.release(this.boss);
    }

    private int scaled(int tick) {
        return this.phase2 ? Math.max(1, Mth.floor(tick * BossConfig.MOTH_PHASE2_CADENCE.get() + 0.5)) : tick;
    }

    private int pointAt(int tick) {
        for (int i = 0; i < POINTS.length; i++) {
            if (this.scaled(POINTS[i]) == tick) {
                return i;
            }
        }
        return -1;
    }

    private boolean playersWithin(ServerLevel level, double size) {
        return !level.getEntitiesOfClass(Player.class, AABB.ofSize(this.boss.position(), size, size, size), p -> !p.isSpectator()).isEmpty();
    }

    private void firePoint(ServerLevel level, int point) {
        boolean first = this.boss.getRandom().nextDouble() < 0.5;
        boolean near = this.playersWithin(level, 14.0);
        if (this.phase2 && this.boss.getRandom().nextDouble() < BossConfig.MOTH_PHASE2_NEW_SKILL_CHANCE.get()) {
            switch (point) {
                case 0 -> this.start(level, MothSkill.STAB, DASH_NONE, false);
                case 1 -> this.start(level, MothSkill.TRIPLE, near ? DASH_NONE : DASH_FORTH, false);
                default -> this.start(level, near ? MothSkill.BLOCK : MothSkill.STAB, DASH_NONE, false);
            }
            return;
        }
        switch (point) {
            case 0 -> {
                if (first) {
                    this.start(level, MothSkill.SMITE, near ? DASH_BACK : DASH_NONE, false);
                } else {
                    this.start(level, MothSkill.ONCE, near ? DASH_NONE : DASH_FORTH, false);
                }
            }
            case 1 -> {
                if (first) {
                    this.start(level, MothSkill.TWIRL, near ? DASH_NONE : DASH_FORTH, false);
                } else {
                    this.start(level, MothSkill.GROUND, near ? DASH_NONE : DASH_FORTH, false);
                }
            }
            default -> {
                if (first) {
                    this.start(level, MothSkill.SKY, near ? DASH_BACK : DASH_NONE, false);
                } else {
                    this.start(level, MothSkill.BACK_ONCE, near ? DASH_NONE : DASH_FORTH, !near);
                }
            }
        }
    }

    private void start(ServerLevel level, MothSkill next, int dash, boolean followDash) {
        this.skill = next;
        this.skillTick = 0;
        this.followDash = followDash;
        this.counterTarget = -1;
        this.counterTick = -1;
        this.skyTargets.clear();
        this.stabHit.clear();
        this.boss.restartAnimation(next.animation);
        if (next.lockTicks > 0) {
            BossCastLock.lock(this.boss, next.lockTicks);
        }
        if (dash == DASH_FORTH) {
            this.dashForth(level);
        } else if (dash == DASH_BACK) {
            this.dashBack(level);
        }
        Vec3 c = this.boss.position();
        float yaw = this.boss.getYRot();
        switch (next) {
            case SMITE -> {
                MothVfx.send(level, MothVfx.WINGS, c, yaw, 4.2f, 34);
                MothVfx.send(level, MothVfx.CHARGE, c, yaw, 2.4f, 14);
            }
            case ONCE -> MothVfx.send(level, MothVfx.WINGS, c, yaw, 4.0f, 30);
            case TWIRL -> {
                MothVfx.send(level, MothVfx.TWIRL, c, yaw, 7.2f, 52);
                MothVfx.send(level, MothVfx.WINGS, c, yaw, 3.8f, 52);
            }
            case GROUND -> {
                MothVfx.send(level, MothVfx.CHARGE, c, yaw, 3.0f, 28);
                MothVfx.send(level, MothVfx.WINGS, c, yaw, 4.2f, 44);
            }
            case SKY -> {
                MothVfx.send(level, MothVfx.WINGS, c, yaw, 4.8f, 70);
                MothVfx.send(level, MothVfx.CHARGE, c, yaw, 3.2f, 18);
            }
            case BLOCK -> {
                BossCastLock.shield(this.boss, BLOCK_WINDOW);
                this.sound(level, "item.armor.equip_netherite", SoundSource.HOSTILE, 2.0f, 0.8f);
                MothVfx.send(level, MothVfx.WARD, c, yaw, 2.4f, BLOCK_WINDOW);
            }
            default -> {
            }
        }
    }

    private void dashForth(ServerLevel level) {
        level.sendParticles(ParticleTypes.POOF, this.boss.getX(), this.boss.getY(), this.boss.getZ(), 8, 0.2, 0.2, 0.2, 0.05);
        this.boss.setDeltaMovement(new Vec3(0.0, 1.0, 0.0));
        this.boss.hurtMarked = true;
        this.dashDelay = 2;
        MothVfx.send(level, MothVfx.SHOCKWAVE, this.boss.position(), 0.0f, 2.2f, 4);
    }

    private void dashBack(ServerLevel level) {
        Vec3 look = this.boss.getLookAngle();
        level.sendParticles(ParticleTypes.POOF, this.boss.getX(), this.boss.getY(), this.boss.getZ(), 8, 0.2, 0.2, 0.2, 0.05);
        this.boss.setDeltaMovement(new Vec3(-3.0 * look.x, 0.15, -3.0 * look.z));
        this.boss.hurtMarked = true;
        MothVfx.send(level, MothVfx.DASH_TRAIL, this.boss.position(), this.boss.getYRot() + 180.0f, 6.0f, 0);
    }

    private void tickDash(ServerLevel level, LivingEntity target) {
        if (this.dashDelay < 0) {
            return;
        }
        if (this.dashDelay-- > 0) {
            return;
        }
        this.dashDelay = -1;
        if (target == null || !target.isAlive()) {
            return;
        }
        double dx = target.getX() - this.boss.getX();
        double dy = target.getY() - this.boss.getY();
        double dz = target.getZ() - this.boss.getZ();
        this.boss.setDeltaMovement(new Vec3(dx * 0.1, dy * 0.1 + 0.15, dz * 0.1));
        this.boss.hurtMarked = true;
        float yaw = (float) (Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0f;
        MothVfx.send(level, MothVfx.DASH_TRAIL, this.boss.position(), yaw, (float) Math.min(16.0, Math.sqrt(dx * dx + dz * dz)), 0);
    }

    private void tickSkill(ServerLevel level) {
        this.skillTick++;
        int t = this.skillTick;
        Vec3 c = this.boss.position();
        float yaw = this.boss.getYRot();
        int end = this.skill.duration;
        switch (this.skill) {
            case SMITE -> {
                if (t == 14) {
                    this.sound(level, "entity.evoker.cast_spell", SoundSource.HOSTILE, 1.6f, 0.7f);
                    for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, new AABB(c, c).inflate(18.0), MothCombat::canBeHit)) {
                        if (horizontalEdgeDistance(e, c.x, c.z) > 18.0) {
                            continue;
                        }
                        double gy = this.groundY(level, e.getX(), e.getY(), e.getZ());
                        this.scheduleBolt(level, e.getX(), gy, e.getZ(), 14, 1.8, 3.6, 1.0, 15.0);
                        int ringCount = this.phase2 ? 5 : 3;
                        double ringRadius = this.phase2 ? 4.0 : 3.6;
                        double baseAng = Math.toRadians(yaw);
                        for (int i = 0; i < ringCount; i++) {
                            double a = baseAng + Math.PI * 2.0 * i / ringCount;
                            double sx = e.getX() + Math.cos(a) * ringRadius;
                            double sz = e.getZ() + Math.sin(a) * ringRadius;
                            double sgy = this.groundY(level, sx, e.getY(), sz);
                            this.scheduleBolt(level, sx, sgy, sz, 18, 1.7, 3.4, 0.85, 12.0);
                        }
                    }
                }
            }
            case ONCE -> {
                if (t == 19) {
                    this.stepIn(0.65);
                }
                if (t == 24) {
                    this.slashOnce(level);
                }
            }
            case TWIRL -> {
                if (t == 16) {
                    this.stepIn(0.45);
                }
                if (t == 20) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.2f);
                    this.hitAround(level, c, 4.5, 1.0, 20.0, 0.0);
                    MothVfx.send(level, MothVfx.SLASH, c, yaw, 4.5f, MothVfx.SLASH_SPIN);
                }
                if (t == 48) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, c, 7.2, 1.0, 20.0, 0.0);
                    MothVfx.send(level, MothVfx.SLASH, c, yaw, 7.2f, MothVfx.SLASH_SPIN);
                    MothVfx.send(level, MothVfx.SHOCKWAVE, c, yaw, 7.2f, 6);
                    if (this.phase2) {
                        for (int i = 0; i < 4; i++) {
                            this.spawnStormWave(level, yaw + i * 90.0f, 0.7, 10.0);
                        }
                    }
                }
            }
            case GROUND -> {
                if (t == 14) {
                    MothVfx.send(level, MothVfx.TELEGRAPH_RING, c, yaw, 5.2f, MothVfx.ring(14, 0.0));
                    MothVfx.send(level, MothVfx.TELEGRAPH_RING, c, yaw, 9.0f, MothVfx.ring(28, 4.5));
                }
                if (t == 28) {
                    this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 0.75f);
                    level.sendParticles(ParticleTypes.EXPLOSION, c.x, c.y + 0.2, c.z, 10, 2.0, 0.4, 2.0, 0.01);
                    this.hitRing(level, c, 0.0, 5.2, 1.0, 20.0);
                    MothVfx.send(level, MothVfx.SHOCKWAVE, c, yaw, 5.2f, MothVfx.ring(5, 0.0));
                    MothVfx.send(level, MothVfx.LIGHTNING, c, yaw, 11.0f, this.boss.getRandom().nextInt(1000));
                }
                if (t == 42) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.2f);
                    this.sound(level, "entity.lightning_bolt.thunder", SoundSource.HOSTILE, 1.4f, 1.25f);
                    this.hitRing(level, c, 4.5, 9.0, 1.05, 20.0);
                    MothVfx.send(level, MothVfx.SLASH, c, yaw, 9.0f, MothVfx.SLASH_SPIN);
                    MothVfx.send(level, MothVfx.SHOCKWAVE, c, yaw, 9.0f, MothVfx.ring(8, 4.5));
                    for (int i = 0; i < 4; i++) {
                        this.spawnStormWave(level, yaw + 45.0f + i * 90.0f, 0.75, 12.0);
                    }
                }
            }
            case SKY -> this.tickSky(level, t, c);
            case BACK -> {
                if (t == 16) {
                    this.backSlash(level);
                }
            }
            case BACK_ONCE -> {
                if (t == 16) {
                    this.backSlash(level);
                }
                if (t == 35) {
                    this.boss.restartAnimation("attackswordonce");
                    BossCastLock.lock(this.boss, 50);
                    MothVfx.send(level, MothVfx.WINGS, c, yaw, 4.2f, 32);
                    if (this.followDash) {
                        this.dashForth(level);
                    }
                }
                if (t == 54) {
                    this.stepIn(0.65);
                }
                if (t == 59) {
                    this.slashOnce(level);
                }
            }
            case STAB -> this.tickStab(level, t);
            case TRIPLE -> {
                if (t == 14 || t == 22) {
                    this.stepIn(0.55);
                }
                if (t == 18) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.3f);
                    this.hitAround(level, c, 6.2, 0.65, 10.0, 65.0);
                    MothVfx.send(level, MothVfx.SLASH, c, yaw, 6.2f, MothVfx.SLASH_LEFT);
                }
                if (t == 26) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.4f);
                    this.hitAround(level, c, 6.2, 0.65, 10.0, 65.0);
                    MothVfx.send(level, MothVfx.SLASH, c, yaw, 6.2f, MothVfx.SLASH_RIGHT);
                }
                if (t == 29) {
                    this.sound(level, "entity.lightning_bolt.impact", SoundSource.HOSTILE, 1.6f, 1.2f);
                    this.hitAround(level, c, 7.2, 1.0, 20.0, 65.0);
                    MothVfx.send(level, MothVfx.SLASH, c, yaw, 7.2f, MothVfx.SLASH_OVERHEAD);
                    Vec3 look = this.flatLook();
                    MothVfx.send(level, MothVfx.LIGHTNING, c.add(look.scale(4.0)), yaw, 10.0f, this.boss.getRandom().nextInt(1000));
                    double[] dists = {5.0, 9.0, 13.0};
                    for (int i = 0; i < dists.length; i++) {
                        double sx = c.x + look.x * dists[i];
                        double sz = c.z + look.z * dists[i];
                        double gy = this.groundY(level, sx, c.y, sz);
                        this.scheduleBolt(level, sx, gy, sz, 6 + i * 3, 2.0, 4.0, 0.85, 14.0);
                    }
                }
            }
            case BLOCK -> end = this.tickBlock(level, t);
            default -> {
            }
        }
        if (this.skill != null && this.skillTick >= end) {
            this.finish();
        }
    }

    private void tickSky(ServerLevel level, int t, Vec3 c) {
        if (t == 18) {
            this.sound(level, "entity.evoker.prepare_attack", SoundSource.HOSTILE, 2.0f, 0.8f);
            level.sendParticles(MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get(), c.x, c.y + 1.0, c.z, 24, 0.5, 0.8, 0.5, 0.04);
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, new AABB(c, c).inflate(28.0), MothCombat::canBeHit)) {
                e.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 16, 1, false, false));
                e.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 48, 0, false, false));
                this.skyTargets.add(e.getId());
                MothVfx.send(level, MothVfx.LEVITATE, e.position(), 0.0f, 1.3f, 24);
            }
        }
        if (t == 24) {
            this.sound(level, "entity.evoker.cast_spell", SoundSource.HOSTILE, 1.6f, 0.9f);
            for (int id : this.skyTargets) {
                if (level.getEntity(id) instanceof LivingEntity e && e.isAlive()) {
                    double gy = this.groundY(level, e.getX(), e.getY(), e.getZ());
                    this.scheduleBolt(level, e.getX(), gy, e.getZ(), 12, 1.8, 5.0, 0.9, 12.0);
                }
            }
        }
        if (t == 38) {
            this.sound(level, "entity.evoker.cast_spell", SoundSource.HOSTILE, 1.6f, 1.05f);
            for (int id : this.skyTargets) {
                if (level.getEntity(id) instanceof LivingEntity e && e.isAlive()) {
                    for (int i = 0; i < 3; i++) {
                        double a = Math.PI * 2.0 * i / 3.0;
                        double sx = e.getX() + Math.cos(a) * 3.2;
                        double sz = e.getZ() + Math.sin(a) * 3.2;
                        double gy = this.groundY(level, sx, e.getY(), sz);
                        this.scheduleBolt(level, sx, gy, sz, 12, 1.8, 5.0, 0.85, 12.0);
                    }
                }
            }
        }
        if (t == 52) {
            this.sound(level, "entity.lightning_bolt.thunder", SoundSource.HOSTILE, 1.8f, 1.15f);
            double rot = this.phase2 ? Math.PI * 0.25 : 0.0;
            for (int id : this.skyTargets) {
                if (level.getEntity(id) instanceof LivingEntity e && e.isAlive()) {
                    double cgy = this.groundY(level, e.getX(), e.getY(), e.getZ());
                    this.scheduleBolt(level, e.getX(), cgy, e.getZ(), 12, 1.9, 6.0, 1.0, 15.0);
                    MothVfx.send(level, MothVfx.BOLT_RISE, new Vec3(e.getX(), cgy, e.getZ()), 0.0f, 10.0f, 12);
                    for (int i = 0; i < 4; i++) {
                        double a = rot + Math.PI * 0.5 * i;
                        double sx = e.getX() + Math.cos(a) * 3.8;
                        double sz = e.getZ() + Math.sin(a) * 3.8;
                        double gy = this.groundY(level, sx, e.getY(), sz);
                        this.scheduleBolt(level, sx, gy, sz, 12, 1.8, 5.0, 0.85, 12.0);
                    }
                }
            }
            this.skyTargets.clear();
        }
    }

    private void scheduleBolt(ServerLevel level, double x, double y, double z, int delay, double radius, double height, double multiplier, double grief) {
        this.strikes.add(new Strike(x, y, z, this.clock + delay, radius, height, multiplier, grief));
        MothVfx.send(level, MothVfx.BOLT_FALL, new Vec3(x, y, z), 0.0f, (float) radius, delay);
    }

    private double groundY(ServerLevel level, double x, double y, double z) {
        BlockPos.MutableBlockPos pos = BlockPos.containing(x, y + 1.0, z).mutable();
        for (int i = 0; i < 12; i++) {
            if (!level.getBlockState(pos.below()).getCollisionShape(level, pos.below()).isEmpty()) {
                return pos.getY();
            }
            pos.move(0, -1, 0);
        }
        return y;
    }

    private void finish() {
        this.skill = null;
        this.skillTick = 0;
        this.followDash = false;
        this.counterTarget = -1;
        this.counterTick = -1;
        this.skyTargets.clear();
        this.stabHit.clear();
    }

    private void tickStab(ServerLevel level, int t) {
        if (t == STAB_LOCK) {
            Vec3 look = this.flatLook();
            this.dirX = look.x;
            this.dirZ = look.z;
            this.sound(level, "item.trident.riptide_1", SoundSource.HOSTILE, 2.0f, 0.8f);
            MothVfx.send(level, MothVfx.TELEGRAPH_LINE, this.boss.position(), this.boss.getYRot(), 9.5f, STAB_START - STAB_LOCK);
        }
        if (t == STAB_START) {
            this.sound(level, "entity.lightning_bolt.impact", SoundSource.HOSTILE, 1.4f, 1.6f);
            MothVfx.send(level, MothVfx.DASH_TRAIL, this.boss.position(), this.boss.getYRot(), 9.5f, 1);
        }
        if (t >= STAB_START && t <= STAB_END) {
            this.boss.setDeltaMovement(new Vec3(this.dirX * 1.25, this.boss.getDeltaMovement().y, this.dirZ * 1.25));
            this.boss.hurtMarked = true;
        }
        if (t >= STAB_START && t <= STAB_END + 2) {
            DamageSource source = this.boss.damageSources().mobAttack(this.boss);
            float damage = BossAttributes.skillDamage(this.boss, 1.2);
            Vec3 c = this.boss.position();
            for (LivingEntity e : this.targetsInCylinder(level, c, 1.9, 3.2)) {
                if (this.stabHit.add(e.getId())) {
                    this.damage(e, source, damage, 20.0, "STAB", 1.9);
                }
            }
        }
    }

    private int tickBlock(ServerLevel level, int t) {
        if (this.counterTick < 0) {
            if (t == BLOCK_WINDOW) {
                Vec3 c = this.boss.position();
                float yaw = this.boss.getYRot();
                this.sound(level, "entity.lightning_bolt.impact", SoundSource.HOSTILE, 1.6f, 1.0f);
                this.hitAround(level, c, 5.2, 0.85, 12.0, 0.0);
                MothVfx.send(level, MothVfx.SHOCKWAVE, c, yaw, 5.2f, 5);
                MothVfx.send(level, MothVfx.LIGHTNING, c, yaw, 9.0f, this.boss.getRandom().nextInt(1000));
                for (int i = 0; i < 4; i++) {
                    this.spawnStormWave(level, yaw + i * 90.0f, 0.7, 10.0);
                }
            }
            return MothSkill.BLOCK.duration;
        }
        if (t == this.counterTick) {
            this.blink(level);
        }
        if (t == this.counterTick + COUNTER_HIT) {
            Vec3 c = this.boss.position();
            float yaw = this.boss.getYRot();
            this.sound(level, "entity.lightning_bolt.thunder", SoundSource.HOSTILE, 1.2f, 1.4f);
            this.hitAround(level, c, 5.2, 1.4, 30.0, 0.0);
            MothVfx.send(level, MothVfx.SLASH, c, yaw, 5.2f, MothVfx.SLASH_SPIN);
            MothVfx.send(level, MothVfx.LIGHTNING, c, yaw, 12.0f, this.boss.getRandom().nextInt(1000));
            this.spawnStormWaveFan(level, yaw, 3, 22.0f, 0.8, 12.0);
        }
        return this.counterTick + COUNTER_HIT + 10;
    }

    private void blink(ServerLevel level) {
        if (!(level.getEntity(this.counterTarget) instanceof LivingEntity target) || !target.isAlive()) {
            BossCastLock.release(this.boss);
            this.finish();
            return;
        }
        Vec3 from = this.boss.position();
        Vec3 back = new Vec3(target.getLookAngle().x, 0.0, target.getLookAngle().z);
        back = back.lengthSqr() < 1.0E-4 ? new Vec3(0.0, 0.0, 1.0) : back.normalize();
        Vec3 dest = target.position().subtract(back.scale(2.2));
        if (!this.fits(level, dest)) {
            Vec3 away = from.subtract(target.position());
            away = new Vec3(away.x, 0.0, away.z);
            dest = away.lengthSqr() < 1.0E-4 ? from : target.position().add(away.normalize().scale(2.2));
            if (!this.fits(level, dest)) {
                dest = from;
            }
        }
        MothVfx.send(level, MothVfx.BLINK, from, this.boss.getYRot(), 1.0f, 0);
        this.boss.teleportTo(dest.x, dest.y, dest.z);
        this.faceInstantly(target);
        BossCastLock.release(this.boss);
        BossCastLock.lock(this.boss, COUNTER_HIT + 10);
        this.boss.restartAnimation("attackback");
        this.sound(level, "entity.enderman.teleport", SoundSource.HOSTILE, 1.4f, 1.4f);
        MothVfx.send(level, MothVfx.BLINK, dest, this.boss.getYRot(), 1.0f, 1);
        MothVfx.send(level, MothVfx.TELEGRAPH_RING, dest, this.boss.getYRot(), 5.2f, MothVfx.ring(COUNTER_HIT, 0.0));
    }

    private boolean fits(ServerLevel level, Vec3 pos) {
        return level.noCollision(this.boss, this.boss.getBoundingBox().move(pos.subtract(this.boss.position())));
    }

    private void slashOnce(ServerLevel level) {
        Vec3 c = this.boss.position();
        float yaw = this.boss.getYRot();
        this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.2f);
        this.hitAround(level, c, 10.5, 1.0, 20.0, 0.0);
        MothVfx.send(level, MothVfx.SLASH, c, yaw, 10.5f, MothVfx.SLASH_LEFT);
        MothVfx.send(level, MothVfx.SHOCKWAVE, c, yaw, 10.5f, 7);
        this.spawnStormWaveFan(level, yaw, this.phase2 ? 3 : 1, 20.0f, 0.75, 12.0);
    }

    private void backSlash(ServerLevel level) {
        Vec3 c = this.boss.position();
        this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.2f);
        this.hitAround(level, c, 8.0, 1.0, 20.0, 0.0);
        MothVfx.send(level, MothVfx.SLASH, c, this.boss.getYRot(), 8.0f, MothVfx.SLASH_RIGHT);
    }

    private void spawnStormWaveFan(ServerLevel level, float baseYaw, int count, float spreadDeg, double multiplier, double grief) {
        this.sound(level, "item.trident.riptide_1", SoundSource.HOSTILE, 1.2f, 1.35f);
        if (count <= 1) {
            this.spawnStormWave(level, baseYaw, multiplier, grief);
            return;
        }
        float start = -spreadDeg * (count - 1) * 0.5f;
        for (int i = 0; i < count; i++) {
            this.spawnStormWave(level, baseYaw + start + spreadDeg * i, multiplier, grief);
        }
    }

    private void spawnStormWave(ServerLevel level, float yawDeg, double multiplier, double grief) {
        float rad = yawDeg * Mth.DEG_TO_RAD;
        double dx = -Mth.sin(rad);
        double dz = Mth.cos(rad);
        Vec3 origin = this.boss.position().add(dx * 1.6, 0.0, dz * 1.6);
        this.waves.add(new StormWave(origin, dx, dz, STORM_WAVE_RADIUS, multiplier, grief, STORM_WAVE_TICKS));
        MothVfx.send(level, MothVfx.STORM_WAVE, origin, yawDeg, (float) STORM_WAVE_RADIUS, STORM_WAVE_TICKS);
    }

    private void tickWaves(ServerLevel level) {
        if (this.waves.isEmpty()) {
            return;
        }
        DamageSource source = this.boss.damageSources().mobAttack(this.boss);
        Iterator<StormWave> it = this.waves.iterator();
        while (it.hasNext()) {
            StormWave w = it.next();
            w.x += w.dx * STORM_WAVE_SPEED;
            w.z += w.dz * STORM_WAVE_SPEED;
            w.ticksLeft--;
            float damage = BossAttributes.skillDamage(this.boss, w.multiplier);
            AABB box = new AABB(w.x - w.radius, w.y - 1.0, w.z - w.radius, w.x + w.radius, w.y + 3.2, w.z + w.radius);
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, MothCombat::canBeHit)) {
                if (horizontalEdgeDistance(e, w.x, w.z) <= w.radius && w.hit.add(e.getId())) {
                    this.damage(e, source, damage, w.grief, "STORM_WAVE", w.radius);
                    e.knockback(0.35, -w.dx, -w.dz);
                    e.hurtMarked = true;
                }
            }
            if (w.ticksLeft <= 0) {
                it.remove();
            }
        }
    }

    private Vec3 flatLook() {
        Vec3 look = this.boss.getLookAngle();
        Vec3 flat = new Vec3(look.x, 0.0, look.z);
        if (flat.lengthSqr() < 1.0E-4) {
            float rad = this.boss.getYRot() * Mth.DEG_TO_RAD;
            return new Vec3(-Mth.sin(rad), 0.0, Mth.cos(rad));
        }
        return flat.normalize();
    }

    private void tickStrikes(ServerLevel level) {
        if (this.strikes.isEmpty()) {
            return;
        }
        Iterator<Strike> it = this.strikes.iterator();
        while (it.hasNext()) {
            Strike s = it.next();
            if (this.clock < s.fireAt) {
                continue;
            }
            it.remove();
            DamageSource source = this.boss.damageSources().mobAttack(this.boss);
            float damage = BossAttributes.skillDamage(this.boss, s.multiplier);
            AABB box = new AABB(s.x - s.radius, s.y - 0.8, s.z - s.radius, s.x + s.radius, s.y + s.height, s.z + s.radius);
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, MothCombat::canBeHit)) {
                if (horizontalEdgeDistance(e, s.x, s.z) <= s.radius) {
                    this.damage(e, source, damage, s.grief, "STRIKE", s.radius);
                }
            }
            level.sendParticles(MokelsBossfightSaphyraModParticleTypes.GHOSTLY_FLAME_PARTICLE.get(), s.x, s.y + 0.3, s.z, 5, 0.4, 0.2, 0.4, 0.02);
            SoundEvent impact = BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.lightning_bolt.impact"));
            if (impact != null) {
                level.playSound(null, s.x, s.y, s.z, impact, SoundSource.HOSTILE, 0.7f, 1.55f);
            }
        }
    }

    private List<LivingEntity> targetsInCylinder(ServerLevel level, Vec3 c, double radius, double height) {
        AABB box = new AABB(c.x - radius, c.y - 1.0, c.z - radius, c.x + radius, c.y + height, c.z + radius);
        List<LivingEntity> out = new ArrayList<>();
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, MothCombat::canBeHit)) {
            if (horizontalEdgeDistance(e, c.x, c.z) <= radius) {
                out.add(e);
            }
        }
        return out;
    }

    private void hitRing(ServerLevel level, Vec3 center, double innerRadius, double outerRadius, double multiplier, double grief) {
        DamageSource source = this.boss.damageSources().mobAttack(this.boss);
        float damage = BossAttributes.skillDamage(this.boss, multiplier);
        double height = Math.max(3.2, outerRadius * 0.4);
        AABB box = new AABB(center.x - outerRadius, center.y - 1.0, center.z - outerRadius, center.x + outerRadius, center.y + height, center.z + outerRadius);
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, MothCombat::canBeHit)) {
            double edgeDist = horizontalEdgeDistance(e, center.x, center.z);
            if (edgeDist > outerRadius) {
                continue;
            }
            if (innerRadius > 0.0) {
                double centerDist = Math.hypot(e.getX() - center.x, e.getZ() - center.z);
                if (centerDist < innerRadius) {
                    continue;
                }
            }
            this.damage(e, source, damage, grief, this.skill == null ? "RING" : this.skill.name(), outerRadius);
        }
    }

    private void hitAround(ServerLevel level, Vec3 center, double radius, double multiplier, double grief, double halfConeDeg) {
        DamageSource source = this.boss.damageSources().mobAttack(this.boss);
        float damage = BossAttributes.skillDamage(this.boss, multiplier);
        double height = Math.max(3.0, radius * 0.4);
        Vec3 look = this.flatLook();
        double cosLimit = Math.cos(Math.toRadians(halfConeDeg));
        for (LivingEntity e : this.targetsInCylinder(level, center, radius, height)) {
            if (halfConeDeg > 0.0) {
                double dx = e.getX() - center.x;
                double dz = e.getZ() - center.z;
                double d = Math.sqrt(dx * dx + dz * dz);
                if (d > 1.5 && (dx * look.x + dz * look.z) / d < cosLimit) {
                    continue;
                }
            }
            this.damage(e, source, damage, grief, this.skill == null ? "NONE" : this.skill.name(), radius);
        }
    }

    private void damage(LivingEntity e, DamageSource source, float damage, double grief, String tag, double radius) {
        if (e instanceof Player) {
            MokelsBossfightSaphyraMod.LOGGER.info("[MothHitDebug] skill={} tick={} radius={} dist={} damage={}",
                    tag, this.skillTick, radius, String.format("%.2f", Math.sqrt(e.distanceToSqr(this.boss.getX(), e.getY(), this.boss.getZ()))), damage);
        }
        e.hurt(source, damage);
        if (grief > 0.0 && e instanceof Player player) {
            addGrief(player, grief);
        }
    }

    private static double horizontalEdgeDistance(LivingEntity e, double x, double z) {
        AABB bb = e.getBoundingBox();
        double nx = Mth.clamp(x, bb.minX, bb.maxX) - x;
        double nz = Mth.clamp(z, bb.minZ, bb.maxZ) - z;
        return Math.sqrt(nx * nx + nz * nz);
    }

    public static boolean canBeHit(LivingEntity e) {
        return !(e instanceof MothbossEntity) && e.isAlive() && !e.isSpectator() && !e.hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE);
    }

    public static void addGrief(Player player, double amount) {
        MokelsBossfightSaphyraModVariables.PlayerVariables vars = MokelsBossfightSaphyraModVariables.getPlayerVariables(player);
        vars.Grief += amount;
        vars.syncPlayerVariables(player);
    }

    private void tickMusic(ServerLevel level, LivingEntity target) {
        CompoundTag data = this.boss.getPersistentData();
        double music = data.getDouble("music");
        if (target != null) {
            music += 1.0;
            data.putDouble("music", music);
        }
        for (int i = 0; i < THEME_TICKS.length; i++) {
            if (music == THEME_TICKS[i]) {
                BossMusic.stop(level, this.boss.getX(), this.boss.getY(), this.boss.getZ(), null);
                this.sound(level, "saphyra_reborn:ivorydevil_theme" + (i + 1), SoundSource.MUSIC, 2.0f, 1.0f);
            }
        }
        if (music >= MUSIC_RESET) {
            data.putDouble("music", 0.0);
        }
    }

    private void sound(ServerLevel level, String id, SoundSource source, float volume, float pitch) {
        SoundEvent event = BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse(id));
        if (event != null) {
            level.playSound(null, this.boss.getX(), this.boss.getY(), this.boss.getZ(), event, source, volume, pitch);
        }
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Timeline", this.timeline);
        tag.putInt("Pending", this.pendingPoint);
        tag.putBoolean("Phase2", this.phase2);
        tag.putInt("PhaseShift", this.phaseShiftTicks);
        return tag;
    }

    public void load(CompoundTag tag) {
        this.timeline = tag.getInt("Timeline");
        this.pendingPoint = tag.contains("Pending") ? tag.getInt("Pending") : -1;
        this.phase2 = tag.getBoolean("Phase2");
        this.phaseShiftTicks = tag.getInt("PhaseShift");
        this.skill = null;
        this.skillTick = 0;
        this.waves.clear();
        this.boss.setPhase2Visual(this.phase2 && this.phaseShiftTicks <= PHASE_SHIFT_TICKS - PHASE_BURST_TICK);
    }
}
