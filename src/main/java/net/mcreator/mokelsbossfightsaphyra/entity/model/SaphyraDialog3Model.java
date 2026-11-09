/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.constant.DataTickets
 *  com.geckolib.cache.model.GeoBone
 *  com.geckolib.animation.state.AnimationTest
 *  com.geckolib.model.GeoModel
 *  com.geckolib.animation.state.AnimationTest
 */
package com.hp.saphyrareborn.entity.model;

import net.minecraft.world.entity.LivingEntity;

import com.hp.saphyrareborn.client.GeckoHead;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.entity.SaphyraDialog3Entity;
import net.minecraft.resources.Identifier;
import com.geckolib.constant.DataTickets;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.model.GeoModel;

public class SaphyraDialog3Model
extends GeoModel<SaphyraDialog3Entity> {
    public Identifier getAnimationResource(SaphyraDialog3Entity entity) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "animations/saphyrasitting.animation.json");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "geo/saphyrasitting.geo.json");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/entities/" + state.getOrDefaultGeckolibData(GeckoHead.TEXTURE, "missing") + ".png");
    }

    

    @Override
    public void addAdditionalStateData(SaphyraDialog3Entity animatable, Object relatedObject, GeoRenderState state) {
        state.addGeckolibData(GeckoHead.TEXTURE, animatable.getTexture());
        if (animatable instanceof LivingEntity living) {
            GeckoHead.capture(living, state);
        }
    }

}
















