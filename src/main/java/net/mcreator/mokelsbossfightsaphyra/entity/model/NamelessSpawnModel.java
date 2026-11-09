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

import com.hp.saphyrareborn.entity.NamelessSpawnEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class NamelessSpawnModel
extends GeoModel<NamelessSpawnEntity> {
    public Identifier getAnimationResource(NamelessSpawnEntity entity) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "animations/knightboss.animation.json");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "geo/knightboss.geo.json");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/entities/" + state.getOrDefaultGeckolibData(GeckoHead.TEXTURE, "missing") + ".png");
    }

    @Override
    public void addAdditionalStateData(NamelessSpawnEntity animatable, Object relatedObject, GeoRenderState state) {
        state.addGeckolibData(GeckoHead.TEXTURE, animatable.getTexture());
    }

}
















