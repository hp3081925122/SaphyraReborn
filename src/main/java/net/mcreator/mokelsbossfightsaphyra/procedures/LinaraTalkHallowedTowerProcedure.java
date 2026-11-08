/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.LinaraHallowedTowerEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class LinaraTalkHallowedTowerProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
            if (entity.isAlive()) {
                if (!world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.cdfaa93f85e8"), false);
                }
                if (entity instanceof LinaraHallowedTowerEntity) {
                    ((LinaraHallowedTowerEntity)entity).setAnimation("greetings");
                }
            }
            MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.827eba185df0"), false);
                }
                MokelsBossfightSaphyraMod.queueServerWork(160, () -> {
                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.a8fcce248be5"), false);
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.0530afd45472"), false);
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.ad8c31059ba3"), false);
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.b5b057cb74b7"), false);
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(140, () -> {
                                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.897125bb2101"), false);
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.66de5b3bd528"), false);
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(160, () -> {
                                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.70c61aa8a43e"), false);
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(140, () -> {
                                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.dcd7a1ba466e"), false);
                                                }
                                                MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                                                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.af3cf4a324b7"), false);
                                                    }
                                                    MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                                                        if (entity.isAlive()) {
                                                            if (!world.isClientSide() && world.getServer() != null) {
                                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.3df9da692fd1"), false);
                                                            }
                                                            if (entity instanceof LinaraHallowedTowerEntity) {
                                                                ((LinaraHallowedTowerEntity)entity).setAnimation("give");
                                                            }
                                                            MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                                                                if (world instanceof ServerLevel) {
                                                                    ServerLevel _level1 = (ServerLevel)world;
                                                                    ItemEntity entityToSpawn = new ItemEntity((Level)_level1, entity.getX(), entity.getY(), entity.getZ(), new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.KEY_3.get()));
                                                                    entityToSpawn.setPickUpDelay(0);
                                                                    entityToSpawn.setUnlimitedLifetime();
                                                                    _level1.addFreshEntity((Entity)entityToSpawn);
                                                                }
                                                            });
                                                        }
                                                        MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                                                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.87a93d650b2c"), false);
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
        });
    }
}






















