package com.hp.saphyrareborn.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class WarriorKeyItem
extends Item {
    public WarriorKeyItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }
}
