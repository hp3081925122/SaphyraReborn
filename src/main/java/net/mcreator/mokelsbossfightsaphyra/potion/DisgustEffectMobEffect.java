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

import com.hp.saphyrareborn.procedures.DisgustEffectEffectStartedappliedProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.LevelAccessor;

public class DisgustEffectMobEffect
extends MobEffect {
    public DisgustEffectMobEffect() {
        super(MobEffectCategory.HARMFUL, -3355648);
    }

    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        DisgustEffectEffectStartedappliedProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}

















