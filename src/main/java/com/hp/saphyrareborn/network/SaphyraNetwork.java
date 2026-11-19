package com.hp.saphyrareborn.network;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.moth.MothVfx;
import com.hp.saphyrareborn.boss.nameless.NamelessVfx;
import com.hp.saphyrareborn.boss.puppet.PuppetVfx;
import com.hp.saphyrareborn.boss.witch.WitchVfx;
import com.hp.saphyrareborn.highlight.StoryHighlightPayload;
import com.hp.saphyrareborn.weapon.WeaponFx;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class SaphyraNetwork {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MokelsBossfightSaphyraMod.MODID, "main"),
            () -> PROTOCOL,
            v -> true,
            v -> true);

    private static boolean registered;

    private SaphyraNetwork() {
    }

    public static synchronized void register() {
        if (registered) {
            return;
        }
        registered = true;
        int id = 0;
        CHANNEL.registerMessage(id++, WeaponFx.class, WeaponFx::encode, WeaponFx::decode, WeaponFx::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(id++, MothVfx.class, MothVfx::encode, MothVfx::decode, MothVfx::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(id++, NamelessVfx.class, NamelessVfx::encode, NamelessVfx::decode, NamelessVfx::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(id++, PuppetVfx.class, PuppetVfx::encode, PuppetVfx::decode, PuppetVfx::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(id++, WitchVfx.class, WitchVfx::encode, WitchVfx::decode, WitchVfx::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(id++, PlayerAnimationPayload.class, PlayerAnimationPayload::encode, PlayerAnimationPayload::decode, PlayerAnimationPayload::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(id++, StoryHighlightPayload.class, StoryHighlightPayload::encode, StoryHighlightPayload::decode, StoryHighlightPayload::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(id++, PlayerVariablesSyncPacket.class, PlayerVariablesSyncPacket::encode, PlayerVariablesSyncPacket::decode, PlayerVariablesSyncPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static void sendToNear(ServerLevel level, double x, double y, double z, double radius, Object msg) {
        CHANNEL.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(x, y, z, radius, level.dimension())), msg);
    }

    public static void sendToPlayer(ServerPlayer player, Object msg) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static void sendToTrackingAndSelf(Entity entity, Object msg) {
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }
}
