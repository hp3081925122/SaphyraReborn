package com.hp.saphyrareborn.highlight;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.List;

/** Server -> client: positions of story containers (gold) and story blocks to interact with next (aqua). */
@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID)
public record StoryHighlightPayload(List<BlockPos> containers, List<BlockPos> targets) implements CustomPacketPayload {
    public static final Type<StoryHighlightPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "story_highlight"));

    public static final StreamCodec<ByteBuf, StoryHighlightPayload> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), StoryHighlightPayload::containers,
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), StoryHighlightPayload::targets,
            StoryHighlightPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        // optional(): vanilla/older clients without the channel are simply not sent anything
        event.registrar("1").optional().playToClient(TYPE, CODEC,
                (payload, context) -> context.enqueueWork(() -> StoryHighlightState.set(payload.containers(), payload.targets())));
    }
}
