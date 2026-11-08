package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.LevelAccessor;

/** 按延迟依次广播剧情台词，写法同原模组的对话流程。 */
public final class StoryDialog {
    private StoryDialog() {}

    public static void say(LevelAccessor world, String key) {
        if (!world.isClientSide() && world.getServer() != null) {
            world.getServer().getPlayerList().broadcastSystemMessage(Component.translatable("text.saphyra_reborn.story." + key), false);
        }
    }

    /** lines 为 {延迟tick, 台词键} 交替，延迟从上一句开始累计。返回总时长。 */
    public static int sequence(LevelAccessor world, Object... lines) {
        int t = 0;
        for (int i = 0; i < lines.length; i += 2) {
            t += (Integer) lines[i];
            String key = (String) lines[i + 1];
            MokelsBossfightSaphyraMod.queueServerWork(t, () -> say(world, key));
        }
        return t;
    }
}
