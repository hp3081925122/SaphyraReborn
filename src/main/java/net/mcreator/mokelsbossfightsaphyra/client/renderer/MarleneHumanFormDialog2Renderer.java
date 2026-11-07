/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  software.bernie.geckolib.cache.object.BakedGeoModel
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 *  software.bernie.geckolib.renderer.GeoRenderer
 */
package com.hp.saphyrareborn.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.hp.saphyrareborn.entity.MarleneHumanFormDialog2Entity;
import com.hp.saphyrareborn.entity.layer.MarleneHumanFormDialog2Layer;
import com.hp.saphyrareborn.entity.model.MarleneHumanFormDialog2Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class MarleneHumanFormDialog2Renderer
extends GeoEntityRenderer<MarleneHumanFormDialog2Entity> {
    public MarleneHumanFormDialog2Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel)new MarleneHumanFormDialog2Model());
        this.shadowRadius = 0.8f;
        this.addRenderLayer(new MarleneHumanFormDialog2Layer((GeoRenderer<MarleneHumanFormDialog2Entity>)this));
    }

    public RenderType getRenderType(MarleneHumanFormDialog2Entity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucentCull((ResourceLocation)this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, MarleneHumanFormDialog2Entity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
        float scale;
        this.scaleHeight = scale = 1.0f;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
    }
}

















