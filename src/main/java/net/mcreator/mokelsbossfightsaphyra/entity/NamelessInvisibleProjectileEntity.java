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
    private static final int POOL_TICKS = 60;
    private int poolAge = -1;
    public static final ItemStack PROJECTILE_ITEM = new ItemStack((ItemLike)Blocks.AIR);

    public NamelessInvisibleProjectileEntity(EntityType<? extends NamelessInvisibleProjectileEntity> type, Level world) {
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
        if (this.poolAge < 0 && this.level() instanceof net.minecraft.server.level.ServerLevel level) {
            this.poolAge = 0;
            com.hp.saphyrareborn.boss.nameless.NamelessVfx.send(level, com.hp.saphyrareborn.boss.nameless.NamelessVfx.ROT_POOL, this.position(), 0.0f, 2.6f, POOL_TICKS);
        }
    }

    private void tickRotPool(net.minecraft.server.level.ServerLevel level) {
        this.poolAge++;
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        if (this.poolAge % 4 == 0 && this.poolAge <= POOL_TICKS) {
            level.sendParticles(com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get(), x, y, z, 3, 0.5, 0.5, 0.5, 0.01);
            level.sendParticles(com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get(), x, y, z, 2, 0.5, 0.5, 0.5, 0.01);
        }
        if (this.poolAge % 2 == 0 && this.poolAge <= POOL_TICKS) {
            for (net.minecraft.world.entity.player.Player player : level.getEntitiesOfClass(net.minecraft.world.entity.player.Player.class, new net.minecraft.world.phys.AABB(x, y, z, x, y, z).inflate(3.0), com.hp.saphyrareborn.boss.nameless.NamelessCombat::canBeHit)) {
                com.hp.saphyrareborn.boss.nameless.NamelessCombat.addRotting(player, 1.0);
            }
        }
        if (this.poolAge >= POOL_TICKS) {
            for (int i = 0; i < 3; i++) {
                NamelessProjectileFallEntity fall = new NamelessProjectileFallEntity(MokelsBossfightSaphyraModEntities.NAMELESS_PROJECTILE_FALL.get(), level);
                fall.setOwner(this.getOwner());
                fall.setBaseDamage(0.0);
                fall.setKnockback(1);
                fall.setSilent(true);
                fall.setPos(x, y + 1.0, z);
                fall.shoot(0.0, 90.0, 0.0, 0.7f, 15.0f);
                level.addFreshEntity(fall);
            }
            this.discard();
        }
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("RotPoolAge", this.poolAge);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("RotPoolAge")) {
            this.poolAge = tag.getInt("RotPoolAge");
        }
    }

    public void tick() {
        super.tick();
        if (this.level() instanceof net.minecraft.server.level.ServerLevel level) {
            if (this.poolAge >= 0) {
                this.tickRotPool(level);
            } else if (this.inGround) {
                this.discard();
            }
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
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        world.addFreshEntity((Entity)entityarrow);
        return entityarrow;
    }

    public static NamelessInvisibleProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
        NamelessInvisibleProjectileEntity entityarrow = new NamelessInvisibleProjectileEntity((EntityType<? extends NamelessInvisibleProjectileEntity>)((EntityType)MokelsBossfightSaphyraModEntities.NAMELESS_INVISIBLE_PROJECTILE.get()), entity, entity.level());
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




























