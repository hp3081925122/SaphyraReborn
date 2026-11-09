/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.entity.projectile.ItemSupplier
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.network.NetworkHooks
 *  net.neoforged.network.PlayMessages$SpawnEntity
 */
package com.hp.saphyrareborn.entity;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT, _interface=ItemSupplier.class)
public class MirrorBladeProjectileEntity
extends AbstractArrow
implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack((ItemLike)Blocks.AIR);

    public MirrorBladeProjectileEntity(EntityType<? extends MirrorBladeProjectileEntity> type, Level world) {
        super(type, world);
    }

    public MirrorBladeProjectileEntity(EntityType<? extends MirrorBladeProjectileEntity> type, double x, double y, double z, Level world) {
        super(type, world);
        this.setPos(x, y, z);
    }

    public MirrorBladeProjectileEntity(EntityType<? extends MirrorBladeProjectileEntity> type, LivingEntity entity, Level world) {
        super(type, world);
        this.setOwner(entity);
        this.setPos(entity.getX(), entity.getEyeY() - 0.1F, entity.getZ());
    }

    @OnlyIn(value=Dist.CLIENT)
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    protected ItemStack getDefaultPickupItem() {
        return PROJECTILE_ITEM;
    }

    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    private static final int BURST_AGE = 20;
    private static final double BURST_RADIUS = 3.0;
    private float burstDamage;
    private int mirrorAge;

    public void setBurstDamage(float damage) {
        this.burstDamage = damage;
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    @Override
    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    @Override
    public void tick() {
        this.setNoGravity(true);
        super.tick();
        if (!(this.level() instanceof net.minecraft.server.level.ServerLevel level) || this.isRemoved()) {
            return;
        }
        if (this.inGround) {
            this.discard();
            return;
        }
        if (this.mirrorAge % 2 == 0) {
            level.sendParticles(com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes.GOLDEN_PARTICLE.get(), this.getX(), this.getY(), this.getZ(), 1, 0.05, 0.05, 0.05, 0.02);
        }
        if (++this.mirrorAge < BURST_AGE) {
            return;
        }
        if (this.getOwner() instanceof net.minecraft.world.entity.player.Player player && this.burstDamage > 0.0f) {
            net.minecraft.world.damagesource.DamageSource source = player.damageSources().playerAttack(player);
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(BURST_RADIUS), e -> com.hp.saphyrareborn.weapon.WeaponAbilities.canHit(player, e))) {
                if (e.distanceToSqr(this) <= BURST_RADIUS * BURST_RADIUS + e.getBbWidth()) {
                    e.hurt(source, this.burstDamage);
                }
            }
            level.playSound(null, this.getX(), this.getY(), this.getZ(), net.minecraft.sounds.SoundEvents.AMETHYST_BLOCK_BREAK, net.minecraft.sounds.SoundSource.PLAYERS, 1.2f, 1.5f);
            com.hp.saphyrareborn.weapon.WeaponFx.send(level, com.hp.saphyrareborn.weapon.WeaponFx.MIRROR_BURST, com.hp.saphyrareborn.weapon.WeaponStyle.MIRROR_BLADE, this.position(), this.getYRot(), (float) BURST_RADIUS, 0);
        }
        this.discard();
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("BurstDamage", this.burstDamage);
        compound.putInt("MirrorAge", this.mirrorAge);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.burstDamage = compound.getFloat("BurstDamage");
        this.mirrorAge = compound.getInt("MirrorAge");
    }

    public static MirrorBladeProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source) {
        return MirrorBladeProjectileEntity.shoot(world, entity, source, 1.0f, 5.0, 1);
    }

    public static MirrorBladeProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
        return MirrorBladeProjectileEntity.shoot(world, entity, source, pullingPower * 1.0f, 5.0, 1);
    }

    public static MirrorBladeProjectileEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        MirrorBladeProjectileEntity entityarrow = new MirrorBladeProjectileEntity((EntityType<? extends MirrorBladeProjectileEntity>)((EntityType)MokelsBossfightSaphyraModEntities.MIRROR_BLADE_PROJECTILE.get()), entity, world);
        entityarrow.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2.0f, 0.0f);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        world.addFreshEntity((Entity)entityarrow);
        return entityarrow;
    }

    public static MirrorBladeProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
        MirrorBladeProjectileEntity entityarrow = new MirrorBladeProjectileEntity((EntityType<? extends MirrorBladeProjectileEntity>)((EntityType)MokelsBossfightSaphyraModEntities.MIRROR_BLADE_PROJECTILE.get()), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + (double)target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * (double)0.2f, dz, 2.0f, 12.0f);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(5.0);
        entityarrow.setCritArrow(false);
        entity.level().addFreshEntity((Entity)entityarrow);
        return entityarrow;
    }
}




























