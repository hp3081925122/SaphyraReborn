package com.hp.saphyrareborn.block.listener;

import com.hp.saphyrareborn.block.renderer.BossDoor1TileRenderer;
import com.hp.saphyrareborn.block.renderer.BossDoor3TileRenderer;
import com.hp.saphyrareborn.block.renderer.BossDoorTileRenderer;
import com.hp.saphyrareborn.block.renderer.LyiorStatueAktivatedTileRenderer;
import com.hp.saphyrareborn.block.renderer.LyiorStatueTileRenderer;
import com.hp.saphyrareborn.block.renderer.OddmentHallowedTowerTileRenderer;
import com.hp.saphyrareborn.block.renderer.OddmentStage2TileRenderer;
import com.hp.saphyrareborn.block.renderer.SaphyraDoor1TileRenderer;
import com.hp.saphyrareborn.block.renderer.SaphyraDoor2TileRenderer;
import com.hp.saphyrareborn.block.renderer.SaphyraDoor3TileRenderer;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="saphyra_reborn", bus=Mod.EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class ClientListener {
    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer((BlockEntityType)MokelsBossfightSaphyraModBlockEntities.BOSS_DOOR2.get(), context -> new BossDoorTileRenderer());
        event.registerBlockEntityRenderer((BlockEntityType)MokelsBossfightSaphyraModBlockEntities.LYIOR_STATUE.get(), context -> new LyiorStatueTileRenderer());
        event.registerBlockEntityRenderer((BlockEntityType)MokelsBossfightSaphyraModBlockEntities.LYIOR_STATUE_AKTIVATED.get(), context -> new LyiorStatueAktivatedTileRenderer());
        event.registerBlockEntityRenderer((BlockEntityType)MokelsBossfightSaphyraModBlockEntities.BOSS_DOOR_1.get(), context -> new BossDoor1TileRenderer());
        event.registerBlockEntityRenderer((BlockEntityType)MokelsBossfightSaphyraModBlockEntities.BOSS_DOOR_3.get(), context -> new BossDoor3TileRenderer());
        event.registerBlockEntityRenderer((BlockEntityType)MokelsBossfightSaphyraModBlockEntities.ODDMENT_STAGE_2.get(), context -> new OddmentStage2TileRenderer());
        event.registerBlockEntityRenderer((BlockEntityType)MokelsBossfightSaphyraModBlockEntities.ODDMENT_HALLOWED_TOWER.get(), context -> new OddmentHallowedTowerTileRenderer());
        event.registerBlockEntityRenderer((BlockEntityType)MokelsBossfightSaphyraModBlockEntities.SAPHYRA_DOOR_1.get(), context -> new SaphyraDoor1TileRenderer());
        event.registerBlockEntityRenderer((BlockEntityType)MokelsBossfightSaphyraModBlockEntities.SAPHYRA_DOOR_2.get(), context -> new SaphyraDoor2TileRenderer());
        event.registerBlockEntityRenderer((BlockEntityType)MokelsBossfightSaphyraModBlockEntities.SAPHYRA_DOOR_3.get(), context -> new SaphyraDoor3TileRenderer());
    }
}
