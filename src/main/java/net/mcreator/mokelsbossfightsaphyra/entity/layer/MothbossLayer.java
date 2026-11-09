package com.hp.saphyrareborn.entity.layer;

import com.hp.saphyrareborn.entity.MothbossEntity;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class MothbossLayer
extends TextureLayerGeoLayer<MothbossEntity, Void, LivingEntityRenderState> {
    private static final Identifier LAYER = Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/entities/saphyramothbosstextureglow.png");

    public MothbossLayer(GeoRenderer<MothbossEntity, Void, LivingEntityRenderState> entityRenderer) {
        super(entityRenderer, LAYER, RenderTypes::eyes);
    }
}
