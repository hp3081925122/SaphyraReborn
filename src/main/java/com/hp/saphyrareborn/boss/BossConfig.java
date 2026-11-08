package com.hp.saphyrareborn.boss;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class BossConfig {
    public static final ModConfigSpec SPEC;
    public static final Entry WITCHBOSS;
    public static final Entry MOTHBOSS;
    public static final Entry NAMELESS;
    public static final Entry PUPPET;
    public static final Entry PUTRID_ROSE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.comment("Boss 属性，只在 Boss 首次进入世界时写入一次，之后指令、KubeJS 或增益做的调整不会被覆盖。",
                "技能伤害 = attack_damage × 技能倍率。").push("bosses");
        WITCHBOSS = new Entry(builder, "witchboss", 600.0, 14.0, 0.0);
        MOTHBOSS = new Entry(builder, "mothboss", 600.0, 14.0, 0.0);
        NAMELESS = new Entry(builder, "nameless", 450.0, 14.0, 0.0);
        PUPPET = new Entry(builder, "puppet_of_yiva_nemor", 300.0, 14.0, 0.0);
        PUTRID_ROSE = new Entry(builder, "putrid_rose", 1000.0, 0.0, 0.0);
        builder.pop();
        SPEC = builder.build();
    }

    private BossConfig() {
    }

    public static final class Entry {
        public final ModConfigSpec.DoubleValue maxHealth;
        public final ModConfigSpec.DoubleValue attackDamage;
        public final ModConfigSpec.DoubleValue armor;

        Entry(ModConfigSpec.Builder builder, String name, double health, double attack, double armor) {
            builder.push(name);
            this.maxHealth = builder.defineInRange("max_health", health, 1.0, 1.0E6);
            this.attackDamage = builder.defineInRange("attack_damage", attack, 0.0, 1.0E5);
            this.armor = builder.defineInRange("armor", armor, 0.0, 30.0);
            builder.pop();
        }
    }
}
