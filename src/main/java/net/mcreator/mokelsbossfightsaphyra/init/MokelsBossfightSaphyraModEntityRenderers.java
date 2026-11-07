/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.ThrownItemRenderer
 *  net.minecraft.world.entity.EntityType
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.neoforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.fml.common.Mod$EventBusSubscriber$Bus
 */
package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.client.renderer.GhostSpiritRenderer;
import com.hp.saphyrareborn.client.renderer.GhostZombieRenderer;
import com.hp.saphyrareborn.client.renderer.InfestedWitchRenderer;
import com.hp.saphyrareborn.client.renderer.LinaraHallowedTower2Renderer;
import com.hp.saphyrareborn.client.renderer.LinaraHallowedTowerRenderer;
import com.hp.saphyrareborn.client.renderer.LinaraRenderer;
import com.hp.saphyrareborn.client.renderer.MarleneHumanFormDialog2Renderer;
import com.hp.saphyrareborn.client.renderer.MarleneHumanFormRenderer;
import com.hp.saphyrareborn.client.renderer.MoonbeamRenderer;
import com.hp.saphyrareborn.client.renderer.MothbossRenderer;
import com.hp.saphyrareborn.client.renderer.NamelessRenderer;
import com.hp.saphyrareborn.client.renderer.NamelessSpawnRenderer;
import com.hp.saphyrareborn.client.renderer.PuppetOfYivaNemorRenderer;
import com.hp.saphyrareborn.client.renderer.PutridRoseRenderer;
import com.hp.saphyrareborn.client.renderer.SaphyraDialog1Renderer;
import com.hp.saphyrareborn.client.renderer.SaphyraDialog2Renderer;
import com.hp.saphyrareborn.client.renderer.SaphyraDialog3Renderer;
import com.hp.saphyrareborn.client.renderer.SaphyraDialog4Renderer;
import com.hp.saphyrareborn.client.renderer.SaphyraSpawnRenderer;
import com.hp.saphyrareborn.client.renderer.ScorchedRenderer;
import com.hp.saphyrareborn.client.renderer.WitchbossRenderer;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class MokelsBossfightSaphyraModEntityRenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.WITCHBOSS.get(), WitchbossRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.MOONBEAM.get(), MoonbeamRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.WITCH_BOSS_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.WITCH_BOSS_PROJECTILEFALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.GHOST_SPIRIT.get(), GhostSpiritRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.MOTHBOSS.get(), MothbossRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.PUTRID_ROSE.get(), PutridRoseRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.GHOST_ZOMBIE.get(), GhostZombieRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.NAMELESS.get(), NamelessRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.NAMELESS_PROJECTILE_FALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.NAMELESS_INVISIBLE_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.DISGUST_PROJECTILE_FALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.LINARA.get(), LinaraRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.PUPPET_OF_YIVA_NEMOR.get(), PuppetOfYivaNemorRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.SAPHYRA_DIALOG_1.get(), SaphyraDialog1Renderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.SAPHYRA_DIALOG_2.get(), SaphyraDialog2Renderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.MIRROR_BLADE_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.ROT_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.NAMELESS_SPAWN.get(), NamelessSpawnRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.MARLENE_HUMAN_FORM.get(), MarleneHumanFormRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.SAPHYRA_DIALOG_3.get(), SaphyraDialog3Renderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.MARLENE_HUMAN_FORM_DIALOG_2.get(), MarleneHumanFormDialog2Renderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.LINARA_HALLOWED_TOWER.get(), LinaraHallowedTowerRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.LINARA_HALLOWED_TOWER_2.get(), LinaraHallowedTower2Renderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.SCORCHED.get(), ScorchedRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.INFESTED_WITCH.get(), InfestedWitchRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.SAPHYRA_SPAWN.get(), SaphyraSpawnRenderer::new);
        event.registerEntityRenderer((EntityType)MokelsBossfightSaphyraModEntities.SAPHYRA_DIALOG_4.get(), SaphyraDialog4Renderer::new);
    }
}

















