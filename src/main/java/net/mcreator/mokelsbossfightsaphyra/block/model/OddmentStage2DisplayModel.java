/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.block.display.OddmentStage2DisplayItem;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class OddmentStage2DisplayModel
extends GeoModel<OddmentStage2DisplayItem> {
    public Identifier getAnimationResource(OddmentStage2DisplayItem animatable) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "losthopestage2");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "losthopestage2");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/block/losthopetexture.png");
    }
}

















