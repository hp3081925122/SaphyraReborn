package com.hp.saphyrareborn.procedures;

import javax.annotation.Nullable;
import com.hp.saphyrareborn.entity.MothbossEntity;
import com.hp.saphyrareborn.entity.WitchbossEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DevotionBarPlayerTickProcedure {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        {
            DevotionBarPlayerTickProcedure.execute((Event)event, (Entity)event.player);
        }
    }

    public static void execute(Entity entity) {
        DevotionBarPlayerTickProcedure.execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        double _setval;
        if (entity == null) {
            return;
        }
        MokelsBossfightSaphyraModVariables.PlayerVariables playerVariables = new MokelsBossfightSaphyraModVariables.PlayerVariables();
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion > 0.0 && !(MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion < 0.0)) {
            final double devotionValue = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion - 0.05;
            MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                capability.Devotion = devotionValue;
                capability.syncPlayerVariables(entity);
            });
        }
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion == 0.0 && !(MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion < 0.0)) {
            final double devotionValue = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion - 5.0;
            MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                capability.Devotion = devotionValue;
                capability.syncPlayerVariables(entity);
            });
        }
        MokelsBossfightSaphyraModVariables.PlayerVariables playerVariables2 = new MokelsBossfightSaphyraModVariables.PlayerVariables();
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion > 70.0) {
            LivingEntity _entity1;
            final double devotionValue = 0.0;
            MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                capability.Devotion = devotionValue;
                capability.syncPlayerVariables(entity);
            });
            if (!(entity instanceof MothbossEntity) && !(entity instanceof WitchbossEntity) && entity instanceof LivingEntity && entity instanceof LivingEntity && !(_entity1 = (LivingEntity)entity).level().isClientSide()) {
                _entity1.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.GRIEF_EFFECT.get(), 200, 0, false, false));
            }
        }
    }
}
