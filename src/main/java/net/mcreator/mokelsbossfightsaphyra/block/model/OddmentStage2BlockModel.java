/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.block.entity.OddmentStage2TileEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class OddmentStage2BlockModel
extends GeoModel<OddmentStage2TileEntity> {
    public Identifier getAnimationResource(OddmentStage2TileEntity animatable) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "animations/losthopestage2.animation.json");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "geo/losthopestage2.geo.json");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/block/losthopetexture.png");
    }
}

















