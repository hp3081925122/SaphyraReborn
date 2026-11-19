package com.hp.saphyrareborn.block.renderer;

import com.hp.saphyrareborn.block.entity.SaphyraDoor1TileEntity;
import com.hp.saphyrareborn.block.model.SaphyraDoor1BlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SaphyraDoor1TileRenderer
extends GeoBlockRenderer<SaphyraDoor1TileEntity> {
    public SaphyraDoor1TileRenderer() {
        super((GeoModel)new SaphyraDoor1BlockModel());
    }

    public RenderType getRenderType(SaphyraDoor1TileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}
