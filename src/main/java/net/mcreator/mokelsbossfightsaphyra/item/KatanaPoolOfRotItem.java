/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.Level
 */
package com.hp.saphyrareborn.item;

import java.util.List;
import com.hp.saphyrareborn.procedures.PoolOfRotHoldingTickProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class KatanaPoolOfRotItem
extends Item {
    public KatanaPoolOfRotItem() {
        super(WeaponProperties.sword(5.0f, -1.6f));
    }

    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, display, list, flag);
        list.accept(net.minecraft.network.chat.Component.translatable("text.saphyra_reborn.weapon.katana_pool_of_rot").withStyle(net.minecraft.ChatFormatting.GOLD));
        list.accept((Component)Component.translatable("text.saphyra_reborn.hardcoded.d1e0320c1fe4"));
        list.accept((Component)Component.translatable("text.saphyra_reborn.hardcoded.82fcc3e1a7fe"));
    }

    @Override
    public void inventoryTick(ItemStack itemstack, net.minecraft.server.level.ServerLevel world, Entity entity, net.minecraft.world.entity.EquipmentSlot slot) {
        super.inventoryTick(itemstack, world, entity, slot);
        if (slot == net.minecraft.world.entity.EquipmentSlot.MAINHAND) {
            PoolOfRotHoldingTickProcedure.execute(entity);
        }
    }
}
