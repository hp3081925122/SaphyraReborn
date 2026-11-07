/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 */
package com.hp.saphyrareborn.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class RuleCooldownMobEffect
extends MobEffect {
    public RuleCooldownMobEffect() {
        super(MobEffectCategory.NEUTRAL, -1);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}


















