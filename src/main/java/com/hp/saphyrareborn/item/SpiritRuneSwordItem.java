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

public class SpiritRuneSwordItem
extends SwordItem {
    public SpiritRuneSwordItem() {
        super(new Tier(){

            public int getUses() {
                return 0;
            }

            public float getSpeed() {
                return 2.0f;
            }

            public float getAttackDamageBonus() {
                return 5.0f;
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
        list.add(net.minecraft.network.chat.Component.translatable("text.saphyra_reborn.weapon.spirit_rune_sword").withStyle(net.minecraft.ChatFormatting.GOLD));
    }

    @Override
    public net.minecraft.world.InteractionResultHolder<net.minecraft.world.item.ItemStack> use(net.minecraft.world.level.Level world, net.minecraft.world.entity.player.Player player, net.minecraft.world.InteractionHand hand) {
        return com.hp.saphyrareborn.weapon.WeaponAbilities.spiritRune(world, player, hand);
    }
}
