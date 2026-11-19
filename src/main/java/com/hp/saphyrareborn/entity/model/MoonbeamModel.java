package com.hp.saphyrareborn.entity.model;

import com.hp.saphyrareborn.entity.MoonbeamEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MoonbeamModel
extends GeoModel<MoonbeamEntity> {
    public ResourceLocation getAnimationResource(MoonbeamEntity entity) {
        return new ResourceLocation("saphyra_reborn", "animations/moonbeam.animation.json");
    }

    public ResourceLocation getModelResource(MoonbeamEntity entity) {
        return new ResourceLocation("saphyra_reborn", "geo/moonbeam.geo.json");
    }

    public ResourceLocation getTextureResource(MoonbeamEntity entity) {
        return new ResourceLocation("saphyra_reborn", "textures/entities/" + entity.getTexture() + ".png");
    }
}
