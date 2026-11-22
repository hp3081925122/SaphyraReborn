package com.hp.saphyrareborn.block.renderer;

import com.geckolib.renderer.base.GeoRenderState;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.hp.saphyrareborn.block.display.BossDoorDisplayItem;
import com.hp.saphyrareborn.block.model.BossDoorDisplayModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoItemRenderer;

public class BossDoorDisplayItemRenderer
extends GeoItemRenderer<BossDoorDisplayItem> {
    public BossDoorDisplayItemRenderer() {
        super((GeoModel)new BossDoorDisplayModel());
    }

    public RenderType getRenderType(GeoRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}

















