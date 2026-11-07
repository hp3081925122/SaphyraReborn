/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
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
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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

public class AttackGroundAttackProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isAlive()) {
            LivingEntity _entity;
            if (entity instanceof WitchbossEntity) {
                ((WitchbossEntity)entity).setAnimation("attackground");
            }
            if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 130, 100, false, false));
            }
            MokelsBossfightSaphyraMod.queueServerWork(41, () -> {
                LivingEntity _livEnt3;
                if (!(entity instanceof LivingEntity && (_livEnt3 = (LivingEntity)entity).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.STUN.get()) || !entity.isAlive())) {
                    Level _level;
                    if (world instanceof Level) {
                        _level = (Level)world;
                        if (!_level.isClientSide()) {
                            _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.NEUTRAL, 2.0f, 1.0f);
                        } else {
                            _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.NEUTRAL, 2.0f, 1.0f, false);
                        }
                    }
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY(), entity.getZ(), 900, 8.0, 0.5, 8.0, 0.01);
                    }
                    Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                    List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(18.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                    for (Entity entityiterator : _entfound) {
                        LivingEntity _livEnt18;
                        if (entityiterator instanceof WitchbossEntity || !(entityiterator instanceof LivingEntity) || entityiterator instanceof LivingEntity && (_livEnt18 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                        entityiterator.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK)), 14.0f);
                        double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Grief + 40.0;
                        entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                            capability.Grief = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                }
            });
            MokelsBossfightSaphyraMod.queueServerWork(64, () -> {
                LivingEntity _livEnt23;
                if (!(entity instanceof LivingEntity && (_livEnt23 = (LivingEntity)entity).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.STUN.get()) || !entity.isAlive())) {
                    Level _level;
                    if (world instanceof Level) {
                        _level = (Level)world;
                        if (!_level.isClientSide()) {
                            _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.NEUTRAL, 2.0f, 1.0f);
                        } else {
                            _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.NEUTRAL, 2.0f, 1.0f, false);
                        }
                    }
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY(), entity.getZ(), 900, 8.0, 0.5, 8.0, 0.01);
                    }
                    Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                    List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(18.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                    for (Entity entityiterator : _entfound) {
                        LivingEntity _livEnt38;
                        if (entityiterator instanceof WitchbossEntity || !(entityiterator instanceof LivingEntity) || entityiterator instanceof LivingEntity && (_livEnt38 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                        entityiterator.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK)), 14.0f);
                        double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Grief + 40.0;
                        entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                            capability.Grief = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                }
            });
            MokelsBossfightSaphyraMod.queueServerWork(102, () -> {
                LivingEntity _livEnt43;
                if (!(entity instanceof LivingEntity && (_livEnt43 = (LivingEntity)entity).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.STUN.get()) || !entity.isAlive())) {
                    Level _level;
                    if (world instanceof Level) {
                        _level = (Level)world;
                        if (!_level.isClientSide()) {
                            _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.NEUTRAL, 2.0f, 1.0f);
                        } else {
                            _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.NEUTRAL, 2.0f, 1.0f, false);
                        }
                    }
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY(), entity.getZ(), 900, 8.0, 0.5, 8.0, 0.01);
                    }
                    Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                    List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(18.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                    for (Entity entityiterator : _entfound) {
                        LivingEntity _livEnt58;
                        if (entityiterator instanceof WitchbossEntity || !(entityiterator instanceof LivingEntity) || entityiterator instanceof LivingEntity && (_livEnt58 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                        entityiterator.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK)), 30.0f);
                        double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Grief + 40.0;
                        entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                            capability.Grief = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                }
            });
        }
    }
}




