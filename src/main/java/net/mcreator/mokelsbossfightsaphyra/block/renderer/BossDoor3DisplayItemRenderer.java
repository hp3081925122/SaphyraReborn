/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.rendertype.RenderType
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.item.Item
 *  com.geckolib.model.GeoModel
 *  com.geckolib.renderer.GeoItemRenderer
 */
package com.hp.saphyrareborn.block.renderer;

import com.geckolib.renderer.base.GeoRenderState;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.hp.saphyrareborn.block.display.BossDoor3DisplayItem;
import com.hp.saphyrareborn.block.model.BossDoor3DisplayModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoItemRenderer;

public class BossDoor3DisplayItemRenderer
extends GeoItemRenderer<BossDoor3DisplayItem> {
    public BossDoor3DisplayItemRenderer() {
        super((GeoModel)new BossDoor3DisplayModel());
    }

    public RenderType getRenderType(GeoRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}

















