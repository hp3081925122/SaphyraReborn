package com.hp.saphyrareborn.entity.model;

import com.hp.saphyrareborn.entity.SaphyraSpawnEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SaphyraSpawnModel
extends GeoModel<SaphyraSpawnEntity> {
    public ResourceLocation getAnimationResource(SaphyraSpawnEntity entity) {
        return new ResourceLocation("saphyra_reborn", "animations/moonbeam.animation.json");
    }

    public ResourceLocation getModelResource(SaphyraSpawnEntity entity) {
        return new ResourceLocation("saphyra_reborn", "geo/moonbeam.geo.json");
    }

    public ResourceLocation getTextureResource(SaphyraSpawnEntity entity) {
        return new ResourceLocation("saphyra_reborn", "textures/entities/" + entity.getTexture() + ".png");
    }
}
