package com.hp.saphyrareborn.boss.witch;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.network.SaphyraNetwork;
import java.util.ArrayDeque;
import java.util.function.Supplier;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public record WitchVfx(byte kind, double x, double y, double z, float yaw, float size, int extra) {
    public static final byte SLASH = 0;
    public static final byte CLAW = 1;
    public static final byte SHOCKWAVE = 2;
    public static final byte TELEGRAPH_RING = 3;
    public static final byte TELEGRAPH_LINE = 4;
    public static final byte MOON_SIGIL = 5;
    public static final byte MOON_LANCE = 6;
    public static final byte CHARGE = 7;
    public static final byte PHASE_SHIFT = 8;
    public static final byte DASH_TRAIL = 9;
    public static final byte WHIRL = 10;
    public static final byte FLAME_BURST = 11;
    public static final byte CRESCENT = 12;

    public static final int SLASH_LEFT = 0;
    public static final int SLASH_RIGHT = 1;
    public static final int SLASH_OVERHEAD = 2;
    public static final int SLASH_SPIN = 3;

    private static final double SEND_RADIUS = 96.0;
    public static final ArrayDeque<WitchVfx> INBOX = new ArrayDeque<>();

    public static int ring(int ticks, double innerRadius) {
        int t = Mth.clamp(ticks, 1, 255);
        int inner = Mth.clamp((int) Math.round(innerRadius * 10.0), 0, 400);
        return t | (inner << 8);
    }

    public static int ringTicks(int extra) {
        return Math.max(2, extra & 0xFF);
    }

    public static double ringInner(int extra) {
        return Math.max(0, extra >>> 8) * 0.1;
    }

    public static WitchVfx decode(FriendlyByteBuf buf) {
        return new WitchVfx(buf.readByte(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readVarInt());
    }

    public static void encode(WitchVfx self, FriendlyByteBuf buf) {
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
        SaphyraNetwork.sendToNear(level, pos.x, pos.y, pos.z, SEND_RADIUS, new WitchVfx(kind, pos.x, pos.y, pos.z, yaw, size, extra));
    }

    public static void handle(WitchVfx payload, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            if (INBOX.size() < 256) {
                INBOX.add(payload);
            }
        });
        ctx.setPacketHandled(true);
    }
}
