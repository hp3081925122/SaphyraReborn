package com.hp.saphyrareborn.item;

import com.hp.saphyrareborn.client.model.Modelgrassvisage;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public abstract class GrassVisageItem extends ArmorItem {
    private static final ArmorMaterial MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 47;
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return type == ArmorItem.Type.HELMET ? 3 : 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 25;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }

        @Override
        public String getName() {
            return "saphyra_reborn:grass_visage";
        }

        @Override
        public float getToughness() {
            return 1.5f;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0f;
        }
    };

    private static final String TEXTURE = "saphyra_reborn:textures/entities/grassvisagetexture.png";

    protected GrassVisageItem(ArmorItem.Type type, Item.Properties properties) {
        super(MATERIAL, type, properties);
    }

    public static class Helmet extends GrassVisageItem {
        public Helmet() {
            super(ArmorItem.Type.HELMET, new Item.Properties().durability(11 * 47));
        }

        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flag) {
            super.appendHoverText(stack, world, list, flag);
            list.add(Component.translatable("text.saphyra_reborn.hardcoded.2f239b5cf85b"));
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
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
