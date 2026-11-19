package com.hp.saphyrareborn.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class StayStillOnEffectActiveTickProcedure {
    public static void execute(Entity entity) {
        if (entity == null) {
            return;
        }
        entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
    }
}
