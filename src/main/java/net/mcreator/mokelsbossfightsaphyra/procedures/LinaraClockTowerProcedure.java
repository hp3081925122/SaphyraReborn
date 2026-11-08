package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.LinaraClockTowerEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;

/**
 * 莉娜拉支线（续作内容，原模组未实现）：
 * 交出狂乱之眼 -> 打败钟楼里的伊瓦的傀儡 -> 莉娜拉现身 -> 交出傀儡的记忆 -> 获得奖励。
 */
public final class LinaraClockTowerProcedure {
    private LinaraClockTowerProcedure() {}

    /** 傀儡死亡时调用：附近有已交出狂乱之眼的玩家，就让莉娜拉现身。 */
    public static void onPuppetDefeated(ServerLevel level, Entity puppet) {
        List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(puppet.blockPosition()).inflate(48.0));
        boolean unlocked = players.stream().anyMatch(p -> MokelsBossfightSaphyraModVariables.getPlayerVariables(p).LinaraQuest == 1);
        if (!unlocked || !level.getEntitiesOfClass(LinaraClockTowerEntity.class, new AABB(puppet.blockPosition()).inflate(48.0)).isEmpty()) {
            return;
        }
        double x = puppet.getX(), y = puppet.getY(), z = puppet.getZ();
        MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
            level.sendParticles(MokelsBossfightSaphyraModParticleTypes.PURPLE_FLAME_PARTICLE.get(), x, y, z, 250, 0.2, 1.2, 0.2, 0.1);
            MokelsBossfightSaphyraModEntities.LINARA_CLOCK_TOWER.get().spawn(level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
        });
    }

    /** 莉娜拉生成时的台词。 */
    public static void intro(LevelAccessor world, Entity linara) {
        StoryDialog.sequence(world,
                20, "linara_ct_1",
                100, "linara_ct_2",
                120, "linara_ct_3",
                120, "linara_ct_4");
    }

    /** 玩家把伊瓦的傀儡的记忆交给莉娜拉。 */
    public static void convert(LevelAccessor world, Entity linara, Player player) {
        if (linara instanceof LinaraClockTowerEntity l) {
            l.setAnimation("sky");
        }
        MokelsBossfightSaphyraModVariables.PlayerVariables vars = MokelsBossfightSaphyraModVariables.getPlayerVariables(player);
        vars.LinaraQuest = 2;
        vars.syncPlayerVariables(player);

        int end = StoryDialog.sequence(world,
                40, "linara_ct_5",
                80, "linara_ct_6",
                100, "linara_ct_7");
        MokelsBossfightSaphyraMod.queueServerWork(120, () -> {
            if (!linara.isAlive() || !(world instanceof ServerLevel level)) {
                return;
            }
            level.sendParticles(MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get(), linara.getX(), linara.getY(), linara.getZ(), 300, 0.2, 1.0, 0.2, 0.05);
            for (ItemStack reward : List.of(
                    new ItemStack(MokelsBossfightSaphyraModItems.SPIRIT_RUNE_SWORD.get()),
                    new ItemStack(MokelsBossfightSaphyraModItems.SPRIRITUAL_RUNE.get()),
                    new ItemStack(MokelsBossfightSaphyraModItems.EMPTY_RULE.get()))) {
                ItemEntity drop = new ItemEntity(level, linara.getX(), linara.getY(), linara.getZ(), reward);
                drop.setPickUpDelay(2);
                drop.setUnlimitedLifetime();
                level.addFreshEntity(drop);
            }
        });
        MokelsBossfightSaphyraMod.queueServerWork(end + 60, () -> {
            if (linara.isAlive() && world instanceof ServerLevel level) {
                level.sendParticles(MokelsBossfightSaphyraModParticleTypes.PURPLE_FLAME_PARTICLE.get(), linara.getX(), linara.getY(), linara.getZ(), 400, 0.2, 1.2, 0.2, 0.1);
                linara.discard();
            }
        });
    }
}
