/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.neoforge.client.event.EntityRenderersEvent$RegisterLayerDefinitions
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.fml.common.Mod$EventBusSubscriber$Bus
 */
package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.client.model.Modelgrassvisage;
import com.hp.saphyrareborn.client.model.Modelmirrorbladeprojectile;
import com.hp.saphyrareborn.client.model.Modelsaphyrashat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class MokelsBossfightSaphyraModModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(Modelsaphyrashat.LAYER_LOCATION, Modelsaphyrashat::createBodyLayer);
        event.registerLayerDefinition(Modelmirrorbladeprojectile.LAYER_LOCATION, Modelmirrorbladeprojectile::createBodyLayer);
        event.registerLayerDefinition(Modelgrassvisage.LAYER_LOCATION, Modelgrassvisage::createBodyLayer);
    }
}
















