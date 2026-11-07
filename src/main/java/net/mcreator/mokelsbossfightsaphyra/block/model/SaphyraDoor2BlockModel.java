/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.entity.SaphyraDoor2TileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SaphyraDoor2BlockModel
extends GeoModel<SaphyraDoor2TileEntity> {
    public ResourceLocation getAnimationResource(SaphyraDoor2TileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "animations/bossgate.animation.json");
    }

    public ResourceLocation getModelResource(SaphyraDoor2TileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "geo/bossgate.geo.json");
    }

    public ResourceLocation getTextureResource(SaphyraDoor2TileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/block/bossgatetexturesaphyra.png");
    }
}















