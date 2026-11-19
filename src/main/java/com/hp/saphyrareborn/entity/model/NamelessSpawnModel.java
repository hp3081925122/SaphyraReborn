package com.hp.saphyrareborn.entity.model;

import com.hp.saphyrareborn.entity.NamelessSpawnEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NamelessSpawnModel
extends GeoModel<NamelessSpawnEntity> {
    public ResourceLocation getAnimationResource(NamelessSpawnEntity entity) {
        return new ResourceLocation("saphyra_reborn", "animations/knightboss.animation.json");
    }

    public ResourceLocation getModelResource(NamelessSpawnEntity entity) {
        return new ResourceLocation("saphyra_reborn", "geo/knightboss.geo.json");
    }

    public ResourceLocation getTextureResource(NamelessSpawnEntity entity) {
        return new ResourceLocation("saphyra_reborn", "textures/entities/" + entity.getTexture() + ".png");
    }
}
