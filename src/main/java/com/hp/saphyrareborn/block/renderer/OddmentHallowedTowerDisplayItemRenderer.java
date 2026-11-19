package com.hp.saphyrareborn.block.renderer;

import com.hp.saphyrareborn.block.display.OddmentHallowedTowerDisplayItem;
import com.hp.saphyrareborn.block.model.OddmentHallowedTowerDisplayModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class OddmentHallowedTowerDisplayItemRenderer
extends GeoItemRenderer<OddmentHallowedTowerDisplayItem> {
    public OddmentHallowedTowerDisplayItemRenderer() {
        super((GeoModel)new OddmentHallowedTowerDisplayModel());
    }

    public RenderType getRenderType(OddmentHallowedTowerDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)this.getTextureLocation(animatable));
    }
}
