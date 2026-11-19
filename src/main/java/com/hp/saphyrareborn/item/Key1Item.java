package com.hp.saphyrareborn.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Key1Item
extends Item {
    public Key1Item() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }
}
