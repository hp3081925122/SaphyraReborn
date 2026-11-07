/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 */
package com.hp.saphyrareborn.procedures;

import java.util.Comparator;
import java.util.List;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.WitchbossEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttackAxeAttackProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isAlive()) {
            LivingEntity _entity;
            if (entity instanceof WitchbossEntity) {
                ((WitchbossEntity)entity).setAnimation("axeattack");
            }
            if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 100, false, false));
            }
            MokelsBossfightSaphyraMod.queueServerWork(30, () -> {
                LivingEntity _livEnt3;
                if (!(entity instanceof LivingEntity && (_livEnt3 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN) || !entity.isAlive())) {
                    if (world instanceof Level) {
                        _level1_level1 = (Level)world;
                        if (!_level1.isClientSide()) {
                            _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.player.attack.sweep")), SoundSource.NEUTRAL, 2.0f, 1.0f);
                        } else {
                            _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.player.attack.sweep")), SoundSource.NEUTRAL, 2.0f, 1.0f, false);
                        }
                    }
                    _center1enter = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                   _entfound1ity> _entfound1 = world.getEntitiesOfClass(Entity.class, new AABB(_center1, _center1).inflate(7.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center1))).toList();
                    for (Entity entityiterator : _entfound1) {
                        LivingEntity _livEnt14;
                        if (entityiterator instanceof WitchbossEntity || !(entityiterator instanceof LivingEntity) || entityiterator instanceof LivingEntity && (_livEnt14 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                        entityiterator.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), entity), 14.0f);
                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Grief + 20.0;
                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                            capability.Grief = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                }
            });
            MokelsBossfightSaphyraMod.queueServerWork(45, () -> {
                LivingEntity _livEnt19;
                if (!(entity instanceof LivingEntity && (_livEnt19 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN) || !entity.isAlive())) {
                    if (world instanceof Level) {
                 _level2 Level _level2 = (Level)world;
                        if (!_level2.isClientSide()) {
                            _level2.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.player.attack.sweep")), SoundSource.NEUTRAL, 2.0f, 1.0f);
                        } else {
                            _level2.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.player.attack.sweep")), SoundSource.NEUTRAL, 2.0f, 1.0f, false);
                        }
                    }
                    _center2enter = new Vec3(entity.getX(), entity.getY(), entity.getZ());
               _entfound2<Entity> _entfound2 = world.getEntitiesOfClass(Entity.class, new AABB(_center2, _center2).inflate(7.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center2))).toList();
                    for (Entity entityiterator : _entfound2) {
                        LivingEntity _livEnt30;
                        if (entityiterator instanceof WitchbossEntity || !(entityiterator instanceof LivingEntity) || entityiterator instanceof LivingEntity && (_livEnt30 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                        entityiterator.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), entity), 14.0f);
                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Grief + 20.0;
                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                            capability.Grief = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                }
            });
            MokelsBossfightSaphyraMod.queueServerWork(55, () -> {
                LivingEntity _livEnt35;
                if (!(entity instanceof LivingEntity && (_livEnt35 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN) || !entity.isAlive())) {
                    if (world instanceof Level) {
                        Level _level3 = (Level)world;
                        if (!_level3.isClientSide()) {
                            _level3.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.player.attack.sweep")), SoundSource.NEUTRAL, 2.0f, 1.0f);
                        } else {
                            _level3.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.player.attack.sweep")), SoundSource.NEUTRAL, 2.0f, 1.0f, false);
                        }
                    }
                    _center3enter = new Vec3(entity.getX(), entity.getY(), entity.getZ());
              _entfound3t<Entity> _entfound3 = world.getEntitiesOfClass(Entity.class, new AABB(_center3, _center3).inflate(7.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center3))).toList();
                    for (Entity entityiterator : _entfound3) {
                        LivingEntity _livEnt46;
                        if (entityiterator instanceof WitchbossEntity || !(entityiterator instanceof LivingEntity) || entityiterator instanceof LivingEntity && (_livEnt46 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                        entityiterator.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), entity), 14.0f);
                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Grief + 20.0;
                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                            capability.Grief = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                }
            });
            MokelsBossfightSaphyraMod.queueServerWork(87, () -> {
                LivingEntity _livEnt51;
                if (!(entity instanceof LivingEntity && (_livEnt51 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN) || !entity.isAlive())) {
                    if (world instanceof Level) {
                        Level _level4 = (Level)world;
                        if (!_level4.isClientSide()) {
                            _level4.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.explode")), SoundSource.NEUTRAL, 2.0f, 1.0f);
                        } else {
                            _level4.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.explode")), SoundSource.NEUTRAL, 2.0f, 1.0f, false);
                        }
                    }
                    _center4enter = new Vec3(entity.getX(), entity.getY(), entity.getZ());
             _entfound4st<Entity> _entfound4 = world.getEntitiesOfClass(Entity.class, new AABB(_center4, _center4).inflate(7.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center4))).toList();
                    for (Entity entityiterator : _entfound4) {
                        LivingEntity _livEnt62;
                        if (entityiterator instanceof WitchbossEntity || !(entityiterator instanceof LivingEntity) || entityiterator instanceof LivingEntity && (_livEnt62 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                        entityiterator.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), entity), 14.0f);
                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Grief + 20.0;
                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                            capability.Grief = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                }
            });
        }
    }
}




















