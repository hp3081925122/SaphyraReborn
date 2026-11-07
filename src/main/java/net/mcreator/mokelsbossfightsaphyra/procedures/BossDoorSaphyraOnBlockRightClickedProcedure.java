/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 */
package com.hp.saphyrareborn.procedures;

import java.util.Map;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class BossDoorSaphyraOnBlockRightClickedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        IntegerProperty _integerProp;
        int _value = 2;
        BlockPos _pos = BlockPos.containing((double)x, (double)y, (double)z);
        BlockState _bs = world.getBlockState(_pos);
        Property property = _bs.getBlock().getStateDefinition().getProperty("animation");
        if (property instanceof IntegerProperty && (_integerProp = (IntegerProperty)property).getPossibleValues().contains(_value)) {
            world.setBlock(_pos, (BlockState)_bs.setValue((Property)_integerProp, (Comparable)Integer.valueOf(_value)), 3);
        }
        MokelsBossfightSaphyraMod.queueServerWork(32, () -> {
            if (world instanceof Level) {
                Level _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)x, (double)y, (double)z), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getValue(new ResourceLocation("mokels_bossfight_saphyra:heavydooropening")), SoundSource.BLOCKS, 1.0f, 1.0f);
                } else {
                    _level.playLocalSound(x, y, z, (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getValue(new ResourceLocation("mokels_bossfight_saphyra:heavydooropening")), SoundSource.BLOCKS, 1.0f, 1.0f, false);
                }
            }
        });
        MokelsBossfightSaphyraMod.queueServerWork(260, () -> {
            BlockPos _bp = BlockPos.containing((double)x, (double)y, (double)z);
            BlockState _bs = ((Block)MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_3.get()).defaultBlockState();
            BlockState _bso = world.getBlockState(_bp);
            for (Map.Entry entry : _bso.getValues().entrySet()) {
                Property _property = _bs.getBlock().getStateDefinition().getProperty(((Property)entry.getKey()).getName());
                if (_property == null || _bs.getValue(_property) == null) continue;
                try {
                    _bs = (BlockState)_bs.setValue(_property, (Comparable)entry.getValue());
                }
                catch (Exception exception) {}
            }
            world.setBlock(_bp, _bs, 3);
        });
    }
}




