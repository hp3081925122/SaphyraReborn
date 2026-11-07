/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.entity.model;

import com.hp.saphyrareborn.entity.NamelessSpawnEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NamelessSpawnModel
extends GeoModel<NamelessSpawnEntity> {
    public ResourceLocation getAnimationResource(NamelessSpawnEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "animations/knightboss.animation.json");
    }

    public ResourceLocation getModelResource(NamelessSpawnEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "geo/knightboss.geo.json");
    }

    public ResourceLocation getTextureResource(NamelessSpawnEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/entities/" + entity.getTexture() + ".png");
    }
}
















