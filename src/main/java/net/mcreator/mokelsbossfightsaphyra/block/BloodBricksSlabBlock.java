/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.SlabBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.properties.NoteBlockInstrument
 *  net.minecraft.world.level.material.PushReaction
 */
package com.hp.saphyrareborn.block;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public class BloodBricksSlabBlock
extends SlabBlock {
    public BloodBricksSlabBlock() {
        super(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE_BRICKS).strength(-1.0f, 3600000.0f).pushReaction(PushReaction.IGNORE));
    }
}
















