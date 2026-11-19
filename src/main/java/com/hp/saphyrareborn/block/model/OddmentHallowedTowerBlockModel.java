package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.entity.OddmentHallowedTowerTileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class OddmentHallowedTowerBlockModel
extends GeoModel<OddmentHallowedTowerTileEntity> {
    public ResourceLocation getAnimationResource(OddmentHallowedTowerTileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "animations/losthopestage2.animation.json");
    }

    public ResourceLocation getModelResource(OddmentHallowedTowerTileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "geo/losthopestage2.geo.json");
    }

    public ResourceLocation getTextureResource(OddmentHallowedTowerTileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "textures/block/losthopetexture.png");
    }
}
