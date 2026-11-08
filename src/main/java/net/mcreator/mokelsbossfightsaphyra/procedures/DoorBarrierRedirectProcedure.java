package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/**
 * The boss doors are 3x5 models, but only the centre bottom block is the door; the rest is barrier.
 * Right-clicking any of those barriers is forwarded to the door so the whole doorway accepts the key.
 */
@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID)
public final class DoorBarrierRedirectProcedure {
    private DoorBarrierRedirectProcedure() {
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos clicked = event.getPos();
        if (event.getHand() != InteractionHand.MAIN_HAND || !level.getBlockState(clicked).is(Blocks.BARRIER)) {
            return;
        }
        // door sits at most 1 block sideways and up to 4 blocks below the barrier (same pattern the door clears)
        for (int dy = 0; dy >= -4; dy--) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx != 0 && dz != 0) continue;
                    BlockPos door = clicked.offset(dx, dy, dz);
                    Block block = level.getBlockState(door).getBlock();
                    boolean boss = block == MokelsBossfightSaphyraModBlocks.BOSS_DOOR_1.get();
                    boolean saphyra = block == MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_1.get();
                    if (!boss && !saphyra) continue;
                    if (!level.isClientSide()) {
                        if (boss) {
                            RightClickBossDoorKeyProcedure.execute(level, door.getX(), door.getY(), door.getZ(), event.getEntity());
                        } else {
                            RightClickSaphyraDoorKeyProcedure.execute(level, door.getX(), door.getY(), door.getZ(), event.getEntity());
                        }
                    }
                    event.setCanceled(true);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    return;
                }
            }
        }
    }
}
