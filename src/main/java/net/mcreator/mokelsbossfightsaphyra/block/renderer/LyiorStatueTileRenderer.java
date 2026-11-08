/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoBlockRenderer
 */
package com.hp.saphyrareborn.block.renderer;

import com.hp.saphyrareborn.block.entity.LyiorStatueTileEntity;
import com.hp.saphyrareborn.block.model.LyiorStatueBlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class LyiorStatueTileRenderer
extends GeoBlockRenderer<LyiorStatueTileEntity> {
    public LyiorStatueTileRenderer() {
        super((GeoModel)new LyiorStatueBlockModel());
    }

    public RenderType getRenderType(LyiorStatueTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}

















