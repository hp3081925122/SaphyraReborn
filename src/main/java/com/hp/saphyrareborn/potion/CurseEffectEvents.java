package com.hp.saphyrareborn.potion;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID)
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
                && instance.getEffect() == MokelsBossfightSaphyraModMobEffects.GRIEF_EFFECT.get()) {
            GriefEffectMobEffect.onRemoved(entity);
        }
    }
}
