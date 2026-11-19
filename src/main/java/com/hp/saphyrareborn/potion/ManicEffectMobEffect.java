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

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        ManicEffectStartedProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        ManicEffectTickProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
        
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
