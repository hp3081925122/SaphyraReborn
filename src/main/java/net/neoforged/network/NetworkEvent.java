package net.neoforged.network;

import net.minecraft.server.level.ServerPlayer;
import java.util.function.Supplier;

public final class NetworkEvent {
    private NetworkEvent() {
    }
    public static final class Context {
        private final ServerPlayer sender;
        public Context(ServerPlayer sender) { this.sender = sender; }
        public void enqueueWork(Runnable work) { work.run(); }
        public void setPacketHandled(boolean handled) { }
        public ServerPlayer getSender() { return sender; }
        public NetworkDirection getDirection() { return NetworkDirection.PLAY_TO_CLIENT; }
    }
}













