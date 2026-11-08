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

import com.hp.saphyrareborn.procedures.GriefEffectEffectExpiresProcedure;
import com.hp.saphyrareborn.procedures.GriefEffectEffectStartedappliedProcedure;
import com.hp.saphyrareborn.procedures.GriefEffectOnEffectActiveTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.LevelAccessor;

public class GriefEffectMobEffect
extends MobEffect {
    public GriefEffectMobEffect() {
        super(MobEffectCategory.HARMFUL, -16764007);
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        GriefEffectEffectStartedappliedProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
    }

    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        GriefEffectOnEffectActiveTickProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
        return true;
    }

    /** Called from {@link CurseEffectEvents} when the effect expires or is removed. */
    public static void onRemoved(LivingEntity entity) {
        GriefEffectEffectExpiresProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}



















