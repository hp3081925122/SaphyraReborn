package com.hp.saphyrareborn.boss.moth;

public enum MothSkill {
    SMITE("attacksmite", 48, 48),
    ONCE("attackswordonce", 48, 48),
    TWIRL("attacktwirl", 83, 83),
    GROUND("attackground", 68, 68),
    SKY("attacksky", 88, 88),
    BACK("attackback", 33, 33),
    BACK_ONCE("attackback", 35, 83),
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
