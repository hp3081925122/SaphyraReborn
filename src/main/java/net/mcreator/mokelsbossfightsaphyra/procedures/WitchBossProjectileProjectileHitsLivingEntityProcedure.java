/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class WitchBossProjectileProjectileHitsLivingEntityProcedure {
    public static void execute(LevelAccessor world, Entity immediatesourceentity) {
        if (immediatesourceentity == null) {
            return;
        }
        MokelsBossfightSaphyraMod.queueServerWork(1, () -> {
            if (!immediatesourceentity.level().isClientSide()) {
                immediatesourceentity.discard();
            }
        });
    }
}






















