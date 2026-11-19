package com.hp.saphyrareborn.block.renderer;

import com.hp.saphyrareborn.block.display.BossDoor1DisplayItem;
import com.hp.saphyrareborn.block.model.BossDoor1DisplayModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BossDoor1DisplayItemRenderer
extends GeoItemRenderer<BossDoor1DisplayItem> {
    public BossDoor1DisplayItemRenderer() {
        super((GeoModel)new BossDoor1DisplayModel());
    }

    public RenderType getRenderType(BossDoor1DisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}
