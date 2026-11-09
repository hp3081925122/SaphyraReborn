/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.item.model;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.item.SaphyrasCapeItem;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class SaphyrasCapeModel
extends GeoModel<SaphyrasCapeItem> {
    public Identifier getAnimationResource(SaphyrasCapeItem object) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "animations/capesaphyraanimated.animation.json");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "geo/capesaphyraanimated.geo.json");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/item/capesaphyratexture.png");
    }
}
















