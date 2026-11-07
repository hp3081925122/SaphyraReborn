/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
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
import com.hp.saphyrareborn.entity.MothbossEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttackGroundMothProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isAlive()) {
            LivingEntity _entity1;
            if (entity instanceof MothbossEntity) {
                ((MothbossEntity)entity).setAnimation("attackground");
            }
            if (entity instanceof LivingEntity && !(_entity1 = (LivingEntity)entity).level().isClientSide()) {
                _entity1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 100, false, false));
            }
            MokelsBossfightSaphyraMod.queueServerWork(34, () -> {
                LivingEntity _livEnt3;
                if (!(entity instanceof LivingEntity && (_livEnt3 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN) || !entity.isAlive())) {
                    Level _level1;
                    if (world instanceof Level) {
                        _level1 = (Level)world;
                        if (!_level1.isClientSide()) {
                            _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.explode")), SoundSource.NEUTRAL, 2.0f, 0.75f);
                        } else {
                            _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.explode")), SoundSource.NEUTRAL, 2.0f, 0.75f, false);
                        }
                    }
                    if (world instanceof ServerLevel) {
                        _level1 = (ServerLevel)world;
                        ((ServerLevel)_level1).sendParticles((ParticleOptions)ParticleTypes.EXPLOSION, entity.getX(), entity.getY(), entity.getZ(), 160, 2.0, 0.5, 2.0, 0.01);
                    }
                    Vec3 _center1 = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                    List<Entity> _entfound1 = world.getEntitiesOfClass(Entity.class, new AABB(_center1, _center1).inflate(5.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center1))).toList();
                    for (Entity entityiterator : _entfound1) {
                        LivingEntity _livEnt18;
                        if (entityiterator instanceof MothbossEntity || !(entityiterator instanceof LivingEntity) || entityiterator instanceof LivingEntity && (_livEnt18 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                        entityiterator.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), entity), 14.0f);
                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Grief + 20.0;
                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                            capability.Grief = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                }
            });
            MokelsBossfightSaphyraMod.queueServerWork(48, () -> {
                LivingEntity _livEnt23;
                if (!(entity instanceof LivingEntity && (_livEnt23 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN) || !entity.isAlive())) {
                    if (world instanceof Level) {
                        Level _level2 = (Level)world;
                        if (!_level2.isClientSide()) {
                            _level2.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.player.attack.sweep")), SoundSource.NEUTRAL, 2.0f, 1.2f);
                        } else {
                            _level2.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.player.attack.sweep")), SoundSource.NEUTRAL, 2.0f, 1.2f, false);
                        }
                    }
                    Vec3 _center2 = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                    List<Entity> _entfound2 = world.getEntitiesOfClass(Entity.class, new AABB(_center2, _center2).inflate(8.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center2))).toList();
                    for (Entity entityiterator : _entfound2) {
                        LivingEntity _livEnt34;
                        if (entityiterator instanceof MothbossEntity || !(entityiterator instanceof LivingEntity) || entityiterator instanceof LivingEntity && (_livEnt34 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
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





