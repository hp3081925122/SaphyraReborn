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
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
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
 *  net.minecraft.world.entity.ai.goal.BreakDoorGoal
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.RandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
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

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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

public class ScorchedEntity
extends PathfinderMob
implements GeoEntity {
    private static final String[] TRIGGERABLE_ANIMATIONS = {"pray", "pray2", "pray3", "pray4", "attack", "idle", "aggro", "walk"};
    private AnimationController<?> procedureController;
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ScorchedEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ScorchedEntity.class, (EntityDataSerializer)EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;

    public ScorchedEntity(EntityType<ScorchedEntity> type, Level world) {
        super(type, world);
        this.xpReward = 20;
        this.setNoAi(false);
        this.setNoGravity(true);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHOOT, false);
        builder.define(TEXTURE, "corpsetexture");
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE,texture);
    }

    public String getTexture() {
        return (String)this.entityData.get(TEXTURE);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, ServerPlayer.class, true, true));
        this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true, true));
        this.goalSelector.addGoal(3, (Goal)new MeleeAttackGoal(this, 1.2, true){

            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(4, (Goal)new RandomStrollGoal((PathfinderMob)this, 1.0));
        this.targetSelector.addGoal(5, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]).setAlertOthers(new Class[0]));
        this.goalSelector.addGoal(6, (Goal)new RandomLookAroundGoal((Mob)this));
        this.goalSelector.addGoal(7, (Goal)new FloatGoal((Mob)this));
        this.goalSelector.addGoal(8, (Goal)new BreakDoorGoal((Mob)this, e -> true));
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    public SoundEvent getAmbientSound() {
        return (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:idleinfested"));
    }

    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound((SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.withDefaultNamespace("entity.husk.step")), 0.15f, 1.0f);
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:infested5"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:infested6"));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture")) {
            this.setTexture(compound.getString("Texture"));
        }
    }

    public void tick() {
        super.tick();
        this.refreshDimensions();
    }

    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.15);
        builder = builder.add(Attributes.MAX_HEALTH, 60.0);
        builder = builder.add(Attributes.ARMOR, 2.5);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 8.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
        builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
        return builder;
    }

    private PlayState movementPredicate(AnimationState event) {
        if (!this.isProcedureAnimating()) {
            if (!(!event.isMoving() && event.getLimbSwingAmount() > -0.15f && event.getLimbSwingAmount() < 0.15f || this.isSprinting())) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            if (this.isSprinting() && event.isMoving()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("aggro"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState attackingPredicate(AnimationState event) {
        double d1 = this.getX() - this.xo;
        double d0 = this.getZ() - this.zo;
        float velocity = (float)Math.sqrt(d1 * d1 + d0 * d0);
        if (this.getAttackAnim(event.getPartialTick()) > 0.0f && !this.swinging) {
            this.swinging = true;
            this.lastSwing = this.level().getGameTime();
        }
        if (this.swinging && this.lastSwing + 7L <= this.level().getGameTime()) {
            this.swinging = false;
        }
        if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().forceAnimationReset();
            return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
        }
        return PlayState.CONTINUE;
    }

    private PlayState procedurePredicate(AnimationState event) {
        return event.getController().isPlayingTriggeredAnimation() && !this.isDeadOrDying() ? PlayState.CONTINUE : PlayState.STOP;
    }

    private boolean isProcedureAnimating() {
        return this.procedureController != null && this.procedureController.isPlayingTriggeredAnimation();
    }

    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove(Entity.RemovalReason.KILLED);
            this.discard();
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
        data.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "attacking", 2, this::attackingPredicate)});
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
























