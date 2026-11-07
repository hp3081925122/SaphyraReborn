/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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

public class AttackSmiteProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isAlive()) {
            LivingEntity _entity1;
            if (entity instanceof MothbossEntity) {
                ((MothbossEntity)entity).setAnimation("attacksmite");
            }
            if (entity instanceof LivingEntity && !(_entity1 = (LivingEntity)entity).level().isClientSide()) {
                _entity1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 100, false, false));
            }
            MokelsBossfightSaphyraMod.queueServerWork(27, () -> {
                LivingEntity _livEnt3;
                if (!(entity instanceof LivingEntity && (_livEnt3 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.STUN) || !entity.isAlive())) {
                    Vec3 _center1 = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                    List<Entity> _entfound1 = world.getEntitiesOfClass(Entity.class, new AABB(_center1, _center1).inflate(15.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center1))).toList();
                    for (Entity entityiterator : _entfound1) {
                        if (entityiterator instanceof MothbossEntity || !(entityiterator instanceof LivingEntity)) continue;
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
                            _entityToSpawn.setPos(entityiterator.getX(), 6.0 + entityiterator.getY(), entityiterator.getZ());
                            _entityToSpawn.shoot(0.0, -90.0, 0.0, 1.0f, 20.0f);
                            projectileLevel.addFreshEntity((Entity)_entityToSpawn);
                        }
                    }
                }
            });
        }
    }
}





