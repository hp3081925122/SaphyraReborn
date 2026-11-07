/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.neoforged.neoforge.items.ItemHandlerHelper
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class LyiorStatueAktivatedOnBlockRightClickedProcedure {
    public static void execute(Entity entity) {
        ItemStack itemStack;
        if (entity == null) {
            return;
        }
        if (entity instanceof LivingEntity) {
            LivingEntity _livEnt = (LivingEntity)entity;
            itemStack = _livEnt.getMainHandItem();
        } else {
            itemStack = ItemStack.EMPTY;
        }
        if (itemStack.getItem() == MokelsBossfightSaphyraModItems.BROKEN_SIGIL_OF_THE_RED_SUN.get()) {
            Player _player;
            if (entity instanceof Player) {
                _player = (Player)entity;
                ItemStack _stktoremove = new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.BROKEN_SIGIL_OF_THE_RED_SUN.get());
                _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, (Container)_player.getInventory());
            }
            if (entity instanceof Player) {
                _player = (Player)entity;
                ItemStack _setstack = new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN_STAGE_2.get()).copy();
                _setstack.setCount(1);
                ItemHandlerHelper.giveItemToPlayer((Player)_player, (ItemStack)_setstack);
            }
        }
    }
}





















