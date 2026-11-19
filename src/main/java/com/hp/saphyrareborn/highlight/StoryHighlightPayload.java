package com.hp.saphyrareborn.highlight;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public record StoryHighlightPayload(List<BlockPos> containers, List<BlockPos> targets) {
    public static void encode(StoryHighlightPayload msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.containers.size());
        for (BlockPos p : msg.containers) {
            buf.writeBlockPos(p);
        }
        buf.writeVarInt(msg.targets.size());
        for (BlockPos p : msg.targets) {
            buf.writeBlockPos(p);
        }
    }

    public static StoryHighlightPayload decode(FriendlyByteBuf buf) {
        int cSize = Math.min(4096, Math.max(0, buf.readVarInt()));
        List<BlockPos> c = new ArrayList<>(cSize);
        for (int i = 0; i < cSize; i++) {
            c.add(buf.readBlockPos());
        }
        int tSize = Math.min(4096, Math.max(0, buf.readVarInt()));
        List<BlockPos> t = new ArrayList<>(tSize);
        for (int i = 0; i < tSize; i++) {
            t.add(buf.readBlockPos());
        }
        return new StoryHighlightPayload(c, t);
    }

    public static void handle(StoryHighlightPayload msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> StoryHighlightState.set(msg.containers, msg.targets)));
        ctx.setPacketHandled(true);
    }
}
