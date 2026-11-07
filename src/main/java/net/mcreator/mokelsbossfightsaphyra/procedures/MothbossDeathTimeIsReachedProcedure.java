/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class MothbossDeathTimeIsReachedProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        ServerLevel _level;
        if (entity == null) {
            return;
        }
        if (world instanceof ServerLevel) {
            _level = (ServerLevel)world;
            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY(), entity.getZ(), 800, 0.6, 1.7, 0.6, 0.01);
        }
        if (world instanceof ServerLevel) {
            _level = (ServerLevel)world;
            ItemEntity entityToSpawn = new ItemEntity((Level)_level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.BOTTOMLESS_BAG.get()));
            entityToSpawn.setPickUpDelay(2);
            entityToSpawn.setUnlimitedLifetime();
            _level.addFreshEntity((Entity)entityToSpawn);
        }
    }
}






















