package com.hp.saphyrareborn.item;

import java.util.List;
import com.hp.saphyrareborn.procedures.PoolOfRotHoldingTickProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class KatanaPoolOfRotItem
extends SwordItem {
    public KatanaPoolOfRotItem() {
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
        }, 3, -1.6f, new Item.Properties().fireResistant());
    }

    public void appendHoverText(ItemStack itemstack, @javax.annotation.Nullable net.minecraft.world.level.Level context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(net.minecraft.network.chat.Component.translatable("text.saphyra_reborn.weapon.katana_pool_of_rot").withStyle(net.minecraft.ChatFormatting.GOLD));
        list.add((Component)Component.translatable("text.saphyra_reborn.hardcoded.d1e0320c1fe4"));
        list.add((Component)Component.translatable("text.saphyra_reborn.hardcoded.82fcc3e1a7fe"));
    }

    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected) {
            PoolOfRotHoldingTickProcedure.execute(entity);
        }
    }
}
