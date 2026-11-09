/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.commands.CommandSource
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.Vec2
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.NamelessEntity;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class NamelessEntityDiesForRealProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) {
            return;
        }
        boolean found = false;
        double sx = 0.0;
        double sy = 0.0;
        double sz = 0.0;
        if (entity instanceof NamelessEntity) {
            ((NamelessEntity)entity).setAnimation("empty");
        }
        MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
            ServerLevel _level1;
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                com.hp.saphyrareborn.boss.BossMusic.stop(_level1, x, y, z, null);
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level1, net.minecraft.server.permissions.LevelBasedPermissionSet.OWNER, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/title @a title {\"translate\":\"text.saphyra_reborn.title.burden_lifted\",\"fallback\":\"Burden Lifted\",\"bold\":true,\"color\":\"#FFCF25\"}");
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level1, net.minecraft.server.permissions.LevelBasedPermissionSet.OWNER, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/title @a times 20 40 20");
            }
            if (world instanceof Level _soundLevel) {
                if (!_soundLevel.isClientSide()) {
                    _soundLevel.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("saphyra_reborn:bosskilled")).map(net.minecraft.core.Holder::value).orElseThrow(), SoundSource.MASTER, 2.0f, 1.0f);
                } else {
                    _soundLevel.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("saphyra_reborn:bosskilled")).map(net.minecraft.core.Holder::value).orElseThrow(), SoundSource.MASTER, 2.0f, 1.0f, false);
                }
            }
        });
    }
}






















