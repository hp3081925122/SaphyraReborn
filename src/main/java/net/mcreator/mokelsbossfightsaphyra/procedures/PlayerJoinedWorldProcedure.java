package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.items.ItemHandlerHelper;

@EventBusSubscriber
public class PlayerJoinedWorldProcedure {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof Player player) || player.level().isClientSide()) {
            return;
        }
        MokelsBossfightSaphyraModVariables.PlayerVariables vars = MokelsBossfightSaphyraModVariables.getPlayerVariables(player);
        if (vars.SigilGiven) {
            return;
        }
        vars.SigilGiven = true;
        vars.syncPlayerVariables(player);
        if (hasAnySigil(player)) {
            com.hp.saphyrareborn.MokelsBossfightSaphyraMod.LOGGER.info("[SigilDebug] {} already owns a sigil, marked as given", player.getName().getString());
            return;
        }
        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN.get()));
        player.sendSystemMessage(Component.translatable("text.saphyra_reborn.story.join_clocktower"));
        com.hp.saphyrareborn.MokelsBossfightSaphyraMod.LOGGER.info("[SigilDebug] gave starting sigil to {}", player.getName().getString());
    }

    private static boolean hasAnySigil(Player player) {
        Inventory inv = player.getInventory();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            Item item = inv.getItem(i).getItem();
            if (item == MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN.get()
                    || item == MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN_STAGE_2.get()
                    || item == MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN_STAGE_3.get()
                    || item == MokelsBossfightSaphyraModItems.BROKEN_SIGIL_OF_THE_RED_SUN.get()
                    || item == MokelsBossfightSaphyraModItems.BROKEN_SIGIL_OF_THE_RED_SUN_2.get()) {
                return true;
            }
        }
        return false;
    }
}
