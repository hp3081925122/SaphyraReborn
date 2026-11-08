/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.DoorBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.properties.BlockSetType
 *  net.minecraft.world.level.block.state.properties.NoteBlockInstrument
 *  net.minecraft.world.level.material.PushReaction
 */
package com.hp.saphyrareborn.block;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public class BloodDoor1Block
extends DoorBlock {
    public BloodDoor1Block() {
        super(BlockSetType.OAK, BlockBehaviour.Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.DEEPSLATE_BRICKS).strength(-1.0f, 3600000.0f).pushReaction(PushReaction.IGNORE));
    }
}
















