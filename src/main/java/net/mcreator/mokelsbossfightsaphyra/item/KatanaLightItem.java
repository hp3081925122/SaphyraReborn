/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.crafting.Ingredient
 */
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

            public net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> getIncorrectBlocksForDrops() { return net.minecraft.tags.BlockTags.INCORRECT_FOR_WOODEN_TOOL; }

            public int getEnchantmentValue() {
                return 15;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.EMPTY;
            }
        }, WeaponProperties.sword(2.5f, -2.0f));
    }

    @Override
    public void appendHoverText(net.minecraft.world.item.ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext context, java.util.List<net.minecraft.network.chat.Component> list, net.minecraft.world.item.TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(net.minecraft.network.chat.Component.translatable("text.saphyra_reborn.weapon.katana_light").withStyle(net.minecraft.ChatFormatting.GOLD));
    }
}
