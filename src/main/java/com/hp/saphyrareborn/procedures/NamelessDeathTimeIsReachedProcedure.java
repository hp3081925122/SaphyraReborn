package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class NamelessDeathTimeIsReachedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        ItemEntity entityToSpawn;
        ServerLevel _level1;
        if (world instanceof ServerLevel) {
            _level1 = (ServerLevel)world;
            entityToSpawn = new ItemEntity((Level)_level1, x, y, z, new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.NAMELESS_GATEKEEPER_REMEMBRANCE.get()));
            entityToSpawn.setPickUpDelay(2);
            entityToSpawn.setUnlimitedLifetime();
            _level1.addFreshEntity((Entity)entityToSpawn);
        }
        if (world instanceof ServerLevel) {
            _level1 = (ServerLevel)world;
            entityToSpawn = new ItemEntity((Level)_level1, x, y, z, new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.KEY_4.get()));
            entityToSpawn.setPickUpDelay(2);
            entityToSpawn.setUnlimitedLifetime();
            _level1.addFreshEntity((Entity)entityToSpawn);
        }
    }
}
