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
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.Event
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.fml.common.Mod$EventBusSubscriber$Bus
 *  net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
 *  net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.neoforged.network.NetworkEvent$Context
 */
package com.hp.saphyrareborn.procedures;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.network.NetworkEvent;

@EventBusSubscriber(modid="saphyra_reborn", bus=EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class AnimationsSetUpProcedure {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "player_animation"), 1000, AnimationsSetUpProcedure::registerPlayerAnimations);
    }

    private static IAnimation registerPlayerAnimations(AbstractClientPlayer player) {
        return new ModifierLayer();
    }

    public static void execute() {
        AnimationsSetUpProcedure.execute(null);
    }

    private static void execute(@Nullable Event event) {
    }

    @EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD)
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
                AbstractClientPlayer player_;
                ModifierLayer animation;
                Player player;
                Level level = Minecraft.getInstance().player.level();
                if (level.getEntity(message.target) != null && (player = (Player)level.getEntity(message.target)) instanceof AbstractClientPlayer && (animation = (ModifierLayer)PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer)(player_ = (AbstractClientPlayer)player)).get(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "player_animation"))) != null && (message.override || !animation.isActive())) {
                    animation.setAnimation((IAnimation)new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", message.animation.getString()))));
                }
            });
            context.setPacketHandled(true);
        }

        @SubscribeEvent
        public static void registerMessage(FMLCommonSetupEvent event) {
            MokelsBossfightSaphyraMod.addNetworkMessage(MokelsBossfightSaphyraModAnimationMessage.class, MokelsBossfightSaphyraModAnimationMessage::buffer, MokelsBossfightSaphyraModAnimationMessage::new, MokelsBossfightSaphyraModAnimationMessage::handler);
        }
    }
}






















