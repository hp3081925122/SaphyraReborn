package com.hp.saphyrareborn.network;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID)
public final class MokelsBossfightSaphyraModVariables {
    public static final Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private static final ResourceLocation PLAYER_VARS_ID = new ResourceLocation(MokelsBossfightSaphyraMod.MODID, "player_variables");
    private static final PlayerVariables FALLBACK = new PlayerVariables();

    private MokelsBossfightSaphyraModVariables() {
    }

    @Mod.EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModBusEvents {
        private ModBusEvents() {
        }

        @SubscribeEvent
        public static void registerCaps(RegisterCapabilitiesEvent event) {
            event.register(PlayerVariables.class);
        }
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            PlayerVariablesProvider provider = new PlayerVariablesProvider();
            event.addCapability(PLAYER_VARS_ID, provider);
            event.addListener(provider::invalidate);
        }
    }

    public static PlayerVariables getPlayerVariables(Entity entity) {
        if (entity == null) {
            return FALLBACK;
        }
        return entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(FALLBACK);
    }

    public static void withPlayerVariables(Entity entity, Consumer<PlayerVariables> consumer) {
        consumer.accept(getPlayerVariables(entity));
    }

    private static final class PlayerVariablesProvider implements ICapabilitySerializable<CompoundTag> {
        private final PlayerVariables instance = new PlayerVariables();
        private final LazyOptional<PlayerVariables> optional = LazyOptional.of(() -> this.instance);

        void invalidate() {
            this.optional.invalidate();
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return cap == PLAYER_VARIABLES_CAPABILITY ? this.optional.cast() : LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.instance.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.instance.deserializeNBT(nbt);
        }
    }

    public static final class PlayerVariables implements INBTSerializable<CompoundTag> {
        public double Rotting;
        public double Grief;
        public double Manic;
        public double Xplayer;
        public double Zplayer;
        public double Devotion;
        public int LinaraQuest;
        public boolean SigilGiven;

        public void syncPlayerVariables(Entity entity) {
            if (entity instanceof ServerPlayer serverPlayer) {
                SaphyraNetwork.sendToPlayer(serverPlayer, new PlayerVariablesSyncPacket(this.writeNBT()));
            }
        }

        public CompoundTag writeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putDouble("Rotting", Rotting);
            nbt.putDouble("Grief", Grief);
            nbt.putDouble("Manic", Manic);
            nbt.putDouble("Xplayer", Xplayer);
            nbt.putDouble("Zplayer", Zplayer);
            nbt.putDouble("Devotion", Devotion);
            nbt.putInt("LinaraQuest", LinaraQuest);
            nbt.putBoolean("SigilGiven", SigilGiven);
            return nbt;
        }

        public void readNBT(Tag tag) {
            if (tag instanceof CompoundTag compound) {
                deserializeNBT(compound);
            }
        }

        @Override
        public CompoundTag serializeNBT() {
            return writeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            Rotting = nbt.getDouble("Rotting");
            Grief = nbt.getDouble("Grief");
            Manic = nbt.getDouble("Manic");
            Xplayer = nbt.getDouble("Xplayer");
            Zplayer = nbt.getDouble("Zplayer");
            Devotion = nbt.getDouble("Devotion");
            LinaraQuest = nbt.getInt("LinaraQuest");
            SigilGiven = nbt.getBoolean("SigilGiven");
        }
    }

    public static final class MapVariables extends SavedData {
        private static final String DATA_NAME = "saphyra_reborn_mapvars";
        private static final MapVariables CLIENT = new MapVariables();

        private static MapVariables load(CompoundTag tag) {
            return new MapVariables();
        }

        @Override
        public CompoundTag save(CompoundTag tag) {
            return tag;
        }

        public void syncData(LevelAccessor world) {
            setDirty();
        }

        public static MapVariables get(LevelAccessor world) {
            if (world instanceof ServerLevel serverLevel) {
                return serverLevel.getDataStorage().computeIfAbsent(MapVariables::load, MapVariables::new, DATA_NAME);
            }
            return CLIENT;
        }
    }

    public static final class WorldVariables extends SavedData {
        private static final String DATA_NAME = "saphyra_reborn_worldvars";
        public double Playerfacing = 1.0;
        public double playerjoinedworld;
        private static final WorldVariables CLIENT = new WorldVariables();

        private static WorldVariables load(CompoundTag tag) {
            WorldVariables data = new WorldVariables();
            data.Playerfacing = tag.getDouble("Playerfacing");
            data.playerjoinedworld = tag.getDouble("playerjoinedworld");
            return data;
        }

        @Override
        public CompoundTag save(CompoundTag tag) {
            tag.putDouble("Playerfacing", Playerfacing);
            tag.putDouble("playerjoinedworld", playerjoinedworld);
            return tag;
        }

        public void syncData(LevelAccessor world) {
            setDirty();
        }

        public static WorldVariables get(LevelAccessor world) {
            if (world instanceof ServerLevel serverLevel) {
                return serverLevel.getDataStorage().computeIfAbsent(WorldVariables::load, WorldVariables::new, DATA_NAME);
            }
            return CLIENT;
        }
    }

    @SubscribeEvent
    public static void clonePlayer(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        try {
            PlayerVariables original = getPlayerVariables(event.getOriginal());
            PlayerVariables clone = getPlayerVariables(event.getEntity());
            clone.deserializeNBT(original.serializeNBT());
        } finally {
            event.getOriginal().invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            getPlayerVariables(event.getEntity()).syncPlayerVariables(event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            getPlayerVariables(event.getEntity()).syncPlayerVariables(event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            getPlayerVariables(event.getEntity()).syncPlayerVariables(event.getEntity());
        }
    }
}
