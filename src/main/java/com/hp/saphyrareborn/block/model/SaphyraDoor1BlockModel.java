package com.hp.saphyrareborn.block.model;

import com.hp.saphyrareborn.block.entity.SaphyraDoor1TileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SaphyraDoor1BlockModel
extends GeoModel<SaphyraDoor1TileEntity> {
    public ResourceLocation getAnimationResource(SaphyraDoor1TileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "animations/bossgate.animation.json");
    }

    public ResourceLocation getModelResource(SaphyraDoor1TileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "geo/bossgate.geo.json");
    }

    public ResourceLocation getTextureResource(SaphyraDoor1TileEntity animatable) {
        return new ResourceLocation("saphyra_reborn", "textures/block/bossgatetexturesaphyra.png");
    }
}
