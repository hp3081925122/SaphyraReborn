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
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: Hello again"), false);
                }
                if (entity instanceof LinaraHallowedTowerEntity) {
                    ((LinaraHallowedTowerEntity)entity).setAnimation("greetings");
                }
            }
            MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: I watched thou fight against the Nameless Gatekeeper"), false);
                }
                MokelsBossfightSaphyraMod.queueServerWork(160, () -> {
                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: greatly done"), false);
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: However..."), false);
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: The wind whispers to me. "), false);
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: His rotten mind still taints the canvas of our world"), false);
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(140, () -> {
                                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: ..."), false);
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: could thee go back to the church of the oath pus for me?"), false);
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(160, () -> {
                                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: I bear the key to a treasure room"), false);
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(140, () -> {
                                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: bring me the \"Manic Eye\""), false);
                                                }
                                                MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                                                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: Thy payment shall be the rest of the treasure."), false);
                                                    }
                                                    MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                                                        if (entity.isAlive()) {
                                                            if (!world.isClientSide() && world.getServer() != null) {
                                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: Here, take the key"), false);
                                                            }
                                                            if (entity instanceof LinaraHallowedTowerEntity) {
                                                                ((LinaraHallowedTowerEntity)entity).setAnimation("give");
                                                            }
                                                            MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                                                                if (world instanceof ServerLevel) {
                                                                    ServerLevel _level = (ServerLevel)world;
                                                                    ItemEntity entityToSpawn = new ItemEntity((Level)_level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.KEY_3.get()));
                                                                    entityToSpawn.setPickUpDelay(0);
                                                                    entityToSpawn.setUnlimitedLifetime();
                                                                    _level.addFreshEntity((Entity)entityToSpawn);
                                                                }
                                                            });
                                                        }
                                                        MokelsBossfightSaphyraMod.queueServerWork(100, () -> {
                                                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Linara: I shall wait here for thee to return"), false);
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




















