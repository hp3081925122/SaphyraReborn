/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.particles.SimpleParticleType
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
 *  net.minecraft.world.level.block.Blocks
 *  net.neoforged.neoforge.items.ItemHandlerHelper
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
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
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class OddmentStage2OnBlockRightClickedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        block12: {
            Player _player;
            block11: {
                ItemStack itemStack;
                ItemStack itemStack2;
                if (entity == null) {
                    return;
                }
                Object object = MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN.get();
                if (entity instanceof LivingEntity) {
                    _livEnt1ntity _livEnt1 = (LivingEntity)entity;
                    itemStack2 = _livEnt1.getOffhandItem();
                } else {
                    itemStack2 = ItemStack.EMPTY;
                }
                if (object == itemStack2.getItem()) break block11;
                Object object2 = MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN.get();
                if (entity instanceof LivingEntity) {
                    _livEnt2ntity _livEnt2 = (LivingEntity)entity;
                    itemStack = _livEnt2.getMainHandItem();
                } else {
                    itemStack = ItemStack.EMPTY;
                }
                if (object2 != itemStack.getItem()) break block12;
            }
            if (entity instanceof Player) {
                _player = (Player)entity;
                ItemStack _stktoremove = new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN.get());
                _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, (Container)_player.getInventory());
            }
            if (entity instanceof Player) {
                _player = (Player)entity;
                ItemStack _setstack = new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.BROKEN_SIGIL_OF_THE_RED_SUN.get()).copy();
                _setstack.setCount(1);
                ItemHandlerHelper.giveItemToPlayer((Player)_player, (ItemStack)_setstack);
            }
            if (world instanceof ServerLevel) {
                ServerLevel _level = (ServerLevel)world;
                _level.sendParticles((ParticleOptions)ParticleTypes.POOF, x, y, z, 200, 0.5, 0.75, 0.5, 0.05);
            }
            world.setBlock(BlockPos.containing((double)x, (double)y, (double)z), Blocks.AIR.defaultBlockState(), 3);
            MokelsBossfightSaphyraMod.queueServerWork(10, () -> {
                ServerLevel _level;
                if (world instanceof ServerLevel) {
                    _level = (ServerLevel)world;
                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.PURPLE_FLAME_PARTICLE.get()), x, y, z, 250, 0.2, 1.2, 0.2, 0.1);
                }
                if (world instanceof ServerLevel) {
                    _level = (ServerLevel)world;
                    Entity entityToSpawn = ((EntityType)MokelsBossfightSaphyraModEntities.LINARA.get()).spawn(_level, BlockPos.containing((double)x, (double)y, (double)z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                    }
                }
            });
        }
    }
}





















