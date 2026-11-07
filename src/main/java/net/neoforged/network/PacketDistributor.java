package net.neoforged.network;

import java.util.function.Supplier;

public final class PacketDistributor {
    public static final Target PLAYER = new Target();
    public static final Target ALL = new Target();
    public static final Target DIMENSION = new Target();
    private PacketDistributor() {
    }
    public static final class Target {
        public <T> Object with(Supplier<T> supplier) { return supplier.get(); }
        public Object noArg() { return this; }
    }
}













