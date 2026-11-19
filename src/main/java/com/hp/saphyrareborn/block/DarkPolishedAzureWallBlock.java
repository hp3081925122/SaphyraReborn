package com.hp.saphyrareborn.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public class DarkPolishedAzureWallBlock
extends WallBlock {
    public DarkPolishedAzureWallBlock() {
        super(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_BRICKS).strength(-1.0f, 3600000.0f).pushReaction(PushReaction.IGNORE).forceSolidOn());
    }
}
