/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.entity.LyiorStatueTileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LyiorStatueBlockModel
extends GeoModel<LyiorStatueTileEntity> {
    public ResourceLocation getAnimationResource(LyiorStatueTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "animations/lyiorstatue.animation.json");
    }

    public ResourceLocation getModelResource(LyiorStatueTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "geo/lyiorstatue.geo.json");
    }

    public ResourceLocation getTextureResource(LyiorStatueTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/block/lyiorstatuetexture.png");
    }
}















