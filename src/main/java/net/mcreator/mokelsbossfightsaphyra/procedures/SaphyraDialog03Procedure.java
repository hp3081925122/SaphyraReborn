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
            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: well done,"), false);
        }
        MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Thee earned respect."), false);
            }
            MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Thou art in possession of his memory, I perceive."), false);
                }
                MokelsBossfightSaphyraMod.queueServerWork(140, () -> {
                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Talk to Marlene."), false);
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: She is a student of mine,"), false);
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: And the only one able to convert his past."), false);
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Take these keys."), false);
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                    if (entity.isAlive()) {
                                        if (!world.isClientSide() && world.getServer() != null) {
                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: It will open the path to her room."), false);
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                                            ItemEntity entityToSpawn;
                                            ServerLevel _level;
                                            if (world instanceof ServerLevel) {
                                                _level = (ServerLevel)world;
                                                entityToSpawn = new ItemEntity((Level)_level, entity.getX() + entity.getLookAngle().x * 2.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 2.0, new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.HALLOWED_KEY_2.get()));
                                                entityToSpawn.setPickUpDelay(0);
                                                entityToSpawn.setUnlimitedLifetime();
                                                _level.addFreshEntity((Entity)entityToSpawn);
                                            }
                                            if (world instanceof ServerLevel) {
                                                _level = (ServerLevel)world;
                                                entityToSpawn = new ItemEntity((Level)_level, entity.getX() + entity.getLookAngle().x * 2.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 2.0, new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.HALLOWED_KEY_2.get()));
                                                entityToSpawn.setPickUpDelay(0);
                                                entityToSpawn.setUnlimitedLifetime();
                                                _level.addFreshEntity((Entity)entityToSpawn);
                                            }
                                        });
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: She stands upon the second level."), false);
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Once it is done, return to me."), false);
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



















