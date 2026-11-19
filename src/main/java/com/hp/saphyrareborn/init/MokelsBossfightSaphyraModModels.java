package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.client.model.Modelgrassvisage;
import com.hp.saphyrareborn.client.model.Modelmirrorbladeprojectile;
import com.hp.saphyrareborn.client.model.Modelsaphyrashat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class MokelsBossfightSaphyraModModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(Modelsaphyrashat.LAYER_LOCATION, Modelsaphyrashat::createBodyLayer);
        event.registerLayerDefinition(Modelmirrorbladeprojectile.LAYER_LOCATION, Modelmirrorbladeprojectile::createBodyLayer);
        event.registerLayerDefinition(Modelgrassvisage.LAYER_LOCATION, Modelgrassvisage::createBodyLayer);
    }
}
