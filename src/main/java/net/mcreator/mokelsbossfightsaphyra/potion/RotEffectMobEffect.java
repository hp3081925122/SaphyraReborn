/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeMap
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.potion;

import com.hp.saphyrareborn.procedures.RotEffectEffectStartedappliedProcedure;
import com.hp.saphyrareborn.procedures.RotEffectOnEffectActiveTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.LevelAccessor;

public class RotEffectMobEffect
extends MobEffect {
    public RotEffectMobEffect() {
        super(MobEffectCategory.HARMFUL, -6750208);
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        RotEffectEffectStartedappliedProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
    }

    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        RotEffectOnEffectActiveTickProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}


















