package com.hp.saphyrareborn.init;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/** Set by the registry supplier immediately before a block or item is constructed. */
public final class RegistryIds {
    public static ResourceKey<Block> block;
    public static ResourceKey<Item> item;

    private RegistryIds() {
    }
}
