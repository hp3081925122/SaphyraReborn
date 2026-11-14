package com.hp.saphyrareborn.mixin;

import com.hp.saphyrareborn.client.PlayerAnimations;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin {
    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void saphyra_reborn$playAnimation(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        PlayerModel<?> model = (PlayerModel<?>) (Object) this;
        if (PlayerAnimations.applyModel(entity.getId(), ageInTicks, model)) {
            // The overlay layers were copied from the base parts before we moved them.
            model.hat.copyFrom(model.head);
            model.jacket.copyFrom(model.body);
            model.leftSleeve.copyFrom(model.leftArm);
            model.rightSleeve.copyFrom(model.rightArm);
            model.leftPants.copyFrom(model.leftLeg);
            model.rightPants.copyFrom(model.rightLeg);
        }
    }
}
