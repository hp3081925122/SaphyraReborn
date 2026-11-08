package com.hp.saphyrareborn.highlight;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Every 2 seconds, looks at block entities within 3 chunks of each player and sends the positions of
 * containers that still hold story items, plus the story block the player can use next.
 */
@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID)
public final class StoryHighlightServer {
    private static final int INTERVAL = 40;
    private static final int CHUNK_RADIUS = 3;
    private static final Map<UUID, StoryHighlightPayload> LAST = new ConcurrentHashMap<>();
    private static Set<Item> storyItems;

    private StoryHighlightServer() {
    }

    private static Set<Item> storyItems() {
        if (storyItems == null) {
            storyItems = Set.of(
                    MokelsBossfightSaphyraModItems.KEY_1.get(), MokelsBossfightSaphyraModItems.KEY_2.get(),
                    MokelsBossfightSaphyraModItems.KEY_3.get(), MokelsBossfightSaphyraModItems.KEY_4.get(),
                    MokelsBossfightSaphyraModItems.HALLOWED_KEY_1.get(), MokelsBossfightSaphyraModItems.HALLOWED_KEY_2.get(),
                    MokelsBossfightSaphyraModItems.HALLOWED_KEY_3.get(), MokelsBossfightSaphyraModItems.WARRIOR_KEY.get(),
                    MokelsBossfightSaphyraModItems.SAPHYRA_KEY.get(), MokelsBossfightSaphyraModItems.MANIC_EYE.get(),
                    MokelsBossfightSaphyraModItems.THE_FIRST_GOD.get(),
                    MokelsBossfightSaphyraModItems.INVITATION_TO_THE_HALLOWED_TOWER.get());
        }
        return storyItems;
    }

    /** Story block -> item the player must carry for the block to be highlighted (null = always). */
    private static Supplier<? extends Item> requirement(Block block) {
        if (block == MokelsBossfightSaphyraModBlocks.ODDMENT_STAGE_2.get()) return MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN;
        if (block == MokelsBossfightSaphyraModBlocks.ODDMENT_HALLOWED_TOWER.get()) return MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN_STAGE_2;
        if (block == MokelsBossfightSaphyraModBlocks.LYIOR_STATUE_AKTIVATED.get()) return MokelsBossfightSaphyraModItems.BROKEN_SIGIL_OF_THE_RED_SUN;
        if (block == MokelsBossfightSaphyraModBlocks.BOSS_DOOR_1.get()) return MokelsBossfightSaphyraModItems.WARRIOR_KEY;
        if (block == MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_1.get()) return MokelsBossfightSaphyraModItems.SAPHYRA_KEY;
        return null;
    }

    private static boolean isStoryBlock(Block block) {
        return block == MokelsBossfightSaphyraModBlocks.LYIOR_STATUE.get() || requirement(block) != null;
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || player.tickCount % INTERVAL != 0) {
            return;
        }
        ServerLevel level = player.serverLevel();
        List<BlockPos> containers = new ArrayList<>();
        List<BlockPos> targets = new ArrayList<>();
        int pcx = player.chunkPosition().x, pcz = player.chunkPosition().z;
        for (int dx = -CHUNK_RADIUS; dx <= CHUNK_RADIUS; dx++) {
            for (int dz = -CHUNK_RADIUS; dz <= CHUNK_RADIUS; dz++) {
                LevelChunk chunk = level.getChunkSource().getChunkNow(pcx + dx, pcz + dz);
                if (chunk == null) continue;
                for (BlockEntity be : chunk.getBlockEntities().values()) {
                    Block block = be.getBlockState().getBlock();
                    if (isStoryBlock(block)) {
                        Supplier<? extends Item> need = requirement(block);
                        if (need == null || player.getInventory().hasAnyMatching(s -> s.is(need.get()))) {
                            targets.add(be.getBlockPos());
                        }
                    } else if (be instanceof Container c && holdsStoryItem(be, c)) {
                        containers.add(be.getBlockPos());
                    }
                }
            }
        }
        StoryHighlightPayload payload = new StoryHighlightPayload(containers, targets);
        if (!payload.equals(LAST.put(player.getUUID(), payload))) {
            PacketDistributor.sendToPlayer(player, payload);
        }
    }

    private static boolean holdsStoryItem(BlockEntity be, Container c) {
        // never force-generate loot just to look inside
        if (be instanceof RandomizableContainer r && r.getLootTable() != null) {
            return false;
        }
        Set<Item> items = storyItems();
        for (int i = 0; i < c.getContainerSize(); i++) {
            if (items.contains(c.getItem(i).getItem())) {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        LAST.remove(event.getEntity().getUUID());
    }

    @SubscribeEvent
    public static void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        LAST.remove(event.getEntity().getUUID());
    }
}
