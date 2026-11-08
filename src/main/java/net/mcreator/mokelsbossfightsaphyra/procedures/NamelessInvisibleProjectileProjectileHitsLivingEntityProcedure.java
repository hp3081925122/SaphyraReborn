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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class NamelessInvisibleProjectileProjectileHitsLivingEntityProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
            ServerLevel _level1;
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                ((ServerLevel)_level1).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                ((ServerLevel)_level1).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
            }
            Vec3 _center1 = new Vec3(x, y, z);
            List<Entity> _entfound1 = world.getEntitiesOfClass(Entity.class, new AABB(_center1, _center1).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center1))).toList();
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
                ServerLevel _level2;
                if (world instanceof ServerLevel) {
                    _level2 = (ServerLevel)world;
                    ((ServerLevel)_level2).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                }
                if (world instanceof ServerLevel) {
                    _level2 = (ServerLevel)world;
                    ((ServerLevel)_level2).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                }
                Vec3 _center2 = new Vec3(x, y, z);
                List<Entity> _entfound2 = world.getEntitiesOfClass(Entity.class, new AABB(_center2, _center2).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center2))).toList();
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
                    ServerLevel _level3;
                    if (world instanceof ServerLevel) {
                        _level3 = (ServerLevel)world;
                        ((ServerLevel)_level3).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                    }
                    if (world instanceof ServerLevel) {
                        _level3 = (ServerLevel)world;
                        ((ServerLevel)_level3).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                    }
                    Vec3 _center3 = new Vec3(x, y, z);
                    List<Entity> _entfound3 = world.getEntitiesOfClass(Entity.class, new AABB(_center3, _center3).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center3))).toList();
                    for (Entity entityiterator : _entfound3) {
                        LivingEntity _livEnt16;
                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt16 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                            capability.Rotting = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                        ServerLevel _level4;
                        if (world instanceof ServerLevel) {
                            _level4 = (ServerLevel)world;
                            ((ServerLevel)_level4).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                        }
                        if (world instanceof ServerLevel) {
                            _level4 = (ServerLevel)world;
                            ((ServerLevel)_level4).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                        }
                        Vec3 _center4 = new Vec3(x, y, z);
                        List<Entity> _entfound4 = world.getEntitiesOfClass(Entity.class, new AABB(_center4, _center4).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center4))).toList();
                        for (Entity entityiterator : _entfound4) {
                            LivingEntity _livEnt22;
                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt22 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                            MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                capability.Rotting = _setval;
                                capability.syncPlayerVariables(entityiterator);
                            });
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                            ServerLevel _level5;
                            if (world instanceof ServerLevel) {
                                _level5 = (ServerLevel)world;
                                ((ServerLevel)_level5).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                            }
                            if (world instanceof ServerLevel) {
                                _level5 = (ServerLevel)world;
                                ((ServerLevel)_level5).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                            }
                            Vec3 _center5 = new Vec3(x, y, z);
                            List<Entity> _entfound5 = world.getEntitiesOfClass(Entity.class, new AABB(_center5, _center5).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center5))).toList();
                            for (Entity entityiterator : _entfound5) {
                                LivingEntity _livEnt28;
                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt28 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                    capability.Rotting = _setval;
                                    capability.syncPlayerVariables(entityiterator);
                                });
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                ServerLevel _level6;
                                if (world instanceof ServerLevel) {
                                    _level6 = (ServerLevel)world;
                                    ((ServerLevel)_level6).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                }
                                if (world instanceof ServerLevel) {
                                    _level6 = (ServerLevel)world;
                                    ((ServerLevel)_level6).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                }
                                Vec3 _center6 = new Vec3(x, y, z);
                                List<Entity> _entfound6 = world.getEntitiesOfClass(Entity.class, new AABB(_center6, _center6).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center6))).toList();
                                for (Entity entityiterator : _entfound6) {
                                    LivingEntity _livEnt34;
                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt34 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                        capability.Rotting = _setval;
                                        capability.syncPlayerVariables(entityiterator);
                                    });
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                    ServerLevel _level7;
                                    if (world instanceof ServerLevel) {
                                        _level7 = (ServerLevel)world;
                                        ((ServerLevel)_level7).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                    }
                                    if (world instanceof ServerLevel) {
                                        _level7 = (ServerLevel)world;
                                        ((ServerLevel)_level7).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                    }
                                    Vec3 _center7 = new Vec3(x, y, z);
                                    List<Entity> _entfound7 = world.getEntitiesOfClass(Entity.class, new AABB(_center7, _center7).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center7))).toList();
                                    for (Entity entityiterator : _entfound7) {
                                        LivingEntity _livEnt40;
                                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt40 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                            capability.Rotting = _setval;
                                            capability.syncPlayerVariables(entityiterator);
                                        });
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                        ServerLevel _level8;
                                        if (world instanceof ServerLevel) {
                                            _level8 = (ServerLevel)world;
                                            ((ServerLevel)_level8).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                        }
                                        if (world instanceof ServerLevel) {
                                            _level8 = (ServerLevel)world;
                                            ((ServerLevel)_level8).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                        }
                                        Vec3 _center8 = new Vec3(x, y, z);
                                        List<Entity> _entfound8 = world.getEntitiesOfClass(Entity.class, new AABB(_center8, _center8).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center8))).toList();
                                        for (Entity entityiterator : _entfound8) {
                                            LivingEntity _livEnt46;
                                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt46 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                            MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                                capability.Rotting = _setval;
                                                capability.syncPlayerVariables(entityiterator);
                                            });
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                            ServerLevel _level9;
                                            if (world instanceof ServerLevel) {
                                                _level9 = (ServerLevel)world;
                                                ((ServerLevel)_level9).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                            }
                                            if (world instanceof ServerLevel) {
                                                _level9 = (ServerLevel)world;
                                                ((ServerLevel)_level9).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                            }
                                            Vec3 _center9 = new Vec3(x, y, z);
                                            List<Entity> _entfound9 = world.getEntitiesOfClass(Entity.class, new AABB(_center9, _center9).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center9))).toList();
                                            for (Entity entityiterator : _entfound9) {
                                                LivingEntity _livEnt52;
                                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt52 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                                    capability.Rotting = _setval;
                                                    capability.syncPlayerVariables(entityiterator);
                                                });
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                                ServerLevel _level10;
                                                if (world instanceof ServerLevel) {
                                                    _level10 = (ServerLevel)world;
                                                    ((ServerLevel)_level10).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                                }
                                                if (world instanceof ServerLevel) {
                                                    _level10 = (ServerLevel)world;
                                                    ((ServerLevel)_level10).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                                }
                                                Vec3 _center10 = new Vec3(x, y, z);
                                                List<Entity> _entfound10 = world.getEntitiesOfClass(Entity.class, new AABB(_center10, _center10).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center10))).toList();
                                                for (Entity entityiterator : _entfound10) {
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
            ServerLevel _level11;
            if (world instanceof ServerLevel) {
                _level11 = (ServerLevel)world;
                ((ServerLevel)_level11).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
            }
            if (world instanceof ServerLevel) {
                _level11 = (ServerLevel)world;
                ((ServerLevel)_level11).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
            }
            Vec3 _center11 = new Vec3(x, y, z);
            List<Entity> _entfound11 = world.getEntitiesOfClass(Entity.class, new AABB(_center11, _center11).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center11))).toList();
            for (Entity entityiterator : _entfound11) {
                LivingEntity _livEnt74;
                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt74 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                    capability.Rotting = _setval;
                    capability.syncPlayerVariables(entityiterator);
                });
            }
            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                ServerLevel _level12;
                if (world instanceof ServerLevel) {
                    _level12 = (ServerLevel)world;
                    ((ServerLevel)_level12).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                }
                if (world instanceof ServerLevel) {
                    _level12 = (ServerLevel)world;
                    ((ServerLevel)_level12).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                }
                Vec3 _center12 = new Vec3(x, y, z);
                List<Entity> _entfound12 = world.getEntitiesOfClass(Entity.class, new AABB(_center12, _center12).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center12))).toList();
                for (Entity entityiterator : _entfound12) {
                    LivingEntity _livEnt80;
                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt80 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                        capability.Rotting = _setval;
                        capability.syncPlayerVariables(entityiterator);
                    });
                }
                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                    ServerLevel _level13;
                    if (world instanceof ServerLevel) {
                        _level13 = (ServerLevel)world;
                        ((ServerLevel)_level13).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                    }
                    if (world instanceof ServerLevel) {
                        _level13 = (ServerLevel)world;
                        ((ServerLevel)_level13).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                    }
                    Vec3 _center13 = new Vec3(x, y, z);
                    List<Entity> _entfound13 = world.getEntitiesOfClass(Entity.class, new AABB(_center13, _center13).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center13))).toList();
                    for (Entity entityiterator : _entfound13) {
                        LivingEntity _livEnt86;
                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt86 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                            capability.Rotting = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                        ServerLevel _level14;
                        if (world instanceof ServerLevel) {
                            _level14 = (ServerLevel)world;
                            ((ServerLevel)_level14).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                        }
                        if (world instanceof ServerLevel) {
                            _level14 = (ServerLevel)world;
                            ((ServerLevel)_level14).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                        }
                        Vec3 _center14 = new Vec3(x, y, z);
                        List<Entity> _entfound14 = world.getEntitiesOfClass(Entity.class, new AABB(_center14, _center14).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center14))).toList();
                        for (Entity entityiterator : _entfound14) {
                            LivingEntity _livEnt92;
                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt92 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                            MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                capability.Rotting = _setval;
                                capability.syncPlayerVariables(entityiterator);
                            });
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                            ServerLevel _level15;
                            if (world instanceof ServerLevel) {
                                _level15 = (ServerLevel)world;
                                ((ServerLevel)_level15).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                            }
                            if (world instanceof ServerLevel) {
                                _level15 = (ServerLevel)world;
                                ((ServerLevel)_level15).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                            }
                            Vec3 _center15 = new Vec3(x, y, z);
                            List<Entity> _entfound15 = world.getEntitiesOfClass(Entity.class, new AABB(_center15, _center15).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center15))).toList();
                            for (Entity entityiterator : _entfound15) {
                                LivingEntity _livEnt98;
                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt98 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                    capability.Rotting = _setval;
                                    capability.syncPlayerVariables(entityiterator);
                                });
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                ServerLevel _level16;
                                if (world instanceof ServerLevel) {
                                    _level16 = (ServerLevel)world;
                                    ((ServerLevel)_level16).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                }
                                if (world instanceof ServerLevel) {
                                    _level16 = (ServerLevel)world;
                                    ((ServerLevel)_level16).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                }
                                Vec3 _center16 = new Vec3(x, y, z);
                                List<Entity> _entfound16 = world.getEntitiesOfClass(Entity.class, new AABB(_center16, _center16).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center16))).toList();
                                for (Entity entityiterator : _entfound16) {
                                    LivingEntity _livEnt104;
                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt104 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                        capability.Rotting = _setval;
                                        capability.syncPlayerVariables(entityiterator);
                                    });
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                    ServerLevel _level17;
                                    if (world instanceof ServerLevel) {
                                        _level17 = (ServerLevel)world;
                                        ((ServerLevel)_level17).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                    }
                                    if (world instanceof ServerLevel) {
                                        _level17 = (ServerLevel)world;
                                        ((ServerLevel)_level17).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                    }
                                    Vec3 _center17 = new Vec3(x, y, z);
                                    List<Entity> _entfound17 = world.getEntitiesOfClass(Entity.class, new AABB(_center17, _center17).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center17))).toList();
                                    for (Entity entityiterator : _entfound17) {
                                        LivingEntity _livEnt110;
                                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt110 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                            capability.Rotting = _setval;
                                            capability.syncPlayerVariables(entityiterator);
                                        });
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                        ServerLevel _level18;
                                        if (world instanceof ServerLevel) {
                                            _level18 = (ServerLevel)world;
                                            ((ServerLevel)_level18).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                        }
                                        if (world instanceof ServerLevel) {
                                            _level18 = (ServerLevel)world;
                                            ((ServerLevel)_level18).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                        }
                                        Vec3 _center18 = new Vec3(x, y, z);
                                        List<Entity> _entfound18 = world.getEntitiesOfClass(Entity.class, new AABB(_center18, _center18).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center18))).toList();
                                        for (Entity entityiterator : _entfound18) {
                                            LivingEntity _livEnt116;
                                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt116 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                            MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                                capability.Rotting = _setval;
                                                capability.syncPlayerVariables(entityiterator);
                                            });
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                            ServerLevel _level19;
                                            if (world instanceof ServerLevel) {
                                                _level19 = (ServerLevel)world;
                                                ((ServerLevel)_level19).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                            }
                                            if (world instanceof ServerLevel) {
                                                _level19 = (ServerLevel)world;
                                                ((ServerLevel)_level19).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                            }
                                            Vec3 _center19 = new Vec3(x, y, z);
                                            List<Entity> _entfound19 = world.getEntitiesOfClass(Entity.class, new AABB(_center19, _center19).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center19))).toList();
                                            for (Entity entityiterator : _entfound19) {
                                                LivingEntity _livEnt122;
                                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt122 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                                    capability.Rotting = _setval;
                                                    capability.syncPlayerVariables(entityiterator);
                                                });
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                                ServerLevel _level20;
                                                if (world instanceof ServerLevel) {
                                                    _level20 = (ServerLevel)world;
                                                    ((ServerLevel)_level20).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                                }
                                                if (world instanceof ServerLevel) {
                                                    _level20 = (ServerLevel)world;
                                                    ((ServerLevel)_level20).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                                }
                                                Vec3 _center20 = new Vec3(x, y, z);
                                                List<Entity> _entfound20 = world.getEntitiesOfClass(Entity.class, new AABB(_center20, _center20).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center20))).toList();
                                                for (Entity entityiterator : _entfound20) {
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
            ServerLevel _level21;
            if (world instanceof ServerLevel) {
                _level21 = (ServerLevel)world;
                ((ServerLevel)_level21).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
            }
            if (world instanceof ServerLevel) {
                _level21 = (ServerLevel)world;
                ((ServerLevel)_level21).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
            }
            Vec3 _center21 = new Vec3(x, y, z);
            List<Entity> _entfound21 = world.getEntitiesOfClass(Entity.class, new AABB(_center21, _center21).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center21))).toList();
            for (Entity entityiterator : _entfound21) {
                LivingEntity _livEnt144;
                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt144 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                    capability.Rotting = _setval;
                    capability.syncPlayerVariables(entityiterator);
                });
            }
            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                ServerLevel _level22;
                if (world instanceof ServerLevel) {
                    _level22 = (ServerLevel)world;
                    ((ServerLevel)_level22).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                }
                if (world instanceof ServerLevel) {
                    _level22 = (ServerLevel)world;
                    ((ServerLevel)_level22).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                }
                Vec3 _center22 = new Vec3(x, y, z);
                List<Entity> _entfound22 = world.getEntitiesOfClass(Entity.class, new AABB(_center22, _center22).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center22))).toList();
                for (Entity entityiterator : _entfound22) {
                    LivingEntity _livEnt150;
                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt150 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                        capability.Rotting = _setval;
                        capability.syncPlayerVariables(entityiterator);
                    });
                }
                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                    ServerLevel _level23;
                    if (world instanceof ServerLevel) {
                        _level23 = (ServerLevel)world;
                        ((ServerLevel)_level23).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                    }
                    if (world instanceof ServerLevel) {
                        _level23 = (ServerLevel)world;
                        ((ServerLevel)_level23).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                    }
                    Vec3 _center23 = new Vec3(x, y, z);
                    List<Entity> _entfound23 = world.getEntitiesOfClass(Entity.class, new AABB(_center23, _center23).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center23))).toList();
                    for (Entity entityiterator : _entfound23) {
                        LivingEntity _livEnt156;
                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt156 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                            capability.Rotting = _setval;
                            capability.syncPlayerVariables(entityiterator);
                        });
                    }
                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                        ServerLevel _level24;
                        if (world instanceof ServerLevel) {
                            _level24 = (ServerLevel)world;
                            ((ServerLevel)_level24).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                        }
                        if (world instanceof ServerLevel) {
                            _level24 = (ServerLevel)world;
                            ((ServerLevel)_level24).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                        }
                        Vec3 _center24 = new Vec3(x, y, z);
                        List<Entity> _entfound24 = world.getEntitiesOfClass(Entity.class, new AABB(_center24, _center24).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center24))).toList();
                        for (Entity entityiterator : _entfound24) {
                            LivingEntity _livEnt162;
                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt162 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                            MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                capability.Rotting = _setval;
                                capability.syncPlayerVariables(entityiterator);
                            });
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                            ServerLevel _level25;
                            if (world instanceof ServerLevel) {
                                _level25 = (ServerLevel)world;
                                ((ServerLevel)_level25).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                            }
                            if (world instanceof ServerLevel) {
                                _level25 = (ServerLevel)world;
                                ((ServerLevel)_level25).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                            }
                            Vec3 _center25 = new Vec3(x, y, z);
                            List<Entity> _entfound25 = world.getEntitiesOfClass(Entity.class, new AABB(_center25, _center25).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center25))).toList();
                            for (Entity entityiterator : _entfound25) {
                                LivingEntity _livEnt168;
                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt168 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                    capability.Rotting = _setval;
                                    capability.syncPlayerVariables(entityiterator);
                                });
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                ServerLevel _level26;
                                if (world instanceof ServerLevel) {
                                    _level26 = (ServerLevel)world;
                                    ((ServerLevel)_level26).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                }
                                if (world instanceof ServerLevel) {
                                    _level26 = (ServerLevel)world;
                                    ((ServerLevel)_level26).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                }
                                Vec3 _center26 = new Vec3(x, y, z);
                                List<Entity> _entfound26 = world.getEntitiesOfClass(Entity.class, new AABB(_center26, _center26).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center26))).toList();
                                for (Entity entityiterator : _entfound26) {
                                    LivingEntity _livEnt174;
                                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt174 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                    double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                    MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                        capability.Rotting = _setval;
                                        capability.syncPlayerVariables(entityiterator);
                                    });
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                    ServerLevel _level27;
                                    if (world instanceof ServerLevel) {
                                        _level27 = (ServerLevel)world;
                                        ((ServerLevel)_level27).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                    }
                                    if (world instanceof ServerLevel) {
                                        _level27 = (ServerLevel)world;
                                        ((ServerLevel)_level27).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                    }
                                    Vec3 _center27 = new Vec3(x, y, z);
                                    List<Entity> _entfound27 = world.getEntitiesOfClass(Entity.class, new AABB(_center27, _center27).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center27))).toList();
                                    for (Entity entityiterator : _entfound27) {
                                        LivingEntity _livEnt180;
                                        if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt180 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                        double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                        MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                            capability.Rotting = _setval;
                                            capability.syncPlayerVariables(entityiterator);
                                        });
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                        ServerLevel _level28;
                                        if (world instanceof ServerLevel) {
                                            _level28 = (ServerLevel)world;
                                            ((ServerLevel)_level28).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                        }
                                        if (world instanceof ServerLevel) {
                                            _level28 = (ServerLevel)world;
                                            ((ServerLevel)_level28).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                        }
                                        Vec3 _center28 = new Vec3(x, y, z);
                                        List<Entity> _entfound28 = world.getEntitiesOfClass(Entity.class, new AABB(_center28, _center28).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center28))).toList();
                                        for (Entity entityiterator : _entfound28) {
                                            LivingEntity _livEnt186;
                                            if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt186 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                            double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                            MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                                capability.Rotting = _setval;
                                                capability.syncPlayerVariables(entityiterator);
                                            });
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                            ServerLevel _level29;
                                            if (world instanceof ServerLevel) {
                                                _level29 = (ServerLevel)world;
                                                ((ServerLevel)_level29).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                            }
                                            if (world instanceof ServerLevel) {
                                                _level29 = (ServerLevel)world;
                                                ((ServerLevel)_level29).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                            }
                                            Vec3 _center29 = new Vec3(x, y, z);
                                            List<Entity> _entfound29 = world.getEntitiesOfClass(Entity.class, new AABB(_center29, _center29).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center29))).toList();
                                            for (Entity entityiterator : _entfound29) {
                                                LivingEntity _livEnt192;
                                                if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof NamelessEntity || entityiterator instanceof LivingEntity && (_livEnt192 = (LivingEntity)entityiterator).hasEffect(MokelsBossfightSaphyraModMobEffects.IMMUNE)) continue;
                                                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entityiterator).Rotting + 1.0;
                                                MokelsBossfightSaphyraModVariables.withPlayerVariables(entityiterator, capability -> {
                                                    capability.Rotting = _setval;
                                                    capability.syncPlayerVariables(entityiterator);
                                                });
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                                ServerLevel _level30;
                                                if (world instanceof ServerLevel) {
                                                    _level30 = (ServerLevel)world;
                                                    ((ServerLevel)_level30).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get()), x, y, z, 20, 0.5, 0.5, 0.5, 0.01);
                                                }
                                                if (world instanceof ServerLevel) {
                                                    _level30 = (ServerLevel)world;
                                                    ((ServerLevel)_level30).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get()), x, y, z, 15, 0.5, 0.5, 0.5, 0.01);
                                                }
                                                Vec3 _center30 = new Vec3(x, y, z);
                                                List<Entity> _entfound30 = world.getEntitiesOfClass(Entity.class, new AABB(_center30, _center30).inflate(3.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center30))).toList();
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



