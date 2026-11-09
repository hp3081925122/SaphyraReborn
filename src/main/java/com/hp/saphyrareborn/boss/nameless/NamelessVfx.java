package com.hp.saphyrareborn.boss.nameless;

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
public record NamelessVfx(byte kind, double x, double y, double z, float yaw, float size, int extra) implements CustomPacketPayload {
    public static final byte SLASH = 0;
    public static final byte SLAM = 1;
    public static final byte TELEGRAPH_RING = 2;
    public static final byte TELEGRAPH_LINE = 3;
    public static final byte ROT_PILLAR = 4;
    public static final byte ROAR = 5;
    public static final byte SHIELD_SPARK = 6;
    public static final byte SHIELD_BREAK = 7;
    public static final byte CHARGE = 8;
    public static final byte PHASE_SHIFT = 9;
    public static final byte SHIELD_RAISE = 10;
    public static final byte ROT_POOL = 11;

    public static final int SLASH_LEFT = 0;
    public static final int SLASH_RIGHT = 1;
    public static final int SLASH_OVERHEAD = 2;

    private static final double SEND_RADIUS = 96.0;
    public static final ArrayDeque<NamelessVfx> INBOX = new ArrayDeque<>();

    public static final Type<NamelessVfx> TYPE = new Type<>(Identifier.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "nameless_vfx"));
    public static final StreamCodec<FriendlyByteBuf, NamelessVfx> CODEC = StreamCodec.ofMember(NamelessVfx::write, NamelessVfx::read);

    private static NamelessVfx read(FriendlyByteBuf buf) {
        return new NamelessVfx(buf.readByte(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readVarInt());
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
        PacketDistributor.sendToPlayersNear(level, null, pos.x, pos.y, pos.z, SEND_RADIUS, new NamelessVfx(kind, pos.x, pos.y, pos.z, yaw, size, extra));
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
