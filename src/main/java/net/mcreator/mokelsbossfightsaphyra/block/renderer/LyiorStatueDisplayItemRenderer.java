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

import com.hp.saphyrareborn.block.display.LyiorStatueDisplayItem;
import com.hp.saphyrareborn.block.model.LyiorStatueDisplayModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class LyiorStatueDisplayItemRenderer
extends GeoItemRenderer<LyiorStatueDisplayItem> {
    public LyiorStatueDisplayItemRenderer() {
        super((GeoModel)new LyiorStatueDisplayModel());
    }

    public RenderType getRenderType(LyiorStatueDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}

















