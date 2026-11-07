/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Holder
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class SwordTearAndDropletEntitySwingsItemProcedure {
    public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null) {
            return;
        }
        if (world.getLevelData().isThundering()) {
            entity.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), sourceentity), 15.0f);
            if (world instanceof ServerLevel) {
                ServerLevel _level1 = (ServerLevel)world;
                ((ServerLevel)_level1).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), 1.0 + entity.getY(), entity.getZ(), 30, 0.2, 0.2, 0.2, 0.03);
            }
        } else if (world.getLevelData().isRaining()) {
            entity.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), sourceentity), 7.5f);
            if (world instanceof ServerLevel) {
                ServerLevel _level2 = (ServerLevel)world;
                ((ServerLevel)_level2).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), 1.0 + entity.getY(), entity.getZ(), 30, 0.2, 0.2, 0.2, 0.03);
            }
        }
    }
}






















