package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.display.SaphyraDoor2DisplayItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SaphyraDoor2DisplayModel
extends GeoModel<SaphyraDoor2DisplayItem> {
    public ResourceLocation getAnimationResource(SaphyraDoor2DisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "animations/bossgate.animation.json");
    }

    public ResourceLocation getModelResource(SaphyraDoor2DisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "geo/bossgate.geo.json");
    }

    public ResourceLocation getTextureResource(SaphyraDoor2DisplayItem entity) {
        return new ResourceLocation("saphyra_reborn", "textures/block/bossgatetexturesaphyra.png");
    }
}
