/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 */
package com.hp.saphyrareborn.procedures;

import net.minecraft.world.entity.Entity;

public class MirrorBladeProjectileProjectileHitsBlockProcedure {
    public static void execute(Entity immediatesourceentity) {
        if (immediatesourceentity == null) {
            return;
        }
        if (!immediatesourceentity.level().isClientSide()) {
            immediatesourceentity.discard();
        }
    }
}



















