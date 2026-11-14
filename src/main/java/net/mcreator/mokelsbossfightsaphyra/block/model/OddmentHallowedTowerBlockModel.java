/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.block.entity.OddmentHallowedTowerTileEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class OddmentHallowedTowerBlockModel
extends GeoModel<OddmentHallowedTowerTileEntity> {
    public Identifier getAnimationResource(OddmentHallowedTowerTileEntity animatable) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "losthopestage2");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "losthopestage2");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/block/losthopetexture.png");
    }
}

















