package com.hp.saphyrareborn.boss.nameless;

import com.hp.saphyrareborn.boss.BossAttributes;
import com.hp.saphyrareborn.boss.BossCastLock;
import com.hp.saphyrareborn.boss.BossConfig;
import com.hp.saphyrareborn.boss.BossMusic;
import com.hp.saphyrareborn.entity.NamelessEntity;
import com.hp.saphyrareborn.entity.NamelessInvisibleProjectileEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class NamelessCombat {
    private static final int[] POINTS = {20, 100, 220, 340, 450, 520};
    private static final int RESET = 620;
    private static final int[] THEME_TICKS = {20, 320, 620, 920, 1220, 1520, 1820};
    private static final int MUSIC_RESET = 1900;
    private static final int PHASE_SHIFT_TICKS = 60;
    private static final int PHASE_ROAR_TICK = 30;
    private static final double[] ROT_DISTANCES = {4.5, 8.5, 12.5, 16.5, 20.5};
    private static final double ROT_FAN_RAD = Math.toRadians(26.0);
    private static final double WAVE_SPEED = 1.15;
    private static final int WAVE_LIFE = 16;

    private final NamelessEntity boss;
    private final List<BladeWave> waves = new ArrayList<>();
    private int timeline;
    private NamelessSkill skill;
    private int skillTick;
    private int repeats;
    private int pendingPoint = -1;
    private boolean phase2;
    private int phaseShiftTicks;
    private int staggerTicks;
    private float blockedDamage;
    private float lastBlockedAmount;
    private int lastBlockTick = -100;
    private double rotDirX;
    private double rotDirZ;
    private double anchorX;
    private double anchorY;
    private double anchorZ;
    private float anchorYaw;

    public NamelessCombat(NamelessEntity boss) {
        this.boss = boss;
    }

    public boolean isPhase2() {
        return this.phase2;
    }

    public boolean isInvulnerable() {
        return this.phaseShiftTicks > 0;
    }

    public void resetFight() {
        this.cancelSkill();
        this.waves.clear();
        this.phase2 = false;
        this.phaseShiftTicks = 0;
        this.staggerTicks = 0;
        this.pendingPoint = -1;
        this.timeline = 0;
        this.boss.setPhase2Visual(false);
        this.boss.stopProcedureAnimation();
    }

    public void tick() {
        if (!(this.boss.level() instanceof ServerLevel level) || !this.boss.isAlive()) {
            return;
        }
        this.tickWaves(level);
        LivingEntity target = this.boss.getTarget();
        boolean floating = this.skill == NamelessSkill.FLOAT;
        if (this.boss.isNoGravity() != floating) {
            this.boss.setNoGravity(floating);
        }
        this.tickMusic(level, target);
        this.faceTarget(target);
        this.debugLog(target);
        if (!this.phase2 && BossConfig.NAMELESS_PHASE2_ENABLED.get()
                && this.boss.getHealth() <= this.boss.getMaxHealth() * BossConfig.NAMELESS_PHASE2_RATIO.get()) {
            this.startPhaseShift(level);
        }
        if (this.phaseShiftTicks > 0) {
            this.tickPhaseShift(level);
            return;
        }
        if (this.staggerTicks > 0) {
            this.staggerTicks--;
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
        if (this.phaseShiftTicks > 0 || this.staggerTicks > 0) {
            return 0.0f;
        }
        if (this.skill == null) {
            return 30.0f;
        }
        return switch (this.skill) {
            case ROT -> this.skillTick >= 10 ? 0.0f : 12.0f;
            case FLOAT -> this.skillTick >= 21 ? 0.0f : 12.0f;
            case STOMP -> this.skillTick >= 20 ? 0.0f : 10.0f;
            case GROUND -> this.skillTick >= 31 ? 0.0f : 10.0f;
            default -> 12.0f;
        };
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

    private void stepIn(double speed) {
        double scale = this.boss.onGround() ? 1.0 : 0.35;
        double rad = Math.toRadians(this.boss.getYRot());
        double fx = -Math.sin(rad) * speed * scale;
        double fz = Math.cos(rad) * speed * scale;
        Vec3 cur = this.boss.getDeltaMovement();
        this.boss.setDeltaMovement(cur.x * 0.25 + fx, cur.y, cur.z * 0.25 + fz);
        this.boss.hurtMarked = true;
    }

    private void lockAnchor() {
        this.anchorX = this.boss.getX();
        this.anchorY = this.boss.getY();
        this.anchorZ = this.boss.getZ();
        this.anchorYaw = this.boss.getYRot();
    }

    private Vec3 anchorPos() {
        if (this.anchorX == 0.0 && this.anchorY == 0.0 && this.anchorZ == 0.0) {
            return this.boss.position();
        }
        return new Vec3(this.anchorX, this.anchorY, this.anchorZ);
    }

    private void fireWaveFan(ServerLevel level, int count, double spreadDeg) {
        float baseYaw = this.boss.getYRot();
        Vec3 origin = new Vec3(this.boss.getX(), this.boss.getY(), this.boss.getZ());
        this.sound(level, "entity.blaze.shoot", SoundSource.HOSTILE, 1.4f, 0.75f);
        for (int i = 0; i < count; i++) {
            double offset = count == 1 ? 0.0 : (i - (count - 1) * 0.5) * spreadDeg;
            float yaw = (float) (baseYaw + offset);
            double rad = Math.toRadians(yaw);
            double fx = -Math.sin(rad);
            double fz = Math.cos(rad);
            Vec3 spawn = new Vec3(origin.x + fx * 1.4, origin.y, origin.z + fz * 1.4);
            this.waves.add(new BladeWave(spawn.x, spawn.y, spawn.z, fx * WAVE_SPEED, fz * WAVE_SPEED, WAVE_LIFE));
            NamelessVfx.send(level, NamelessVfx.WAVE, spawn, yaw, 2.2f, WAVE_LIFE);
        }
    }

    private void tickWaves(ServerLevel level) {
        if (this.waves.isEmpty()) {
            return;
        }
        DamageSource source = this.boss.damageSources().mobAttack(this.boss);
        float damage = BossAttributes.skillDamage(this.boss, 0.75);
        Iterator<BladeWave> it = this.waves.iterator();
        while (it.hasNext()) {
            BladeWave w = it.next();
            w.x += w.vx;
            w.z += w.vz;
            w.age++;
            if (w.age % 2 == 0) {
                level.sendParticles(this.redFlame(), w.x, w.y + 0.9, w.z, 3, 0.35, 0.35, 0.35, 0.01);
            }
            AABB box = new AABB(w.x - 2.0, w.y - 0.8, w.z - 2.0, w.x + 2.0, w.y + 2.6, w.z + 2.0);
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, NamelessCombat::canBeHit)) {
                if (!w.hitIds.add(e.getId())) {
                    continue;
                }
                AABB bb = e.getBoundingBox();
                double nx = Mth.clamp(w.x, bb.minX, bb.maxX) - w.x;
                double nz = Mth.clamp(w.z, bb.minZ, bb.maxZ) - w.z;
                if (nx * nx + nz * nz > 4.0) {
                    w.hitIds.remove(e.getId());
                    continue;
                }
                e.hurtOrSimulate(source, damage);
                if (e instanceof Player player) {
                    addRotting(player, 8.0);
                }
            }
            if (w.age >= w.maxAge) {
                it.remove();
            }
        }
    }

    private void debugLog(LivingEntity target) {
        if (target == null || this.boss.tickCount % 100 != 0) {
            return;
        }
        double dx = target.getX() - this.boss.getX();
        double dz = target.getZ() - this.boss.getZ();
        float want = (float) (Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0f;
        com.hp.saphyrareborn.MokelsBossfightSaphyraMod.LOGGER.info("[NamelessDebug] id={} onGround={} noGravity={} yRot={} bodyRot={} headRot={} targetYaw={} navDone={} skill={} timeline={} phase2={}",
                this.boss.getId(), this.boss.onGround(), this.boss.isNoGravity(), String.format("%.1f", Mth.wrapDegrees(this.boss.getYRot())), String.format("%.1f", Mth.wrapDegrees(this.boss.yBodyRot)),
                String.format("%.1f", Mth.wrapDegrees(this.boss.getYHeadRot())), String.format("%.1f", Mth.wrapDegrees(want)), this.boss.getNavigation().isDone(), this.skill, this.timeline, this.phase2);
    }

    public boolean onBlockedHit(DamageSource source, float amount) {
        if (!(this.boss.level() instanceof ServerLevel level)) {
            return true;
        }
        float counted;
        if (this.boss.tickCount - this.lastBlockTick >= 10) {
            counted = amount;
            this.lastBlockTick = this.boss.tickCount;
            this.lastBlockedAmount = amount;
            this.sound(level, "item.shield.block", SoundSource.HOSTILE, 1.5f, 0.9f + this.boss.getRandom().nextFloat() * 0.2f);
            NamelessVfx.send(level, NamelessVfx.SHIELD_SPARK, this.boss.position(), this.boss.getYRot(), 1.0f, 0);
        } else if (amount > this.lastBlockedAmount) {
            counted = amount - this.lastBlockedAmount;
            this.lastBlockedAmount = amount;
        } else {
            return true;
        }
        if (Float.isFinite(counted) && counted > 0.0f) {
            this.blockedDamage += counted;
        }
        double ratio = BossConfig.NAMELESS_SHIELD_BREAK_RATIO.get();
        if (ratio > 0.0 && this.skill == NamelessSkill.SHIELD && this.blockedDamage >= this.boss.getMaxHealth() * ratio) {
            this.breakShield(level);
        }
        return true;
    }

    private void breakShield(ServerLevel level) {
        this.cancelSkill();
        int ticks = BossConfig.NAMELESS_STAGGER_TICKS.get();
        this.staggerTicks = ticks;
        BossCastLock.lock(this.boss, ticks);
        this.boss.restartAnimation("stagger");
        this.sound(level, "item.shield.break", SoundSource.HOSTILE, 2.0f, 0.7f);
        this.sound(level, "entity.wither_skeleton.hurt", SoundSource.HOSTILE, 2.0f, 0.6f);
        NamelessVfx.send(level, NamelessVfx.SHIELD_BREAK, this.boss.position(), this.boss.getYRot(), 1.0f, 0);
    }

    private void startPhaseShift(ServerLevel level) {
        this.phase2 = true;
        this.cancelSkill();
        this.staggerTicks = 0;
        this.pendingPoint = -1;
        this.timeline = 0;
        this.phaseShiftTicks = PHASE_SHIFT_TICKS;
        BossCastLock.lock(this.boss, PHASE_SHIFT_TICKS);
        this.boss.restartAnimation("phaseshift");
        this.sound(level, "entity.wither_skeleton.death", SoundSource.HOSTILE, 2.0f, 0.5f);
        NamelessVfx.send(level, NamelessVfx.PHASE_SHIFT, this.boss.position(), this.boss.getYRot(), 6.0f, PHASE_SHIFT_TICKS);
    }

    private void tickPhaseShift(ServerLevel level) {
        int age = PHASE_SHIFT_TICKS - this.phaseShiftTicks;
        if (age == PHASE_ROAR_TICK) {
            this.boss.setPhase2Visual(true);
            this.sound(level, "saphyra_reborn:namelessscream", SoundSource.HOSTILE, 2.0f, 0.8f);
            Vec3 c = this.boss.position();
            level.sendParticles(this.redFlame(), c.x, c.y + 1.0, c.z, 50, 3.0, 1.5, 3.0, 0.05);
            level.sendParticles(this.redFire(), c.x, c.y, c.z, 25, 6.0, 0.5, 6.0, 0.01);
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, this.boss.getBoundingBox().inflate(6.0), e -> e != this.boss && !(e instanceof NamelessEntity))) {
                e.knockback(0.8, this.boss.getX() - e.getX(), this.boss.getZ() - e.getZ());
                e.hurtMarked = true;
            }
        }
        this.phaseShiftTicks--;
    }

    private void cancelSkill() {
        this.skill = null;
        this.skillTick = 0;
        this.repeats = 0;
        this.blockedDamage = 0.0f;
        BossCastLock.release(this.boss);
    }

    private int scaled(int tick) {
        return this.phase2 ? Math.max(1, Mth.floor(tick * BossConfig.NAMELESS_PHASE2_CADENCE.get() + 0.5)) : tick;
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
        boolean first = this.boss.getRandom().nextDouble() <= 0.5;
        boolean near = this.playersWithin(level, 14.0);
        boolean far = this.playersWithin(level, 30.0);
        switch (point) {
            case 0 -> {
                if (first) {
                    this.start(level, near ? NamelessSkill.DOUBLE : NamelessSkill.ROT, 0);
                } else {
                    this.start(level, near ? NamelessSkill.ONCE : NamelessSkill.FLOAT, 0);
                }
            }
            case 1 -> {
                if (first) {
                    if (near) {
                        this.start(level, NamelessSkill.SHIELD, 0);
                    } else {
                        this.start(level, NamelessSkill.FLOAT, 2);
                    }
                } else if (near) {
                    this.start(level, NamelessSkill.SHIELD_SLASH, 0);
                } else {
                    this.start(level, NamelessSkill.ROT, 1);
                }
            }
            case 2 -> {
                if (first) {
                    if (near) {
                        this.start(level, NamelessSkill.SUPER, 0);
                    } else {
                        this.start(level, NamelessSkill.ROT, 1);
                    }
                } else if (near) {
                    this.start(level, NamelessSkill.DOUBLE_ONCE, 0);
                } else {
                    this.start(level, NamelessSkill.FLOAT, 1);
                }
            }
            case 3 -> {
                if (far) {
                    this.start(level, first ? NamelessSkill.GROUND : NamelessSkill.BRUELLEN, 0);
                }
            }
            case 4 -> {
                if (first) {
                    if (far) {
                        this.start(level, NamelessSkill.ROT, 0);
                    }
                } else if (this.boss.getRandom().nextDouble() <= 0.5) {
                    this.start(level, near ? NamelessSkill.DOUBLE : NamelessSkill.FLOAT, 0);
                } else {
                    this.start(level, near ? NamelessSkill.ONCE : NamelessSkill.FLOAT, 0);
                }
            }
            case 5 -> {
                if (far) {
                    if (first) {
                        this.start(level, NamelessSkill.STOMP, 0);
                    } else {
                        this.start(level, NamelessSkill.FLOAT, 2);
                    }
                }
            }
            default -> {
            }
        }
    }

    private void start(ServerLevel level, NamelessSkill next, int extraRepeats) {
        this.skill = next;
        this.repeats = extraRepeats;
        this.begin(level);
    }

    private void begin(ServerLevel level) {
        this.skillTick = 0;
        this.blockedDamage = 0.0f;
        NamelessSkill current = this.skill;
        this.boss.restartAnimation(current.animation);
        if (current.lockTicks > 0) {
            BossCastLock.lock(this.boss, current.lockTicks);
        }
        Vec3 c = this.boss.position();
        float yaw = this.boss.getYRot();
        switch (current) {
            case SHIELD -> {
                BossCastLock.shield(this.boss, 80);
                this.sound(level, "item.armor.equip_iron", SoundSource.HOSTILE, 2.0f, 0.6f);
                NamelessVfx.send(level, NamelessVfx.SHIELD_RAISE, c, yaw, 1.0f, 80);
            }
            case GROUND -> NamelessVfx.send(level, NamelessVfx.CHARGE, c, yaw, 3.2f, 31);
            case BRUELLEN -> NamelessVfx.send(level, NamelessVfx.CHARGE, c, yaw, 4.2f, 26);
            case STOMP -> NamelessVfx.send(level, NamelessVfx.CHARGE, c, yaw, 2.8f, 20);
            default -> {
            }
        }
    }

    private void tickSkill(ServerLevel level) {
        this.skillTick++;
        int t = this.skillTick;
        switch (this.skill) {
            case ONCE -> {
                if (t == 16) {
                    this.stepIn(0.65);
                }
                if (t == 22) {
                    this.slash(level, 1.0, 10.0, NamelessVfx.SLASH_LEFT, "entity.player.attack.sweep");
                    this.fireWaveFan(level, this.phase2 ? 3 : 1, 18.0);
                }
            }
            case DOUBLE -> this.tickDouble(level, t);
            case DOUBLE_ONCE -> {
                this.tickDouble(level, t);
                if (t == 40) {
                    this.boss.restartAnimation("attackonce");
                    BossCastLock.lock(this.boss, 50);
                }
                if (t == 56) {
                    this.stepIn(0.75);
                }
                if (t == 62) {
                    this.slash(level, 1.05, 12.0, NamelessVfx.SLASH_LEFT, "entity.player.attack.sweep");
                    this.fireWaveFan(level, this.phase2 ? 3 : 1, 18.0);
                }
            }
            case FLOAT -> {
                if (!this.tickFloat(level, t)) {
                    this.boss.stopProcedureAnimation();
                    this.finishOrRepeat(level, true);
                    return;
                }
            }
            case ROT -> this.tickRot(level, t);
            case SHIELD -> this.tickShield(level, t);
            case SHIELD_SLASH -> {
                if (t == 22) {
                    this.stepIn(0.6);
                }
                if (t == 28) {
                    this.sound(level, "item.shield.block", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, this.boss.position(), 5.0, 1.0, 10.0);
                    NamelessVfx.send(level, NamelessVfx.SHIELD_SPARK, this.boss.position(), this.boss.getYRot(), 2.0f, 0);
                    NamelessVfx.send(level, NamelessVfx.SLAM, this.boss.position(), this.boss.getYRot(), 5.0f, NamelessVfx.ring(5, 0.0));
                }
                if (t == 40) {
                    this.stepIn(0.65);
                }
                if (t == 45) {
                    this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, this.boss.position(), 6.0, 1.05, 10.0);
                    NamelessVfx.send(level, NamelessVfx.SLASH, this.boss.position(), this.boss.getYRot(), 6.0f, NamelessVfx.SLASH_OVERHEAD);
                    NamelessVfx.send(level, NamelessVfx.SLAM, this.boss.position(), this.boss.getYRot(), 6.0f, NamelessVfx.ring(6, 0.0));
                }
            }
            case SUPER -> {
                if (t == 20) {
                    this.stepIn(0.65);
                }
                if (t == 25) {
                    this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, this.boss.position(), 6.0, 1.0, 10.0);
                    NamelessVfx.send(level, NamelessVfx.SLASH, this.boss.position(), this.boss.getYRot(), 6.0f, NamelessVfx.SLASH_OVERHEAD);
                    NamelessVfx.send(level, NamelessVfx.SLAM, this.boss.position(), this.boss.getYRot(), 6.0f, NamelessVfx.ring(6, 0.0));
                }
                if (t == 43) {
                    this.stepIn(0.55);
                }
                if (t == 47) {
                    this.sound(level, "item.shield.block", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, this.boss.position(), 6.0, 1.0, 10.0);
                    NamelessVfx.send(level, NamelessVfx.SHIELD_SPARK, this.boss.position(), this.boss.getYRot(), 2.2f, 0);
                    NamelessVfx.send(level, NamelessVfx.SLAM, this.boss.position(), this.boss.getYRot(), 4.5f, NamelessVfx.ring(5, 0.0));
                }
                if (t == 58) {
                    this.stepIn(0.75);
                }
                if (t == 63) {
                    this.slash(level, 1.15, 12.0, NamelessVfx.SLASH_RIGHT, "entity.player.attack.sweep");
                    this.fireWaveFan(level, this.phase2 ? 5 : 3, this.phase2 ? 16.0 : 20.0);
                }
            }
            case GROUND -> this.tickGround(level, t);
            case BRUELLEN -> {
                if (t == 26) {
                    this.eruption(level, 15.0, 40.0, SoundSource.HOSTILE, "saphyra_reborn:namelessscream", false);
                    NamelessVfx.send(level, NamelessVfx.ROAR, this.boss.position(), this.boss.getYRot(), 15.0f, 0);
                    this.throwRotRain(level);
                }
            }
            case STOMP -> this.tickStomp(level, t);
            default -> {
            }
        }
        if (this.skill != null && this.skillTick >= (this.repeats > 0 ? this.skill.repeatInterval : this.skill.duration)) {
            this.finishOrRepeat(level, false);
        }
    }

    private void tickShield(ServerLevel level, int t) {
        if (t == 58) {
            Vec3 c = this.boss.position();
            float yaw = this.boss.getYRot();
            this.sound(level, "block.respawn_anchor.charge", SoundSource.HOSTILE, 1.8f, 1.15f);
            NamelessVfx.send(level, NamelessVfx.CHARGE, c, yaw, 3.2f, 18);
            NamelessVfx.send(level, NamelessVfx.TELEGRAPH_RING, c, yaw, 7.0f, NamelessVfx.ring(18, 0.0));
        }
        if (t == 76) {
            Vec3 c = this.boss.position();
            float yaw = this.boss.getYRot();
            this.sound(level, "item.shield.block", SoundSource.HOSTILE, 2.0f, 0.65f);
            this.sound(level, "entity.generic.explode", SoundSource.HOSTILE, 1.8f, 1.15f);
            this.hitAround(level, c, 7.0, 0.9, 15.0);
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, this.boss.getBoundingBox().inflate(7.0), NamelessCombat::canBeHit)) {
                e.knockback(0.6, this.boss.getX() - e.getX(), this.boss.getZ() - e.getZ());
                e.hurtMarked = true;
            }
            NamelessVfx.send(level, NamelessVfx.SHIELD_COUNTER, c, yaw, 7.0f, 10);
            this.fireWaveFan(level, this.phase2 ? 5 : 3, 20.0);
        }
    }

    private void tickGround(ServerLevel level, int t) {
        if (t == 31) {
            this.lockAnchor();
            Vec3 c = this.anchorPos();
            float yaw = this.anchorYaw;
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 1.0f);
            level.sendParticles(ParticleTypes.EXPLOSION, c.x, c.y, c.z, 10, 2.2, 0.4, 2.2, 0.01);
            this.hitRing(level, c, 0.0, 5.5, 1.0, 10.0);
            NamelessVfx.send(level, NamelessVfx.SLAM, c, yaw, 5.5f, NamelessVfx.ring(6, 0.0));
            NamelessVfx.send(level, NamelessVfx.TELEGRAPH_RING, c, yaw, 6.5f, NamelessVfx.ring(24, 0.0));
            NamelessVfx.send(level, NamelessVfx.TELEGRAPH_RING, c, yaw, 11.5f, NamelessVfx.ring(37, 6.0));
            NamelessVfx.send(level, NamelessVfx.TELEGRAPH_RING, c, yaw, 16.5f, NamelessVfx.ring(50, 11.0));
        }
        if (t == 55) {
            this.detonateGroundRing(level, 0.0, 6.5, 4.8, 6, 1.0, 20.0, 8, 1.05f);
        }
        if (t == 68) {
            this.detonateGroundRing(level, 6.0, 11.5, 8.8, 10, 1.05, 25.0, 9, 0.92f);
        }
        if (t == 81) {
            this.detonateGroundRing(level, 11.0, 16.5, 13.8, 14, 1.1, 30.0, 10, 0.8f);
        }
    }

    private void detonateGroundRing(ServerLevel level, double innerR, double outerR, double pillarR, int pillarCount,
                                    double multiplier, double rot, int slamTicks, float pitch) {
        Vec3 c = this.anchorPos();
        this.sound(level, "entity.generic.explode", SoundSource.HOSTILE, 2.0f, pitch);
        this.sound(level, "item.trident.hit_ground", SoundSource.HOSTILE, 1.8f, pitch);
        this.hitRing(level, c, innerR, outerR, multiplier, rot);
        NamelessVfx.send(level, NamelessVfx.SLAM, c, this.anchorYaw, (float) outerR, NamelessVfx.ring(slamTicks, innerR));
        double base = Math.toRadians(this.anchorYaw);
        for (int i = 0; i < pillarCount; i++) {
            double a = base + Math.PI * 2.0 * i / pillarCount;
            Vec3 p = new Vec3(c.x + Math.cos(a) * pillarR, c.y, c.z + Math.sin(a) * pillarR);
            level.sendParticles(this.redFlame(), p.x, p.y + 0.2, p.z, 6, 0.6, 0.3, 0.6, 0.02);
            NamelessVfx.send(level, NamelessVfx.ROT_PILLAR, p, 0.0f, 1.9f, i % 3);
        }
    }

    private void tickStomp(ServerLevel level, int t) {
        if (t == 20) {
            this.lockAnchor();
            if (this.phase2) {
                this.anchorYaw += 22.5f;
            }
            Vec3 c = this.anchorPos();
            NamelessVfx.send(level, NamelessVfx.TELEGRAPH_RING, c, this.anchorYaw, 6.0f, NamelessVfx.ring(36, 0.0));
            for (int k = 0; k < 8; k++) {
                float rayYaw = this.anchorYaw + k * 45.0f;
                NamelessVfx.send(level, NamelessVfx.TELEGRAPH_LINE, c, rayYaw, 18.0f, 36);
            }
        }
        if (t == 56) {
            Vec3 c = this.anchorPos();
            this.sound(level, "entity.generic.explode", SoundSource.HOSTILE, 2.0f, 0.85f);
            this.sound(level, "item.trident.thunder", SoundSource.HOSTILE, 1.4f, 1.25f);
            Set<Integer> hitSet = new HashSet<>();
            this.hitRingUnique(level, c, 0.0, 6.0, 1.05, 25.0, hitSet);
            NamelessVfx.send(level, NamelessVfx.SLAM, c, this.anchorYaw, 6.0f, NamelessVfx.ring(8, 0.0));
            for (int k = 0; k < 8; k++) {
                double rad = Math.toRadians(this.anchorYaw + k * 45.0f);
                double fx = -Math.sin(rad);
                double fz = Math.cos(rad);
                this.hitLineUnique(level, c, fx, fz, 18.5, 1.35, 1.0, 25.0, hitSet);
                for (int step = 0; step < 3; step++) {
                    double d = 6.5 + step * 5.0;
                    Vec3 p = new Vec3(c.x + fx * d, c.y, c.z + fz * d);
                    NamelessVfx.send(level, NamelessVfx.ROT_PILLAR, p, 0.0f, 1.85f, step * 2);
                }
            }
        }
    }

    private void finishOrRepeat(ServerLevel level, boolean aborted) {
        if (!aborted && this.repeats > 0) {
            this.repeats--;
            this.begin(level);
            return;
        }
        this.skill = null;
        this.skillTick = 0;
        this.repeats = 0;
        this.blockedDamage = 0.0f;
    }

    private void tickDouble(ServerLevel level, int t) {
        if (t == 12) {
            this.stepIn(0.55);
        }
        if (t == 16) {
            this.slash(level, 0.65, 5.0, NamelessVfx.SLASH_OVERHEAD, "entity.player.attack.sweep");
        }
        if (t == 21) {
            this.stepIn(0.55);
        }
        if (t == 24) {
            this.slash(level, 0.65, 5.0, NamelessVfx.SLASH_RIGHT, "entity.player.attack.sweep");
        }
    }

    private boolean tickFloat(ServerLevel level, int t) {
        if (t == 1) {
            Vec3 look = this.boss.getLookAngle();
            this.boss.setDeltaMovement(new Vec3(-look.x, 1.5, -look.z));
            this.boss.hurtMarked = true;
            level.sendParticles(ParticleTypes.POOF, this.boss.getX(), this.boss.getY(), this.boss.getZ(), 10, 0.5, 0.5, 0.5, 0.1);
            NamelessVfx.send(level, NamelessVfx.SLAM, this.boss.position(), this.boss.getYRot(), 2.5f, NamelessVfx.ring(4, 0.0));
        }
        if (t == 21) {
            LivingEntity target = this.boss.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            double dx = target.getX() - this.boss.getX();
            double dy = target.getY() - this.boss.getY();
            double dz = target.getZ() - this.boss.getZ();
            this.boss.setDeltaMovement(new Vec3(dx * 0.35, dy * 0.35 + 0.15, dz * 0.35));
            this.boss.hurtMarked = true;
            NamelessVfx.send(level, NamelessVfx.TELEGRAPH_RING, target.position(), this.boss.getYRot(), 6.0f, NamelessVfx.ring(3, 0.0));
        }
        if (t == 24) {
            this.boss.setDeltaMovement(new Vec3(0.0, -0.5, 0.0));
            this.boss.hurtMarked = true;
            this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.0f);
            this.hitAround(level, this.boss.position(), 6.0, 1.05, 10.0);
            NamelessVfx.send(level, NamelessVfx.SLASH, this.boss.position(), this.boss.getYRot(), 5.5f, NamelessVfx.SLASH_OVERHEAD);
            NamelessVfx.send(level, NamelessVfx.SLAM, this.boss.position(), this.boss.getYRot(), 6.0f, NamelessVfx.ring(6, 0.0));
            if (this.phase2) {
                this.fireWaveFan(level, 3, 22.0);
            }
        }
        return true;
    }

    private void tickRot(ServerLevel level, int t) {
        if (t == 10) {
            this.lockAnchor();
            double rad = Math.toRadians(this.boss.getYRot());
            this.rotDirX = -Math.sin(rad);
            this.rotDirZ = Math.cos(rad);
            Vec3 origin = this.anchorPos();
            for (double[] dir : this.rotDirections()) {
                float yaw = (float) Math.toDegrees(Math.atan2(-dir[0], dir[1]));
                NamelessVfx.send(level, NamelessVfx.TELEGRAPH_LINE, origin, yaw, 21.0f, 12);
            }
        }
        if (t >= 18 && t <= 26 && (t - 18) % 2 == 0) {
            int idx = (t - 18) / 2;
            double d = ROT_DISTANCES[idx];
            Vec3 origin = this.anchorPos();
            this.sound(level, "item.trident.hit", SoundSource.NEUTRAL, 1.8f, 0.85f + idx * 0.08f);
            for (double[] dir : this.rotDirections()) {
                Vec3 p = new Vec3(origin.x + dir[0] * d, origin.y, origin.z + dir[1] * d);
                level.sendParticles(this.redFlame(), p.x, p.y + 0.2, p.z, 10, 1.2, 0.4, 1.2, 0.01);
                this.hitAround(level, p, 3.3, 0.95, 10.0);
                NamelessVfx.send(level, NamelessVfx.ROT_PILLAR, p, 0.0f, 2.2f, 0);
            }
        }
    }

    private double[][] rotDirections() {
        double len = Math.hypot(this.rotDirX, this.rotDirZ);
        double bx = len > 1.0E-4 ? this.rotDirX / len : 0.0;
        double bz = len > 1.0E-4 ? this.rotDirZ / len : 1.0;
        if (!this.phase2) {
            return new double[][]{{bx, bz}};
        }
        double cos = Math.cos(ROT_FAN_RAD);
        double sin = Math.sin(ROT_FAN_RAD);
        return new double[][]{
                {bx, bz},
                {bx * cos - bz * sin, bx * sin + bz * cos},
                {bx * cos + bz * sin, -bx * sin + bz * cos}
        };
    }

    private void slash(ServerLevel level, double multiplier, double rot, int variant, String sound) {
        this.sound(level, sound, SoundSource.NEUTRAL, 2.0f, 1.0f);
        this.hitAround(level, this.boss.position(), 6.0, multiplier, rot);
        NamelessVfx.send(level, NamelessVfx.SLASH, this.boss.position(), this.boss.getYRot(), 6.0f, variant);
    }

    private void eruption(ServerLevel level, double radius, double rot, SoundSource source, String sound, boolean slam) {
        Vec3 c = this.boss.position();
        this.sound(level, sound, source, 2.0f, 1.0f);
        level.sendParticles(this.redFlame(), c.x, c.y, c.z, 80, 8.0, 0.5, 8.0, 0.01);
        level.sendParticles(this.redFire(), c.x, c.y, c.z, 40, 8.0, 0.5, 8.0, 0.01);
        this.hitAround(level, c, radius, 1.0, rot);
        if (slam) {
            NamelessVfx.send(level, NamelessVfx.SLAM, c, this.boss.getYRot(), (float) radius, NamelessVfx.ring(10, 0.0));
        }
    }

    private void throwRotRain(ServerLevel level) {
        int count = this.phase2 ? 12 : 8;
        for (int i = 0; i < count; i++) {
            NamelessInvisibleProjectileEntity projectile = new NamelessInvisibleProjectileEntity(MokelsBossfightSaphyraModEntities.NAMELESS_INVISIBLE_PROJECTILE.get(), level);
            projectile.setOwner(this.boss);
            projectile.setBaseDamage(0.0);
            projectile.setSilent(true);
            projectile.setPos(this.boss.getX(), this.boss.getY() + 2.2, this.boss.getZ());
            projectile.shoot(0.0, 90.0, 0.0, 1.0f, 30.0f);
            level.addFreshEntity(projectile);
        }
    }

    private void hitAround(ServerLevel level, Vec3 center, double radius, double multiplier, double rot) {
        this.hitRing(level, center, 0.0, radius, multiplier, rot);
    }

    private void hitRing(ServerLevel level, Vec3 center, double innerRadius, double outerRadius, double multiplier, double rot) {
        this.hitRingUnique(level, center, innerRadius, outerRadius, multiplier, rot, null);
    }

    private void hitRingUnique(ServerLevel level, Vec3 center, double innerRadius, double outerRadius, double multiplier, double rot, Set<Integer> seen) {
        DamageSource source = this.boss.damageSources().mobAttack(this.boss);
        float damage = BossAttributes.skillDamage(this.boss, multiplier);
        double height = Math.max(3.0, outerRadius * 0.4);
        AABB box = new AABB(center.x - outerRadius, center.y - 1.0, center.z - outerRadius, center.x + outerRadius, center.y + height, center.z + outerRadius);
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, NamelessCombat::canBeHit)) {
            AABB bb = e.getBoundingBox();
            double nx = Mth.clamp(center.x, bb.minX, bb.maxX) - center.x;
            double nz = Mth.clamp(center.z, bb.minZ, bb.maxZ) - center.z;
            double edgeDist = Math.sqrt(nx * nx + nz * nz);
            double centerDist = Math.hypot(e.getX() - center.x, e.getZ() - center.z);
            if (edgeDist > outerRadius || (innerRadius > 0.0 && centerDist < innerRadius)) {
                continue;
            }
            if (seen != null && !seen.add(e.getId())) {
                continue;
            }
            if (e instanceof Player) {
                com.hp.saphyrareborn.MokelsBossfightSaphyraMod.LOGGER.info("[NamelessHitDebug] skill={} tick={} inner={} outer={} edgeDist={} centerDist={} dy={} damage={}",
                        this.skill, this.skillTick, innerRadius, outerRadius, String.format("%.2f", edgeDist), String.format("%.2f", centerDist), String.format("%.2f", e.getY() - center.y), damage);
            }
            e.hurtOrSimulate(source, damage);
            if (e instanceof Player player) {
                addRotting(player, rot);
            }
        }
    }

    private void hitLineUnique(ServerLevel level, Vec3 origin, double fx, double fz, double length, double halfWidth, double multiplier, double rot, Set<Integer> seen) {
        DamageSource source = this.boss.damageSources().mobAttack(this.boss);
        float damage = BossAttributes.skillDamage(this.boss, multiplier);
        double minX = Math.min(origin.x, origin.x + fx * length) - halfWidth - 1.0;
        double maxX = Math.max(origin.x, origin.x + fx * length) + halfWidth + 1.0;
        double minZ = Math.min(origin.z, origin.z + fz * length) - halfWidth - 1.0;
        double maxZ = Math.max(origin.z, origin.z + fz * length) + halfWidth + 1.0;
        AABB box = new AABB(minX, origin.y - 1.0, minZ, maxX, origin.y + 3.5, maxZ);
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, NamelessCombat::canBeHit)) {
            double rx = e.getX() - origin.x;
            double rz = e.getZ() - origin.z;
            double along = rx * fx + rz * fz;
            if (along < -0.5 || along > length) {
                continue;
            }
            double side = Math.abs(rx * fz - rz * fx);
            double hitWidth = halfWidth + e.getBbWidth() * 0.45;
            if (side > hitWidth) {
                continue;
            }
            if (seen != null && !seen.add(e.getId())) {
                continue;
            }
            e.hurtOrSimulate(source, damage);
            if (e instanceof Player player) {
                addRotting(player, rot);
            }
        }
    }

    public static boolean canBeHit(LivingEntity e) {
        return !(e instanceof NamelessEntity) && !e.hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE);
    }

    public static void addRotting(Player player, double amount) {
        MokelsBossfightSaphyraModVariables.PlayerVariables vars = MokelsBossfightSaphyraModVariables.getPlayerVariables(player);
        vars.Rotting += amount;
        vars.syncPlayerVariables(player);
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

    private ParticleOptions redFlame() {
        return MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get();
    }

    private ParticleOptions redFire() {
        return MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get();
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Timeline", this.timeline);
        if (this.skill != null) {
            tag.putString("Skill", this.skill.name());
        }
        tag.putInt("SkillTick", this.skillTick);
        tag.putInt("Repeats", this.repeats);
        tag.putInt("Pending", this.pendingPoint);
        tag.putBoolean("Phase2", this.phase2);
        tag.putInt("PhaseShift", this.phaseShiftTicks);
        tag.putInt("Stagger", this.staggerTicks);
        tag.putFloat("Blocked", this.blockedDamage);
        tag.putDouble("RotX", this.rotDirX);
        tag.putDouble("RotZ", this.rotDirZ);
        tag.putDouble("AnchorX", this.anchorX);
        tag.putDouble("AnchorY", this.anchorY);
        tag.putDouble("AnchorZ", this.anchorZ);
        tag.putFloat("AnchorYaw", this.anchorYaw);
        return tag;
    }

    public void load(CompoundTag tag) {
        this.timeline = tag.getIntOr("Timeline", 0);
        this.skill = tag.contains("Skill") ? NamelessSkill.byName(tag.getStringOr("Skill", "")) : null;
        this.skillTick = tag.getIntOr("SkillTick", 0);
        this.repeats = tag.getIntOr("Repeats", 0);
        this.pendingPoint = tag.contains("Pending") ? tag.getIntOr("Pending", 0) : -1;
        this.phase2 = tag.getBooleanOr("Phase2", false);
        this.phaseShiftTicks = tag.getIntOr("PhaseShift", 0);
        this.staggerTicks = tag.getIntOr("Stagger", 0);
        this.blockedDamage = tag.getFloatOr("Blocked", 0.0f);
        this.rotDirX = tag.getDoubleOr("RotX", 0.0);
        this.rotDirZ = tag.getDoubleOr("RotZ", 0.0);
        this.anchorX = tag.getDoubleOr("AnchorX", 0.0);
        this.anchorY = tag.getDoubleOr("AnchorY", 0.0);
        this.anchorZ = tag.getDoubleOr("AnchorZ", 0.0);
        this.anchorYaw = tag.getFloatOr("AnchorYaw", 0.0f);
        this.boss.setPhase2Visual(this.phase2 && this.phaseShiftTicks <= PHASE_SHIFT_TICKS - PHASE_ROAR_TICK);
    }

    private static final class BladeWave {
        double x;
        double y;
        double z;
        final double vx;
        final double vz;
        final int maxAge;
        int age;
        final Set<Integer> hitIds = new HashSet<>();

        BladeWave(double x, double y, double z, double vx, double vz, int maxAge) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.vx = vx;
            this.vz = vz;
            this.maxAge = maxAge;
        }
    }
}
