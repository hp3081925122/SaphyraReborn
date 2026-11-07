package com.hp.saphyrareborn.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public final class RightClickBossDoorKeyProcedure {
    private RightClickBossDoorKeyProcedure() {
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        BossDoorOnBlockRightClickedProcedure.execute(world, x, y, z);
    }
}




















