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

public class SwordOfTheNamelessGatekeeperItem
extends Item {
    public SwordOfTheNamelessGatekeeperItem() {
        super(WeaponProperties.sword(4.0f, -2.4f));
    }

    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, display, list, flag);
        list.accept(net.minecraft.network.chat.Component.translatable("text.saphyra_reborn.weapon.nameless").withStyle(net.minecraft.ChatFormatting.GOLD));
        list.accept((Component)Component.translatable("text.saphyra_reborn.hardcoded.2f73f6ca82ad"));
    }

    @Override
    public net.minecraft.world.InteractionResult use(net.minecraft.world.level.Level world, net.minecraft.world.entity.player.Player player, net.minecraft.world.InteractionHand hand) {
        return com.hp.saphyrareborn.weapon.WeaponAbilities.groundSlam(world, player, hand);
    }
}
