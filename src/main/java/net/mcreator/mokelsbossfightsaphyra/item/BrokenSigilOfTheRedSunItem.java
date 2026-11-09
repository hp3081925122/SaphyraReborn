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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class BrokenSigilOfTheRedSunItem
extends Item {
    public BrokenSigilOfTheRedSunItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add((Component)Component.translatable("text.saphyra_reborn.hardcoded.8c7a1e042576"));
        list.add((Component)Component.translatable("text.saphyra_reborn.hardcoded.41163860252e"));
        SigilLocate.hint(list, SigilLocate.CHURCH);
        list.add((Component)Component.literal((String)""));
        list.add((Component)Component.translatable("text.saphyra_reborn.hardcoded.d38b85ee6dba"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return SigilLocate.use(level, player, hand, SigilLocate.CHURCH);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return SigilLocate.useOn(context, SigilLocate.CHURCH);
    }
}























