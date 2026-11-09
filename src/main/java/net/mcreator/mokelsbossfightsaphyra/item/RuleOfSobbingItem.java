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
import com.hp.saphyrareborn.procedures.RuleCurseCastProcedure;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class RuleOfSobbingItem
extends Item {
    public RuleOfSobbingItem() {
        super(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).stacksTo(1).rarity(Rarity.COMMON));
    }

    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, display, list, flag);
        list.accept((Component)Component.translatable("text.saphyra_reborn.rule.sobbing.desc"));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
        if (level instanceof ServerLevel serverLevel) {
            RuleCurseCastProcedure.cast(serverLevel, player, stack, RuleCurseCastProcedure.Rule.SOBBING);
        }
        return (level.isClientSide() ? net.minecraft.world.InteractionResult.SUCCESS : net.minecraft.world.InteractionResult.SUCCESS_SERVER);
    }
}
