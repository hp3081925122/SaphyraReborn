package com.hp.saphyrareborn.highlight;

import net.neoforged.api.distmarker.Dist;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Client only: while a story key is held in either hand, finds the lock it opens in nearby loaded chunks
 * (block states are already on the client, so no packet is needed). Also adds the "opens" line to key tooltips.
 */
@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class KeyLockHighlight {
    private static final int CHUNK_RADIUS = 3;
    private static final int INTERVAL = 10;
    private static volatile List<BlockPos> locks = List.of();
    private static Map<Item, Block> keyToLock;
    private static int timer;

    private KeyLockHighlight() {
    }

    public static List<BlockPos> locks() {
        return locks;
    }

    private static Map<Item, Block> map() {
        if (keyToLock == null) {
            Map<Item, Block> m = new IdentityHashMap<>();
            m.put(MokelsBossfightSaphyraModItems.KEY_1.get(), MokelsBossfightSaphyraModBlocks.LOCKED_FOG_STEPS.get());
            m.put(MokelsBossfightSaphyraModItems.KEY_2.get(), MokelsBossfightSaphyraModBlocks.LOCKED_FOG_KNIFE.get());
            m.put(MokelsBossfightSaphyraModItems.KEY_3.get(), MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get());
            m.put(MokelsBossfightSaphyraModItems.KEY_4.get(), MokelsBossfightSaphyraModBlocks.LOCKED_FOG_HATERED.get());
            m.put(MokelsBossfightSaphyraModItems.HALLOWED_KEY_1.get(), MokelsBossfightSaphyraModBlocks.LOCKED_FOG_WORSHIP.get());
            m.put(MokelsBossfightSaphyraModItems.HALLOWED_KEY_2.get(), MokelsBossfightSaphyraModBlocks.LOCKED_FOG_CHAIN.get());
            m.put(MokelsBossfightSaphyraModItems.HALLOWED_KEY_3.get(), MokelsBossfightSaphyraModBlocks.LOCKED_FOG_SHATTERED.get());
            m.put(MokelsBossfightSaphyraModItems.WARRIOR_KEY.get(), MokelsBossfightSaphyraModBlocks.BOSS_DOOR_1.get());
            m.put(MokelsBossfightSaphyraModItems.SAPHYRA_KEY.get(), MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_1.get());
            keyToLock = m;
        }
        return keyToLock;
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ClientLevel level = mc.level;
        if (player == null || level == null) {
            locks = List.of();
            return;
        }
        Block main = map().get(player.getMainHandItem().getItem());
        Block off = map().get(player.getOffhandItem().getItem());
        if (main == null && off == null) {
            locks = List.of();
            return;
        }
        if (++timer < INTERVAL && !locks.isEmpty()) {
            return;
        }
        timer = 0;
        List<BlockPos> found = new ArrayList<>();
        int pcx = player.chunkPosition().x(), pcz = player.chunkPosition().z();
        for (int dx = -CHUNK_RADIUS; dx <= CHUNK_RADIUS; dx++) {
            for (int dz = -CHUNK_RADIUS; dz <= CHUNK_RADIUS; dz++) {
                LevelChunk chunk = level.getChunkSource().getChunk(pcx + dx, pcz + dz, false);
                if (chunk == null) continue;
                LevelChunkSection[] sections = chunk.getSections();
                for (int si = 0; si < sections.length; si++) {
                    LevelChunkSection sec = sections[si];
                    if (sec == null || sec.hasOnlyAir()
                            || !sec.getStates().maybeHas(s -> s.is(main == null ? off : main) || (off != null && s.is(off)))) continue;
                    int baseY = level.getSectionYFromSectionIndex(si) << 4;
                    for (int i = 0; i < 4096; i++) {
                        int x = i & 15, z = (i >> 4) & 15, y = i >> 8;
                        Block b = sec.getBlockState(x, y, z).getBlock();
                        if (b == main || b == off) {
                            found.add(new BlockPos(chunk.getPos().getMinBlockX() + x, baseY + y, chunk.getPos().getMinBlockZ() + z));
                        }
                    }
                }
            }
        }
        locks = List.copyOf(found);
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        Block lock = map().get(event.getItemStack().getItem());
        if (lock == null) {
            return;
        }
        event.getToolTip().add(Component.translatable("tooltip.saphyra_reborn.key_opens",
                Component.translatable(lock.getDescriptionId()).withStyle(ChatFormatting.LIGHT_PURPLE)).withStyle(ChatFormatting.GRAY));
        event.getToolTip().add(Component.translatable("tooltip.saphyra_reborn.key_highlight").withStyle(ChatFormatting.DARK_GRAY));
    }
}
