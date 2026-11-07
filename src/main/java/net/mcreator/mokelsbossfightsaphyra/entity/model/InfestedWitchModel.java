/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.entity.model;

import com.hp.saphyrareborn.entity.InfestedWitchEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class InfestedWitchModel
extends GeoModel<InfestedWitchEntity> {
    public ResourceLocation getAnimationResource(InfestedWitchEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "animations/scream.animation.json");
    }

    public ResourceLocation getModelResource(InfestedWitchEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "geo/scream.geo.json");
    }

    public ResourceLocation getTextureResource(InfestedWitchEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/entities/" + entity.getTexture() + ".png");
    }
}
















