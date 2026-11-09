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
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
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
    private static final double LANCE_HALF_WIDTH = 1.0;
    private static final int JUMP_PLANT = 38;
    private static final int JUMP_SLAM = 46;
    private static final int SPIN_START = 25;
    private static final int SPIN_END = 80;

    private final WitchbossEntity boss;
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

    public WitchCombat(WitchbossEntity boss) {
        this.boss = boss;
    }

    public boolean isInvulnerable() {
        return this.phaseShiftTicks > 0;
    }

    public void resetFight() {
        this.cancelSkill();
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
            case GROUND -> WitchVfx.send(level, WitchVfx.CHARGE, c, yaw, 4.0f, 35);
            case AXE -> WitchVfx.send(level, WitchVfx.CHARGE, c, yaw, 2.5f, 26);
            case WAND -> WitchVfx.send(level, WitchVfx.CHARGE, c, yaw, 1.6f, WAND_FIRE);
            case JUMP -> {
                this.boss.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1, false, false));
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
        WitchVfx.send(level, WitchVfx.SHOCKWAVE, this.boss.position(), 0.0f, 2.0f, 4);
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
                if (t == 15) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, this.attackSource(), c, 4.0, 1.0, 10.0);
                    WitchVfx.send(level, WitchVfx.SLASH, c, yaw, 4.0f, WitchVfx.SLASH_RIGHT);
                }
            }
            case CLAW -> {
                if (t == 9) {
                    this.sound(level, "entity.phantom.flap", SoundSource.HOSTILE, 1.2f, 1.4f);
                    WitchVfx.send(level, WitchVfx.CLAW, c, yaw, 3.0f, 0);
                }
                if (t == 18) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, this.attackSource(), c, 5.0, 1.0, 20.0);
                    WitchVfx.send(level, WitchVfx.CLAW, c, yaw, 5.0f, 1);
                }
            }
            case CLAW2 -> {
                if (t == 11) {
                    WitchVfx.send(level, WitchVfx.CLAW, c, yaw, 3.5f, 0);
                }
                if (t == 21) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, this.attackSource(), c, 6.0, 1.0, 20.0);
                    WitchVfx.send(level, WitchVfx.CLAW, c, yaw, 6.0f, 1);
                }
                if (t == 45) {
                    WitchVfx.send(level, WitchVfx.CLAW, c, yaw, 4.0f, 0);
                    WitchVfx.send(level, WitchVfx.TELEGRAPH_RING, c, yaw, 7.0f, 8);
                }
                if (t == 53) {
                    this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    level.sendParticles(MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get(), c.x, c.y, c.z, 60, 3.0, 0.5, 3.0, 0.01);
                    this.hitAround(level, this.attackSource(), c, 7.0, 1.0, 20.0);
                    WitchVfx.send(level, WitchVfx.SHOCKWAVE, c, yaw, 7.0f, 6);
                    WitchVfx.send(level, WitchVfx.FLAME_BURST, c, yaw, 4.0f, 0);
                }
            }
            case RAIN -> this.tickRain(level, t);
            case GROUND -> this.tickGround(level, t);
            case AXE -> {
                if (t == 26 || t == 39 || t == 51) {
                    this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, t == 39 ? 1.15f : 1.0f);
                    this.hitAround(level, this.attackSource(), c, 7.0, 1.0, 20.0);
                    WitchVfx.send(level, WitchVfx.SLASH, c, yaw, 7.0f, t == 39 ? WitchVfx.SLASH_RIGHT : WitchVfx.SLASH_LEFT);
                }
                if (t == 73) {
                    WitchVfx.send(level, WitchVfx.TELEGRAPH_RING, c, yaw, 7.0f, 8);
                }
                if (t == 81) {
                    this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 1.0f);
                    this.hitAround(level, this.attackSource(), c, 7.0, 1.0, 20.0);
                    WitchVfx.send(level, WitchVfx.SLASH, c, yaw, 7.0f, WitchVfx.SLASH_OVERHEAD);
                    WitchVfx.send(level, WitchVfx.SHOCKWAVE, c, yaw, 7.0f, 6);
                    WitchVfx.send(level, WitchVfx.FLAME_BURST, c, yaw, 5.0f, 0);
                }
            }
            case WAND -> this.tickWand(level, t, target);
            case JUMP -> this.tickJump(level, t);
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
        }
        if (t == RAIN_DELAY || t == RAIN_DELAY + 20 || t == RAIN_DELAY + 40) {
            this.summonMoonbeam(level);
        }
    }

    private void summonMoonbeam(ServerLevel level) {
        BlockPos pos = BlockPos.containing(this.boss.getX(), this.boss.getY() + 4.0, this.boss.getZ());
        MoonbeamEntity beam = MokelsBossfightSaphyraModEntities.MOONBEAM.get().spawn(level, pos, MobSpawnType.MOB_SUMMONED);
        if (beam != null) {
            beam.setDeltaMovement(0.0, 0.0, 0.0);
            AttributeInstance attack = beam.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attack != null) {
                attack.setBaseValue(BossAttributes.skillDamage(this.boss, 1.0));
            }
        }
        this.sound(level, "block.amethyst_block.resonate", SoundSource.HOSTILE, 1.6f, 0.8f);
        WitchVfx.send(level, WitchVfx.MOON_SIGIL, new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5), this.boss.getYRot(), 1.6f, 20);
    }

    private void tickGround(ServerLevel level, int t) {
        Vec3 c = this.boss.position();
        float yaw = this.boss.getYRot();
        if (t == 20 || t == 43 || t == 80) {
            WitchVfx.send(level, WitchVfx.TELEGRAPH_RING, c, yaw, 18.0f, 15);
        }
        if (t == 35 || t == 58 || t == 95) {
            boolean last = t == 95;
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, last ? 0.8f : 1.0f);
            level.sendParticles(MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get(), c.x, c.y, c.z, last ? 90 : 60, 8.0, 0.5, 8.0, 0.01);
            this.hitAround(level, this.sourcelessDamage(level), c, 18.0, last ? 30.0 / 14.0 : 1.0, 40.0);
            WitchVfx.send(level, WitchVfx.SHOCKWAVE, c, yaw, 18.0f, 10);
            WitchVfx.send(level, WitchVfx.FLAME_BURST, c, yaw, last ? 12.0f : 8.0f, 0);
            if (last) {
                WitchVfx.send(level, WitchVfx.SLASH, c, yaw, 8.0f, WitchVfx.SLASH_SPIN);
            }
        }
    }

    private void tickWand(ServerLevel level, int t, LivingEntity target) {
        int local = (t - 1) % WAND_PERIOD + 1;
        if (t > WAND_PERIOD * 3) {
            return;
        }
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
            this.sound(level, "block.amethyst_block.chime", SoundSource.HOSTILE, 2.0f, 0.7f);
            WitchVfx.send(level, WitchVfx.TELEGRAPH_LINE, this.boss.position(), yaw, (float) LANCE_LENGTH, WAND_FIRE - 1);
        }
        if (local == WAND_AIM) {
            this.boss.restartAnimation("wandattack");
        }
        if (local == WAND_FIRE) {
            this.fireLance(level);
        }
    }

    private void fireLance(ServerLevel level) {
        Vec3 c = this.boss.position();
        float yaw = yawOf(this.dirX, this.dirZ);
        this.sound(level, "entity.evoker.cast_spell", SoundSource.HOSTILE, 1.8f, 1.3f);
        this.sound(level, "block.amethyst_block.break", SoundSource.HOSTILE, 1.6f, 0.6f);
        WitchVfx.send(level, WitchVfx.MOON_LANCE, c, yaw, (float) LANCE_LENGTH, 0);
        double ex = c.x + this.dirX * LANCE_LENGTH;
        double ez = c.z + this.dirZ * LANCE_LENGTH;
        AABB box = new AABB(Math.min(c.x, ex), c.y - 1.0, Math.min(c.z, ez), Math.max(c.x, ex), c.y + 3.5, Math.max(c.z, ez)).inflate(LANCE_HALF_WIDTH + 1.0, 0.0, LANCE_HALF_WIDTH + 1.0);
        DamageSource source = this.attackSource();
        float damage = BossAttributes.skillDamage(this.boss, 0.8);
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, WitchCombat::canBeHit)) {
            double rx = e.getX() - c.x;
            double rz = e.getZ() - c.z;
            double along = rx * this.dirX + rz * this.dirZ;
            if (along < -1.0 || along > LANCE_LENGTH) {
                continue;
            }
            double side = Math.abs(rx * this.dirZ - rz * this.dirX) - e.getBbWidth() * 0.5;
            if (side <= LANCE_HALF_WIDTH) {
                this.damage(e, source, damage, 10.0, "WAND_LANCE", LANCE_HALF_WIDTH);
            }
        }
    }

    private void tickJump(ServerLevel level, int t) {
        if (t == JUMP_PLANT) {
            BossCastLock.lock(this.boss, WitchSkill.JUMP.duration - JUMP_PLANT);
            this.boss.setDeltaMovement(new Vec3(0.0, this.boss.getDeltaMovement().y, 0.0));
            this.boss.hurtMarked = true;
            Vec3 landing = this.boss.position().add(this.flatLook().scale(4.0));
            WitchVfx.send(level, WitchVfx.TELEGRAPH_RING, landing, this.boss.getYRot(), 6.0f, JUMP_SLAM - JUMP_PLANT);
            WitchVfx.send(level, WitchVfx.CHARGE, this.boss.position(), this.boss.getYRot(), 2.0f, JUMP_SLAM - JUMP_PLANT);
        }
        if (t == JUMP_SLAM) {
            Vec3 center = this.boss.position().add(this.flatLook().scale(4.0));
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 2.0f, 1.0f);
            level.sendParticles(ParticleTypes.EXPLOSION, center.x, center.y, center.z, 16, 3.0, 0.5, 3.0, 0.01);
            this.hitAround(level, this.sourcelessDamage(level), center, 6.0, 1.0, 20.0);
            WitchVfx.send(level, WitchVfx.SHOCKWAVE, center, this.boss.getYRot(), 6.0f, 6);
            WitchVfx.send(level, WitchVfx.FLAME_BURST, center, this.boss.getYRot(), 4.0f, 0);
            WitchVfx.send(level, WitchVfx.SLASH, this.boss.position(), this.boss.getYRot(), 6.0f, WitchVfx.SLASH_OVERHEAD);
        }
    }

    private void tickSpin(ServerLevel level, int t, LivingEntity target) {
        Vec3 c = this.boss.position();
        if (t == SPIN_START) {
            this.sound(level, "item.trident.riptide_2", SoundSource.HOSTILE, 1.6f, 0.8f);
            WitchVfx.send(level, WitchVfx.WHIRL, c, this.boss.getYRot(), 4.5f, SPIN_END - SPIN_START);
        }
        if (t >= SPIN_START && t < SPIN_END && target != null && target.isAlive()) {
            Vec3 d = new Vec3(target.getX() - c.x, 0.0, target.getZ() - c.z);
            if (d.lengthSqr() > 1.0) {
                d = d.normalize().scale(0.18);
                this.boss.setDeltaMovement(new Vec3(d.x, this.boss.getDeltaMovement().y, d.z));
                this.boss.hurtMarked = true;
            }
        }
        if (t >= SPIN_START + 5 && t <= SPIN_END - 10 && (t - SPIN_START) % 10 == 5) {
            this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 1.6f, 1.3f);
            this.hitAround(level, this.attackSource(), c, 4.5, 0.35, 5.0);
        }
        if (t == SPIN_END) {
            this.sound(level, "entity.player.attack.sweep", SoundSource.NEUTRAL, 2.0f, 0.9f);
            this.sound(level, "entity.generic.explode", SoundSource.NEUTRAL, 1.4f, 1.2f);
            this.hitAround(level, this.attackSource(), c, 6.0, 0.8, 20.0);
            WitchVfx.send(level, WitchVfx.SLASH, c, this.boss.getYRot(), 6.0f, WitchVfx.SLASH_SPIN);
            WitchVfx.send(level, WitchVfx.SHOCKWAVE, c, this.boss.getYRot(), 6.0f, 6);
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
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK));
    }

    private void hitAround(ServerLevel level, DamageSource source, Vec3 center, double radius, double multiplier, double grief) {
        float damage = BossAttributes.skillDamage(this.boss, multiplier);
        double height = Math.max(3.0, radius * 0.4);
        AABB box = new AABB(center.x - radius, center.y - 1.0, center.z - radius, center.x + radius, center.y + height, center.z + radius);
        List<LivingEntity> hits = new ArrayList<>();
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, WitchCombat::canBeHit)) {
            if (horizontalEdgeDistance(e, center.x, center.z) <= radius) {
                hits.add(e);
            }
        }
        String tag = this.skill == null ? "NONE" : this.skill.name();
        for (LivingEntity e : hits) {
            this.damage(e, source, damage, grief, tag, radius);
        }
    }

    private void damage(LivingEntity e, DamageSource source, float damage, double grief, String tag, double radius) {
        if (e instanceof Player) {
            MokelsBossfightSaphyraMod.LOGGER.info("[WitchHitDebug] skill={} tick={} radius={} dist={} damage={}",
                    tag, this.skillTick, radius, String.format("%.2f", Math.sqrt(e.distanceToSqr(this.boss.getX(), e.getY(), this.boss.getZ()))), damage);
        }
        e.hurt(source, damage);
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
        double music = data.getDouble("music");
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
        this.boss.setPhase2Visual(this.phase2 && this.phaseShiftTicks <= PHASE_SHIFT_TICKS - PHASE_BURST_TICK);
    }
}
