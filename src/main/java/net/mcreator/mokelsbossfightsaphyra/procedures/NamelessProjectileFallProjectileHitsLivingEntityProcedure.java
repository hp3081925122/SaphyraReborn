package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.boss.BossAttributes;
import com.hp.saphyrareborn.boss.nameless.NamelessCombat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.LevelAccessor;

public class NamelessProjectileFallProjectileHitsLivingEntityProcedure {
    private static final ResourceKey<DamageType> ROT = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath("saphyra_reborn", "rot"));
    private static final double ROT_DAMAGE = 4.0;

    public static void execute(LevelAccessor world, Entity entity, Entity immediatesourceentity) {
        if (entity == null || immediatesourceentity == null || world.isClientSide()) {
            return;
        }
        immediatesourceentity.discard();
        if (!(entity instanceof LivingEntity living) || !NamelessCombat.canBeHit(living)) {
            return;
        }
        if (living instanceof Player player) {
            NamelessCombat.addRotting(player, 40.0);
        }
        Entity owner = immediatesourceentity instanceof Projectile projectile ? projectile.getOwner() : null;
        float damage = owner instanceof LivingEntity ? BossAttributes.skillDamage(owner, ROT_DAMAGE / BossAttributes.SKILL_BASE_DAMAGE) : (float) ROT_DAMAGE;
        living.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).get(ROT).orElseThrow()), damage);
    }
}
