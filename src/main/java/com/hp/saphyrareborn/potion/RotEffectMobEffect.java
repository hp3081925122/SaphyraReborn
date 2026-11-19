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
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        RotEffectEffectStartedappliedProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        RotEffectOnEffectActiveTickProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
        
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
