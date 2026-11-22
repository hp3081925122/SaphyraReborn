package com.hp.saphyrareborn.block.renderer;

import com.geckolib.renderer.base.GeoRenderState;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.hp.saphyrareborn.block.display.OddmentHallowedTowerDisplayItem;
import com.hp.saphyrareborn.block.model.OddmentHallowedTowerDisplayModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoItemRenderer;

public class OddmentHallowedTowerDisplayItemRenderer
extends GeoItemRenderer<OddmentHallowedTowerDisplayItem> {
    public OddmentHallowedTowerDisplayItemRenderer() {
        super((GeoModel)new OddmentHallowedTowerDisplayModel());
    }

    public RenderType getRenderType(GeoRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}

















