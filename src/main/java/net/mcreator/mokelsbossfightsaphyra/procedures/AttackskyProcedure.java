/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import java.util.Comparator;
import java.util.List;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.MothbossEntity;
import com.hp.saphyrareborn.entity.WitchBossProjectilefallEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AttackskyProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isAlive()) {
            LivingEntity _entity;
            if (entity instanceof MothbossEntity) {
                ((MothbossEntity)entity).setAnimation("attacksky");
            }
            if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 90, 100, false, false));
            }
            MokelsBossfightSaphyraMod.queueServerWork(25, () -> {
                LivingEntity _livEnt3;
                if (!(entity instanceof LivingEntity && (_livEnt3 = (LivingEntity)entity).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.STUN.get()) || !entity.isAlive())) {
                    Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                    List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                    for (Entity entityiterator : _entfound) {
                        LivingEntity _entity;
                        LivingEntity _livEnt14;
                        if (entityiterator instanceof MothbossEntity || !(entityiterator instanceof LivingEntity)) continue;
                        if (world instanceof ServerLevel) {
                            ServerLevel _level = (ServerLevel)world;
                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), 1.0 + entity.getY(), entity.getZ(), 70, 0.1, 0.2, 0.1, 0.04);
                        }
                        if (entityiterator instanceof LivingEntity && (_livEnt14 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                        if (entityiterator instanceof LivingEntity && !(_entity = (LivingEntity)entityiterator).level().isClientSide()) {
                            _entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 25, 10, false, false));
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(25, () -> {
                            if (entity.isAlive()) {
                                for (int index0 = 0; index0 < 8; ++index0) {
                                    if (!(world instanceof ServerLevel)) continue;
                                    ServerLevel projectileLevel = (ServerLevel)world;
                                    Projectile _entityToSpawn = new Object(){

                                        public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
                                            WitchBossProjectilefallEntity entityToSpawn = new WitchBossProjectilefallEntity((EntityType<? extends WitchBossProjectilefallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.WITCH_BOSS_PROJECTILEFALL.get()), level);
                                            entityToSpawn.setOwner(shooter);
                                            entityToSpawn.setBaseDamage(damage);
                                            entityToSpawn.setKnockback(knockback);
                                            entityToSpawn.setSilent(true);
                                            entityToSpawn.setPierceLevel(piercing);
                                            return entityToSpawn;
                                        }
                                    }.getArrow((Level)projectileLevel, entity, 14.0f, 0, (byte)1);
                                    _entityToSpawn.setPos(entityiterator.getX(), -4.0 + entityiterator.getY(), entityiterator.getZ());
                                    _entityToSpawn.shoot(0.0, 90.0, 0.0, 1.0f, 7.5f);
                                    projectileLevel.addFreshEntity((Entity)_entityToSpawn);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}




