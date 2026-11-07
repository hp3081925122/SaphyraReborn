/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.GameType
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.configuration.StaminaBarConfiguration;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

public class EnduranceOverlayProcedure2Procedure {
    public static boolean execute(Entity entity) {
        if (entity == null) {
            return false;
        }
        return MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Dash >= 20.0 && new Object(){

            public boolean checkGamemode(Entity _ent) {
                if (_ent instanceof ServerPlayer) {
                    ServerPlayer _serverPlayer = (ServerPlayer)_ent;
                    return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
                }
                if (_ent.level().isClientSide() && _ent instanceof Player) {
                    Player _player = (Player)_ent;
                    return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SURVIVAL;
                }
                return false;
            }
        }.checkGamemode(entity) && (Boolean)StaminaBarConfiguration.STAMINABAR.get() == true;
    }
}





