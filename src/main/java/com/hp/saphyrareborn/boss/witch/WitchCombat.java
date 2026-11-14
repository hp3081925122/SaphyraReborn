package com.hp.saphyrareborn.boss.witch;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.BossAttributes;
import com.hp.saphyrareborn.boss.BossCastLock;
import com.hp.saphyrareborn.boss.BossConfig;
import com.hp.saphyrareborn.boss.BossMusic;
import com.hp.saphyrareborn.entity.MoonbeamEntity;
import com.hp.saphyrareborn.entity.WitchbossEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class WitchCombat {
    private static final int[] POINTS = {20, 80, 200};
    private static final int RESET = 340;
    private static final int[] THEME_TICKS = {20, 320, 620, 920, 1220, 1520};
    private static final int MUSIC_RESET = 1780;
    private static final int PHASE_SHIFT_TICKS = 60;
    private static final int PHASE_BURST_TICK = 30;
    private static final int DASH_NONE = 0;
    private static final int DASH_FORTH = 1;
    private static final int DASH_BACK = 2;
    private static final int RAIN_DELAY = 5;
    private static final int WAND_PERIOD = 16;
    private static final int WAND_AIM = 10;
    private static final int WAND_FIRE = 13;
    private static final double LANCE_LENGTH = 22.0;
    private static final double LANCE_HALF_WIDTH = 1.05;
    private static final int JUMP_LEAP = 18;
    private static final int JUMP_PLANT = 36;
    private static final int JUMP_SLAM = 46;
    private static final int SPIN_START = 25;
    private static final int SPIN_END = 80;
    private static final double CRESCENT_SPEED = 1.2;
    private static final int CRESCENT_LIFE = 16;

    private final WitchbossEntity boss;
    private final List<LunarCrescent> crescents = new ArrayList<>();
    private int clock;
    private int timeline;
    private WitchSkill skill;
    private int skillTick;
    private int pendingPoint = -1;
    private boolean phase2;
    private int phaseShiftTicks;
    private int dashDelay = -1;
    private double dirX;
    private double dirZ;
    private double anchorX;
    private double anchorY;
    private double anchorZ;
    private float anchorYaw;

    public WitchCombat(WitchbossEntity boss) {
        this.boss = boss;
    }

    public boolean isInvulnerable() {
        return this.phaseShiftTicks > 0;
    }

    public void resetFight() {
        this.cancelSkill();
        this.crescents.clear();
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
        this.tickCrescents(level);
        LivingEntity target = this.boss.getTarget();
        this.tickDash(level, target);
        this.tickMusic(level, target);
        this.faceTarget(target);
        this.debugLog(target);
        if (!this.phase2 && BossConfig.WITCH_PHASE2_ENABLED.get()
                && this.boss.getHealth() <= this.boss.getMaxHealth() * BossConfig.WITCH_PHASE2_RATIO.get()) {
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
            this.tickSkill(level, target);
        } else if (this.pendingPoint >= 0 && !stunned && target != null) {
            int point = this.pendingPoint;
            this.pendingPoint = -1;
            this.firePoint(level, point);
        }
    }

    private float turnRate() {
        if (this.phaseShiftTicks > 0 || this.skill == WitchSkill.WAND) {
            return 0.0f;
        }
        if (this.skill == WitchSkill.JUMP && this.skillTick >= JUMP_PLANT) {
            return 0.0f;
        }
        if (this.skill == WitchSkill.GROUND && this.skillTick >= 20) {
            return 0.0f;
        }
        return this.skill == null ? 30.0f : 12.0f;
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
        double scale = this.boss.onGround() ? 1.0 : 0.35;
        Vec3 look = this.flatLook();
        Vec3 cur = this.boss.getDeltaMovement();
        this.boss.setDeltaMovement(cur.x * 0.25 + look.x * speed * scale, cur.y, cur.z * 0.25 + look.z * speed * scale);
        this.boss.hurtMarked = true;
    }

    private void lockAnchor(Vec3 pos, float yaw) {
        this.anchorX = pos.x;
        this.anchorY = pos.y;
        this.anchorZ = pos.z;
        this.anchorYaw = yaw;
    }

    private Vec3 anchorPos() {
        if (this.anchorX == 0.0 && this.anchorY == 0.0 && this.anchorZ == 0.0) {
            return this.boss.position();
        }
        return new Vec3(this.anchorX, this.anchorY, this.anchorZ);
    }

    private void fireCrescentFan(ServerLevel level, Vec3 origin, float baseYaw, int count, double spreadDeg) {
        this.sound(level, "block.amethyst_block.chime", SoundSource.HOSTILE, 1.6f, 1.25f);
        for (int i = 0; i < count; i++) {
            double offset = count == 1 ? 0.0 : (i - (count - 1) * 0.5) * spreadDeg;
            float yaw = (float) (baseYaw + offset);
            double rad = Math.toRadians(yaw);
            double fx = -Math.sin(rad);
            double fz = Math.cos(rad);
            Vec3 spawn = new Vec3(origin.x + fx * 1.2, origin.y, origin.z + fz * 1.2);
            this.crescents.add(new LunarCrescent(spawn.x, spawn.y, spawn.z, fx * CRESCENT_SPEED, fz * CRESCENT_SPEED, CRESCENT_LIFE));
            WitchVfx.send(level, WitchVfx.CRESCENT, spawn, yaw, 2.0f, CRESCENT_LIFE);
        }
    }

    private void fireRadialCrescents(ServerLevel level, Vec3 origin, float startYaw, int count) {
        this.sound(level, "block.amethyst_block.chime", SoundSource.HOSTILE, 1.8f, 1.1f);
        for (int i = 0; i < count; i++) {
            float yaw = startYaw + i * (360.0f / count);
            double rad = Math.toRadians(yaw);
            double fx = -Math.sin(rad);
            double fz = Math.cos(rad);
            Vec3 spawn = new Vec3(origin.x + fx * 1.3, origin.y, origin.z + fz * 1.3);
            this.crescents.add(new LunarCrescent(spawn.x, spawn.y, spawn.z, fx * CRESCENT_SPEED, fz * CRESCENT_SPEED, CRESCENT_LIFE));
            WitchVfx.send(level, WitchVfx.CRESCENT, spawn, yaw, 2.0f, CRESCENT_LIFE);
        }
    }

    private void tickCrescents(ServerLevel level) {
        if (this.crescents.isEmpty()) {
            return;
        }
        DamageSource source = this.attackSource();
        float damage = BossAttributes.skillDamage(this.boss, 0.7);
        Iterator<LunarCrescent> it = this.crescents.iterator();
        while (it.hasNext()) {
            LunarCrescent c = it.next();
            c.x += c.vx;
            c.z += c.vz;
            c.age++;
            if (c.age % 2 == 0) {
                level.sendParticles(MokelsBossfightSaphyraModParticleTypes.GHOSTLY_FLAME_PARTICLE.get(), c.x, c.y + 1.1, c.z, 2, 0.3, 0.3, 0.3, 0.01);
            }
            AABB box = new AABB(c.x - 1.9, c.y - 0.8, c.z - 1.9, c.x + 1.9, c.y + 2.5, c.z + 1.9);
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, WitchCombat::canBeHit)) {
                if (!c.hitIds.add(e.getId())) {
                    continue;
                }
                if (horizontalEdgeDistance(e, c.x, c.z) > 1.9) {
                    c.hitIds.remove(e.getId());
                    continue;
                }
                this.damage(e, source, damage, 12.0, "CRESCENT", 1.9);
            }
            if (c.age >= c.maxAge) {
                it.remove();
            }
        }
    }

    private void debugLog(LivingEntity target) {
        if (target == null || this.boss.tickCount % 100 != 0) {
            return;
        }
        MokelsBossfightSaphyraMod.LOGGER.info("[WitchDebug] id={} onGround={} yRot={} skill={} skillTick={} timeline={} phase2={} health={}/{}",
                this.boss.getId(), this.boss.onGround(), String.format("%.1f", Mth.wrapDegrees(this.boss.getYRot())), this.skill, this.skillTick,
                this.timeline, this.phase2, this.boss.getHealth(), this.boss.getMaxHealth());
    }

    private void startPhaseShift(ServerLevel level) {
        this.phase2 = true;
        this.cancelSkill();
        this.pendingPoint = -1;
        this.timeline = 0;
        this.phaseShiftTicks = PHASE_SHIFT_TICKS;
        BossCastLock.lock(this.boss, PHASE_SHIFT_TICKS);
        this.boss.restartAnimation("phaseshift");
        this.sound(level, "entity.evoker.prepare_summon", SoundSource.HOSTILE, 2.0f, 0.6f);
        WitchVfx.send(level, WitchVfx.PHASE_SHIFT, this.boss.position(), this.boss.getYRot(), 6.0f, PHASE_SHIFT_TICKS);
    }

    private void tickPhaseShift(ServerLevel level) {
        int age = PHASE_SHIFT_TICKS - this.phaseShiftTicks;
        if (age == PHASE_BURST_TICK) {
            this.boss.setPhase2Visual(true);
            this.sound(level, "entity.illusioner.mirror_move", SoundSource.HOSTILE, 2.0f, 0.6f);
            this.sound(level, "entity.generic.explode", SoundSource.HOSTILE, 1.6f, 0.6f);
            Vec3 c = this.boss.position();
            level.sendParticles(MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get(), c.x, c.y + 1.5, c.z, 40, 2.0, 1.5, 2.0, 0.05);
            WitchVfx.send(level, WitchVfx.FLAME_BURST, c, 0.0f, 6.0f, 0);
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, this.boss.getBoundingBox().inflate(6.0), e -> e != this.boss && !(e instanceof WitchbossEntity) && !(e instanceof MoonbeamEntity))) {
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
        BossCastLock.release(this.boss);
    }

    private int scaled(int tick) {
        return this.phase2 ? Math.max(1, Mth.floor(tick * BossConfig.WITCH_PHASE2_CADENCE.get() + 0.5)) : tick;
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
        if (this.phase2 && this.boss.getRandom().nextDouble() < BossConfig.WITCH_PHASE2_NEW_SKILL_CHANCE.get()) {
            switch (point) {
                case 0 -> this.start(level, WitchSkill.WAND, DASH_NONE);
                case 1 -> this.start(level, WitchSkill.JUMP, DASH_NONE);
                default -> this.start(level, WitchSkill.SPIN, DASH_NONE);
            }
            return;
        }
        switch (point) {
            case 0 -> {
                if (first) {
                    this.start(level, WitchSkill.SLASH, near ? DASH_NONE : DASH_FORTH);
                } else {
                    this.start(level, WitchSkill.CLAW, near ? DASH_NONE : DASH_FORTH);
                }
            }
            case 1 -> {
                if (first) {
                    this.start(level, WitchSkill.CLAW2, near ? DASH_NONE : DASH_FORTH);
                } else {
                    this.start(level, WitchSkill.RAIN, DASH_BACK);
                }
            }
            default -> {
                if (first) {
                    this.start(level, WitchSkill.GROUND, near ? DASH_BACK : DASH_NONE);
                } else {
                    this.start(level, WitchSkill.AXE, near ? DASH_NONE : DASH_FORTH);
                }
            }
        }
    }

    private void start(ServerLevel level, WitchSkill next, int dash) {
        this.skill = next;
        this.skillTick = 0;
        if (next.animation != null) {
            this.boss.restartAnimation(next.animation);
        }
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
            case CLAW2 -> WitchVfx.send(level, WitchVfx.CHARGE, c, yaw, 2.0f, 21);
            case GROUND -> WitchVfx.send(level, WitchVfx.CHARGE, c, yaw, 4.0f, 20);
            case AXE -> WitchVfx.send(level, WitchVfx.CHARGE, c, yaw, 2.5f, 26);
            case WAND -> WitchVfx.send(level, WitchVfx.CHARGE, c, yaw, 1.8f, WAND_FIRE);
            case JUMP -> {
                this.boss.addEffect(new MobEffectInstance(MobEffects.SPEED, 60, 1, false, false));
                this.sound(level, "entity.evoker.prepare_attack", SoundSource.HOSTILE, 1.6f, 1.2f);
            }
            case SPIN -> WitchVfx.send(level, WitchVfx.CHARGE, c, yaw, 3.0f, SPIN_START);
            default -> {
            }
        }
    }

    private void dashForth(ServerLevel level) {
        level.sendParticles(ParticleTypes.POOF, this.boss.getX(), this.boss.getY(), this.boss.getZ(), 8, 0.2, 0.2, 0.2, 0.05);
        this.boss.setDeltaMovement(new Vec3(0.0, 1.0, 0.0));
        this.boss.hurtMarked = true;
        this.dashDelay = 2;
        WitchVfx.send(level, WitchVfx.SHOCKWAVE, this.boss.position(), 0.0f, 2.0f, WitchVfx.ring(4, 0.0));
    }

    private void dashBack(ServerLevel level) {
        Vec3 look = this.boss.getLookAngle();
        level.sendParticles(ParticleTypes.POOF, this.boss.getX(), this.boss.getY(), this.boss.getZ(), 8, 0.2, 0.2, 0.2, 0.05);
        this.boss.setDeltaMovement(new Vec3(-3.0 * look.x, 0.15, -3.0 * look.z));
        this.boss.hurtMarked = true;
        WitchVfx.send(level, WitchVfx.DASH_TRAIL, this.boss.position(), this.boss.getYRot() + 180.0f, 6.0f, 0);
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
        WitchVfx.send(level, WitchVfx.DASH_TRAIL, this.boss.position(), yawOf(dx, dz), (float) Math.min(16.0, Math.sqrt(dx * dx + dz * dz)), 0);
    }

    private void tickSkill(ServerLevel level, LivingEntity target) {
        this.skillTick++;
        int t = this.skillTick;
        Vec3 c = this.boss.position();
        float yaw = this.boss.getYRot();
        switch (this.skill) {
            case SLASH -> {
                if (t == 10) {
                    this.stepIn(0.6);
                }
                if (t == 15) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, this.attackSource(), c, 4.5, 1.0, 10.0);
                    WitchVfx.send(level, WitchVfx.SLASH, c, yaw, 4.5f, WitchVfx.SLASH_RIGHT);
                    if (this.phase2) {
                        this.fireCrescentFan(level, c, yaw, 1, 0.0);
                    }
                }
            }
            case CLAW -> {
                if (t == 9) {
                    this.sound(level, "entity.phantom.flap", SoundSource.HOSTILE, 1.2f, 1.4f);
                    WitchVfx.send(level, WitchVfx.CLAW, c, yaw, 3.2f, 0);
                }
                if (t == 14) {
                    this.stepIn(0.65);
                }
                if (t == 18) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, this.attackSource(), c, 5.2, 1.0, 20.0);
                    WitchVfx.send(level, WitchVfx.CLAW, c, yaw, 5.2f, 1);
                }
            }
            case CLAW2 -> {
                if (t == 11) {
                    WitchVfx.send(level, WitchVfx.CLAW, c, yaw, 3.6f, 0);
                }
                if (t == 17) {
                    this.stepIn(0.65);
                }
                if (t == 21) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, this.attackSource(), c, 6.0, 1.0, 20.0);
                    WitchVfx.send(level, WitchVfx.CLAW, c, yaw, 6.0f, 1);
                }
                if (t == 45) {
                    WitchVfx.send(level, WitchVfx.CLAW, c, yaw, 4.2f, 0);
                    WitchVfx.send(level, WitchVfx.TELEGRAPH_RING, c, yaw, 7.0f, WitchVfx.ring(8, 0.0));
                }
                if (t == 53) {
                    this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    level.sendParticles(MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get(), c.x, c.y, c.z, 60, 3.0, 0.5, 3.0, 0.01);
                    this.hitAround(level, this.attackSource(), c, 7.0, 1.05, 20.0);
                    WitchVfx.send(level, WitchVfx.SHOCKWAVE, c, yaw, 7.0f, WitchVfx.ring(6, 0.0));
                    WitchVfx.send(level, WitchVfx.FLAME_BURST, c, yaw, 4.2f, 0);
                    if (this.phase2) {
                        this.fireCrescentFan(level, c, yaw, 3, 22.0);
                    }
                }
            }
            case RAIN -> this.tickRain(level, t);
            case GROUND -> this.tickGround(level, t);
            case AXE -> {
                if (t == 22 || t == 35 || t == 47) {
                    this.stepIn(0.55);
                }
                if (t == 26 || t == 39 || t == 51) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, t == 39 ? 1.15f : 1.0f);
                    this.hitAround(level, this.attackSource(), c, 7.0, 1.0, 20.0);
                    WitchVfx.send(level, WitchVfx.SLASH, c, yaw, 7.0f, t == 39 ? WitchVfx.SLASH_RIGHT : WitchVfx.SLASH_LEFT);
                    if (t == 51) {
                        this.fireCrescentFan(level, c, yaw, 1, 0.0);
                    }
                }
                if (t == 73) {
                    WitchVfx.send(level, WitchVfx.TELEGRAPH_RING, c, yaw, 7.5f, WitchVfx.ring(8, 0.0));
                }
                if (t == 81) {
                    this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, this.attackSource(), c, 7.5, 1.1, 20.0);
                    WitchVfx.send(level, WitchVfx.SLASH, c, yaw, 7.5f, WitchVfx.SLASH_OVERHEAD);
                    WitchVfx.send(level, WitchVfx.SHOCKWAVE, c, yaw, 7.5f, WitchVfx.ring(6, 0.0));
                    WitchVfx.send(level, WitchVfx.FLAME_BURST, c, yaw, 5.2f, 0);
                    this.fireCrescentFan(level, c, yaw, this.phase2 ? 5 : 3, this.phase2 ? 18.0 : 24.0);
                }
            }
            case WAND -> this.tickWand(level, t, target);
            case JUMP -> this.tickJump(level, t, target);
            case SPIN -> this.tickSpin(level, t, target);
            default -> {
            }
        }
        if (this.skill != null && this.skillTick >= this.skill.duration) {
            this.finish();
        }
    }

    private void finish() {
        this.skill = null;
        this.skillTick = 0;
    }

    private void tickRain(ServerLevel level, int t) {
        if (t == RAIN_DELAY) {
            this.boss.restartAnimation("rainattack");
            BossCastLock.lock(this.boss, 50);
            this.sound(level, "entity.evoker.prepare_summon", SoundSource.HOSTILE, 1.6f, 1.3f);
            WitchVfx.send(level, WitchVfx.MOON_SIGIL, this.boss.position().add(0.0, 0.08, 0.0), this.boss.getYRot(), 3.6f, 42);
        }
        if (t == 5) {
            this.summonMoonbeamAt(level, -55.0);
        } else if (t == 12 && this.phase2) {
            this.summonMoonbeamAt(level, -110.0);
        } else if (t == 20) {
            this.summonMoonbeamAt(level, 0.0);
        } else if (t == 28 && this.phase2) {
            this.summonMoonbeamAt(level, 110.0);
        } else if (t == 35) {
            this.summonMoonbeamAt(level, 55.0);
        }
    }

    private void summonMoonbeamAt(ServerLevel level, double relAngleDeg) {
        double rad = Math.toRadians(this.boss.getYRot() + relAngleDeg);
        double sx = this.boss.getX() - Math.sin(rad) * 3.5;
        double sy = this.boss.getY() + 3.8;
        double sz = this.boss.getZ() + Math.cos(rad) * 3.5;
        BlockPos pos = BlockPos.containing(sx, sy, sz);
        MoonbeamEntity beam = MokelsBossfightSaphyraModEntities.MOONBEAM.get().spawn(level, pos, EntitySpawnReason.MOB_SUMMONED);
        if (beam != null) {
            beam.setPos(sx, sy, sz);
            beam.setDeltaMovement(0.0, 0.0, 0.0);
            AttributeInstance attack = beam.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attack != null) {
                attack.setBaseValue(BossAttributes.skillDamage(this.boss, 1.0));
            }
        }
        this.sound(level, "block.amethyst_block.resonate", SoundSource.HOSTILE, 1.6f, 0.85f);
        WitchVfx.send(level, WitchVfx.MOON_SIGIL, new Vec3(sx, sy, sz), this.boss.getYRot(), 2.0f, 24);
    }

    private void tickGround(ServerLevel level, int t) {
        if (t == 20) {
            this.lockAnchor(this.boss.position(), this.boss.getYRot());
            Vec3 c = this.anchorPos();
            WitchVfx.send(level, WitchVfx.TELEGRAPH_RING, c, this.anchorYaw, 8.0f, WitchVfx.ring(15, 0.0));
            WitchVfx.send(level, WitchVfx.MOON_SIGIL, c.add(0.0, 0.08, 0.0), this.anchorYaw, 7.6f, 15);
        }
        if (t == 35) {
            Vec3 c = this.anchorPos();
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 1.05f);
            level.sendParticles(MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get(), c.x, c.y, c.z, 50, 4.0, 0.5, 4.0, 0.01);
            this.hitRing(level, this.sourcelessDamage(level), c, 0.0, 8.0, 1.0, 30.0, null);
            WitchVfx.send(level, WitchVfx.SHOCKWAVE, c, this.anchorYaw, 8.0f, WitchVfx.ring(8, 0.0));
            WitchVfx.send(level, WitchVfx.FLAME_BURST, c, this.anchorYaw, 6.5f, 0);
        }
        if (t == 40) {
            Vec3 c = this.anchorPos();
            this.sound(level, "block.amethyst_block.chime", SoundSource.HOSTILE, 2.0f, 0.7f);
            WitchVfx.send(level, WitchVfx.TELEGRAPH_RING, c, this.anchorYaw, 17.0f, WitchVfx.ring(18, 7.5));
        }
        if (t == 58) {
            Vec3 c = this.anchorPos();
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 0.9f);
            level.sendParticles(MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get(), c.x, c.y, c.z, 80, 9.0, 0.5, 9.0, 0.01);
            this.hitRing(level, this.sourcelessDamage(level), c, 7.5, 17.0, 1.1, 30.0, null);
            WitchVfx.send(level, WitchVfx.SHOCKWAVE, c, this.anchorYaw, 17.0f, WitchVfx.ring(10, 7.5));
            for (int i = 0; i < 8; i++) {
                double a = Math.toRadians(this.anchorYaw) + Math.PI * 2.0 * i / 8.0;
                Vec3 p = new Vec3(c.x + Math.cos(a) * 12.2, c.y, c.z + Math.sin(a) * 12.2);
                WitchVfx.send(level, WitchVfx.FLAME_BURST, p, 0.0f, 3.2f, 0);
            }
        }
        if (t == 76) {
            Vec3 c = this.anchorPos();
            this.sound(level, "entity.evoker.prepare_attack", SoundSource.HOSTILE, 2.0f, 0.75f);
            WitchVfx.send(level, WitchVfx.TELEGRAPH_RING, c, this.anchorYaw, 8.0f, WitchVfx.ring(19, 0.0));
            for (int k = 0; k < 8; k++) {
                WitchVfx.send(level, WitchVfx.TELEGRAPH_LINE, c, this.anchorYaw + k * 45.0f, 20.0f, 19);
            }
        }
        if (t == 95) {
            Vec3 c = this.anchorPos();
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 0.75f);
            this.sound(level, "entity.evoker.cast_spell", SoundSource.HOSTILE, 2.0f, 0.9f);
            level.sendParticles(MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get(), c.x, c.y, c.z, 90, 6.0, 0.6, 6.0, 0.02);
            Set<Integer> seen = new HashSet<>();
            DamageSource src = this.sourcelessDamage(level);
            this.hitRing(level, src, c, 0.0, 8.0, 1.4, 40.0, seen);
            WitchVfx.send(level, WitchVfx.SLASH, c, this.anchorYaw, 8.0f, WitchVfx.SLASH_SPIN);
            WitchVfx.send(level, WitchVfx.SHOCKWAVE, c, this.anchorYaw, 8.0f, WitchVfx.ring(9, 0.0));
            WitchVfx.send(level, WitchVfx.FLAME_BURST, c, this.anchorYaw, 8.0f, 0);
            for (int k = 0; k < 8; k++) {
                float rayYaw = this.anchorYaw + k * 45.0f;
                double rad = Math.toRadians(rayYaw);
                this.fireLanceRay(level, c, -Math.sin(rad), Math.cos(rad), rayYaw, 20.0, 0.95, 25.0, src, seen);
            }
        }
    }

    private void tickWand(ServerLevel level, int t, LivingEntity target) {
        int shot = (t - 1) / WAND_PERIOD;
        int local = (t - 1) % WAND_PERIOD + 1;
        if (shot >= 3) {
            return;
        }
        boolean fanShot = shot == 2;
        if (local == 1) {
            Vec3 dir = this.flatLook();
            if (target != null && target.isAlive()) {
                Vec3 d = new Vec3(target.getX() - this.boss.getX(), 0.0, target.getZ() - this.boss.getZ());
                if (d.lengthSqr() > 1.0E-4) {
                    dir = d.normalize();
                }
            }
            this.dirX = dir.x;
            this.dirZ = dir.z;
            float yaw = yawOf(dir.x, dir.z);
            this.faceYaw(yaw);
            this.sound(level, "block.amethyst_block.chime", SoundSource.HOSTILE, 2.0f, fanShot ? 0.95f : 0.7f);
            if (fanShot) {
                for (int i = -1; i <= 1; i++) {
                    WitchVfx.send(level, WitchVfx.TELEGRAPH_LINE, this.boss.position(), yaw + i * 20.0f, (float) LANCE_LENGTH, WAND_FIRE - 1);
                }
            } else {
                WitchVfx.send(level, WitchVfx.TELEGRAPH_LINE, this.boss.position(), yaw, (float) LANCE_LENGTH, WAND_FIRE - 1);
            }
        }
        if (local == WAND_AIM) {
            this.boss.restartAnimation("wandattack");
        }
        if (local == WAND_FIRE) {
            Vec3 c = this.boss.position();
            float baseYaw = yawOf(this.dirX, this.dirZ);
            this.sound(level, "entity.evoker.cast_spell", SoundSource.HOSTILE, 1.8f, 1.3f);
            this.sound(level, "block.amethyst_block.break", SoundSource.HOSTILE, 1.6f, 0.6f);
            Set<Integer> seen = new HashSet<>();
            DamageSource src = this.attackSource();
            if (fanShot) {
                for (int i = -1; i <= 1; i++) {
                    float rayYaw = baseYaw + i * 20.0f;
                    double rad = Math.toRadians(rayYaw);
                    this.fireLanceRay(level, c, -Math.sin(rad), Math.cos(rad), rayYaw, LANCE_LENGTH, 0.85, 12.0, src, seen);
                }
            } else {
                this.fireLanceRay(level, c, this.dirX, this.dirZ, baseYaw, LANCE_LENGTH, 0.85, 10.0, src, seen);
            }
        }
    }

    private void fireLanceRay(ServerLevel level, Vec3 c, double fx, double fz, float yaw, double length,
                              double multiplier, double grief, DamageSource source, Set<Integer> seen) {
        WitchVfx.send(level, WitchVfx.MOON_LANCE, c, yaw, (float) length, 0);
        double ex = c.x + fx * length;
        double ez = c.z + fz * length;
        AABB box = new AABB(Math.min(c.x, ex), c.y - 1.0, Math.min(c.z, ez), Math.max(c.x, ex), c.y + 3.5, Math.max(c.z, ez))
                .inflate(LANCE_HALF_WIDTH + 1.0, 0.0, LANCE_HALF_WIDTH + 1.0);
        float damage = BossAttributes.skillDamage(this.boss, multiplier);
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, WitchCombat::canBeHit)) {
            double rx = e.getX() - c.x;
            double rz = e.getZ() - c.z;
            double along = rx * fx + rz * fz;
            if (along < -1.0 || along > length) {
                continue;
            }
            double side = Math.abs(rx * fz - rz * fx) - e.getBbWidth() * 0.5;
            if (side <= LANCE_HALF_WIDTH && (seen == null || seen.add(e.getId()))) {
                this.damage(e, source, damage, grief, "MOON_LANCE", LANCE_HALF_WIDTH);
            }
        }
    }

    private void tickJump(ServerLevel level, int t, LivingEntity target) {
        if (t == JUMP_LEAP && target != null && target.isAlive()) {
            double dx = target.getX() - this.boss.getX();
            double dz = target.getZ() - this.boss.getZ();
            double dist = Math.hypot(dx, dz);
            if (dist > 1.0) {
                double speed = Math.min(1.25, dist * 0.14);
                this.boss.setDeltaMovement(dx / dist * speed, 0.45, dz / dist * speed);
                this.boss.hurtMarked = true;
                WitchVfx.send(level, WitchVfx.DASH_TRAIL, this.boss.position(), yawOf(dx, dz), (float) Math.min(10.0, dist), 0);
            }
        }
        if (t == JUMP_PLANT) {
            BossCastLock.lock(this.boss, WitchSkill.JUMP.duration - JUMP_PLANT);
            this.boss.setDeltaMovement(new Vec3(0.0, this.boss.getDeltaMovement().y, 0.0));
            this.boss.hurtMarked = true;
            Vec3 landing = this.boss.position().add(this.flatLook().scale(3.6));
            this.lockAnchor(landing, this.boss.getYRot());
            WitchVfx.send(level, WitchVfx.TELEGRAPH_RING, landing, this.anchorYaw, 6.0f, WitchVfx.ring(JUMP_SLAM - JUMP_PLANT, 0.0));
            WitchVfx.send(level, WitchVfx.MOON_SIGIL, landing.add(0.0, 0.08, 0.0), this.anchorYaw, 5.6f, JUMP_SLAM - JUMP_PLANT);
            WitchVfx.send(level, WitchVfx.CHARGE, this.boss.position(), this.anchorYaw, 2.0f, JUMP_SLAM - JUMP_PLANT);
        }
        if (t == JUMP_SLAM) {
            Vec3 center = this.anchorPos();
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 1.0f);
            level.sendParticles(ParticleTypes.EXPLOSION, center.x, center.y, center.z, 12, 2.6, 0.5, 2.6, 0.01);
            this.hitAround(level, this.sourcelessDamage(level), center, 6.0, 1.05, 20.0);
            WitchVfx.send(level, WitchVfx.SHOCKWAVE, center, this.anchorYaw, 6.0f, WitchVfx.ring(6, 0.0));
            WitchVfx.send(level, WitchVfx.FLAME_BURST, center, this.anchorYaw, 4.5f, 0);
            WitchVfx.send(level, WitchVfx.SLASH, this.boss.position(), this.anchorYaw, 6.0f, WitchVfx.SLASH_OVERHEAD);
            this.fireRadialCrescents(level, center, this.anchorYaw, 4);
        }
    }

    private void tickSpin(ServerLevel level, int t, LivingEntity target) {
        Vec3 c = this.boss.position();
        if (t == SPIN_START) {
            this.sound(level, "item.trident.riptide_2", SoundSource.HOSTILE, 1.6f, 0.8f);
            WitchVfx.send(level, WitchVfx.WHIRL, c, this.boss.getYRot(), 4.8f, SPIN_END - SPIN_START);
        }
        if (t >= SPIN_START && t < SPIN_END && target != null && target.isAlive()) {
            Vec3 d = new Vec3(target.getX() - c.x, 0.0, target.getZ() - c.z);
            if (d.lengthSqr() > 1.0) {
                d = d.normalize().scale(0.2);
                this.boss.setDeltaMovement(new Vec3(d.x, this.boss.getDeltaMovement().y, d.z));
                this.boss.hurtMarked = true;
            }
        }
        if (t >= SPIN_START + 5 && t <= SPIN_END - 10 && (t - SPIN_START) % 10 == 5) {
            this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 1.6f, 1.3f);
            this.hitAround(level, this.attackSource(), c, 4.8, 0.35, 5.0);
        }
        if (t == SPIN_END) {
            this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 0.9f);
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 1.4f, 1.2f);
            this.hitAround(level, this.attackSource(), c, 6.2, 0.85, 20.0);
            WitchVfx.send(level, WitchVfx.SLASH, c, this.boss.getYRot(), 6.2f, WitchVfx.SLASH_SPIN);
            WitchVfx.send(level, WitchVfx.SHOCKWAVE, c, this.boss.getYRot(), 6.2f, WitchVfx.ring(6, 0.0));
            this.fireRadialCrescents(level, c, this.boss.getYRot() + 45.0f, 4);
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

    private DamageSource attackSource() {
        return this.boss.damageSources().mobAttack(this.boss);
    }

    private DamageSource sourcelessDamage(ServerLevel level) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).get(DamageTypes.MOB_ATTACK).orElseThrow());
    }

    private void hitAround(ServerLevel level, DamageSource source, Vec3 center, double radius, double multiplier, double grief) {
        this.hitRing(level, source, center, 0.0, radius, multiplier, grief, null);
    }

    private void hitRing(ServerLevel level, DamageSource source, Vec3 center, double innerRadius, double outerRadius,
                         double multiplier, double grief, Set<Integer> seen) {
        float damage = BossAttributes.skillDamage(this.boss, multiplier);
        double height = Math.max(3.0, outerRadius * 0.4);
        AABB box = new AABB(center.x - outerRadius, center.y - 1.0, center.z - outerRadius, center.x + outerRadius, center.y + height, center.z + outerRadius);
        List<LivingEntity> hits = new ArrayList<>();
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, WitchCombat::canBeHit)) {
            double edgeDist = horizontalEdgeDistance(e, center.x, center.z);
            double centerDist = Math.hypot(e.getX() - center.x, e.getZ() - center.z);
            if (edgeDist > outerRadius || (innerRadius > 0.0 && centerDist < innerRadius)) {
                continue;
            }
            if (seen != null && !seen.add(e.getId())) {
                continue;
            }
            hits.add(e);
        }
        String tag = this.skill == null ? "NONE" : this.skill.name();
        for (LivingEntity e : hits) {
            this.damage(e, source, damage, grief, tag, outerRadius);
        }
    }

    private void damage(LivingEntity e, DamageSource source, float damage, double grief, String tag, double radius) {
        if (e instanceof Player) {
            MokelsBossfightSaphyraMod.LOGGER.info("[WitchHitDebug] skill={} tick={} radius={} dist={} damage={}",
                    tag, this.skillTick, radius, String.format("%.2f", Math.sqrt(e.distanceToSqr(this.boss.getX(), e.getY(), this.boss.getZ()))), damage);
        }
        e.hurtOrSimulate(source, damage);
        if (grief > 0.0 && e instanceof Player player) {
            MokelsBossfightSaphyraModVariables.PlayerVariables vars = MokelsBossfightSaphyraModVariables.getPlayerVariables(player);
            vars.Grief += grief;
            vars.syncPlayerVariables(player);
        }
    }

    private static double horizontalEdgeDistance(LivingEntity e, double x, double z) {
        AABB bb = e.getBoundingBox();
        double nx = Mth.clamp(x, bb.minX, bb.maxX) - x;
        double nz = Mth.clamp(z, bb.minZ, bb.maxZ) - z;
        return Math.sqrt(nx * nx + nz * nz);
    }

    public static boolean canBeHit(LivingEntity e) {
        return !(e instanceof WitchbossEntity) && !(e instanceof MoonbeamEntity) && e.isAlive() && !e.isSpectator()
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
                this.sound(level, "saphyra_reborn:saphyrastheme" + (i + 1), SoundSource.MUSIC, 2.0f, 1.0f);
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
        this.boss.setPhase2Visual(this.phase2 && this.phaseShiftTicks <= PHASE_SHIFT_TICKS - PHASE_BURST_TICK);
    }

    private static final class LunarCrescent {
        double x;
        double y;
        double z;
        final double vx;
        final double vz;
        final int maxAge;
        int age;
        final Set<Integer> hitIds = new HashSet<>();

        LunarCrescent(double x, double y, double z, double vx, double vz, int maxAge) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.vx = vx;
            this.vz = vz;
            this.maxAge = maxAge;
        }
    }
}
