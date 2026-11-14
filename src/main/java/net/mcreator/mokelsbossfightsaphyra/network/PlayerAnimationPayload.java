package com.hp.saphyrareborn.network;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.client.PlayerAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

/** Server-to-client: play a player animation (see client.PlayerAnimations) on the given player. */
@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public record PlayerAnimationPayload(int target, String animation, boolean override) implements CustomPacketPayload {
    public static final Type<PlayerAnimationPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "player_animation"));
    public static final StreamCodec<FriendlyByteBuf, PlayerAnimationPayload> CODEC = StreamCodec.ofMember(PlayerAnimationPayload::write, PlayerAnimationPayload::read);

    private static PlayerAnimationPayload read(FriendlyByteBuf buf) {
        return new PlayerAnimationPayload(buf.readVarInt(), buf.readUtf(), buf.readBoolean());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeVarInt(this.target);
        buf.writeUtf(this.animation);
        buf.writeBoolean(this.override);
    }

    @Override
    public Type<PlayerAnimationPayload> type() {
        return TYPE;
    }

    /** Sends to the player and everyone tracking them. Server side only. */
    public static void send(Entity player, String animation) {
        if (player.level().isClientSide()) return;
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(player, new PlayerAnimationPayload(player.getId(), animation, true));
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("1").optional().playToClient(TYPE, CODEC, (payload, context) -> context.enqueueWork(
                () -> PlayerAnimations.play(payload.target, payload.animation, payload.override)));
    }
}
