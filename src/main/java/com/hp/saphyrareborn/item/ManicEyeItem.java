package com.hp.saphyrareborn.item;

import java.util.List;
import com.hp.saphyrareborn.procedures.ManicEyeTickInventoryProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ManicEyeItem
extends Item {
    public ManicEyeItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    public void appendHoverText(ItemStack itemstack, @javax.annotation.Nullable net.minecraft.world.level.Level context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add((Component)Component.translatable("text.saphyra_reborn.hardcoded.d35fd39a42ca"));
        list.add((Component)Component.literal((String)""));
        list.add(Component.translatable("text.saphyra_reborn.hint.manic_eye").withStyle(net.minecraft.ChatFormatting.DARK_AQUA));
        list.add((Component)Component.translatable("text.saphyra_reborn.hardcoded.d38b85ee6dba"));
    }

    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        ManicEyeTickInventoryProcedure.execute(entity);
    }
}
