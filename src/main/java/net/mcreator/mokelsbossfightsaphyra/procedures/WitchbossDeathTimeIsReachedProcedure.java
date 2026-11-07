/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

public class WitchbossDeathTimeIsReachedProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        ServerLevel _level;
        if (entity == null) {
            return;
        }
        if (world instanceof ServerLevel) {
            _level = (ServerLevel)world;
            Entity entityToSpawn = ((EntityType)MokelsBossfightSaphyraModEntities.MOTHBOSS.get()).spawn(_level, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
                entityToSpawn.setYRot(entity.getYRot());
                entityToSpawn.setYBodyRot(entity.getYRot());
                entityToSpawn.setYHeadRot(entity.getYRot());
                entityToSpawn.setXRot(entity.getXRot());
                entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
            }
        }
        if (world instanceof ServerLevel) {
            _level = (ServerLevel)world;
            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY(), entity.getZ(), 800, 1.0, 2.0, 1.0, 0.05);
        }
    }
}





















