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
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.network.NetworkHooks
 *  net.neoforged.network.PlayMessages$SpawnEntity
 */
package com.hp.saphyrareborn.entity;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.procedures.NamelessInvisibleProjectileProjectileHitsLivingEntityProcedure;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT, _interface=ItemSupplier.class)
public class NamelessInvisibleProjectileEntity
extends AbstractArrow
implements ItemSupplier {
    private int legacyKnockback;
    public static final ItemStack PROJECTILE_ITEM = new ItemStack((ItemLike)Blocks.AIR);

    public NamelessInvisibleProjectileEntity(EntityType<? extends NamelessInvisibleProjectileEntity> type, Level world) {
        super(type, world);
    }

    public void setKnockback(int value) {
        this.legacyKnockback = value;
    }

    public NamelessInvisibleProjectileEntity(EntityType<? extends NamelessInvisibleProjectileEntity> type, double x, double y, double z, Level world) {
        super(type, world);
        this.setPos(x, y, z);
    }

    public NamelessInvisibleProjectileEntity(EntityType<? extends NamelessInvisibleProjectileEntity> type, LivingEntity entity, Level world) {
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

    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        NamelessInvisibleProjectileProjectileHitsLivingEntityProcedure.execute((LevelAccessor)this.level(), blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ());
    }

    public void tick() {
        super.tick();
        if (this.inGround) {
            this.discard();
        }
    }

    public static NamelessInvisibleProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source) {
        return NamelessInvisibleProjectileEntity.shoot(world, entity, source, 1.0f, 5.0, 1);
    }

    public static NamelessInvisibleProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
        return NamelessInvisibleProjectileEntity.shoot(world, entity, source, pullingPower * 1.0f, 5.0, 1);
    }

    public static NamelessInvisibleProjectileEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        NamelessInvisibleProjectileEntity entityarrow = new NamelessInvisibleProjectileEntity((EntityType<? extends NamelessInvisibleProjectileEntity>)((EntityType)MokelsBossfightSaphyraModEntities.NAMELESS_INVISIBLE_PROJECTILE.get()), entity, world);
        entityarrow.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2.0f, 0.0f);
        entityarrow.setNoPhysics(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        world.addFreshEntity((Entity)entityarrow);
        return entityarrow;
    }

    public static NamelessInvisibleProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
        NamelessInvisibleProjectileEntity entityarrow = new NamelessInvisibleProjectileEntity((EntityType<? extends NamelessInvisibleProjectileEntity>)((EntityType)MokelsBossfightSaphyraModEntities.NAMELESS_INVISIBLE_PROJECTILE.get()), entity, entity.level());
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




























