/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerPlayer
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

import com.hp.saphyrareborn.gecko.GeckoAnim;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
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
import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;

public class PuppetOfYivaNemorEntity
extends Monster
implements GeoEntity {
    private static final String[] TRIGGERABLE_ANIMATIONS = {"idle", "walk", "attackfirst", "attacksecond", "attackthird", "attackfourth", "attackfifth", "death", "pose", "phaseshift"};
    private AnimationController<?> procedureController;
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(PuppetOfYivaNemorEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(PuppetOfYivaNemorEntity.class, (EntityDataSerializer)EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Boolean> DATA_animations = SynchedEntityData.defineId(PuppetOfYivaNemorEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> PHASE2 = SynchedEntityData.defineId(PuppetOfYivaNemorEntity.class, EntityDataSerializers.BOOLEAN);
    private final com.hp.saphyrareborn.boss.puppet.PuppetCombat combat = new com.hp.saphyrareborn.boss.puppet.PuppetCombat(this);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getUUID(), this.getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.NOTCHED_6);

    public PuppetOfYivaNemorEntity(EntityType<PuppetOfYivaNemorEntity> type, Level world) {
        super(type, world);
        this.xpReward = 450;
        this.setNoAi(false);
        this.setPersistenceRequired();
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHOOT, false);
        builder.define(TEXTURE, "puppettexture");
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
        this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, ServerPlayer.class, true, true));
        this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true, true));
        this.goalSelector.addGoal(3, (Goal)new MeleeAttackGoal((PathfinderMob)this, 1.0, true){

            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(4, (Goal)new RandomStrollGoal((PathfinderMob)this, 1.0));
        this.targetSelector.addGoal(5, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]).setAlertOthers(new Class[0]));
        this.goalSelector.addGoal(6, (Goal)new RandomLookAroundGoal((Mob)this));
        this.goalSelector.addGoal(7, (Goal)new FloatGoal((Mob)this));
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    public boolean hurtServer(net.minecraft.server.level.ServerLevel serverLevel, DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL)) {
            return false;
        }
        if (!this.level().isClientSide() && this.combat.isInvulnerable() && !source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }
        return super.hurtServer(serverLevel, source, amount);
    }

    public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
        compound.putBoolean("Dataanimations", ((Boolean)this.entityData.get(DATA_animations)).booleanValue());
        compound.store("PuppetCombat", net.minecraft.nbt.CompoundTag.CODEC, this.combat.save());
    }

    public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
        super.readAdditionalSaveData(compound);
        if (compound.keySet().contains("Texture")) {
            this.setTexture(compound.getStringOr("Texture", ""));
        }
        if (compound.keySet().contains("Dataanimations")) {
            this.entityData.set(DATA_animations,compound.getBooleanOr("Dataanimations", false));
        }
        compound.read("PuppetCombat", net.minecraft.nbt.CompoundTag.CODEC).ifPresent(this.combat::load);
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
                PuppetOfYivaNemorEntity.this.yBodyRot = PuppetOfYivaNemorEntity.this.getYRot();
            }
        };
    }

    public com.hp.saphyrareborn.boss.puppet.PuppetCombat getCombat() {
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
            this.level().addParticle(com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get(), this.getX() + (random.nextDouble() - 0.5) * w * 2.0, this.getY() + random.nextDouble() * this.getBbHeight(), this.getZ() + (random.nextDouble() - 0.5) * w * 2.0, 0.0, 0.02 + random.nextDouble() * 0.02, 0.0);
        }
        if (random.nextInt(4) == 0) {
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.SMOKE, this.getX() + (random.nextDouble() - 0.5) * w * 2.0, this.getY() + 0.5 + random.nextDouble() * this.getBbHeight(), this.getZ() + (random.nextDouble() - 0.5) * w * 2.0, 0.0, 0.03, 0.0);
        }
        if (random.nextInt(9) == 0) {
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.LAVA, this.getX() + (random.nextDouble() - 0.5) * w * 2.0, this.getY() + 0.3 + random.nextDouble() * this.getBbHeight() * 0.6, this.getZ() + (random.nextDouble() - 0.5) * w * 2.0, 0.0, 0.0, 0.0);
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
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.27);
        builder = builder.add(Attributes.MAX_HEALTH, 300.0);
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

    public boolean isProcedureAnimating() {
        return com.hp.saphyrareborn.gecko.GeckoAnim.procedureActive(this.procedureController);
    }

    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 80) {
            // 死亡时掉落伊瓦的傀儡的记忆，写法同无名守门人
            if (this.level() instanceof net.minecraft.server.level.ServerLevel level) {
                level.sendParticles((net.minecraft.core.particles.SimpleParticleType)com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get(), this.getX(), this.getY(), this.getZ(), 400, 0.6, 1.7, 0.6, 0.01);
                net.minecraft.world.entity.item.ItemEntity drop = new net.minecraft.world.entity.item.ItemEntity(level, this.getX(), this.getY(), this.getZ(), new net.minecraft.world.item.ItemStack(com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems.PUPPET_OF_YIVA_REMEMBRANCE.get()));
                drop.setPickUpDelay(2);
                drop.setUnlimitedLifetime();
                level.addFreshEntity(drop);
                com.hp.saphyrareborn.procedures.LinaraClockTowerProcedure.onPuppetDefeated(level, this);
            }
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
























