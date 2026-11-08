package com.hp.saphyrareborn.item;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

/** 萨菲拉的奖励（玛琳转换萨菲拉的记忆后给出）。 */
public class BottomlessBagItem extends Item {
    public BottomlessBagItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(Component.translatable("text.saphyra_reborn.hardcoded.57111d062616"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return BagRewards.open(level, player, hand, List.of(
                MokelsBossfightSaphyraModItems.SAPHYRAS_CAPE_CHESTPLATE,
                MokelsBossfightSaphyraModItems.WITCH_STAFF,
                MokelsBossfightSaphyraModItems.RULE_OF_DESPAIR,
                MokelsBossfightSaphyraModItems.RULE_OF_SOBBING,
                MokelsBossfightSaphyraModItems.SAPHYRAS_HAT_HELMET,
                MokelsBossfightSaphyraModItems.MIRROR_BLADE));
    }
}
