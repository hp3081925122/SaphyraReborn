package com.hp.saphyrareborn.procedures;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;

public class GriefEffectOnEffectActiveTickProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (new Object(){

            public boolean checkGamemode(Entity _ent) {
                if (_ent instanceof ServerPlayer) {
                    ServerPlayer _serverPlayer = (ServerPlayer)_ent;
                    return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
                }
                if (_ent.level().isClientSide() && _ent instanceof Player) {
                    Player _player = (Player)_ent;
                    return Boolean.TRUE.equals(net.minecraftforge.fml.DistExecutor.unsafeCallWhenOn(net.minecraftforge.api.distmarker.Dist.CLIENT, () -> () -> com.hp.saphyrareborn.client.SaphyraClientBridge.isClientGameMode(_player, GameType.SURVIVAL)));
                }
                return false;
            }
        }.checkGamemode(entity) && Math.random() <= 1.0 && world instanceof ServerLevel) {
            ServerLevel _level1 = (ServerLevel)world;
            ((ServerLevel)_level1).sendParticles((ParticleOptions)ParticleTypes.RAIN, entity.getX(), 1.3 + entity.getY(), entity.getZ(), 1, 0.05, 0.05, 0.05, 0.0);
        }
    }
}
