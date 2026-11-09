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

public class KatanaHeavyItem
extends Item {
    public KatanaHeavyItem() {
        super(WeaponProperties.sword(4.0f, -3.0f));
    }

    @Override
    public void appendHoverText(net.minecraft.world.item.ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<net.minecraft.network.chat.Component> list, net.minecraft.world.item.TooltipFlag flag) {
        super.appendHoverText(itemstack, context, display, list, flag);
        list.accept(net.minecraft.network.chat.Component.translatable("text.saphyra_reborn.weapon.katana_heavy").withStyle(net.minecraft.ChatFormatting.GOLD));
    }

    @Override
    public net.minecraft.world.InteractionResult use(net.minecraft.world.level.Level world, net.minecraft.world.entity.player.Player player, net.minecraft.world.InteractionHand hand) {
        return com.hp.saphyrareborn.weapon.WeaponAbilities.iaiSlash(world, player, hand);
    }
}
