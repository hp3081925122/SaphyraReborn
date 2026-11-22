package com.hp.saphyrareborn.block.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.hp.saphyrareborn.block.entity.LyiorStatueAktivatedTileEntity;
import com.hp.saphyrareborn.block.model.LyiorStatueAktivatedBlockModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoBlockRenderer;

public class LyiorStatueAktivatedTileRenderer
extends GeoBlockRenderer<LyiorStatueAktivatedTileEntity, BlockEntityRenderState> {
    public LyiorStatueAktivatedTileRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new LyiorStatueAktivatedBlockModel());
    }

    public RenderType getRenderType(BlockEntityRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}

















