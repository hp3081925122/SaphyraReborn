package com.hp.saphyrareborn.block.renderer;

import com.geckolib.renderer.base.GeoRenderState;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.hp.saphyrareborn.block.display.OddmentStage2DisplayItem;
import com.hp.saphyrareborn.block.model.OddmentStage2DisplayModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoItemRenderer;

public class OddmentStage2DisplayItemRenderer
extends GeoItemRenderer<OddmentStage2DisplayItem> {
    public OddmentStage2DisplayItemRenderer() {
        super((GeoModel)new OddmentStage2DisplayModel());
    }

    public RenderType getRenderType(GeoRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}

















