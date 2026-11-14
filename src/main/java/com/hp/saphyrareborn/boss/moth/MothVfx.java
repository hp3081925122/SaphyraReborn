package com.hp.saphyrareborn.boss.moth;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import java.util.ArrayDeque;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID)
public record MothVfx(byte kind, double x, double y, double z, float yaw, float size, int extra) implements CustomPacketPayload {
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

    public static final Type<MothVfx> TYPE = new Type<>(Identifier.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "moth_vfx"));
    public static final StreamCodec<FriendlyByteBuf, MothVfx> CODEC = StreamCodec.ofMember(MothVfx::write, MothVfx::read);

    public static int ring(int ticks, double innerRadius) {
        return Math.max(0, Math.min(255, ticks)) | (Math.max(0, (int) Math.round(innerRadius * 10.0)) << 8);
    }

    public static int ringTicks(int packed) {
        return Math.max(0, packed) & 255;
    }

    public static double ringInner(int packed) {
        return (Math.max(0, packed) >> 8) / 10.0;
    }

    private static MothVfx read(FriendlyByteBuf buf) {
        return new MothVfx(buf.readByte(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readVarInt());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeByte(this.kind);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.yaw);
        buf.writeFloat(this.size);
        buf.writeVarInt(this.extra);
    }

    public static void send(ServerLevel level, byte kind, Vec3 pos, float yaw, float size, int extra) {
        PacketDistributor.sendToPlayersNear(level, null, pos.x, pos.y, pos.z, SEND_RADIUS, new MothVfx(kind, pos.x, pos.y, pos.z, yaw, size, extra));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("1").optional().playToClient(TYPE, CODEC, (payload, context) -> context.enqueueWork(() -> {
            if (INBOX.size() < 256) {
                INBOX.add(payload);
            }
        }));
    }
}
