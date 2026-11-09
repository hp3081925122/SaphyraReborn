package com.hp.saphyrareborn.weapon;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.entity.MirrorBladeProjectileEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID)
public final class WeaponAbilities {
    private static final int IAI_COOLDOWN = 160;
    private static final double IAI_LENGTH = 6.0;
    private static final double IAI_HALF_WIDTH = 1.2;
    private static final int MIRROR_COOLDOWN = 80;
    private static final int RUNE_COOLDOWN = 240;
    private static final int RUNE_DELAY = 15;
    private static final double RUNE_RANGE = 16.0;
    private static final double RUNE_RADIUS = 4.0;
    private static final int SLAM_COOLDOWN = 120;
    private static final double SLAM_RADIUS = 3.5;
    private static final int COMBO_WINDOW = 40;
    private static final int MAX_TASKS = 64;

    private static final Map<UUID, long[]> COMBOS = new HashMap<>();
    private static final List<RuneTask> TASKS = new ArrayList<>();

    private WeaponAbilities() {
    }

    private record RuneTask(ResourceKey<Level> dimension, UUID owner, Vec3 pos, float damage, int fireTick) {
    }

    public static float attack(Player player) {
        return (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    public static boolean canHit(Player player, LivingEntity e) {
        return e != player && e.isAlive() && !(e instanceof Player) && !(e instanceof TamableAnimal) && !(e instanceof ArmorStand) && !e.isSpectator();
    }

    private static void sound(ServerLevel level, Vec3 pos, String id, float volume, float pitch) {
        SoundEvent event = BuiltInRegistries.SOUND_EVENT.get(Identifier.parse(id)).map(net.minecraft.core.Holder::value).orElseThrow();
        if (event != null) {
            level.playSound(null, pos.x, pos.y, pos.z, event, SoundSource.PLAYERS, volume, pitch);
        }
    }

    private static Vec3 flatLook(Player player) {
        float rad = player.getYRot() * Mth.DEG_TO_RAD;
        return new Vec3(-Mth.sin(rad), 0.0, Mth.cos(rad));
    }

    private static int hitCircle(ServerLevel level, Player player, Vec3 center, double radius, float damage, double knockback) {
        DamageSource source = player.damageSources().playerAttack(player);
        AABB box = new AABB(center.x - radius, center.y - 1.0, center.z - radius, center.x + radius, center.y + Math.max(3.0, radius * 0.5), center.z + radius);
        int count = 0;
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, e -> canHit(player, e))) {
            AABB bb = e.getBoundingBox();
            double nx = Mth.clamp(center.x, bb.minX, bb.maxX) - center.x;
            double nz = Mth.clamp(center.z, bb.minZ, bb.maxZ) - center.z;
            if (nx * nx + nz * nz > radius * radius) {
                continue;
            }
            if (e.hurtOrSimulate(source, damage)) {
                count++;
                if (knockback > 0.0) {
                    e.knockback(knockback, center.x - e.getX(), center.z - e.getZ());
                }
                WeaponFx.send(level, WeaponFx.HIT, styleOf(player), e.position().add(0.0, e.getBbHeight() * 0.6, 0.0), player.getYRot(), 1.0f, 0);
            }
        }
        return count;
    }

    private static WeaponStyle styleOf(Player player) {
        WeaponStyle style = WeaponStyle.of(player.getMainHandItem());
        return style == null ? WeaponStyle.KATANA_LIGHT : style;
    }

