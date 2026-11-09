/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.commands.CommandSource
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.Vec2
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class MarleneDialogProcedure {
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
        if (entity.getPersistentData().getDoubleOr("IA", 0.0) == 20.0) {
            MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.5221475cf21f"), false);
                }
                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.0dc694559f11"), false);
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.28d85d8b7b88"), false);
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.f3808c3da42d"), false);
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.66149a0f6591"), false);
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.38eeb6646738"), false);
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(140, () -> {
                                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.bb1dd95ef458"), false);
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.d5e599b9e3ca"), false);
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(160, () -> {
                                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.8fb04962eed3"), false);
                                                }
                                                MokelsBossfightSaphyraMod.queueServerWork(180, () -> {
                                                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.cbd3483b7581"), false);
                                                    }
                                                    MokelsBossfightSaphyraMod.queueServerWork(180, () -> {
                                                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.171f4fc46d59"), false);
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
        }
    }
}






















