/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 */
package com.hp.saphyrareborn.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class BrokenSigilOfTheRedSun2Item
extends Item {
    public BrokenSigilOfTheRedSun2Item() {
        super(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).stacksTo(1).rarity(Rarity.COMMON));
    }

    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, display, list, flag);
        list.accept((Component)Component.translatable("text.saphyra_reborn.hardcoded.8c7a1e042576"));
        list.accept((Component)Component.translatable("text.saphyra_reborn.hardcoded.41163860252e"));
        list.accept((Component)Component.literal((String)""));
        list.accept((Component)Component.translatable("text.saphyra_reborn.hardcoded.d38b85ee6dba"));
    }
}























