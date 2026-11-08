package com.hp.saphyrareborn.procedures;

import javax.annotation.Nullable;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.DisgustProjectileFallEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
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
public class RuleOfHungerRightclickedProcedure {
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != net.minecraft.world.InteractionHand.MAIN_HAND) return;
        if (event.getHand() != event.getEntity().getUsedItemHand()) {
            return;
        }
        RuleOfHungerRightclickedProcedure.execute((Event)event, (LevelAccessor)event.getLevel(), (Entity)event.getEntity());
    }

    public static void execute(LevelAccessor world, Entity entity) {
        RuleOfHungerRightclickedProcedure.execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        ItemStack itemStack;
        if (entity == null) {
            return;
        }
        double baseRate = 0.0;
        double rateWithAmplifier = 0.0;
        Object object = MokelsBossfightSaphyraModItems.RULE_OF_HUNGER.get();
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
                _player.getCooldowns().addCooldown((Item)MokelsBossfightSaphyraModItems.RULE_OF_HUNGER.get(), 120);
            }
            if (world instanceof Level) {
                Level _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:rule_spell")), SoundSource.NEUTRAL, 1.0f, 1.0f);
                } else {
                    _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:rule_spell")), SoundSource.NEUTRAL, 1.0f, 1.0f, false);
                }
            }
            if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.COOLDOWN, 30, 1, false, false));
            }
            if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.STAY_STILL, 30, 1, false, false));
            }
            if (entity.isAlive()) {
                for (int index0 = 0; index0 < 2; ++index0) {
                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                        Entity _shootFrom1 = entity;
                        Level projectileLevel1 = _shootFrom1.level();
                        if (!projectileLevel1.isClientSide()) {
                            Projectile _entityToSpawn = new Object(){

                                public Projectile getArrow(Level level, float damage, int knockback) {
                                    DisgustProjectileFallEntity entityToSpawn = new DisgustProjectileFallEntity((EntityType<? extends DisgustProjectileFallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.DISGUST_PROJECTILE_FALL.get()), level);
                                    entityToSpawn.setBaseDamage(damage);
                                    entityToSpawn.setSilent(true);
                                    return entityToSpawn;
                                }
                            }.getArrow(projectileLevel1, 5.0f, 1);
                            _entityToSpawn.setPos(_shootFrom1.getX(), _shootFrom1.getEyeY() - 0.1, _shootFrom1.getZ());
                            _entityToSpawn.shoot(_shootFrom1.getLookAngle().x, _shootFrom1.getLookAngle().y, _shootFrom1.getLookAngle().z, 1.2f, 45.0f);
                            projectileLevel1.addFreshEntity((Entity)_entityToSpawn);
                        }
                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                            Entity _shootFrom2 = entity;
                            Level projectileLevel2 = _shootFrom2.level();
                            if (!projectileLevel2.isClientSide()) {
                                Projectile _entityToSpawn = new Object(){

                                    public Projectile getArrow(Level level, float damage, int knockback) {
                                        DisgustProjectileFallEntity entityToSpawn = new DisgustProjectileFallEntity((EntityType<? extends DisgustProjectileFallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.DISGUST_PROJECTILE_FALL.get()), level);
                                        entityToSpawn.setBaseDamage(damage);
                                        entityToSpawn.setSilent(true);
                                        return entityToSpawn;
                                    }
                                }.getArrow(projectileLevel2, 5.0f, 1);
                                _entityToSpawn.setPos(_shootFrom2.getX(), _shootFrom2.getEyeY() - 0.1, _shootFrom2.getZ());
                                _entityToSpawn.shoot(_shootFrom2.getLookAngle().x, _shootFrom2.getLookAngle().y, _shootFrom2.getLookAngle().z, 1.2f, 45.0f);
                                projectileLevel2.addFreshEntity((Entity)_entityToSpawn);
                            }
                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                Entity _shootFrom3 = entity;
                                Level projectileLevel3 = _shootFrom3.level();
                                if (!projectileLevel3.isClientSide()) {
                                    Projectile _entityToSpawn = new Object(){

                                        public Projectile getArrow(Level level, float damage, int knockback) {
                                            DisgustProjectileFallEntity entityToSpawn = new DisgustProjectileFallEntity((EntityType<? extends DisgustProjectileFallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.DISGUST_PROJECTILE_FALL.get()), level);
                                            entityToSpawn.setBaseDamage(damage);
                                            entityToSpawn.setSilent(true);
                                            return entityToSpawn;
                                        }
                                    }.getArrow(projectileLevel3, 5.0f, 1);
                                    _entityToSpawn.setPos(_shootFrom3.getX(), _shootFrom3.getEyeY() - 0.1, _shootFrom3.getZ());
                                    _entityToSpawn.shoot(_shootFrom3.getLookAngle().x, _shootFrom3.getLookAngle().y, _shootFrom3.getLookAngle().z, 1.2f, 45.0f);
                                    projectileLevel3.addFreshEntity((Entity)_entityToSpawn);
                                }
                                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                    Entity _shootFrom4 = entity;
                                    Level projectileLevel4 = _shootFrom4.level();
                                    if (!projectileLevel4.isClientSide()) {
                                        Projectile _entityToSpawn = new Object(){

                                            public Projectile getArrow(Level level, float damage, int knockback) {
                                                DisgustProjectileFallEntity entityToSpawn = new DisgustProjectileFallEntity((EntityType<? extends DisgustProjectileFallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.DISGUST_PROJECTILE_FALL.get()), level);
                                                entityToSpawn.setBaseDamage(damage);
                                                entityToSpawn.setSilent(true);
                                                return entityToSpawn;
                                            }
                                        }.getArrow(projectileLevel4, 5.0f, 1);
                                        _entityToSpawn.setPos(_shootFrom4.getX(), _shootFrom4.getEyeY() - 0.1, _shootFrom4.getZ());
                                        _entityToSpawn.shoot(_shootFrom4.getLookAngle().x, _shootFrom4.getLookAngle().y, _shootFrom4.getLookAngle().z, 1.2f, 45.0f);
                                        projectileLevel4.addFreshEntity((Entity)_entityToSpawn);
                                    }
                                    MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                        Entity _shootFrom5 = entity;
                                        Level projectileLevel5 = _shootFrom5.level();
                                        if (!projectileLevel5.isClientSide()) {
                                            Projectile _entityToSpawn = new Object(){

                                                public Projectile getArrow(Level level, float damage, int knockback) {
                                                    DisgustProjectileFallEntity entityToSpawn = new DisgustProjectileFallEntity((EntityType<? extends DisgustProjectileFallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.DISGUST_PROJECTILE_FALL.get()), level);
                                                    entityToSpawn.setBaseDamage(damage);
                                                    entityToSpawn.setSilent(true);
                                                    return entityToSpawn;
                                                }
                                            }.getArrow(projectileLevel5, 5.0f, 1);
                                            _entityToSpawn.setPos(_shootFrom5.getX(), _shootFrom5.getEyeY() - 0.1, _shootFrom5.getZ());
                                            _entityToSpawn.shoot(_shootFrom5.getLookAngle().x, _shootFrom5.getLookAngle().y, _shootFrom5.getLookAngle().z, 1.2f, 45.0f);
                                            projectileLevel5.addFreshEntity((Entity)_entityToSpawn);
                                        }
                                        MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                            Entity _shootFrom6 = entity;
                                            Level projectileLevel6 = _shootFrom6.level();
                                            if (!projectileLevel6.isClientSide()) {
                                                Projectile _entityToSpawn = new Object(){

                                                    public Projectile getArrow(Level level, float damage, int knockback) {
                                                        DisgustProjectileFallEntity entityToSpawn = new DisgustProjectileFallEntity((EntityType<? extends DisgustProjectileFallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.DISGUST_PROJECTILE_FALL.get()), level);
                                                        entityToSpawn.setBaseDamage(damage);
                                                        entityToSpawn.setSilent(true);
                                                        return entityToSpawn;
                                                    }
                                                }.getArrow(projectileLevel6, 5.0f, 1);
                                                _entityToSpawn.setPos(_shootFrom6.getX(), _shootFrom6.getEyeY() - 0.1, _shootFrom6.getZ());
                                                _entityToSpawn.shoot(_shootFrom6.getLookAngle().x, _shootFrom6.getLookAngle().y, _shootFrom6.getLookAngle().z, 1.2f, 45.0f);
                                                projectileLevel6.addFreshEntity((Entity)_entityToSpawn);
                                            }
                                            MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                                Entity _shootFrom7 = entity;
                                                Level projectileLevel7 = _shootFrom7.level();
                                                if (!projectileLevel7.isClientSide()) {
                                                    Projectile _entityToSpawn = new Object(){

                                                        public Projectile getArrow(Level level, float damage, int knockback) {
                                                            DisgustProjectileFallEntity entityToSpawn = new DisgustProjectileFallEntity((EntityType<? extends DisgustProjectileFallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.DISGUST_PROJECTILE_FALL.get()), level);
                                                            entityToSpawn.setBaseDamage(damage);
                                                            entityToSpawn.setSilent(true);
                                                            return entityToSpawn;
                                                        }
                                                    }.getArrow(projectileLevel7, 5.0f, 1);
                                                    _entityToSpawn.setPos(_shootFrom7.getX(), _shootFrom7.getEyeY() - 0.1, _shootFrom7.getZ());
                                                    _entityToSpawn.shoot(_shootFrom7.getLookAngle().x, _shootFrom7.getLookAngle().y, _shootFrom7.getLookAngle().z, 1.2f, 45.0f);
                                                    projectileLevel7.addFreshEntity((Entity)_entityToSpawn);
                                                }
                                                MokelsBossfightSaphyraMod.queueServerWork(2, () -> {
                                                    Entity _shootFrom8 = entity;
                                                    Level projectileLevel8 = _shootFrom8.level();
                                                    if (!projectileLevel8.isClientSide()) {
                                                        Projectile _entityToSpawn = new Object(){

                                                            public Projectile getArrow(Level level, float damage, int knockback) {
                                                                DisgustProjectileFallEntity entityToSpawn = new DisgustProjectileFallEntity((EntityType<? extends DisgustProjectileFallEntity>)((EntityType)MokelsBossfightSaphyraModEntities.DISGUST_PROJECTILE_FALL.get()), level);
                                                                entityToSpawn.setBaseDamage(damage);
                                                                entityToSpawn.setSilent(true);
                                                                return entityToSpawn;
                                                            }
                                                        }.getArrow(projectileLevel8, 5.0f, 1);
                                                        _entityToSpawn.setPos(_shootFrom8.getX(), _shootFrom8.getEyeY() - 0.1, _shootFrom8.getZ());
                                                        _entityToSpawn.shoot(_shootFrom8.getLookAngle().x, _shootFrom8.getLookAngle().y, _shootFrom8.getLookAngle().z, 1.2f, 45.0f);
                                                        projectileLevel8.addFreshEntity((Entity)_entityToSpawn);
                                                    }
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                }
            }
        }
    }
}

