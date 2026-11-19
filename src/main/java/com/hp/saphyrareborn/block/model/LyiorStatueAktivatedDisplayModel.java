package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.display.LyiorStatueAktivatedDisplayItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LyiorStatueAktivatedDisplayModel
extends GeoModel<LyiorStatueAktivatedDisplayItem> {
    public ResourceLocation getAnimationResource(LyiorStatueAktivatedDisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "animations/lyiorstatue.animation.json");
    }

    public ResourceLocation getModelResource(LyiorStatueAktivatedDisplayItem animatable) {
        return new ResourceLocation("saphyra_reborn", "geo/lyiorstatue.geo.json");
    }

    public ResourceLocation getTextureResource(LyiorStatueAktivatedDisplayItem entity) {
        return new ResourceLocation("saphyra_reborn", "textures/block/lyiorstatueanimatedtexture.png");
    }
}
