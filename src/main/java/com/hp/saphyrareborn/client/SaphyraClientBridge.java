package com.hp.saphyrareborn.client;

import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;

public final class SaphyraClientBridge {
    private SaphyraClientBridge() {
    }

    public static void applyPlayerVars(CompoundTag tag) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            MokelsBossfightSaphyraModVariables.getPlayerVariables(player).deserializeNBT(tag);
        }
    }

    public static void playPlayerAnimation(int target, String animation, boolean override) {
        PlayerAnimations.play(target, animation, override);
    }

    public static boolean isClientGameMode(Player player, GameType mode) {
        ClientPacketListener conn = Minecraft.getInstance().getConnection();
        if (conn == null) {
            return false;
        }
        PlayerInfo info = conn.getPlayerInfo(player.getGameProfile().getId());
        return info != null && info.getGameMode() == mode;
    }

    public static void displayItemActivation(ItemStack stack) {
        Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
    }
}
