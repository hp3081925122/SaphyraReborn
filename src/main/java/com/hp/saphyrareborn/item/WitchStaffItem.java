package com.hp.saphyrareborn.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class WitchStaffItem
extends SwordItem {
    public WitchStaffItem() {
        super(new Tier(){

            public int getUses() {
                return 0;
            }

            public float getSpeed() {
                return 2.0f;
            }

            public float getAttackDamageBonus() {
                return 2.0f;
            }

            public int getLevel() {
                return 0;
            }

            public int getEnchantmentValue() {
                return 15;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.EMPTY;
            }
        }, 3, -2.4f, new Item.Properties().fireResistant());
    }

    public void appendHoverText(ItemStack itemstack, @javax.annotation.Nullable net.minecraft.world.level.Level context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add((Component)Component.translatable("text.saphyra_reborn.hardcoded.efbaba6d2f01"));
        list.add((Component)Component.translatable("text.saphyra_reborn.hardcoded.2c1c80b68da4"));
    }
}
