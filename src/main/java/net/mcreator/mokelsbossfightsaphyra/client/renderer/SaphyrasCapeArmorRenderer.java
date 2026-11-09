/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.rendertype.RenderType
 *  net.minecraft.resources.Identifier
 *  com.geckolib.cache.model.GeoBone
 *  com.geckolib.animatable.GeoAnimatable
 *  com.geckolib.model.GeoModel
 *  com.geckolib.renderer.GeoArmorRenderer
 */
package com.hp.saphyrareborn.client.renderer;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

import com.geckolib.renderer.base.GeoRenderState;

import net.minecraft.client.renderer.rendertype.RenderTypes;

import com.hp.saphyrareborn.item.SaphyrasCapeItem;
import com.hp.saphyrareborn.item.model.SaphyrasCapeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoArmorRenderer;

public class SaphyrasCapeArmorRenderer
extends GeoArmorRenderer<SaphyrasCapeItem, HumanoidRenderState> {
    public SaphyrasCapeArmorRenderer() {
        super(new SaphyrasCapeModel());
    }

    public RenderType getRenderType(HumanoidRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}

















