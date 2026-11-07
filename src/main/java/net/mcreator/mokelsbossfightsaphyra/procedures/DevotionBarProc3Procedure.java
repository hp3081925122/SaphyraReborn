/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.world.entity.Entity;

public class DevotionBarProc3Procedure {
    public static boolean execute(Entity entity) {
        if (entity == null) {
            return false;
        }
        return MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Devotion >= 40.0;
    }
}




















