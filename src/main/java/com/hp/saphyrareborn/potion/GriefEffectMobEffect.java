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
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        GriefEffectEffectStartedappliedProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        GriefEffectOnEffectActiveTickProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
        
    }

    
    public static void onRemoved(LivingEntity entity) {
        GriefEffectEffectExpiresProcedure.execute((LevelAccessor)entity.level(), (Entity)entity);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
