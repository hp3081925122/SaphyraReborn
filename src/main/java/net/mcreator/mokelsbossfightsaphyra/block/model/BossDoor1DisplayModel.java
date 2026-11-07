/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.display.BossDoor1DisplayItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BossDoor1DisplayModel
extends GeoModel<BossDoor1DisplayItem> {
    public ResourceLocation getAnimationResource(BossDoor1DisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "animations/bossgate.animation.json");
    }

    public ResourceLocation getModelResource(BossDoor1DisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "geo/bossgate.geo.json");
    }

    public ResourceLocation getTextureResource(BossDoor1DisplayItem entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/block/bossgatetexture.png");
    }
}















