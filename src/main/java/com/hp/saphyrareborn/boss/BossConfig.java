package com.hp.saphyrareborn.boss;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class BossConfig {
    public static final ModConfigSpec SPEC;
    public static final Entry WITCHBOSS;
    public static final Entry MOTHBOSS;
    public static final Entry NAMELESS;
    public static final Entry PUPPET;
    public static final Entry PUTRID_ROSE;
    public static final ModConfigSpec.BooleanValue NAMELESS_PHASE2_ENABLED;
    public static final ModConfigSpec.DoubleValue NAMELESS_PHASE2_RATIO;
    public static final ModConfigSpec.DoubleValue NAMELESS_PHASE2_CADENCE;
    public static final ModConfigSpec.DoubleValue NAMELESS_SHIELD_BREAK_RATIO;
    public static final ModConfigSpec.IntValue NAMELESS_STAGGER_TICKS;
    public static final ModConfigSpec.BooleanValue MOTH_PHASE2_ENABLED;
    public static final ModConfigSpec.DoubleValue MOTH_PHASE2_RATIO;
    public static final ModConfigSpec.DoubleValue MOTH_PHASE2_CADENCE;
    public static final ModConfigSpec.DoubleValue MOTH_PHASE2_NEW_SKILL_CHANCE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.comment("Boss 属性，只在 Boss 首次进入世界时写入一次，之后指令、KubeJS 或增益做的调整不会被覆盖。",
                "技能伤害 = attack_damage × 技能倍率。").push("bosses");
        WITCHBOSS = new Entry(builder, "witchboss", 600.0, 14.0, 0.0);
        MOTHBOSS = new Entry(builder, "mothboss", 600.0, 14.0, 0.0);
        NAMELESS = new Entry(builder, "nameless", 450.0, 14.0, 0.0);
        PUPPET = new Entry(builder, "puppet_of_yiva_nemor", 300.0, 14.0, 0.0);
        PUTRID_ROSE = new Entry(builder, "putrid_rose", 1000.0, 0.0, 0.0);
        builder.push("nameless_mechanics");
        NAMELESS_PHASE2_ENABLED = builder.comment("是否启用无名守门人二阶段").define("phase2_enabled", true);
        NAMELESS_PHASE2_RATIO = builder.comment("血量低于最大生命的该比例时进入二阶段").defineInRange("phase2_health_ratio", 0.5, 0.01, 0.99);
        NAMELESS_PHASE2_CADENCE = builder.comment("二阶段出招节奏倍率，越小出招越快").defineInRange("phase2_cadence", 0.75, 0.3, 1.0);
        NAMELESS_SHIELD_BREAK_RATIO = builder.comment("举盾期间累计被格挡伤害达到最大生命的该比例时破防，0 为关闭").defineInRange("shield_break_ratio", 0.06, 0.0, 1.0);
        NAMELESS_STAGGER_TICKS = builder.comment("破防硬直时长（tick）").defineInRange("stagger_ticks", 60, 10, 400);
        builder.pop();
        builder.push("mothboss_mechanics");
        MOTH_PHASE2_ENABLED = builder.comment("是否启用墨蛾二阶段").define("phase2_enabled", true);
        MOTH_PHASE2_RATIO = builder.comment("血量低于最大生命的该比例时进入二阶段").defineInRange("phase2_health_ratio", 0.5, 0.01, 0.99);
        MOTH_PHASE2_CADENCE = builder.comment("二阶段出招节奏倍率，越小出招越快").defineInRange("phase2_cadence", 0.75, 0.3, 1.0);
        MOTH_PHASE2_NEW_SKILL_CHANCE = builder.comment("二阶段每个出招节点改用新招（突刺、三连斩、格挡反击）的概率").defineInRange("phase2_new_skill_chance", 0.34, 0.0, 1.0);
        builder.pop();
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
