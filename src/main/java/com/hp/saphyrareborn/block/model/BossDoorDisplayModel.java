package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.display.BossDoorDisplayItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BossDoorDisplayModel
extends GeoModel<BossDoorDisplayItem> {
    public ResourceLocation getAnimationResource(BossDoorDisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "animations/bossgate.animation.json");
    }

    public ResourceLocation getModelResource(BossDoorDisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "geo/bossgate.geo.json");
    }

    public ResourceLocation getTextureResource(BossDoorDisplayItem entity) {
        return new ResourceLocation("saphyra_reborn", "textures/block/bossgatetexture.png");
    }
}
