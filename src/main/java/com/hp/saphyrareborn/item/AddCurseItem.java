package com.hp.saphyrareborn.item;

import com.hp.saphyrareborn.procedures.AdddCurseProcedure;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class AddCurseItem
extends Item {
    public AddCurseItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder ar = super.use(world, entity, hand);
        AdddCurseProcedure.execute((LevelAccessor)world, (Entity)entity, (ItemStack)ar.getObject());
        return ar;
    }
}
