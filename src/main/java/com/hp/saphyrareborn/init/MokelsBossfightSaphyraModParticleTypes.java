package com.hp.saphyrareborn.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.RegistryObject;

public class MokelsBossfightSaphyraModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "saphyra_reborn");
    public static final RegistryObject<SimpleParticleType> GHOSTLY_FLAME_PARTICLE = REGISTRY.register("ghostly_flame_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> PURPLE_FLAME_PARTICLE = REGISTRY.register("purple_flame_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> REDFLAME_PARTICLE = REGISTRY.register("redflame_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> REDFIRE_PARTICLE = REGISTRY.register("redfire_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> PUKE_PARTICLE = REGISTRY.register("puke_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> DISGUST_OMEN_PARTICLE = REGISTRY.register("disgust_omen_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GOLDEN_PARTICLE = REGISTRY.register("golden_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_GHOSTLY_FLAME_PARTICLE = REGISTRY.register("long_ghostly_flame_particle", () -> new SimpleParticleType(true));
}
