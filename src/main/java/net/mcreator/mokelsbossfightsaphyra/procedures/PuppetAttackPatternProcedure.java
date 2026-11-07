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
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec2
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.procedures.AttackDashForthProcedure;
import com.hp.saphyrareborn.procedures.AttackFifthProcedure;
import com.hp.saphyrareborn.procedures.AttackFirstProcedure;
import com.hp.saphyrareborn.procedures.AttackFourthProcedure;
import com.hp.saphyrareborn.procedures.AttackSecondProcedure;
import com.hp.saphyrareborn.procedures.PuppetDashProcedure;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class PuppetAttackPatternProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        Level _level1;
        LivingEntity livingEntity;
        LivingEntity _livEnt23;
        Mob _mobEnt;
        LivingEntity _livEnt5;
        LivingEntity livingEntity2;
        Mob _mobEnt2;
        if (entity == null) {
            return;
        }
        boolean found = false;
        double sx = 0.0;
        double sy = 0.0;
        double sz = 0.0;
        if (entity instanceof Mob) {
            _mobEnt2 = (Mob)entity;
            livingEntity2 = _mobEnt2.getTarget();
        } else {
            livingEntity2 = null;
        }
        if (livingEntity2 != null) {
            entity.getPersistentData().putDouble("IA", entity.getPersistentData().getDouble("IA") + 1.0);
        }
        if (!(entity.getPersistentData().getDouble("IA") != 20.0 || entity instanceof LivingEntity && (_livEnt5 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN))) {
            if (Math.random() <= 0.5) {
                LivingEntity livingEntity3;
                if (entity instanceof Mob) {
                    _mobEnt = (Mob)entity;
                    livingEntity3 = _mobEnt.getTarget();
                } else {
                    livingEntity3 = null;
                }
                if (livingEntity3 instanceof LivingEntity) {
                    if (!world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackFifthProcedure.execute(world, x, z, entity);
                        PuppetDashProcedure.execute(world, entity);
                        MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                            AttackFifthProcedure.execute(world, x, z, entity);
                            MokelsBossfightSaphyraMod.queueServerWork(60, () -> AttackFifthProcedure.execute(world, x, z, entity));
                        });
                    } else if (world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackFifthProcedure.execute(world, x, z, entity);
                        MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                            AttackFifthProcedure.execute(world, x, z, entity);
                            MokelsBossfightSaphyraMod.queueServerWork(60, () -> AttackFifthProcedure.execute(world, x, z, entity));
                        });
                    }
                }
            } else if (Math.random() <= 1.0) {
                LivingEntity livingEntity4;
                if (entity instanceof Mob) {
                    _mobEnt = (Mob)entity;
                    livingEntity4 = _mobEnt.getTarget();
                } else {
                    livingEntity4 = null;
                }
                if (livingEntity4 instanceof LivingEntity) {
                    if (!world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackFourthProcedure.execute(world, x, z, entity);
                        MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                            AttackFourthProcedure.execute(world, x, z, entity);
                            MokelsBossfightSaphyraMod.queueServerWork(60, () -> AttackFourthProcedure.execute(world, x, z, entity));
                        });
                    } else if (world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        PuppetDashProcedure.execute(world, entity);
                        MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
                            AttackFourthProcedure.execute(world, x, z, entity);
                            MokelsBossfightSaphyraMod.queueServerWork(60, () -> AttackFourthProcedure.execute(world, x, z, entity));
                        });
                    }
                }
            }
        }
        if (!(entity.getPersistentData().getDouble("IA") != 240.0 || entity instanceof LivingEntity && (_livEnt23 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN))) {
            if (Math.random() <= 0.5) {
                LivingEntity livingEntity5;
                if (entity instanceof Mob) {
                    _mobEnt = (Mob)entity;
                    livingEntity5 = _mobEnt.getTarget();
                } else {
                    livingEntity5 = null;
                }
                if (livingEntity5 instanceof LivingEntity) {
                    if (!world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackFirstProcedure.execute(world, x, z, entity);
                    } else if (world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackDashForthProcedure.execute(world, entity);
                        AttackFirstProcedure.execute(world, x, z, entity);
                    }
                }
            } else if (Math.random() <= 1.0) {
                LivingEntity livingEntity6;
                if (entity instanceof Mob) {
                    _mobEnt = (Mob)entity;
                    livingEntity6 = _mobEnt.getTarget();
                } else {
                    livingEntity6 = null;
                }
                if (livingEntity6 instanceof LivingEntity) {
                    if (!world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackSecondProcedure.execute(world, x, z, entity);
                    } else if (world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackSecondProcedure.execute(world, x, z, entity);
                        AttackDashForthProcedure.execute(world, entity);
                    }
                }
            }
        }
        if (entity.getPersistentData().getDouble("IA") == 300.0) {
            entity.getPersistentData().putDouble("IA", 0.0);
        }
        if (entity instanceof Mob) {
            _mobEnt2 = (Mob)entity;
            livingEntity = _mobEnt2.getTarget();
        } else {
            livingEntity = null;
        }
        if (livingEntity != null) {
            entity.getPersistentData().putDouble("music", entity.getPersistentData().getDouble("music") + 1.0);
        }
        if (entity.getPersistentData().getDouble("music") == 20.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level1 = (Level)world;
                if (!_level1.isClientSide()) {
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme1")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme1")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
        }
        if (entity.getPersistentData().getDouble("music") == 320.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level1 = (Level)world;
                if (!_level1.isClientSide()) {
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme2")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme2")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
        }
        if (entity.getPersistentData().getDouble("music") == 620.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level1 = (Level)world;
                if (!_level1.isClientSide()) {
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme3")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme3")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
        }
        if (entity.getPersistentData().getDouble("music") == 920.0 && entity.isAlive()) {
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
            if (world instanceof Level) {
                _level1 = (Level)world;
                if (!_level1.isClientSide()) {
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme4")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme4")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
        }
        if (entity.getPersistentData().getDouble("music") == 1220.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level1 = (Level)world;
                if (!_level1.isClientSide()) {
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme5")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme5")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
        }
        if (entity.getPersistentData().getDouble("music") == 1520.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level1 = (Level)world;
                if (!_level1.isClientSide()) {
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme6")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme6")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
        }
        if (entity.getPersistentData().getDouble("music") == 1820.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level1 = (Level)world;
                if (!_level1.isClientSide()) {
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme7")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme7")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
        }
        if (entity.getPersistentData().getDouble("music") == 1900.0) {
            entity.getPersistentData().putDouble("music", 0.0);
        }
        if (world instanceof ServerLevel) {
            _level1 = (ServerLevel)world;
            _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/tp @s ~ ~ ~ facing entity @p[distance=0..48]");
        }
    }
}






















