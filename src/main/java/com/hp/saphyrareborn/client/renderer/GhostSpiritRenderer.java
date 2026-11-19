package com.hp.saphyrareborn.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.hp.saphyrareborn.entity.GhostSpiritEntity;
import com.hp.saphyrareborn.entity.layer.GhostSpiritLayer;
import com.hp.saphyrareborn.entity.model.GhostSpiritModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class GhostSpiritRenderer
extends GeoEntityRenderer<GhostSpiritEntity> {
    public GhostSpiritRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel)new GhostSpiritModel());
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new GhostSpiritLayer((GeoRenderer<GhostSpiritEntity>)this));
    }

    public RenderType getRenderType(GhostSpiritEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, GhostSpiritEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float scale;
        this.scaleHeight = scale = 1.0f;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
