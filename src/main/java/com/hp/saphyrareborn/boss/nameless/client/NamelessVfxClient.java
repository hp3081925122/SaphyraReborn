package com.hp.saphyrareborn.boss.nameless.client;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.nameless.NamelessVfx;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.joml.Vector3f;

@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class NamelessVfxClient {
    private static final DustParticleOptions CRIMSON = new DustParticleOptions(new Vector3f(0.62f, 0.05f, 0.07f), 1.5f);
    private static final DustParticleOptions DARK = new DustParticleOptions(new Vector3f(0.22f, 0.02f, 0.03f), 2.2f);
    private static final DustParticleOptions GOLD = new DustParticleOptions(new Vector3f(1.0f, 0.75f, 0.35f), 0.8f);
    private static final DustColorTransitionOptions EMBER = new DustColorTransitionOptions(new Vector3f(1.0f, 0.35f, 0.2f), new Vector3f(0.35f, 0.0f, 0.02f), 1.2f);
    private static final int MAX_EFFECTS = 64;
    private static final List<Fx> ACTIVE = new ArrayList<>();
    private static ClientLevel lastLevel;
    private static float shake;

    private NamelessVfxClient() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level != lastLevel) {
            ACTIVE.clear();
            NamelessVfx.INBOX.clear();
            shake = 0.0f;
            lastLevel = level;
        }
        if (level == null) {
            return;
        }
        while (!NamelessVfx.INBOX.isEmpty()) {
            NamelessVfx payload = NamelessVfx.INBOX.poll();
            if (ACTIVE.size() < MAX_EFFECTS) {
                ACTIVE.add(new Fx(payload));
            }
        }
        if (!mc.isPaused()) {
            Iterator<Fx> it = ACTIVE.iterator();
            while (it.hasNext()) {
                Fx fx = it.next();
                if (fx.tick(level)) {
                    it.remove();
                }
            }
            shake *= 0.82f;
            if (shake < 0.01f) {
                shake = 0.0f;
            }
        }
    }

    @SubscribeEvent
    public static void onCamera(ViewportEvent.ComputeCameraAngles event) {
        if (shake <= 0.0f) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        float t = mc.player.tickCount + (float) event.getPartialTick();
        event.setYaw(event.getYaw() + Mth.sin(t * 2.7f) * shake * 1.6f);
        event.setPitch(event.getPitch() + Mth.cos(t * 3.1f) * shake * 1.2f);
        event.setRoll(event.getRoll() + Mth.sin(t * 1.9f) * shake);
    }

    private static void addShake(double x, double y, double z, float intensity, double range) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        double d = Math.sqrt(player.distanceToSqr(x, y, z));
        if (d >= range) {
            return;
        }
        float s = (float) (intensity * (1.0 - d / range));
        shake = Math.min(1.5f, Math.max(shake, s));
    }

    private static ParticleOptions redFlame() {
        return MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get();
    }

    private static ParticleOptions redFire() {
        return MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get();
    }

    private static final class Fx {
        private final byte kind;
        private final double x;
        private final double y;
        private final double z;
        private final float size;
        private final int extra;
        private final double fx;
        private final double fz;
        private int age;

        Fx(NamelessVfx p) {
            this.kind = p.kind();
            this.x = p.x();
            this.y = p.y();
            this.z = p.z();
            this.size = Math.max(0.5f, Math.min(p.size(), 32.0f));
            this.extra = Math.max(0, Math.min(p.extra(), 400));
            double rad = Math.toRadians(p.yaw());
            this.fx = -Math.sin(rad);
            this.fz = Math.cos(rad);
        }

        boolean tick(ClientLevel level) {
            RandomSource r = level.random;
            boolean done = switch (this.kind) {
                case NamelessVfx.SLASH -> this.slash(level, r);
                case NamelessVfx.SLAM -> this.slam(level, r);
                case NamelessVfx.TELEGRAPH_RING -> this.telegraphRing(level, r);
                case NamelessVfx.TELEGRAPH_LINE -> this.telegraphLine(level, r);
                case NamelessVfx.ROT_PILLAR -> this.rotPillar(level, r);
                case NamelessVfx.ROAR -> this.roar(level, r);
                case NamelessVfx.SHIELD_SPARK -> this.shieldSpark(level, r);
                case NamelessVfx.SHIELD_BREAK -> this.shieldBreak(level, r);
                case NamelessVfx.CHARGE -> this.charge(level, r, this.size, this.extra, 6);
                case NamelessVfx.PHASE_SHIFT -> this.phaseShift(level, r);
                case NamelessVfx.SHIELD_RAISE -> this.shieldRaise(level, r);
                default -> true;
            };
            this.age++;
            return done;
        }

        private double sideX() {
            return this.fz;
        }

        private double sideZ() {
            return -this.fx;
        }

        private boolean slash(ClientLevel level, RandomSource r) {
            int steps = 4;
            double radius = this.size * 0.75;
            if (this.extra == NamelessVfx.SLASH_OVERHEAD) {
                double from = Math.toRadians(-35.0);
                double to = Math.toRadians(110.0);
                double a0 = from + (to - from) * this.age / steps;
                double a1 = from + (to - from) * (this.age + 1) / steps;
                int count = 18;
                for (int i = 0; i <= count; i++) {
                    double a = a0 + (a1 - a0) * i / count;
                    double up = Math.cos(a) * radius;
                    double fw = Math.sin(a) * radius;
                    double px = this.x + this.fx * fw;
                    double py = this.y + 0.4 + Math.max(0.0, up);
                    double pz = this.z + this.fz * fw;
                    level.addParticle(CRIMSON, px, py, pz, 0.0, 0.0, 0.0);
                    level.addParticle(redFlame(), px, py, pz, this.fx * 0.04, 0.02, this.fz * 0.04);
                    if (i % 3 == 0) {
                        level.addParticle(EMBER, px + this.fx * 0.3, py, pz + this.fz * 0.3, 0.0, 0.0, 0.0);
                    }
                }
                if (this.age == steps - 1) {
                    double gx = this.x + this.fx * radius;
                    double gz = this.z + this.fz * radius;
                    this.debris(level, r, gx, this.y, gz, 14, 0.25);
                    for (int i = 0; i < 16; i++) {
                        level.addParticle(redFire(), gx + r.nextGaussian() * 0.4, this.y + 0.1, gz + r.nextGaussian() * 0.4, r.nextGaussian() * 0.08, 0.12 + r.nextDouble() * 0.15, r.nextGaussian() * 0.08);
                    }
                }
            } else {
                double dir = this.extra == NamelessVfx.SLASH_RIGHT ? -1.0 : 1.0;
                double from = Math.toRadians(-85.0) * dir;
                double to = Math.toRadians(85.0) * dir;
                double a0 = from + (to - from) * this.age / steps;
                double a1 = from + (to - from) * (this.age + 1) / steps;
                int count = 18;
                for (int i = 0; i <= count; i++) {
                    double a = a0 + (a1 - a0) * i / count;
                    double cos = Math.cos(a);
                    double sin = Math.sin(a);
                    double dx = this.fx * cos + this.sideX() * sin;
                    double dz = this.fz * cos + this.sideZ() * sin;
                    double tilt = sin * 0.5 * dir;
                    for (int layer = 0; layer < 2; layer++) {
                        double rr = radius * (layer == 0 ? 1.0 : 0.82);
                        double px = this.x + dx * rr;
                        double py = this.y + 1.3 + tilt;
                        double pz = this.z + dz * rr;
                        level.addParticle(layer == 0 ? CRIMSON : DARK, px, py, pz, 0.0, 0.0, 0.0);
                        if (layer == 0) {
                            level.addParticle(redFlame(), px, py, pz, dx * 0.05, 0.01, dz * 0.05);
                        }
                    }
                    if (i % 4 == 0) {
                        level.addParticle(EMBER, this.x + dx * radius * 1.08, this.y + 1.3 + tilt, this.z + dz * radius * 1.08, 0.0, 0.0, 0.0);
                    }
                }
            }
            if (this.age == 0) {
                level.addParticle(ParticleTypes.SWEEP_ATTACK, this.x + this.fx * 2.0, this.y + 1.2, this.z + this.fz * 2.0, 0.0, 0.0, 0.0);
                addShake(this.x, this.y, this.z, 0.18f, 12.0);
            }
            return this.age >= steps - 1;
        }

        private boolean slam(ClientLevel level, RandomSource r) {
            int ticks = Math.max(2, this.extra);
            if (this.age == 0) {
                this.debris(level, r, this.x, this.y, this.z, (int) Math.min(60, 8 + this.size * 3), 0.35);
                for (int i = 0; i < 20; i++) {
                    level.addParticle(redFlame(), this.x + r.nextGaussian() * 0.5, this.y + 0.2, this.z + r.nextGaussian() * 0.5, r.nextGaussian() * 0.1, 0.2 + r.nextDouble() * 0.3, r.nextGaussian() * 0.1);
                }
                addShake(this.x, this.y, this.z, Math.min(1.0f, 0.15f + this.size * 0.045f), this.size * 2.5 + 8.0);
            }
            double radius = this.size * (this.age + 1) / ticks;
            int count = (int) Math.min(140, Math.max(12, radius * Math.PI * 2.0 * 1.2));
            for (int i = 0; i < count; i++) {
                double a = Math.PI * 2.0 * i / count + r.nextDouble() * 0.05;
                double cx = Math.cos(a);
                double cz = Math.sin(a);
                double px = this.x + cx * radius;
                double pz = this.z + cz * radius;
                level.addParticle(DARK, px, this.y + 0.1, pz, 0.0, 0.0, 0.0);
                if (i % 2 == 0) {
                    level.addParticle(redFire(), px, this.y + 0.1, pz, cx * 0.04, 0.04 + r.nextDouble() * 0.06, cz * 0.04);
                }
                if (i % 5 == 0) {
                    level.addParticle(EMBER, px, this.y + 0.3 + r.nextDouble() * 0.4, pz, 0.0, 0.0, 0.0);
                }
            }
            return this.age >= ticks - 1;
        }

        private boolean telegraphRing(ClientLevel level, RandomSource r) {
            int dur = Math.max(2, this.extra);
            if (this.age % 2 == 0) {
                this.circle(level, CRIMSON, this.size, (int) Math.min(110, Math.max(16, this.size * 4.0)), 0.12, 0.0);
                double inner = this.size * this.age / dur;
                if (inner > 0.4) {
                    this.circle(level, EMBER, inner, (int) Math.min(90, Math.max(10, inner * 3.0)), 0.15, 0.0);
                }
            }
            if (this.age == dur - 1) {
                this.circle(level, redFlame(), this.size, (int) Math.min(120, this.size * 4.0), 0.2, 0.08);
            }
            return this.age >= dur - 1;
        }

        private boolean telegraphLine(ClientLevel level, RandomSource r) {
            int dur = Math.max(2, this.extra);
            double scale = this.size / 19.0;
            if (this.age % 2 == 0) {
                for (double d = 1.0; d <= this.size + 1.5; d += 0.7) {
                    level.addParticle(CRIMSON, this.x + this.fx * d, this.y + 0.12, this.z + this.fz * d, 0.0, 0.0, 0.0);
                }
                for (double d : new double[]{5.0, 12.0, 19.0}) {
                    double cx = this.x + this.fx * d * scale;
                    double cz = this.z + this.fz * d * scale;
                    double rr = 1.2 + 0.8 * this.age / dur;
                    for (int i = 0; i < 12; i++) {
                        double a = Math.PI * 2.0 * i / 12;
                        level.addParticle(EMBER, cx + Math.cos(a) * rr, this.y + 0.14, cz + Math.sin(a) * rr, 0.0, 0.0, 0.0);
                    }
                }
            }
            return this.age >= dur - 1;
        }

        private boolean rotPillar(ClientLevel level, RandomSource r) {
            int start = this.extra;
            if (this.age < start) {
                return false;
            }
            int local = this.age - start;
            if (local == 0) {
                this.debris(level, r, this.x, this.y, this.z, 12, 0.3);
                this.circle(level, DARK, this.size * 1.6, 24, 0.1, 0.0);
                addShake(this.x, this.y, this.z, 0.25f, 10.0);
            }
            if (local == 1) {
                this.circle(level, redFire(), this.size * 2.0, 28, 0.1, 0.05);
            }
            for (int i = 0; i < 10; i++) {
                double a = r.nextDouble() * Math.PI * 2.0;
                double d = Math.sqrt(r.nextDouble()) * this.size * 0.5;
                level.addParticle(redFlame(), this.x + Math.cos(a) * d, this.y + 0.1, this.z + Math.sin(a) * d, 0.0, 0.25 + r.nextDouble() * 0.35, 0.0);
            }
            if (local % 2 == 0) {
                level.addParticle(ParticleTypes.LARGE_SMOKE, this.x + r.nextGaussian() * 0.4, this.y + 0.5, this.z + r.nextGaussian() * 0.4, 0.0, 0.08, 0.0);
                level.addParticle(redFire(), this.x, this.y + 0.2, this.z, r.nextGaussian() * 0.05, 0.4, r.nextGaussian() * 0.05);
            }
            return local >= 7;
        }

        private boolean roar(ClientLevel level, RandomSource r) {
            if (this.age == 0) {
                for (int i = 0; i < 140; i++) {
                    double a = r.nextDouble() * Math.PI * 2.0;
                    double speed = 0.45 + r.nextDouble() * 0.35;
                    level.addParticle(redFlame(), this.x, this.y + 1.2 + r.nextDouble() * 1.6, this.z, Math.cos(a) * speed, r.nextGaussian() * 0.03, Math.sin(a) * speed);
                }
                addShake(this.x, this.y, this.z, 0.9f, this.size * 2.0 + 8.0);
            }
            for (int wave = 0; wave < 3; wave++) {
                int local = this.age - wave * 4;
                if (local < 0 || local > 8) {
                    continue;
                }
                double radius = this.size * (local + 1) / 9.0;
                int count = (int) Math.min(100, Math.max(16, radius * 5.0));
                double height = this.y + 0.3 + wave * 0.9;
                for (int i = 0; i < count; i++) {
                    double a = Math.PI * 2.0 * i / count;
                    level.addParticle(wave == 1 ? EMBER : DARK, this.x + Math.cos(a) * radius, height, this.z + Math.sin(a) * radius, 0.0, 0.0, 0.0);
                }
            }
            return this.age >= 16;
        }

        private boolean shieldSpark(ClientLevel level, RandomSource r) {
            double px = this.x + this.fx * 1.3;
            double py = this.y + 1.7;
            double pz = this.z + this.fz * 1.3;
            int count = (int) (12 * this.size);
            for (int i = 0; i < count; i++) {
                double vx = this.fx * 0.3 + r.nextGaussian() * 0.2;
                double vy = r.nextGaussian() * 0.15 + 0.05;
                double vz = this.fz * 0.3 + r.nextGaussian() * 0.2;
                level.addParticle(ParticleTypes.CRIT, px, py, pz, vx, vy, vz);
                if (i % 2 == 0) {
                    level.addParticle(GOLD, px + r.nextGaussian() * 0.3, py + r.nextGaussian() * 0.3, pz + r.nextGaussian() * 0.3, 0.0, 0.0, 0.0);
                }
            }
            level.addParticle(ParticleTypes.ENCHANTED_HIT, px, py, pz, this.fx * 0.2, 0.0, this.fz * 0.2);
            return true;
        }

        private boolean shieldRaise(ClientLevel level, RandomSource r) {
            int dur = Math.max(4, this.extra);
            if (this.age % 4 == 0) {
                for (int i = 0; i <= 10; i++) {
                    double a = Math.toRadians(-70.0 + 140.0 * i / 10);
                    double dx = this.fx * Math.cos(a) + this.sideX() * Math.sin(a);
                    double dz = this.fz * Math.cos(a) + this.sideZ() * Math.sin(a);
                    level.addParticle(i % 2 == 0 ? GOLD : CRIMSON, this.x + dx * 1.5, this.y + 1.0 + r.nextDouble() * 1.2, this.z + dz * 1.5, 0.0, 0.01, 0.0);
                }
            }
            if (this.age == 0) {
                for (int i = 0; i < 16; i++) {
                    level.addParticle(ParticleTypes.CRIT, this.x + this.fx * 1.2, this.y + 1.6, this.z + this.fz * 1.2, r.nextGaussian() * 0.2, r.nextGaussian() * 0.2, r.nextGaussian() * 0.2);
                }
            }
            return this.age >= dur;
        }

        private boolean shieldBreak(ClientLevel level, RandomSource r) {
            double px = this.x + this.fx * 1.2;
            double py = this.y + 1.6;
            double pz = this.z + this.fz * 1.2;
            ItemParticleOption shard = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SHIELD));
            for (int i = 0; i < 30; i++) {
                level.addParticle(shard, px, py, pz, r.nextGaussian() * 0.25, 0.15 + r.nextDouble() * 0.3, r.nextGaussian() * 0.25);
                level.addParticle(ParticleTypes.CRIT, px, py, pz, r.nextGaussian() * 0.4, r.nextGaussian() * 0.4, r.nextGaussian() * 0.4);
            }
            for (int i = 0; i < 40; i++) {
                double a = r.nextDouble() * Math.PI * 2.0;
                level.addParticle(redFlame(), px, py, pz, Math.cos(a) * 0.3, r.nextGaussian() * 0.1, Math.sin(a) * 0.3);
                level.addParticle(DARK, px + r.nextGaussian() * 0.6, py + r.nextGaussian() * 0.6, pz + r.nextGaussian() * 0.6, 0.0, 0.0, 0.0);
            }
            level.addParticle(ParticleTypes.FLASH, px, py, pz, 0.0, 0.0, 0.0);
            addShake(this.x, this.y, this.z, 0.6f, 16.0);
            return true;
        }

        private boolean charge(ClientLevel level, RandomSource r, double radius, int dur, int perTick) {
            dur = Math.max(2, dur);
            double cy = this.y + 1.6;
            for (int i = 0; i < perTick; i++) {
                double theta = r.nextDouble() * Math.PI * 2.0;
                double phi = Math.acos(2.0 * r.nextDouble() - 1.0);
                double px = this.x + Math.sin(phi) * Math.cos(theta) * radius;
                double py = cy + Math.cos(phi) * radius * 0.6;
                double pz = this.z + Math.sin(phi) * Math.sin(theta) * radius;
                level.addParticle(redFlame(), px, py, pz, (this.x - px) * 0.09, (cy - py) * 0.09, (this.z - pz) * 0.09);
            }
            double ground = radius * 1.4 * (1.0 - (double) this.age / dur);
            if (this.age % 2 == 0 && ground > 0.3) {
                this.circle(level, DARK, ground, (int) Math.max(10, ground * 5.0), 0.1, 0.0);
            }
            if (this.age >= dur - 1) {
                for (int i = 0; i < 30; i++) {
                    level.addParticle(redFire(), this.x, cy, this.z, r.nextGaussian() * 0.2, r.nextGaussian() * 0.2, r.nextGaussian() * 0.2);
                }
                return true;
            }
            return false;
        }

        private boolean phaseShift(ClientLevel level, RandomSource r) {
            int roar = 30;
            if (this.age < roar) {
                this.charge(level, r, 5.0, roar, 10);
                return false;
            }
            int local = this.age - roar;
            if (local == 0) {
                for (int i = 0; i < 200; i++) {
                    double a = r.nextDouble() * Math.PI * 2.0;
                    double speed = 0.4 + r.nextDouble() * 0.5;
                    level.addParticle(redFlame(), this.x, this.y + 0.5 + r.nextDouble() * 2.5, this.z, Math.cos(a) * speed, r.nextGaussian() * 0.05, Math.sin(a) * speed);
                }
                this.debris(level, r, this.x, this.y, this.z, 50, 0.45);
                level.addParticle(ParticleTypes.FLASH, this.x, this.y + 1.5, this.z, 0.0, 0.0, 0.0);
                addShake(this.x, this.y, this.z, 1.2f, 32.0);
            }
            int pillar = Math.max(0, 16 - local);
            for (int i = 0; i < pillar; i++) {
                double a = r.nextDouble() * Math.PI * 2.0;
                double d = r.nextDouble() * 1.2;
                level.addParticle(redFlame(), this.x + Math.cos(a) * d, this.y + 0.2, this.z + Math.sin(a) * d, 0.0, 0.6 + r.nextDouble() * 0.6, 0.0);
            }
            if (local < 10) {
                double radius = this.size * (local + 1) / 10.0 * 1.5;
                this.circle(level, local % 2 == 0 ? EMBER : DARK, radius, (int) Math.min(100, radius * 5.0), 0.15, 0.0);
            }
            return local >= 16;
        }

        private void circle(ClientLevel level, ParticleOptions particle, double radius, int count, double yOffset, double vy) {
            for (int i = 0; i < count; i++) {
                double a = Math.PI * 2.0 * i / count;
                level.addParticle(particle, this.x + Math.cos(a) * radius, this.y + yOffset, this.z + Math.sin(a) * radius, 0.0, vy, 0.0);
            }
        }

        private void debris(ClientLevel level, RandomSource r, double px, double py, double pz, int count, double speed) {
            BlockState state = level.getBlockState(BlockPos.containing(px, py - 0.5, pz));
            if (state.isAir() || state.getRenderShape() == RenderShape.INVISIBLE) {
                return;
            }
            BlockParticleOption particle = new BlockParticleOption(ParticleTypes.BLOCK, state);
            for (int i = 0; i < count; i++) {
                level.addParticle(particle, px + r.nextGaussian() * 0.8, py + 0.1, pz + r.nextGaussian() * 0.8, r.nextGaussian() * speed * 0.4, speed + r.nextDouble() * speed, r.nextGaussian() * speed * 0.4);
            }
        }
    }
}
