package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.display.SaphyraDoor1DisplayItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SaphyraDoor1DisplayModel
extends GeoModel<SaphyraDoor1DisplayItem> {
    public ResourceLocation getAnimationResource(SaphyraDoor1DisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "animations/bossgate.animation.json");
    }

    public ResourceLocation getModelResource(SaphyraDoor1DisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "geo/bossgate.geo.json");
    }

    public ResourceLocation getTextureResource(SaphyraDoor1DisplayItem entity) {
        return new ResourceLocation("saphyra_reborn", "textures/block/bossgatetexturesaphyra.png");
    }
}
