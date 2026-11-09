package com.hp.saphyrareborn.procedures;

import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import com.hp.saphyrareborn.entity.WitchBossProjectileEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
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
public class WitchStaffShootProcedure {
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != net.minecraft.world.InteractionHand.MAIN_HAND) return;
        if (event.getHand() != event.getEntity().getUsedItemHand()) {
            return;
        }
        WitchStaffShootProcedure.execute((Event)event, (LevelAccessor)event.getLevel(), (Entity)event.getEntity());
    }

    public static void execute(LevelAccessor world, Entity entity) {
        WitchStaffShootProcedure.execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        block25: {
            block24: {
                LivingEntity _entity;
                ItemStack itemStack;
                if (entity == null) {
                    return;
                }
                if (!entity.isShiftKeyDown()) break block24;
                if (entity instanceof LivingEntity) {
                    LivingEntity _livEnt = (LivingEntity)entity;
                    itemStack = _livEnt.getMainHandItem();
                } else {
                    itemStack = ItemStack.EMPTY;
                }
                if (itemStack.getItem() != MokelsBossfightSaphyraModItems.WITCH_STAFF.get()) break block25;
                if (world instanceof Level) {
                    Level _level = (Level)world;
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:spellcast")), SoundSource.NEUTRAL, 1.0f, 0.6f);
                    } else {
                        _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:spellcast")), SoundSource.NEUTRAL, 1.0f, 0.6f, false);
                    }
                }
                if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                    _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.STAY_STILL, 20, 1));
                }
                if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                    _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.COOLDOWN, 20, 1));
                }
                if (entity instanceof LivingEntity) {
                    _entity = (LivingEntity)entity;
                    _entity.swing(InteractionHand.MAIN_HAND, true);
                }
                if (entity instanceof Player) {
                    Player _player = (Player)entity;
                    _player.getCooldowns().addCooldown((Item)MokelsBossfightSaphyraModItems.WITCH_STAFF.get(), 200);
                }
                if (world instanceof ServerLevel _level) {
                    _level.sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get()), entity.getX(), entity.getY(), entity.getZ(), 40, 3.0, 0.5, 3.0, 0.05);
                    if (entity instanceof Player _caster) {
                        com.hp.saphyrareborn.weapon.WeaponAbilities.staffNova(_level, _caster);
                    }
                }
                Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6.5), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    if (!(entityiterator instanceof LivingEntity) || entityiterator instanceof Player || entityiterator instanceof TamableAnimal) continue;
                    entityiterator.hurt(new DamageSource((Holder)world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), entity), 20.0f);
                }
                break block25;
            }
            if (!entity.isShiftKeyDown()) {
                ItemStack itemStack;
                if (entity instanceof LivingEntity) {
                    LivingEntity _livEnt = (LivingEntity)entity;
                    itemStack = _livEnt.getMainHandItem();
                } else {
                    itemStack = ItemStack.EMPTY;
                }
                if (itemStack.getItem() == MokelsBossfightSaphyraModItems.WITCH_STAFF.get()) {
                    LivingEntity _entity;
                    if (world instanceof Level) {
                        Level _level = (Level)world;
                        if (!_level.isClientSide()) {
                            _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:spellcast")), SoundSource.NEUTRAL, 0.75f, 1.0f);
                        } else {
                            _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:spellcast")), SoundSource.NEUTRAL, 0.75f, 1.0f, false);
                        }
                    }
                    if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                        _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.STAY_STILL, 20, 1));
                    }
                    if (entity instanceof LivingEntity && !(_entity = (LivingEntity)entity).level().isClientSide()) {
                        _entity.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.COOLDOWN, 20, 1));
                    }
                    if (entity instanceof LivingEntity) {
                        _entity = (LivingEntity)entity;
                        _entity.swing(InteractionHand.MAIN_HAND, true);
                    }
                    if (entity instanceof Player) {
                        Player _player = (Player)entity;
                        _player.getCooldowns().addCooldown((Item)MokelsBossfightSaphyraModItems.WITCH_STAFF.get(), 60);
                    }
                    if (world instanceof ServerLevel _castLevel && entity instanceof Player _caster) {
                        com.hp.saphyrareborn.weapon.WeaponAbilities.staffCast(_castLevel, _caster);
                    }
                    for (int index1 = 0; index1 < 4; ++index1) {
                        Entity _shootFrom = entity;
                        Level projectileLevel = _shootFrom.level();
                        if (projectileLevel.isClientSide()) continue;
                        Projectile _entityToSpawn = new Object(){

                            public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                                WitchBossProjectileEntity entityToSpawn = new WitchBossProjectileEntity((EntityType<? extends WitchBossProjectileEntity>)((EntityType)MokelsBossfightSaphyraModEntities.WITCH_BOSS_PROJECTILE.get()), level);
                                entityToSpawn.setOwner(shooter);
                                entityToSpawn.setBaseDamage(damage);
                                entityToSpawn.setSilent(true);
                                return entityToSpawn;
                            }
                        }.getArrow(projectileLevel, entity, 16.0f, 0);
                        _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                        _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 0.6f, 10.0f);
                        projectileLevel.addFreshEntity((Entity)_entityToSpawn);
                    }
                }
            }
        }
    }
}

