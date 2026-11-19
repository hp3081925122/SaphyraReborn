package com.hp.saphyrareborn.block.renderer;

import com.hp.saphyrareborn.block.entity.OddmentStage2TileEntity;
import com.hp.saphyrareborn.block.model.OddmentStage2BlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class OddmentStage2TileRenderer
extends GeoBlockRenderer<OddmentStage2TileEntity> {
    public OddmentStage2TileRenderer() {
        super((GeoModel)new OddmentStage2BlockModel());
    }

    public RenderType getRenderType(OddmentStage2TileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}
