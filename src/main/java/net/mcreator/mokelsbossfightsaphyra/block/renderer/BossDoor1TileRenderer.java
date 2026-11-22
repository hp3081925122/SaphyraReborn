package com.hp.saphyrareborn.block.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.hp.saphyrareborn.block.entity.BossDoor1TileEntity;
import com.hp.saphyrareborn.block.model.BossDoor1BlockModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoBlockRenderer;

public class BossDoor1TileRenderer
extends GeoBlockRenderer<BossDoor1TileEntity, BlockEntityRenderState> {
    public BossDoor1TileRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new BossDoor1BlockModel());
    }

    public RenderType getRenderType(BlockEntityRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}

















