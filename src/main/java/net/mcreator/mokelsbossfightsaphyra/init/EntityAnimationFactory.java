package com.hp.saphyrareborn.init;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class EntityAnimationFactory {
    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }
        try {
            Method getAnimation = entity.getClass().getMethod("getSyncedAnimation");
            Object animation = getAnimation.invoke(entity);
            if (!(animation instanceof String value) || value.equals("undefined")) {
                return;
            }
            entity.getClass().getMethod("setAnimation", String.class).invoke(entity, "undefined");
            Field procedure = entity.getClass().getField("animationprocedure");
            procedure.set(entity, value);
        } catch (ReflectiveOperationException ignored) {
        }
    }
}












