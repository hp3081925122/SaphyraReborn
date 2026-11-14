/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.BossEvent$BossBarOverlay
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.EntitySpawnReason
 *  net.minecraft.world.entity.MobType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.SpawnGroupData
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
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.neoforged.network.NetworkHooks
 *  net.neoforged.network.PlayMessages$SpawnEntity
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
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

import com.hp.saphyrareborn.gecko.GeckoAnim;

import javax.annotation.Nullable;
import com.hp.saphyrareborn.entity.NamelessEntity;
import com.hp.saphyrareborn.procedures.WitchbossDeathTimeIsReachedProcedure;
import com.hp.saphyrareborn.procedures.WitchbossOnInitialEntitySpawnProcedure;
import com.hp.saphyrareborn.procedures.WitchbossStopSoundProcedure;
import com.hp.saphyrareborn.procedures.WitchbossThisEntityKillsAnotherOneProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;

public class WitchbossEntity
extends Monster
implements GeoEntity {
    private static final String[] TRIGGERABLE_ANIMATIONS = {"spawn", "idle", "walk", "slashattack", "clawattack", "attackclaw2", "jumpattack", "wandattack", "rainattack", "axeattack", "death", "spawncreative", "pose", "poster2", "anotherpose", "spinattack", "attackground", "sorry", "phaseshift"};
    private AnimationController<?> procedureController;
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(WitchbossEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> PHASE2 = SynchedEntityData.defineId(WitchbossEntity.class, EntityDataSerializers.BOOLEAN);
    private final com.hp.saphyrareborn.boss.witch.WitchCombat combat = new com.hp.saphyrareborn.boss.witch.WitchCombat(this);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(WitchbossEntity.class, (EntityDataSerializer)EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getUUID(), this.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.NOTCHED_10);

    public WitchbossEntity(EntityType<WitchbossEntity> type, Level world) {
        super(type, world);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setPersistenceRequired();
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHOOT, false);
        builder.define(TEXTURE, "witchbosssittingtextureanimated");
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
        this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, NamelessEntity.class, true, true));
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

    public SoundEvent getHurtSound(DamageSource ds) {
        return BuiltInRegistries.SOUND_EVENT.get(Identifier.withDefaultNamespace("entity.generic.hurt")).map(net.minecraft.core.Holder::value).orElseThrow();
    }

    public SoundEvent getDeathSound() {
        return BuiltInRegistries.SOUND_EVENT.get(Identifier.withDefaultNamespace("entity.generic.death")).map(net.minecraft.core.Holder::value).orElseThrow();
    }

    public boolean hurtServer(net.minecraft.server.level.ServerLevel serverLevel, DamageSource source, float amount) {
        if (!this.level().isClientSide() && this.combat.isInvulnerable() && !source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }
        return super.hurtServer(serverLevel, source, amount);
    }

    public void die(DamageSource source) {
        super.die(source);
        WitchbossStopSoundProcedure.execute((LevelAccessor)this.level(), (Entity)this, source.getEntity());
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData livingdata) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
        WitchbossOnInitialEntitySpawnProcedure.execute();
        return retval;
    }

    public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
        compound.store("WitchCombat", net.minecraft.nbt.CompoundTag.CODEC, this.combat.save());
    }

    public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
        super.readAdditionalSaveData(compound);
        if (compound.keySet().contains("Texture")) {
            this.setTexture(compound.getStringOr("Texture", ""));
        }
        compound.read("WitchCombat", net.minecraft.nbt.CompoundTag.CODEC).ifPresent(this.combat::load);
    }

    public void awardKillScore(Entity entity, DamageSource damageSource) {
        super.awardKillScore(entity, damageSource);
        WitchbossThisEntityKillsAnotherOneProcedure.execute((LevelAccessor)this.level(), (Entity)this);
    }

    @Override
    public net.minecraft.world.entity.EntityDimensions getDefaultDimensions(net.minecraft.world.entity.Pose pose) {
        return super.getDefaultDimensions(pose).scale(1.1f);
    }

    @Override
    public boolean doHurtTarget(net.minecraft.server.level.ServerLevel serverLevel, Entity target) {
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
    protected net.minecraft.world.entity.ai.control.BodyRotationControl createBodyControl() {
        return new net.minecraft.world.entity.ai.control.BodyRotationControl(this) {
            @Override
            public void clientTick() {
                WitchbossEntity.this.yBodyRot = WitchbossEntity.this.getYRot();
            }
        };
    }

    public com.hp.saphyrareborn.boss.witch.WitchCombat getCombat() {
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
        double w = this.getBbWidth() * 0.7;
        if (this.tickCount % 3 == 0) {
            this.level().addParticle(com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes.GHOSTLY_FLAME_PARTICLE.get(), this.getX() + (random.nextDouble() - 0.5) * w * 2.0, this.getY() + random.nextDouble() * this.getBbHeight(), this.getZ() + (random.nextDouble() - 0.5) * w * 2.0, 0.0, 0.02 + random.nextDouble() * 0.02, 0.0);
        }
        if (random.nextInt(5) == 0) {
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.WITCH, this.getX() + (random.nextDouble() - 0.5) * w * 3.0, this.getY() + 0.5 + random.nextDouble() * this.getBbHeight(), this.getZ() + (random.nextDouble() - 0.5) * w * 3.0, 0.0, 0.0, 0.0);
        }
        if (random.nextInt(7) == 0) {
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.END_ROD, this.getX() + (random.nextDouble() - 0.5) * w * 2.0, this.getY() + this.getBbHeight() + 0.3, this.getZ() + (random.nextDouble() - 0.5) * w * 2.0, 0.0, -0.02, 0.0);
        }
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

    public void customServerAiStep(net.minecraft.server.level.ServerLevel serverLevel) {
        super.customServerAiStep(serverLevel);
        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.24);
        builder = builder.add(Attributes.MAX_HEALTH, 600.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 14.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0);
        builder = builder.add(Attributes.STEP_HEIGHT, 1.8);
        return builder;
    }

    private PlayState movementPredicate(AnimationTest event) {
        if (this.isDeadOrDying()) {
            return event.setAndContinue(RawAnimation.begin().thenPlay("death"));
        }
        if (!this.isProcedureAnimating()) {
            if (event.isMoving() || !(GeckoAnim.limb(event) > -0.15f) || !(GeckoAnim.limb(event) < 0.15f)) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
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
        if (this.deathTime == 180) {
            if (this.level() instanceof net.minecraft.server.level.ServerLevel _xpLevel) this.dropExperience(_xpLevel, this.getLastAttacker());
            this.remove(Entity.RemovalReason.KILLED);
            WitchbossDeathTimeIsReachedProcedure.execute((LevelAccessor)this.level(), (Entity)this);
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
























