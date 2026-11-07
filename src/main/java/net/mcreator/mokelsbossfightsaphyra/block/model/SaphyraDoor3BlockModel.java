/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.entity.SaphyraDoor3TileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SaphyraDoor3BlockModel
extends GeoModel<SaphyraDoor3TileEntity> {
    public ResourceLocation getAnimationResource(SaphyraDoor3TileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "animations/bossgateopen.animation.json");
    }

    public ResourceLocation getModelResource(SaphyraDoor3TileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "geo/bossgateopen.geo.json");
    }

    public ResourceLocation getTextureResource(SaphyraDoor3TileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/block/bossgatetexturesaphyra.png");
    }
}
















