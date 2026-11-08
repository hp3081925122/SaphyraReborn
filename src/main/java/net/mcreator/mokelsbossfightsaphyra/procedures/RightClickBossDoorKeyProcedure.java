package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.List;
import java.util.function.Supplier;

/**
 * Boss door (boss_door_1) in the Church of the Oath Pus. Ported from the original 1.20.1 procedure:
 * the Sinister key (warrior_key) turns it into boss_door2 (which opens on the next right-click) and
 * clears the barrier blocks around it; any other key only prints a hint.
 */
public final class RightClickBossDoorKeyProcedure {
    private static final List<Supplier<? extends Item>> OTHER_KEYS = List.of(
            MokelsBossfightSaphyraModItems.KEY_1, MokelsBossfightSaphyraModItems.KEY_2,
            MokelsBossfightSaphyraModItems.KEY_3, MokelsBossfightSaphyraModItems.KEY_4,
            MokelsBossfightSaphyraModItems.HALLOWED_KEY_1, MokelsBossfightSaphyraModItems.HALLOWED_KEY_2,
            MokelsBossfightSaphyraModItems.HALLOWED_KEY_3);

    // barrier offsets cleared by the original: 5-high column at the door and its four neighbours
    private static final int[][] BARRIER_OFFSETS = {
            {0, 0, 0}, {0, 1, 0}, {0, 2, 0}, {0, 3, 0}, {0, 4, 0},
            {0, 0, 1}, {0, 1, 1}, {0, 2, 1}, {0, 3, 1}, {0, 4, 1},
            {0, 0, -1}, {0, 1, -1}, {0, 2, -1}, {0, 3, -1}, {0, 4, -1},
            {1, 0, 0}, {1, 1, 0}, {1, 2, 0}, {1, 3, 0}, {1, 4, 0},
            {-1, 0, 0}, {-1, 1, 0}, {-1, 2, 0}, {-1, 3, 0}, {-1, 4, 0}};

    private RightClickBossDoorKeyProcedure() {
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (!(entity instanceof LivingEntity living) || world.isClientSide()) {
            return;
        }
        Item warrior = MokelsBossfightSaphyraModItems.WARRIOR_KEY.get();
        if (living.getMainHandItem().is(warrior) || living.getOffhandItem().is(warrior)) {
            BlockPos pos = BlockPos.containing(x, y, z);
            BlockState old = world.getBlockState(pos);
            BlockState next = MokelsBossfightSaphyraModBlocks.BOSS_DOOR2.get().defaultBlockState();
            for (Property<?> prop : old.getProperties()) {
                next = copy(next, old, prop);
            }
            world.setBlock(pos, next, 3);
            for (int[] o : BARRIER_OFFSETS) {
                BlockPos p = pos.offset(o[0], o[1], o[2]);
                if (world.getBlockState(p).is(Blocks.BARRIER)) {
                    world.destroyBlock(p, false);
                }
            }
            if (living instanceof Player player && !player.getAbilities().instabuild) {
                ItemStack held = player.getMainHandItem().is(warrior) ? player.getMainHandItem() : player.getOffhandItem();
                held.shrink(1);
            }
            message(world, "text.saphyra_reborn.door.opens");
            return;
        }
        boolean holdingKey = living.getMainHandItem().isEmpty() && living.getOffhandItem().isEmpty();
        for (Supplier<? extends Item> key : OTHER_KEYS) {
            holdingKey |= living.getMainHandItem().is(key.get()) || living.getOffhandItem().is(key.get());
        }
        if (holdingKey) {
            message(world, "text.saphyra_reborn.door.need_sinister_key");
        }
    }

    private static <T extends Comparable<T>> BlockState copy(BlockState target, BlockState source, Property<T> prop) {
        Property<?> match = target.getBlock().getStateDefinition().getProperty(prop.getName());
        if (match == null || !match.getValueClass().equals(prop.getValueClass())) {
            return target;
        }
        @SuppressWarnings("unchecked") Property<T> typed = (Property<T>) match;
        T value = source.getValue(prop);
        return typed.getPossibleValues().contains(value) ? target.setValue(typed, value) : target;
    }

    private static void message(LevelAccessor world, String key) {
        if (world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage(Component.translatable(key), false);
        }
    }
}
