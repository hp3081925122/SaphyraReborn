/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.world.entity.Entity
 *  net.neoforged.neoforge.event.tick.PlayerTickEvent$Phase
 *  net.neoforged.neoforge.event.tick.PlayerTickEvent$PlayerTickEvent
 *  net.neoforged.bus.api.Event
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 */
package com.hp.saphyrareborn.procedures;

import javax.annotation.Nullable;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class DashBarPlayerTickProcedure {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {

            DashBarPlayerTickProcedure.execute((Event)event, (Entity)event.player);
        }

    public static void execute(Entity entity) {
        DashBarPlayerTickProcedure.execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        double _setval;
        if (entity == null) {
            return;
        }
        MokelsBossfightSaphyraModVariables.PlayerVariables playerVariables = new MokelsBossfightSaphyraModVariables.PlayerVariables();
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Dash > 0.0 && !(MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Dash < 0.0)) {
            _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Dash + 0.2;
            MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                capability.Dash = _setval;
                capability.syncPlayerVariables(entity);
            });
        }
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Dash == 0.0 && !(MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Dash < 0.0)) {
            _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Dash + 5.0;
            MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                capability.Dash = _setval;
                capability.syncPlayerVariables(entity);
            });
        }
        MokelsBossfightSaphyraModVariables.PlayerVariables playerVariables2 = new MokelsBossfightSaphyraModVariables.PlayerVariables();
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Dash > 30.0) {
            _setval = 30.0;
            MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                capability.Dash = _setval;
                capability.syncPlayerVariables(entity);
            });
        }
    }
}




















