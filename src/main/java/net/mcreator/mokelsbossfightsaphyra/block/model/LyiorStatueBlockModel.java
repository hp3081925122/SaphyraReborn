/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.block.entity.LyiorStatueTileEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class LyiorStatueBlockModel
extends GeoModel<LyiorStatueTileEntity> {
    public Identifier getAnimationResource(LyiorStatueTileEntity animatable) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "animations/lyiorstatue.animation.json");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "geo/lyiorstatue.geo.json");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/block/lyiorstatuetexture.png");
    }
}

















