package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.item.SaphyrasCapeItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class ArmorAnimationFactory {
    @SubscribeEvent
    public static void animatedArmors(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.isEmpty() || !(stack.getItem() instanceof SaphyrasCapeItem cape)) {
                continue;
            }
            CustomData data = stack.get(DataComponents.CUSTOM_DATA);
            if (data == null) {
                continue;
            }
            CompoundTag tag = data.copyTag();
            String animation = tag.getString("geckoAnim");
            if (animation.isEmpty()) {
                continue;
            }
            tag.putString("geckoAnim", "");
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            if (player.level().isClientSide()) {
                cape.animationprocedure = animation;
            }
        }
    }
}












