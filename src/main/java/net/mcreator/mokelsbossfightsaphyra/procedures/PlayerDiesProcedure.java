package com.hp.saphyrareborn.procedures;

import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import com.hp.saphyrareborn.entity.MothbossEntity;
import com.hp.saphyrareborn.entity.NamelessEntity;
import com.hp.saphyrareborn.entity.PuppetOfYivaNemorEntity;
import com.hp.saphyrareborn.entity.WitchbossEntity;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class PlayerDiesProcedure {
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            PlayerDiesProcedure.execute((Event)event, (LevelAccessor)event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), (Entity)event.getEntity());
        }
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        PlayerDiesProcedure.execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) {
            return;
        }
        if (entity instanceof ServerPlayer || entity instanceof Player) {
            Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(128.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                ServerLevel _level;
                LivingEntity _entity;
                if (entityiterator instanceof WitchbossEntity) {
                    if (entityiterator instanceof LivingEntity && !(_entity = (LivingEntity)entityiterator).level().isClientSide()) {
                        _entity.addEffect(new MobEffectInstance(MobEffects.HARM, 3, 10000, false, false));
                    }
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        com.hp.saphyrareborn.boss.BossMusic.stop(_level, x, y, z, null);
                    }
                    entityiterator.getPersistentData().putDouble("music", 0.0);
                }
                if (entityiterator instanceof MothbossEntity) {
                    if (entityiterator instanceof LivingEntity && !(_entity = (LivingEntity)entityiterator).level().isClientSide()) {
                        _entity.addEffect(new MobEffectInstance(MobEffects.HEAL, 3, 10000, false, false));
                    }
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        com.hp.saphyrareborn.boss.BossMusic.stop(_level, x, y, z, null);
                    }
                    entityiterator.getPersistentData().putDouble("music", 0.0);
                }
                if (entityiterator instanceof PuppetOfYivaNemorEntity) {
                    if (entityiterator instanceof LivingEntity && !(_entity = (LivingEntity)entityiterator).level().isClientSide()) {
                        _entity.addEffect(new MobEffectInstance(MobEffects.HEAL, 3, 10000, false, false));
                    }
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        com.hp.saphyrareborn.boss.BossMusic.stop(_level, x, y, z, null);
                    }
                    entityiterator.getPersistentData().putDouble("music", 0.0);
                }
                if (!(entityiterator instanceof NamelessEntity)) continue;
                if (entityiterator instanceof LivingEntity && !(_entity = (LivingEntity)entityiterator).level().isClientSide()) {
                    _entity.addEffect(new MobEffectInstance(MobEffects.HARM, 3, 10000, false, false));
                }
                if (world instanceof ServerLevel) {
                    _level = (ServerLevel)world;
                    com.hp.saphyrareborn.boss.BossMusic.stop(_level, x, y, z, null);
                }
                entityiterator.getPersistentData().putDouble("music", 0.0);
            }
        }
    }
}

