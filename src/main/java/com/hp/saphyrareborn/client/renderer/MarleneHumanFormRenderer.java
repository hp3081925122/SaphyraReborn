package com.hp.saphyrareborn.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.hp.saphyrareborn.entity.MarleneHumanFormEntity;
import com.hp.saphyrareborn.entity.layer.MarleneHumanFormLayer;
import com.hp.saphyrareborn.entity.model.MarleneHumanFormModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class MarleneHumanFormRenderer
extends GeoEntityRenderer<MarleneHumanFormEntity> {
    public MarleneHumanFormRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel)new MarleneHumanFormModel());
        this.shadowRadius = 0.8f;
        this.addRenderLayer(new MarleneHumanFormLayer((GeoRenderer<MarleneHumanFormEntity>)this));
    }

    public RenderType getRenderType(MarleneHumanFormEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, MarleneHumanFormEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float scale;
        this.scaleHeight = scale = 1.0f;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
