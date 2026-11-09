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
 *  net.minecraft.world.entity.projectile.arrow.AbstractArrow
 *  net.minecraft.world.entity.projectile.ItemSupplier
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.EntityHitResult
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.network.NetworkHooks
 *  net.neoforged.network.PlayMessages$SpawnEntity
 */
package com.hp.saphyrareborn.entity;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.procedures.WitchBossProjectileProjectileHitsLivingEntityProcedure;
import com.hp.saphyrareborn.procedures.WitchbosswhileflyingfallingtaickProcedure;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;

public class WitchBossProjectilefallEntity
extends AbstractArrow
implements ItemSupplier {
    private int legacyKnockback;
    private byte legacyPierceLevel;
    public static final ItemStack PROJECTILE_ITEM = new ItemStack((ItemLike)Blocks.AIR);

    public WitchBossProjectilefallEntity(EntityType<? extends WitchBossProjectilefallEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void doKnockback(net.minecraft.world.entity.LivingEntity target, net.minecraft.world.damagesource.DamageSource source) {
        super.doKnockback(target, source);
        if (this.legacyKnockback > 0) {
            net.minecraft.world.phys.Vec3 push = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(this.legacyKnockback * 0.6);
            if (push.lengthSqr() > 0.0) {
                target.push(push.x, 0.1, push.z);
            }
        }
    }

    public void setKnockback(int value) {
        this.legacyKnockback = value;
    }

    public void setPierceLevel(byte value) {
        this.legacyPierceLevel = value;
    }

    public WitchBossProjectilefallEntity(EntityType<? extends WitchBossProjectilefallEntity> type, double x, double y, double z, Level world) {
        super(type, world);
        this.setPos(x, y, z);
    }

    public WitchBossProjectilefallEntity(EntityType<? extends WitchBossProjectilefallEntity> type, LivingEntity entity, Level world) {
        super(type, world);
        this.setOwner(entity);
        this.setPos(entity.getX(), entity.getEyeY() - 0.1F, entity.getZ());
    }

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
        WitchBossProjectileProjectileHitsLivingEntityProcedure.execute((LevelAccessor)this.level(), (Entity)this);
    }

    public void tick() {
        super.tick();
        WitchbosswhileflyingfallingtaickProcedure.execute((LevelAccessor)this.level(), this.getX(), this.getY(), this.getZ());
        if (this.isInGround()) {
            this.discard();
        }
    }

    public static WitchBossProjectilefallEntity shoot(Level world, LivingEntity entity, RandomSource source) {
        return WitchBossProjectilefallEntity.shoot(world, entity, source, 1.0f, 5.0, 1);
    }

    public static WitchBossProjectilefallEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
        return WitchBossProjectilefallEntity.shoot(world, entity, source, pullingPower * 1.0f, 5.0, 1);
    }

    public static WitchBossProjectilefallEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        WitchBossProjectilefallEntity entityarrow = new WitchBossProjectilefallEntity((EntityType<? extends WitchBossProjectilefallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.WITCH_BOSS_PROJECTILEFALL.get()), entity, world);
        entityarrow.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2.0f, 0.0f);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        world.addFreshEntity((Entity)entityarrow);
        return entityarrow;
    }

    public static WitchBossProjectilefallEntity shoot(LivingEntity entity, LivingEntity target) {
        WitchBossProjectilefallEntity entityarrow = new WitchBossProjectilefallEntity((EntityType<? extends WitchBossProjectilefallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.WITCH_BOSS_PROJECTILEFALL.get()), entity, entity.level());
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



























