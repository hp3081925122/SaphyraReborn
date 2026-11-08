/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.BossEvent$BossBarOverlay
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.RandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.state.BlockState
 *  net.neoforged.network.NetworkHooks
 *  net.neoforged.network.PlayMessages$SpawnEntity
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.AnimationController$State
 *  software.bernie.geckolib.animation.AnimationState
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.animation.PlayState
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.hp.saphyrareborn.entity;

import com.hp.saphyrareborn.entity.WitchbossEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.procedures.NamelessDeathTimeIsReachedProcedure;
import com.hp.saphyrareborn.procedures.NamelessEntityDiesForRealProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class NamelessEntity
extends Monster
implements GeoEntity {
    private static final String[] TRIGGERABLE_ANIMATIONS = {"idle", "pose", "walk", "attackcharge", "attackdouble", "attackshield", "attackshieldslash", "attackbr\u00fcllen", "attackonce", "attacksuper", "death", "sit", "attackground", "attackrot", "attackstomp", "attackfloat", "stun", "spawn", "idlespawn", "stagger", "phaseshift"};
    private AnimationController<?> procedureController;
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(NamelessEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(NamelessEntity.class, (EntityDataSerializer)EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Boolean> DATA_animations = SynchedEntityData.defineId(NamelessEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> PHASE2 = SynchedEntityData.defineId(NamelessEntity.class, EntityDataSerializers.BOOLEAN);
    private final com.hp.saphyrareborn.boss.nameless.NamelessCombat combat = new com.hp.saphyrareborn.boss.nameless.NamelessCombat(this);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_6);

    public NamelessEntity(EntityType<NamelessEntity> type, Level world) {
        super(type, world);
        this.xpReward = 600;
        this.setNoAi(false);
    }

    @Override
    protected net.minecraft.world.entity.ai.control.BodyRotationControl createBodyControl() {
        return new net.minecraft.world.entity.ai.control.BodyRotationControl(this) {
            @Override
            public void clientTick() {
                NamelessEntity.this.yBodyRot = NamelessEntity.this.getYRot();
            }
        };
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHOOT, false);
        builder.define(TEXTURE, "knightbosstexture");
        builder.define(DATA_animations, true);
        builder.define(PHASE2, false);
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE,texture);
    }

    public String getTexture() {
        return (String)this.entityData.get(TEXTURE);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, WitchbossEntity.class, true, true));
        this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, ServerPlayer.class, true, true));
        this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true, true));
        this.goalSelector.addGoal(4, (Goal)new MeleeAttackGoal((PathfinderMob)this, 1.0, true){

            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(5, (Goal)new RandomStrollGoal((PathfinderMob)this, 1.0));
        this.targetSelector.addGoal(6, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]).setAlertOthers(new Class[0]));
        this.goalSelector.addGoal(7, (Goal)new RandomLookAroundGoal((Mob)this));
        this.goalSelector.addGoal(8, (Goal)new FloatGoal((Mob)this));
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound((SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.wither_skeleton.step")), 0.15f, 1.0f);
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.skeleton.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.wither_skeleton.death"));
    }

    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL)) {
            return false;
        }
        if (this.level().isClientSide()) {
            return super.hurt(source, amount);
        }
        if (this.combat.isInvulnerable() && !source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            this.logHurt(source, amount, false, "phase_shift");
            return false;
        }
        if (com.hp.saphyrareborn.boss.BossCastLock.blocksDamage(this, source)) {
            this.combat.onBlockedHit(source, amount);
            this.logHurt(source, amount, false, "shield");
            return false;
        }
        int cooldown = this.invulnerableTime;
        float before = this.getHealth();
        boolean result = super.hurt(source, amount);
        this.logHurt(source, amount, result, cooldown > 10 ? "vanilla_cooldown(" + cooldown + ",lastHurt=" + this.lastHurt + ",dealt=" + (before - this.getHealth()) + ")" : "normal(dealt=" + (before - this.getHealth()) + ")");
        return result;
    }

    private void logHurt(DamageSource source, float amount, boolean result, String reason) {
        if (source.getEntity() instanceof Player) {
            com.hp.saphyrareborn.MokelsBossfightSaphyraMod.LOGGER.info("[NamelessHurtDebug] tick={} type={} amount={} result={} reason={} health={}",
                    this.tickCount, source.getMsgId(), amount, result, reason, this.getHealth());
        }
    }

    public void die(DamageSource source) {
        super.die(source);
        NamelessEntityDiesForRealProcedure.execute((LevelAccessor)this.level(), this.getX(), this.getY(), this.getZ(), (Entity)this);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
        compound.putBoolean("Dataanimations", ((Boolean)this.entityData.get(DATA_animations)).booleanValue());
        compound.put("NamelessCombat", this.combat.save());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture")) {
            this.setTexture(compound.getString("Texture"));
        }
        if (compound.contains("Dataanimations")) {
            this.entityData.set(DATA_animations,compound.getBoolean("Dataanimations"));
        }
        if (compound.contains("NamelessCombat")) {
            this.combat.load(compound.getCompound("NamelessCombat"));
        }
    }

    @Override
    public net.minecraft.world.entity.EntityDimensions getDefaultDimensions(net.minecraft.world.entity.Pose pose) {
        return super.getDefaultDimensions(pose).scale(1.1f);
    }

    @Override
    protected float getDamageAfterMagicAbsorb(DamageSource source, float amount) {
        if (com.hp.saphyrareborn.boss.BossCastLock.blocksDamage(this, source)) {
            return 0.0f;
        }
        return super.getDamageAfterMagicAbsorb(source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        return false;
    }

    public void tick() {
        super.tick();
        com.hp.saphyrareborn.boss.BossCastLock.tick(this);
        if (this.level().isClientSide()) {
            this.tickPhase2Aura();
        } else {
            this.combat.tick();
        }
        this.refreshDimensions();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    protected void doPush(Entity entityIn) {
    }

    protected void pushEntities() {
    }


    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.24);
        builder = builder.add(Attributes.MAX_HEALTH, 450.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 14.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0);
        builder = builder.add(Attributes.STEP_HEIGHT, 1.8);
        return builder;
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.isDeadOrDying()) {
            return event.setAndContinue(RawAnimation.begin().thenPlay("death"));
        }
        if (!this.isProcedureAnimating()) {
            if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15f) || !(event.getLimbSwingAmount() < 0.15f)) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationState event) {
        return event.getController().isPlayingTriggeredAnimation() && !this.isDeadOrDying() ? PlayState.CONTINUE : PlayState.STOP;
    }

    private boolean isProcedureAnimating() {
        return this.procedureController != null && this.procedureController.isPlayingTriggeredAnimation();
    }

    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 100) {
            this.remove(Entity.RemovalReason.KILLED);
            this.discard();
            NamelessDeathTimeIsReachedProcedure.execute((LevelAccessor)this.level(), this.getX(), this.getY(), this.getZ());
        }
    }

    public com.hp.saphyrareborn.boss.nameless.NamelessCombat getCombat() {
        return this.combat;
    }

    public void restartAnimation(String animation) {
        this.stopTriggeredAnim("procedure", null);
        this.triggerAnim("procedure", animation);
    }

    public void stopProcedureAnimation() {
        this.stopTriggeredAnim("procedure", null);
    }

    public void setPhase2Visual(boolean value) {
        this.entityData.set(PHASE2, value);
    }

    public boolean isPhase2Visual() {
        return this.entityData.get(PHASE2);
    }

    private void tickPhase2Aura() {
        if (!this.isPhase2Visual() || !this.isAlive()) {
            return;
        }
        net.minecraft.util.RandomSource random = this.getRandom();
        double w = this.getBbWidth() * 0.6;
        if (this.tickCount % 2 == 0) {
            this.level().addParticle(com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get(), this.getX() + (random.nextDouble() - 0.5) * w * 2.0, this.getY() + random.nextDouble() * this.getBbHeight(), this.getZ() + (random.nextDouble() - 0.5) * w * 2.0, 0.0, 0.02 + random.nextDouble() * 0.03, 0.0);
        }
        if (random.nextInt(8) == 0) {
            this.level().addParticle(new net.minecraft.core.particles.DustParticleOptions(new org.joml.Vector3f(0.45f, 0.03f, 0.05f), 1.4f), this.getX() + (random.nextDouble() - 0.5) * w * 2.0, this.getY() + 0.1, this.getZ() + (random.nextDouble() - 0.5) * w * 2.0, 0.0, 0.0, 0.0);
        }
    }

    public void setAnimation(String animation) {
        if ("empty".equals(animation)) {
            this.stopTriggeredAnim("procedure", null);
        } else {
            this.triggerAnim("procedure", animation);
        }
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "movement", 2, this::movementPredicate)});
        AnimationController<?> procedure = new AnimationController<>(this, "procedure", 2, this::procedurePredicate).receiveTriggeredAnimations();
        for (String name : TRIGGERABLE_ANIMATIONS) {
            procedure.triggerableAnim(name, RawAnimation.begin().thenPlay(name));
        }
        this.procedureController = procedure;
        data.add(procedure);
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
























