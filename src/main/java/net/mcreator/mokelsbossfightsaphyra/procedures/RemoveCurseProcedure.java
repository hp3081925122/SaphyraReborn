package com.hp.saphyrareborn.procedures;

import javax.annotation.Nullable;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.minecraft.core.registries.BuiltInRegistries;

@EventBusSubscriber
public class RemoveCurseProcedure {
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != net.minecraft.world.InteractionHand.MAIN_HAND) return;
        if (event.getHand() != event.getEntity().getUsedItemHand()) {
            return;
        }
        RemoveCurseProcedure.execute((Event)event, (LevelAccessor)event.getLevel(), (Entity)event.getEntity());
    }

    public static void execute(LevelAccessor world, Entity entity) {
        RemoveCurseProcedure.execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        ItemStack itemStack;
        if (entity == null) {
            return;
        }
        Object object = MokelsBossfightSaphyraModItems.RULE_OF_PURIFICATION.get();
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
                _player.getCooldowns().addCooldown(new ItemStack((net.minecraft.world.level.ItemLike)MokelsBossfightSaphyraModItems.RULE_OF_PURIFICATION.get()), 2400);
            }
            if (world instanceof Level) {
                Level _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("saphyra_reborn:rule_spell")).map(net.minecraft.core.Holder::value).orElseThrow(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                } else {
                    _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("saphyra_reborn:rule_spell")).map(net.minecraft.core.Holder::value).orElseThrow(), SoundSource.NEUTRAL, 1.0f, 1.0f, false);
                }
            }
            if (entity instanceof LivingEntity) {
                _entity = (LivingEntity)entity;
                _entity.removeEffect(MokelsBossfightSaphyraModMobEffects.GRIEF_EFFECT);
            }
            if (entity instanceof LivingEntity) {
                _entity = (LivingEntity)entity;
                _entity.removeEffect(MokelsBossfightSaphyraModMobEffects.ROT_EFFECT);
            }
            if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.COOLDOWN, 30, 1, false, false));
            }
            if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.STAY_STILL, 30, 1, false, false));
            }
        }
    }
}

