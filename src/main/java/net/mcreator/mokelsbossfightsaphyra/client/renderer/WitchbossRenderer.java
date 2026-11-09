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
 *  com.geckolib.renderer.base.GeoRenderer
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
import com.hp.saphyrareborn.entity.WitchbossEntity;
import com.hp.saphyrareborn.entity.layer.WitchbossLayer;
import com.hp.saphyrareborn.entity.model.WitchbossModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import com.geckolib.cache.model.BakedGeoModel;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderer;

public class WitchbossRenderer
extends GeoEntityRenderer<WitchbossEntity, LivingEntityRenderState> {
    public WitchbossRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel)new WitchbossModel());
        this.withScale(1.1f);
        this.withRenderLayer(WitchbossLayer::new);
    }

    public RenderType getRenderType(LivingEntityRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }

    

    protected float getDeathMaxRotation(WitchbossEntity entityLivingBaseIn) {
        return 0.0f;
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<LivingEntityRenderState> renderPassInfo, BoneSnapshots boneSnapshots) {
        GeckoHead.apply(renderPassInfo, boneSnapshots);
    }

}

















