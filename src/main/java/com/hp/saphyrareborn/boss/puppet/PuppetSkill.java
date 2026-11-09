package com.hp.saphyrareborn.boss.puppet;

public enum PuppetSkill {
    FIRST("attackfirst", 70, 70),
    SECOND("attacksecond", 50, 50),
    FOURTH("attackfourth", 45, 46),
    FIFTH("attackfifth", 50, 50),
    THIRD("attackthird", 78, 78),
    CROSS("attacksecond", 50, 50);

    public final String animation;
    public final int lockTicks;
    public final int duration;

    PuppetSkill(String animation, int lockTicks, int duration) {
        this.animation = animation;
        this.lockTicks = lockTicks;
        this.duration = duration;
    }
}
