/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.entity.model;

import com.hp.saphyrareborn.client.GeckoHead;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.entity.InfestedWitchEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class InfestedWitchModel
extends GeoModel<InfestedWitchEntity> {
    public Identifier getAnimationResource(InfestedWitchEntity entity) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "animations/scream.animation.json");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "geo/scream.geo.json");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/entities/" + state.getOrDefaultGeckolibData(GeckoHead.TEXTURE, "missing") + ".png");
    }

    @Override
    public void addAdditionalStateData(InfestedWitchEntity animatable, Object relatedObject, GeoRenderState state) {
        state.addGeckolibData(GeckoHead.TEXTURE, animatable.getTexture());
    }

}
















