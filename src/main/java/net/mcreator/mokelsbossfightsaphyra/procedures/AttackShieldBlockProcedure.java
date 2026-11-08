/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.entity.NamelessEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class AttackShieldBlockProcedure {
    public static void execute(Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isAlive()) {
            LivingEntity _entity1;
            if (entity instanceof NamelessEntity) {
                ((NamelessEntity)entity).setAnimation("attackshield");
            }
            if (entity instanceof LivingEntity && !(_entity1 = (LivingEntity)entity).level().isClientSide()) {
                com.hp.saphyrareborn.boss.BossCastLock.lock(_entity1, 90);
            }
            if (entity instanceof LivingEntity && !(_entity1 = (LivingEntity)entity).level().isClientSide()) {
                com.hp.saphyrareborn.boss.BossCastLock.shield(_entity1, 80);
            }
        }
    }
}





