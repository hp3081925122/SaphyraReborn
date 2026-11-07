/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.entity.BossDoor1TileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BossDoor1BlockModel
extends GeoModel<BossDoor1TileEntity> {
    public ResourceLocation getAnimationResource(BossDoor1TileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "animations/bossgate.animation.json");
    }

    public ResourceLocation getModelResource(BossDoor1TileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "geo/bossgate.geo.json");
    }

    public ResourceLocation getTextureResource(BossDoor1TileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/block/bossgatetexture.png");
    }
}
