    private static InteractionResult begin(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND || player.getCooldowns().isOnCooldown(stack)) {
            return InteractionResult.PASS;
        }
        return null;
    }

    public static InteractionResult iaiSlash(Level world, Player player, InteractionHand hand) {
        InteractionResult blocked = begin(player, hand);
        if (blocked != null) {
            return blocked;
        }
        ItemStack stack = player.getItemInHand(hand);
        if (!(world instanceof ServerLevel level)) {
            return (true ? net.minecraft.world.InteractionResult.SUCCESS : net.minecraft.world.InteractionResult.SUCCESS_SERVER);
        }
        Vec3 dir = flatLook(player);
        Vec3 start = player.position();
        double length = IAI_LENGTH;
        for (double d = 0.5; d <= IAI_LENGTH; d += 0.5) {
            if (!level.noCollision(player, player.getBoundingBox().move(dir.scale(d)))) {
                length = d - 0.5;
                break;
            }
        }
        float damage = attack(player) * 1.5f;
        DamageSource source = player.damageSources().playerAttack(player);
        Vec3 end = start.add(dir.scale(length));
        AABB box = new AABB(start, end).inflate(IAI_HALF_WIDTH + 1.0, 0.0, IAI_HALF_WIDTH + 1.0).expandTowards(0.0, 2.5, 0.0);
        int hits = 0;
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, e -> canHit(player, e))) {
            double rx = e.getX() - start.x;
            double rz = e.getZ() - start.z;
            double along = rx * dir.x + rz * dir.z;
            double side = Math.abs(rx * dir.z - rz * dir.x) - e.getBbWidth() * 0.5;
            if (along < -0.5 || along > length + 1.0 || side > IAI_HALF_WIDTH) {
                continue;
            }
            if (e.hurtOrSimulate(source, damage)) {
                hits++;
                WeaponFx.send(level, WeaponFx.HIT, WeaponStyle.KATANA_HEAVY, e.position().add(0.0, e.getBbHeight() * 0.6, 0.0), player.getYRot(), 1.4f, 0);
            }
        }
        player.setDeltaMovement(dir.x * length * 0.32, 0.1, dir.z * length * 0.32);
        player.hurtMarked = true;
        player.getCooldowns().addCooldown(stack, IAI_COOLDOWN);
        sound(level, start, "entity.player.attack.sweep", 1.2f, 0.7f);
        sound(level, start, "item.trident.riptide_1", 0.8f, 1.6f);
        WeaponFx.send(level, WeaponFx.DASH_SLASH, WeaponStyle.KATANA_HEAVY, start, player.getYRot(), (float) Math.max(1.0, length), 0);
        MokelsBossfightSaphyraMod.LOGGER.info("[WeaponDebug] iai player={} length={} hits={} damage={}", player.getName().getString(), length, hits, damage);
        return (false ? net.minecraft.world.InteractionResult.SUCCESS : net.minecraft.world.InteractionResult.SUCCESS_SERVER);
    }

    public static InteractionResult mirrorThrow(Level world, Player player, InteractionHand hand) {
        InteractionResult blocked = begin(player, hand);
        if (blocked != null) {
            return blocked;
        }
        ItemStack stack = player.getItemInHand(hand);
        if (!(world instanceof ServerLevel level)) {
            return (true ? net.minecraft.world.InteractionResult.SUCCESS : net.minecraft.world.InteractionResult.SUCCESS_SERVER);
        }
        float a = attack(player);
        for (float offset : new float[]{-8.0f, 8.0f}) {
            MirrorBladeProjectileEntity blade = new MirrorBladeProjectileEntity(MokelsBossfightSaphyraModEntities.MIRROR_BLADE_PROJECTILE.get(), player, level);
            blade.setOwner(player);
            blade.pickup = AbstractArrow.Pickup.DISALLOWED;
            blade.setSilent(true);
            blade.setBaseDamage(a * 0.5);
            blade.setBurstDamage(a * 0.6f);
            blade.shootFromRotation(player, player.getXRot(), player.getYRot() + offset, 0.0f, 2.0f, 0.0f);
            level.addFreshEntity(blade);
        }
        player.getCooldowns().addCooldown(stack, MIRROR_COOLDOWN);
        sound(level, player.position(), "entity.player.attack.sweep", 0.8f, 1.7f);
        sound(level, player.position(), "block.amethyst_block.chime", 1.2f, 1.4f);
        return (false ? net.minecraft.world.InteractionResult.SUCCESS : net.minecraft.world.InteractionResult.SUCCESS_SERVER);
    }

    public static InteractionResult spiritRune(Level world, Player player, InteractionHand hand) {
        InteractionResult blocked = begin(player, hand);
        if (blocked != null) {
            return blocked;
        }
        ItemStack stack = player.getItemInHand(hand);
        if (!(world instanceof ServerLevel level)) {
            return (true ? net.minecraft.world.InteractionResult.SUCCESS : net.minecraft.world.InteractionResult.SUCCESS_SERVER);
        }
        Vec3 eye = player.getEyePosition();
        Vec3 far = eye.add(player.getLookAngle().scale(RUNE_RANGE));
        BlockHitResult hit = level.clip(new ClipContext(eye, far, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        Vec3 point = hit.getType() == HitResult.Type.MISS ? far : hit.getLocation();
        BlockHitResult ground = level.clip(new ClipContext(point.add(0.0, 0.5, 0.0), point.add(0.0, -8.0, 0.0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        Vec3 pos = ground.getType() == HitResult.Type.MISS ? point : ground.getLocation();
        if (TASKS.size() < MAX_TASKS) {
            TASKS.add(new RuneTask(level.dimension(), player.getUUID(), pos, attack(player) * 2.0f, level.getServer().getTickCount() + RUNE_DELAY));
        }
        player.getCooldowns().addCooldown(stack, RUNE_COOLDOWN);
        sound(level, pos, "block.amethyst_block.resonate", 1.6f, 0.8f);
        sound(level, player.position(), "entity.evoker.prepare_attack", 1.0f, 1.4f);
        WeaponFx.send(level, WeaponFx.RUNE_CIRCLE, WeaponStyle.SPIRIT_RUNE_SWORD, pos, player.getYRot(), (float) RUNE_RADIUS, RUNE_DELAY);
        return (false ? net.minecraft.world.InteractionResult.SUCCESS : net.minecraft.world.InteractionResult.SUCCESS_SERVER);
    }

    public static InteractionResult groundSlam(Level world, Player player, InteractionHand hand) {
        InteractionResult blocked = begin(player, hand);
        if (blocked != null) {
            return blocked;
        }
        ItemStack stack = player.getItemInHand(hand);
        if (!(world instanceof ServerLevel level)) {
            return (true ? net.minecraft.world.InteractionResult.SUCCESS : net.minecraft.world.InteractionResult.SUCCESS_SERVER);
        }
        Vec3 center = player.position().add(flatLook(player).scale(1.2));
        int hits = hitCircle(level, player, center, SLAM_RADIUS, attack(player) * 1.2f, 0.6);
        player.getCooldowns().addCooldown(stack, SLAM_COOLDOWN);
        sound(level, center, "entity.generic.explode", 0.9f, 1.3f);
        sound(level, center, "entity.iron_golem.attack", 1.4f, 0.6f);
        WeaponFx.send(level, WeaponFx.GROUND_SLAM, WeaponStyle.NAMELESS, center, player.getYRot(), (float) SLAM_RADIUS, 0);
        MokelsBossfightSaphyraMod.LOGGER.info("[WeaponDebug] slam player={} hits={}", player.getName().getString(), hits);
        return (false ? net.minecraft.world.InteractionResult.SUCCESS : net.minecraft.world.InteractionResult.SUCCESS_SERVER);
    }

    public static void louringHit(LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof Player player) || !(target.level() instanceof ServerLevel level)) {
            return;
        }
        Vec3 at = target.position().add(0.0, target.getBbHeight() * 0.6, 0.0);
        if (!player.hasEffect(MokelsBossfightSaphyraModMobEffects.GRIEF_EFFECT)) {
            MokelsBossfightSaphyraModVariables.PlayerVariables vars = MokelsBossfightSaphyraModVariables.getPlayerVariables(player);
            vars.Grief += 10.0;
            vars.syncPlayerVariables(player);
            WeaponFx.send(level, WeaponFx.HIT, WeaponStyle.LOURING_CLAYMORE, at, player.getYRot(), 1.2f, 0);
            return;
        }
        target.invulnerableTime = 0;
        target.hurt(player.damageSources().indirectMagic(player, player), 10.0f);
        sound(level, at, "entity.vex.charge", 1.0f, 0.6f);
        WeaponFx.send(level, WeaponFx.GRIEF_BURST, WeaponStyle.LOURING_CLAYMORE, target.position(), player.getYRot(), Math.max(1.2f, target.getBbWidth() + 0.6f), 0);
    }

    public static void staffNova(ServerLevel level, Player player) {
        WeaponFx.send(level, WeaponFx.STAFF_NOVA, WeaponStyle.WITCH_STAFF, player.position(), player.getYRot(), 6.5f, 0);
    }

    public static void staffCast(ServerLevel level, Player player) {
        WeaponFx.send(level, WeaponFx.STAFF_CAST, WeaponStyle.WITCH_STAFF, player.getEyePosition().subtract(0.0, 0.3, 0.0), player.getYRot(), 1.0f, (int) player.getXRot());
    }

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        DamageSource source = event.getSource();
        if (!(source.getEntity() instanceof Player player) || source.getDirectEntity() != player || !source.is(DamageTypes.PLAYER_ATTACK)) {
            return;
        }
        LivingEntity target = event.getEntity();
        if (!(target.level() instanceof ServerLevel level) || !canHit(player, target)) {
            return;
        }
        WeaponStyle style = WeaponStyle.of(player.getMainHandItem());
        if (style == null) {
            return;
        }
        Vec3 at = target.position().add(0.0, target.getBbHeight() * 0.6, 0.0);
        switch (style) {
            case KATANA_LIGHT -> {
                long now = level.getGameTime();
                long[] combo = COMBOS.computeIfAbsent(player.getUUID(), k -> new long[]{0L, 0L});
                if (now == combo[1]) {
                    return;
                }
                combo[0] = now - combo[1] > COMBO_WINDOW ? 1 : combo[0] + 1;
                combo[1] = now;
                if (combo[0] >= 3) {
                    combo[0] = 0;
                    event.setAmount(event.getAmount() * 1.5f);
                    sound(level, at, "entity.player.attack.crit", 1.0f, 1.3f);
                    WeaponFx.send(level, WeaponFx.CROSS_SLASH, style, at, player.getYRot(), 1.6f, 0);
                } else {
                    WeaponFx.send(level, WeaponFx.HIT, style, at, player.getYRot(), 0.8f, 0);
                }
            }
            case KATANA_POOL_OF_ROT -> {
                boolean rotten = player.hasEffect(MokelsBossfightSaphyraModMobEffects.ROT_EFFECT);
                if (rotten) {
                    event.setAmount(event.getAmount() * 1.4f);
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1), player);
                    sound(level, at, "block.honey_block.break", 1.2f, 0.6f);
                } else {
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 0), player);
                }
                WeaponFx.send(level, WeaponFx.ROT_SPLASH, style, target.position(), 0.0f, rotten ? 1.8f : 1.0f, rotten ? 1 : 0);
            }
            case SUNVEIL -> {
                target.igniteForSeconds(4.0f);
                boolean sunlit = level.isBrightOutside() && level.canSeeSky(player.blockPosition());
                if (sunlit) {
                    event.setAmount(event.getAmount() * 1.25f);
                }
                WeaponFx.send(level, WeaponFx.SUN_FLARE, style, at, 0.0f, sunlit ? 1.6f : 1.0f, sunlit ? 1 : 0);
            }
            case TEAR_AND_DROPLET -> {
                if (level.isThundering()) {
                    event.setAmount(event.getAmount() * 2.5f);
                    sound(level, at, "entity.lightning_bolt.impact", 0.6f, 1.6f);
                    WeaponFx.send(level, WeaponFx.LIGHTNING, style, target.position(), 0.0f, Math.max(1.0f, target.getBbHeight()), 0);
                } else if (level.isRaining()) {
                    event.setAmount(event.getAmount() * 1.75f);
                    sound(level, at, "entity.generic.splash", 0.6f, 1.4f);
                    WeaponFx.send(level, WeaponFx.RAIN_SPLASH, style, target.position(), 0.0f, Math.max(1.0f, target.getBbWidth() + 0.5f), 0);
                } else {
                    WeaponFx.send(level, WeaponFx.HIT, style, at, player.getYRot(), 0.8f, 0);
                }
            }
            case LOURING_CLAYMORE -> {
            }
            default -> WeaponFx.send(level, WeaponFx.HIT, style, at, player.getYRot(), style.heavy ? 1.2f : 0.9f, 0);
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (TASKS.isEmpty()) {
            return;
        }
        int now = event.getServer().getTickCount();
        Iterator<RuneTask> it = TASKS.iterator();
        while (it.hasNext()) {
            RuneTask task = it.next();
            if (now < task.fireTick()) {
                continue;
            }
            it.remove();
            ServerLevel level = event.getServer().getLevel(task.dimension());
            Player player = level == null ? null : level.getPlayerByUUID(task.owner());
            if (player == null || !player.isAlive()) {
                continue;
            }
            int hits = hitCircle(level, player, task.pos(), RUNE_RADIUS, task.damage(), 0.0);
            sound(level, task.pos(), "entity.generic.explode", 0.8f, 1.5f);
            sound(level, task.pos(), "block.amethyst_block.break", 1.6f, 0.6f);
            level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, task.pos().x, task.pos().y + 0.3, task.pos().z, 16, 1.6, 0.3, 1.6, 0.04);
            WeaponFx.send(level, WeaponFx.RUNE_BURST, WeaponStyle.SPIRIT_RUNE_SWORD, task.pos(), 0.0f, (float) RUNE_RADIUS, 0);
            MokelsBossfightSaphyraMod.LOGGER.info("[WeaponDebug] rune burst player={} hits={} damage={}", player.getName().getString(), hits, task.damage());
        }
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        COMBOS.remove(event.getEntity().getUUID());
        TASKS.removeIf(task -> task.owner().equals(event.getEntity().getUUID()));
    }
}
