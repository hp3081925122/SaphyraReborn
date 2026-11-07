/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$PlayerTickEvent
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
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
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DevotionBarPlayerTickProcedure {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
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
        if (((MokelsBossfightSaphyraModVariables.PlayerVariables)entity.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)playerVariables)).Devotion > 0.0 && !(((MokelsBossfightSaphyraModVariables.PlayerVariables)entity.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Devotion < 0.0)) {
            _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entity.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Devotion - 0.05;
            entity.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.Devotion = _setval;
                capability.syncPlayerVariables(entity);
            });
        }
        if (((MokelsBossfightSaphyraModVariables.PlayerVariables)entity.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Devotion == 0.0 && !(((MokelsBossfightSaphyraModVariables.PlayerVariables)entity.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Devotion < 0.0)) {
            _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entity.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Devotion - 5.0;
            entity.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.Devotion = _setval;
                capability.syncPlayerVariables(entity);
            });
        }
        MokelsBossfightSaphyraModVariables.PlayerVariables playerVariables2 = new MokelsBossfightSaphyraModVariables.PlayerVariables();
        if (((MokelsBossfightSaphyraModVariables.PlayerVariables)entity.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)playerVariables2)).Devotion > 70.0) {
            LivingEntity _entity;
            _setval = 0.0;
            entity.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.Devotion = _setval;
                capability.syncPlayerVariables(entity);
            });
            if (!(entity instanceof MothbossEntity) && !(entity instanceof WitchbossEntity) && entity instanceof LivingEntity && entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance((MobEffect)MokelsBossfightSaphyraModMobEffects.GRIEF_EFFECT.get(), 200, 0, false, false));
            }
        }
    }
}



