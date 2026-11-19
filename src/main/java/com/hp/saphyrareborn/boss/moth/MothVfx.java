package com.hp.saphyrareborn.boss.moth;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.network.SaphyraNetwork;
import java.util.ArrayDeque;
import java.util.function.Supplier;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public record MothVfx(byte kind, double x, double y, double z, float yaw, float size, int extra) {
    public static final byte SLASH = 0;
    public static final byte SHOCKWAVE = 1;
    public static final byte TELEGRAPH_RING = 2;
    public static final byte TELEGRAPH_LINE = 3;
    public static final byte BOLT_FALL = 4;
    public static final byte BOLT_RISE = 5;
    public static final byte LIGHTNING = 6;
    public static final byte LEVITATE = 7;
    public static final byte CHARGE = 8;
    public static final byte PHASE_SHIFT = 9;
    public static final byte BLINK = 10;
    public static final byte DASH_TRAIL = 11;
    public static final byte WARD = 12;
    public static final byte TWIRL = 13;
    public static final byte WINGS = 14;
    public static final byte STORM_WAVE = 15;

    public static final int SLASH_LEFT = 0;
    public static final int SLASH_RIGHT = 1;
    public static final int SLASH_OVERHEAD = 2;
    public static final int SLASH_SPIN = 3;

    private static final double SEND_RADIUS = 96.0;
    public static final ArrayDeque<MothVfx> INBOX = new ArrayDeque<>();

    public static int ring(int ticks, double innerRadius) {
        return Math.max(0, Math.min(255, ticks)) | (Math.max(0, (int) Math.round(innerRadius * 10.0)) << 8);
    }

    public static int ringTicks(int packed) {
        return Math.max(0, packed) & 255;
    }

    public static double ringInner(int packed) {
        return (Math.max(0, packed) >> 8) / 10.0;
    }

    public static MothVfx decode(FriendlyByteBuf buf) {
        return new MothVfx(buf.readByte(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readVarInt());
    }

    public static void encode(MothVfx self, FriendlyByteBuf buf) {
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
        SaphyraNetwork.sendToNear(level, pos.x, pos.y, pos.z, SEND_RADIUS, new MothVfx(kind, pos.x, pos.y, pos.z, yaw, size, extra));
    }

    public static void handle(MothVfx payload, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            if (INBOX.size() < 256) {
                INBOX.add(payload);
            }
        });
        ctx.setPacketHandled(true);
    }
}
