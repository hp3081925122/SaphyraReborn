/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffect
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 *  net.neoforged.neoforge.registries.IForgeRegistry
 *  net.neoforged.neoforge.registries.RegistryObject
 */
package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.potion.BlessingOfTheWhiteFlameMobEffect;
import com.hp.saphyrareborn.potion.CooldownMobEffect;
import com.hp.saphyrareborn.potion.DisgustEffectMobEffect;
import com.hp.saphyrareborn.potion.GriefEffectMobEffect;
import com.hp.saphyrareborn.potion.ImmuneMobEffect;
import com.hp.saphyrareborn.potion.ManicEffectMobEffect;
import com.hp.saphyrareborn.potion.NoBuildMobEffect;
import com.hp.saphyrareborn.potion.NoGravityMobEffect;
import com.hp.saphyrareborn.potion.RotEffectMobEffect;
import com.hp.saphyrareborn.potion.RuleCooldownMobEffect;
import com.hp.saphyrareborn.potion.SprintMobEffect;
import com.hp.saphyrareborn.potion.StayStillMobEffect;
import com.hp.saphyrareborn.potion.StunMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class MokelsBossfightSaphyraModMobEffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(Registries.MOB_EFFECT, "saphyra_reborn");
    public static final DeferredHolder<MobEffect, MobEffect> IMMUNE = REGISTRY.register("immune", () -> new ImmuneMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> COOLDOWN = REGISTRY.register("cooldown", () -> new CooldownMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> GRIEF_EFFECT = REGISTRY.register("grief_effect", () -> new GriefEffectMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> ROT_EFFECT = REGISTRY.register("rot_effect", () -> new RotEffectMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> NO_BUILD = REGISTRY.register("no_build", () -> new NoBuildMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> DISGUST_EFFECT = REGISTRY.register("disgust_effect", () -> new DisgustEffectMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> STAY_STILL = REGISTRY.register("stay_still", () -> new StayStillMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> NO_GRAVITY = REGISTRY.register("no_gravity", () -> new NoGravityMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> SPRINT = REGISTRY.register("sprint", () -> new SprintMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> STUN = REGISTRY.register("stun", () -> new StunMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> RULE_COOLDOWN = REGISTRY.register("rule_cooldown", () -> new RuleCooldownMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> MANIC_EFFECT = REGISTRY.register("manic_effect", () -> new ManicEffectMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> BLESSING_OF_THE_WHITE_FLAME = REGISTRY.register("blessing_of_the_white_flame", () -> new BlessingOfTheWhiteFlameMobEffect());
}















