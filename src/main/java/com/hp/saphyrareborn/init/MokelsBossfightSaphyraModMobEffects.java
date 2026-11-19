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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.RegistryObject;

public class MokelsBossfightSaphyraModMobEffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "saphyra_reborn");
    public static final RegistryObject<MobEffect> IMMUNE = REGISTRY.register("immune", () -> new ImmuneMobEffect());
    public static final RegistryObject<MobEffect> COOLDOWN = REGISTRY.register("cooldown", () -> new CooldownMobEffect());
    public static final RegistryObject<MobEffect> GRIEF_EFFECT = REGISTRY.register("grief_effect", () -> new GriefEffectMobEffect());
    public static final RegistryObject<MobEffect> ROT_EFFECT = REGISTRY.register("rot_effect", () -> new RotEffectMobEffect());
    public static final RegistryObject<MobEffect> NO_BUILD = REGISTRY.register("no_build", () -> new NoBuildMobEffect());
    public static final RegistryObject<MobEffect> DISGUST_EFFECT = REGISTRY.register("disgust_effect", () -> new DisgustEffectMobEffect());
    public static final RegistryObject<MobEffect> STAY_STILL = REGISTRY.register("stay_still", () -> new StayStillMobEffect());
    public static final RegistryObject<MobEffect> NO_GRAVITY = REGISTRY.register("no_gravity", () -> new NoGravityMobEffect());
    public static final RegistryObject<MobEffect> SPRINT = REGISTRY.register("sprint", () -> new SprintMobEffect());
    public static final RegistryObject<MobEffect> STUN = REGISTRY.register("stun", () -> new StunMobEffect());
    public static final RegistryObject<MobEffect> RULE_COOLDOWN = REGISTRY.register("rule_cooldown", () -> new RuleCooldownMobEffect());
    public static final RegistryObject<MobEffect> MANIC_EFFECT = REGISTRY.register("manic_effect", () -> new ManicEffectMobEffect());
    public static final RegistryObject<MobEffect> BLESSING_OF_THE_WHITE_FLAME = REGISTRY.register("blessing_of_the_white_flame", () -> new BlessingOfTheWhiteFlameMobEffect());
}
