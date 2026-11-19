package com.hp.saphyrareborn.block.renderer;

import com.hp.saphyrareborn.block.entity.BossDoorTileEntity;
import com.hp.saphyrareborn.block.model.BossDoorBlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BossDoorTileRenderer
extends GeoBlockRenderer<BossDoorTileEntity> {
    public BossDoorTileRenderer() {
        super((GeoModel)new BossDoorBlockModel());
    }

    public RenderType getRenderType(BossDoorTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}
