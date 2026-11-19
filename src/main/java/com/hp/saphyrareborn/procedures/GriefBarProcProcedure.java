package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.world.entity.Entity;

public class GriefBarProcProcedure {
    public static boolean execute(Entity entity) {
        if (entity == null) {
            return false;
        }
        return MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Grief >= 20.0;
    }
}
