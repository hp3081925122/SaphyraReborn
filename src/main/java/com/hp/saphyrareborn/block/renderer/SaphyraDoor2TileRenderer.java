package com.hp.saphyrareborn.block.renderer;

import com.hp.saphyrareborn.block.entity.SaphyraDoor2TileEntity;
import com.hp.saphyrareborn.block.model.SaphyraDoor2BlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SaphyraDoor2TileRenderer
extends GeoBlockRenderer<SaphyraDoor2TileEntity> {
    public SaphyraDoor2TileRenderer() {
        super((GeoModel)new SaphyraDoor2BlockModel());
    }

    public RenderType getRenderType(SaphyraDoor2TileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}
