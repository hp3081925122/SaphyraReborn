package net.neoforged.network;

public final class NetworkRegistry {
    private NetworkRegistry() {
    }
    public static net.neoforged.network.simple.SimpleChannel newSimpleChannel(Object id, java.util.function.Supplier<String> protocol, java.util.function.Predicate<String> clientAccepted, java.util.function.Predicate<String> serverAccepted) {
        return new net.neoforged.network.simple.SimpleChannel();
    }
}














