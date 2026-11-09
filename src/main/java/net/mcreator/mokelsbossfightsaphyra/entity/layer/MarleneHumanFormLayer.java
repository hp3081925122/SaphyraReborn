package com.hp.saphyrareborn.entity.layer;

import com.hp.saphyrareborn.entity.MarleneHumanFormEntity;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class MarleneHumanFormLayer
extends TextureLayerGeoLayer<MarleneHumanFormEntity, Void, LivingEntityRenderState> {
    private static final Identifier LAYER = Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/entities/marnetextureglow.png");

    public MarleneHumanFormLayer(GeoRenderer<MarleneHumanFormEntity, Void, LivingEntityRenderState> entityRenderer) {
        super(entityRenderer, LAYER, RenderTypes::eyes);
    }
}
