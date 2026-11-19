package com.hp.saphyrareborn.block.renderer;

import com.hp.saphyrareborn.block.entity.LyiorStatueTileEntity;
import com.hp.saphyrareborn.block.model.LyiorStatueBlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class LyiorStatueTileRenderer
extends GeoBlockRenderer<LyiorStatueTileEntity> {
    public LyiorStatueTileRenderer() {
        super((GeoModel)new LyiorStatueBlockModel());
    }

    public RenderType getRenderType(LyiorStatueTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}
