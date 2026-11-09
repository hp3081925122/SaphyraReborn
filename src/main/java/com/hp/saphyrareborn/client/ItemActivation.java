package com.hp.saphyrareborn.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

/** Client-only item activation flash. Dedicated servers never call this. */
public final class ItemActivation {
    private ItemActivation() {
    }

    public static void show(ItemStack stack) {
        Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
    }
}
