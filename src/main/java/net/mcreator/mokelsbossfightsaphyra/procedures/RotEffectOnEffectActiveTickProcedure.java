/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.Holder
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.entity.NamelessEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RotEffectOnEffectActiveTickProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isAlive()) {
            entity.getPersistentData().putDouble("ROT", entity.getPersistentData().getDoubleOr("ROT", 0.0) + 1.0);
        }
        if (entity.getPersistentData().getDoubleOr("ROT", 0.0) == 20.0 && new Object(){

            public boolean checkGamemode(Entity _ent) {
                if (_ent instanceof ServerPlayer) {
                    ServerPlayer _serverPlayer = (ServerPlayer)_ent;
                    return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
                }
                return false;
            }
        }.checkGamemode(entity)) {
            ServerLevel _level1;
            entity.hurt(new DamageSource((Holder)world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).get(ResourceKey.create((ResourceKey)Registries.DAMAGE_TYPE, (Identifier)Identifier.parse("saphyra_reborn:rot"))).orElseThrow()), 4.0f);
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                ((ServerLevel)_level1).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), entity.getX(), 1.0 + entity.getY(), entity.getZ(), 15, 0.2, 0.2, 0.2, 0.1);
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                ((ServerLevel)_level1).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), entity.getX(), 1.0 + entity.getY(), entity.getZ(), 30, 0.2, 0.2, 0.2, 0.1);
            }
            if (!world.isClientSide()) {
                for (NamelessEntity nameless : world.getEntitiesOfClass(NamelessEntity.class, entity.getBoundingBox().inflate(16.0), NamelessEntity::isAlive)) {
                    nameless.heal(16.0f);
                }
            }
        }
        if (entity.getPersistentData().getDoubleOr("ROT", 0.0) == 80.0) {
            entity.getPersistentData().putDouble("ROT", 0.0);
        }
    }
}






















