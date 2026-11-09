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

import com.hp.saphyrareborn.entity.MarleneHumanFormEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.constant.DataTickets;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.model.GeoModel;

public class MarleneHumanFormModel
extends GeoModel<MarleneHumanFormEntity> {
    public Identifier getAnimationResource(MarleneHumanFormEntity entity) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "animations/marlenehuman.animation.json");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "geo/marlenehuman.geo.json");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/entities/" + state.getOrDefaultGeckolibData(GeckoHead.TEXTURE, "missing") + ".png");
    }

    

    @Override
    public void addAdditionalStateData(MarleneHumanFormEntity animatable, Object relatedObject, GeoRenderState state) {
        state.addGeckolibData(GeckoHead.TEXTURE, animatable.getTexture());
        if (animatable instanceof LivingEntity living) {
            GeckoHead.capture(living, state);
        }
    }

}
















