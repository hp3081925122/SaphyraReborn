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
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.AreaEffectCloud
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.FlyingMoveControl
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.navigation.FlyingPathNavigation
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.arrow.AbstractArrow
 *  net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.state.BlockState
 *  net.neoforged.network.NetworkHooks
 *  net.neoforged.network.PlayMessages$SpawnEntity
 *  com.geckolib.animatable.GeoEntity
 *  com.geckolib.animatable.GeoAnimatable
 *  com.geckolib.animatable.instance.AnimatableInstanceCache
 *  com.geckolib.animatable.manager.AnimatableManager$ControllerRegistrar
 *  com.geckolib.animation.AnimationController
 *  com.geckolib.animation.AnimationController$State
 *  com.geckolib.animation.state.AnimationTest
 *  com.geckolib.animation.RawAnimation
 *  com.geckolib.animation.object.PlayState
 *  com.geckolib.util.GeckoLibUtil
 */
package com.hp.saphyrareborn.entity;

import com.hp.saphyrareborn.procedures.SaphyraSpawnOnEntityTickUpdateProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;

public class SaphyraSpawnEntity
extends PathfinderMob
implements GeoEntity {
    private static final String[] TRIGGERABLE_ANIMATIONS = {"idle"};
    private AnimationController<?> procedureController;
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(SaphyraSpawnEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(SaphyraSpawnEntity.class, (EntityDataSerializer)EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;

    public SaphyraSpawnEntity(EntityType<SaphyraSpawnEntity> type, Level world) {
        super(type, world);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.moveControl = new FlyingMoveControl((Mob)this, 10, true);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHOOT, false);
        builder.define(TEXTURE, "blank");
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE,texture);
    }

    public String getTexture() {
        return (String)this.entityData.get(TEXTURE);
    }

    protected PathNavigation createNavigation(Level world) {
        return new FlyingPathNavigation((Mob)this, world);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true, false));
        this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, ServerPlayer.class, true, false));
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    public boolean hurtServer(net.minecraft.server.level.ServerLevel serverLevel, DamageSource source, float amount) {
        if (source.is(DamageTypes.IN_FIRE)) {
            return false;
        }
        if (source.getDirectEntity() instanceof AbstractArrow) {
            return false;
        }
        if (source.getDirectEntity() instanceof Player) {
            return false;
        }
        if (source.getDirectEntity() instanceof AbstractThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud) {
            return false;
        }
        if (source.is(DamageTypes.FALL)) {
            return false;
        }
        if (source.is(DamageTypes.CACTUS)) {
            return false;
        }
        if (source.is(DamageTypes.DROWN)) {
            return false;
        }
        if (source.is(DamageTypes.LIGHTNING_BOLT)) {
            return false;
        }
        if (source.is(DamageTypes.EXPLOSION)) {
            return false;
        }
        if (source.is(DamageTypes.TRIDENT)) {
            return false;
        }
        if (source.is(DamageTypes.INDIRECT_MAGIC)) {
            return false;
        }
        if (source.is(DamageTypes.DRAGON_BREATH)) {
            return false;
        }
        if (source.is(DamageTypes.WITHER)) {
            return false;
        }
        if (source.is(DamageTypes.WITHER_SKULL)) {
            return false;
        }
        return super.hurtServer(serverLevel, source, amount);
    }

    public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
    }

    public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
        super.readAdditionalSaveData(compound);
        if (compound.keySet().contains("Texture")) {
            this.setTexture(compound.getStringOr("Texture", ""));
        }
    }

    public void tick() {
        super.tick();
        SaphyraSpawnOnEntityTickUpdateProcedure.execute((LevelAccessor)this.level(), (Entity)this);
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

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
        this.setNoGravity(true);
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.0);
        builder = builder.add(Attributes.MAX_HEALTH, 1000.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 10.0);
        builder = builder.add(Attributes.FLYING_SPEED, 0.0);
        return builder;
    }

    private PlayState movementPredicate(AnimationTest event) {
        if (!this.isProcedureAnimating()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationTest event) {
        return com.hp.saphyrareborn.gecko.GeckoAnim.procedure(event, this.isDeadOrDying());
    }

    private boolean isProcedureAnimating() {
        return com.hp.saphyrareborn.gecko.GeckoAnim.procedureActive(this.procedureController);
    }

    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            if (this.level() instanceof net.minecraft.server.level.ServerLevel _xpLevel) this.dropExperience(_xpLevel, this.getLastAttacker());
            this.remove(Entity.RemovalReason.KILLED);
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
        data.add(new AnimationController[]{new AnimationController<>("movement", 2, this::movementPredicate)});
        AnimationController<?> procedure = new AnimationController<>("procedure", 2, this::procedurePredicate).receiveTriggeredAnimations();
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
























