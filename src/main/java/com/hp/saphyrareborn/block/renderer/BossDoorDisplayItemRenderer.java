package com.hp.saphyrareborn.block.renderer;

import com.hp.saphyrareborn.block.display.BossDoorDisplayItem;
import com.hp.saphyrareborn.block.model.BossDoorDisplayModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BossDoorDisplayItemRenderer
extends GeoItemRenderer<BossDoorDisplayItem> {
    public BossDoorDisplayItemRenderer() {
        super((GeoModel)new BossDoorDisplayModel());
    }

    public RenderType getRenderType(BossDoorDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}
