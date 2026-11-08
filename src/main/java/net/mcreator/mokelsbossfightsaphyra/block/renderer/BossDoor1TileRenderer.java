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

import com.hp.saphyrareborn.block.entity.BossDoor1TileEntity;
import com.hp.saphyrareborn.block.model.BossDoor1BlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BossDoor1TileRenderer
extends GeoBlockRenderer<BossDoor1TileEntity> {
    public BossDoor1TileRenderer() {
        super((GeoModel)new BossDoor1BlockModel());
    }

    public RenderType getRenderType(BossDoor1TileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}

















