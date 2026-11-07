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
import com.hp.saphyrareborn.entity.SaphyraDialog2Entity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class WellItSeemeth2Procedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        MokelsBossfightSaphyraMod.queueServerWork(40, () -> {
            if (entity.isAlive()) {
                if (!world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Very well"), false);
                }
                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                    if (entity.isAlive()) {
                        if (entity instanceof SaphyraDialog2Entity) {
                            ((SaphyraDialog2Entity)entity).setAnimation("posing");
                        }
                        if (!world.isClientSide() && world.getServer() != null) {
                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: May the stars align and take on the form of our new member."), false);
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(200, () -> {
                            if (entity.isAlive()) {
                                if (!world.isClientSide() && world.getServer() != null) {
                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: ..."), false);
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                    if (entity.isAlive()) {
                                        if (!world.isClientSide() && world.getServer() != null) {
                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: I sense great potential inside of thee."), false);
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                            if (entity.isAlive()) {
                                                if (!world.isClientSide() && world.getServer() != null) {
                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: And a will to rival the shattered goddess"), false);
                                                }
                                                MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                                                    if (entity.isAlive()) {
                                                        if (!world.isClientSide() && world.getServer() != null) {
                                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: The path ahead is fraught with peril, but together, we shall overcome."), false);
                                                        }
                                                        MokelsBossfightSaphyraMod.queueServerWork(200, () -> {
                                                            if (entity.isAlive()) {
                                                                if (!world.isClientSide() && world.getServer() != null) {
                                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Take this"), false);
                                                                }
                                                                MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                                                                    if (world instanceof ServerLevel) {
                                                                        ServerLevel _level = (ServerLevel)world;
                                                                        ItemEntity entityToSpawn = new ItemEntity((Level)_level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.KEY_4.get()));
                                                                        entityToSpawn.setPickUpDelay(0);
                                                                        entityToSpawn.setUnlimitedLifetime();
                                                                        _level.addFreshEntity((Entity)entityToSpawn);
                                                                    }
                                                                });
                                                                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                                                    if (entity.isAlive()) {
                                                                        if (!world.isClientSide() && world.getServer() != null) {
                                                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Head to the church of the oath pus and defeat the Nameless Gatekeeper"), false);
                                                                        }
                                                                        MokelsBossfightSaphyraMod.queueServerWork(180, () -> {
                                                                            if (entity.isAlive()) {
                                                                                if (!world.isClientSide() && world.getServer() != null) {
                                                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: I will wait here for thee to return."), false);
                                                                                }
                                                                                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                                                                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Now go"), false);
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}




















