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
            entity.getPersistentData().putDouble("IA", entity.getPersistentData().getDouble("IA") + 1.0);
        }
        if (entity.getPersistentData().getDouble("IA") == 20.0) {
            MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: Who art thou?"), false);
                }
                MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: Did mother send thee forth?"), false);
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: ..."), false);
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: good..."), false);
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: well..."), false);
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: i sense thou got your hold on a memory?"), false);
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(140, () -> {
                                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: I shall convert it,"), false);
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                                            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: but only if thou offer me thy help first."), false);
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(160, () -> {
                                                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: I am a scholar of the written word, a seeker of forgotten lore"), false);
                                                }
                                                MokelsBossfightSaphyraMod.queueServerWork(180, () -> {
                                                    if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: Bring me a book, a tale I have not yet encountered."), false);
                                                    }
                                                    MokelsBossfightSaphyraMod.queueServerWork(180, () -> {
                                                        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                                                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: When thou hast found it, return to me, and I shall grant thy wish."), false);
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
        if (world instanceof ServerLevel) {
            ServerLevel _level1 = (ServerLevel)world;
            _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, _level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/tp @s ~ ~ ~ facing entity @p[distance=0..48]");
        }
    }
}






















