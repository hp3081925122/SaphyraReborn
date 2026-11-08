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
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
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
import com.hp.saphyrareborn.procedures.AttackBackProcedure;
import com.hp.saphyrareborn.procedures.AttackDashBackProcedure;
import com.hp.saphyrareborn.procedures.AttackDashForthProcedure;
import com.hp.saphyrareborn.procedures.AttackGroundMothProcedure;
import com.hp.saphyrareborn.procedures.AttackSmiteProcedure;
import com.hp.saphyrareborn.procedures.AttackSwordOnceProcedure;
import com.hp.saphyrareborn.procedures.AttackskyProcedure;
import com.hp.saphyrareborn.procedures.AttacktwirlProcedure;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
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

public class MothbossAttackPatternProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        Level _level1;
        LivingEntity livingEntity;
        LivingEntity livingEntity2;
        Mob _mobEnt;
        if (entity == null) {
            return;
        }
        if (entity instanceof Mob) {
            _mobEnt = (Mob)entity;
            livingEntity2 = _mobEnt.getTarget();
        } else {
            livingEntity2 = null;
        }
        if (livingEntity2 != null) {
            entity.getPersistentData().putDouble("IA", entity.getPersistentData().getDouble("IA") + 1.0);
        }
        if (entity.getPersistentData().getDouble("IA") == 20.0) {
            if (Math.random() < 0.5) {
                if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 14.0, 14.0, 14.0), e -> true).isEmpty()) {
                    AttackDashBackProcedure.execute(world, entity);
                    AttackSmiteProcedure.execute(world, entity);
                } else {
                    AttackSmiteProcedure.execute(world, entity);
                }
            } else if (Math.random() < 1.0) {
                if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 14.0, 14.0, 14.0), e -> true).isEmpty()) {
                    AttackSwordOnceProcedure.execute(world, entity);
                } else {
                    AttackDashForthProcedure.execute(world, entity);
                    AttackSwordOnceProcedure.execute(world, entity);
                }
            }
        }
        if (entity.getPersistentData().getDouble("IA") == 120.0) {
            if (Math.random() < 0.5) {
                if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 14.0, 14.0, 14.0), e -> true).isEmpty()) {
                    AttacktwirlProcedure.execute(world, entity);
                } else {
                    AttacktwirlProcedure.execute(world, entity);
                    AttackDashForthProcedure.execute(world, entity);
                }
            } else if (Math.random() < 1.0) {
                if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 14.0, 14.0, 14.0), e -> true).isEmpty()) {
                    AttackGroundMothProcedure.execute(world, entity);
                } else {
                    AttackDashForthProcedure.execute(world, entity);
                    AttackGroundMothProcedure.execute(world, entity);
                }
            }
        }
        if (entity.getPersistentData().getDouble("IA") == 240.0) {
            if (Math.random() < 0.5) {
                if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 14.0, 14.0, 14.0), e -> true).isEmpty()) {
                    AttackskyProcedure.execute(world, entity);
                    AttackDashBackProcedure.execute(world, entity);
                } else {
                    AttackskyProcedure.execute(world, entity);
                }
            } else if (Math.random() < 1.0) {
                if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 14.0, 14.0, 14.0), e -> true).isEmpty()) {
                    AttackBackProcedure.execute(world, entity);
                    MokelsBossfightSaphyraMod.queueServerWork(35, () -> AttackSwordOnceProcedure.execute(world, entity));
                } else {
                    AttackBackProcedure.execute(world, entity);
                    AttackDashForthProcedure.execute(world, entity);
                    MokelsBossfightSaphyraMod.queueServerWork(35, () -> {
                        AttackDashForthProcedure.execute(world, entity);
                        AttackSwordOnceProcedure.execute(world, entity);
                    });
                }
            }
        }
        if (entity.getPersistentData().getDouble("IA") == 320.0) {
            entity.getPersistentData().putDouble("IA", 0.0);
        }
        if (entity instanceof Mob) {
            _mobEnt = (Mob)entity;
            livingEntity = _mobEnt.getTarget();
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
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:ivorydevil_theme1")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:ivorydevil_theme1")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                com.hp.saphyrareborn.boss.BossMusic.stop(_level1, entity.getX(), entity.getY(), entity.getZ(), null);
            }
        }
        if (entity.getPersistentData().getDouble("music") == 320.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level1 = (Level)world;
                if (!_level1.isClientSide()) {
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:ivorydevil_theme2")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:ivorydevil_theme2")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                com.hp.saphyrareborn.boss.BossMusic.stop(_level1, entity.getX(), entity.getY(), entity.getZ(), null);
            }
        }
        if (entity.getPersistentData().getDouble("music") == 620.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level1 = (Level)world;
                if (!_level1.isClientSide()) {
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:ivorydevil_theme3")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:ivorydevil_theme3")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                com.hp.saphyrareborn.boss.BossMusic.stop(_level1, entity.getX(), entity.getY(), entity.getZ(), null);
            }
        }
        if (entity.getPersistentData().getDouble("music") == 920.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level1 = (Level)world;
                if (!_level1.isClientSide()) {
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:ivorydevil_theme4")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:ivorydevil_theme4")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                com.hp.saphyrareborn.boss.BossMusic.stop(_level1, entity.getX(), entity.getY(), entity.getZ(), null);
            }
        }
        if (entity.getPersistentData().getDouble("music") == 1220.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level1 = (Level)world;
                if (!_level1.isClientSide()) {
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:ivorydevil_theme5")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:ivorydevil_theme5")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                com.hp.saphyrareborn.boss.BossMusic.stop(_level1, entity.getX(), entity.getY(), entity.getZ(), null);
            }
        }
        if (entity.getPersistentData().getDouble("music") == 1520.0 && entity.isAlive()) {
            if (world instanceof Level) {
                _level1 = (Level)world;
                if (!_level1.isClientSide()) {
                    _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:ivorydevil_theme6")), SoundSource.MUSIC, 2.0f, 1.0f);
                } else {
                    _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:ivorydevil_theme6")), SoundSource.MUSIC, 2.0f, 1.0f, false);
                }
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                com.hp.saphyrareborn.boss.BossMusic.stop(_level1, entity.getX(), entity.getY(), entity.getZ(), null);
            }
        }
        if (entity.getPersistentData().getDouble("music") == 1780.0) {
            entity.getPersistentData().putDouble("music", 0.0);
        }
    }
}






















