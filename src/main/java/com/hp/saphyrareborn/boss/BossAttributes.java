package com.hp.saphyrareborn.boss;

import com.hp.saphyrareborn.entity.MothbossEntity;
import com.hp.saphyrareborn.entity.NamelessEntity;
import com.hp.saphyrareborn.entity.PuppetOfYivaNemorEntity;
import com.hp.saphyrareborn.entity.PutridRoseEntity;
import com.hp.saphyrareborn.entity.WitchbossEntity;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = "saphyra_reborn")
public final class BossAttributes {
    public static final double SKILL_BASE_DAMAGE = 14.0;
    private static final String APPLIED_KEY = "SaphyraConfigApplied";

    private BossAttributes() {
    }

    public static float skillDamage(Entity caster, double multiplier) {
        double attack = SKILL_BASE_DAMAGE;
        if (caster instanceof LivingEntity living && living.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            attack = living.getAttributeValue(Attributes.ATTACK_DAMAGE);
        }
        double damage = attack * multiplier;
        return Double.isFinite(damage) && damage > 0.0 ? (float) damage : 0.0f;
    }

    @SubscribeEvent
    public static void onJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || !(event.getEntity() instanceof LivingEntity boss)) {
            return;
        }
        BossConfig.Entry entry = entryFor(boss);
        if (entry == null || boss.getPersistentData().getBooleanOr(APPLIED_KEY, false)) {
            return;
        }
        float ratio = boss.getMaxHealth() > 0.0f ? boss.getHealth() / boss.getMaxHealth() : 1.0f;
        setBase(boss, Attributes.MAX_HEALTH, entry.maxHealth.get());
        setBase(boss, Attributes.ATTACK_DAMAGE, entry.attackDamage.get());
        setBase(boss, Attributes.ARMOR, entry.armor.get());
        boss.setHealth(Math.max(1.0f, boss.getMaxHealth() * Math.min(1.0f, ratio)));
        boss.getPersistentData().putBoolean(APPLIED_KEY, true);
    }

    private static BossConfig.Entry entryFor(LivingEntity entity) {
        if (entity instanceof WitchbossEntity) {
            return BossConfig.WITCHBOSS;
        }
        if (entity instanceof MothbossEntity) {
            return BossConfig.MOTHBOSS;
        }
        if (entity instanceof NamelessEntity) {
            return BossConfig.NAMELESS;
        }
        if (entity instanceof PuppetOfYivaNemorEntity) {
            return BossConfig.PUPPET;
        }
        if (entity instanceof PutridRoseEntity) {
            return BossConfig.PUTRID_ROSE;
        }
        return null;
    }

    private static void setBase(LivingEntity entity, Holder<Attribute> attribute, double value) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance != null && Double.isFinite(value)) {
            instance.setBaseValue(value);
        }
    }
}
