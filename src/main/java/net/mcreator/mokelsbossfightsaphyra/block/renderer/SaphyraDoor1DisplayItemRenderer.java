package com.hp.saphyrareborn.block.renderer;

import com.geckolib.renderer.base.GeoRenderState;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.hp.saphyrareborn.block.display.SaphyraDoor1DisplayItem;
import com.hp.saphyrareborn.block.model.SaphyraDoor1DisplayModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoItemRenderer;

public class SaphyraDoor1DisplayItemRenderer
extends GeoItemRenderer<SaphyraDoor1DisplayItem> {
    public SaphyraDoor1DisplayItemRenderer() {
        super((GeoModel)new SaphyraDoor1DisplayModel());
    }

    public RenderType getRenderType(GeoRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}

















