/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.hp.saphyrareborn.procedures;

import java.util.Comparator;
import java.util.List;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.NamelessEntity;
import com.hp.saphyrareborn.entity.NamelessProjectileFallEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class NamelessInvisibleProjectileProjectileHitsLivingEntityProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
            ServerLevel _level;
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
            }
            Vec3 _center = new Vec3(x, y, z);
            List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                LivingEntity _livEnt4;
                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt4 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                    capability.Rotting = _setval;
                    capability.syncPlayerVariables(entityiterator);
                });
            }
            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                ServerLevel _level;
                if (world instanceof ServerLevel) {
                    _level = (ServerLevel)world;
                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                }
                if (world instanceof ServerLevel) {
                    _level = (ServerLevel)world;
                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                }
                Vec3 _center = new Vec3(x, y, z);
                List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    LivingEntity _livEnt10;
                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt10 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                    double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                    entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                        capability.Rotting = _setval;
                        capability.syncPlayerVariables(entityiterator);
                    });
                }
                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                    ServerLevel _level;
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                    }
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                    }
                    Vec3 _center = new Vec3(x, y, z);
                    List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                    for (Entity entityiterator : _entfound) {
                        LivingEntity _livEnt16;
                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt16 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                        double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                        entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                            capability.Rotting = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                        ServerLevel _level;
                        if (world instanceof ServerLevel) {
                            _level = (ServerLevel)world;
                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                        }
                        if (world instanceof ServerLevel) {
                            _level = (ServerLevel)world;
                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                        }
                        Vec3 _center = new Vec3(x, y, z);
                        List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                        for (Entity entityiterator : _entfound) {
                            LivingEntity _livEnt22;
                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt22 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                            double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                            entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.Rotting = _setval;
                                capability.syncPlayerVariables(entityiterator);
                            });
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                            ServerLevel _level;
                            if (world instanceof ServerLevel) {
                                _level = (ServerLevel)world;
                                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                            }
                            if (world instanceof ServerLevel) {
                                _level = (ServerLevel)world;
                                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                            }
                            Vec3 _center = new Vec3(x, y, z);
                            List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                            for (Entity entityiterator : _entfound) {
                                LivingEntity _livEnt28;
                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt28 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                    capability.Rotting = _setval;
                                    capability.syncPlayerVariables(entityiterator);
                                });
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                ServerLevel _level;
                                if (world instanceof ServerLevel) {
                                    _level = (ServerLevel)world;
                                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                }
                                if (world instanceof ServerLevel) {
                                    _level = (ServerLevel)world;
                                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                }
                                Vec3 _center = new Vec3(x, y, z);
                                List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                for (Entity entityiterator : _entfound) {
                                    LivingEntity _livEnt34;
                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt34 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                    double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                    entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                        capability.Rotting = _setval;
                                        capability.syncPlayerVariables(entityiterator);
                                    });
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                    ServerLevel _level;
                                    if (world instanceof ServerLevel) {
                                        _level = (ServerLevel)world;
                                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                    }
                                    if (world instanceof ServerLevel) {
                                        _level = (ServerLevel)world;
                                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                    }
                                    Vec3 _center = new Vec3(x, y, z);
                                    List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                    for (Entity entityiterator : _entfound) {
                                        LivingEntity _livEnt40;
                                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt40 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                        double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                        entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                            capability.Rotting = _setval;
                                            capability.syncPlayerVariables(entityiterator);
                                        });
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                        ServerLevel _level;
                                        if (world instanceof ServerLevel) {
                                            _level = (ServerLevel)world;
                                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                        }
                                        if (world instanceof ServerLevel) {
                                            _level = (ServerLevel)world;
                                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                        }
                                        Vec3 _center = new Vec3(x, y, z);
                                        List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                        for (Entity entityiterator : _entfound) {
                                            LivingEntity _livEnt46;
                                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt46 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                            double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                            entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                                capability.Rotting = _setval;
                                                capability.syncPlayerVariables(entityiterator);
                                            });
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                            ServerLevel _level;
                                            if (world instanceof ServerLevel) {
                                                _level = (ServerLevel)world;
                                                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                            }
                                            if (world instanceof ServerLevel) {
                                                _level = (ServerLevel)world;
                                                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                            }
                                            Vec3 _center = new Vec3(x, y, z);
                                            List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                            for (Entity entityiterator : _entfound) {
                                                LivingEntity _livEnt52;
                                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt52 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                                double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                                entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                                    capability.Rotting = _setval;
                                                    capability.syncPlayerVariables(entityiterator);
                                                });
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                                ServerLevel _level;
                                                if (world instanceof ServerLevel) {
                                                    _level = (ServerLevel)world;
                                                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                                }
                                                if (world instanceof ServerLevel) {
                                                    _level = (ServerLevel)world;
                                                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                                }
                                                Vec3 _center = new Vec3(x, y, z);
                                                List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                                for (Entity entityiterator : _entfound) {
                                                    LivingEntity _livEnt58;
                                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt58 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                                    double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                                    entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                                        capability.Rotting = _setval;
                                                        capability.syncPlayerVariables(entityiterator);
                                                    });
                                                }
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
        MokelsBossfightSaphyraMod.queueServerWork(20, () -> {
            ServerLevel _level;
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
            }
            Vec3 _center = new Vec3(x, y, z);
            List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                LivingEntity _livEnt74;
                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt74 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                    capability.Rotting = _setval;
                    capability.syncPlayerVariables(entityiterator);
                });
            }
            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                ServerLevel _level;
                if (world instanceof ServerLevel) {
                    _level = (ServerLevel)world;
                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                }
                if (world instanceof ServerLevel) {
                    _level = (ServerLevel)world;
                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                }
                Vec3 _center = new Vec3(x, y, z);
                List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    LivingEntity _livEnt80;
                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt80 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                    double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                    entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                        capability.Rotting = _setval;
                        capability.syncPlayerVariables(entityiterator);
                    });
                }
                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                    ServerLevel _level;
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                    }
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                    }
                    Vec3 _center = new Vec3(x, y, z);
                    List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                    for (Entity entityiterator : _entfound) {
                        LivingEntity _livEnt86;
                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt86 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                        double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                        entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                            capability.Rotting = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                        ServerLevel _level;
                        if (world instanceof ServerLevel) {
                            _level = (ServerLevel)world;
                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                        }
                        if (world instanceof ServerLevel) {
                            _level = (ServerLevel)world;
                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                        }
                        Vec3 _center = new Vec3(x, y, z);
                        List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                        for (Entity entityiterator : _entfound) {
                            LivingEntity _livEnt92;
                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt92 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                            double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                            entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.Rotting = _setval;
                                capability.syncPlayerVariables(entityiterator);
                            });
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                            ServerLevel _level;
                            if (world instanceof ServerLevel) {
                                _level = (ServerLevel)world;
                                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                            }
                            if (world instanceof ServerLevel) {
                                _level = (ServerLevel)world;
                                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                            }
                            Vec3 _center = new Vec3(x, y, z);
                            List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                            for (Entity entityiterator : _entfound) {
                                LivingEntity _livEnt98;
                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt98 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                    capability.Rotting = _setval;
                                    capability.syncPlayerVariables(entityiterator);
                                });
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                ServerLevel _level;
                                if (world instanceof ServerLevel) {
                                    _level = (ServerLevel)world;
                                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                }
                                if (world instanceof ServerLevel) {
                                    _level = (ServerLevel)world;
                                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                }
                                Vec3 _center = new Vec3(x, y, z);
                                List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                for (Entity entityiterator : _entfound) {
                                    LivingEntity _livEnt104;
                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt104 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                    double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                    entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                        capability.Rotting = _setval;
                                        capability.syncPlayerVariables(entityiterator);
                                    });
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                    ServerLevel _level;
                                    if (world instanceof ServerLevel) {
                                        _level = (ServerLevel)world;
                                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                    }
                                    if (world instanceof ServerLevel) {
                                        _level = (ServerLevel)world;
                                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                    }
                                    Vec3 _center = new Vec3(x, y, z);
                                    List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                    for (Entity entityiterator : _entfound) {
                                        LivingEntity _livEnt110;
                                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt110 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                        double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                        entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                            capability.Rotting = _setval;
                                            capability.syncPlayerVariables(entityiterator);
                                        });
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                        ServerLevel _level;
                                        if (world instanceof ServerLevel) {
                                            _level = (ServerLevel)world;
                                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                        }
                                        if (world instanceof ServerLevel) {
                                            _level = (ServerLevel)world;
                                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                        }
                                        Vec3 _center = new Vec3(x, y, z);
                                        List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                        for (Entity entityiterator : _entfound) {
                                            LivingEntity _livEnt116;
                                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt116 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                            double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                            entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                                capability.Rotting = _setval;
                                                capability.syncPlayerVariables(entityiterator);
                                            });
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                            ServerLevel _level;
                                            if (world instanceof ServerLevel) {
                                                _level = (ServerLevel)world;
                                                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                            }
                                            if (world instanceof ServerLevel) {
                                                _level = (ServerLevel)world;
                                                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                            }
                                            Vec3 _center = new Vec3(x, y, z);
                                            List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                            for (Entity entityiterator : _entfound) {
                                                LivingEntity _livEnt122;
                                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt122 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                                double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                                entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                                    capability.Rotting = _setval;
                                                    capability.syncPlayerVariables(entityiterator);
                                                });
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                                ServerLevel _level;
                                                if (world instanceof ServerLevel) {
                                                    _level = (ServerLevel)world;
                                                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                                }
                                                if (world instanceof ServerLevel) {
                                                    _level = (ServerLevel)world;
                                                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                                }
                                                Vec3 _center = new Vec3(x, y, z);
                                                List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                                for (Entity entityiterator : _entfound) {
                                                    LivingEntity _livEnt128;
                                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt128 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                                    double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                                    entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                                        capability.Rotting = _setval;
                                                        capability.syncPlayerVariables(entityiterator);
                                                    });
                                                }
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
        MokelsBossfightSaphyraMod.queueServerWork(40, () -> {
            ServerLevel _level;
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
            }
            Vec3 _center = new Vec3(x, y, z);
            List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                LivingEntity _livEnt144;
                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt144 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                    capability.Rotting = _setval;
                    capability.syncPlayerVariables(entityiterator);
                });
            }
            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                ServerLevel _level;
                if (world instanceof ServerLevel) {
                    _level = (ServerLevel)world;
                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                }
                if (world instanceof ServerLevel) {
                    _level = (ServerLevel)world;
                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                }
                Vec3 _center = new Vec3(x, y, z);
                List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    LivingEntity _livEnt150;
                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt150 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                    double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                    entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                        capability.Rotting = _setval;
                        capability.syncPlayerVariables(entityiterator);
                    });
                }
                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                    ServerLevel _level;
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                    }
                    if (world instanceof ServerLevel) {
                        _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                    }
                    Vec3 _center = new Vec3(x, y, z);
                    List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                    for (Entity entityiterator : _entfound) {
                        LivingEntity _livEnt156;
                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt156 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                        double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                        entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                            capability.Rotting = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                        ServerLevel _level;
                        if (world instanceof ServerLevel) {
                            _level = (ServerLevel)world;
                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                        }
                        if (world instanceof ServerLevel) {
                            _level = (ServerLevel)world;
                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                        }
                        Vec3 _center = new Vec3(x, y, z);
                        List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                        for (Entity entityiterator : _entfound) {
                            LivingEntity _livEnt162;
                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt162 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                            double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                            entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.Rotting = _setval;
                                capability.syncPlayerVariables(entityiterator);
                            });
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                            ServerLevel _level;
                            if (world instanceof ServerLevel) {
                                _level = (ServerLevel)world;
                                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                            }
                            if (world instanceof ServerLevel) {
                                _level = (ServerLevel)world;
                                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                            }
                            Vec3 _center = new Vec3(x, y, z);
                            List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                            for (Entity entityiterator : _entfound) {
                                LivingEntity _livEnt168;
                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt168 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                    capability.Rotting = _setval;
                                    capability.syncPlayerVariables(entityiterator);
                                });
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                ServerLevel _level;
                                if (world instanceof ServerLevel) {
                                    _level = (ServerLevel)world;
                                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                }
                                if (world instanceof ServerLevel) {
                                    _level = (ServerLevel)world;
                                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                }
                                Vec3 _center = new Vec3(x, y, z);
                                List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                for (Entity entityiterator : _entfound) {
                                    LivingEntity _livEnt174;
                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt174 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                    double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                    entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                        capability.Rotting = _setval;
                                        capability.syncPlayerVariables(entityiterator);
                                    });
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                    ServerLevel _level;
                                    if (world instanceof ServerLevel) {
                                        _level = (ServerLevel)world;
                                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                    }
                                    if (world instanceof ServerLevel) {
                                        _level = (ServerLevel)world;
                                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                    }
                                    Vec3 _center = new Vec3(x, y, z);
                                    List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                    for (Entity entityiterator : _entfound) {
                                        LivingEntity _livEnt180;
                                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt180 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                        double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                        entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                            capability.Rotting = _setval;
                                            capability.syncPlayerVariables(entityiterator);
                                        });
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                        ServerLevel _level;
                                        if (world instanceof ServerLevel) {
                                            _level = (ServerLevel)world;
                                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                        }
                                        if (world instanceof ServerLevel) {
                                            _level = (ServerLevel)world;
                                            _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                        }
                                        Vec3 _center = new Vec3(x, y, z);
                                        List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                        for (Entity entityiterator : _entfound) {
                                            LivingEntity _livEnt186;
                                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt186 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                            double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                            entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                                capability.Rotting = _setval;
                                                capability.syncPlayerVariables(entityiterator);
                                            });
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                            ServerLevel _level;
                                            if (world instanceof ServerLevel) {
                                                _level = (ServerLevel)world;
                                                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                            }
                                            if (world instanceof ServerLevel) {
                                                _level = (ServerLevel)world;
                                                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                            }
                                            Vec3 _center = new Vec3(x, y, z);
                                            List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                            for (Entity entityiterator : _entfound) {
                                                LivingEntity _livEnt192;
                                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt192 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                                double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                                entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                                    capability.Rotting = _setval;
                                                    capability.syncPlayerVariables(entityiterator);
                                                });
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                                ServerLevel _level;
                                                if (world instanceof ServerLevel) {
                                                    _level = (ServerLevel)world;
                                                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                                }
                                                if (world instanceof ServerLevel) {
                                                    _level = (ServerLevel)world;
                                                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                                }
                                                Vec3 _center = new Vec3(x, y, z);
                                                List _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                                                for (Entity entityiterator : _entfound) {
                                                    LivingEntity _livEnt198;
                                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt198 = (LivingEntity)entityiterator).hasEffect((MobEffect)MokelsBossfightSaphyraModMobEffects.IMMUNE.get())) continue;
                                                    double _setval = ((MokelsBossfightSaphyraModVariables.PlayerVariables)entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse((Object)new MokelsBossfightSaphyraModVariables.PlayerVariables())).Rotting + 1.0;
                                                    entityiterator.getCapability(MokelsBossfightSaphyraModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                                        capability.Rotting = _setval;
                                                        capability.syncPlayerVariables(entityiterator);
                                                    });
                                                }
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
        MokelsBossfightSaphyraMod.queueServerWork(60, () -> {
            for (int index0 = 0; index0 < 3; ++index0) {
                if (!(world instanceof ServerLevel)) continue;
                ServerLevel projectileLevel = (ServerLevel)world;
                Projectile _entityToSpawn = new Object(){

                    public Projectile getArrow(Level level, float damage, int knockback) {
                        NamelessProjectileFallEntity entityToSpawn = new NamelessProjectileFallEntity((EntityType<? extends NamelessProjectileFallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.NAMELESS_PROJECTILE_FALL.get()), level);
                        entityToSpawn.setBaseDamage(damage);
                        entityToSpawn.setKnockback(knockback);
                        entityToSpawn.setSilent(true);
                        return entityToSpawn;
                    }
                }.getArrow((Level)projectileLevel, 0.0f, 1);
                _entityToSpawn.setPos(x, y + 1.0, z);
                _entityToSpawn.shoot(0.0, 90.0, 0.0, 0.7f, 15.0f);
                projectileLevel.addFreshEntity((Entity)_entityToSpawn);
            }
        });
    }
}

