package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.display.BossDoor3DisplayItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BossDoor3DisplayModel
extends GeoModel<BossDoor3DisplayItem> {
    public ResourceLocation getAnimationResource(BossDoor3DisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "animations/bossgateopen.animation.json");
    }

    public ResourceLocation getModelResource(BossDoor3DisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "geo/bossgateopen.geo.json");
    }

    public ResourceLocation getTextureResource(BossDoor3DisplayItem entity) {
        return new ResourceLocation("saphyra_reborn", "textures/block/bossgatetexture.png");
    }
}
