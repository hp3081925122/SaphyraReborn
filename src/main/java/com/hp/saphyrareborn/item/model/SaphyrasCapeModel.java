package com.hp.saphyrareborn.item.model;

import com.hp.saphyrareborn.item.SaphyrasCapeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SaphyrasCapeModel
extends GeoModel<SaphyrasCapeItem> {
    public ResourceLocation getAnimationResource(SaphyrasCapeItem object) {
        return new ResourceLocation("saphyra_reborn", "animations/capesaphyraanimated.animation.json");
    }

    public ResourceLocation getModelResource(SaphyrasCapeItem object) {
        return new ResourceLocation("saphyra_reborn", "geo/capesaphyraanimated.geo.json");
    }

    public ResourceLocation getTextureResource(SaphyrasCapeItem object) {
        return new ResourceLocation("saphyra_reborn", "textures/item/capesaphyratexture.png");
    }
}
