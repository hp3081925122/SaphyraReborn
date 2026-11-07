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
            _center1enter = new Vec3(x, y, z);
           _entfound1ity> _entfound1 = world.getEntitiesOfClass(Entity.class, new AABB(_center1, _center1).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center1))).toList();
            for (Entity entityiterator : _entfound1) {
                LivingEntity _livEnt4;
                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt4 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                _center2enter = new Vec3(x, y, z);
           _entfound2<Entity> _entfound2 = world.getEntitiesOfClass(Entity.class, new AABB(_center2, _center2).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center2))).toList();
                for (Entity entityiterator : _entfound2) {
                    LivingEntity _livEnt10;
                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt10 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
               _center213 _center21 = new Vec3(x, y, z);
        _entfound21  List<Entity> _entfound21 = world.getEntitiesOfClass(Entity.class, new AABB(_center21, _center21).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center21))).toList();
                    for (Entity entityiterator : _entfound21) {
                        LivingEntity _livEnt16;
                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt16 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                   _center223 _center22 = new Vec3(x, y, z);
       _entfound22       List<Entity> _entfound22 = world.getEntitiesOfClass(Entity.class, new AABB(_center22, _center22).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center22))).toList();
                        for (Entity entityiterator : _entfound22) {
                            LivingEntity _livEnt22;
                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt22 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                            MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                       _center233 _center23 = new Vec3(x, y, z);
      _entfound23            List<Entity> _entfound23 = world.getEntitiesOfClass(Entity.class, new AABB(_center23, _center23).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center23))).toList();
                            for (Entity entityiterator : _entfound23) {
                                LivingEntity _livEnt28;
                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt28 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                           _center243 _center24 = new Vec3(x, y, z);
     _entfound24                 List<Entity> _entfound24 = world.getEntitiesOfClass(Entity.class, new AABB(_center24, _center24).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center24))).toList();
                                for (Entity entityiterator : _entfound24) {
                                    LivingEntity _livEnt34;
                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt34 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                               _center253 _center25 = new Vec3(x, y, z);
    _entfound25                      List<Entity> _entfound25 = world.getEntitiesOfClass(Entity.class, new AABB(_center25, _center25).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center25))).toList();
                                    for (Entity entityiterator : _entfound25) {
                                        LivingEntity _livEnt40;
                                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt40 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                                   _center263 _center26 = new Vec3(x, y, z);
   _entfound26                           List<Entity> _entfound26 = world.getEntitiesOfClass(Entity.class, new AABB(_center26, _center26).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center26))).toList();
                                        for (Entity entityiterator : _entfound26) {
                                            LivingEntity _livEnt46;
                                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt46 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                            MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                                       _center273 _center27 = new Vec3(x, y, z);
  _entfound27                                List<Entity> _entfound27 = world.getEntitiesOfClass(Entity.class, new AABB(_center27, _center27).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center27))).toList();
                                            for (Entity entityiterator : _entfound27) {
                                                LivingEntity _livEnt52;
                                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt52 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                                           _center283 _center28 = new Vec3(x, y, z);
 _entfound28                                     List<Entity> _entfound28 = world.getEntitiesOfClass(Entity.class, new AABB(_center28, _center28).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center28))).toList();
                                                for (Entity entityiterator : _entfound28) {
                                                    LivingEntity _livEnt58;
                                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt58 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
        _entfound1113enter = new Vec3(x, y, z);
            List<Entity> _entfound111 = world.getEntitiesOfClass(Entity.class, new AABB(_center3, _center3).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center3))).toList();
            for (Entity entityiterator : _entfound111) {
                LivingEntity _livEnt74;
                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt74 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
            _entfound1124enter = new Vec3(x, y, z);
                List<Entity> _entfound112 = world.getEntitiesOfClass(Entity.class, new AABB(_center4, _center4).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center4))).toList();
                for (Entity entityiterator : _entfound112) {
                    LivingEntity _livEnt80;
                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt80 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                _entfound1135enter = new Vec3(x, y, z);
                    List<Entity> _entfound113 = world.getEntitiesOfClass(Entity.class, new AABB(_center5, _center5).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center5))).toList();
                    for (Entity entityiterator : _entfound113) {
                        LivingEntity _livEnt86;
                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt86 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
       _center141     _entfound114nter141 = new Vec3(x, y, z);
                        List<Entity> _entfound114 = world.getEntitiesOfClass(Entity.class, new AABB(_center141, _center141).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center141))).toList();
                        for (Entity entityiterator : _entfound114) {
                            LivingEntity _livEnt92;
                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt92 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                            MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
           _center142    _entfound115enter142 = new Vec3(x, y, z);
                            List<Entity> _entfound115 = world.getEntitiesOfClass(Entity.class, new AABB(_center142, _center142).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center142))).toList();
                            for (Entity entityiterator : _entfound115) {
                                LivingEntity _livEnt98;
                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt98 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
               _center143   _entfound116center143 = new Vec3(x, y, z);
                                List<Entity> _entfound116 = world.getEntitiesOfClass(Entity.class, new AABB(_center143, _center143).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center143))).toList();
                                for (Entity entityiterator : _entfound116) {
                                    LivingEntity _livEnt104;
                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt104 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                   _center144  _entfound117_center144 = new Vec3(x, y, z);
                                    List<Entity> _entfound117 = world.getEntitiesOfClass(Entity.class, new AABB(_center144, _center144).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center144))).toList();
                                    for (Entity entityiterator : _entfound117) {
                                        LivingEntity _livEnt110;
                                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt110 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                       _center145 _entfound118 _center145 = new Vec3(x, y, z);
                                        List<Entity> _entfound118 = world.getEntitiesOfClass(Entity.class, new AABB(_center145, _center145).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center145))).toList();
                                        for (Entity entityiterator : _entfound118) {
                                            LivingEntity _livEnt116;
                                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt116 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                            MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                           _center146_entfound1193 _center146 = new Vec3(x, y, z);
                                            List<Entity> _entfound119 = world.getEntitiesOfClass(Entity.class, new AABB(_center146, _center146).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center146))).toList();
                                            for (Entity entityiterator : _entfound119) {
                                                LivingEntity _livEnt122;
                                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt122 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                               _center14_entfound1110c3 _center147 = new Vec3(x, y, z);
                                                List<Entity> _entfound1110 = world.getEntitiesOfClass(Entity.class, new AABB(_center147, _center147).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center147))).toList();
                                                for (Entity entityiterator : _entfound1110) {
                                                    LivingEntity _livEnt128;
                                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt128 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0._entfound3.5, 0.01);
            }
            _center6enter = new Vec3(x, y, z);
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center6, _center6).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center6))).toList();
            for (Entity entityiterator : _entfound) {
                LivingEntity _livEnt144;
                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt144 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, _entfound4);
                }
                _center7enter = new Vec3(x, y, z);
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center7, _center7).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center7))).toList();
                for (Entity entityiterator : _entfound) {
                    LivingEntity _livEnt150;
                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt150 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                        _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.0_entfound5               }
                    _center8enter = new Vec3(x, y, z);
                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center8, _center8).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center8))).toList();
                    for (Entity entityiterator : _entfound) {
                        LivingEntity _livEnt156;
                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt156 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
    _entfound6           }
                        _center9enter = new Vec3(x, y, z);
                        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center9, _center9).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center9))).toList();
                        for (Entity entityiterator : _entfound) {
                            LivingEntity _livEnt162;
                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt162 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                            MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
            _entfound7       }
                            _center10enter = new Vec3(x, y, z);
                            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center10, _center10).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center10))).toList();
                            for (Entity entityiterator : _entfound) {
                                LivingEntity _livEnt168;
                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt168 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                _entfound8       }
                                _center11enter = new Vec3(x, y, z);
                                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center11, _center11).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center11))).toList();
                                for (Entity entityiterator : _entfound) {
                                    LivingEntity _livEnt174;
                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt174 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                    _entfound9       }
                                    _center12enter = new Vec3(x, y, z);
                                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center12, _center12).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center12))).toList();
                                    for (Entity entityiterator : _entfound) {
                                        LivingEntity _livEnt180;
                                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt180 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                        _entfound10       }
                                        _center13enter = new Vec3(x, y, z);
                                        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center13, _center13).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center13))).toList();
                                        for (Entity entityiterator : _entfound) {
                                            LivingEntity _livEnt186;
                                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt186 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                            MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                            _entfound11       }
                                            _center14enter = new Vec3(x, y, z);
                                            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center14, _center14).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center14))).toList();
                                            for (Entity entityiterator : _entfound) {
                                                LivingEntity _livEnt192;
                                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt192 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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
                                                _center15enter = new Vec3(x, y, z);
                                                List<Entity> _entfound30 = world.getEntitiesOfClass(Entity.class, new AABB(_center15, _center15).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center15))).toList();
                                                for (Entity entityiterator : _entfound30) {
                                                    LivingEntity _livEnt198;
                                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt198 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
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




















