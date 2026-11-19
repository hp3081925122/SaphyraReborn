package com.hp.saphyrareborn.network;

import com.hp.saphyrareborn.client.SaphyraClientBridge;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public record PlayerAnimationPayload(int target, String animation, boolean override) {
    public static void encode(PlayerAnimationPayload msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.target);
        buf.writeUtf(msg.animation);
        buf.writeBoolean(msg.override);
    }

    public static PlayerAnimationPayload decode(FriendlyByteBuf buf) {
        return new PlayerAnimationPayload(buf.readVarInt(), buf.readUtf(), buf.readBoolean());
    }

    public static void send(Entity player, String animation) {
        if (player.level().isClientSide()) {
            return;
        }
        SaphyraNetwork.sendToTrackingAndSelf(player, new PlayerAnimationPayload(player.getId(), animation, true));
    }

    public static void handle(PlayerAnimationPayload msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SaphyraClientBridge.playPlayerAnimation(msg.target, msg.animation, msg.override)));
        ctx.setPacketHandled(true);
    }
}
