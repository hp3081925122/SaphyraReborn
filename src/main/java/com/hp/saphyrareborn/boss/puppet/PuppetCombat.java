package com.hp.saphyrareborn.boss.puppet;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.BossAttributes;
import com.hp.saphyrareborn.boss.BossCastLock;
import com.hp.saphyrareborn.boss.BossConfig;
import com.hp.saphyrareborn.boss.BossMusic;
import com.hp.saphyrareborn.entity.PuppetOfYivaNemorEntity;
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
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class PuppetCombat {
    private static final int[] POINTS = {20, 150};
    private static final int RESET = 210;
    private static final int[] THEME_TICKS = {20, 320, 620, 920, 1220, 1520, 1820};
    private static final int MUSIC_RESET = 1900;
    private static final int PHASE_SHIFT_TICKS = 60;
    private static final int PHASE_BURST_TICK = 30;
    private static final int CHAIN_GAP = 8;
    private static final double MELEE_OFFSET = 3.0;
    private static final double MELEE_RADIUS = 4.0;
    private static final double[] FIFTH_DISTANCES = {4.5, 9.0, 13.5, 18.0};
    private static final double FIFTH_RADIUS = 2.6;
    private static final int FIFTH_AIM = 12;
    private static final int FIFTH_FIRST_HIT = 20;
    private static final int THIRD_AIM = 8;
    private static final int THIRD_CHOP = 23;
    private static final double[] CRACK_DISTANCES = {6.0, 9.0, 12.0, 15.0};
    private static final double CRACK_RADIUS = 2.5;
    private static final int THIRD_RISE = 43;
    private static final double RISE_RADIUS = 5.2;
    private static final int CROSS_HIT = 24;
    private static final int CROSS_PLUS_HIT = 36;
    private static final double CROSS_RADIUS = 2.1;
    private static final double CROSS_ARM = 6.2;
    private static final double CROSS_MAX_REACH = 12.0;
    private static final int FOURTH_HIT_LATEST = 42;
    private static final double INFERNO_WAVE_SPEED = 1.1;
    private static final int INFERNO_WAVE_TICKS = 15;
    private static final double INFERNO_WAVE_RADIUS = 1.9;

    private final PuppetOfYivaNemorEntity boss;
    private final Set<Integer> struck = new HashSet<>();
    private final List<InfernoWave> waves = new ArrayList<>();
    private int timeline;
    private PuppetSkill skill;
    private int skillTick;
    private int pendingPoint = -1;
    private boolean phase2;
    private boolean fourthLanded;
    private int phaseShiftTicks;
    private int dashDelay = -1;
    private PuppetSkill chainSkill;
    private int chainLeft;
    private int chainTimer;
    private double dirX;
    private double dirZ;
    private double originX;
    private double originY;
    private double originZ;
    private double crossX;
    private double crossY;
    private double crossZ;

    private static final class InfernoWave {
        double x;
        double y;
        double z;
        final double dx;
        final double dz;
        final double radius;
        final double multiplier;
        final double rotting;
        int ticksLeft;
        final Set<Integer> hit = new HashSet<>();

        InfernoWave(Vec3 pos, double dx, double dz, double radius, double multiplier, double rotting, int ticksLeft) {
            this.x = pos.x;
            this.y = pos.y;
            this.z = pos.z;
            this.dx = dx;
            this.dz = dz;
            this.radius = radius;
            this.multiplier = multiplier;
            this.rotting = rotting;
            this.ticksLeft = ticksLeft;
        }
    }

    public PuppetCombat(PuppetOfYivaNemorEntity boss) {
        this.boss = boss;
    }

    public boolean isInvulnerable() {
        return this.phaseShiftTicks > 0;
    }

    public void resetFight() {
        this.cancelSkill();
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
        LivingEntity target = this.boss.getTarget();
        this.tickWaves(level);
        this.tickDash(level, target);
        this.tickMusic(level, target);
        this.faceTarget(target);
        this.debugLog(target);
        if (!this.phase2 && BossConfig.PUPPET_PHASE2_ENABLED.get()
                && this.boss.getHealth() <= this.boss.getMaxHealth() * BossConfig.PUPPET_PHASE2_RATIO.get()) {
            this.startPhaseShift(level);
        }
        if (this.phaseShiftTicks > 0) {
            this.tickPhaseShift(level);
            return;
        }
        boolean stunned = this.boss.hasEffect(MokelsBossfightSaphyraModMobEffects.STUN);
        if (stunned && this.isBusy()) {
            this.cancelSkill();
            this.boss.stopProcedureAnimation();
        }
        if (target != null) {
            this.timeline++;
            int point = this.pointAt(this.timeline);
            if (point >= 0 && !stunned) {
                if (this.isBusy()) {
                    this.pendingPoint = point;
                } else {
                    this.firePoint(level, point);
                }
            }
            if (this.timeline >= this.scaled(RESET)) {
                this.timeline = 0;
            }
        }
        if (this.chainLeft > 0 && !stunned && this.skill == null && --this.chainTimer <= 0) {
            this.chainLeft--;
            this.chainTimer = this.scaled(CHAIN_GAP);
            this.start(level, this.chainSkill);
        }
        if (this.skill != null) {
            this.tickSkill(level, target);
        } else if (this.pendingPoint >= 0 && !this.isBusy() && !stunned && target != null) {
            int point = this.pendingPoint;
            this.pendingPoint = -1;
            this.firePoint(level, point);
        }
    }

    private boolean isBusy() {
        return this.skill != null || this.chainLeft > 0;
    }

    private float turnRate() {
        if (this.phaseShiftTicks > 0 || this.skill == PuppetSkill.CROSS) {
            return 0.0f;
        }
        if (this.skill == PuppetSkill.FIFTH && this.skillTick >= FIFTH_AIM) {
            return 0.0f;
        }
        if (this.skill == PuppetSkill.THIRD && this.skillTick >= THIRD_AIM && this.skillTick <= THIRD_CHOP + 8) {
            return 0.0f;
        }
        return this.skill == null ? 30.0f : 10.0f;
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
        float yaw = Mth.approachDegrees(this.boss.getYRot(), yawOf(dx, dz), rate);
        this.boss.setYRot(yaw);
        this.boss.setYBodyRot(yaw);
    }

    private void faceYaw(float yaw) {
        this.boss.setYRot(yaw);
        this.boss.setYBodyRot(yaw);
        this.boss.setYHeadRot(yaw);
    }

    private static float yawOf(double dx, double dz) {
        return (float) (Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0f;
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
        MokelsBossfightSaphyraMod.LOGGER.info("[PuppetDebug] id={} onGround={} yRot={} skill={} skillTick={} chain={}x{} waves={} timeline={} phase2={} health={}/{}",
                this.boss.getId(), this.boss.onGround(), String.format("%.1f", Mth.wrapDegrees(this.boss.getYRot())), this.skill, this.skillTick,
                this.chainSkill, this.chainLeft, this.waves.size(), this.timeline, this.phase2, this.boss.getHealth(), this.boss.getMaxHealth());
    }

    private void startPhaseShift(ServerLevel level) {
        this.phase2 = true;
        this.cancelSkill();
        this.pendingPoint = -1;
        this.timeline = 0;
        this.phaseShiftTicks = PHASE_SHIFT_TICKS;
        BossCastLock.lock(this.boss, PHASE_SHIFT_TICKS);
        this.boss.restartAnimation("phaseshift");
        this.sound(level, "entity.blaze.ambient", SoundSource.HOSTILE, 2.0f, 0.5f);
        PuppetVfx.send(level, PuppetVfx.PHASE_SHIFT, this.boss.position(), this.boss.getYRot(), 6.0f, PHASE_SHIFT_TICKS);
        PuppetVfx.send(level, PuppetVfx.STRINGS, this.boss.position(), this.boss.getYRot(), 4.5f, PHASE_SHIFT_TICKS);
    }

    private void tickPhaseShift(ServerLevel level) {
        int age = PHASE_SHIFT_TICKS - this.phaseShiftTicks;
        if (age == PHASE_BURST_TICK) {
            this.boss.setPhase2Visual(true);
            this.sound(level, "entity.blaze.shoot", SoundSource.HOSTILE, 2.0f, 0.6f);
            this.sound(level, "entity.generic.explode", SoundSource.HOSTILE, 1.6f, 0.7f);
            Vec3 c = this.boss.position();
            level.sendParticles(MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get(), c.x, c.y + 1.2, c.z, 40, 2.0, 1.2, 2.0, 0.05);
            PuppetVfx.send(level, PuppetVfx.EMBER_BURST, c, 0.0f, 6.0f, 0);
            PuppetVfx.send(level, PuppetVfx.SHOCKWAVE, c, 0.0f, 8.5f, 8);
            for (int i = 0; i < 6; i++) {
                this.spawnInfernoWave(level, this.boss.getYRot() + i * 60.0f, 0.7, 10.0);
            }
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, this.boss.getBoundingBox().inflate(6.0), e -> e != this.boss && !(e instanceof PuppetOfYivaNemorEntity))) {
                e.knockback(0.8, this.boss.getX() - e.getX(), this.boss.getZ() - e.getZ());
                e.hurtMarked = true;
            }
        }
        this.phaseShiftTicks--;
    }

    private void cancelSkill() {
        this.skill = null;
        this.skillTick = 0;
        this.dashDelay = -1;
        this.chainSkill = null;
        this.chainLeft = 0;
        this.chainTimer = 0;
        BossCastLock.release(this.boss);
    }

    private int scaled(int tick) {
        return this.phase2 ? Math.max(1, Mth.floor(tick * BossConfig.PUPPET_PHASE2_CADENCE.get() + 0.5)) : tick;
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
        if (this.phase2 && this.boss.getRandom().nextDouble() < BossConfig.PUPPET_PHASE2_NEW_SKILL_CHANCE.get()) {
            this.start(level, point == 0 ? PuppetSkill.THIRD : PuppetSkill.CROSS);
            return;
        }
        if (point == 0) {
            if (first) {
                this.start(level, PuppetSkill.FIFTH);
                if (near) {
                    this.hopBack(level);
                }
                this.chain(PuppetSkill.FIFTH);
            } else if (near) {
                this.start(level, PuppetSkill.FOURTH);
                this.chain(PuppetSkill.FOURTH);
            } else {
                this.hopBack(level);
                this.chain(PuppetSkill.FOURTH);
            }
            return;
        }
        if (first) {
            if (!near) {
                this.dashForth(level);
            }
            this.start(level, PuppetSkill.FIRST);
        } else {
            this.start(level, PuppetSkill.SECOND);
            if (!near) {
                this.dashForth(level);
            }
        }
    }

    private void chain(PuppetSkill next) {
        this.chainSkill = next;
        this.chainLeft = 2;
        this.chainTimer = this.scaled(CHAIN_GAP);
    }

    private void start(ServerLevel level, PuppetSkill next) {
        this.skill = next;
        this.skillTick = 0;
        this.struck.clear();
        this.fourthLanded = false;
        this.boss.restartAnimation(next.animation);
        BossCastLock.lock(this.boss, next.lockTicks);
        Vec3 c = this.boss.position();
        float yaw = this.boss.getYRot();
        switch (next) {
            case FOURTH -> {
                this.hopBack(level);
                PuppetVfx.send(level, PuppetVfx.STRINGS, c, yaw, 3.5f, 36);
            }
            case FIFTH -> {
                PuppetVfx.send(level, PuppetVfx.CHARGE, c, yaw, 1.8f, FIFTH_FIRST_HIT);
                PuppetVfx.send(level, PuppetVfx.STRINGS, c, yaw, 3.2f, 32);
            }
            case THIRD -> {
                PuppetVfx.send(level, PuppetVfx.CHARGE, c, yaw, 2.5f, THIRD_CHOP);
                PuppetVfx.send(level, PuppetVfx.STRINGS, c, yaw, 3.8f, 52);
                this.sound(level, "entity.blaze.ambient", SoundSource.HOSTILE, 1.6f, 0.7f);
            }
            case CROSS -> {
                this.aimCross(level);
                PuppetVfx.send(level, PuppetVfx.STRINGS, c, yaw, 3.8f, 44);
            }
            default -> {
            }
        }
    }

    private void hopBack(ServerLevel level) {
        Vec3 look = this.boss.getLookAngle();
        this.boss.setDeltaMovement(new Vec3(-1.0 * look.x, 0.01, -1.0 * look.z));
        this.boss.hurtMarked = true;
        level.sendParticles(ParticleTypes.POOF, this.boss.getX(), this.boss.getY(), this.boss.getZ(), 8, 0.1, 0.1, 0.1, 0.1);
        PuppetVfx.send(level, PuppetVfx.DASH_TRAIL, this.boss.position(), this.boss.getYRot() + 180.0f, 4.0f, 0);
    }

    private void dashForth(ServerLevel level) {
        level.sendParticles(ParticleTypes.POOF, this.boss.getX(), this.boss.getY(), this.boss.getZ(), 8, 0.2, 0.2, 0.2, 0.05);
        this.boss.setDeltaMovement(new Vec3(0.0, 1.0, 0.0));
        this.boss.hurtMarked = true;
        this.dashDelay = 2;
        PuppetVfx.send(level, PuppetVfx.SHOCKWAVE, this.boss.position(), 0.0f, 2.2f, 4);
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
        PuppetVfx.send(level, PuppetVfx.DASH_TRAIL, this.boss.position(), yawOf(dx, dz), (float) Math.min(16.0, Math.sqrt(dx * dx + dz * dz)), 0);
    }

    private void tickSkill(ServerLevel level, LivingEntity target) {
        this.skillTick++;
        int t = this.skillTick;
        Vec3 c = this.boss.position();
        float yaw = this.boss.getYRot();
        switch (this.skill) {
            case FIRST -> {
                if (t == 22 || t == 42) {
                    this.stepIn(0.6);
                }
                if (t == 28 || t == 48) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 1.2f, 1.0f);
                    this.meleeHit(level, 1.0, 20.0);
                    PuppetVfx.send(level, PuppetVfx.SLASH, c, yaw, (float) (MELEE_OFFSET + MELEE_RADIUS), t == 28 ? PuppetVfx.SLASH_RIGHT : PuppetVfx.SLASH_LEFT);
                    if (t == 48 && this.phase2) {
                        this.spawnInfernoWaveFan(level, yaw, 1, 0.0f, 0.75, 12.0);
                    }
                }
            }
            case SECOND -> {
                if (t == 18) {
                    this.stepIn(0.65);
                }
                if (t == 24) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 1.4f, 0.95f);
                    this.meleeHit(level, 1.0, 20.0);
                    this.overheadVfx(level, c, yaw);
                    this.spawnInfernoWaveFan(level, yaw, this.phase2 ? 3 : 1, 22.0f, 0.75, 12.0);
                }
            }
            case FOURTH -> this.tickFourth(level, t, target);
            case FIFTH -> this.tickFifth(level, t);
            case THIRD -> this.tickThird(level, t);
            case CROSS -> this.tickCross(level, t);
            default -> {
            }
        }
        if (this.skill != null && this.skillTick >= this.skill.duration) {
            this.skill = null;
            this.skillTick = 0;
        }
    }

    private void overheadVfx(ServerLevel level, Vec3 c, float yaw) {
        Vec3 front = this.front(MELEE_OFFSET);
        PuppetVfx.send(level, PuppetVfx.SLASH, c, yaw, (float) (MELEE_OFFSET + MELEE_RADIUS), PuppetVfx.SLASH_OVERHEAD);
        PuppetVfx.send(level, PuppetVfx.SHOCKWAVE, front, yaw, (float) MELEE_RADIUS, 5);
        PuppetVfx.send(level, PuppetVfx.SCORCH, front, yaw, (float) MELEE_RADIUS, 30);
    }

    private void tickFourth(ServerLevel level, int t, LivingEntity target) {
        if (t == 19) {
            this.boss.setDeltaMovement(new Vec3(0.0, 1.0, 0.0));
            this.boss.hurtMarked = true;
            level.sendParticles(ParticleTypes.POOF, this.boss.getX(), this.boss.getY(), this.boss.getZ(), 8, 0.1, 0.1, 0.1, 0.1);
            PuppetVfx.send(level, PuppetVfx.SHOCKWAVE, this.boss.position(), 0.0f, 2.5f, 4);
        }
        if (t == 29 && target != null && target.isAlive()) {
            double dx = target.getX() - this.boss.getX();
            double dy = target.getY() - this.boss.getY();
            double dz = target.getZ() - this.boss.getZ();
            this.boss.setDeltaMovement(new Vec3(dx * 0.35, dy * 0.35 + 0.15, dz * 0.35));
            this.boss.hurtMarked = true;
            PuppetVfx.send(level, PuppetVfx.DASH_TRAIL, this.boss.position(), yawOf(dx, dz), (float) Math.min(16.0, Math.sqrt(dx * dx + dz * dz)), 0);
        }
        if (t >= 30 && !this.fourthLanded && (this.boss.onGround() || t >= FOURTH_HIT_LATEST)) {
            this.fourthLanded = true;
            float yaw = this.boss.getYRot();
            this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.0f);
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 1.2f, 1.15f);
            Vec3 front = this.front(MELEE_OFFSET);
            this.hitAround(level, this.sourcelessDamage(level), front, MELEE_RADIUS, 1.0, 20.0, false, this.groundDepth(level, front));
            PuppetVfx.send(level, PuppetVfx.SLASH, this.boss.position(), yaw, (float) (MELEE_OFFSET + MELEE_RADIUS), PuppetVfx.SLASH_DOUBLE);
            PuppetVfx.send(level, PuppetVfx.SHOCKWAVE, front, yaw, (float) MELEE_RADIUS, 5);
            PuppetVfx.send(level, PuppetVfx.SCORCH, front, yaw, (float) MELEE_RADIUS, 30);
            if (this.phase2) {
                this.spawnInfernoWaveFan(level, yaw, 2, 36.0f, 0.75, 12.0);
            }
        }
    }

    private double groundDepth(ServerLevel level, Vec3 at) {
        BlockPos.MutableBlockPos pos = BlockPos.containing(at).mutable();
        for (int i = 0; i < 8; i++) {
            if (!level.getBlockState(pos.below()).getCollisionShape(level, pos.below()).isEmpty()) {
                return Math.max(1.0, at.y - pos.getY() + 1.0);
            }
            pos.move(0, -1, 0);
        }
        return 8.0;
    }

    private void tickFifth(ServerLevel level, int t) {
        float[] fanOffsets = this.phase2 ? new float[]{-24.0f, 0.0f, 24.0f} : new float[]{0.0f};
        if (t == FIFTH_AIM) {
            this.lockDirection();
            this.originX = this.boss.getX();
            this.originY = this.boss.getY();
            this.originZ = this.boss.getZ();
            float baseYaw = yawOf(this.dirX, this.dirZ);
            double reach = FIFTH_DISTANCES[FIFTH_DISTANCES.length - 1] + FIFTH_RADIUS;
            Vec3 origin = new Vec3(this.originX, this.originY, this.originZ);
            for (float off : fanOffsets) {
                float rayYaw = baseYaw + off;
                PuppetVfx.send(level, PuppetVfx.TELEGRAPH_LINE, origin, rayYaw, (float) reach, PuppetVfx.line(FIFTH_FIRST_HIT - FIFTH_AIM + 4, FIFTH_RADIUS * 0.78));
                Vec3 axis = this.crossAxis(off);
                for (int i = 0; i < FIFTH_DISTANCES.length; i++) {
                    Vec3 p = new Vec3(this.originX + axis.x * FIFTH_DISTANCES[i], this.originY, this.originZ + axis.z * FIFTH_DISTANCES[i]);
                    PuppetVfx.send(level, PuppetVfx.TELEGRAPH_RING, p, rayYaw, (float) FIFTH_RADIUS, FIFTH_FIRST_HIT - FIFTH_AIM + i * 2);
                }
            }
        }
        if (t == FIFTH_FIRST_HIT - 1) {
            this.struck.clear();
        }
        for (int i = 0; i < FIFTH_DISTANCES.length; i++) {
            if (t == FIFTH_FIRST_HIT + i * 2) {
                if (i == 0) {
                    this.sound(level, "item.trident.hit", SoundSource.NEUTRAL, 2.0f, 1.0f);
                }
                this.sound(level, "entity.blaze.shoot", SoundSource.HOSTILE, 1.4f, 0.8f + i * 0.08f);
                DamageSource source = this.sourcelessDamage(level);
                float baseYaw = yawOf(this.dirX, this.dirZ);
                for (float off : fanOffsets) {
                    Vec3 axis = this.crossAxis(off);
                    Vec3 p = new Vec3(this.originX + axis.x * FIFTH_DISTANCES[i], this.originY, this.originZ + axis.z * FIFTH_DISTANCES[i]);
                    level.sendParticles(MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get(), p.x, p.y, p.z, 16, 1.2, 0.5, 1.2, 0.01);
                    PuppetVfx.send(level, PuppetVfx.FLAME_PILLAR, p, baseYaw + off, (float) FIFTH_RADIUS, 0);
                    PuppetVfx.send(level, PuppetVfx.SCORCH, p, baseYaw + off, (float) FIFTH_RADIUS, 48);
                    this.hitAround(level, source, p, FIFTH_RADIUS, 0.95, 18.0, true);
                }
            }
        }
    }

    private void tickThird(ServerLevel level, int t) {
        if (t == THIRD_AIM) {
            this.lockDirection();
            this.originX = this.boss.getX();
            this.originY = this.boss.getY();
            this.originZ = this.boss.getZ();
            double reach = CRACK_DISTANCES[CRACK_DISTANCES.length - 1] + CRACK_RADIUS;
            PuppetVfx.send(level, PuppetVfx.TELEGRAPH_LINE, this.boss.position(), this.boss.getYRot(), (float) reach, PuppetVfx.line(THIRD_CHOP - THIRD_AIM + 6, CRACK_RADIUS));
        }
        if (t == THIRD_CHOP) {
            this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 0.8f);
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 1.2f, 1.2f);
            this.meleeHit(level, 1.0, 20.0);
            this.overheadVfx(level, this.boss.position(), this.boss.getYRot());
        }
        for (int i = 0; i < CRACK_DISTANCES.length; i++) {
            if (t == THIRD_CHOP + 2 + i * 2) {
                Vec3 p = new Vec3(this.originX + this.dirX * CRACK_DISTANCES[i], this.originY, this.originZ + this.dirZ * CRACK_DISTANCES[i]);
                this.sound(level, "entity.blaze.shoot", SoundSource.HOSTILE, 1.2f, 0.9f + i * 0.08f);
                level.sendParticles(MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get(), p.x, p.y, p.z, 10, 1.0, 0.4, 1.0, 0.01);
                PuppetVfx.send(level, PuppetVfx.FLAME_PILLAR, p, this.boss.getYRot(), (float) CRACK_RADIUS, 0);
                PuppetVfx.send(level, PuppetVfx.SCORCH, p, this.boss.getYRot(), (float) CRACK_RADIUS, 40);
                this.hitAround(level, this.sourcelessDamage(level), p, CRACK_RADIUS, 0.65, 10.0, true);
            }
        }
        if (t == THIRD_RISE - 11) {
            this.struck.clear();
            PuppetVfx.send(level, PuppetVfx.TELEGRAPH_RING, this.boss.position(), this.boss.getYRot(), (float) RISE_RADIUS, 11);
            PuppetVfx.send(level, PuppetVfx.CHARGE, this.boss.position(), this.boss.getYRot(), 2.0f, 11);
        }
        if (t == THIRD_RISE) {
            Vec3 c = this.boss.position();
            float yaw = this.boss.getYRot();
            this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.2f);
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 1.6f, 0.9f);
            level.sendParticles(MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get(), c.x, c.y + 0.5, c.z, 30, 2.5, 0.6, 2.5, 0.02);
            this.hitAround(level, this.sourcelessDamage(level), c, RISE_RADIUS, 1.0, 20.0, false);
            PuppetVfx.send(level, PuppetVfx.SLASH, c, yaw, (float) RISE_RADIUS, PuppetVfx.SLASH_RISING);
            PuppetVfx.send(level, PuppetVfx.EMBER_BURST, c, yaw, (float) RISE_RADIUS, 0);
            PuppetVfx.send(level, PuppetVfx.SHOCKWAVE, c, yaw, (float) RISE_RADIUS, 6);
            for (int i = 0; i < 4; i++) {
                this.spawnInfernoWave(level, yaw + i * 90.0f, 0.7, 10.0);
            }
        }
    }

    private void aimCross(ServerLevel level) {
        Vec3 dir = this.flatLook();
        LivingEntity target = this.boss.getTarget();
        Vec3 c = this.boss.position();
        double reach = 8.0;
        double y = c.y;
        if (target != null && target.isAlive()) {
            Vec3 d = new Vec3(target.getX() - c.x, 0.0, target.getZ() - c.z);
            if (d.lengthSqr() > 1.0E-4) {
                dir = d.normalize();
                reach = Math.min(CROSS_MAX_REACH, d.length());
                y = target.getY();
            }
        }
        this.dirX = dir.x;
        this.dirZ = dir.z;
        float yaw = yawOf(dir.x, dir.z);
        this.faceYaw(yaw);
        this.crossX = c.x + dir.x * reach;
        this.crossY = y;
        this.crossZ = c.z + dir.z * reach;
        this.stampCrossTelegraph(level, new float[]{45.0f, -45.0f}, CROSS_HIT + 4);
        PuppetVfx.send(level, PuppetVfx.CHARGE, c, yaw, 2.0f, CROSS_HIT);
        this.sound(level, "entity.blaze.ambient", SoundSource.HOSTILE, 1.6f, 0.9f);
    }

    private void stampCrossTelegraph(ServerLevel level, float[] offsets, int ticks) {
        int extra = PuppetVfx.line(ticks, CROSS_RADIUS);
        for (float off : offsets) {
            Vec3 axis = this.crossAxis(off);
            Vec3 from = new Vec3(this.crossX - axis.x * CROSS_ARM, this.crossY, this.crossZ - axis.z * CROSS_ARM);
            PuppetVfx.send(level, PuppetVfx.TELEGRAPH_LINE, from, yawOf(axis.x, axis.z), (float) (CROSS_ARM * 2.0), extra);
        }
    }

    private Vec3 crossAxis(float offset) {
        float yaw = yawOf(this.dirX, this.dirZ) + offset;
        float rad = yaw * Mth.DEG_TO_RAD;
        return new Vec3(-Mth.sin(rad), 0.0, Mth.cos(rad));
    }

    private void tickCross(ServerLevel level, int t) {
        if (t == CROSS_HIT) {
            this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 1.2f, 1.0f);
            this.meleeHit(level, 1.0, 20.0);
            this.overheadVfx(level, this.boss.position(), this.boss.getYRot());
            this.struck.clear();
        }
        int xRing = t - CROSS_HIT;
        if (xRing == 0 || xRing == 2 || xRing == 4) {
            this.eruptCrossRing(level, xRing, new float[]{45.0f, -45.0f});
        }
        if (this.phase2) {
            if (t == CROSS_HIT + 1) {
                this.stampCrossTelegraph(level, new float[]{0.0f, 90.0f}, CROSS_PLUS_HIT - t + 4);
                this.sound(level, "entity.blaze.ambient", SoundSource.HOSTILE, 1.4f, 1.2f);
            }
            if (t == CROSS_PLUS_HIT) {
                this.struck.clear();
            }
            int plusRing = t - CROSS_PLUS_HIT;
            if (plusRing == 0 || plusRing == 2 || plusRing == 4) {
                this.eruptCrossRing(level, plusRing, new float[]{0.0f, 90.0f});
            }
        }
    }

    private void eruptCrossRing(ServerLevel level, int ring, float[] offsets) {
        double offset = CROSS_ARM * ring / 4.0;
        DamageSource source = this.sourcelessDamage(level);
        if (ring == 0) {
            this.crossPillar(level, source, new Vec3(this.crossX, this.crossY, this.crossZ));
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 1.4f, 1.1f);
            return;
        }
        this.sound(level, "entity.blaze.shoot", SoundSource.HOSTILE, 1.4f, 0.8f + ring * 0.05f);
        for (float off : offsets) {
            Vec3 axis = this.crossAxis(off);
            for (int sign = -1; sign <= 1; sign += 2) {
                this.crossPillar(level, source, new Vec3(this.crossX + axis.x * offset * sign, this.crossY, this.crossZ + axis.z * offset * sign));
            }
        }
    }

    private void crossPillar(ServerLevel level, DamageSource source, Vec3 p) {
        level.sendParticles(MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get(), p.x, p.y, p.z, 8, 0.8, 0.4, 0.8, 0.01);
        PuppetVfx.send(level, PuppetVfx.FLAME_PILLAR, p, 0.0f, (float) CROSS_RADIUS, 0);
        PuppetVfx.send(level, PuppetVfx.SCORCH, p, 0.0f, (float) CROSS_RADIUS, 40);
        this.hitAround(level, source, p, CROSS_RADIUS, 0.75, 10.0, true);
    }

    private void spawnInfernoWaveFan(ServerLevel level, float baseYaw, int count, float spreadDeg, double multiplier, double rotting) {
        this.sound(level, "entity.blaze.shoot", SoundSource.HOSTILE, 1.3f, 1.15f);
        if (count <= 1) {
            this.spawnInfernoWave(level, baseYaw, multiplier, rotting);
            return;
        }
        float start = -spreadDeg * (count - 1) * 0.5f;
        for (int i = 0; i < count; i++) {
            this.spawnInfernoWave(level, baseYaw + start + spreadDeg * i, multiplier, rotting);
        }
    }

    private void spawnInfernoWave(ServerLevel level, float yawDeg, double multiplier, double rotting) {
        float rad = yawDeg * Mth.DEG_TO_RAD;
        double dx = -Mth.sin(rad);
        double dz = Mth.cos(rad);
        Vec3 origin = this.boss.position().add(dx * 1.6, 0.0, dz * 1.6);
        this.waves.add(new InfernoWave(origin, dx, dz, INFERNO_WAVE_RADIUS, multiplier, rotting, INFERNO_WAVE_TICKS));
        PuppetVfx.send(level, PuppetVfx.INFERNO_WAVE, origin, yawDeg, (float) INFERNO_WAVE_RADIUS, INFERNO_WAVE_TICKS);
    }

    private void tickWaves(ServerLevel level) {
        if (this.waves.isEmpty()) {
            return;
        }
        DamageSource source = this.sourcelessDamage(level);
        Iterator<InfernoWave> it = this.waves.iterator();
        while (it.hasNext()) {
            InfernoWave w = it.next();
            w.x += w.dx * INFERNO_WAVE_SPEED;
            w.z += w.dz * INFERNO_WAVE_SPEED;
            w.ticksLeft--;
            float damage = BossAttributes.skillDamage(this.boss, w.multiplier);
            AABB box = new AABB(w.x - w.radius, w.y - 1.0, w.z - w.radius, w.x + w.radius, w.y + 3.0, w.z + w.radius);
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, PuppetCombat::canBeHit)) {
                if (horizontalEdgeDistance(e, w.x, w.z) <= w.radius && w.hit.add(e.getId())) {
                    e.hurtOrSimulate(source, damage);
                    e.knockback(0.35, -w.dx, -w.dz);
                    e.hurtMarked = true;
                    if (w.rotting > 0.0 && e instanceof Player player) {
                        MokelsBossfightSaphyraModVariables.PlayerVariables vars = MokelsBossfightSaphyraModVariables.getPlayerVariables(player);
                        vars.Rotting += w.rotting;
                        vars.syncPlayerVariables(player);
                    }
                }
            }
            if (w.ticksLeft <= 0) {
                it.remove();
            }
        }
    }

    private void lockDirection() {
        Vec3 dir = this.flatLook();
        this.dirX = dir.x;
        this.dirZ = dir.z;
        this.faceYaw(yawOf(dir.x, dir.z));
    }

    private Vec3 front(double distance) {
        Vec3 look = this.flatLook();
        return this.boss.position().add(look.scale(distance));
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

    private DamageSource sourcelessDamage(ServerLevel level) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).get(DamageTypes.MOB_ATTACK).orElseThrow());
    }

    private void meleeHit(ServerLevel level, double multiplier, double rotting) {
        this.hitAround(level, this.sourcelessDamage(level), this.front(MELEE_OFFSET), MELEE_RADIUS, multiplier, rotting, false);
    }

    private void hitAround(ServerLevel level, DamageSource source, Vec3 center, double radius, double multiplier, double rotting, boolean once) {
        this.hitAround(level, source, center, radius, multiplier, rotting, once, 1.0);
    }

    private void hitAround(ServerLevel level, DamageSource source, Vec3 center, double radius, double multiplier, double rotting, boolean once, double depth) {
        float damage = BossAttributes.skillDamage(this.boss, multiplier);
        double height = Math.max(3.0, radius * 0.4);
        AABB box = new AABB(center.x - radius, center.y - depth, center.z - radius, center.x + radius, center.y + height, center.z + radius);
        String tag = this.skill == null ? "NONE" : this.skill.name();
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, PuppetCombat::canBeHit)) {
            if (horizontalEdgeDistance(e, center.x, center.z) > radius) {
                continue;
            }
            if (once && !this.struck.add(e.getId())) {
                continue;
            }
            if (e instanceof Player) {
                MokelsBossfightSaphyraMod.LOGGER.info("[PuppetHitDebug] skill={} tick={} radius={} dist={} damage={}",
                        tag, this.skillTick, radius, String.format("%.2f", Math.sqrt(e.distanceToSqr(center.x, e.getY(), center.z))), damage);
            }
            e.hurtOrSimulate(source, damage);
            if (rotting > 0.0 && e instanceof Player player) {
                MokelsBossfightSaphyraModVariables.PlayerVariables vars = MokelsBossfightSaphyraModVariables.getPlayerVariables(player);
                vars.Rotting += rotting;
                vars.syncPlayerVariables(player);
            }
        }
    }

    private static double horizontalEdgeDistance(LivingEntity e, double x, double z) {
        AABB bb = e.getBoundingBox();
        double nx = Mth.clamp(x, bb.minX, bb.maxX) - x;
        double nz = Mth.clamp(z, bb.minZ, bb.maxZ) - z;
        return Math.sqrt(nx * nx + nz * nz);
    }

    public static boolean canBeHit(LivingEntity e) {
        return !(e instanceof PuppetOfYivaNemorEntity) && e.isAlive() && !e.isSpectator()
                && !e.hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE);
    }

    private void tickMusic(ServerLevel level, LivingEntity target) {
        CompoundTag data = this.boss.getPersistentData();
        double music = data.getDoubleOr("music", 0.0);
        if (target != null) {
            music += 1.0;
            data.putDouble("music", music);
        }
        for (int i = 0; i < THEME_TICKS.length; i++) {
            if (music == THEME_TICKS[i]) {
                BossMusic.stop(level, this.boss.getX(), this.boss.getY(), this.boss.getZ(), null);
                this.sound(level, "saphyra_reborn:namelesstheme" + (i + 1), SoundSource.MUSIC, 2.0f, 1.0f);
            }
        }
        if (music >= MUSIC_RESET) {
            data.putDouble("music", 0.0);
        }
    }

    private void sound(ServerLevel level, String id, SoundSource source, float volume, float pitch) {
        SoundEvent event = BuiltInRegistries.SOUND_EVENT.get(Identifier.parse(id)).map(net.minecraft.core.Holder::value).orElse(null);
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
        this.timeline = tag.getIntOr("Timeline", 0);
        this.pendingPoint = tag.contains("Pending") ? tag.getIntOr("Pending", 0) : -1;
        this.phase2 = tag.getBooleanOr("Phase2", false);
        this.phaseShiftTicks = tag.getIntOr("PhaseShift", 0);
        this.skill = null;
        this.skillTick = 0;
        this.chainSkill = null;
        this.chainLeft = 0;
        this.waves.clear();
        this.boss.setPhase2Visual(this.phase2 && this.phaseShiftTicks <= PHASE_SHIFT_TICKS - PHASE_BURST_TICK);
    }
}
