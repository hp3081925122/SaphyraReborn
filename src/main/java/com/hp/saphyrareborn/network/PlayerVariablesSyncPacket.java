package com.hp.saphyrareborn.network;

import com.hp.saphyrareborn.client.SaphyraClientBridge;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public record PlayerVariablesSyncPacket(CompoundTag data) {
    public static void encode(PlayerVariablesSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.data);
    }

    public static PlayerVariablesSyncPacket decode(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        return new PlayerVariablesSyncPacket(tag == null ? new CompoundTag() : tag);
    }

    public static void handle(PlayerVariablesSyncPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SaphyraClientBridge.applyPlayerVars(msg.data)));
        ctx.setPacketHandled(true);
    }
}
