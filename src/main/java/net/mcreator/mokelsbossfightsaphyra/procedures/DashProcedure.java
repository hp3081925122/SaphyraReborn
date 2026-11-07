/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.configuration.StaminaBarConfiguration;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class DashProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        LivingEntity _livEnt2;
        if (entity == null) {
            return;
        }
        if (!(!((Boolean)StaminaBarConfiguration.STAMINABAR.get()).booleanValue() || !entity.isAlive() || entity instanceof LivingEntity && (_livEnt2 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.COOLDOWN) || world.isEmptyBlock(BlockPos.containing((double)entity.getX(), (double)(entity.getY() + -0.1), (double)entity.getZ())) || MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Dash < 10.0)) {
            LivingEntity _entity1;
            LivingEntity _entity2;
            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Dash - 10.0;
            MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                capability.Dash = _setval;
                capability.syncPlayerVariables(entity);
            });
            entity.setDeltaMovement(new Vec3(entity.getDeltaMovement().x() * 7.0, 0.15, entity.getDeltaMovement().z() * 7.0));
            if (entity instanceof LivingEntity && !(_entity2 = (LivingEntity)entity).level().isClientSide()) {
                _entity2.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.IMMUNE, 6, 1, false, false));
            }
            if (entity instanceof LivingEntity && !(_entity1 = (LivingEntity)entity).level().isClientSide()) {
                _entity1.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.COOLDOWN, 10, 1, false, false));
            }
            if (world instanceof ServerLevel) {
                ServerLevel _level1 = (ServerLevel)world;
                ((ServerLevel)_level1).sendParticles((ParticleOptions)ParticleTypes.POOF, entity.getX(), 0.2 + entity.getY(), entity.getZ(), 15, 0.3, 0.0, 0.3, 0.05);
            }
            if (entity instanceof Player) {
                Player _player = (Player)entity;
                _player.getFoodData().setSaturation(-2.0f);
            }
        }
    }
}





