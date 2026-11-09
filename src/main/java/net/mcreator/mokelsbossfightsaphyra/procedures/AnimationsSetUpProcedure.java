/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  dev.kosmx.playerAnim.api.layered.IAnimation
 *  dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer
 *  dev.kosmx.playerAnim.api.layered.ModifierLayer
 *  dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess
 *  dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory
 *  dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.Event
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.fml.common.Mod$EventBusSubscriber$Bus
 *  net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
 *  net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.neoforged.neoforge.network.NetworkEvent$Context
 */
package com.hp.saphyrareborn.procedures;

import net.neoforged.api.distmarker.Dist;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.network.NetworkEvent;

@EventBusSubscriber(modid="saphyra_reborn", value={Dist.CLIENT})
public class AnimationsSetUpProcedure {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
    }

    public static void execute() {
        AnimationsSetUpProcedure.execute(null);
    }

    private static void execute(@Nullable Event event) {
    }

    @EventBusSubscriber
    public static class MokelsBossfightSaphyraModAnimationMessage {
        Component animation;
        int target;
        boolean override;

        public MokelsBossfightSaphyraModAnimationMessage(Component animation, int target, boolean override) {
            this.animation = animation;
            this.target = target;
            this.override = override;
        }

        public MokelsBossfightSaphyraModAnimationMessage(FriendlyByteBuf buffer) {
            this.animation = Component.literal(buffer.readUtf());
            this.target = buffer.readInt();
            this.override = buffer.readBoolean();
        }

        public static void buffer(MokelsBossfightSaphyraModAnimationMessage message, FriendlyByteBuf buffer) {
            buffer.writeUtf(message.animation.getString());
            buffer.writeInt(message.target);
            buffer.writeBoolean(message.override);
        }

        public static void handler(MokelsBossfightSaphyraModAnimationMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
            });
            context.setPacketHandled(true);
        }

        @SubscribeEvent
        public static void registerMessage(FMLCommonSetupEvent event) {
            MokelsBossfightSaphyraMod.addNetworkMessage(MokelsBossfightSaphyraModAnimationMessage.class, MokelsBossfightSaphyraModAnimationMessage::buffer, MokelsBossfightSaphyraModAnimationMessage::new, MokelsBossfightSaphyraModAnimationMessage::handler);
        }
    }
}





