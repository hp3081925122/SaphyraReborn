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
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class SaphyraDialog03Procedure {
    public static void execute(LevelAccessor world, double x, double z, Entity entity) {
        if (entity == null) {
            return;
        }
        if (!world.isClientSide() && world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.1797993a1912"), false);
        }
        MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.323863bfc608"), false);
            }
            MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.6d83a0c9b779"), false);
                }
                MokelsBossfightSaphyraMod.queueServerWork(140, () -> {
                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.32e124674e1e"), false);
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.53725027b257"), false);
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.5e76359dd941"), false);
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.911b6172a72e"), false);
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                    if (entity.isAlive()) {
                                        if (!world.isClientSide() && world.getServer() != null) {
                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.68f988765128"), false);
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                                            ItemEntity entityToSpawn;
                                            ServerLevel _level1;
                                            if (world instanceof ServerLevel) {
                                                _level1 = (ServerLevel)world;
                                                entityToSpawn = new ItemEntity((Level)_level1, entity.getX() + entity.getLookAngle().x * 2.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 2.0, new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.HALLOWED_KEY_2.get()));
                                                entityToSpawn.setPickUpDelay(0);
                                                entityToSpawn.setUnlimitedLifetime();
                                                _level1.addFreshEntity((Entity)entityToSpawn);
                                            }
                                            if (world instanceof ServerLevel) {
                                                _level1 = (ServerLevel)world;
                                                entityToSpawn = new ItemEntity((Level)_level1, entity.getX() + entity.getLookAngle().x * 2.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 2.0, new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.HALLOWED_KEY_2.get()));
                                                entityToSpawn.setPickUpDelay(0);
                                                entityToSpawn.setUnlimitedLifetime();
                                                _level1.addFreshEntity((Entity)entityToSpawn);
                                            }
                                        });
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.3a9db853bb56"), false);
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.04421a79aaa0"), false);
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
    }
}






















