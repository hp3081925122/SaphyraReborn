package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.item.SaphyrasCapeItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ArmorAnimationFactory {
    @SubscribeEvent
    public static void animatedArmors(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Player player = event.player;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.isEmpty() || !(stack.getItem() instanceof SaphyrasCapeItem cape)) {
                continue;
            }
            CompoundTag tag = stack.getTag();
            if (tag == null) {
                continue;
            }
            String animation = tag.getString("geckoAnim");
            if (animation.isEmpty()) {
                continue;
            }
            tag.putString("geckoAnim", "");
            if (player.level().isClientSide()) {
                cape.animationprocedure = animation;
            }
        }
    }
}
