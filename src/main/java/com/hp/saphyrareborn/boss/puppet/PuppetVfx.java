package com.hp.saphyrareborn.boss.puppet;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.network.SaphyraNetwork;
import java.util.ArrayDeque;
import java.util.function.Supplier;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public record PuppetVfx(byte kind, double x, double y, double z, float yaw, float size, int extra) {
    public static final byte SLASH = 0;
    public static final byte FLAME_PILLAR = 1;
    public static final byte SHOCKWAVE = 2;
    public static final byte TELEGRAPH_RING = 3;
    public static final byte TELEGRAPH_LINE = 4;
    public static final byte CHARGE = 5;
    public static final byte PHASE_SHIFT = 6;
    public static final byte DASH_TRAIL = 7;
    public static final byte EMBER_BURST = 8;
    public static final byte SCORCH = 9;
    public static final byte STRINGS = 10;
    public static final byte INFERNO_WAVE = 11;

    public static final int SLASH_LEFT = 0;
    public static final int SLASH_RIGHT = 1;
    public static final int SLASH_OVERHEAD = 2;
    public static final int SLASH_RISING = 3;
    public static final int SLASH_DOUBLE = 4;

    public static int line(int ticks, double halfWidth) {
        return Math.max(0, Math.min(255, ticks)) | (Math.max(0, (int) Math.round(halfWidth * 10.0)) << 8);
    }

    public static int lineTicks(int packed) {
        return Math.max(0, packed) & 255;
    }

    public static double lineWidth(int packed) {
        return (Math.max(0, packed) >> 8) / 10.0;
    }

    private static final double SEND_RADIUS = 96.0;
    public static final ArrayDeque<PuppetVfx> INBOX = new ArrayDeque<>();

    public static PuppetVfx decode(FriendlyByteBuf buf) {
        return new PuppetVfx(buf.readByte(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readVarInt());
    }

    public static void encode(PuppetVfx self, FriendlyByteBuf buf) {
        self.writeTo(buf);
    }

    private void writeTo(FriendlyByteBuf buf) {
        buf.writeByte(this.kind);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.yaw);
        buf.writeFloat(this.size);
        buf.writeVarInt(this.extra);
    }

    public static void send(ServerLevel level, byte kind, Vec3 pos, float yaw, float size, int extra) {
        SaphyraNetwork.sendToNear(level, pos.x, pos.y, pos.z, SEND_RADIUS, new PuppetVfx(kind, pos.x, pos.y, pos.z, yaw, size, extra));
    }

    public static void handle(PuppetVfx payload, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            if (INBOX.size() < 256) {
                INBOX.add(payload);
            }
        });
        ctx.setPacketHandled(true);
    }
}
