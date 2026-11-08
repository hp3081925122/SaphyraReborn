package com.hp.saphyrareborn.item;

import com.hp.saphyrareborn.client.model.Modelgrassvisage;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public abstract class GrassVisageItem extends ArmorItem {
    // 1.20.1 原版：头盔护甲 3，韧性 1.5，耐久 11 * 47
    private static final ArmorMaterial MATERIAL = new ArmorMaterial(Map.of(ArmorItem.Type.HELMET, 3), 25, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.EMPTY, List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "grass_visage"))), 1.5f, 0.0f);
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/entities/grassvisagetexture.png");

    protected GrassVisageItem(ArmorItem.Type type, Item.Properties properties) { super(Holder.direct(MATERIAL), type, properties); }

    public static class Helmet extends GrassVisageItem {
        public Helmet() { super(ArmorItem.Type.HELMET, new Item.Properties().durability(11 * 47)); }

        @Override
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, net.minecraft.world.item.TooltipFlag flag) {
            super.appendHoverText(stack, context, list, flag);
            list.add(Component.translatable("text.saphyra_reborn.hardcoded.2f239b5cf85b"));
        }

        @Override
        public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
            return TEXTURE;
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                private HumanoidModel<LivingEntity> model;

                @Override
                public HumanoidModel<?> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
                    if (this.model == null) {
                        ModelPart head = new Modelgrassvisage<>(Minecraft.getInstance().getEntityModels().bakeLayer(Modelgrassvisage.LAYER_LOCATION)).headarmor;
                        this.model = HeadOnlyArmorModel.create(head);
                    }
                    return this.model;
                }
            });
        }
    }
}
