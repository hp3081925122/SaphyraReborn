/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.world.entity.Entity;

public class DevotionBarProc1Procedure {
    public static boolean execute(Entity entity) {
        if (entity == null) {
            return false;
        }
        return ((MokelsBossfightSaphyraModVariables.PlayerVariables)entity.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Devotion >= 20.0;
    }
}



