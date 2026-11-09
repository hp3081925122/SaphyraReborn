/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;

public class ManicEffectTickProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isAlive()) {
            entity.getPersistentData().putDouble("MANIC", entity.getPersistentData().getDoubleOr("MANIC", 0.0) + 1.0);
        }
        if (entity.getPersistentData().getDoubleOr("MANIC", 0.0) == 5.0 && new Object(){

            public boolean checkGamemode(Entity _ent) {
                if (_ent instanceof ServerPlayer) {
                    ServerPlayer _serverPlayer = (ServerPlayer)_ent;
                    return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
                }
                return false;
            }
        }.checkGamemode(entity)) {
            entity.hurt(new DamageSource((Holder)world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).get(ResourceKey.create((ResourceKey)Registries.DAMAGE_TYPE, (Identifier)Identifier.parse("saphyra_reborn:manic"))).orElseThrow()), 6.0f);
        }
        if (entity.getPersistentData().getDoubleOr("MANIC", 0.0) == 10.0) {
            entity.getPersistentData().putDouble("MANIC", 0.0);
        }
    }
}






















