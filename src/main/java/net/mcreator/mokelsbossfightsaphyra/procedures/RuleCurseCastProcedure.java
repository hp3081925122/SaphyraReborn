package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Saphyra Reborn addition: effects for the three rules the original registered without any behaviour
 * (Despair, Sobbing, Exudation). Modelled on the curses described in the Augurium books.
 */
public final class RuleCurseCastProcedure {
    public enum Rule { DESPAIR, SOBBING, EXUDATION }

    private static final int ITEM_COOLDOWN = 120;

    private RuleCurseCastProcedure() {
    }

    /** Runs on the server only. Returns true when the rule was cast. */
    public static boolean cast(ServerLevel level, Player player, ItemStack stack, Rule rule) {
        double radius = switch (rule) {
            case DESPAIR -> 10.0;
            case SOBBING -> 8.0;
            case EXUDATION -> 6.0;
        };
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(radius),
                e -> e != player && e.isAlive() && !e.isAlliedTo(player)
                        && (e instanceof Enemy || (e instanceof Mob mob && mob.getTarget() == player)));

        for (LivingEntity target : targets) {
            switch (rule) {
                case DESPAIR -> {
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160, 1), player);
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 160, 2), player);
                    target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 160, 0), player);
                    burst(level, target, ParticleTypes.SQUID_INK, 20);
                }
                case SOBBING -> {
                    target.hurt(level.damageSources().drown(), 6.0f);
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1), player);
                    burst(level, target, ParticleTypes.FALLING_WATER, 40);
                }
                case EXUDATION -> {
                    target.hurt(level.damageSources().indirectMagic(player, player), 6.0f);
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1), player);
                    burst(level, target, ParticleTypes.SNEEZE, 30);
                }
            }
        }

        if (rule == Rule.SOBBING) {
            // "A rule is a last farewell to the ones we love": the caster weeps, but is soothed.
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
            level.sendParticles(ParticleTypes.FALLING_WATER, player.getX(), player.getEyeY(), player.getZ(), 30, 0.3, 0.2, 0.3, 0.0);
        } else if (rule == Rule.EXUDATION) {
            level.sendParticles(ParticleTypes.SNEEZE, player.getX(), player.getY() + 1.0, player.getZ(), 80, radius / 3, 0.5, radius / 3, 0.05);
        } else {
            level.sendParticles(ParticleTypes.SQUID_INK, player.getX(), player.getY() + 1.0, player.getZ(), 60, radius / 3, 0.5, radius / 3, 0.02);
        }

        SoundEvent sound = BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("saphyra_reborn:rule_spell"));
        if (sound != null) {
            level.playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
        player.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.COOLDOWN, 30, 1, false, false));
        player.getCooldowns().addCooldown(stack.getItem(), ITEM_COOLDOWN);
        return true;
    }

    private static void burst(ServerLevel level, LivingEntity target, ParticleOptions particle, int count) {
        level.sendParticles(particle, target.getX(), target.getY() + target.getBbHeight() * 0.6, target.getZ(), count,
                target.getBbWidth() * 0.4, target.getBbHeight() * 0.3, target.getBbWidth() * 0.4, 0.02);
    }
}
