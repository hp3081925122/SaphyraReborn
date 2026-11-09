package com.hp.saphyrareborn.network;

import com.hp.saphyrareborn.SaphyraAttachments;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Consumer;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
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

    public static final class PlayerVariables implements ValueIOSerializable {
        public double Rotting;
        public double Grief;
        public double Manic;
        public double Xplayer;
        public double Zplayer;
        public double Devotion;
        public int LinaraQuest;
        public boolean SigilGiven;

        public void syncPlayerVariables(Entity entity) {
            if (!entity.level().isClientSide()) entity.syncData(SaphyraAttachments.PLAYER_VARIABLES.get());
        }

        @Override
        public void serialize(ValueOutput output) {
            output.putDouble("Rotting", Rotting);
            output.putDouble("Grief", Grief);
            output.putDouble("Manic", Manic);
            output.putDouble("Xplayer", Xplayer);
            output.putDouble("Zplayer", Zplayer);
            output.putDouble("Devotion", Devotion);
            output.putInt("LinaraQuest", LinaraQuest);
            output.putBoolean("SigilGiven", SigilGiven);
        }

        @Override
        public void deserialize(ValueInput input) {
            Rotting = input.getDoubleOr("Rotting", 0.0);
            Grief = input.getDoubleOr("Grief", 0.0);
            Manic = input.getDoubleOr("Manic", 0.0);
            Xplayer = input.getDoubleOr("Xplayer", 0.0);
            Zplayer = input.getDoubleOr("Zplayer", 0.0);
            Devotion = input.getDoubleOr("Devotion", 0.0);
            LinaraQuest = input.getIntOr("LinaraQuest", 0);
            SigilGiven = input.getBooleanOr("SigilGiven", false);
        }
    }

    public static final class MapVariables extends SavedData {
        private static final MapVariables CLIENT = new MapVariables();
        private static final SavedDataType<MapVariables> TYPE = new SavedDataType<>(
                Identifier.fromNamespaceAndPath("saphyra_reborn", "mapvars"),
                MapVariables::new,
                RecordCodecBuilder.create(instance -> instance.point(new MapVariables())));

        public void syncData(LevelAccessor world) {
            setDirty();
        }

        public static MapVariables get(LevelAccessor world) {
            if (world instanceof ServerLevel serverLevel) return serverLevel.getDataStorage().computeIfAbsent(TYPE);
            return CLIENT;
        }
    }

    public static final class WorldVariables extends SavedData {
        public double Playerfacing = 1.0;
        public double playerjoinedworld;
        private static final WorldVariables CLIENT = new WorldVariables();
        private static final Codec<WorldVariables> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.DOUBLE.optionalFieldOf("Playerfacing", 1.0).forGetter(data -> data.Playerfacing),
                Codec.DOUBLE.optionalFieldOf("playerjoinedworld", 0.0).forGetter(data -> data.playerjoinedworld)
        ).apply(instance, (facing, joined) -> {
            WorldVariables data = new WorldVariables();
            data.Playerfacing = facing;
            data.playerjoinedworld = joined;
            return data;
        }));
        private static final SavedDataType<WorldVariables> TYPE = new SavedDataType<>(
                Identifier.fromNamespaceAndPath("saphyra_reborn", "worldvars"),
                WorldVariables::new,
                CODEC);

        public void syncData(LevelAccessor world) {
            setDirty();
        }

        public static WorldVariables get(LevelAccessor world) {
            if (world instanceof ServerLevel serverLevel) return serverLevel.getDataStorage().computeIfAbsent(TYPE);
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
        clone.LinaraQuest = original.LinaraQuest;
        clone.SigilGiven = original.SigilGiven;
    }
}















