/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.block.entity.BossDoor3TileEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class BossDoor3BlockModel
extends GeoModel<BossDoor3TileEntity> {
    public Identifier getAnimationResource(BossDoor3TileEntity animatable) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "animations/bossgateopen.animation.json");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "geo/bossgateopen.geo.json");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/block/bossgatetexture.png");
    }
}

















