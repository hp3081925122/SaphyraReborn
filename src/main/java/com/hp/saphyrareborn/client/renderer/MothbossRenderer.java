package com.hp.saphyrareborn.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.hp.saphyrareborn.entity.MothbossEntity;
import com.hp.saphyrareborn.entity.layer.MothbossLayer;
import com.hp.saphyrareborn.entity.model.MothbossModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class MothbossRenderer
extends GeoEntityRenderer<MothbossEntity> {
    public MothbossRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel)new MothbossModel());
        this.shadowRadius = 0.9f;
        this.addRenderLayer(new MothbossLayer((GeoRenderer<MothbossEntity>)this));
    }

    public RenderType getRenderType(MothbossEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, MothbossEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float scale;
        this.scaleHeight = scale = 1.0f;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    protected float getDeathMaxRotation(MothbossEntity entityLivingBaseIn) {
        return 0.0f;
    }
}
