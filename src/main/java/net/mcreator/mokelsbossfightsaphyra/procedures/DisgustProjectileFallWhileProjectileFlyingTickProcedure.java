/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class DisgustProjectileFallWhileProjectileFlyingTickProcedure {
    public static void execute(LevelAccessor world, Entity immediatesourceentity) {
        if (immediatesourceentity == null) {
            return;
        }
        MokelsBossfightSaphyraMod.queueServerWork(1, () -> {
            if (world instanceof ServerLevel) {
                ServerLevel _level = (ServerLevel)world;
                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.PUKE_PARTICLE.get()), immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ(), 3, 0.1, 0.1, 0.1, 0.01);
            }
        });
        MokelsBossfightSaphyraMod.queueServerWork(10, () -> immediatesourceentity.setDeltaMovement(new Vec3(0.0, -1.0, 0.0)));
    }
}




