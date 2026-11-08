package com.hp.saphyrareborn.procedures;

import javax.annotation.Nullable;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.items.ItemHandlerHelper;

@EventBusSubscriber
public class PlayerJoinedWorldProcedure {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerJoinedWorldProcedure.execute((Event)event, (LevelAccessor)event.getEntity().level(), (Entity)event.getEntity());
    }

    public static void execute(LevelAccessor world, Entity entity) {
        PlayerJoinedWorldProcedure.execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        MokelsBossfightSaphyraModVariables.WorldVariables.get((LevelAccessor)world).playerjoinedworld += 1.0;
        MokelsBossfightSaphyraModVariables.WorldVariables.get(world).syncData(world);
        if (MokelsBossfightSaphyraModVariables.WorldVariables.get((LevelAccessor)world).playerjoinedworld == 1.0) {
            if (entity instanceof Player) {
                Player _player = (Player)entity;
                ItemStack _setstack = new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN.get()).copy();
                _setstack.setCount(1);
                ItemHandlerHelper.giveItemToPlayer((Player)_player, (ItemStack)_setstack);
            }
            if (!world.isClientSide() && world.getServer() != null) {
                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("text.saphyra_reborn.story.join_clocktower"), false);
            }
        }
    }
}

