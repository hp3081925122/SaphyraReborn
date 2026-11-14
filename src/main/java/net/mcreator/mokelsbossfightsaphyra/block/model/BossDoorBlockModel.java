/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.block.entity.BossDoorTileEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class BossDoorBlockModel
extends GeoModel<BossDoorTileEntity> {
    public Identifier getAnimationResource(BossDoorTileEntity animatable) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "bossgate");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "bossgate");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/block/bossgatetexture.png");
    }
}

















