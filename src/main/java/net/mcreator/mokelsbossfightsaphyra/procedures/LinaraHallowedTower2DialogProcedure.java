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
                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: I bid thee for forgiveness"), false);
            }
            MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: My goal was to find out how resistant thou are against the vile curse called manic"), false);
                }
                MokelsBossfightSaphyraMod.queueServerWork(200, () -> {
                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: And here thee standeth before me."), false);
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: Without loosing your mind"), false);
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: So resistant..."), false);
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: So powerful..."), false);
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: I have to go now."), false);
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: My duty is needed elsewhere."), false);
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                                            if (entity.isAlive()) {
                                                if (!world.isClientSide() && world.getServer() != null) {
                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: May we meet again."), false);
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
                                                            ServerLevel _level = (ServerLevel)world;
                                                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.PURPLE_FLAME_PARTICLE.get()), entity.getX(), entity.getY(), entity.getZ(), 400, 0.2, 1.2, 0.2, 0.1);
                                                        }
                                                    }
                                                });
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(160, () -> {
                                                if (!world.isClientSide() && world.getServer() != null) {
                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: Please..."), false);
                                                }
                                                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                                    if (!world.isClientSide() && world.getServer() != null) {
                                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: Do not give up..."), false);
                                                    }
                                                    MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                                                        if (!world.isClientSide() && world.getServer() != null) {
                                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: I believe in thou..."), false);
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






















