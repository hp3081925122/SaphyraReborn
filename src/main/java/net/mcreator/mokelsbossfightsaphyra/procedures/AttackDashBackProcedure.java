/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class AttackDashBackProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isAlive()) {
            entity.setDeltaMovement(new Vec3(-3.0 * entity.getLookAngle().x, 0.15, -3.0 * entity.getLookAngle().z));
            if (world instanceof ServerLevel) {
                ServerLevel _level = (ServerLevel)world;
                _level.sendParticles((ParticleOptions)ParticleTypes.POOF, entity.getX(), entity.getY(), entity.getZ(), 20, 0.2, 0.2, 0.2, 0.05);
            }
        }
    }
}




