/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.decoration.ArmorStand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.ArmorMaterial
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
 *  software.bernie.geckolib.animatable.GeoItem
 *  software.bernie.geckolib.constant.DataTickets
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.AnimationController$State
 *  software.bernie.geckolib.animation.AnimationState
 *  software.bernie.geckolib.animation.RawAnimation
 *  software.bernie.geckolib.animation.PlayState
 *  software.bernie.geckolib.renderer.GeoArmorRenderer
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package com.hp.saphyrareborn.item;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import com.hp.saphyrareborn.client.renderer.SaphyrasCapeArmorRenderer;
import com.hp.saphyrareborn.procedures.SaphyrasCapeChestplateTickEventProcedure;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
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
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SaphyrasCapeItem
extends ArmorItem
implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    public String animationprocedure = "empty";
    String prevAnim = "empty";

    public SaphyrasCapeItem(ArmorItem.Type type, Item.Properties properties) {
        super(Holder.direct(new ArmorMaterial(
                // 1.20.1 原版护甲值（13/15/16/11 是耐久倍率，不是护甲值）
                Map.of(
                        ArmorItem.Type.HELMET, 2,
                        ArmorItem.Type.CHESTPLATE, 10,
                        ArmorItem.Type.LEGGINGS, 5,
                        ArmorItem.Type.BOOTS, 2
                ),
                15,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                () -> Ingredient.of(Items.LEATHER),
                List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "saphyras_cape"))),
                3.0f,
                0.1f
        )), type, properties);
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions(){
            private GeoArmorRenderer<?> renderer;

            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = new SaphyrasCapeArmorRenderer();
                }
                this.renderer.prepForRender((Entity)livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity holder, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, holder, slot, selected);
        if (holder instanceof Player player && player.getItemBySlot(EquipmentSlot.CHEST) == itemstack) {
            this.onArmorTick(itemstack, world, player);
        }
    }

    private void onArmorTick(ItemStack itemstack, Level world, Player entity) {
        SaphyrasCapeItem armor;
        Item item = itemstack.getItem();
        if (item instanceof SaphyrasCapeItem && (armor = (SaphyrasCapeItem)item).getType() == ArmorItem.Type.CHESTPLATE) {
            SaphyrasCapeChestplateTickEventProcedure.execute((LevelAccessor)world, entity.getX(), entity.getY(), entity.getZ(), (Entity)entity, itemstack);
        }
    }

    private PlayState predicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("idle2"));
            Entity entity = (Entity)event.getData(DataTickets.ENTITY);
            if (entity instanceof ArmorStand) {
                return PlayState.CONTINUE;
            }
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationState event) {
        if (!this.animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED || !this.animationprocedure.equals(this.prevAnim) && !this.animationprocedure.equals("empty")) {
            Entity entity;
            if (!this.animationprocedure.equals(this.prevAnim)) {
                event.getController().forceAnimationReset();
            }
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
            if ((entity = (Entity)event.getData(DataTickets.ENTITY)) instanceof ArmorStand) {
                return PlayState.CONTINUE;
            }
            return PlayState.CONTINUE;
        }
        if (this.animationprocedure.equals("empty")) {
            this.prevAnim = "empty";
            return PlayState.STOP;
        }
        this.prevAnim = this.animationprocedure;
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "controller", 5, this::predicate)});
        data.add(new AnimationController[]{new AnimationController((GeoAnimatable)this, "procedureController", 5, this::procedurePredicate)});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}

























