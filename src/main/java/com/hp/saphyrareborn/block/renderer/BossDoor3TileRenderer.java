package com.hp.saphyrareborn.block.renderer;

import com.hp.saphyrareborn.block.entity.BossDoor3TileEntity;
import com.hp.saphyrareborn.block.model.BossDoor3BlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BossDoor3TileRenderer
extends GeoBlockRenderer<BossDoor3TileEntity> {
    public BossDoor3TileRenderer() {
        super((GeoModel)new BossDoor3BlockModel());
    }

    public RenderType getRenderType(BossDoor3TileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}
