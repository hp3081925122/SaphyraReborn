/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;

public class WellItSeemethProcedure {
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
            if (!world.isClientSide() && world.getServer() != null) {
                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.589f19fd9a63"), false);
            }
            MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                if (entity.isAlive()) {
                    if (!world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.00cd4898683f"), false);
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                        if (entity.isAlive()) {
                            if (!world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.8fc2aaa537b9"), false);
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(180, () -> {
                                if (entity.isAlive()) {
                                    if (!world.isClientSide() && world.getServer() != null) {
                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.e83a41005eaf"), false);
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(180, () -> {
                                        if (entity.isAlive()) {
                                            if (!world.isClientSide() && world.getServer() != null) {
                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.662d9862454e"), false);
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.hardcoded.89e39c5710f8"), false);
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
}






















