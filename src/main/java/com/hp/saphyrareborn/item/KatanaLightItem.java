package com.hp.saphyrareborn.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class KatanaLightItem
extends SwordItem {
    public KatanaLightItem() {
        super(new Tier(){

            public int getUses() {
                return 0;
            }

            public float getSpeed() {
                return 2.0f;
            }

            public float getAttackDamageBonus() {
                return 2.5f;
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
        }, 3, -2.0f, new Item.Properties().fireResistant());
    }

    @Override
    public void appendHoverText(net.minecraft.world.item.ItemStack itemstack, @javax.annotation.Nullable net.minecraft.world.level.Level context, java.util.List<net.minecraft.network.chat.Component> list, net.minecraft.world.item.TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(net.minecraft.network.chat.Component.translatable("text.saphyra_reborn.weapon.katana_light").withStyle(net.minecraft.ChatFormatting.GOLD));
    }
}
