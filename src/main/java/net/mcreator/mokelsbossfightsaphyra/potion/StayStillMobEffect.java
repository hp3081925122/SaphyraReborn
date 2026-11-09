/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package com.hp.saphyrareborn.potion;

import com.hp.saphyrareborn.procedures.StayStillOnEffectActiveTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class StayStillMobEffect
extends MobEffect {
    public StayStillMobEffect() {
        super(MobEffectCategory.NEUTRAL, -1);
    }

    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        StayStillOnEffectActiveTickProcedure.execute((Entity)entity);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    
}


















