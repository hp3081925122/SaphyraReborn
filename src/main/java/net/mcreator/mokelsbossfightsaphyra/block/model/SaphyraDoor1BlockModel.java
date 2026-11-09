/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.block.entity.SaphyraDoor1TileEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class SaphyraDoor1BlockModel
extends GeoModel<SaphyraDoor1TileEntity> {
    public Identifier getAnimationResource(SaphyraDoor1TileEntity animatable) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "animations/bossgate.animation.json");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "geo/bossgate.geo.json");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/block/bossgatetexturesaphyra.png");
    }
}

















