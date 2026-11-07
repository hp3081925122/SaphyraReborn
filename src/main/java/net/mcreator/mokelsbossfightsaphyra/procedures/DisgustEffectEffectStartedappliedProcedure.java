/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.commands.CommandSource
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.Vec2
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class DisgustEffectEffectStartedappliedProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        ServerLevel _level1;
        if (entity == null) {
            return;
        }
        double baseRate = 0.0;
        double rateWithAmplifier = 0.0;
        if (world instanceof ServerLevel) {
            _level1 = (ServerLevel)world;
            _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, _level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/title @p times 20 20 20");
        }
        if (world instanceof ServerLevel) {
            _level1 = (ServerLevel)world;
            _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, _level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/title @p title {\"text\":\"Disgust\",\"color\":\"#AAD02A\"}");
        }
        if (entity instanceof Player) {
            Player _player = (Player)entity;
            _player.getFoodData().setSaturation(-100.0f);
        }
    }
}





