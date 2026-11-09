/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.crafting.Ingredient
 */
package com.hp.saphyrareborn.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class KatanaLightItem
extends Item {
    public KatanaLightItem() {
        super(WeaponProperties.sword(2.5f, -2.0f));
    }

    @Override
    public void appendHoverText(net.minecraft.world.item.ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<net.minecraft.network.chat.Component> list, net.minecraft.world.item.TooltipFlag flag) {
        super.appendHoverText(itemstack, context, display, list, flag);
        list.accept(net.minecraft.network.chat.Component.translatable("text.saphyra_reborn.weapon.katana_light").withStyle(net.minecraft.ChatFormatting.GOLD));
    }
}
