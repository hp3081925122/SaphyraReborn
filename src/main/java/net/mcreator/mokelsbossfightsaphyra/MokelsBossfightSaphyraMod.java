package com.hp.saphyrareborn;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlockEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModSounds;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModTabs;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.util.thread.EffectiveSide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MokelsBossfightSaphyraMod.MODID)
public final class MokelsBossfightSaphyraMod {
    public static final String MODID = "saphyra_reborn";
    public static final Logger LOGGER = LogManager.getLogger(MokelsBossfightSaphyraMod.class);
    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> WORK_QUEUE = new ConcurrentLinkedQueue<>();

    public MokelsBossfightSaphyraMod(IEventBus modBus, ModContainer container) {
        NeoForge.EVENT_BUS.register(this);
        SaphyraAttachments.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModSounds.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModBlocks.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModBlockEntities.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModItems.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModEntities.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModTabs.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModMobEffects.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModParticleTypes.REGISTRY.register(modBus);
        container.registerConfig(ModConfig.Type.COMMON, com.hp.saphyrareborn.boss.BossConfig.SPEC);
    }

    public static void queueServerWork(int ticks, Runnable action) {
        if (EffectiveSide.get() != LogicalSide.SERVER) {
            return;
        }
        if (ticks <= 0) {
            runSafely(action);
            return;
        }
        WORK_QUEUE.add(new AbstractMap.SimpleEntry<>(action, ticks));
    }

    private static void runSafely(Runnable action) {
        try {
            action.run();
        } catch (RuntimeException e) {
            LOGGER.error("Saphyra delayed task failed", e);
        }
    }

    @SubscribeEvent
    public void tick(ServerTickEvent.Post event) {
        ArrayList<Runnable> ready = new ArrayList<>();
        Iterator<AbstractMap.SimpleEntry<Runnable, Integer>> it = WORK_QUEUE.iterator();
        while (it.hasNext()) {
            AbstractMap.SimpleEntry<Runnable, Integer> work = it.next();
            int left = work.getValue() - 1;
            if (left <= 0) {
                it.remove();
                ready.add(work.getKey());
            } else {
                work.setValue(left);
            }
        }
        for (Runnable action : ready) {
            runSafely(action);
        }
    }

    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        WORK_QUEUE.clear();
    }
}


















