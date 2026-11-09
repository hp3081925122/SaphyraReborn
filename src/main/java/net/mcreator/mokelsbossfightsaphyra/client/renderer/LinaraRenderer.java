/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.rendertype.RenderType
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.Entity
 *  com.geckolib.cache.model.BakedGeoModel
 *  com.geckolib.model.GeoModel
 *  com.geckolib.renderer.GeoEntityRenderer
 */
package com.hp.saphyrareborn.client.renderer;

import com.hp.saphyrareborn.client.GeckoHead;

import com.geckolib.renderer.base.RenderPassInfo;

import com.geckolib.renderer.base.BoneSnapshots;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import com.geckolib.renderer.base.GeoRenderState;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.hp.saphyrareborn.entity.LinaraEntity;
import com.hp.saphyrareborn.entity.model.LinaraModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import com.geckolib.cache.model.BakedGeoModel;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoEntityRenderer;

public class LinaraRenderer
extends GeoEntityRenderer<LinaraEntity, LivingEntityRenderState> {
    public LinaraRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel)new LinaraModel());
        this.withScale(1.0f);
    }

    public RenderType getRenderType(LivingEntityRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }

    

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<LivingEntityRenderState> renderPassInfo, BoneSnapshots boneSnapshots) {
        GeckoHead.apply(renderPassInfo, boneSnapshots);
    }

}

















