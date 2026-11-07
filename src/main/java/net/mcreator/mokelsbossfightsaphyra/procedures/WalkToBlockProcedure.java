/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;

public class WalkToBlockProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) {
            return;
        }
        if (world.getBlockState(BlockPos.containing((double)x, (double)y, (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.AZURE_BRICKS_ANIMATED.get() && entity instanceof Mob) {
            Mob _entity = (Mob)entity;
            _entity.getNavigation().moveTo(x, y, z, 2.0);
        }
    }
}






















