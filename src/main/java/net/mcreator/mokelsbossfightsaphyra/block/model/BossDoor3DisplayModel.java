/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  com.geckolib.model.GeoModel
 */
package com.hp.saphyrareborn.block.model;

import com.geckolib.renderer.base.GeoRenderState;

import com.hp.saphyrareborn.block.display.BossDoor3DisplayItem;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class BossDoor3DisplayModel
extends GeoModel<BossDoor3DisplayItem> {
    public Identifier getAnimationResource(BossDoor3DisplayItem animatable) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "bossgateopen");
    }

    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "bossgateopen");
    }

    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/block/bossgatetexture.png");
    }
}

















