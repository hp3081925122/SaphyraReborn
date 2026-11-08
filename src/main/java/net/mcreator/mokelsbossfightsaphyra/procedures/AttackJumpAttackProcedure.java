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
import com.hp.saphyrareborn.entity.WitchbossEntity;
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

public class AttackJumpAttackProcedure {
    public static void execute(LevelAccessor world, double x, double z, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isAlive()) {
            LivingEntity _entity1;
            if (entity instanceof WitchbossEntity) {
                ((WitchbossEntity)entity).setAnimation("jumpattack");
            }
            if (entity instanceof LivingEntity && !(_entity1 = (LivingEntity)entity).level().isClientSide()) {
                com.hp.saphyrareborn.boss.BossCastLock.lock(_entity1, 9);
            }
            if (entity instanceof LivingEntity && !(_entity1 = (LivingEntity)entity).level().isClientSide()) {
                _entity1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1, false, false));
            }
            MokelsBossfightSaphyraMod.queueServerWork(50, () -> {
                LivingEntity _livEnt4;
                if (!(entity instanceof LivingEntity && (_livEnt4 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN) || !entity.isAlive())) {
                    Level _level1;
                    LivingEntity _entity2;
                    if (entity instanceof LivingEntity && !(_entity2 = (LivingEntity)entity).level().isClientSide()) {
                        com.hp.saphyrareborn.boss.BossCastLock.lock(_entity2, 17);
                    }
                    if (world instanceof Level) {
                        _level1 = (Level)world;
                        if (!_level1.isClientSide()) {
                            _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.explode")), SoundSource.NEUTRAL, 2.0f, 1.0f);
                        } else {
                            _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.explode")), SoundSource.NEUTRAL, 2.0f, 1.0f, false);
                        }
                    }
                    if (world instanceof ServerLevel) {
                        _level1 = (ServerLevel)world;
                        ((ServerLevel)_level1).sendParticles((ParticleOptions)ParticleTypes.EXPLOSION, entity.getX() + entity.getLookAngle().x * 4.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 4.0, 100, 3.0, 0.5, 3.0, 0.01);
                    }
                    Vec3 _center1 = new Vec3(entity.getX() + entity.getLookAngle().x * 4.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 4.0);
                    List<Entity> _entfound1 = world.getEntitiesOfClass(Entity.class, new AABB(_center1, _center1).inflate(6.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center1))).toList();
                    for (Entity entityiterator : _entfound1) {
                        LivingEntity _livEnt20;
                        if (entityiterator instanceof WitchbossEntity || !(entityiterator instanceof LivingEntity) || entityiterator instanceof LivingEntity && (_livEnt20 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                        entityiterator.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK)), com.hp.saphyrareborn.boss.BossAttributes.skillDamage(entity, 1.0));
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





