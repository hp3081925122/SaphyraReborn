/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.WallBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.properties.NoteBlockInstrument
 *  net.minecraft.world.level.material.PushReaction
 */
package com.hp.saphyrareborn.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public class AzureWallBlock
extends WallBlock {
    public AzureWallBlock() {
        super(BlockBehaviour.Properties.of().setId(com.hp.saphyrareborn.init.RegistryIds.block).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_BRICKS).strength(-1.0f, 3600000.0f).pushReaction(PushReaction.IGNORE).forceSolidOn());
    }
}
















