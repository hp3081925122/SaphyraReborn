/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.entity.OddmentStage2TileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class OddmentStage2BlockModel
extends GeoModel<OddmentStage2TileEntity> {
    public ResourceLocation getAnimationResource(OddmentStage2TileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "animations/losthopestage2.animation.json");
    }

    public ResourceLocation getModelResource(OddmentStage2TileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "geo/losthopestage2.geo.json");
    }

    public ResourceLocation getTextureResource(OddmentStage2TileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/block/losthopetexture.png");
    }
}
















