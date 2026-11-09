package com.hp.saphyrareborn.boss.witch.client;

import static com.hp.saphyrareborn.boss.client.BossVfxRender.clamp01;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.easeOut;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.rgba;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.client.BossVfxRender;
import com.hp.saphyrareborn.boss.client.BossVfxRender.Painter;
import com.hp.saphyrareborn.boss.witch.WitchVfx;
import com.hp.saphyrareborn.entity.WitchbossEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class WitchVfxClient {
    private static final int MAX_EFFECTS = 96;
    private static final List<Fx> ACTIVE = new ArrayList<>();
    private static ClientLevel lastLevel;

    private WitchVfxClient() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level != lastLevel) {
            ACTIVE.clear();
            WitchVfx.INBOX.clear();
            lastLevel = level;
        }
        if (level == null) {
            return;
        }
        while (!WitchVfx.INBOX.isEmpty()) {
            WitchVfx payload = WitchVfx.INBOX.poll();
            if (ACTIVE.size() < MAX_EFFECTS) {
                ACTIVE.add(new Fx(payload, level));
            }
        }
        if (mc.isPaused()) {
            return;
        }
        Iterator<Fx> it = ACTIVE.iterator();
        while (it.hasNext()) {
            Fx fx = it.next();
            fx.follow(1.0f);
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
        Painter painter = new Painter(consumer, pose.last());
        for (Fx fx : ACTIVE) {
            fx.follow(partial);
            if (cam.distanceToSqr(fx.x, fx.y, fx.z) < BossVfxRender.RENDER_RANGE_SQR) {
                fx.render(painter, fx.age + partial);
            }
        }
        buffers.endBatch(BossVfxRender.GLOW);
        pose.popPose();
    }

    private static ParticleOptions ghostFlame() {
        return MokelsBossfightSaphyraModParticleTypes.GHOSTLY_FLAME_PARTICLE.get();
    }

    private static float[] moon(float a) {
        return rgba(0.92f, 0.94f, 1.0f, a);
    }

    private static float[] violet(float a) {
        return rgba(0.66f, 0.38f, 1.0f, a);
    }

    private static float[] deep(float a) {
        return rgba(0.30f, 0.08f, 0.55f, a);
    }

    private static float[] soul(float a) {
        return rgba(0.45f, 0.95f, 0.9f, a);
    }

    private static final class Fx {
        private final byte kind;
        private final double ox;
        private final double oy;
        private final double oz;
        private final float size;
        private final int extra;
        private final double fx;
        private final double fz;
        private final int life;
        private final WitchbossEntity anchor;
        private final long seed;
        private double x;
        private double y;
        private double z;
        private int age;

        Fx(WitchVfx p, ClientLevel level) {
            this.kind = p.kind();
            this.ox = p.x();
            this.oy = p.y();
            this.oz = p.z();
            this.x = this.ox;
            this.y = this.oy;
            this.z = this.oz;
            this.size = Math.max(0.5f, Math.min(p.size(), 40.0f));
            this.extra = Math.max(0, Math.min(p.extra(), 400));
            double yawRad = Math.toRadians(p.yaw());
            this.fx = -Math.sin(yawRad);
            this.fz = Math.cos(yawRad);
            this.seed = Double.doubleToLongBits(p.x() * 31.0 + p.z()) ^ p.extra();
            boolean follows = this.kind == WitchVfx.WHIRL || this.kind == WitchVfx.CHARGE;
            this.anchor = follows ? nearestWitch(level, this.ox, this.oy, this.oz) : null;
            this.life = switch (this.kind) {
                case WitchVfx.SLASH -> this.extra == WitchVfx.SLASH_SPIN ? 9 : 8;
                case WitchVfx.CLAW -> 9;
                case WitchVfx.SHOCKWAVE -> Math.max(2, this.extra) + 8;
                case WitchVfx.TELEGRAPH_RING -> Math.max(2, this.extra) + 4;
                case WitchVfx.TELEGRAPH_LINE -> Math.max(2, this.extra) + 4;
                case WitchVfx.MOON_SIGIL -> Math.max(4, this.extra) + 6;
                case WitchVfx.MOON_LANCE -> 12;
                case WitchVfx.CHARGE -> Math.max(2, this.extra) + 3;
                case WitchVfx.PHASE_SHIFT -> 54;
                case WitchVfx.DASH_TRAIL -> 10;
                case WitchVfx.WHIRL -> Math.max(4, this.extra);
                case WitchVfx.FLAME_BURST -> 16;
                default -> 0;
            };
        }

        private static WitchbossEntity nearestWitch(ClientLevel level, double x, double y, double z) {
            WitchbossEntity best = null;
            double bestDist = 16.0;
            for (WitchbossEntity witch : level.getEntitiesOfClass(WitchbossEntity.class, new AABB(x - 4, y - 4, z - 4, x + 4, y + 4, z + 4))) {
                double d = witch.distanceToSqr(x, y, z);
                if (d < bestDist) {
                    bestDist = d;
                    best = witch;
                }
            }
            return best;
        }

        void follow(float partial) {
            if (this.anchor != null && this.anchor.isAlive()) {
                Vec3 p = this.anchor.getPosition(partial);
                this.x = p.x;
                this.y = p.y;
                this.z = p.z;
            }
        }

        private double faceAngle() {
            return Math.atan2(this.fz, this.fx);
        }

        void tickParticles(ClientLevel level, RandomSource r) {
            switch (this.kind) {
                case WitchVfx.SLASH, WitchVfx.CLAW -> {
                    if (this.age == 0) {
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.15f + this.size * 0.01f, 12.0);
                    }
                    if (this.age == 2) {
                        double reach = this.size * 0.8;
                        for (int i = 0; i < 5; i++) {
                            double a = this.extra == WitchVfx.SLASH_SPIN && this.kind == WitchVfx.SLASH ? r.nextDouble() * Math.PI * 2.0 : this.faceAngle() + (r.nextDouble() - 0.5) * 2.6;
                            level.addParticle(ParticleTypes.WITCH, this.x + Math.cos(a) * reach, this.y + 0.8 + r.nextDouble(), this.z + Math.sin(a) * reach, 0.0, 0.05, 0.0);
                        }
                    }
                }
                case WitchVfx.SHOCKWAVE -> {
                    if (this.age == 0) {
                        BossVfxRender.debris(level, r, this.x, this.y, this.z, (int) Math.min(16, 4 + this.size), 0.3);
                        for (int i = 0; i < 6; i++) {
                            level.addParticle(ghostFlame(), this.x + r.nextGaussian() * 0.5, this.y + 0.2, this.z + r.nextGaussian() * 0.5, r.nextGaussian() * 0.06, 0.15 + r.nextDouble() * 0.2, r.nextGaussian() * 0.06);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(0.9f, 0.12f + this.size * 0.05f), this.size * 2.0 + 8.0);
                    }
                }
                case WitchVfx.MOON_SIGIL -> {
                    if (this.age % 3 == 0 && this.age < this.life - 5) {
                        double a = r.nextDouble() * Math.PI * 2.0;
                        level.addParticle(ParticleTypes.END_ROD, this.x + Math.cos(a) * this.size, this.y, this.z + Math.sin(a) * this.size, 0.0, -0.03, 0.0);
                    }
                }
                case WitchVfx.MOON_LANCE -> {
                    if (this.age == 0) {
                        for (int i = 0; i < 12; i++) {
                            double d = r.nextDouble() * this.size;
                            level.addParticle(ParticleTypes.END_ROD, this.x + this.fx * d + r.nextGaussian() * 0.2, this.y + 2.0 + r.nextGaussian() * 0.2, this.z + this.fz * d + r.nextGaussian() * 0.2, r.nextGaussian() * 0.03, 0.02, r.nextGaussian() * 0.03);
                        }
                        double tx = this.x + this.fx * this.size, tz = this.z + this.fz * this.size;
                        level.addParticle(ParticleTypes.FLASH, tx, this.y + 2.0, tz, 0.0, 0.0, 0.0);
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.3f, 16.0);
                    }
                }
                case WitchVfx.CHARGE -> {
                    if (this.age % 2 == 0 && this.age < this.life - 3) {
                        double a = r.nextDouble() * Math.PI * 2.0;
                        double d = this.size * 1.3;
                        double px = this.x + Math.cos(a) * d, py = this.y + 0.6 + r.nextDouble() * 2.2, pz = this.z + Math.sin(a) * d;
                        level.addParticle(ghostFlame(), px, py, pz, (this.x - px) * 0.09, (this.y + 1.8 - py) * 0.09, (this.z - pz) * 0.09);
                    }
                }
                case WitchVfx.PHASE_SHIFT -> {
                    if (this.age < 30 && this.age % 2 == 0) {
                        double a = r.nextDouble() * Math.PI * 2.0;
                        double px = this.x + Math.cos(a) * 5.0, py = this.y + 0.5 + r.nextDouble() * 3.0, pz = this.z + Math.sin(a) * 5.0;
                        level.addParticle(ghostFlame(), px, py, pz, (this.x - px) * 0.09, (this.y + 2.0 - py) * 0.09, (this.z - pz) * 0.09);
                    }
                    if (this.age == 30) {
                        for (int i = 0; i < 30; i++) {
                            double a = r.nextDouble() * Math.PI * 2.0;
                            double speed = 0.35 + r.nextDouble() * 0.5;
                            level.addParticle(ghostFlame(), this.x, this.y + 0.5 + r.nextDouble() * 3.0, this.z, Math.cos(a) * speed, r.nextGaussian() * 0.05, Math.sin(a) * speed);
                        }
                        BossVfxRender.debris(level, r, this.x, this.y, this.z, 18, 0.45);
                        BossVfxRender.addShake(this.x, this.y, this.z, 1.2f, 36.0);
                    }
                }
                case WitchVfx.DASH_TRAIL -> {
                    if (this.age == 0) {
                        for (int i = 0; i < 4; i++) {
                            level.addParticle(ParticleTypes.CLOUD, this.x + r.nextGaussian() * 0.3, this.y + 0.2, this.z + r.nextGaussian() * 0.3, r.nextGaussian() * 0.05, 0.03, r.nextGaussian() * 0.05);
                        }
                    }
                }
                case WitchVfx.WHIRL -> {
                    if (this.age % 2 == 0) {
                        double a = this.age * 0.9 + r.nextDouble() * Math.PI * 2.0;
                        level.addParticle(ParticleTypes.WITCH, this.x + Math.cos(a) * this.size, this.y + 1.0 + r.nextDouble(), this.z + Math.sin(a) * this.size, 0.0, 0.05, 0.0);
                    }
                }
                case WitchVfx.FLAME_BURST -> {
                    if (this.age == 0) {
                        int n = (int) Math.min(16, 4 + this.size);
                        for (int i = 0; i < n; i++) {
                            double a = r.nextDouble() * Math.PI * 2.0;
                            double d = this.size * (0.4 + 0.6 * r.nextDouble());
                            level.addParticle(ghostFlame(), this.x + Math.cos(a) * d, this.y + 0.2, this.z + Math.sin(a) * d, 0.0, 0.2 + r.nextDouble() * 0.2, 0.0);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(1.0f, 0.2f + this.size * 0.05f), this.size * 2.0 + 10.0);
                    }
                }
                default -> {
                }
            }
        }

        void render(Painter p, float t) {
            switch (this.kind) {
                case WitchVfx.SLASH -> this.renderSlash(p, t);
                case WitchVfx.CLAW -> this.renderClaw(p, t);
                case WitchVfx.SHOCKWAVE -> this.renderShockwave(p, t);
                case WitchVfx.TELEGRAPH_RING -> this.renderTelegraphRing(p, t);
                case WitchVfx.TELEGRAPH_LINE -> this.renderTelegraphLine(p, t);
                case WitchVfx.MOON_SIGIL -> this.renderSigil(p, t);
                case WitchVfx.MOON_LANCE -> this.renderLance(p, t);
                case WitchVfx.CHARGE -> this.renderCharge(p, t, this.size, Math.max(2, this.extra));
                case WitchVfx.PHASE_SHIFT -> this.renderPhaseShift(p, t);
                case WitchVfx.DASH_TRAIL -> this.renderDash(p, t);
                case WitchVfx.WHIRL -> this.renderWhirl(p, t);
                case WitchVfx.FLAME_BURST -> this.renderFlameBurst(p, t);
                default -> {
                }
            }
        }

        private void renderSlash(Painter p, float t) {
            boolean spin = this.extra == WitchVfx.SLASH_SPIN;
            float reveal = easeOut(t / (spin ? 4.0f : 3.0f));
            float fade = 1.0f - clamp01((t - (spin ? 4.0f : 3.0f)) / 4.5f);
            if (fade <= 0.0f) {
                return;
            }
            double outer = this.size * 0.95;
            double inner = this.size * (spin ? 0.7 : 0.58);
            boolean overhead = this.extra == WitchVfx.SLASH_OVERHEAD;
            double dir = this.extra == WitchVfx.SLASH_RIGHT ? -1.0 : 1.0;
            double from;
            double to;
            if (spin) {
                from = this.faceAngle();
                to = from + Math.PI * 2.0;
            } else if (overhead) {
                from = Math.toRadians(-35.0);
                to = Math.toRadians(110.0);
            } else {
                from = Math.toRadians(-90.0) * dir;
                to = Math.toRadians(90.0) * dir;
            }
            double end = from + (to - from) * reveal;
            int segs = spin ? 40 : 22;
            for (int i = 0; i < segs; i++) {
                double f0 = (double) i / segs;
                double f1 = (double) (i + 1) / segs;
                double a0 = from + (end - from) * f0;
                double a1 = from + (end - from) * f1;
                float w0 = (float) (0.2 + 0.8 * f0) * fade;
                float w1 = (float) (0.2 + 0.8 * f1) * fade;
                double[] o0 = this.slashPoint(a0, outer, overhead, spin, dir);
                double[] o1 = this.slashPoint(a1, outer, overhead, spin, dir);
                double[] i0 = this.slashPoint(a0, inner, overhead, spin, dir);
                double[] i1 = this.slashPoint(a1, inner, overhead, spin, dir);
                p.quad(i0[0], i0[1], i0[2], deep(0.0f), o0[0], o0[1], o0[2], violet(w0 * 0.8f), o1[0], o1[1], o1[2], violet(w1 * 0.8f), i1[0], i1[1], i1[2], deep(0.0f));
                double[] e0 = this.slashPoint(a0, outer * 1.05, overhead, spin, dir);
                double[] e1 = this.slashPoint(a1, outer * 1.05, overhead, spin, dir);
                p.quad(o0[0], o0[1], o0[2], moon(w0), e0[0], e0[1], e0[2], violet(0.0f), e1[0], e1[1], e1[2], violet(0.0f), o1[0], o1[1], o1[2], moon(w1));
            }
        }

        private double[] slashPoint(double a, double radius, boolean overhead, boolean spin, double dir) {
            if (spin) {
                return new double[]{this.x + Math.cos(a) * radius, this.y + 1.1, this.z + Math.sin(a) * radius};
            }
            if (overhead) {
                double up = Math.cos(a) * radius;
                double fw = Math.sin(a) * radius;
                return new double[]{this.x + this.fx * fw, this.y + 0.4 + Math.max(0.0, up * 0.6), this.z + this.fz * fw};
            }
            double cos = Math.cos(a);
            double sin = Math.sin(a);
            double dx = this.fx * cos + this.fz * sin;
            double dz = this.fz * cos - this.fx * sin;
            return new double[]{this.x + dx * radius, this.y + 1.4 + sin * 0.5 * dir, this.z + dz * radius};
        }

        private void renderClaw(Painter p, float t) {
            float reveal = easeOut(t / 2.5f);
            float fade = 1.0f - clamp01((t - 2.5f) / 6.0f);
            if (fade <= 0.0f) {
                return;
            }
            double dir = this.extra == 1 ? -1.0 : 1.0;
            double from = Math.toRadians(-70.0) * dir;
            double to = Math.toRadians(70.0) * dir;
            double end = from + (to - from) * reveal;
            int segs = 16;
            for (int k = 0; k < 3; k++) {
                double radius = this.size * (0.92 - 0.15 * k);
                double half = Math.max(0.06, this.size * 0.035);
                double lift = 1.0 + 0.35 * k;
                for (int i = 0; i < segs; i++) {
                    double f0 = (double) i / segs;
                    double f1 = (double) (i + 1) / segs;
                    double a0 = from + (end - from) * f0;
                    double a1 = from + (end - from) * f1;
                    float w0 = (float) Math.sin(Math.PI * f0) * fade;
                    float w1 = (float) Math.sin(Math.PI * f1) * fade;
                    double[] c0 = this.clawPoint(a0, radius, lift, dir);
                    double[] c1 = this.clawPoint(a1, radius, lift, dir);
                    p.quad(c0[0], c0[1] - half, c0[2], soul(0.0f), c0[0], c0[1], c0[2], moon(w0), c1[0], c1[1], c1[2], moon(w1), c1[0], c1[1] - half, c1[2], soul(0.0f));
                    p.quad(c0[0], c0[1], c0[2], moon(w0), c0[0], c0[1] + half * 3.0, c0[2], violet(0.0f), c1[0], c1[1] + half * 3.0, c1[2], violet(0.0f), c1[0], c1[1], c1[2], moon(w1));
                }
            }
        }

        private double[] clawPoint(double a, double radius, double lift, double dir) {
            double cos = Math.cos(a);
            double sin = Math.sin(a);
            double dx = this.fx * cos + this.fz * sin;
            double dz = this.fz * cos - this.fx * sin;
            return new double[]{this.x + dx * radius, this.y + lift - sin * 0.6 * dir, this.z + dz * radius};
        }

        private void renderShockwave(Painter p, float t) {
            int ticks = Math.max(2, this.extra);
            float prog = easeOut(t / ticks);
            float fade = 1.0f - clamp01((t - ticks) / 7.0f);
            double r = this.size * prog;
            double w = Math.max(0.3, this.size * 0.08);
            int segs = (int) Math.min(72, Math.max(16, this.size * 4.0));
            p.annulus(this.x, this.y + 0.06, this.z, Math.max(0.0, r - w), r, segs, deep(0.0f), violet(0.85f * fade));
            p.annulus(this.x, this.y + 0.065, this.z, r, r + w * 0.4, segs, moon(0.9f * fade), violet(0.0f));
            p.annulus(this.x, this.y + 0.05, this.z, 0.0, r, segs, deep(0.15f * fade), deep(0.04f * fade));
            double h = 1.1 * (1.0 - prog) + 0.25;
            p.wall(this.x, this.y, this.y + h, this.z, r, segs, 0.0, Math.PI * 2.0, soul(0.45f * fade), soul(0.0f));
        }

        private void renderTelegraphRing(Painter p, float t) {
            int dur = Math.max(2, this.extra);
            float pulse = 0.75f + 0.25f * Mth.sin(t * 0.8f);
            int segs = (int) Math.min(72, Math.max(20, this.size * 4.0));
            if (t <= dur) {
                p.annulus(this.x, this.y + 0.07, this.z, this.size - 0.2, this.size, segs, violet(0.5f * pulse), violet(0.75f * pulse));
                double inner = this.size * clamp01(t / dur);
                p.annulus(this.x, this.y + 0.06, this.z, 0.0, inner, segs, deep(0.05f), deep(0.22f));
                p.annulus(this.x, this.y + 0.065, this.z, Math.max(0.0, inner - 0.22), inner, segs, moon(0.0f), moon(0.55f));
            } else {
                float flash = 1.0f - clamp01((t - dur) / 4.0f);
                p.annulus(this.x, this.y + 0.07, this.z, 0.0, this.size, segs, moon(0.25f * flash), violet(0.6f * flash));
            }
        }

        private void renderTelegraphLine(Painter p, float t) {
            int dur = Math.max(2, this.extra);
            float a = t <= dur ? 0.4f + 0.2f * Mth.sin(t * 1.2f) : 0.55f * (1.0f - clamp01((t - dur) / 4.0f));
            double sx = this.fz, sz = -this.fx;
            double w = 1.0;
            double len = this.size;
            double fill = len * clamp01(t / dur);
            p.quad(this.x + sx * w, this.y + 0.07, this.z + sz * w, deep(0.1f), this.x - sx * w, this.y + 0.07, this.z - sz * w, deep(0.1f),
                    this.x - sx * w + this.fx * len, this.y + 0.07, this.z - sz * w + this.fz * len, violet(a), this.x + sx * w + this.fx * len, this.y + 0.07, this.z + sz * w + this.fz * len, violet(a));
            p.quad(this.x + sx * 0.15, this.y + 0.075, this.z + sz * 0.15, moon(a), this.x - sx * 0.15, this.y + 0.075, this.z - sz * 0.15, moon(a),
                    this.x - sx * 0.15 + this.fx * fill, this.y + 0.075, this.z - sz * 0.15 + this.fz * fill, moon(a * 1.4f), this.x + sx * 0.15 + this.fx * fill, this.y + 0.075, this.z + sz * 0.15 + this.fz * fill, moon(a * 1.4f));
            for (int i = 1; i <= 4; i++) {
                double d = len * i / 5.0;
                double cx = this.x + this.fx * d, cz = this.z + this.fz * d;
                p.quad(cx + sx * w, this.y + 0.08, cz + sz * w, violet(0.0f), cx + this.fx * 0.9, this.y + 0.08, cz + this.fz * 0.9, moon(a),
                        cx - sx * w, this.y + 0.08, cz - sz * w, violet(0.0f), cx + this.fx * 0.5, this.y + 0.08, cz + this.fz * 0.5, moon(a * 0.6f));
            }
        }

        private void renderSigil(Painter p, float t) {
            float in = easeOut(t / 4.0f);
            float out = 1.0f - clamp01((t - (this.life - 5)) / 5.0f);
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            double r = this.size * (0.6 + 0.4 * in);
            double spin = t * 0.08;
            p.annulus(this.x, this.y, this.z, r - 0.08, r, 40, moon(0.85f * a), violet(0.4f * a));
            p.annulus(this.x, this.y, this.z, r * 0.62, r * 0.68, 32, violet(0.7f * a), violet(0.7f * a));
            p.annulus(this.x, this.y - 0.01, this.z, 0.0, r * 0.62, 32, deep(0.25f * a), deep(0.05f * a));
            p.arcBand(this.x, this.y + 0.01, this.z, r * 0.28, r * 0.42, -spin, -spin + Math.PI * 1.25, 16, moon(0.0f), moon(0.8f * a));
            for (int i = 0; i < 8; i++) {
                double ang = spin + i * Math.PI / 4.0;
                double cx = this.x + Math.cos(ang) * r * 0.84, cz = this.z + Math.sin(ang) * r * 0.84;
                double rx = Math.cos(ang), rz = Math.sin(ang);
                double tx = -rz, tz = rx;
                double l = r * 0.13;
                double wd = r * 0.06;
                p.quad(cx + rx * l, this.y + 0.02, cz + rz * l, moon(0.0f), cx + tx * wd, this.y + 0.02, cz + tz * wd, violet(0.9f * a),
                        cx - rx * l, this.y + 0.02, cz - rz * l, moon(0.0f), cx - tx * wd, this.y + 0.02, cz - tz * wd, violet(0.9f * a));
            }
            p.wall(this.x, this.y - 1.2 * a, this.y, this.z, r * 0.65, 24, 0.0, Math.PI * 2.0, violet(0.0f), violet(0.3f * a));
        }

        private void renderLance(Painter p, float t) {
            float reveal = easeOut(t / 2.0f);
            float fade = 1.0f - clamp01((t - 2.0f) / 9.0f);
            if (fade <= 0.0f) {
                return;
            }
            double len = this.size * reveal;
            double sy = this.y + 2.0;
            double ex = this.x + this.fx * len, ez = this.z + this.fz * len;
            double width = 1.0 + 0.6 * (1.0 - fade);
            p.beam(this.x, sy, this.z, ex, sy, ez, width * 1.8, violet(0.55f * fade), violet(0.0f));
            p.beam(this.x, sy, this.z, ex, sy, ez, width * 0.6, moon(fade), moon(0.4f * fade));
            double sx = this.fz, sz = -this.fx;
            int turns = (int) Math.max(8, this.size * 1.6);
            double rad = 0.7 + 0.5 * (1.0 - fade);
            double phase = t * 0.9;
            double px = this.x + sx * rad * Math.cos(phase), py = sy + rad * Math.sin(phase), pz = this.z + sz * rad * Math.cos(phase);
            for (int i = 1; i <= turns; i++) {
                double k = (double) i / turns;
                double ang = phase + k * Math.PI * 2.0 * (this.size / 4.0);
                double d = len * k;
                double nx = this.x + this.fx * d + sx * rad * Math.cos(ang);
                double ny = sy + rad * Math.sin(ang);
                double nz = this.z + this.fz * d + sz * rad * Math.cos(ang);
                p.beam(px, py, pz, nx, ny, nz, 0.22, soul(0.8f * fade), soul(0.0f));
                px = nx;
                py = ny;
                pz = nz;
            }
            if (reveal >= 0.99f) {
                double r = 0.6 + 2.4 * easeOut((t - 2.0f) / 5.0f);
                p.annulus(ex, this.y + 0.07, ez, r * 0.7, r, 28, violet(0.0f), moon(0.7f * fade));
            }
        }

        private void renderCharge(Painter p, float t, double radius, int dur) {
            float prog = clamp01(t / dur);
            if (t <= dur) {
                double r = radius * 1.4 * (1.0 - prog) + 0.3;
                p.annulus(this.x, this.y + 0.07, this.z, r - 0.25, r, 40, deep(0.0f), violet(0.25f + 0.55f * prog));
                p.wall(this.x, this.y, this.y + 3.4, this.z, 0.45 + 0.3 * prog, 10, 0.0, Math.PI * 2.0, violet(0.4f * prog), violet(0.0f));
            } else {
                float fade = 1.0f - clamp01((t - dur) / 3.0f);
                p.annulus(this.x, this.y + 0.07, this.z, 0.0, radius * 0.8, 32, moon(0.6f * fade), violet(0.0f));
            }
        }

        private void renderPhaseShift(Painter p, float t) {
            if (t < 30.0f) {
                this.renderCharge(p, t, 5.0, 30);
                double r2 = 8.0 * (1.0 - t / 30.0f) + 0.5;
                p.annulus(this.x, this.y + 0.08, this.z, r2 - 0.22, r2, 48, deep(0.0f), violet(0.55f));
                return;
            }
            float local = t - 30.0f;
            float fade = 1.0f - clamp01(local / 20.0f);
            double r = this.size * 2.8 * easeOut(local / 10.0f);
            p.annulus(this.x, this.y + 0.06, this.z, r * 0.82, r, 64, violet(0.0f), moon(0.85f * fade));
            p.wall(this.x, this.y, this.y + 2.0, this.z, r, 64, 0.0, Math.PI * 2.0, violet(0.55f * fade), violet(0.0f));
            double rise = 18.0 * easeOut(local / 4.0f);
            p.beam(this.x, this.y, this.z, this.x, this.y + rise, this.z, 3.0 * fade, moon(0.75f * fade), violet(0.0f));
            this.crescent(p, this.x - this.fx * 0.8, this.y + 3.6, this.z - this.fz * 0.8, 2.8 * easeOut(local / 6.0f), fade);
        }

        private void crescent(Painter p, double cx, double cy, double cz, double radius, float a) {
            if (radius <= 0.05 || a <= 0.0f) {
                return;
            }
            double sx = this.fz, sz = -this.fx;
            int segs = 28;
            double from = -Math.PI * 0.8;
            double to = Math.PI * 0.8;
            for (int i = 0; i < segs; i++) {
                double a0 = from + (to - from) * i / segs;
                double a1 = from + (to - from) * (i + 1) / segs;
                double in0 = radius * (1.0 - 0.32 * Math.cos(a0 * 0.62));
                double in1 = radius * (1.0 - 0.32 * Math.cos(a1 * 0.62));
                double[] o0 = {cx + sx * Math.sin(a0) * radius, cy + Math.cos(a0) * radius, cz + sz * Math.sin(a0) * radius};
                double[] o1 = {cx + sx * Math.sin(a1) * radius, cy + Math.cos(a1) * radius, cz + sz * Math.sin(a1) * radius};
                double[] i0 = {cx + sx * Math.sin(a0) * in0, cy + Math.cos(a0) * in0, cz + sz * Math.sin(a0) * in0};
                double[] i1 = {cx + sx * Math.sin(a1) * in1, cy + Math.cos(a1) * in1, cz + sz * Math.sin(a1) * in1};
                p.quad(i0[0], i0[1], i0[2], violet(0.4f * a), o0[0], o0[1], o0[2], moon(0.95f * a), o1[0], o1[1], o1[2], moon(0.95f * a), i1[0], i1[1], i1[2], violet(0.4f * a));
            }
        }

        private void renderDash(Painter p, float t) {
            float fade = 1.0f - clamp01(t / 10.0f);
            if (fade <= 0.0f) {
                return;
            }
            double len = this.size;
            double sx = this.fz, sz = -this.fx;
            double sx0 = this.ox, sz0 = this.oz;
            double ex = sx0 + this.fx * len, ez = sz0 + this.fz * len;
            double w = 0.6 * (0.5 + 0.5 * fade);
            for (double hy : new double[]{1.0, 2.0}) {
                p.quad(sx0, this.oy + hy, sz0, violet(0.0f), ex, this.oy + hy - w, ez, violet(0.0f), ex, this.oy + hy, ez, moon(0.7f * fade), sx0, this.oy + hy, sz0, violet(0.0f));
                p.quad(sx0, this.oy + hy, sz0, violet(0.0f), ex, this.oy + hy, ez, moon(0.7f * fade), ex, this.oy + hy + w, ez, violet(0.0f), sx0, this.oy + hy, sz0, violet(0.0f));
            }
            p.quad(sx0 + sx * 0.6, this.oy + 0.07, sz0 + sz * 0.6, deep(0.0f), sx0 - sx * 0.6, this.oy + 0.07, sz0 - sz * 0.6, deep(0.0f),
                    ex - sx * 0.6, this.oy + 0.07, ez - sz * 0.6, violet(0.5f * fade), ex + sx * 0.6, this.oy + 0.07, ez + sz * 0.6, violet(0.5f * fade));
        }

        private void renderWhirl(Painter p, float t) {
            float in = clamp01(t / 5.0f);
            float out = 1.0f - clamp01((t - (this.life - 4)) / 4.0f);
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            double rad = this.size;
            double spin = t * 0.95;
            for (int i = 0; i < 4; i++) {
                double from = spin + i * Math.PI * 0.5;
                double hy = this.y + (i % 2 == 0 ? 1.0 : 1.8);
                p.arcBand(this.x, hy, this.z, rad * 0.5, rad, from, from + 0.9, 10, deep(0.0f), violet(0.55f * a));
                p.arcBand(this.x, hy + 0.01, this.z, rad * 0.94, rad * 1.04, from, from + 0.9, 10, moon(0.85f * a), violet(0.0f));
            }
            p.annulus(this.x, this.y + 0.07, this.z, rad - 0.12, rad, 40, soul(0.4f * a), soul(0.5f * a));
            p.wall(this.x, this.y + 0.1, this.y + 2.4, this.z, rad * 0.85, 32, 0.0, Math.PI * 2.0, violet(0.15f * a), violet(0.0f));
        }

        private void renderFlameBurst(Painter p, float t) {
            float fade = 1.0f - clamp01((t - 4.0f) / 12.0f);
            if (fade <= 0.0f) {
                return;
            }
            RandomSource r = RandomSource.create(this.seed);
            int n = (int) Math.min(28, 8 + this.size * 1.6);
            float grow = easeOut(t / 5.0f);
            for (int i = 0; i < n; i++) {
                double ang = Math.PI * 2.0 * i / n + r.nextDouble() * 0.3;
                double d = this.size * (0.5 + 0.5 * r.nextDouble()) * (0.6 + 0.4 * grow);
                double bx = this.x + Math.cos(ang) * d, bz = this.z + Math.sin(ang) * d;
                double h = (1.2 + r.nextDouble() * 2.2) * grow * (0.5 + 0.5 * fade);
                double sway = Math.sin(t * 0.7 + i) * 0.25;
                p.beam(bx, this.y, bz, bx + Math.cos(ang) * sway, this.y + h, bz + Math.sin(ang) * sway, 0.55, soul(0.8f * fade), soul(0.0f));
                p.beam(bx, this.y, bz, bx + Math.cos(ang) * sway, this.y + h * 0.6, bz + Math.sin(ang) * sway, 0.22, moon(0.9f * fade), violet(0.3f * fade));
            }
            p.annulus(this.x, this.y + 0.06, this.z, 0.0, this.size * grow, 40, violet(0.3f * fade), deep(0.0f));
        }
    }
}
