/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import java.util.Comparator;
import java.util.List;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.MarleneHumanFormDialog2Entity;
import com.hp.saphyrareborn.entity.SaphyraDialog3Entity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MarleneGiveMemoryProcedure {
    public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
        ItemStack itemStack;
        if (entity == null || sourceentity == null) {
            return;
        }
        if (sourceentity instanceof LivingEntity) {
            _livEnt1ntity _livEnt1 = (LivingEntity)sourceentity;
            itemStack = _livEnt1.getMainHandItem();
        } else {
            itemStack = ItemStack.EMPTY;
        }
        if (itemStack.getItem() == MokelsBossfightSaphyraModItems.NAMELESS_GATEKEEPER_REMEMBRANCE.get()) {
            Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(128.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                if (!(entityiterator instanceof SaphyraDialog3Entity)) continue;
                if (!entityiterator.level().isClientSide()) {
                    entityiterator.discard();
                }
                if (!(world instanceof ServerLevel)) continue;
                ServerLevel _level = (ServerLevel)world;
                Entity entityToSpawn = ((EntityType)MokelsBossfightSaphyraModEntities.SAPHYRA_DIALOG_4.get()).spawn(_level, BlockPos.containing((double)entityiterator.getX(), (double)entityiterator.getY(), (double)entityiterator.getZ()), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn == null) continue;
                entityToSpawn.setYRot(entityiterator.getYRot());
                entityToSpawn.setYBodyRot(entityiterator.getYRot());
                entityToSpawn.setYHeadRot(entityiterator.getYRot());
                entityToSpawn.setXRot(entityiterator.getXRot());
                entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
            }
            if (sourceentity instanceof Player) {
                Player _player = (Player)sourceentity;
             _stktoremove1 _stktoremove1 = new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.NAMELESS_GATEKEEPER_REMEMBRANCE.get());
                _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove1.getItem() == p.getItem(), 1, (Container)_player.getInventory());
            }
            if (entity instanceof MarleneHumanFormDialog2Entity) {
                ((MarleneHumanFormDialog2Entity)entity).setAnimation("convert");
            }
            MokelsBossfightSaphyraMod.queueServerWork(66, () -> {
                if (entity.isAlive()) {
                    ServerLevel _level;
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        ItemEntity entityToSpawn = new ItemEntity((Level)_level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.SINISTER_BAG.get()));
                        entityToSpawn.setPickUpDelay(2);
                        entityToSpawn.setUnlimitedLifetime();
                        _level.addFreshEntity((Entity)entityToSpawn);
                    }
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), entity.getX(), entity.getY(), entity.getZ(), 300, 0.2, 1.0, 0.2, 0.05);
                    }
                    if (!world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: ..."), false);
                    }
                }
                MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                    if (entity.isAlive()) {
                        if (!world.isClientSide() && world.getServer() != null) {
                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: Thee should head to the observatory"), false);
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: Mother wishes to test thy strength in a duel."), false);
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(140, () -> {
                                if (entity.isAlive()) {
                                    if (!world.isClientSide() && world.getServer() != null) {
                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: Take this key"), false);
                                    }
                                    if (entity.isAlive() && world instanceof ServerLevel) {
                                        ServerLevel _level = (ServerLevel)world;
                                        ItemEntity entityToSpawn = new ItemEntity((Level)_level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.SAPHYRA_KEY.get()));
                                        entityToSpawn.setPickUpDelay(2);
                                        entityToSpawn.setUnlimitedLifetime();
                                        _level.addFreshEntity((Entity)entityToSpawn);
                                    }
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: I wish thee good luck..."), false);
                                    }
                                });
                            });
                        });
                    }
                });
            });
        } else {
            ItemStack itemStack2;
            if (sourceentity instanceof LivingEntity) {
                _livEnt2ntity _livEnt2 = (LivingEntity)sourceentity;
                itemStack2 = _livEnt2.getMainHandItem();
            } else {
                itemStack2 = ItemStack.EMPTY;
            }
            if (itemStack2.getItem() == MokelsBossfightSaphyraModItems.SAPHYRA_REMEMBRANCE.get()) {
                if (sourceentity instanceof Player) {
                    Player _player = (Player)sourceentity;
               _stktoremove2ck _stktoremove2 = new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.SAPHYRA_REMEMBRANCE.get());
                    _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove2.getItem() == p.getItem(), 1, (Container)_player.getInventory());
                }
                if (entity instanceof MarleneHumanFormDialog2Entity) {
                    ((MarleneHumanFormDialog2Entity)entity).setAnimation("convert");
                }
                MokelsBossfightSaphyraMod.queueServerWork(66, () -> {
                    if (entity.isAlive()) {
                        ServerLevel _level;
                        if (world instanceof ServerLevel) {
                            _level = (ServerLevel)world;
                            ItemEntity entityToSpawn = new ItemEntity((Level)_level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.BOTTOMLESS_BAG.get()));
                            entityToSpawn.setPickUpDelay(2);
                            entityToSpawn.setUnlimitedLifetime();
                            _level.addFreshEntity((Entity)entityToSpawn);
                        }
                        if (world instanceof ServerLevel) {
                            _level = (ServerLevel)world;
                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY(), entity.getZ(), 300, 0.2, 1.0, 0.2, 0.05);
                        }
                    }
                });
            }
        }
    }
}




















