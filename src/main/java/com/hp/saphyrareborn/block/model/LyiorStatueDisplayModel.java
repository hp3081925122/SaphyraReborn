package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.display.LyiorStatueDisplayItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LyiorStatueDisplayModel
extends GeoModel<LyiorStatueDisplayItem> {
    public ResourceLocation getAnimationResource(LyiorStatueDisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "animations/lyiorstatue.animation.json");
    }

    public ResourceLocation getModelResource(LyiorStatueDisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "geo/lyiorstatue.geo.json");
    }

    public ResourceLocation getTextureResource(LyiorStatueDisplayItem entity) {
        return new ResourceLocation("saphyra_reborn", "textures/block/lyiorstatuetexture.png");
    }
}
