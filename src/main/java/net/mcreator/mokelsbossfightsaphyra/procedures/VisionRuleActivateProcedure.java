package com.hp.saphyrareborn.procedures;

import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.minecraft.core.registries.BuiltInRegistries;

@EventBusSubscriber
public class VisionRuleActivateProcedure {
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != net.minecraft.world.InteractionHand.MAIN_HAND) return;
        if (event.getHand() != event.getEntity().getUsedItemHand()) {
            return;
        }
        VisionRuleActivateProcedure.execute((Event)event, (LevelAccessor)event.getLevel(), (Entity)event.getEntity());
    }

    public static void execute(LevelAccessor world, Entity entity) {
        VisionRuleActivateProcedure.execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        ItemStack itemStack;
        if (entity == null) {
            return;
        }
        Object object = MokelsBossfightSaphyraModItems.RULE_OF_VISION.get();
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
                _player.getCooldowns().addCooldown(new ItemStack((net.minecraft.world.level.ItemLike)MokelsBossfightSaphyraModItems.RULE_OF_VISION.get()), 2400);
            }
            Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                LivingEntity _entity2;
                if (entityiterator instanceof Player || entityiterator instanceof ServerPlayer || !(entityiterator instanceof LivingEntity) || !(entityiterator instanceof LivingEntity) || (_entity2 = (LivingEntity)entityiterator).level().isClientSide()) continue;
                _entity2.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 1, false, false));
            }
            if (world instanceof Level) {
                Level _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("saphyra_reborn:rule_spell")).map(net.minecraft.core.Holder::value).orElseThrow(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                } else {
                    _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("saphyra_reborn:rule_spell")).map(net.minecraft.core.Holder::value).orElseThrow(), SoundSource.NEUTRAL, 1.0f, 1.0f, false);
                }
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

