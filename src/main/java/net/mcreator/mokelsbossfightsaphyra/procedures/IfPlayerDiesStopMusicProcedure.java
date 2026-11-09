package com.hp.saphyrareborn.procedures;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class IfPlayerDiesStopMusicProcedure {
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            IfPlayerDiesStopMusicProcedure.execute((Event)event, (LevelAccessor)event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), (Entity)event.getEntity());
        }
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        IfPlayerDiesStopMusicProcedure.execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) {
            return;
        }
        if ((entity instanceof Player || entity instanceof ServerPlayer) && world instanceof ServerLevel) {
            ServerLevel _level = (ServerLevel)world;
            com.hp.saphyrareborn.boss.BossMusic.stop(_level, x, y, z, "saphyra_reborn:witchbossfighttheme");
        }
    }
}

