package com.hp.saphyrareborn.procedures;

import javax.annotation.Nullable;
import com.hp.saphyrareborn.entity.NamelessEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class RottingBarPlayerTickProcedure {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        {
            RottingBarPlayerTickProcedure.execute((Event)event, (Entity)event.getEntity());
        }
    }

    public static void execute(Entity entity) {
        RottingBarPlayerTickProcedure.execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        double _setval;
        if (entity == null) {
            return;
        }
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Rotting > 0.0 && !(MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Rotting < 0.0)) {
            _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Rotting - 0.05;
            {
                MokelsBossfightSaphyraModVariables.PlayerVariables capability = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity);
                capability.Rotting = _setval;
                capability.syncPlayerVariables(entity);
            }
        }
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Rotting == 0.0 && !(MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Rotting < 0.0)) {
            _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Rotting - 5.0;
            {
                MokelsBossfightSaphyraModVariables.PlayerVariables capability = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity);
                capability.Rotting = _setval;
                capability.syncPlayerVariables(entity);
            }
        }
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Rotting > 70.0) {
            LivingEntity _entity;
            _setval = 0.0;
            {
                MokelsBossfightSaphyraModVariables.PlayerVariables capability = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity);
                capability.Rotting = _setval;
                capability.syncPlayerVariables(entity);
            }
            if (!(entity instanceof NamelessEntity) && entity instanceof LivingEntity && entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.ROT_EFFECT, 400, 0, false, false));
            }
        }
    }
}

