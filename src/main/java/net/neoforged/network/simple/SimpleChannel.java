package net.neoforged.network.simple;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.network.NetworkEvent;

public class SimpleChannel {
    public <T> SimpleChannel registerMessage(int id, Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> handler) { return this; }
    public void sendToServer(Object message) { }
    public void send(Object target, Object message) { }
    public void sendTo(Object message, Object connection, Object direction) { }
}














