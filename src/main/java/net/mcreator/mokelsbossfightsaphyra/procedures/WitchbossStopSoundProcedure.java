/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.commands.CommandSource
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.Vec2
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.WitchbossEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class WitchbossStopSoundProcedure {
    public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null) {
            return;
        }
        if (entity instanceof WitchbossEntity) {
            ((WitchbossEntity)entity).setAnimation("empty");
        }
        if (world instanceof ServerLevel) {
            ServerLevel _level = (ServerLevel)world;
            _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), Vec2.ZERO, _level, 4, "", (Component)Component.literal((String)""), _level.getServer(), null).withSuppressedOutput(), "/stopsound @a music");
        }
        if (!world.isClientSide() && world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Very well..."), false);
        }
        MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
            if (!world.isClientSide() && world.getServer() != null) {
                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Enough playing around."), false);
            }
            MokelsBossfightSaphyraMod.queueServerWork(80, () -> {
                if (!world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Saphyra: Let's get serious."), false);
                }
            });
        });
        MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
            if (world instanceof ServerLevel) {
                ServerLevel _level = (ServerLevel)world;
                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), 3.0 + entity.getY(), entity.getZ(), 10, 0.2, 0.2, 0.2, 0.05);
            }
            MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                if (world instanceof ServerLevel) {
                    ServerLevel _level = (ServerLevel)world;
                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), 3.0 + entity.getY(), entity.getZ(), 40, 0.2, 0.2, 0.2, 0.05);
                }
                MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
                    if (world instanceof ServerLevel) {
                        ServerLevel _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), 3.0 + entity.getY(), entity.getZ(), 160, 0.2, 0.2, 0.2, 0.05);
                    }
                });
            });
        });
    }
}





















