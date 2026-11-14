package com.hp.saphyrareborn.gecko;

import com.geckolib.animation.AnimationController;
import com.geckolib.animation.object.PlayState;

import com.geckolib.animation.state.AnimationTest;
import net.minecraft.world.entity.LivingEntity;

public final class GeckoAnim {
    private GeckoAnim() {
    }

    /**
     * Handler for the "procedure" controller that plays server-triggered animations.
     * GeckoLib 5 clears the animation point on trigger, so isPlayingTriggeredAnimation() is false
     * until the next frame; returning STOP then would cancel every trigger. Finished animations
     * are reset so the controller releases the bones back to the movement controller.
     */
    public static PlayState procedure(AnimationTest<?> event, boolean dead) {
        AnimationController<?> controller = event.controller();
        if (controller.getCurrentRawAnimation() == null) {
            return PlayState.STOP;
        }
        boolean pending = !controller.isAnimatingBones() && controller.getPlayState() != PlayState.STOP;
        if (!dead && (pending || !controller.hasAnimationFinished())) {
            return PlayState.CONTINUE;
        }
        controller.reset();
        return PlayState.STOP;
    }

    public static boolean procedureActive(AnimationController<?> controller) {
        return controller != null && controller.getCurrentRawAnimation() != null && controller.getPlayState() != PlayState.STOP;
    }

    public static float limb(AnimationTest<?> event) {
        if (event.animatable() instanceof LivingEntity living) {
            return living.walkAnimation.speed();
        }
        return 0.0F;
    }
}
