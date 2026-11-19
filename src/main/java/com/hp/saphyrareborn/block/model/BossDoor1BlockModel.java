package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.entity.BossDoor1TileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BossDoor1BlockModel
extends GeoModel<BossDoor1TileEntity> {
    public ResourceLocation getAnimationResource(BossDoor1TileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "animations/bossgate.animation.json");
    }

    public ResourceLocation getModelResource(BossDoor1TileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "geo/bossgate.geo.json");
    }

    public ResourceLocation getTextureResource(BossDoor1TileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "textures/block/bossgatetexture.png");
    }
}
