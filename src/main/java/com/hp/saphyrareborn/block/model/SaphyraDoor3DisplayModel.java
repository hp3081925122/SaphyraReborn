package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.display.SaphyraDoor3DisplayItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SaphyraDoor3DisplayModel
extends GeoModel<SaphyraDoor3DisplayItem> {
    public ResourceLocation getAnimationResource(SaphyraDoor3DisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "animations/bossgateopen.animation.json");
    }

    public ResourceLocation getModelResource(SaphyraDoor3DisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "geo/bossgateopen.geo.json");
    }

    public ResourceLocation getTextureResource(SaphyraDoor3DisplayItem entity) {
        return new ResourceLocation("saphyra_reborn", "textures/block/bossgatetexturesaphyra.png");
    }
}
