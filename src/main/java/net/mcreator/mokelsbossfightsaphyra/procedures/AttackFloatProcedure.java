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
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
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
import com.hp.saphyrareborn.entity.NamelessEntity;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttackFloatProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        LivingEntity _livEnt1;
        if (entity == null) {
            return;
        }
        if (!(!entity.isAlive() || entity instanceof LivingEntity && (_livEnt1 = (LivingEntity)entity).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.STUN.get()))) {
            MokelsBossfightSaphyraMod.queueServerWork(1, () -> {
                LivingEntity _livEnt2;
                if (!(entity instanceof LivingEntity && (_livEnt2 = (LivingEntity)entity).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.STUN.get()) || !entity.isAlive())) {
                    entity.setDeltaMovement(new Vec3(-1.0 * entity.getLookAngle().x, 1.5, -1.0 * entity.getLookAngle().z));
                    if (world instanceof ServerLevel) {
                        ServerLevel _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)ParticleTypes.POOF, entity.getX(), entity.getY(), entity.getZ(), 10, 0.5, 0.5, 0.5, 0.1);
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                        LivingEntity _livEnt11;
                        if (!(entity instanceof LivingEntity) || !(_livEnt11 = (LivingEntity)entity).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.STUN.get())) {
                            if (entity instanceof NamelessEntity) {
                                ((NamelessEntity)entity).setAnimation("attackfloat");
                            }
                            if (entity.isAlive()) {
                                LivingEntity livingEntity;
                                LivingEntity livingEntity2;
                                LivingEntity livingEntity3;
                                LivingEntity livingEntity4;
                                LivingEntity livingEntity5;
                                Mob _mobEnt;
                                LivingEntity livingEntity6;
                                Mob _mobEnt2;
                                if (entity instanceof Mob) {
                                    _mobEnt2 = (Mob)entity;
                                    livingEntity6 = _mobEnt2.getTarget();
                                } else {
                                    livingEntity6 = null;
                                }
                                double d = (livingEntity6.getX() - entity.getX()) * 0.5;
                                if (entity instanceof Mob) {
                                    _mobEnt = (Mob)entity;
                                    livingEntity5 = _mobEnt.getTarget();
                                } else {
                                    livingEntity5 = null;
                                }
                                double d2 = d - (livingEntity5.getX() - entity.getX()) * 0.15;
                                if (entity instanceof Mob) {
                                    _mobEnt2 = (Mob)entity;
                                    livingEntity4 = _mobEnt2.getTarget();
                                } else {
                                    livingEntity4 = null;
                                }
                                double d3 = (livingEntity4.getY() - entity.getY()) * 0.5;
                                if (entity instanceof Mob) {
                                    _mobEnt = (Mob)entity;
                                    livingEntity3 = _mobEnt.getTarget();
                                } else {
                                    livingEntity3 = null;
                                }
                                double d4 = d3 - (livingEntity3.getY() - entity.getY()) * 0.15 + 0.15;
                                if (entity instanceof Mob) {
                                    _mobEnt2 = (Mob)entity;
                                    livingEntity2 = _mobEnt2.getTarget();
                                } else {
                                    livingEntity2 = null;
                                }
                                double d5 = (livingEntity2.getZ() - entity.getZ()) * 0.5;
                                if (entity instanceof Mob) {
                                    _mobEnt = (Mob)entity;
                                    livingEntity = _mobEnt.getTarget();
                                } else {
                                    livingEntity = null;
                                }
                                entity.setDeltaMovement(new Vec3(d2, d4, d5 - (livingEntity.getZ() - entity.getZ()) * 0.15));
                                MokelsBossfightSaphyraMod.queueServerWork(3, () -> {
                                    LivingEntity _livEnt33;
                                    if (!(entity instanceof LivingEntity && (_livEnt33 = (LivingEntity)entity).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.STUN.get()) || !entity.isAlive())) {
                                        entity.setDeltaMovement(new Vec3(0.0, -0.5, 0.0));
                                        if (world instanceof Level) {
                                            Level _level = (Level)world;
                                            if (!_level.isClientSide()) {
                                                _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.NEUTRAL, 2.0f, 1.0f);
                                            } else {
                                                _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getValue(new ResourceLocation("entity.player.attack.sweep")), SoundSource.NEUTRAL, 2.0f, 1.0f, false);
                                            }
                                        }
                                        Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                                        List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                        for (Entity entityiterator : _entfound) {
                                            LivingEntity _livEnt45;
                                            if (entityiterator instanceof NamelessEntity || !(entityiterator instanceof LivingEntity) || entityiterator instanceof LivingEntity && (_livEnt45 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                            entityiterator.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), entity), 14.0f);
                                            double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 10.0;
                                            entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                                capability.Rotting = _setval;
                                                capability.syncPlayerVariables(entityiterator);
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
    }
}




