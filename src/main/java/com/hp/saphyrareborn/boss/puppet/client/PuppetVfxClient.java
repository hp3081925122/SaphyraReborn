package com.hp.saphyrareborn.boss.puppet.client;

import net.neoforged.api.distmarker.Dist;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.clamp01;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.easeOut;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.rgba;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.client.BossVfxRender;
import com.hp.saphyrareborn.boss.client.BossVfxRender.Painter;
import com.hp.saphyrareborn.boss.puppet.PuppetVfx;
import com.hp.saphyrareborn.entity.PuppetOfYivaNemorEntity;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class PuppetVfxClient {
    private static final int MAX_EFFECTS = 96;
    private static final List<Fx> ACTIVE = new ArrayList<>();
    private static ClientLevel lastLevel;

    private PuppetVfxClient() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level != lastLevel) {
            ACTIVE.clear();
            PuppetVfx.INBOX.clear();
            lastLevel = level;
        }
        if (level == null) {
            return;
        }
        while (!PuppetVfx.INBOX.isEmpty()) {
            PuppetVfx payload = PuppetVfx.INBOX.poll();
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
            fx.tickParticles(level, level.getRandom());
            fx.age++;
            if (fx.age > fx.life) {
                it.remove();
            }
        }
    }

    @SubscribeEvent
    public static void onRender(RenderLevelStageEvent.AfterTranslucentParticles event) {
        if (ACTIVE.isEmpty()) {
            return;
        }
        Vec3 cam = Minecraft.getInstance().gameRenderer.getMainCamera().position();
        float partial = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
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

    private static ParticleOptions redFlame() {
        return MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get();
    }

    private static float[] core(float a) {
        return rgba(1.0f, 0.93f, 0.7f, a);
    }

    private static float[] flame(float a) {
        return rgba(1.0f, 0.68f, 0.22f, a);
    }

    private static float[] ember(float a) {
        return rgba(1.0f, 0.36f, 0.1f, a);
    }

    private static float[] crimson(float a) {
        return rgba(0.5f, 0.04f, 0.03f, a);
    }

    private static final class Fx {
        private final byte kind;
        private final double ox;
        private final double oy;
        private final double oz;
        private final float size;
        private final int extra;
        private final int dur;
        private final double halfWidth;
        private final double fx;
        private final double fz;
        private final int life;
        private final PuppetOfYivaNemorEntity anchor;
        private final long seed;
        private double x;
        private double y;
        private double z;
        private int age;

        Fx(PuppetVfx p, ClientLevel level) {
            this.kind = p.kind();
            this.ox = p.x();
            this.oy = p.y();
            this.oz = p.z();
            this.x = this.ox;
            this.y = this.oy;
            this.z = this.oz;
            this.size = Math.max(0.5f, Math.min(p.size(), 40.0f));
            int raw = Math.max(0, p.extra());
            if (this.kind == PuppetVfx.TELEGRAPH_LINE) {
                this.extra = raw & 255;
                this.halfWidth = Mth.clamp((raw >> 8) / 10.0, 0.3, 6.0);
            } else {
                this.extra = Math.min(raw, 400);
                this.halfWidth = 1.0;
            }
            this.dur = Math.max(2, this.extra);
            double yawRad = Math.toRadians(p.yaw());
            this.fx = -Math.sin(yawRad);
            this.fz = Math.cos(yawRad);
            this.seed = Double.doubleToLongBits(p.x() * 31.0 + p.z()) ^ raw;
            this.anchor = this.kind == PuppetVfx.CHARGE ? nearestPuppet(level, this.ox, this.oy, this.oz) : null;
            this.life = switch (this.kind) {
                case PuppetVfx.SLASH -> 9;
                case PuppetVfx.FLAME_PILLAR -> this.extra + 18;
                case PuppetVfx.SHOCKWAVE -> this.dur + 8;
                case PuppetVfx.TELEGRAPH_RING, PuppetVfx.TELEGRAPH_LINE -> this.dur + 4;
                case PuppetVfx.CHARGE -> this.dur + 3;
                case PuppetVfx.PHASE_SHIFT -> 54;
                case PuppetVfx.DASH_TRAIL -> 10;
                case PuppetVfx.EMBER_BURST -> 16;
                case PuppetVfx.SCORCH -> Math.max(10, this.extra);
                default -> 0;
            };
        }

        private static PuppetOfYivaNemorEntity nearestPuppet(ClientLevel level, double x, double y, double z) {
            PuppetOfYivaNemorEntity best = null;
            double bestDist = 16.0;
            for (PuppetOfYivaNemorEntity puppet : level.getEntitiesOfClass(PuppetOfYivaNemorEntity.class, new AABB(x - 4, y - 4, z - 4, x + 4, y + 4, z + 4))) {
                double d = puppet.distanceToSqr(x, y, z);
                if (d < bestDist) {
                    bestDist = d;
                    best = puppet;
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
                case PuppetVfx.SLASH -> {
                    if (this.age == 0) {
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.15f + this.size * 0.015f, 12.0);
                    }
                    if (this.age == 2) {
                        double reach = this.size * 0.8;
                        for (int i = 0; i < 6; i++) {
                            double a = this.faceAngle() + (r.nextDouble() - 0.5) * 2.4;
                            level.addParticle(ParticleTypes.FLAME, this.x + Math.cos(a) * reach, this.y + 0.6 + r.nextDouble() * 1.2, this.z + Math.sin(a) * reach, 0.0, 0.04, 0.0);
                        }
                    }
                }
                case PuppetVfx.FLAME_PILLAR -> {
                    if (this.age == this.extra) {
                        for (int i = 0; i < 8; i++) {
                            double a = r.nextDouble() * Math.PI * 2.0;
                            double d = this.size * r.nextDouble();
                            level.addParticle(redFlame(), this.x + Math.cos(a) * d, this.y + 0.2, this.z + Math.sin(a) * d, 0.0, 0.15 + r.nextDouble() * 0.2, 0.0);
                        }
                        for (int i = 0; i < 3; i++) {
                            level.addParticle(ParticleTypes.LAVA, this.x + r.nextGaussian() * 0.4, this.y + 0.3, this.z + r.nextGaussian() * 0.4, 0.0, 0.0, 0.0);
                        }
                        BossVfxRender.debris(level, r, this.x, this.y, this.z, (int) Math.min(10, 3 + this.size * 1.5), 0.25);
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(0.7f, 0.15f + this.size * 0.08f), this.size * 3.0 + 10.0);
                    }
                    if (this.age > this.extra && this.age < this.extra + 10 && this.age % 2 == 0) {
                        level.addParticle(ParticleTypes.SMOKE, this.x + r.nextGaussian() * this.size * 0.3, this.y + 1.0 + r.nextDouble() * 2.5, this.z + r.nextGaussian() * this.size * 0.3, 0.0, 0.06, 0.0);
                    }
                }
                case PuppetVfx.SHOCKWAVE -> {
                    if (this.age == 0) {
                        BossVfxRender.debris(level, r, this.x, this.y, this.z, (int) Math.min(16, 4 + this.size), 0.3);
                        for (int i = 0; i < 6; i++) {
                            level.addParticle(ParticleTypes.FLAME, this.x + r.nextGaussian() * 0.5, this.y + 0.2, this.z + r.nextGaussian() * 0.5, r.nextGaussian() * 0.06, 0.12 + r.nextDouble() * 0.15, r.nextGaussian() * 0.06);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(0.9f, 0.12f + this.size * 0.05f), this.size * 2.0 + 8.0);
                    }
                }
                case PuppetVfx.CHARGE -> {
                    if (this.age % 2 == 0 && this.age < this.life - 3) {
                        double a = r.nextDouble() * Math.PI * 2.0;
                        double d = this.size * 1.3;
                        double px = this.x + Math.cos(a) * d, py = this.y + 0.6 + r.nextDouble() * 2.2, pz = this.z + Math.sin(a) * d;
                        level.addParticle(redFlame(), px, py, pz, (this.x - px) * 0.09, (this.y + 1.8 - py) * 0.09, (this.z - pz) * 0.09);
                    }
                }
                case PuppetVfx.PHASE_SHIFT -> {
                    if (this.age < 30 && this.age % 2 == 0) {
                        double a = r.nextDouble() * Math.PI * 2.0;
                        double px = this.x + Math.cos(a) * 5.0, py = this.y + 0.5 + r.nextDouble() * 3.0, pz = this.z + Math.sin(a) * 5.0;
                        level.addParticle(redFlame(), px, py, pz, (this.x - px) * 0.09, (this.y + 2.0 - py) * 0.09, (this.z - pz) * 0.09);
                    }
                    if (this.age == 30) {
                        for (int i = 0; i < 30; i++) {
                            double a = r.nextDouble() * Math.PI * 2.0;
                            double speed = 0.35 + r.nextDouble() * 0.5;
                            level.addParticle(ParticleTypes.FLAME, this.x, this.y + 0.5 + r.nextDouble() * 3.0, this.z, Math.cos(a) * speed, r.nextGaussian() * 0.05, Math.sin(a) * speed);
                        }
                        for (int i = 0; i < 6; i++) {
                            level.addParticle(ParticleTypes.LAVA, this.x + r.nextGaussian(), this.y + 1.0, this.z + r.nextGaussian(), 0.0, 0.0, 0.0);
                        }
                        BossVfxRender.debris(level, r, this.x, this.y, this.z, 18, 0.45);
                        BossVfxRender.addShake(this.x, this.y, this.z, 1.2f, 36.0);
                    }
                }
                case PuppetVfx.DASH_TRAIL -> {
                    if (this.age == 0) {
                        for (int i = 0; i < 4; i++) {
                            level.addParticle(ParticleTypes.SMOKE, this.x + r.nextGaussian() * 0.3, this.y + 0.2, this.z + r.nextGaussian() * 0.3, r.nextGaussian() * 0.05, 0.03, r.nextGaussian() * 0.05);
                        }
                    }
                }
                case PuppetVfx.EMBER_BURST -> {
                    if (this.age == 0) {
                        int n = (int) Math.min(16, 4 + this.size);
                        for (int i = 0; i < n; i++) {
                            double a = r.nextDouble() * Math.PI * 2.0;
                            double d = this.size * (0.4 + 0.6 * r.nextDouble());
                            level.addParticle(redFlame(), this.x + Math.cos(a) * d, this.y + 0.2, this.z + Math.sin(a) * d, 0.0, 0.2 + r.nextDouble() * 0.2, 0.0);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(1.0f, 0.2f + this.size * 0.05f), this.size * 2.0 + 10.0);
                    }
                }
                case PuppetVfx.SCORCH -> {
                    if (this.age % 6 == 0 && this.age < this.life * 0.6) {
                        double a = r.nextDouble() * Math.PI * 2.0;
                        double d = this.size * Math.sqrt(r.nextDouble());
                        level.addParticle(ParticleTypes.SMOKE, this.x + Math.cos(a) * d, this.y + 0.1, this.z + Math.sin(a) * d, 0.0, 0.02, 0.0);
                    }
                }
                default -> {
                }
            }
        }

        void render(Painter p, float t) {
            switch (this.kind) {
                case PuppetVfx.SLASH -> this.renderSlash(p, t);
                case PuppetVfx.FLAME_PILLAR -> this.renderPillar(p, t - this.extra);
                case PuppetVfx.SHOCKWAVE -> this.renderShockwave(p, t);
                case PuppetVfx.TELEGRAPH_RING -> this.renderTelegraphRing(p, t);
                case PuppetVfx.TELEGRAPH_LINE -> this.renderTelegraphLine(p, t);
                case PuppetVfx.CHARGE -> this.renderCharge(p, t, this.size, this.dur);
                case PuppetVfx.PHASE_SHIFT -> this.renderPhaseShift(p, t);
                case PuppetVfx.DASH_TRAIL -> this.renderDash(p, t);
                case PuppetVfx.EMBER_BURST -> this.renderEmberBurst(p, t);
                case PuppetVfx.SCORCH -> this.renderScorch(p, t);
                default -> {
                }
            }
        }

        private void renderSlash(Painter p, float t) {
            if (this.extra == PuppetVfx.SLASH_DOUBLE) {
                this.slashArc(p, t, 1.0, false, false, 1.15);
                this.slashArc(p, t, -1.0, false, false, 1.65);
                return;
            }
            boolean overhead = this.extra == PuppetVfx.SLASH_OVERHEAD;
            boolean rising = this.extra == PuppetVfx.SLASH_RISING;
            double dir = this.extra == PuppetVfx.SLASH_RIGHT ? -1.0 : 1.0;
            this.slashArc(p, t, dir, overhead, rising, 1.4);
        }

        private void slashArc(Painter p, float t, double dir, boolean overhead, boolean rising, double height) {
            float reveal = easeOut(t / 3.0f);
            float fade = 1.0f - clamp01((t - 3.0f) / 4.5f);
            if (fade <= 0.0f) {
                return;
            }
            double outer = this.size * 0.95;
            double inner = this.size * 0.55;
            double from;
            double to;
            if (overhead) {
                from = Math.toRadians(-35.0);
                to = Math.toRadians(110.0);
            } else if (rising) {
                from = Math.toRadians(110.0);
                to = Math.toRadians(-20.0);
            } else {
                from = Math.toRadians(-90.0) * dir;
                to = Math.toRadians(90.0) * dir;
            }
            boolean vertical = overhead || rising;
            double end = from + (to - from) * reveal;
            int segs = 22;
            for (int i = 0; i < segs; i++) {
                double f0 = (double) i / segs;
                double f1 = (double) (i + 1) / segs;
                double a0 = from + (end - from) * f0;
                double a1 = from + (end - from) * f1;
                float w0 = (float) (0.2 + 0.8 * f0) * fade;
                float w1 = (float) (0.2 + 0.8 * f1) * fade;
                double[] o0 = this.slashPoint(a0, outer, vertical, dir, height);
                double[] o1 = this.slashPoint(a1, outer, vertical, dir, height);
                double[] i0 = this.slashPoint(a0, inner, vertical, dir, height);
                double[] i1 = this.slashPoint(a1, inner, vertical, dir, height);
                p.quad(i0[0], i0[1], i0[2], crimson(0.0f), o0[0], o0[1], o0[2], ember(w0 * 0.85f), o1[0], o1[1], o1[2], ember(w1 * 0.85f), i1[0], i1[1], i1[2], crimson(0.0f));
                double[] e0 = this.slashPoint(a0, outer * 1.06, vertical, dir, height);
                double[] e1 = this.slashPoint(a1, outer * 1.06, vertical, dir, height);
                p.quad(o0[0], o0[1], o0[2], core(w0), e0[0], e0[1], e0[2], flame(0.0f), e1[0], e1[1], e1[2], flame(0.0f), o1[0], o1[1], o1[2], core(w1));
            }
        }

        private double[] slashPoint(double a, double radius, boolean vertical, double dir, double height) {
            if (vertical) {
                double up = Math.cos(a) * radius;
                double fw = Math.sin(a) * radius;
                return new double[]{this.x + this.fx * fw, this.y + 0.4 + Math.max(0.0, up * 0.6), this.z + this.fz * fw};
            }
            double cos = Math.cos(a);
            double sin = Math.sin(a);
            double dx = this.fx * cos + this.fz * sin;
            double dz = this.fz * cos - this.fx * sin;
            return new double[]{this.x + dx * radius, this.y + height + sin * 0.5 * dir, this.z + dz * radius};
        }

        private void renderPillar(Painter p, float t) {
            if (t < 0.0f) {
                float warm = clamp01((t + 4.0f) / 4.0f);
                p.annulus(this.x, this.y + 0.07, this.z, 0.0, this.size * 0.6, 24, ember(0.35f * warm), crimson(0.0f));
                return;
            }
            float rise = easeOut(t / 3.0f);
            float fade = 1.0f - clamp01((t - 5.0f) / 12.0f);
            if (fade <= 0.0f) {
                return;
            }
            double h = (3.0 + this.size * 0.8) * rise * (0.6 + 0.4 * fade);
            double w = this.size * 0.75 * (0.7 + 0.3 * fade);
            p.beam(this.x, this.y, this.z, this.x, this.y + h, this.z, w * 2.0, ember(0.55f * fade), crimson(0.0f));
            p.beam(this.x, this.y, this.z, this.x, this.y + h * 0.75, this.z, w * 0.8, flame(0.85f * fade), ember(0.2f * fade));
            p.beam(this.x, this.y, this.z, this.x, this.y + h * 0.5, this.z, w * 0.3, core(fade), flame(0.3f * fade));
            RandomSource r = RandomSource.create(this.seed);
            int tongues = 6;
            for (int i = 0; i < tongues; i++) {
                double ang = Math.PI * 2.0 * i / tongues + r.nextDouble() * 0.5;
                double d = this.size * (0.55 + 0.35 * r.nextDouble());
                double bx = this.x + Math.cos(ang) * d, bz = this.z + Math.sin(ang) * d;
                double th = h * (0.3 + 0.3 * r.nextDouble());
                double sway = Math.sin(t * 0.8 + i) * 0.3;
                p.beam(bx, this.y, bz, bx + Math.cos(ang) * sway, this.y + th, bz + Math.sin(ang) * sway, 0.45, flame(0.7f * fade), ember(0.0f));
            }
            double ring = this.size * (0.6 + 0.4 * easeOut(t / 4.0f));
            p.annulus(this.x, this.y + 0.08, this.z, ring * 0.82, ring, 32, ember(0.0f), core(0.75f * fade));
            p.wall(this.x, this.y, this.y + 0.8 * fade, this.z, ring, 32, 0.0, Math.PI * 2.0, ember(0.45f * fade), ember(0.0f));
        }

        private void renderShockwave(Painter p, float t) {
            float prog = easeOut(t / this.dur);
            float fade = 1.0f - clamp01((t - this.dur) / 7.0f);
            double r = this.size * prog;
            double w = Math.max(0.3, this.size * 0.08);
            int segs = (int) Math.min(72, Math.max(16, this.size * 4.0));
            p.annulus(this.x, this.y + 0.06, this.z, Math.max(0.0, r - w), r, segs, crimson(0.0f), ember(0.85f * fade));
            p.annulus(this.x, this.y + 0.065, this.z, r, r + w * 0.4, segs, core(0.9f * fade), flame(0.0f));
            p.annulus(this.x, this.y + 0.05, this.z, 0.0, r, segs, crimson(0.18f * fade), crimson(0.04f * fade));
            double h = 1.1 * (1.0 - prog) + 0.25;
            p.wall(this.x, this.y, this.y + h, this.z, r, segs, 0.0, Math.PI * 2.0, flame(0.45f * fade), flame(0.0f));
        }

        private void renderTelegraphRing(Painter p, float t) {
            float pulse = 0.75f + 0.25f * Mth.sin(t * 0.8f);
            int segs = (int) Math.min(72, Math.max(20, this.size * 4.0));
            if (t <= this.dur) {
                p.annulus(this.x, this.y + 0.07, this.z, this.size - 0.2, this.size, segs, ember(0.5f * pulse), ember(0.8f * pulse));
                double inner = this.size * clamp01(t / this.dur);
                p.annulus(this.x, this.y + 0.06, this.z, 0.0, inner, segs, crimson(0.06f), crimson(0.28f));
                p.annulus(this.x, this.y + 0.065, this.z, Math.max(0.0, inner - 0.22), inner, segs, flame(0.0f), flame(0.6f));
            } else {
                float flash = 1.0f - clamp01((t - this.dur) / 4.0f);
                p.annulus(this.x, this.y + 0.07, this.z, 0.0, this.size, segs, core(0.25f * flash), ember(0.6f * flash));
            }
        }

        private void renderTelegraphLine(Painter p, float t) {
            float a = t <= this.dur ? 0.4f + 0.2f * Mth.sin(t * 1.2f) : 0.55f * (1.0f - clamp01((t - this.dur) / 4.0f));
            double sx = this.fz, sz = -this.fx;
            double w = this.halfWidth;
            double len = this.size;
            double fill = len * clamp01(t / this.dur);
            p.quad(this.x + sx * w, this.y + 0.07, this.z + sz * w, crimson(0.12f), this.x - sx * w, this.y + 0.07, this.z - sz * w, crimson(0.12f),
                    this.x - sx * w + this.fx * len, this.y + 0.07, this.z - sz * w + this.fz * len, ember(a), this.x + sx * w + this.fx * len, this.y + 0.07, this.z + sz * w + this.fz * len, ember(a));
            double cw = Math.max(0.12, w * 0.12);
            p.quad(this.x + sx * cw, this.y + 0.075, this.z + sz * cw, flame(a), this.x - sx * cw, this.y + 0.075, this.z - sz * cw, flame(a),
                    this.x - sx * cw + this.fx * fill, this.y + 0.075, this.z - sz * cw + this.fz * fill, core(a * 1.4f), this.x + sx * cw + this.fx * fill, this.y + 0.075, this.z + sz * cw + this.fz * fill, core(a * 1.4f));
            for (double side : new double[]{1.0, -1.0}) {
                double ex = sx * w * side, ez = sz * w * side;
                double bw = Math.max(0.08, w * 0.08) * side;
                p.quad(this.x + ex, this.y + 0.08, this.z + ez, ember(a), this.x + ex - sx * bw, this.y + 0.08, this.z + ez - sz * bw, ember(a),
                        this.x + ex - sx * bw + this.fx * len, this.y + 0.08, this.z + ez - sz * bw + this.fz * len, ember(a), this.x + ex + this.fx * len, this.y + 0.08, this.z + ez + this.fz * len, ember(a));
            }
        }

        private void renderCharge(Painter p, float t, double radius, int duration) {
            float prog = clamp01(t / duration);
            if (t <= duration) {
                double r = radius * 1.4 * (1.0 - prog) + 0.3;
                p.annulus(this.x, this.y + 0.07, this.z, r - 0.25, r, 40, crimson(0.0f), ember(0.25f + 0.55f * prog));
                p.wall(this.x, this.y, this.y + 3.0, this.z, 0.45 + 0.3 * prog, 10, 0.0, Math.PI * 2.0, ember(0.4f * prog), ember(0.0f));
            } else {
                float fade = 1.0f - clamp01((t - duration) / 3.0f);
                p.annulus(this.x, this.y + 0.07, this.z, 0.0, radius * 0.8, 32, core(0.6f * fade), ember(0.0f));
            }
        }

        private void renderPhaseShift(Painter p, float t) {
            if (t < 30.0f) {
                this.renderCharge(p, t, 5.0, 30);
                double r2 = 8.0 * (1.0 - t / 30.0f) + 0.5;
                p.annulus(this.x, this.y + 0.08, this.z, r2 - 0.22, r2, 48, crimson(0.0f), ember(0.55f));
                return;
            }
            float local = t - 30.0f;
            float fade = 1.0f - clamp01(local / 20.0f);
            double r = this.size * 2.8 * easeOut(local / 10.0f);
            p.annulus(this.x, this.y + 0.06, this.z, r * 0.82, r, 64, ember(0.0f), core(0.85f * fade));
            p.wall(this.x, this.y, this.y + 2.2, this.z, r, 64, 0.0, Math.PI * 2.0, ember(0.55f * fade), ember(0.0f));
            double rise = 16.0 * easeOut(local / 4.0f);
            p.beam(this.x, this.y, this.z, this.x, this.y + rise, this.z, 3.2 * fade, flame(0.8f * fade), crimson(0.0f));
            p.beam(this.x, this.y, this.z, this.x, this.y + rise * 0.7, this.z, 1.2 * fade, core(0.9f * fade), flame(0.2f * fade));
            RandomSource rs = RandomSource.create(this.seed);
            float grow = easeOut(local / 5.0f);
            for (int i = 0; i < 12; i++) {
                double ang = Math.PI * 2.0 * i / 12 + rs.nextDouble() * 0.2;
                double d = this.size * (0.5 + 0.4 * rs.nextDouble());
                double bx = this.x + Math.cos(ang) * d, bz = this.z + Math.sin(ang) * d;
                double h = (2.0 + rs.nextDouble() * 2.5) * grow * fade;
                p.beam(bx, this.y, bz, bx + Math.cos(ang) * 0.6, this.y + h, bz + Math.sin(ang) * 0.6, 0.6, ember(0.75f * fade), crimson(0.0f));
            }
        }

        private void renderDash(Painter p, float t) {
            float fade = 1.0f - clamp01(t / 10.0f);
            if (fade <= 0.0f) {
                return;
            }
            double len = this.size;
            double sx = this.fz, sz = -this.fx;
            double ex = this.ox + this.fx * len, ez = this.oz + this.fz * len;
            double w = 0.6 * (0.5 + 0.5 * fade);
            for (double hy : new double[]{0.9, 1.9}) {
                p.quad(this.ox, this.oy + hy, this.oz, ember(0.0f), ex, this.oy + hy - w, ez, ember(0.0f), ex, this.oy + hy, ez, flame(0.7f * fade), this.ox, this.oy + hy, this.oz, ember(0.0f));
                p.quad(this.ox, this.oy + hy, this.oz, ember(0.0f), ex, this.oy + hy, ez, flame(0.7f * fade), ex, this.oy + hy + w, ez, ember(0.0f), this.ox, this.oy + hy, this.oz, ember(0.0f));
            }
            p.quad(this.ox + sx * 0.6, this.oy + 0.07, this.oz + sz * 0.6, crimson(0.0f), this.ox - sx * 0.6, this.oy + 0.07, this.oz - sz * 0.6, crimson(0.0f),
                    ex - sx * 0.6, this.oy + 0.07, ez - sz * 0.6, ember(0.5f * fade), ex + sx * 0.6, this.oy + 0.07, ez + sz * 0.6, ember(0.5f * fade));
        }

        private void renderEmberBurst(Painter p, float t) {
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
                p.beam(bx, this.y, bz, bx + Math.cos(ang) * sway, this.y + h, bz + Math.sin(ang) * sway, 0.55, ember(0.8f * fade), crimson(0.0f));
                p.beam(bx, this.y, bz, bx + Math.cos(ang) * sway, this.y + h * 0.6, bz + Math.sin(ang) * sway, 0.22, core(0.9f * fade), flame(0.3f * fade));
            }
            p.annulus(this.x, this.y + 0.06, this.z, 0.0, this.size * grow, 40, ember(0.3f * fade), crimson(0.0f));
        }

        private void renderScorch(Painter p, float t) {
            float in = clamp01(t / 3.0f);
            float out = 1.0f - clamp01((t - this.life * 0.4f) / (this.life * 0.6f));
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            float flicker = 0.8f + 0.2f * Mth.sin(t * 0.9f + (float) (this.seed & 7));
            double r = this.size;
            p.annulus(this.x, this.y + 0.03, this.z, 0.0, r, 32, crimson(0.45f * a), crimson(0.2f * a));
            p.annulus(this.x, this.y + 0.035, this.z, r * 0.86, r, 32, ember(0.0f), ember(0.6f * a * flicker));
            RandomSource rs = RandomSource.create(this.seed);
            int cracks = 7;
            for (int i = 0; i < cracks; i++) {
                double ang = Math.PI * 2.0 * i / cracks + rs.nextDouble() * 0.6;
                double len = r * (0.55 + 0.4 * rs.nextDouble());
                double mid = len * (0.4 + 0.2 * rs.nextDouble());
                double bend = (rs.nextDouble() - 0.5) * 0.6;
                double cx = Math.cos(ang), cz = Math.sin(ang);
                double mx = this.x + Math.cos(ang + bend) * mid, mz = this.z + Math.sin(ang + bend) * mid;
                double ex = this.x + cx * len, ez = this.z + cz * len;
                double sx = -cz * 0.09, sz = cx * 0.09;
                p.quad(this.x + sx, this.y + 0.04, this.z + sz, flame(0.8f * a * flicker), this.x - sx, this.y + 0.04, this.z - sz, flame(0.8f * a * flicker),
                        mx - sx, this.y + 0.04, mz - sz, ember(0.7f * a), mx + sx, this.y + 0.04, mz + sz, ember(0.7f * a));
                p.quad(mx + sx, this.y + 0.04, mz + sz, ember(0.7f * a), mx - sx, this.y + 0.04, mz - sz, ember(0.7f * a),
                        ex, this.y + 0.04, ez, crimson(0.0f), ex, this.y + 0.04, ez, crimson(0.0f));
            }
        }
    }
}
