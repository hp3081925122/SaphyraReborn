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
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.network.NetworkEvent;
import net.neoforged.network.NetworkRegistry;
import net.neoforged.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MokelsBossfightSaphyraMod.MODID)
public final class MokelsBossfightSaphyraMod {
    public static final String MODID = "saphyra_reborn";
    public static final Logger LOGGER = LogManager.getLogger(MokelsBossfightSaphyraMod.class);
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(null, () -> "1", "1"::equals, "1"::equals);
    private static int messageID;
    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> WORK_QUEUE = new ConcurrentLinkedQueue<>();

    public MokelsBossfightSaphyraMod(IEventBus modBus, ModContainer container) {
        NeoForge.EVENT_BUS.register(this);
        container.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, com.hp.saphyrareborn.configuration.StaminaBarConfiguration.SPEC, "stamina bar config.toml");
        SaphyraAttachments.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModSounds.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModBlocks.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModBlockEntities.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModItems.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModEntities.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModTabs.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModMobEffects.REGISTRY.register(modBus);
        MokelsBossfightSaphyraModParticleTypes.REGISTRY.register(modBus);
    }

    public static <T> void addNetworkMessage(Class<T> type, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> handler) {
        PACKET_HANDLER.registerMessage(messageID++, type, encoder, decoder, handler);
    }

    public static void queueServerWork(int ticks, Runnable action) {
        if (ticks <= 0) { action.run(); return; }
        WORK_QUEUE.add(new AbstractMap.SimpleEntry<>(action, ticks));
    }

    @SubscribeEvent
    public void tick(ServerTickEvent.Post event) {
        ArrayList<AbstractMap.SimpleEntry<Runnable, Integer>> ready = new ArrayList<>();
        for (AbstractMap.SimpleEntry<Runnable, Integer> work : WORK_QUEUE) {
            work.setValue(work.getValue() - 1);
            if (work.getValue() <= 0) ready.add(work);
        }
        for (AbstractMap.SimpleEntry<Runnable, Integer> work : ready) work.getKey().run();
        WORK_QUEUE.removeAll(ready);
    }
}


















