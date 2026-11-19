package com.hp.saphyrareborn.boss;

import java.util.UUID;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class BossCastLock {
    private static final UUID LOCK_UUID = UUID.fromString("8d6e1c4a-9f32-4b78-a51c-7e2049c8b101");
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

    public static void release(LivingEntity entity) {
        if (entity.level().isClientSide()) {
            return;
        }
        entity.getPersistentData().remove(LOCK_KEY);
        entity.getPersistentData().remove(SHIELD_KEY);
        apply(entity, false);
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
        if (speed == null || (speed.getModifier(LOCK_UUID) != null) == locked) {
            return;
        }
        if (locked) {
            speed.addTransientModifier(new AttributeModifier(LOCK_UUID, "saphyra_reborn:cast_lock", -1.0, AttributeModifier.Operation.MULTIPLY_TOTAL));
        } else {
            speed.removeModifier(LOCK_UUID);
        }
    }
}
