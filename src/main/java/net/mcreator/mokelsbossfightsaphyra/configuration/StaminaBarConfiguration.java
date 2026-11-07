/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.neoforge.common.ModConfigSpec
 *  net.neoforged.neoforge.common.ModConfigSpec$Builder
 *  net.neoforged.neoforge.common.ModConfigSpec$ConfigValue
 */
package com.hp.saphyrareborn.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;

public class StaminaBarConfiguration {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.ConfigValue<Boolean> STAMINABAR;

    static {
        BUILDER.push("Stamina Bar Config");
        STAMINABAR = BUILDER.comment("Stamina Bar Config").define("Enable Stamina Bar", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}















