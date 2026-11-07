/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class NoGravityOnEffectActiveTickProcedure {
    public static void execute(Entity entity) {
        if (entity == null) {
            return;
        }
        entity.setDeltaMovement(new Vec3(1.0 * entity.getLookAngle().x, 0.3 * entity.getLookAngle().y, 1.0 * entity.getLookAngle().z));
    }
}




















