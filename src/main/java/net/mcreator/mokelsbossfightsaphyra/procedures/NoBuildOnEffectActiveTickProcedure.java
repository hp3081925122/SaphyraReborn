/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class NoBuildOnEffectActiveTickProcedure {
    public static boolean execute(Entity entity) {
        if (entity == null) {
            return false;
        }
        if (entity instanceof Player || entity instanceof ServerPlayer) {
            LivingEntity _livEnt4;
            LivingEntity _livEnt2;
            if (!(entity instanceof LivingEntity) || !(_livEnt2 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.NO_BUILD)) {
                if (entity instanceof Player) {
                    Player _player = (Player)entity;
                    _player.getAbilities().mayBuild = true;
                    _player.onUpdateAbilities();
                }
                return true;
            }
            if (entity instanceof LivingEntity && (_livEnt4 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.NO_BUILD)) {
                if (entity instanceof Player) {
                    Player _player = (Player)entity;
                    _player.getAbilities().mayBuild = false;
                    _player.onUpdateAbilities();
                }
                return true;
            }
        }
        return false;
    }
}






















