/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.world.entity.Entity;

public class PoolOfRotHoldingTickProcedure {
    public static void execute(Entity entity) {
        if (entity == null) {
            return;
        }
        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Rotting + 0.3;
        MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
            capability.Rotting = _setval;
            capability.syncPlayerVariables(entity);
        });
    }
}



















