package com.hp.saphyrareborn.block.renderer;

import com.hp.saphyrareborn.block.entity.OddmentHallowedTowerTileEntity;
import com.hp.saphyrareborn.block.model.OddmentHallowedTowerBlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class OddmentHallowedTowerTileRenderer
extends GeoBlockRenderer<OddmentHallowedTowerTileEntity> {
    public OddmentHallowedTowerTileRenderer() {
        super((GeoModel)new OddmentHallowedTowerBlockModel());
    }

    public RenderType getRenderType(OddmentHallowedTowerTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}
