package com.hp.saphyrareborn.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class VocalCordsItem
extends Item {
    public VocalCordsItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }
}
