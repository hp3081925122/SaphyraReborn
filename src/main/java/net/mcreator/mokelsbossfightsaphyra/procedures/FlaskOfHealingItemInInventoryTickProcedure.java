package com.hp.saphyrareborn.procedures;

import javax.annotation.Nullable;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class FlaskOfHealingItemInInventoryTickProcedure {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        {
            FlaskOfHealingItemInInventoryTickProcedure.execute((Event)event, (Entity)event.getEntity());
        }
    }

    public static void execute(Entity entity) {
        FlaskOfHealingItemInInventoryTickProcedure.execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        Player _playerHasItem;
        if (entity == null) {
            return;
        }
        if (entity instanceof Player && (_playerHasItem = (Player)entity).getInventory().contains(new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.FLASK_OF_HEALING.get())) && entity instanceof ServerPlayer) {
            ServerPlayer _serverPlayer = (ServerPlayer)entity;
            _serverPlayer.awardRecipesByKey(java.util.List.of(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.RECIPE, Identifier.parse("saphyra_reborn:flaskofhealinglvl"))));
        }
    }
}

