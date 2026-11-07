/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class AttackDashForthProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (world instanceof ServerLevel) {
            ServerLevel _level1 = (ServerLevel)world;
            ((ServerLevel)_level1).sendParticles((ParticleOptions)ParticleTypes.POOF, entity.getX(), entity.getY(), entity.getZ(), 20, 0.2, 0.2, 0.2, 0.05);
        }
        entity.setDeltaMovement(new Vec3(0.0, 1.0, 0.0));
        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
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
            double d = (livingEntity6.getX() - entity.getX()) * 0.25;
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
            double d3 = (livingEntity4.getY() - entity.getY()) * 0.25;
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
            double d5 = (livingEntity2.getZ() - entity.getZ()) * 0.25;
            if (entity instanceof Mob) {
                _mobEnt = (Mob)entity;
                livingEntity = _mobEnt.getTarget();
            } else {
                livingEntity = null;
            }
            entity.setDeltaMovement(new Vec3(d2, d4, d5 - (livingEntity.getZ() - entity.getZ()) * 0.15));
        });
    }
}





