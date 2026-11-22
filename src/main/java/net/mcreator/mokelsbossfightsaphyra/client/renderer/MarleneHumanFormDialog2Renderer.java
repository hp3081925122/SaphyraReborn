package com.hp.saphyrareborn.client.renderer;

import com.hp.saphyrareborn.client.GeckoHead;

import com.geckolib.renderer.base.RenderPassInfo;

import com.geckolib.renderer.base.BoneSnapshots;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import com.geckolib.renderer.base.GeoRenderState;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.hp.saphyrareborn.entity.MarleneHumanFormDialog2Entity;
import com.hp.saphyrareborn.entity.layer.MarleneHumanFormDialog2Layer;
import com.hp.saphyrareborn.entity.model.MarleneHumanFormDialog2Model;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import com.geckolib.cache.model.BakedGeoModel;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderer;

public class MarleneHumanFormDialog2Renderer
extends GeoEntityRenderer<MarleneHumanFormDialog2Entity, LivingEntityRenderState> {
    public MarleneHumanFormDialog2Renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, (GeoModel)new MarleneHumanFormDialog2Model());
        this.withScale(1.0f);
        this.withRenderLayer(MarleneHumanFormDialog2Layer::new);
    }

    public RenderType getRenderType(LivingEntityRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }

    

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<LivingEntityRenderState> renderPassInfo, BoneSnapshots boneSnapshots) {
        GeckoHead.apply(renderPassInfo, boneSnapshots);
    }

}

















