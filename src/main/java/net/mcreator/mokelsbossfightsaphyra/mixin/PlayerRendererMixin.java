package com.hp.saphyrareborn.mixin;

import com.hp.saphyrareborn.client.PlayerAnimations;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {
    @Inject(method = "setupRotations(Lnet/minecraft/client/player/AbstractClientPlayer;Lcom/mojang/blaze3d/vertex/PoseStack;FFFF)V", at = @At("TAIL"))
    private void saphyra_reborn$bodyTransform(AbstractClientPlayer player, PoseStack poseStack, float ageInTicks, float bodyYaw, float partialTick, float scale, CallbackInfo ci) {
        PlayerAnimations.applyBody(player.getId(), ageInTicks, poseStack);
    }
}
