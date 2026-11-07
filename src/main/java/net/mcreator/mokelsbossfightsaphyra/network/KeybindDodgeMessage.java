/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.fml.common.Mod$EventBusSubscriber$Bus
 *  net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.neoforged.network.NetworkEvent$Context
 */
package com.hp.saphyrareborn.network;

import java.util.function.Supplier;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.procedures.DashProcedure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.network.NetworkEvent;

@EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD)
public class KeybindDodgeMessage {
    int type;
    int pressedms;

    public KeybindDodgeMessage(int type, int pressedms) {
        this.type = type;
        this.pressedms = pressedms;
    }

    public KeybindDodgeMessage(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
        this.pressedms = buffer.readInt();
    }

    public static void buffer(KeybindDodgeMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedms);
    }

    public static void handler(KeybindDodgeMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> KeybindDodgeMessage.pressAction((Player)context.getSender(), message.type, message.pressedms));
        context.setPacketHandled(true);
    }

    public static void pressAction(Player entity, int type, int pressedms) {
        Level world = entity.level();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        if (!world.hasChunkAt(entity.blockPosition())) {
            return;
        }
        if (type == 0) {
            DashProcedure.execute((LevelAccessor)world, (Entity)entity);
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        MokelsBossfightSaphyraMod.addNetworkMessage(KeybindDodgeMessage.class, KeybindDodgeMessage::buffer, KeybindDodgeMessage::new, KeybindDodgeMessage::handler);
    }
}















