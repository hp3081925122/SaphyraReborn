package com.hp.saphyrareborn.gecko;

import com.geckolib.animation.state.AnimationTest;
import net.minecraft.world.entity.LivingEntity;

public final class GeckoAnim {
    private GeckoAnim() {
    }

    public static float limb(AnimationTest<?> event) {
        if (event.animatable() instanceof LivingEntity living) {
            return living.walkAnimation.speed();
        }
        return 0.0F;
    }
}
