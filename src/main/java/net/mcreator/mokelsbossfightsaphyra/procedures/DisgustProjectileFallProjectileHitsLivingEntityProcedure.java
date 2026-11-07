/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DisgustProjectileFallProjectileHitsLivingEntityProcedure {
    public static void execute(Entity entity) {
        LivingEntity _entity;
        LivingEntity _livEnt0;
        if (entity == null) {
            return;
        }
        if (!(entity instanceof LivingEntity && (_livEnt0 = (LivingEntity)entity).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get()) || !(entity instanceof LivingEntity) || (_entity = (LivingEntity)entity).level().isClientSide())) {
            _entity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 2, false, false));
        }
    }
}





