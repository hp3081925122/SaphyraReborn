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

import com.hp.saphyrareborn.procedures.ManicEffectStartedProcedure;
import com.hp.saphyrareborn.procedures.ManicEffectTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.LevelAccessor;

public class ManicEffectMobEffect
extends MobEffect {
    public ManicEffectMobEffect() {
        super(MobEffectCategory.HARMFUL, -256);
    }

    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        ManicEffectStartedProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
    }

    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        ManicEffectTickProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
        return true;
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
















