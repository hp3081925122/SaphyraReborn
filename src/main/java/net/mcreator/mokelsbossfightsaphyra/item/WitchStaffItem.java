/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.Level
 */
package com.hp.saphyrareborn.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class WitchStaffItem
extends Item {
    public WitchStaffItem() {
        super(WeaponProperties.sword(2.0f, -2.4f));
    }

    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, display, list, flag);
        list.accept((Component)Component.translatable("text.saphyra_reborn.hardcoded.efbaba6d2f01"));
        list.accept((Component)Component.translatable("text.saphyra_reborn.hardcoded.2c1c80b68da4"));
    }
}























