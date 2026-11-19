package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.entity.BossDoorTileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BossDoorBlockModel
extends GeoModel<BossDoorTileEntity> {
    public ResourceLocation getAnimationResource(BossDoorTileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "animations/bossgate.animation.json");
    }

    public ResourceLocation getModelResource(BossDoorTileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "geo/bossgate.geo.json");
    }

    public ResourceLocation getTextureResource(BossDoorTileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "textures/block/bossgatetexture.png");
    }
}
