package com.hp.saphyrareborn.procedures;

import javax.annotation.Nullable;
import com.hp.saphyrareborn.entity.MothbossEntity;
import com.hp.saphyrareborn.entity.WitchbossEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class GriefBarPlayerTickProcedure {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        {
            GriefBarPlayerTickProcedure.execute((Event)event, (Entity)event.getEntity());
        }
    }

    public static void execute(Entity entity) {
        GriefBarPlayerTickProcedure.execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        double _setval;
        if (entity == null) {
            return;
        }
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Grief > 0.0 && !(MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Grief < 0.0)) {
            _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Grief - 0.05;
            {
                MokelsBossfightSaphyraModVariables.PlayerVariables capability = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity);
                capability.Grief = _setval;
                capability.syncPlayerVariables(entity);
            }
        }
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Grief == 0.0 && !(MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Grief < 0.0)) {
            _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Grief - 5.0;
            {
                MokelsBossfightSaphyraModVariables.PlayerVariables capability = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity);
                capability.Grief = _setval;
                capability.syncPlayerVariables(entity);
            }
        }
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Grief > 70.0) {
            LivingEntity _entity;
            _setval = 0.0;
            {
                MokelsBossfightSaphyraModVariables.PlayerVariables capability = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity);
                capability.Grief = _setval;
                capability.syncPlayerVariables(entity);
            }
            if (!(entity instanceof MothbossEntity) && !(entity instanceof WitchbossEntity) && entity instanceof LivingEntity && entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.GRIEF_EFFECT, 200, 0, false, false));
            }
        }
    }
}

