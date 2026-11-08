package com.hp.saphyrareborn.potion;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

/**
 * 1.20 MobEffect.removeAttributeModifiers fired on both expiry and removal (milk, /effect clear).
 * 1.21 has no such hook, so the Grief "expires" procedure is driven from NeoForge's effect events.
 */
@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID)
public final class CurseEffectEvents {
    private CurseEffectEvents() {
    }

    @SubscribeEvent
    public static void onExpired(MobEffectEvent.Expired event) {
        handle(event.getEntity(), event.getEffectInstance());
    }

    @SubscribeEvent
    public static void onRemoved(MobEffectEvent.Remove event) {
        handle(event.getEntity(), event.getEffectInstance());
    }

    private static void handle(LivingEntity entity, MobEffectInstance instance) {
        if (instance != null && !entity.level().isClientSide()
                && instance.getEffect().is(MokelsBossfightSaphyraModMobEffects.GRIEF_EFFECT.getKey())) {
            GriefEffectMobEffect.onRemoved(entity);
        }
    }
}
