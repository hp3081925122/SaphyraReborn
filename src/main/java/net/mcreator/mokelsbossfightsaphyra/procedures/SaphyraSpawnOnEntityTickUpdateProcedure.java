/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.EntitySpawnReason
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import java.util.Comparator;
import java.util.List;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SaphyraSpawnOnEntityTickUpdateProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        LivingEntity livingEntity;
        if (entity == null) {
            return;
        }
        if (entity instanceof Mob) {
            Mob _mobEnt = (Mob)entity;
            livingEntity = _mobEnt.getTarget();
        } else {
            livingEntity = null;
        }
        if (livingEntity != null) {
            entity.getPersistentData().putDouble("IA", entity.getPersistentData().getDoubleOr("IA", 0.0) + 1.0);
        }
        if (entity.getPersistentData().getDoubleOr("IA", 0.0) == 1.0) {
            MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                if (entity.isAlive()) {
                    if (!world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.e33cdf6a74a4"), false);
                    }
                    if (world instanceof ServerLevel) {
                        ServerLevel _level1 = (ServerLevel)world;
                        ((ServerLevel)_level1).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY() + 1.0, entity.getZ(), 25, 0.2, 0.2, 0.2, 0.1);
                    }
                }
                MokelsBossfightSaphyraMod.queueServerWork(160, () -> {
                    if (entity.isAlive()) {
                        if (!world.isClientSide() && world.getServer() != null) {
                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.02608f68c0b4"), false);
                        }
                        if (world instanceof ServerLevel) {
                            ServerLevel _level2 = (ServerLevel)world;
                            ((ServerLevel)_level2).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY() + 1.0, entity.getZ(), 50, 0.2, 0.2, 0.2, 0.1);
                        }
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(160, () -> {
                        if (entity.isAlive()) {
                            if (!world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.f51f30739c4d"), false);
                            }
                            if (world instanceof ServerLevel) {
                                ServerLevel _level3 = (ServerLevel)world;
                                ((ServerLevel)_level3).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY() + 1.0, entity.getZ(), 100, 0.2, 0.2, 0.2, 0.1);
                            }
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                            if (entity.isAlive()) {
                                if (!world.isClientSide() && world.getServer() != null) {
                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.f7c02d877843"), false);
                                }
                                if (world instanceof ServerLevel) {
                                    ServerLevel _level4 = (ServerLevel)world;
                                    ((ServerLevel)_level4).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY() + 1.0, entity.getZ(), 150, 0.2, 0.2, 0.2, 0.1);
                                }
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                if (entity.isAlive()) {
                                    ServerLevel _level5;
                                    if (!entity.level().isClientSide()) {
                                        entity.discard();
                                    }
                                    if (world instanceof ServerLevel) {
                                        _level5 = (ServerLevel)world;
                                        Entity entityToSpawn = ((EntityType)MokelsBossfightSaphyraModEntities.WITCHBOSS.get()).spawn(_level5, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
                                        if (entityToSpawn != null) {
                                            entityToSpawn.setYRot(entity.getYRot());
                                            entityToSpawn.setYBodyRot(entity.getYRot());
                                            entityToSpawn.setYHeadRot(entity.getYRot());
                                            entityToSpawn.setXRot(entity.getXRot());
                                            entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                                        }
                                    }
                                    if (world instanceof ServerLevel) {
                                        _level5 = (ServerLevel)world;
                                        ((ServerLevel)_level5).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY() + 1.0, entity.getZ(), 200, 0.2, 0.2, 0.2, 0.2);
                                    }
                                }
                            });
                        });
                    });
                });
            });
        }
        Vec3 _center1 = new Vec3(entity.getX(), entity.getY(), entity.getZ());
        List<Entity> _entfound1 = world.getEntitiesOfClass(Entity.class, new AABB(_center1, _center1).inflate(10.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center1))).toList();
        for (Entity entityiterator : _entfound1) {
            LivingEntity _entity1;
            if (!new Object(){

                public boolean checkGamemode(Entity _ent) {
                    if (_ent instanceof ServerPlayer) {
                        ServerPlayer _serverPlayer = (ServerPlayer)_ent;
                        return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
                    }
                    return false;
                }
            }.checkGamemode(entityiterator) || !(entityiterator instanceof Player) && !(entityiterator instanceof ServerPlayer) || !(entityiterator instanceof LivingEntity) || (_entity1 = (LivingEntity)entityiterator).level().isClientSide()) continue;
            _entity1.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.STAY_STILL, 5, 1, false, false));
        }
        if (world instanceof ServerLevel) {
            ServerLevel _level6 = (ServerLevel)world;
            ((ServerLevel)_level6).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY() + 1.0, entity.getZ(), 8, 0.1, 0.1, 0.1, 0.03);
        }
    }
}






















