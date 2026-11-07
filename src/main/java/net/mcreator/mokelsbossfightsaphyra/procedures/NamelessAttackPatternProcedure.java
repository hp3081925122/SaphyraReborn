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
import com.hp.saphyrareborn.procedures.AttackBruellenProcedure;
import com.hp.saphyrareborn.procedures.AttackDoubleOnceProcedure;
import com.hp.saphyrareborn.procedures.AttackDoubleProcedure;
import com.hp.saphyrareborn.procedures.AttackFloatProcedure;
import com.hp.saphyrareborn.procedures.AttackGroundProcedure;
import com.hp.saphyrareborn.procedures.AttackOnceProcedure;
import com.hp.saphyrareborn.procedures.AttackRotProcedure;
import com.hp.saphyrareborn.procedures.AttackShieldBlockProcedure;
import com.hp.saphyrareborn.procedures.AttackShieldSlashProcedure;
import com.hp.saphyrareborn.procedures.AttackStompProcedure;
import com.hp.saphyrareborn.procedures.AttackSuperProcedure;
import com.hp.saphyrareborn.procedures.WalkToBlockProcedure;
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

public class NamelessAttackPatternProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        Level _level;
        LivingEntity livingEntity;
        LivingEntity _livEnt61;
        LivingEntity _livEnt48;
        LivingEntity _livEnt40;
        LivingEntity _livEnt28;
        LivingEntity _livEnt15;
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
                        AttackDoubleProcedure.execute(world, entity);
                    } else if (world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackRotProcedure.execute(world, x, z, entity);
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
                        AttackOnceProcedure.execute(world, entity);
                    } else if (world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackFloatProcedure.execute(world, entity);
                    }
                }
            }
        }
        if (!(entity.getPersistentData().getDouble("IA") != 100.0 || entity instanceof LivingEntity && (_livEnt15 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN))) {
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
                        AttackShieldBlockProcedure.execute(entity);
                    } else if (world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackFloatProcedure.execute(world, entity);
                        MokelsBossfightSaphyraMod.queueServerWork(30, () -> {
                            AttackFloatProcedure.execute(world, entity);
                            MokelsBossfightSaphyraMod.queueServerWork(30, () -> AttackFloatProcedure.execute(world, entity));
                        });
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
                        AttackShieldSlashProcedure.execute(world, entity);
                    } else if (world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackRotProcedure.execute(world, x, z, entity);
                        MokelsBossfightSaphyraMod.queueServerWork(35, () -> AttackRotProcedure.execute(world, x, z, entity));
                    }
                }
            }
        }
        if (!(entity.getPersistentData().getDouble("IA") != 220.0 || entity instanceof LivingEntity && (_livEnt28 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN))) {
            if (Math.random() <= 0.5) {
                LivingEntity livingEntity7;
                if (entity instanceof Mob) {
                    _mobEnt = (Mob)entity;
                    livingEntity7 = _mobEnt.getTarget();
                } else {
                    livingEntity7 = null;
                }
                if (livingEntity7 instanceof LivingEntity) {
                    if (!world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackSuperProcedure.execute(world, entity);
                    } else if (world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackRotProcedure.execute(world, x, z, entity);
                        MokelsBossfightSaphyraMod.queueServerWork(35, () -> AttackRotProcedure.execute(world, x, z, entity));
                    }
                }
            } else if (Math.random() <= 1.0) {
                LivingEntity livingEntity8;
                if (entity instanceof Mob) {
                    _mobEnt = (Mob)entity;
                    livingEntity8 = _mobEnt.getTarget();
                } else {
                    livingEntity8 = null;
                }
                if (livingEntity8 instanceof LivingEntity) {
                    if (!world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackDoubleOnceProcedure.execute(world, entity);
                    } else if (world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                        AttackFloatProcedure.execute(world, entity);
                        MokelsBossfightSaphyraMod.queueServerWork(30, () -> AttackFloatProcedure.execute(world, entity));
                    }
                }
            }
        }
        if (!(entity.getPersistentData().getDouble("IA") != 340.0 || entity instanceof LivingEntity && (_livEnt40 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN))) {
            if (Math.random() <= 0.5) {
                LivingEntity livingEntity9;
                if (entity instanceof Mob) {
                    _mobEnt = (Mob)entity;
                    livingEntity9 = _mobEnt.getTarget();
                } else {
                    livingEntity9 = null;
                }
                if (livingEntity9 instanceof LivingEntity && !world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)30.0, (double)30.0, (double)30.0), e -> true).isEmpty()) {
                    AttackGroundProcedure.execute(world, entity);
                }
            } else if (Math.random() <= 1.0) {
                LivingEntity livingEntity10;
                if (entity instanceof Mob) {
                    _mobEnt = (Mob)entity;
                    livingEntity10 = _mobEnt.getTarget();
                } else {
                    livingEntity10 = null;
                }
                if (livingEntity10 instanceof LivingEntity && !world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)30.0, (double)30.0, (double)30.0), e -> true).isEmpty()) {
                    AttackBruellenProcedure.execute(world, entity);
                }
            }
        }
        if (!(entity.getPersistentData().getDouble("IA") != 450.0 || entity instanceof LivingEntity && (_livEnt48 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN))) {
            if (Math.random() <= 0.5) {
                LivingEntity livingEntity11;
                if (entity instanceof Mob) {
                    _mobEnt = (Mob)entity;
                    livingEntity11 = _mobEnt.getTarget();
                } else {
                    livingEntity11 = null;
                }
                if (livingEntity11 instanceof LivingEntity && !world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)30.0, (double)30.0, (double)30.0), e -> true).isEmpty()) {
                    AttackRotProcedure.execute(world, x, z, entity);
                }
            } else if (Math.random() <= 1.0) {
                if (Math.random() <= 0.5) {
                    LivingEntity livingEntity12;
                    if (entity instanceof Mob) {
                        _mobEnt = (Mob)entity;
                        livingEntity12 = _mobEnt.getTarget();
                    } else {
                        livingEntity12 = null;
                    }
                    if (livingEntity12 instanceof LivingEntity) {
                        if (!world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                            AttackDoubleProcedure.execute(world, entity);
                        } else if (world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                            AttackFloatProcedure.execute(world, entity);
                        }
                    }
                } else if (Math.random() <= 1.0) {
                    LivingEntity livingEntity13;
                    if (entity instanceof Mob) {
                        _mobEnt = (Mob)entity;
                        livingEntity13 = _mobEnt.getTarget();
                    } else {
                        livingEntity13 = null;
                    }
                    if (livingEntity13 instanceof LivingEntity) {
                        if (!world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                            AttackOnceProcedure.execute(world, entity);
                        } else if (world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)14.0, (double)14.0, (double)14.0), e -> true).isEmpty()) {
                            AttackFloatProcedure.execute(world, entity);
                        }
                    }
                }
            }
        }
        if (!(entity.getPersistentData().getDouble("IA") != 520.0 || entity instanceof LivingEntity && (_livEnt61 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN))) {
            if (Math.random() <= 0.5) {
                LivingEntity livingEntity14;
                if (entity instanceof Mob) {
                    _mobEnt = (Mob)entity;
                    livingEntity14 = _mobEnt.getTarget();
                } else {
                    livingEntity14 = null;
                }
                if (livingEntity14 instanceof LivingEntity && !world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)30.0, (double)30.0, (double)30.0), e -> true).isEmpty()) {
                    AttackStompProcedure.execute(world, entity);
                }
            } else if (Math.random() <= 1.0) {
                LivingEntity livingEntity15;
                if (entity instanceof Mob) {
                    _mobEnt = (Mob)entity;
                    livingEntity15 = _mobEnt.getTarget();
                } else {
                    livingEntity15 = null;
                }
                if (livingEntity15 instanceof LivingEntity && !world.getEntitiesOfClass(Player.class, AABB.ofSize((Vec3)new Vec3(x, y, z), (double)30.0, (double)30.0, (double)30.0), e -> true).isEmpty()) {
                    AttackFloatProcedure.execute(world, entity);
                    MokelsBossfightSaphyraMod.queueServerWork(30, () -> {
                        AttackFloatProcedure.execute(world, entity);
                        MokelsBossfightSaphyraMod.queueServerWork(30, () -> AttackFloatProcedure.execute(world, entity));
                    });
                }
            }
        }
        if (entity.getPersistentData().getDouble("IA") == 620.0) {
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
                _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme1")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme1")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
        }
        if (entity.getPersistentData().getDouble("music") == 320.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme2")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme2")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
        }
        if (entity.getPersistentData().getDouble("music") == 620.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme3")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme3")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
        }
        if (entity.getPersistentData().getDouble("music") == 920.0 && entity.isAlive()) {
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
            if (world instanceof Level) {
                _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme4")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme4")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
        }
        if (entity.getPersistentData().getDouble("music") == 1220.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme5")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme5")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
        }
        if (entity.getPersistentData().getDouble("music") == 1520.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme6")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme6")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
        }
        if (entity.getPersistentData().getDouble("music") == 1820.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme7")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:namelesstheme7")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
            }
        }
        if (entity.getPersistentData().getDouble("music") == 1900.0) {
            entity.getPersistentData().putDouble("music", 0.0);
        }
        if (world instanceof ServerLevel) {
            _level = (ServerLevel)world;
            _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, (ServerLevel)_level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/tp @s ~ ~ ~ facing entity @p[distance=0..48]");
        }
        WalkToBlockProcedure.execute(world, x, y, z, entity);
    }
}





















