package com.hp.saphyrareborn.entity.layer;

import com.hp.saphyrareborn.entity.GhostSpiritEntity;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class GhostSpiritLayer
extends TextureLayerGeoLayer<GhostSpiritEntity, Void, LivingEntityRenderState> {
    private static final Identifier LAYER = Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/entities/ghostspirittexture.png");

    public GhostSpiritLayer(GeoRenderer<GhostSpiritEntity, Void, LivingEntityRenderState> entityRenderer) {
        super(entityRenderer, LAYER, RenderTypes::eyes);
    }
}
