package com.hp.saphyrareborn.item;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemHandlerHelper;

/** 奖励袋：右键打开，消耗袋子并给出固定奖励（原模组只有“右键打开”说明，没有实现）。 */
final class BagRewards {
    private BagRewards() {}

    static InteractionResultHolder<ItemStack> open(Level level, Player player, InteractionHand hand, List<Supplier<? extends Item>> rewards) {
        ItemStack bag = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            for (Supplier<? extends Item> item : rewards) {
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(item.get()));
            }
            level.playSound(null, player.blockPosition(), SoundEvents.BUNDLE_DROP_CONTENTS, SoundSource.PLAYERS, 1.0f, 0.8f);
            bag.consume(1, player);
        }
        return InteractionResultHolder.sidedSuccess(bag, level.isClientSide());
    }
}
