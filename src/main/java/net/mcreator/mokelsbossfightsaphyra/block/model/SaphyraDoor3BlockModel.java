/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.block.entity.SaphyraDoor3TileEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class SaphyraDoor3BlockModel
extends GeoModel<SaphyraDoor3TileEntity> {
    public Identifier getAnimationResource(SaphyraDoor3TileEntity animatable) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "bossgateopen");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "bossgateopen");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/block/bossgatetexturesaphyra.png");
    }
}

















