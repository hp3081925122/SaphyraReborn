/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 *  net.neoforged.neoforge.registries.IForgeRegistry
 *  net.neoforged.neoforge.registries.RegistryObject
 */
package com.hp.saphyrareborn.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class MokelsBossfightSaphyraModSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, "saphyra_reborn");
    public static final DeferredHolder<SoundEvent, SoundEvent> WITCHBOSSMUSIC = REGISTRY.register("witchbossmusic", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "witchbossmusic")));
    public static final DeferredHolder<SoundEvent, SoundEvent> WITCHBOSSTHEME = REGISTRY.register("witchbosstheme", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "witchbosstheme")));
    public static final DeferredHolder<SoundEvent, SoundEvent> USINGFLASKOFHEALING = REGISTRY.register("usingflaskofhealing", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "usingflaskofhealing")));
    public static final DeferredHolder<SoundEvent, SoundEvent> WITCHBOSSFIGHTTHEME = REGISTRY.register("witchbossfighttheme", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "witchbossfighttheme")));
    public static final DeferredHolder<SoundEvent, SoundEvent> CRIMSONFLASKSOUND = REGISTRY.register("crimsonflasksound", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "crimsonflasksound")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SPELLCAST = REGISTRY.register("spellcast", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "spellcast")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SAPHYRAS_THEME = REGISTRY.register("saphyras_theme", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "saphyras_theme")));
    public static final DeferredHolder<SoundEvent, SoundEvent> PHASE2_O_MOTHER = REGISTRY.register("phase2_o_mother", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "phase2_o_mother")));
    public static final DeferredHolder<SoundEvent, SoundEvent> ENEMY_FELLED = REGISTRY.register("enemy_felled", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "enemy_felled")));
    public static final DeferredHolder<SoundEvent, SoundEvent> HEAVYDOOROPENING = REGISTRY.register("heavydooropening", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "heavydooropening")));
    public static final DeferredHolder<SoundEvent, SoundEvent> STATUESOUND = REGISTRY.register("statuesound", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "statuesound")));
    public static final DeferredHolder<SoundEvent, SoundEvent> LYIORSOUNDSTATUE = REGISTRY.register("lyiorsoundstatue", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "lyiorsoundstatue")));
    public static final DeferredHolder<SoundEvent, SoundEvent> RULE_SPELL = REGISTRY.register("rule_spell", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "rule_spell")));
    public static final DeferredHolder<SoundEvent, SoundEvent> NAMELESSSCREAM = REGISTRY.register("namelessscream", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "namelessscream")));
    public static final DeferredHolder<SoundEvent, SoundEvent> NAMELESSTHEME1 = REGISTRY.register("namelesstheme1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "namelesstheme1")));
    public static final DeferredHolder<SoundEvent, SoundEvent> NAMELESSTHEME2 = REGISTRY.register("namelesstheme2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "namelesstheme2")));
    public static final DeferredHolder<SoundEvent, SoundEvent> NAMELESSTHEME3 = REGISTRY.register("namelesstheme3", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "namelesstheme3")));
    public static final DeferredHolder<SoundEvent, SoundEvent> NAMELESSTHEME4 = REGISTRY.register("namelesstheme4", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "namelesstheme4")));
    public static final DeferredHolder<SoundEvent, SoundEvent> NAMELESSTHEME5 = REGISTRY.register("namelesstheme5", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "namelesstheme5")));
    public static final DeferredHolder<SoundEvent, SoundEvent> NAMELESSTHEME6 = REGISTRY.register("namelesstheme6", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "namelesstheme6")));
    public static final DeferredHolder<SoundEvent, SoundEvent> NAMELESSTHEME7 = REGISTRY.register("namelesstheme7", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "namelesstheme7")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SAPHYRASTHEME1 = REGISTRY.register("saphyrastheme1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "saphyrastheme1")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SAPHYRASTHEME2 = REGISTRY.register("saphyrastheme2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "saphyrastheme2")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SAPHYRASTHEME3 = REGISTRY.register("saphyrastheme3", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "saphyrastheme3")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SAPHYRASTHEME4 = REGISTRY.register("saphyrastheme4", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "saphyrastheme4")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SAPHYRASTHEME5 = REGISTRY.register("saphyrastheme5", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "saphyrastheme5")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SAPHYRASTHEME6 = REGISTRY.register("saphyrastheme6", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "saphyrastheme6")));
    public static final DeferredHolder<SoundEvent, SoundEvent> IVORYDEVIL_THEME1 = REGISTRY.register("ivorydevil_theme1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "ivorydevil_theme1")));
    public static final DeferredHolder<SoundEvent, SoundEvent> IVORYDEVIL_THEME2 = REGISTRY.register("ivorydevil_theme2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "ivorydevil_theme2")));
    public static final DeferredHolder<SoundEvent, SoundEvent> IVORYDEVIL_THEME3 = REGISTRY.register("ivorydevil_theme3", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "ivorydevil_theme3")));
    public static final DeferredHolder<SoundEvent, SoundEvent> IVORYDEVIL_THEME4 = REGISTRY.register("ivorydevil_theme4", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "ivorydevil_theme4")));
    public static final DeferredHolder<SoundEvent, SoundEvent> IVORYDEVIL_THEME5 = REGISTRY.register("ivorydevil_theme5", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "ivorydevil_theme5")));
    public static final DeferredHolder<SoundEvent, SoundEvent> IVORYDEVIL_THEME6 = REGISTRY.register("ivorydevil_theme6", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "ivorydevil_theme6")));
    public static final DeferredHolder<SoundEvent, SoundEvent> BOSSKILLED = REGISTRY.register("bosskilled", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "bosskilled")));
    public static final DeferredHolder<SoundEvent, SoundEvent> INFESTED1 = REGISTRY.register("infested1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "infested1")));
    public static final DeferredHolder<SoundEvent, SoundEvent> INFESTED2 = REGISTRY.register("infested2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "infested2")));
    public static final DeferredHolder<SoundEvent, SoundEvent> INFESTED3 = REGISTRY.register("infested3", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "infested3")));
    public static final DeferredHolder<SoundEvent, SoundEvent> INFESTED4 = REGISTRY.register("infested4", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "infested4")));
    public static final DeferredHolder<SoundEvent, SoundEvent> INFESTED5 = REGISTRY.register("infested5", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "infested5")));
    public static final DeferredHolder<SoundEvent, SoundEvent> INFESTED6 = REGISTRY.register("infested6", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "infested6")));
    public static final DeferredHolder<SoundEvent, SoundEvent> IDLEINFESTED = REGISTRY.register("idleinfested", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "idleinfested")));
}

















