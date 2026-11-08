package com.hp.saphyrareborn.boss.nameless;

public enum NamelessSkill {
    ONCE("attackonce", 50, 50, 50),
    DOUBLE("attackdouble", 50, 50, 50),
    FLOAT("attackfloat", 0, 30, 30),
    ROT("attackrot", 40, 40, 35),
    SHIELD("attackshield", 90, 90, 90),
    SHIELD_SLASH("attackshieldslash", 70, 70, 70),
    SUPER("attacksuper", 90, 90, 90),
    DOUBLE_ONCE("attackdouble", 50, 90, 90),
    GROUND("attackground", 110, 110, 110),
    BRUELLEN("attackbr\u00fcllen", 110, 110, 110),
    STOMP("attackstomp", 80, 80, 80);

    public final String animation;
    public final int lockTicks;
    public final int duration;
    public final int repeatInterval;

    NamelessSkill(String animation, int lockTicks, int duration, int repeatInterval) {
        this.animation = animation;
        this.lockTicks = lockTicks;
        this.duration = duration;
        this.repeatInterval = repeatInterval;
    }

    public static NamelessSkill byName(String name) {
        for (NamelessSkill skill : values()) {
            if (skill.name().equals(name)) {
                return skill;
            }
        }
        return null;
    }
}
