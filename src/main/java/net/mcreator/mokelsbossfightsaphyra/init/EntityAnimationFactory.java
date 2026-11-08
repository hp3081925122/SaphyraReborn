package com.hp.saphyrareborn.init;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;

/**
 * Copies the synced animation name into {@code animationprocedure} and clears it, like the original
 * 1.20.1 LivingTickEvent handler. It must run BEFORE the entity ticks: procedures set the animation during
 * the tick, the server then syncs it to clients, and only the next tick's pre-handler clears it.
 * (Running it after the tick cleared the value before it was ever sent, so no attack animation played.)
 */
@EventBusSubscriber
public class EntityAnimationFactory {
    private static final String PACKAGE = "com.hp.saphyrareborn.entity.";
    private static final Map<Class<?>, Optional<Accessors>> CACHE = new ConcurrentHashMap<>();

    private record Accessors(Method get, Method set, Field procedure) {
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof LivingEntity entity) || !entity.getClass().getName().startsWith(PACKAGE)) {
            return;
        }
        Optional<Accessors> acc = CACHE.computeIfAbsent(entity.getClass(), EntityAnimationFactory::lookup);
        if (acc.isEmpty()) {
            return;
        }
        try {
            Object animation = acc.get().get().invoke(entity);
            if (!(animation instanceof String value) || value.equals("undefined")) {
                return;
            }
            acc.get().set().invoke(entity, "undefined");
            acc.get().procedure().set(entity, value);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    private static Optional<Accessors> lookup(Class<?> cls) {
        try {
            return Optional.of(new Accessors(cls.getMethod("getSyncedAnimation"), cls.getMethod("setAnimation", String.class),
                    cls.getField("animationprocedure")));
        } catch (ReflectiveOperationException e) {
            return Optional.empty();
        }
    }
}
