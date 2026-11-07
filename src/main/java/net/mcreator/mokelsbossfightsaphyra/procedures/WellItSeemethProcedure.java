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
            entity.getPersistentData().putDouble("IA", entity.getPersistentData().getDouble("IA") + 1.0);
        }
        if (entity.getPersistentData().getDouble("IA") == 20.0) {
            if (!world.isClientSide() && world.getServer() != null) {
                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"???: Welcome"), false);
            }
            MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                if (entity.isAlive()) {
                    if (!world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"???: Linara informed me of thy imminent arrival."), false);
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                        if (entity.isAlive()) {
                            if (!world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: I am Saphyra, one of the few remaining witches that still draw breath in this world."), false);
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(180, () -> {
                                if (entity.isAlive()) {
                                    if (!world.isClientSide() && world.getServer() != null) {
                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: As the leader of the children of aenthir, I welcome thee into my guiding embrace."), false);
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(180, () -> {
                                        if (entity.isAlive()) {
                                            if (!world.isClientSide() && world.getServer() != null) {
                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Hand me the invitation Linara gave thee."), false);
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: And i will gladly adopt thou into my loving care"), false);
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





















