package com.hp.saphyrareborn.boss;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class BossCastLock {
    private static final ResourceLocation LOCK_ID = ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "cast_lock");
    private static final String LOCK_KEY = "SaphyraCastLockUntil";
    private static final String SHIELD_KEY = "SaphyraShieldUntil";

    private BossCastLock() {
    }

    public static void lock(LivingEntity entity, int ticks) {
        if (entity.level().isClientSide()) {
            return;
        }
        extend(entity, LOCK_KEY, ticks);
        apply(entity, true);
    }

    public static void shield(LivingEntity entity, int ticks) {
        if (entity.level().isClientSide()) {
            return;
        }
        extend(entity, SHIELD_KEY, ticks);
    }

    public static boolean blocksDamage(LivingEntity entity, DamageSource source) {
        return entity.level().getGameTime() < entity.getPersistentData().getLong(SHIELD_KEY)
                && !source.is(DamageTypeTags.BYPASSES_EFFECTS)
                && !source.is(DamageTypeTags.BYPASSES_RESISTANCE);
    }

    public static void tick(LivingEntity entity) {
        if (entity.level().isClientSide()) {
            return;
        }
        apply(entity, entity.level().getGameTime() < entity.getPersistentData().getLong(LOCK_KEY));
    }

    private static void extend(LivingEntity entity, String key, int ticks) {
        long until = entity.level().getGameTime() + ticks;
        if (entity.getPersistentData().getLong(key) < until) {
            entity.getPersistentData().putLong(key, until);
        }
    }

    private static void apply(LivingEntity entity, boolean locked) {
        AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null || speed.hasModifier(LOCK_ID) == locked) {
            return;
        }
        if (locked) {
            speed.addTransientModifier(new AttributeModifier(LOCK_ID, -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        } else {
            speed.removeModifier(LOCK_ID);
        }
    }
}
