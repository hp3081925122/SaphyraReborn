/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.entity.BossDoorTileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BossDoorBlockModel
extends GeoModel<BossDoorTileEntity> {
    public ResourceLocation getAnimationResource(BossDoorTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "animations/bossgate.animation.json");
    }

    public ResourceLocation getModelResource(BossDoorTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "geo/bossgate.geo.json");
    }

    public ResourceLocation getTextureResource(BossDoorTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/block/bossgatetexture.png");
    }
}
















