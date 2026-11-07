/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoItemRenderer
 */
package com.hp.saphyrareborn.block.renderer;

import com.hp.saphyrareborn.block.display.SaphyraDoor2DisplayItem;
import com.hp.saphyrareborn.block.model.SaphyraDoor2DisplayModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SaphyraDoor2DisplayItemRenderer
extends GeoItemRenderer<SaphyraDoor2DisplayItem> {
    public SaphyraDoor2DisplayItemRenderer() {
        super((GeoModel)new SaphyraDoor2DisplayModel());
    }

    public RenderType getRenderType(SaphyraDoor2DisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucentCull((ResourceLocation)this.getTextureLocation(animatable));
    }
}
















