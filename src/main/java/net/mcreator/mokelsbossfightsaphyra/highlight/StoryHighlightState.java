package com.hp.saphyrareborn.highlight;

import net.minecraft.core.BlockPos;

import java.util.List;

/** Client-side copy of the last highlight packet. Plain data holder, safe to load on either side. */
public final class StoryHighlightState {
    private static volatile List<BlockPos> containers = List.of();
    private static volatile List<BlockPos> targets = List.of();

    private StoryHighlightState() {
    }

    static void set(List<BlockPos> c, List<BlockPos> t) {
        containers = List.copyOf(c);
        targets = List.copyOf(t);
    }

    public static List<BlockPos> containers() {
        return containers;
    }

    public static List<BlockPos> targets() {
        return targets;
    }

    public static void clear() {
        containers = List.of();
        targets = List.of();
    }
}
