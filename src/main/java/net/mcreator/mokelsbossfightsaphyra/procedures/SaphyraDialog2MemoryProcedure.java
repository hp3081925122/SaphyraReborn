/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.EntitySpawnReason
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

public class SaphyraDialog2MemoryProcedure {
    public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
        ItemStack itemStack;
        if (entity == null || sourceentity == null) {
            return;
        }
        if (sourceentity instanceof LivingEntity) {
            LivingEntity _livEnt = (LivingEntity)sourceentity;
            itemStack = _livEnt.getMainHandItem();
        } else {
            itemStack = ItemStack.EMPTY;
        }
        if (itemStack.getItem() == MokelsBossfightSaphyraModItems.NAMELESS_GATEKEEPER_REMEMBRANCE.get()) {
            if (!entity.level().isClientSide()) {
                entity.discard();
            }
            if (world instanceof ServerLevel) {
                ServerLevel _level1 = (ServerLevel)world;
                Entity entityToSpawn = ((EntityType)MokelsBossfightSaphyraModEntities.SAPHYRA_DIALOG_3.get()).spawn(_level1, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), EntitySpawnReason.MOB_SUMMONED);
                if (entityToSpawn != null) {
                    entityToSpawn.setYRot(entity.getYRot());
                    entityToSpawn.setYBodyRot(entity.getYRot());
                    entityToSpawn.setYHeadRot(entity.getYRot());
                    entityToSpawn.setXRot(entity.getXRot());
                    entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                }
            }
        }
    }
}






















