package com.hp.saphyrareborn.boss.nameless.client;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.client.BossVfxRender;
import com.hp.saphyrareborn.boss.client.BossVfxRender.Painter;
import com.hp.saphyrareborn.boss.nameless.NamelessVfx;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.clamp01;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.easeOut;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.rgba;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class NamelessVfxClient {
    private static final int MAX_EFFECTS = 64;
    private static final List<Fx> ACTIVE = new ArrayList<>();
    private static ClientLevel lastLevel;

    private NamelessVfxClient() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level != lastLevel) {
            ACTIVE.clear();
            NamelessVfx.INBOX.clear();
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
        if (mc.isPaused()) {
            return;
        }
        Iterator<Fx> it = ACTIVE.iterator();
        while (it.hasNext()) {
            Fx fx = it.next();
            fx.tickParticles(level, level.random);
            fx.age++;
            if (fx.age > fx.life) {
                it.remove();
            }
        }
    }

    @SubscribeEvent
    public static void onRender(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES || ACTIVE.isEmpty()) {
            return;
        }
        Vec3 cam = event.getCamera().getPosition();
        float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
        PoseStack pose = event.getPoseStack();
        pose.pushPose();
        pose.translate(-cam.x, -cam.y, -cam.z);
        MultiBufferSource.BufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer consumer = buffers.getBuffer(BossVfxRender.GLOW);
        PoseStack.Pose last = pose.last();
        for (Fx fx : ACTIVE) {
            if (cam.distanceToSqr(fx.x, fx.y, fx.z) < BossVfxRender.RENDER_RANGE_SQR) {
                fx.render(new Painter(consumer, last), fx.age + partial);
            }
        }
        buffers.endBatch(BossVfxRender.GLOW);
        pose.popPose();
    }

    private static ParticleOptions redFlame() {
        return MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get();
    }

    private static ParticleOptions redFire() {
        return MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get();
    }

    private static float[] core(float a) {
        return rgba(1.0f, 0.42f, 0.25f, a);
    }

    private static float[] crimson(float a) {
        return rgba(0.85f, 0.05f, 0.08f, a);
    }

    private static float[] gold(float a) {
        return rgba(1.0f, 0.72f, 0.3f, a);
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
        private final double yawRad;
        private final int life;
        private int age;

        Fx(NamelessVfx p) {
            this.kind = p.kind();
            this.x = p.x();
            this.y = p.y();
            this.z = p.z();
            this.size = Math.max(0.5f, Math.min(p.size(), 32.0f));
            this.extra = Math.max(0, Math.min(p.extra(), 400));
            this.yawRad = Math.toRadians(p.yaw());
            this.fx = -Math.sin(this.yawRad);
            this.fz = Math.cos(this.yawRad);
            this.life = switch (this.kind) {
                case NamelessVfx.SLASH -> 7;
                case NamelessVfx.SLAM -> Math.max(2, this.extra) + 6;
                case NamelessVfx.TELEGRAPH_RING, NamelessVfx.TELEGRAPH_LINE -> Math.max(2, this.extra) + 4;
                case NamelessVfx.ROT_PILLAR -> this.extra + 14;
                case NamelessVfx.ROAR -> 18;
                case NamelessVfx.SHIELD_SPARK -> 5;
                case NamelessVfx.SHIELD_RAISE -> Math.max(4, this.extra);
                case NamelessVfx.SHIELD_BREAK -> 10;
                case NamelessVfx.CHARGE -> Math.max(2, this.extra) + 3;
                case NamelessVfx.PHASE_SHIFT -> 52;
                case NamelessVfx.ROT_POOL -> Math.max(4, this.extra);
                default -> 0;
            };
        }

        private double faceAngle() {
            return Math.atan2(this.fz, this.fx);
        }

        void tickParticles(ClientLevel level, RandomSource r) {
            switch (this.kind) {
                case NamelessVfx.SLASH -> {
                    if (this.age == 0) {
                        level.addParticle(ParticleTypes.SWEEP_ATTACK, this.x + this.fx * 2.0, this.y + 1.2, this.z + this.fz * 2.0, 0.0, 0.0, 0.0);
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.18f, 12.0);
                    }
                    if (this.age == 3) {
                        double reach = this.size * 0.75;
                        for (int i = 0; i < 6; i++) {
                            double a = this.faceAngle() + (r.nextDouble() - 0.5) * 2.4;
                            level.addParticle(redFlame(), this.x + Math.cos(a) * reach, this.y + 0.6 + r.nextDouble() * 1.2, this.z + Math.sin(a) * reach, 0.0, 0.03, 0.0);
                        }
                    }
                }
                case NamelessVfx.SLAM -> {
                    if (this.age == 0) {
                        BossVfxRender.debris(level, r, this.x, this.y, this.z, (int) Math.min(18, 6 + this.size), 0.35);
                        for (int i = 0; i < 8; i++) {
                            level.addParticle(redFlame(), this.x + r.nextGaussian() * 0.5, this.y + 0.2, this.z + r.nextGaussian() * 0.5, r.nextGaussian() * 0.08, 0.2 + r.nextDouble() * 0.25, r.nextGaussian() * 0.08);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(1.0f, 0.15f + this.size * 0.045f), this.size * 2.5 + 8.0);
                    }
                }
                case NamelessVfx.ROT_PILLAR -> {
                    int local = this.age - this.extra;
                    if (local == 0) {
                        BossVfxRender.debris(level, r, this.x, this.y, this.z, 5, 0.3);
                        for (int i = 0; i < 5; i++) {
                            level.addParticle(redFlame(), this.x + r.nextGaussian() * 0.6, this.y + 0.2, this.z + r.nextGaussian() * 0.6, 0.0, 0.3 + r.nextDouble() * 0.3, 0.0);
                        }
                        level.addParticle(ParticleTypes.LARGE_SMOKE, this.x, this.y + 0.6, this.z, 0.0, 0.08, 0.0);
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.25f, 10.0);
                    }
                }
                case NamelessVfx.ROAR -> {
                    if (this.age == 0) {
                        for (int i = 0; i < 30; i++) {
                            double a = r.nextDouble() * Math.PI * 2.0;
                            double speed = 0.45 + r.nextDouble() * 0.35;
                            level.addParticle(redFlame(), this.x, this.y + 1.2 + r.nextDouble() * 1.6, this.z, Math.cos(a) * speed, r.nextGaussian() * 0.03, Math.sin(a) * speed);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.9f, this.size * 2.0 + 8.0);
                    }
                }
                case NamelessVfx.SHIELD_SPARK -> {
                    if (this.age == 0) {
                        double px = this.x + this.fx * 1.3, py = this.y + 1.7, pz = this.z + this.fz * 1.3;
                        for (int i = 0; i < (int) (5 * this.size); i++) {
                            level.addParticle(ParticleTypes.CRIT, px, py, pz, this.fx * 0.3 + r.nextGaussian() * 0.2, r.nextGaussian() * 0.15 + 0.05, this.fz * 0.3 + r.nextGaussian() * 0.2);
                        }
                    }
                }
                case NamelessVfx.SHIELD_RAISE -> {
                    if (this.age == 0) {
                        for (int i = 0; i < 6; i++) {
                            level.addParticle(ParticleTypes.CRIT, this.x + this.fx * 1.2, this.y + 1.6, this.z + this.fz * 1.2, r.nextGaussian() * 0.2, r.nextGaussian() * 0.2, r.nextGaussian() * 0.2);
                        }
                    }
                }
                case NamelessVfx.SHIELD_BREAK -> {
                    if (this.age == 0) {
                        double px = this.x + this.fx * 1.2, py = this.y + 1.6, pz = this.z + this.fz * 1.2;
                        ItemParticleOption shard = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SHIELD));
                        for (int i = 0; i < 12; i++) {
                            level.addParticle(shard, px, py, pz, r.nextGaussian() * 0.25, 0.15 + r.nextDouble() * 0.3, r.nextGaussian() * 0.25);
                        }
                        for (int i = 0; i < 8; i++) {
                            level.addParticle(ParticleTypes.CRIT, px, py, pz, r.nextGaussian() * 0.4, r.nextGaussian() * 0.4, r.nextGaussian() * 0.4);
                        }
                        level.addParticle(ParticleTypes.FLASH, px, py, pz, 0.0, 0.0, 0.0);
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.6f, 16.0);
                    }
                }
                case NamelessVfx.CHARGE -> {
                    if (this.age % 2 == 0 && this.age < this.life - 3) {
                        double a = r.nextDouble() * Math.PI * 2.0;
                        double d = this.size * 1.2;
                        double px = this.x + Math.cos(a) * d, py = this.y + 0.6 + r.nextDouble() * 2.0, pz = this.z + Math.sin(a) * d;
                        level.addParticle(redFlame(), px, py, pz, (this.x - px) * 0.09, (this.y + 1.6 - py) * 0.09, (this.z - pz) * 0.09);
                    }
                }
                case NamelessVfx.PHASE_SHIFT -> {
                    if (this.age < 30 && this.age % 2 == 0) {
                        double a = r.nextDouble() * Math.PI * 2.0;
                        double px = this.x + Math.cos(a) * 5.0, py = this.y + 0.5 + r.nextDouble() * 2.5, pz = this.z + Math.sin(a) * 5.0;
                        level.addParticle(redFlame(), px, py, pz, (this.x - px) * 0.09, (this.y + 1.6 - py) * 0.09, (this.z - pz) * 0.09);
                    }
                    if (this.age == 30) {
                        for (int i = 0; i < 40; i++) {
                            double a = r.nextDouble() * Math.PI * 2.0;
                            double speed = 0.4 + r.nextDouble() * 0.5;
                            level.addParticle(redFlame(), this.x, this.y + 0.5 + r.nextDouble() * 2.5, this.z, Math.cos(a) * speed, r.nextGaussian() * 0.05, Math.sin(a) * speed);
                        }
                        BossVfxRender.debris(level, r, this.x, this.y, this.z, 20, 0.45);
                        level.addParticle(ParticleTypes.FLASH, this.x, this.y + 1.5, this.z, 0.0, 0.0, 0.0);
                        BossVfxRender.addShake(this.x, this.y, this.z, 1.2f, 32.0);
                    }
                }
                case NamelessVfx.ROT_POOL -> {
                    if (this.age % 6 == 0) {
                        level.addParticle(redFire(), this.x + r.nextGaussian() * 1.0, this.y + 0.1, this.z + r.nextGaussian() * 1.0, 0.0, 0.05, 0.0);
                    }
                }
                default -> {
                }
            }
        }

        void render(Painter p, float t) {
            switch (this.kind) {
                case NamelessVfx.SLASH -> this.renderSlash(p, t);
                case NamelessVfx.SLAM -> this.renderSlam(p, t);
                case NamelessVfx.TELEGRAPH_RING -> this.renderTelegraphRing(p, t);
                case NamelessVfx.TELEGRAPH_LINE -> this.renderTelegraphLine(p, t);
                case NamelessVfx.ROT_PILLAR -> this.renderPillar(p, t - this.extra, this.size * 0.6, 5.0, 1.0f);
                case NamelessVfx.ROAR -> this.renderRoar(p, t);
                case NamelessVfx.SHIELD_SPARK -> this.renderFlare(p, t, 5.0f, 0.5 + 0.4 * this.size);
                case NamelessVfx.SHIELD_RAISE -> this.renderBarrier(p, t);
                case NamelessVfx.SHIELD_BREAK -> this.renderShieldBreak(p, t);
                case NamelessVfx.CHARGE -> this.renderCharge(p, t, this.size, Math.max(2, this.extra));
                case NamelessVfx.PHASE_SHIFT -> this.renderPhaseShift(p, t);
                case NamelessVfx.ROT_POOL -> this.renderPool(p, t);
                default -> {
                }
            }
        }

        private void renderSlash(Painter p, float t) {
            float reveal = easeOut(t / 3.0f);
            float fade = 1.0f - clamp01((t - 3.0f) / 4.0f);
            if (fade <= 0.0f) {
                return;
            }
            double outer = this.size * 0.92;
            double inner = this.size * 0.55;
            int segs = 20;
            boolean overhead = this.extra == NamelessVfx.SLASH_OVERHEAD;
            double dir = this.extra == NamelessVfx.SLASH_RIGHT ? -1.0 : 1.0;
            double from = overhead ? Math.toRadians(-35.0) : Math.toRadians(-85.0) * dir;
            double to = overhead ? Math.toRadians(110.0) : Math.toRadians(85.0) * dir;
            double end = from + (to - from) * reveal;
            for (int i = 0; i < segs; i++) {
                double f0 = (double) i / segs;
                double f1 = (double) (i + 1) / segs;
                double a0 = from + (end - from) * f0;
                double a1 = from + (end - from) * f1;
                float w0 = (float) (0.25 + 0.75 * f0) * fade;
                float w1 = (float) (0.25 + 0.75 * f1) * fade;
                double[] o0 = this.slashPoint(a0, outer, overhead, dir);
                double[] o1 = this.slashPoint(a1, outer, overhead, dir);
                double[] i0 = this.slashPoint(a0, inner, overhead, dir);
                double[] i1 = this.slashPoint(a1, inner, overhead, dir);
                p.quad(i0[0], i0[1], i0[2], crimson(0.0f), o0[0], o0[1], o0[2], core(w0 * 0.9f),
                        o1[0], o1[1], o1[2], core(w1 * 0.9f), i1[0], i1[1], i1[2], crimson(0.0f));
                double[] e0 = this.slashPoint(a0, outer * 1.08, overhead, dir);
                double[] e1 = this.slashPoint(a1, outer * 1.08, overhead, dir);
                p.quad(o0[0], o0[1], o0[2], core(w0 * 0.9f), e0[0], e0[1], e0[2], crimson(0.0f),
                        e1[0], e1[1], e1[2], crimson(0.0f), o1[0], o1[1], o1[2], core(w1 * 0.9f));
            }
        }

        private double[] slashPoint(double a, double radius, boolean overhead, double dir) {
            if (overhead) {
                double up = Math.cos(a) * radius;
                double fw = Math.sin(a) * radius;
                return new double[]{this.x + this.fx * fw, this.y + 0.4 + Math.max(0.0, up), this.z + this.fz * fw};
            }
            double cos = Math.cos(a);
            double sin = Math.sin(a);
            double dx = this.fx * cos + this.fz * sin;
            double dz = this.fz * cos - this.fx * sin;
            return new double[]{this.x + dx * radius, this.y + 1.3 + sin * 0.5 * dir, this.z + dz * radius};
        }

        private void renderSlam(Painter p, float t) {
            int ticks = Math.max(2, this.extra);
            float prog = easeOut(t / ticks);
            float fade = 1.0f - clamp01((t - ticks) / 6.0f);
            double r = this.size * prog;
            double w = Math.max(0.35, this.size * 0.12);
            int segs = (int) Math.min(64, Math.max(16, this.size * 4.0));
            p.annulus(this.x, this.y + 0.06, this.z, Math.max(0.0, r - w), r, segs, crimson(0.0f), core(0.85f * fade));
            p.annulus(this.x, this.y + 0.06, this.z, r, r + w * 0.5, segs, core(0.85f * fade), crimson(0.0f));
            p.annulus(this.x, this.y + 0.05, this.z, 0.0, r, segs, crimson(0.18f * fade), crimson(0.05f * fade));
            double h = 0.9 * (1.0 - prog) + 0.2;
            p.wall(this.x, this.y, this.y + h, this.z, r, segs, 0.0, Math.PI * 2.0, crimson(0.55f * fade), crimson(0.0f));
        }

        private void renderTelegraphRing(Painter p, float t) {
            int dur = Math.max(2, this.extra);
            float pulse = 0.75f + 0.25f * Mth.sin(t * 0.8f);
            int segs = (int) Math.min(72, Math.max(20, this.size * 4.0));
            if (t <= dur) {
                p.annulus(this.x, this.y + 0.07, this.z, this.size - 0.22, this.size, segs, crimson(0.5f * pulse), crimson(0.7f * pulse));
                double inner = this.size * clamp01(t / dur);
                p.annulus(this.x, this.y + 0.06, this.z, 0.0, inner, segs, crimson(0.06f), crimson(0.22f));
                p.annulus(this.x, this.y + 0.065, this.z, Math.max(0.0, inner - 0.25), inner, segs, core(0.0f), core(0.6f));
            } else {
                float flash = 1.0f - clamp01((t - dur) / 4.0f);
                p.annulus(this.x, this.y + 0.07, this.z, 0.0, this.size, segs, core(0.25f * flash), core(0.6f * flash));
            }
        }

        private void renderTelegraphLine(Painter p, float t) {
            int dur = Math.max(2, this.extra);
            float a = t <= dur ? 0.35f + 0.15f * Mth.sin(t * 0.9f) : 0.5f * (1.0f - clamp01((t - dur) / 4.0f));
            double sx = this.fz, sz = -this.fx;
            double w = 0.7;
            double len = this.size + 2.0;
            p.quad(this.x + sx * w + this.fx, this.y + 0.07, this.z + sz * w + this.fz, crimson(0.0f),
                    this.x - sx * w + this.fx, this.y + 0.07, this.z - sz * w + this.fz, crimson(0.0f),
                    this.x - sx * w + this.fx * len, this.y + 0.07, this.z - sz * w + this.fz * len, crimson(a),
                    this.x + sx * w + this.fx * len, this.y + 0.07, this.z + sz * w + this.fz * len, crimson(a));
            double scale = this.size / 19.0;
            double grow = 1.2 + 0.8 * clamp01(t / dur);
            for (double d : new double[]{5.0, 12.0, 19.0}) {
                double cx = this.x + this.fx * d * scale;
                double cz = this.z + this.fz * d * scale;
                p.annulus(cx, this.y + 0.075, cz, grow - 0.2, grow, 20, core(a), core(a * 1.3f));
                p.annulus(cx, this.y + 0.07, cz, 0.0, grow - 0.2, 20, crimson(a * 0.3f), crimson(a * 0.5f));
            }
        }

        private void renderPillar(Painter p, float local, double radius, double height, float strength) {
            if (local < 0.0f) {
                return;
            }
            float rise = easeOut(local / 3.0f);
            float fade = (1.0f - clamp01((local - 4.0f) / 9.0f)) * strength;
            if (fade <= 0.0f) {
                return;
            }
            double h = height * rise;
            p.wall(this.x, this.y, this.y + h, this.z, radius, 14, 0.0, Math.PI * 2.0, core(0.75f * fade), crimson(0.0f));
            p.wall(this.x, this.y, this.y + h * 0.7, this.z, radius * 0.45, 10, 0.0, Math.PI * 2.0, core(0.9f * fade), core(0.0f));
            double ring = radius * (1.0 + 1.8 * easeOut(local / 4.0f));
            p.annulus(this.x, this.y + 0.06, this.z, ring * 0.7, ring, 24, crimson(0.0f), core(0.6f * fade));
        }

        private void renderRoar(Painter p, float t) {
            for (int wave = 0; wave < 3; wave++) {
                float local = t - wave * 4;
                if (local < 0.0f || local > 10.0f) {
                    continue;
                }
                float prog = easeOut(local / 10.0f);
                float fade = 1.0f - clamp01(local / 10.0f);
                double r = this.size * prog;
                double cy = this.y + 0.4 + wave * 0.8;
                int segs = (int) Math.min(64, Math.max(20, r * 4.0));
                p.wall(this.x, cy, cy + 1.4, this.z, r, segs, 0.0, Math.PI * 2.0, crimson(0.0f), wave == 1 ? core(0.55f * fade) : crimson(0.6f * fade));
                p.wall(this.x, cy + 1.4, cy + 2.2, this.z, r, segs, 0.0, Math.PI * 2.0, wave == 1 ? core(0.55f * fade) : crimson(0.6f * fade), crimson(0.0f));
            }
            float fade = 1.0f - clamp01(t / 14.0f);
            double r = this.size * easeOut(t / 8.0f);
            p.annulus(this.x, this.y + 0.06, this.z, r * 0.85, r, 48, crimson(0.0f), crimson(0.6f * fade));
        }

        private void renderFlare(Painter p, float t, float lifeTicks, double radius) {
            float fade = 1.0f - clamp01(t / lifeTicks);
            if (fade <= 0.0f) {
                return;
            }
            double cx = this.x + this.fx * 1.35, cy = this.y + 1.7, cz = this.z + this.fz * 1.35;
            double sx = this.fz, sz = -this.fx;
            double r = radius * (0.6 + 0.4 * easeOut(t / 2.0f));
            float[] c = gold(0.95f * fade);
            float[] e = gold(0.0f);
            p.quad(cx - sx * r, cy, cz - sz * r, e, cx, cy + r * 0.25, cz, c, cx + sx * r, cy, cz + sz * r, e, cx, cy - r * 0.25, cz, c);
            p.quad(cx - sx * r * 0.25, cy, cz - sz * r * 0.25, c, cx, cy + r, cz, e, cx + sx * r * 0.25, cy, cz + sz * r * 0.25, c, cx, cy - r, cz, e);
        }

        private void renderBarrier(Painter p, float t) {
            float in = clamp01(t / 4.0f);
            float out = 1.0f - clamp01((t - (this.life - 5)) / 5.0f);
            float a = in * out * (0.75f + 0.25f * Mth.sin(t * 0.6f));
            if (a <= 0.0f) {
                return;
            }
            double face = this.faceAngle();
            double span = Math.toRadians(65.0);
            p.wall(this.x, this.y + 0.2, this.y + 1.5, this.z, 1.7, 12, face - span, face + span, gold(0.05f * a), gold(0.28f * a));
            p.wall(this.x, this.y + 1.5, this.y + 2.8, this.z, 1.7, 12, face - span, face + span, gold(0.28f * a), gold(0.05f * a));
            p.wall(this.x, this.y + 0.2, this.y + 0.35, this.z, 1.72, 12, face - span, face + span, crimson(0.7f * a), crimson(0.7f * a));
        }

        private void renderShieldBreak(Painter p, float t) {
            float fade = 1.0f - clamp01(t / 10.0f);
            double r = 1.7 + 2.0 * easeOut(t / 6.0f);
            double face = this.faceAngle();
            double span = Math.toRadians(80.0);
            p.wall(this.x, this.y + 0.2, this.y + 2.8, this.z, r, 14, face - span, face + span, gold(0.5f * fade), gold(0.0f));
            p.annulus(this.x, this.y + 0.06, this.z, r * 0.8, r, 32, crimson(0.0f), core(0.6f * fade));
            this.renderFlare(p, t, 6.0f, 1.4);
        }

        private void renderCharge(Painter p, float t, double radius, int dur) {
            float prog = clamp01(t / dur);
            if (t <= dur) {
                double r = radius * 1.4 * (1.0 - prog) + 0.3;
                p.annulus(this.x, this.y + 0.07, this.z, r - 0.3, r, 40, crimson(0.0f), core(0.25f + 0.5f * prog));
                p.wall(this.x, this.y, this.y + 3.0, this.z, 0.5 + 0.3 * prog, 10, 0.0, Math.PI * 2.0, crimson(0.45f * prog), crimson(0.0f));
            } else {
                float fade = 1.0f - clamp01((t - dur) / 3.0f);
                p.annulus(this.x, this.y + 0.07, this.z, 0.0, radius * 0.8, 32, core(0.6f * fade), crimson(0.0f));
            }
        }

        private void renderPhaseShift(Painter p, float t) {
            if (t < 30.0f) {
                this.renderCharge(p, t, 5.0, 30);
                double r2 = 7.0 * (1.0 - t / 30.0f) + 0.5;
                p.annulus(this.x, this.y + 0.08, this.z, r2 - 0.25, r2, 48, crimson(0.0f), crimson(0.5f));
                return;
            }
            float local = t - 30.0f;
            this.renderPillar(p, local, 1.6, 14.0, 1.4f);
            float fade = 1.0f - clamp01(local / 16.0f);
            double r = this.size * 2.5 * easeOut(local / 10.0f);
            p.annulus(this.x, this.y + 0.06, this.z, r * 0.8, r, 64, crimson(0.0f), core(0.8f * fade));
            p.wall(this.x, this.y, this.y + 1.5, this.z, r, 64, 0.0, Math.PI * 2.0, crimson(0.5f * fade), crimson(0.0f));
        }

        private void renderPool(Painter p, float t) {
            float in = clamp01(t / 5.0f);
            float out = 1.0f - clamp01((t - (this.life - 8)) / 8.0f);
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            float pulse = 0.8f + 0.2f * Mth.sin(t * 0.5f);
            p.annulus(this.x, this.y + 0.06, this.z, 0.0, 2.6, 24, crimson(0.35f * a * pulse), crimson(0.15f * a));
            p.annulus(this.x, this.y + 0.065, this.z, 2.6, 3.0, 24, core(0.5f * a * pulse), crimson(0.0f));
            p.wall(this.x, this.y, this.y + 0.9, this.z, 2.4, 16, 0.0, Math.PI * 2.0, crimson(0.3f * a * pulse), crimson(0.0f));
        }
    }
}
