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
 *  net.minecraft.world.phys.EntityHitResult
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.network.NetworkHooks
 *  net.neoforged.network.PlayMessages$SpawnEntity
 */
package com.hp.saphyrareborn.entity;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.procedures.DisgustProjectileFallProjectileHitsLivingEntityProcedure;
import com.hp.saphyrareborn.procedures.DisgustProjectileFallWhileProjectileFlyingTickProcedure;
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
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT, _interface=ItemSupplier.class)
public class DisgustProjectileFallEntity
extends AbstractArrow
implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack((ItemLike)Blocks.AIR);

    public DisgustProjectileFallEntity(EntityType<? extends DisgustProjectileFallEntity> type, Level world) {
        super(type, world);
    }

    public DisgustProjectileFallEntity(EntityType<? extends DisgustProjectileFallEntity> type, double x, double y, double z, Level world) {
        super(type, world);
        this.setPos(x, y, z);
    }

    public DisgustProjectileFallEntity(EntityType<? extends DisgustProjectileFallEntity> type, LivingEntity entity, Level world) {
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

    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        DisgustProjectileFallProjectileHitsLivingEntityProcedure.execute(entityHitResult.getEntity());
    }

    public void tick() {
        super.tick();
        DisgustProjectileFallWhileProjectileFlyingTickProcedure.execute((LevelAccessor)this.level(), (Entity)this);
        if (this.inGround) {
            this.discard();
        }
    }

    public static DisgustProjectileFallEntity shoot(Level world, LivingEntity entity, RandomSource source) {
        return DisgustProjectileFallEntity.shoot(world, entity, source, 1.0f, 5.0, 1);
    }

    public static DisgustProjectileFallEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
        return DisgustProjectileFallEntity.shoot(world, entity, source, pullingPower * 1.0f, 5.0, 1);
    }

    public static DisgustProjectileFallEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        DisgustProjectileFallEntity entityarrow = new DisgustProjectileFallEntity((EntityType<? extends DisgustProjectileFallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.DISGUST_PROJECTILE_FALL.get()), entity, world);
        entityarrow.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2.0f, 0.0f);
        entityarrow.setNoPhysics(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        world.addFreshEntity((Entity)entityarrow);
        return entityarrow;
    }

    public static DisgustProjectileFallEntity shoot(LivingEntity entity, LivingEntity target) {
        DisgustProjectileFallEntity entityarrow = new DisgustProjectileFallEntity((EntityType<? extends DisgustProjectileFallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.DISGUST_PROJECTILE_FALL.get()), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + (double)target.getBbHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * (double)0.2f, dz, 2.0f, 12.0f);
        entityarrow.setNoPhysics(true);
        entityarrow.setBaseDamage(5.0);
        entityarrow.setCritArrow(false);
        entity.level().addFreshEntity((Entity)entityarrow);
        return entityarrow;
    }
}





























