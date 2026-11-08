/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.WitchbossEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

public class AttackRainAttackProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isAlive()) {
            LivingEntity _entity1;
            if (entity instanceof WitchbossEntity) {
                ((WitchbossEntity)entity).setAnimation("rainattack");
            }
            if (entity instanceof LivingEntity && !(_entity1 = (LivingEntity)entity).level().isClientSide()) {
                com.hp.saphyrareborn.boss.BossCastLock.lock(_entity1, 50);
            }
            if (entity.isAlive()) {
                if (world instanceof ServerLevel) {
                    ServerLevel _level1 = (ServerLevel)world;
                    Entity entityToSpawn = ((EntityType)MokelsBossfightSaphyraModEntities.MOONBEAM.get()).spawn(_level1, BlockPos.containing((double)entity.getX(), (double)(entity.getY() + 4.0), (double)entity.getZ()), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                    }
                }
                MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                    if (entity.isAlive()) {
                        if (world instanceof ServerLevel) {
                            ServerLevel _level2 = (ServerLevel)world;
                            Entity entityToSpawn = ((EntityType)MokelsBossfightSaphyraModEntities.MOONBEAM.get()).spawn(_level2, BlockPos.containing((double)entity.getX(), (double)(entity.getY() + 4.0), (double)entity.getZ()), MobSpawnType.MOB_SUMMONED);
                            if (entityToSpawn != null) {
                                entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                            }
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                            if (entity.isAlive() && world instanceof ServerLevel) {
                                ServerLevel _level3 = (ServerLevel)world;
                                Entity entityToSpawn = ((EntityType)MokelsBossfightSaphyraModEntities.MOONBEAM.get()).spawn(_level3, BlockPos.containing((double)entity.getX(), (double)(entity.getY() + 4.0), (double)entity.getZ()), MobSpawnType.MOB_SUMMONED);
                                if (entityToSpawn != null) {
                                    entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                                }
                            }
                        });
                    }
                });
            }
        }
    }
}





