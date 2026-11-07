/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 */
package com.hp.saphyrareborn.procedures;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class NamelessEntityDiesProcedure {
    public static void execute(Entity sourceentity) {
        LivingEntity _entity;
        if (sourceentity == null) {
            return;
        }
        if ((sourceentity instanceof Player || sourceentity instanceof ServerPlayer) && sourceentity instanceof LivingEntity && !(_entity = (LivingEntity)sourceentity).level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.HARM, 10, 1000, false, false));
        }
    }
}



















