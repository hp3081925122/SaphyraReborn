package com.hp.saphyrareborn.boss;

import javax.annotation.Nullable;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.LevelAccessor;

public final class BossMusic {
    public static final double RADIUS = 128.0;

    private BossMusic() {
    }

    public static void stop(LevelAccessor world, double x, double y, double z, @Nullable String sound) {
        if (!(world instanceof ServerLevel level)) {
            return;
        }
        ClientboundStopSoundPacket packet = new ClientboundStopSoundPacket(sound == null ? null : ResourceLocation.parse(sound), SoundSource.MUSIC);
        double radiusSqr = RADIUS * RADIUS;
        for (ServerPlayer player : level.players()) {
            if (player.distanceToSqr(x, y, z) <= radiusSqr) {
                player.connection.send(packet);
            }
        }
    }
}
