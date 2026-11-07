/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.commands.CommandSource
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.Vec2
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 */
package com.hp.saphyrareborn.procedures;

import java.util.Map;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LyiorStatueOnBlockRightClickedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        ServerLevel _level;
        BlockPos _bp = BlockPos.containing((double)x, (double)y, (double)z);
        BlockState _bs = ((Block)MokelsBossfightSaphyraModBlocks.LYIOR_STATUE_AKTIVATED.get()).defaultBlockState();
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
        if (world instanceof ServerLevel) {
            _level = (ServerLevel)world;
            _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/title @a times 20 40 20");
        }
        if (world instanceof ServerLevel) {
            _level = (ServerLevel)world;
            _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/title @a title {\"text\":\"lost spirit marked\",\"color\":\"aqua\"}");
        }
        if (world instanceof Level) {
            _level = (Level)world;
            if (!_level.isClientSide()) {
                _level.playSound(null, BlockPos.containing((double)x, (double)y, (double)z), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:lyiorsoundstatue")), SoundSource.BLOCKS, 1.0f, 1.0f);
            } else {
                _level.playLocalSound(x, y, z, (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("mokels_bossfight_saphyra:lyiorsoundstatue")), SoundSource.BLOCKS, 1.0f, 1.0f, false);
            }
        }
    }
}



















