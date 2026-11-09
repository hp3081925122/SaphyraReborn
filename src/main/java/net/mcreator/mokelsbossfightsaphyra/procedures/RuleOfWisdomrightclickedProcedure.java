package com.hp.saphyrareborn.procedures;

import java.util.List;
import javax.annotation.Nullable;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.procedures.AnimationsSetUpProcedure;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.network.NetworkDirection;

@EventBusSubscriber
public class RuleOfWisdomrightclickedProcedure {
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != net.minecraft.world.InteractionHand.MAIN_HAND) return;
        if (event.getHand() != event.getEntity().getUsedItemHand()) {
            return;
        }
        RuleOfWisdomrightclickedProcedure.execute((Event)event, (LevelAccessor)event.getLevel(), (Entity)event.getEntity());
    }

    public static void execute(LevelAccessor world, Entity entity) {
        RuleOfWisdomrightclickedProcedure.execute(null, world, entity);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        ItemStack itemStack;
        if (entity == null) {
            return;
        }
        double baseRate = 0.0;
        double rateWithAmplifier = 0.0;
        Object object = MokelsBossfightSaphyraModItems.RULE_OF_HATERED.get();
        if (entity instanceof LivingEntity) {
            LivingEntity _livEnt = (LivingEntity)entity;
            itemStack = _livEnt.getMainHandItem();
        } else {
            itemStack = ItemStack.EMPTY;
        }
        if (object == itemStack.getItem()) {
            LivingEntity _entity;
            if (entity instanceof Player) {
                Player _player = (Player)entity;
                _player.getCooldowns().addCooldown(new ItemStack((net.minecraft.world.level.ItemLike)MokelsBossfightSaphyraModItems.RULE_OF_HATERED.get()), 2400);
            }
            if (!world.isClientSide() && entity instanceof Player && world instanceof ServerLevel) {
                List<Connection> connections;
                ServerLevel srvLvl_ = (ServerLevel)world;
                List list = connections = srvLvl_.getServer().getConnection().getConnections();
                synchronized (list) {
                    for (Connection connection : connections) {
                        if (connection.isConnecting() || !connection.isConnected()) continue;
                        MokelsBossfightSaphyraMod.PACKET_HANDLER.sendTo((Object)new AnimationsSetUpProcedure.MokelsBossfightSaphyraModAnimationMessage((Component)Component.literal((String)"wisdom"), entity.getId(), true), connection, NetworkDirection.PLAY_TO_CLIENT);
                    }
                }
            }
            if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.COOLDOWN, 80, 1, false, false));
            }
            if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.STAY_STILL, 80, 1, false, false));
            }
            MokelsBossfightSaphyraMod.queueServerWork(52, () -> {
                LivingEntity _entityL;
                if (entity instanceof LivingEntity && !(_entityL = (LivingEntity)entity).level().isClientSide()) {
                    _entityL.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 200, 1, true, false));
                }
                if (entity instanceof LivingEntity && !(_entityL = (LivingEntity)entity).level().isClientSide()) {
                    _entityL.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 200, 1, true, false));
                }
                if (entity instanceof LivingEntity && !(_entityL = (LivingEntity)entity).level().isClientSide()) {
                    _entityL.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1, true, false));
                }
                if (entity instanceof LivingEntity && !(_entityL = (LivingEntity)entity).level().isClientSide()) {
                    _entityL.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 1, true, false));
                }
                if (world instanceof ServerLevel) {
                    ServerLevel _level = (ServerLevel)world;
                    _level.sendParticles((ParticleOptions)ParticleTypes.WITCH, entity.getX(), entity.getY(), entity.getZ(), 200, 0.2, 0.1, 0.2, 0.1);
                }
            });
        }
    }
}

