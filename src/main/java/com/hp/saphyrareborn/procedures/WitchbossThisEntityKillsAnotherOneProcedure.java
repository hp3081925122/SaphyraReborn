package com.hp.saphyrareborn.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class WitchbossThisEntityKillsAnotherOneProcedure {
    public static void execute(LevelAccessor world, Entity sourceentity) {
        if (sourceentity == null) {
            return;
        }
        if ((sourceentity instanceof ServerPlayer || sourceentity instanceof Player) && world instanceof ServerLevel) {
            ServerLevel _level1 = (ServerLevel)world;
            com.hp.saphyrareborn.boss.BossMusic.stop(_level1, sourceentity.getX(), sourceentity.getY(), sourceentity.getZ(), null);
        }
    }
}
