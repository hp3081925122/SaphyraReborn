/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.commands.CommandSource
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.Vec2
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.MothbossEntity;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MothbossEntityDiesProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity instanceof MothbossEntity) {
            ((MothbossEntity)entity).setAnimation("empty");
        }
        MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
            ServerLevel _level;
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/title @a title {\"text\":\"EMERGED VICTORIOUS\",\"bold\":true,\"color\":\"#FFCF25\"}");
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/title @a times 20 40 20");
            }
            if (world instanceof Level) {
                _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:bosskilled")), SoundSource.MASTER, 2.0f, 1.0f);
                } else {
                    _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:bosskilled")), SoundSource.MASTER, 2.0f, 1.0f, false);
                }
            }
        });
        MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
            if (!world.isClientSide() && world.getServer() != null) {
                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: At last, I have found one, enduring enough to confront the horrors that linger within this world"), false);
            }
            MokelsBossfightSaphyraMod.queueServerWork(200, () -> {
                if (!world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Though I regret placing this burden upon thee, know that thou art the chosen one to end our suffering."), false);
                }
                MokelsBossfightSaphyraMod.queueServerWork(240, () -> {
                    if (!world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: forgive me..."), false);
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(160, () -> {
                        if (!world.isClientSide() && world.getServer() != null) {
                            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"END OF CHAPTER 1"), false);
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(140, () -> {
                            if (!world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"CHAPTER 2 COMING SOON"), false);
                            }
                        });
                    });
                });
            });
        });
    }
}




















