/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Holder
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

public class LouringClaymoreLivingEntityIsHitWithToolProcedure {
    public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null) {
            return;
        }
        if (sourceentity instanceof Player || sourceentity instanceof ServerPlayer) {
            LivingEntity _livEnt3;
            LivingEntity _livEnt2;
            if (!(sourceentity instanceof LivingEntity) || !(_livEnt2 = (LivingEntity)sourceentity).hasEffect(MokelsBossfightSaphyraModMobEffects.GRIEF_EFFECT)) {
                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(sourceentity).Grief + 10.0;
                MokelsBossfightSaphyraModVariables.withPlayerVariables(sourceentity, capability -> {
                    capability.Grief = _setval;
                    capability.syncPlayerVariables(sourceentity);
                });
            } else if (sourceentity instanceof LivingEntity && (_livEnt3 = (LivingEntity)sourceentity).hasEffect(MokelsBossfightSaphyraModMobEffects.GRIEF_EFFECT)) {
                entity.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC), sourceentity), 10.0f);
                if (world instanceof ServerLevel) {
                    ServerLevel _level1 = (ServerLevel)world;
                    ((ServerLevel)_level1).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), 1.0 + entity.getY(), entity.getZ(), 30, 0.2, 0.2, 0.2, 0.03);
                }
            }
        }
    }
}






















