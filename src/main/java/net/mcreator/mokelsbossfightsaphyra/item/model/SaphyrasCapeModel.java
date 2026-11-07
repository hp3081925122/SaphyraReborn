/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.item.model;

import com.hp.saphyrareborn.item.SaphyrasCapeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SaphyrasCapeModel
extends GeoModel<SaphyrasCapeItem> {
    public ResourceLocation getAnimationResource(SaphyrasCapeItem object) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "animations/capesaphyraanimated.animation.json");
    }

    public ResourceLocation getModelResource(SaphyrasCapeItem object) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "geo/capesaphyraanimated.geo.json");
    }

    public ResourceLocation getTextureResource(SaphyrasCapeItem object) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/item/capesaphyratexture.png");
    }
}














