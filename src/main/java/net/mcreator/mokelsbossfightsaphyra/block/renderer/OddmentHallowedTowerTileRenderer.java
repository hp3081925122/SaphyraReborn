package com.hp.saphyrareborn.block.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.hp.saphyrareborn.block.entity.OddmentHallowedTowerTileEntity;
import com.hp.saphyrareborn.block.model.OddmentHallowedTowerBlockModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoBlockRenderer;

public class OddmentHallowedTowerTileRenderer
extends GeoBlockRenderer<OddmentHallowedTowerTileEntity, BlockEntityRenderState> {
    public OddmentHallowedTowerTileRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new OddmentHallowedTowerBlockModel());
    }

    public RenderType getRenderType(BlockEntityRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}

















