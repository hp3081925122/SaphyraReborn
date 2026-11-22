package com.hp.saphyrareborn.block.renderer;

import com.geckolib.renderer.base.GeoRenderState;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.hp.saphyrareborn.block.display.SaphyraDoor3DisplayItem;
import com.hp.saphyrareborn.block.model.SaphyraDoor3DisplayModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoItemRenderer;

public class SaphyraDoor3DisplayItemRenderer
extends GeoItemRenderer<SaphyraDoor3DisplayItem> {
    public SaphyraDoor3DisplayItemRenderer() {
        super((GeoModel)new SaphyraDoor3DisplayModel());
    }

    public RenderType getRenderType(GeoRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}

















