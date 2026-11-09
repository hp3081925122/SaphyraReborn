/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.item;

import com.hp.saphyrareborn.procedures.AdddCurseProcedure;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class AddCurseItem
extends Item {
    public AddCurseItem() {
        super(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).stacksTo(64).rarity(Rarity.COMMON));
    }

    public InteractionResult use(Level world, Player entity, InteractionHand hand) {
        AdddCurseProcedure.execute((LevelAccessor)world, (Entity)entity, entity.getItemInHand(hand));
        return super.use(world, entity, hand);
    }
}























