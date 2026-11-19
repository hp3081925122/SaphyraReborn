package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.entity.LyiorStatueAktivatedTileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LyiorStatueAktivatedBlockModel
extends GeoModel<LyiorStatueAktivatedTileEntity> {
    public ResourceLocation getAnimationResource(LyiorStatueAktivatedTileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "animations/lyiorstatue.animation.json");
    }

    public ResourceLocation getModelResource(LyiorStatueAktivatedTileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "geo/lyiorstatue.geo.json");
    }

    public ResourceLocation getTextureResource(LyiorStatueAktivatedTileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "textures/block/lyiorstatueanimatedtexture.png");
    }
}
