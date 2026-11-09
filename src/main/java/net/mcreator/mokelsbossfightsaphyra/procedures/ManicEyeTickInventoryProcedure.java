package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class ManicEyeTickInventoryProcedure {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        execute(event.getEntity());
    }

    public static void execute(Entity entity) {
        if (!(entity instanceof Player player)) {
            return;
        }
        if (!player.getInventory().contains(new ItemStack((ItemLike) MokelsBossfightSaphyraModItems.MANIC_EYE.get()))) {
            return;
        }
        double next = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Manic + 0.1;
        MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
            capability.Manic = next;
            capability.syncPlayerVariables(entity);
        });
    }
}
