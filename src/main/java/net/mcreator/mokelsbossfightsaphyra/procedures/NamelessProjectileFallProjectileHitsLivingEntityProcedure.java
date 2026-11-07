/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.NamelessEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;

public class NamelessProjectileFallProjectileHitsLivingEntityProcedure {
    public static void execute(LevelAccessor world, Entity entity, Entity immediatesourceentity) {
        LivingEntity _livEnt4;
        if (entity == null || immediatesourceentity == null) {
            return;
        }
        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
            if (!immediatesourceentity.level().isClientSide()) {
                immediatesourceentity.discard();
            }
        });
        if (!(!(entity instanceof LivingEntity) || entity instanceof NamelessEntity || entity instanceof LivingEntity && (_livEnt4 = (LivingEntity)entity).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE))) {
            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Rotting + 40.0;
            MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                capability.Rotting = _setval;
                capability.syncPlayerVariables(entity);
            });
            entity.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create((ResourceKey)Registries.DAMAGE_TYPE, (ResourceLocation)ResourceLocation.parse("mokels_bossfight_saphyra:rot")))), 4.0f);
        }
    }
}



















