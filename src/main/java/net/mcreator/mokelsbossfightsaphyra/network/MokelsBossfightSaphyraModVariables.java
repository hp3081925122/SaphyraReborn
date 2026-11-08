package com.hp.saphyrareborn.network;

import com.hp.saphyrareborn.SaphyraAttachments;
import java.util.function.Consumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public final class MokelsBossfightSaphyraModVariables {
    private MokelsBossfightSaphyraModVariables() {
    }

    public static PlayerVariables getPlayerVariables(Entity entity) {
        return entity.getData(SaphyraAttachments.PLAYER_VARIABLES.get());
    }

    public static void withPlayerVariables(Entity entity, Consumer<PlayerVariables> consumer) {
        consumer.accept(getPlayerVariables(entity));
    }

    public static final class PlayerVariables implements INBTSerializable<CompoundTag> {
        public double Rotting;
        public double Grief;
        public double Manic;
        public double Xplayer;
        public double Zplayer;
        public double Devotion;

        public void syncPlayerVariables(Entity entity) {
            if (!entity.level().isClientSide()) entity.syncData(SaphyraAttachments.PLAYER_VARIABLES.get());
        }

        public CompoundTag writeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putDouble("Rotting", Rotting);
            nbt.putDouble("Grief", Grief);
            nbt.putDouble("Manic", Manic);
            nbt.putDouble("Xplayer", Xplayer);
            nbt.putDouble("Zplayer", Zplayer);
            nbt.putDouble("Devotion", Devotion);
            return nbt;
        }

        public void readNBT(Tag tag) {
            deserializeNBT(null, (CompoundTag) tag);
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.Provider provider) {
            return writeNBT();
        }

        @Override
        public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
            Rotting = nbt.getDouble("Rotting");
            Grief = nbt.getDouble("Grief");
            Manic = nbt.getDouble("Manic");
            Xplayer = nbt.getDouble("Xplayer");
            Zplayer = nbt.getDouble("Zplayer");
            Devotion = nbt.getDouble("Devotion");
        }
    }

    public static final class MapVariables extends SavedData {
        private static final String DATA_NAME = "saphyra_reborn_mapvars";
        private static final MapVariables CLIENT = new MapVariables();

        private static MapVariables load(CompoundTag tag, HolderLookup.Provider provider) {
            MapVariables data = new MapVariables();
            return data;
        }

        @Override
        public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
            return tag;
        }

        public void syncData(LevelAccessor world) {
            setDirty();
        }

        public static MapVariables get(LevelAccessor world) {
            if (world instanceof ServerLevel serverLevel) return serverLevel.getDataStorage().computeIfAbsent(new net.minecraft.world.level.saveddata.SavedData.Factory<>(MapVariables::new, MapVariables::load), DATA_NAME);
            return CLIENT;
        }
    }

    public static final class WorldVariables extends SavedData {
        private static final String DATA_NAME = "saphyra_reborn_worldvars";
        public double Playerfacing = 1.0;
        public double playerjoinedworld;
        private static final WorldVariables CLIENT = new WorldVariables();

        private static WorldVariables load(CompoundTag tag, HolderLookup.Provider provider) {
            WorldVariables data = new WorldVariables();
            data.Playerfacing = tag.getDouble("Playerfacing");
            data.playerjoinedworld = tag.getDouble("playerjoinedworld");
            return data;
        }

        @Override
        public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
            tag.putDouble("Playerfacing", Playerfacing);
            tag.putDouble("playerjoinedworld", playerjoinedworld);
            return tag;
        }

        public void syncData(LevelAccessor world) {
            setDirty();
        }

        public static WorldVariables get(LevelAccessor world) {
            if (world instanceof ServerLevel serverLevel) return serverLevel.getDataStorage().computeIfAbsent(new net.minecraft.world.level.saveddata.SavedData.Factory<>(WorldVariables::new, WorldVariables::load), DATA_NAME);
            return CLIENT;
        }
    }

    @SubscribeEvent
    public static void clonePlayer(PlayerEvent.Clone event) {
        PlayerVariables original = getPlayerVariables(event.getOriginal());
        PlayerVariables clone = getPlayerVariables(event.getEntity());
        clone.Rotting = original.Rotting;
        clone.Grief = original.Grief;
        clone.Manic = original.Manic;
        clone.Xplayer = original.Xplayer;
        clone.Zplayer = original.Zplayer;
        clone.Devotion = original.Devotion;
    }
}















