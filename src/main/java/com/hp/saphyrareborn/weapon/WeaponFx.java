package com.hp.saphyrareborn.weapon;

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
public record WeaponFx(byte kind, byte weapon, double x, double y, double z, float yaw, float size, int extra) implements CustomPacketPayload {
    public static final byte SPARK = 0;
    public static final byte CROSS_SLASH = 1;
    public static final byte DASH_SLASH = 2;
    public static final byte RUNE_CIRCLE = 3;
    public static final byte RUNE_BURST = 4;
    public static final byte GROUND_SLAM = 5;
    public static final byte STAFF_NOVA = 6;
    public static final byte STAFF_CAST = 7;
    public static final byte GRIEF_BURST = 8;
    public static final byte RAIN_SPLASH = 9;
    public static final byte LIGHTNING = 10;
    public static final byte SUN_FLARE = 11;
    public static final byte ROT_SPLASH = 12;
    public static final byte MIRROR_BURST = 13;
    public static final byte HIT = 14;

    private static final double SEND_RADIUS = 64.0;
    public static final ArrayDeque<WeaponFx> INBOX = new ArrayDeque<>();

    public static final Type<WeaponFx> TYPE = new Type<>(Identifier.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "weapon_fx"));
    public static final StreamCodec<FriendlyByteBuf, WeaponFx> CODEC = StreamCodec.ofMember(WeaponFx::write, WeaponFx::read);

    private static WeaponFx read(FriendlyByteBuf buf) {
        return new WeaponFx(buf.readByte(), buf.readByte(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readVarInt());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeByte(this.kind);
        buf.writeByte(this.weapon);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.yaw);
        buf.writeFloat(this.size);
        buf.writeVarInt(this.extra);
    }

    public static void send(ServerLevel level, byte kind, WeaponStyle weapon, Vec3 pos, float yaw, float size, int extra) {
        PacketDistributor.sendToPlayersNear(level, null, pos.x, pos.y, pos.z, SEND_RADIUS, new WeaponFx(kind, (byte) weapon.ordinal(), pos.x, pos.y, pos.z, yaw, size, extra));
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
