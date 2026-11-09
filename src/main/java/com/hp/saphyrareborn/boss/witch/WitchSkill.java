package com.hp.saphyrareborn.boss.witch;

public enum WitchSkill {
    SLASH("slashattack", 40, 40),
    CLAW("clawattack", 50, 50),
    CLAW2("attackclaw2", 90, 90),
    RAIN(null, 0, 55),
    GROUND("attackground", 130, 130),
    AXE("axeattack", 120, 120),
    WAND(null, 58, 58),
    JUMP("jumpattack", 9, 68),
    SPIN("spinattack", 108, 108);

    public final String animation;
    public final int lockTicks;
    public final int duration;

    WitchSkill(String animation, int lockTicks, int duration) {
        this.animation = animation;
        this.lockTicks = lockTicks;
        this.duration = duration;
    }
}
