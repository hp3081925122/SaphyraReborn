/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.rendertype.RenderType
 *  net.minecraft.resources.Identifier
 *  com.geckolib.animatable.GeoAnimatable
 *  com.geckolib.model.GeoModel
 *  com.geckolib.renderer.GeoBlockRenderer
 */
package com.hp.saphyrareborn.block.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.hp.saphyrareborn.block.entity.OddmentStage2TileEntity;
import com.hp.saphyrareborn.block.model.OddmentStage2BlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoBlockRenderer;

public class OddmentStage2TileRenderer
extends GeoBlockRenderer<OddmentStage2TileEntity, BlockEntityRenderState> {
    public OddmentStage2TileRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new OddmentStage2BlockModel());
    }

    public RenderType getRenderType(BlockEntityRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}

















