/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public class NamelessProjectileFallWhileProjectileFlyingTickProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        ServerLevel _level;
        if (world instanceof ServerLevel) {
            _level = (ServerLevel)world;
            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 1, 0.1, 0.1, 0.1, 0.01);
        }
        if (world instanceof ServerLevel) {
            _level = (ServerLevel)world;
            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 1, 0.1, 0.1, 0.1, 0.01);
        }
    }
}





















