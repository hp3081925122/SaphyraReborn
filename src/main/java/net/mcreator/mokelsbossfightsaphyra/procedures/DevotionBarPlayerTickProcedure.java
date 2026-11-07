/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.neoforged.neoforge.event.tick.PlayerTickEvent$Phase
 *  net.neoforged.neoforge.event.tick.PlayerTickEvent$PlayerTickEvent
 *  net.neoforged.bus.api.Event
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 */
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
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class DevotionBarPlayerTickProcedure {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {

            DevotionBarPlayerTickProcedure.execute((Event)event, (Entity)event.player);
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
            _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion - 0.05;
            MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                capability.Devotion = _setval;
                capability.syncPlayerVariables(entity);
            });
        }
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion == 0.0 && !(MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion < 0.0)) {
            _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion - 5.0;
            MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                capability.Devotion = _setval;
                capability.syncPlayerVariables(entity);
            });
        }
        MokelsBossfightSaphyraModVariables.PlayerVariables playerVariables2 = new MokelsBossfightSaphyraModVariables.PlayerVariables();
        if (MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion > 70.0) {
            LivingEntity _entity;
            _setval = 0.0;
            MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                capability.Devotion = _setval;
                capability.syncPlayerVariables(entity);
            });
            if (!(entity instanceof MothbossEntity) && !(entity instanceof WitchbossEntity) && entity instanceof LivingEntity && entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.GRIEF_EFFECT, 200, 0, false, false));
            }
        }
    }
}




















