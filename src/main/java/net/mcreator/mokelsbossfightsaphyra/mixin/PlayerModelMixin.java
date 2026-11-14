package com.hp.saphyrareborn.mixin;

import com.hp.saphyrareborn.client.PlayerAnimations;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin {
    // Hat, jacket, sleeves and pants are child parts here, so they follow the base parts.
    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)V", at = @At("TAIL"))
    private void saphyra_reborn$playAnimation(AvatarRenderState state, CallbackInfo ci) {
        PlayerAnimations.applyModel(state.id, state.ageInTicks, (PlayerModel) (Object) this);
    }
}
