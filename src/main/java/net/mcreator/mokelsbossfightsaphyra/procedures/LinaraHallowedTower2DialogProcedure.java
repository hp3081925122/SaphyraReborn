/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.LinaraEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class LinaraHallowedTower2DialogProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.7560f87a9c1a"), false);
            }
            MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.9f35e05a8b10"), false);
                }
                MokelsBossfightSaphyraMod.queueServerWork(200, () -> {
                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.be31d199d4ff"), false);
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.cdab62ddd07d"), false);
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.64df7e23b08b"), false);
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.d7b5b380c818"), false);
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.acdb0c1cc417"), false);
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.54d45dc09b74"), false);
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                                            if (entity.isAlive()) {
                                                if (!world.isClientSide() && world.getServer() != null) {
                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.a08809ae5164"), false);
                                                }
                                                if (entity instanceof LinaraEntity) {
                                                    ((LinaraEntity)entity).setAnimation("sky");
                                                }
                                                MokelsBossfightSaphyraMod.queueServerWork(70, () -> {
                                                    if (entity.isAlive()) {
                                                        if (!entity.level().isClientSide()) {
                                                            entity.discard();
                                                        }
                                                        for (int index0 = 0; index0 < 3; ++index0) {
                                                            if (!(world instanceof ServerLevel)) continue;
                                                            ServerLevel _level1 = (ServerLevel)world;
                                                            ((ServerLevel)_level1).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.PURPLE_FLAME_PARTICLE.get()), entity.getX(), entity.getY(), entity.getZ(), 400, 0.2, 1.2, 0.2, 0.1);
                                                        }
                                                    }
                                                });
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(160, () -> {
                                                if (!world.isClientSide() && world.getServer() != null) {
                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.784e5026bbf3"), false);
                                                }
                                                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                                    if (!world.isClientSide() && world.getServer() != null) {
                                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.0307fa5d6cce"), false);
                                                    }
                                                    MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                                                        if (!world.isClientSide() && world.getServer() != null) {
                                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.3e503d80422a"), false);
                                                        }
                                                    });
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
    }
}






















