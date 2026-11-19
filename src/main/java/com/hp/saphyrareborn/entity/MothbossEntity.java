package com.hp.saphyrareborn.entity;

import net.minecraft.world.entity.MobType;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.procedures.MothbossDeathTimeIsReachedProcedure;
import com.hp.saphyrareborn.procedures.MothbossEntityDiesProcedure;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MothbossEntity
extends PathfinderMob
implements GeoEntity {
    private static final String[] TRIGGERABLE_ANIMATIONS = {"pose", "pose2", "flying", "pose3", "attacksmite", "idle", "walk", "aggro", "attackswordonce", "attackground", "attacksky", "attacktwirl", "attackback", "block", "attackstab", "attacktriple", "death", "phaseshift"};
    private AnimationController<?> procedureController;
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(MothbossEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> PHASE2 = SynchedEntityData.defineId(MothbossEntity.class, EntityDataSerializers.BOOLEAN);
    private final com.hp.saphyrareborn.boss.moth.MothCombat combat = new com.hp.saphyrareborn.boss.moth.MothCombat(this);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(MothbossEntity.class, (EntityDataSerializer)EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private boolean swinging;
    private boolean lastloop;
    private long lastSwing;
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.NOTCHED_12);

    public MothbossEntity(EntityType<MothbossEntity> type, Level world) {
        super(type, world);
        this.xpReward = 1000;
        this.setNoAi(false);
        
        this.setPersistenceRequired();
        this.setMaxUpStep((float) (1.2));
    }

    @Override
    protected net.minecraft.world.entity.ai.control.BodyRotationControl createBodyControl() {
        return new net.minecraft.world.entity.ai.control.BodyRotationControl(this) {
            @Override
            public void clientTick() {
                MothbossEntity.this.yBodyRot = MothbossEntity.this.getYRot();
            }
        };
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PHASE2, false);
        this.entityData.define(SHOOT, false);
        this.entityData.define(TEXTURE, "saphyramothbosstexture");
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
        this.goalSelector.addGoal(3, (Goal)new MeleeAttackGoal(this, 1.0, true){

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

    public SoundEvent getHurtSound(DamageSource ds) {
        return (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("entity.generic.death"));
    }

    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL)) {
            return false;
        }
        if (this.level().isClientSide()) {
            return super.hurt(source, amount);
        }
        if (this.combat.isInvulnerable() && !source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }
        if (com.hp.saphyrareborn.boss.BossCastLock.blocksDamage(this, source)) {
            this.combat.onBlockedHit(source);
            return false;
        }
        return super.hurt(source, amount);
    }

    public void die(DamageSource source) {
        super.die(source);
        MothbossEntityDiesProcedure.execute((LevelAccessor)this.level(), this.getX(), this.getY(), this.getZ(), (Entity)this);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
        compound.put("MothCombat", this.combat.save());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture")) {
            this.setTexture(compound.getString("Texture"));
        }
        if (compound.contains("MothCombat")) {
            this.combat.load(compound.getCompound("MothCombat"));
        }
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
    protected float getDamageAfterMagicAbsorb(DamageSource source, float amount) {
        if (com.hp.saphyrareborn.boss.BossCastLock.blocksDamage(this, source)) {
            return 0.0f;
        }
        return super.getDamageAfterMagicAbsorb(source, amount);
    }

    public com.hp.saphyrareborn.boss.moth.MothCombat getCombat() {
        return this.combat;
    }

    public void restartAnimation(String animation) {
        this.stopTriggeredAnimation("procedure", null);
        this.triggerAnim("procedure", animation);
    }

    public void stopProcedureAnimation() {
        this.stopTriggeredAnimation("procedure", null);
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
        if (random.nextInt(10) == 0) {
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK, this.getX() + (random.nextDouble() - 0.5) * w * 3.0, this.getY() + 0.5 + random.nextDouble() * this.getBbHeight(), this.getZ() + (random.nextDouble() - 0.5) * w * 3.0, 0.0, 0.0, 0.0);
        }
        if (random.nextInt(6) == 0) {
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.END_ROD, this.getX() + (random.nextDouble() - 0.5) * w * 2.0, this.getY() + 0.2, this.getZ() + (random.nextDouble() - 0.5) * w * 2.0, 0.0, 0.03, 0.0);
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

    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
    }

    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26);
        builder = builder.add(Attributes.MAX_HEALTH, 600.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 14.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0);
        builder = builder.add(net.minecraftforge.common.ForgeMod.STEP_HEIGHT_ADDITION.get(), (10.0) - 0.6);
        return builder;
    }

    private PlayState movementPredicate(AnimationState event) {
        if (this.isDeadOrDying()) {
            return event.setAndContinue(RawAnimation.begin().thenPlay("death"));
        }
        if (!this.isProcedureAnimating()) {
            if (!(!event.isMoving() && event.getLimbSwingAmount() > -0.15f && event.getLimbSwingAmount() < 0.15f || this.isAggressive())) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            if (this.isAggressive() && event.isMoving()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("aggro"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationState event) {
        return event.getController().isPlayingTriggeredAnimation() && !this.isDeadOrDying() ? PlayState.CONTINUE : PlayState.STOP;
    }

    public boolean isProcedureAnimating() {
        return this.procedureController != null && this.procedureController.isPlayingTriggeredAnimation();
    }

    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 400) {
            this.dropExperience();
            this.remove(Entity.RemovalReason.KILLED);
            MothbossDeathTimeIsReachedProcedure.execute((LevelAccessor)this.level(), (Entity)this);
        }
    }

    public void setAnimation(String animation) {
        if ("empty".equals(animation)) {
            this.stopTriggeredAnimation("procedure", null);
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

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }
}
