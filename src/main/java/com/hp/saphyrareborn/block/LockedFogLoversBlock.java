package com.hp.saphyrareborn.block;

import com.hp.saphyrareborn.procedures.RightClickKeyLoversProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;

public class LockedFogLoversBlock
extends WallBlock {
    public LockedFogLoversBlock() {
        super(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).strength(-1.0f, 3600000.0f).lightLevel(s -> 5).pushReaction(PushReaction.IGNORE).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).forceSolidOn());
    }

    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, Mob entity) {
        return BlockPathTypes.OPEN;
    }

    @Override
    public net.minecraft.world.InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, net.minecraft.world.InteractionHand hand, BlockHitResult hit) {
        super.use(blockstate, world, pos, entity, hand, hit);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        double hitX = hit.getLocation().x;
        double hitY = hit.getLocation().y;
        double hitZ = hit.getLocation().z;
        Direction direction = hit.getDirection();
        RightClickKeyLoversProcedure.execute((LevelAccessor)world, x, y, z, (Entity)entity);
        return InteractionResult.SUCCESS;
    }
}
