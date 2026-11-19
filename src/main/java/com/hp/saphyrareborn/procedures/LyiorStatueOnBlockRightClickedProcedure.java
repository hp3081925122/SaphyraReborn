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
import net.minecraftforge.registries.ForgeRegistries;

public class LyiorStatueOnBlockRightClickedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        ServerLevel _level1;
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
            _level1 = (ServerLevel)world;
            _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/title @a times 20 40 20");
        }
        if (world instanceof ServerLevel) {
            _level1 = (ServerLevel)world;
            _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/title @a title {\"translate\":\"text.saphyra_reborn.title.lost_spirit_marked\",\"fallback\":\"lost spirit marked\",\"color\":\"aqua\"}");
        }
        if (world instanceof Level _soundLevel) {
            if (!_soundLevel.isClientSide()) {
                _soundLevel.playSound(null, BlockPos.containing((double)x, (double)y, (double)z), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("saphyra_reborn:lyiorsoundstatue")), SoundSource.BLOCKS, 1.0f, 1.0f);
            } else {
                _soundLevel.playLocalSound(x, y, z, (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("saphyra_reborn:lyiorsoundstatue")), SoundSource.BLOCKS, 1.0f, 1.0f, false);
            }
        }
    }
}
