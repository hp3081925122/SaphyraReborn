package com.hp.saphyrareborn;

import com.hp.saphyrareborn.boss.BossConfig;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlockEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModPaintings;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModSounds;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModTabs;
import com.hp.saphyrareborn.network.SaphyraNetwork;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MokelsBossfightSaphyraMod.MODID)
public final class MokelsBossfightSaphyraMod {
    public static final String MODID = "saphyra_reborn";
    public static final Logger LOGGER = LogManager.getLogger(MokelsBossfightSaphyraMod.class);
    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> WORK_QUEUE = new ConcurrentLinkedQueue<>();

    public MokelsBossfightSaphyraMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        SaphyraNetwork.register();
        MokelsBossfightSaphyraModSounds.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModBlocks.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModBlockEntities.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModItems.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModEntities.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModTabs.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModMobEffects.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModParticleTypes.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModPaintings.REGISTRY.register(modBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BossConfig.SPEC);
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
    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
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
