package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.boss.BossMusic;
import com.hp.saphyrareborn.entity.MothbossEntity;
import com.hp.saphyrareborn.entity.NamelessEntity;
import com.hp.saphyrareborn.entity.PuppetOfYivaNemorEntity;
import com.hp.saphyrareborn.entity.WitchbossEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class PlayerDiesProcedure {
    private static final double RESET_RADIUS = 128.0;

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player player) || !(player.level() instanceof ServerLevel level)) {
            return;
        }
        boolean found = false;
        for (LivingEntity boss : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(RESET_RADIUS), PlayerDiesProcedure::isResettingBoss)) {
            if (boss.isAlive()) {
                boss.setHealth(boss.getMaxHealth());
                if (boss instanceof NamelessEntity nameless) {
                    nameless.getCombat().resetFight();
                }
                if (boss instanceof MothbossEntity moth) {
                    moth.getCombat().resetFight();
                }
                if (boss instanceof WitchbossEntity witch) {
                    witch.getCombat().resetFight();
                }
                if (boss instanceof PuppetOfYivaNemorEntity puppet) {
                    puppet.getCombat().resetFight();
                }
            }
            boss.getPersistentData().putDouble("music", 0.0);
            found = true;
        }
        if (found) {
            BossMusic.stop(level, player.getX(), player.getY(), player.getZ(), null);
        }
    }

    private static boolean isResettingBoss(LivingEntity entity) {
        return entity instanceof WitchbossEntity
                || entity instanceof MothbossEntity
                || entity instanceof PuppetOfYivaNemorEntity
                || entity instanceof NamelessEntity;
    }
}
