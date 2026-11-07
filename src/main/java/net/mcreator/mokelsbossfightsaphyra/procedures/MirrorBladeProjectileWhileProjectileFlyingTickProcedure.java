/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Holder
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import java.util.Comparator;
import java.util.List;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MirrorBladeProjectileWhileProjectileFlyingTickProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
        if (immediatesourceentity == null) {
            return;
        }
        immediatesourceentity.setNoGravity(true);
        if (world instanceof ServerLevel) {
            ServerLevel _level = (ServerLevel)world;
            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.GOLDEN_PARTICLE.get()), x, y, z, 3, 0.1, 0.1, 0.1, 0.05);
        }
        MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
            if (immediatesourceentity.isAlive()) {
                Vec3 _center = new Vec3(x, y, z);
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    if (!immediatesourceentity.level().isClientSide()) {
                        immediatesourceentity.discard();
                    }
                    if (!(entityiterator instanceof Player) && entityiterator instanceof LivingEntity) {
                        entityiterator.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_PROJECTILE), immediatesourceentity), 10.0f);
                    }
                    for (int index0 = 0; index0 < 2; ++index0) {
                        if (!(world instanceof ServerLevel)) continue;
                        ServerLevel _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.GOLDEN_PARTICLE.get()), immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ(), 100, 1.5, 1.5, 1.5, 0.05);
                    }
                }
            }
        });
    }
}



















