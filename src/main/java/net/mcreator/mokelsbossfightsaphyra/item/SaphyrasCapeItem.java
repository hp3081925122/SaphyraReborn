/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.decoration.ArmorStand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.equipment.ArmorMaterial
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 *  com.geckolib.animatable.GeoItem
 *  com.geckolib.constant.DataTickets
 *  com.geckolib.animatable.GeoAnimatable
 *  com.geckolib.animatable.instance.AnimatableInstanceCache
 *  com.geckolib.animatable.manager.AnimatableManager$ControllerRegistrar
 *  com.geckolib.animation.AnimationController
 *  com.geckolib.animation.AnimationController$State
 *  com.geckolib.animation.state.AnimationTest
 *  com.geckolib.animation.RawAnimation
 *  com.geckolib.animation.object.PlayState
 *  com.geckolib.renderer.GeoArmorRenderer
 *  com.geckolib.util.GeckoLibUtil
 */
package com.hp.saphyrareborn.item;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import com.hp.saphyrareborn.client.renderer.SaphyrasCapeArmorRenderer;
import com.hp.saphyrareborn.procedures.SaphyrasCapeChestplateTickEventProcedure;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.geckolib.animatable.GeoItem;
import com.geckolib.constant.DataTickets;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.util.GeckoLibUtil;

public class SaphyrasCapeItem
extends Item
implements GeoItem {
    private static final ArmorMaterial MATERIAL = new ArmorMaterial(
            25,
            Map.of(ArmorType.HELMET, 2, ArmorType.CHESTPLATE, 10, ArmorType.LEGGINGS, 5, ArmorType.BOOTS, 2),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            3.0f,
            0.1f,
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("saphyra_reborn", "saphyras_cape_repair")),
            ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath("saphyra_reborn", "saphyras_cape")));
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    public String animationprocedure = "empty";
    String prevAnim = "empty";
    private final ArmorType armorType;

    public SaphyrasCapeItem(ArmorType type, Item.Properties properties) {
        super(properties.humanoidArmor(MATERIAL, type));
        this.armorType = type;
    }

    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, display, list, flag);
    }

    @Override
    public void inventoryTick(ItemStack itemstack, net.minecraft.server.level.ServerLevel world, Entity holder, EquipmentSlot slot) {
        super.inventoryTick(itemstack, world, holder, slot);
        if (holder instanceof Player player && slot == EquipmentSlot.CHEST && player.getItemBySlot(EquipmentSlot.CHEST) == itemstack) {
            this.onArmorTick(itemstack, world, player);
        }
    }

    private void onArmorTick(ItemStack itemstack, Level world, Player entity) {
        SaphyrasCapeItem armor;
        Item item = itemstack.getItem();
        if (item instanceof SaphyrasCapeItem && (armor = (SaphyrasCapeItem)item).armorType == ArmorType.CHESTPLATE) {
            SaphyrasCapeChestplateTickEventProcedure.execute((LevelAccessor)world, entity.getX(), entity.getY(), entity.getZ(), (Entity)entity, itemstack);
        }
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController[]{new AnimationController<>("controller", 5, this::predicate)});
        data.add(new AnimationController[]{new AnimationController<>("procedureController", 5, this::procedurePredicate)});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void createGeoRenderer(java.util.function.Consumer<com.geckolib.animatable.client.GeoRenderProvider> consumer) {
        consumer.accept(new com.geckolib.animatable.client.GeoRenderProvider() {
            private com.geckolib.renderer.GeoArmorRenderer<?, ?> renderer;

            @Override
            public com.geckolib.renderer.GeoArmorRenderer<?, ?> getGeoArmorRenderer(net.minecraft.world.item.ItemStack itemStack, net.minecraft.world.entity.EquipmentSlot slot) {
                if (this.renderer == null) {
                    this.renderer = new com.hp.saphyrareborn.client.renderer.SaphyrasCapeArmorRenderer();
                }
                return this.renderer;
            }
        });
    }


    private PlayState predicate(AnimationTest event) {
        event.controller().setAnimation(RawAnimation.begin().thenLoop("idle2"));
        return PlayState.CONTINUE;
    }

    private PlayState procedurePredicate(AnimationTest event) {
        if (this.animationprocedure.equals("empty")) {
            return PlayState.STOP;
        }
        if (!this.animationprocedure.equals(this.prevAnim)) {
            event.controller().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            this.prevAnim = this.animationprocedure;
        }
        return PlayState.CONTINUE;
    }

}

























