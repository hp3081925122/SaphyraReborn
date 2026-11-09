/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.block.display.SaphyraDoor3DisplayItem;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class SaphyraDoor3DisplayModel
extends GeoModel<SaphyraDoor3DisplayItem> {
    public Identifier getAnimationResource(SaphyraDoor3DisplayItem animatable) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "animations/bossgateopen.animation.json");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "geo/bossgateopen.geo.json");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/block/bossgatetexturesaphyra.png");
    }
}

















