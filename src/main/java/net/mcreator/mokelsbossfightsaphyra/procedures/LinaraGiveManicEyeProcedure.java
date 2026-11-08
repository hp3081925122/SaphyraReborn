/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;

public class LinaraGiveManicEyeProcedure {
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
        if (itemStack.getItem() == MokelsBossfightSaphyraModItems.MANIC_EYE.get()) {
            if (sourceentity instanceof Player) {
                Player _player = (Player)sourceentity;
                ItemStack _stktoremove = new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.MANIC_EYE.get());
                _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, (Container)_player.getInventory());
                com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables.PlayerVariables vars = com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables.getPlayerVariables(_player);
                if (vars.LinaraQuest < 1) {
                    vars.LinaraQuest = 1;
                    vars.syncPlayerVariables(_player);
                }
            }
            if (world instanceof ServerLevel) {
                ServerLevel _level1 = (ServerLevel)world;
                Entity entityToSpawn = ((EntityType)MokelsBossfightSaphyraModEntities.LINARA_HALLOWED_TOWER_2.get()).spawn(_level1, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), MobSpawnType.MOB_SUMMONED);
                if (entityToSpawn != null) {
                    entityToSpawn.setYRot(entity.getYRot());
                    entityToSpawn.setYBodyRot(entity.getYRot());
                    entityToSpawn.setYHeadRot(entity.getYRot());
                    entityToSpawn.setXRot(entity.getXRot());
                    entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                }
            }
            if (!entity.level().isClientSide()) {
                entity.discard();
            }
        }
    }
}






















