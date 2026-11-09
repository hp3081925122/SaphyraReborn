package com.hp.saphyrareborn.item;

import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class SaphyrasHatItem extends Item {
    // 1.20.1 原版：头盔护甲 3，韧性 2.0，耐久 11 * 47，贴图与萨菲拉 Boss 共用
    private static final ArmorMaterial MATERIAL = new ArmorMaterial(47, Map.of(ArmorType.HELMET, 3), 25, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0f, 0.0f, TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("saphyra_reborn", "empty_repair")), ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath("saphyra_reborn", "saphyras_hat")));

    protected SaphyrasHatItem(Item.Properties properties) { super(properties); }

    public static class Helmet extends SaphyrasHatItem {
        public Helmet() { super(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).humanoidArmor(MATERIAL, ArmorType.HELMET)); }

        @Override
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, net.minecraft.world.item.TooltipFlag flag) {
            super.appendHoverText(stack, context, display, list, flag);
        }
    }
}
