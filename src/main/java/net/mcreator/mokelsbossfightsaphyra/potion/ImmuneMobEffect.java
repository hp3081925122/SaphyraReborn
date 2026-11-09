/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 */
package com.hp.saphyrareborn.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;

public class ImmuneMobEffect
extends MobEffect {
    public ImmuneMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -1);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    
}


















