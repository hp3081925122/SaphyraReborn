package com.hp.saphyrareborn.boss.moth;

public enum MothSkill {
    SMITE("attacksmite", 50, 50),
    ONCE("attackswordonce", 50, 50),
    TWIRL("attacktwirl", 80, 80),
    GROUND("attackground", 80, 80),
    SKY("attacksky", 90, 90),
    BACK("attackback", 35, 35),
    BACK_ONCE("attackback", 35, 85),
    STAB("attackstab", 30, 32),
    TRIPLE("attacktriple", 50, 50),
    BLOCK("block", 70, 70);

    public final String animation;
    public final int lockTicks;
    public final int duration;

    MothSkill(String animation, int lockTicks, int duration) {
        this.animation = animation;
        this.lockTicks = lockTicks;
        this.duration = duration;
    }

    public static MothSkill byName(String name) {
        for (MothSkill skill : values()) {
            if (skill.name().equals(name)) {
                return skill;
            }
        }
        return null;
    }
}
