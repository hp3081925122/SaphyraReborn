package com.hp.saphyrareborn.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.Holder;
import java.util.Map;
public abstract class GrassVisageItem extends ArmorItem {
    private static final ArmorMaterial MATERIAL = new ArmorMaterial(Map.of(ArmorItem.Type.HELMET, 13, ArmorItem.Type.CHESTPLATE, 15, ArmorItem.Type.LEGGINGS, 16, ArmorItem.Type.BOOTS, 11), 25, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.EMPTY, List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "grass_visage"))), 1.5f, 0.0f);
    protected GrassVisageItem(ArmorItem.Type type, Item.Properties properties) { super(Holder.direct(MATERIAL), type, properties); }
    public static class Helmet extends GrassVisageItem {
        public Helmet() { super(ArmorItem.Type.HELMET, new Item.Properties()); }
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, net.minecraft.world.item.TooltipFlag flag) { super.appendHoverText(stack, context, list, flag); list.add(Component.literal("Plagued with endless hunger he strove through the landscape, consuming everything in a madness drive attempt to satiate his appetite.")); }
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) { return "saphyra_reborn:textures/entities/grassvisagetexture.png"; }
    }
}















