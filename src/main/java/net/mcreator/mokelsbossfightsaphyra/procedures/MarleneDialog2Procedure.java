/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class MarleneDialog2Procedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: Good..."), false);
        }
        MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
            if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: As promised, i shall convert the memory you achieved."), false);
            }
            MokelsBossfightSaphyraMod.queueServerWork(140, () -> {
                if (entity.isAlive() && !world.isClientSide() && world.getServer() != null) {
                    world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"Marlene: Just Hand it over."), false);
                }
            });
        });
    }
}





















