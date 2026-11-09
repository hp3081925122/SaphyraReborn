package com.hp.saphyrareborn.boss.moth.client;

import net.neoforged.api.distmarker.Dist;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.clamp01;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.easeOut;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.rgba;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.client.BossVfxRender;
import com.hp.saphyrareborn.boss.client.BossVfxRender.Painter;
import com.hp.saphyrareborn.boss.moth.MothVfx;
import com.hp.saphyrareborn.entity.MothbossEntity;
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
public final class MothVfxClient {
    private static final int MAX_EFFECTS = 96;
    private static final List<Fx> ACTIVE = new ArrayList<>();
    private static ClientLevel lastLevel;

    private MothVfxClient() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level != lastLevel) {
            ACTIVE.clear();
            MothVfx.INBOX.clear();
            lastLevel = level;
        }
        if (level == null) {
            return;
        }
        while (!MothVfx.INBOX.isEmpty()) {
            MothVfx payload = MothVfx.INBOX.poll();
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
                fx.render(painter, fx.age + partial, cam);
            }
        }
        buffers.endBatch(BossVfxRender.GLOW);
        pose.popPose();
    }

    private static ParticleOptions ghostFlame() {
        return MokelsBossfightSaphyraModParticleTypes.GHOSTLY_FLAME_PARTICLE.get();
    }

    private static float[] ivory(float a) {
        return rgba(1.0f, 0.95f, 0.84f, a);
    }

    private static float[] ghost(float a) {
        return rgba(0.38f, 0.68f, 1.0f, a);
    }

    private static float[] deep(float a) {
        return rgba(0.12f, 0.22f, 0.75f, a);
    }

    private static float[] bolt(float a) {
        return rgba(0.86f, 0.94f, 1.0f, a);
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
        private final MothbossEntity anchor;
        private final long seed;
        private double x;
        private double y;
        private double z;
        private int age;

        Fx(MothVfx p, ClientLevel level) {
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
            boolean follows = this.kind == MothVfx.WARD || this.kind == MothVfx.TWIRL || this.kind == MothVfx.WINGS || this.kind == MothVfx.CHARGE;
            this.anchor = follows ? nearestMoth(level, this.ox, this.oy, this.oz) : null;
            this.life = switch (this.kind) {
                case MothVfx.SLASH -> this.extra == MothVfx.SLASH_SPIN ? 9 : 8;
                case MothVfx.SHOCKWAVE -> Math.max(2, this.extra) + 8;
                case MothVfx.TELEGRAPH_RING -> Math.max(2, this.extra) + 4;
                case MothVfx.TELEGRAPH_LINE -> Math.max(2, this.extra) + 5;
                case MothVfx.BOLT_FALL, MothVfx.BOLT_RISE -> Math.max(2, this.extra) + 8;
                case MothVfx.LIGHTNING -> 8;
                case MothVfx.LEVITATE, MothVfx.WARD, MothVfx.TWIRL, MothVfx.WINGS -> Math.max(4, this.extra);
                case MothVfx.CHARGE -> Math.max(2, this.extra) + 3;
                case MothVfx.PHASE_SHIFT -> 52;
                case MothVfx.BLINK -> 8;
                case MothVfx.DASH_TRAIL -> 10;
                default -> 0;
            };
        }

        private static MothbossEntity nearestMoth(ClientLevel level, double x, double y, double z) {
            MothbossEntity best = null;
            double bestDist = 16.0;
            for (MothbossEntity moth : level.getEntitiesOfClass(MothbossEntity.class, new AABB(x - 4, y - 4, z - 4, x + 4, y + 4, z + 4))) {
                double d = moth.distanceToSqr(x, y, z);
                if (d < bestDist) {
                    bestDist = d;
                    best = moth;
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
                case MothVfx.SLASH -> {
                    if (this.age == 0) {
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.15f + this.size * 0.01f, 12.0);
                    }
                    if (this.age == 2) {
                        double reach = this.size * 0.8;
                        for (int i = 0; i < 5; i++) {
                            double a = this.extra == MothVfx.SLASH_SPIN ? r.nextDouble() * Math.PI * 2.0 : this.faceAngle() + (r.nextDouble() - 0.5) * 2.6;
                            level.addParticle(ParticleTypes.ELECTRIC_SPARK, this.x + Math.cos(a) * reach, this.y + 0.8 + r.nextDouble() * 1.0, this.z + Math.sin(a) * reach, 0.0, 0.05, 0.0);
                        }
                    }
                }
                case MothVfx.SHOCKWAVE -> {
                    if (this.age == 0) {
                        BossVfxRender.debris(level, r, this.x, this.y, this.z, (int) Math.min(16, 4 + this.size), 0.3);
                        for (int i = 0; i < 6; i++) {
                            level.addParticle(ghostFlame(), this.x + r.nextGaussian() * 0.5, this.y + 0.2, this.z + r.nextGaussian() * 0.5, r.nextGaussian() * 0.06, 0.15 + r.nextDouble() * 0.2, r.nextGaussian() * 0.06);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(0.9f, 0.12f + this.size * 0.05f), this.size * 2.5 + 8.0);
                    }
                }
                case MothVfx.BOLT_FALL, MothVfx.BOLT_RISE -> {
                    if (this.age == Math.max(2, this.extra)) {
                        double py = this.kind == MothVfx.BOLT_FALL ? this.y + 0.2 : this.y + 4.0;
                        for (int i = 0; i < 6; i++) {
                            level.addParticle(ParticleTypes.ELECTRIC_SPARK, this.x + r.nextGaussian() * 0.4, py + r.nextDouble(), this.z + r.nextGaussian() * 0.4, r.nextGaussian() * 0.15, 0.1 + r.nextDouble() * 0.15, r.nextGaussian() * 0.15);
                        }
                        level.addParticle(ghostFlame(), this.x, py, this.z, 0.0, 0.08, 0.0);
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.2f, 10.0);
                    }
                }
                case MothVfx.LIGHTNING -> {
                    if (this.age == 0) {
                        level.addParticle(net.minecraft.core.particles.ColorParticleOption.create(ParticleTypes.FLASH, 0xFFFFFF), this.x, this.y + 1.0, this.z, 0.0, 0.0, 0.0);
                        for (int i = 0; i < 10; i++) {
                            level.addParticle(ParticleTypes.ELECTRIC_SPARK, this.x + r.nextGaussian() * 0.6, this.y + 0.3 + r.nextDouble() * 1.5, this.z + r.nextGaussian() * 0.6, r.nextGaussian() * 0.25, r.nextDouble() * 0.25, r.nextGaussian() * 0.25);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(1.2f, 0.25f + this.size * 0.03f), this.size * 1.5 + 10.0);
                    }
                }
                case MothVfx.LEVITATE -> {
                    if (this.age % 4 == 0) {
                        level.addParticle(ParticleTypes.END_ROD, this.x + r.nextGaussian() * 0.5, this.y + 0.2, this.z + r.nextGaussian() * 0.5, 0.0, 0.25, 0.0);
                    }
                }
                case MothVfx.CHARGE -> {
                    if (this.age % 2 == 0 && this.age < this.life - 3) {
                        double a = r.nextDouble() * Math.PI * 2.0;
                        double d = this.size * 1.3;
                        double px = this.x + Math.cos(a) * d, py = this.y + 0.6 + r.nextDouble() * 2.2, pz = this.z + Math.sin(a) * d;
                        level.addParticle(ghostFlame(), px, py, pz, (this.x - px) * 0.09, (this.y + 1.8 - py) * 0.09, (this.z - pz) * 0.09);
                    }
                }
                case MothVfx.PHASE_SHIFT -> {
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
                case MothVfx.BLINK -> {
                    if (this.age == 0) {
                        for (int i = 0; i < 10; i++) {
                            level.addParticle(ParticleTypes.END_ROD, this.x + r.nextGaussian() * 0.3, this.y + r.nextDouble() * 3.0, this.z + r.nextGaussian() * 0.3, r.nextGaussian() * 0.12, r.nextGaussian() * 0.06, r.nextGaussian() * 0.12);
                        }
                        if (this.extra == 1) {
                            BossVfxRender.addShake(this.x, this.y, this.z, 0.35f, 12.0);
                        }
                    }
                }
                case MothVfx.DASH_TRAIL -> {
                    if (this.age == 0) {
                        for (int i = 0; i < 4; i++) {
                            level.addParticle(ParticleTypes.CLOUD, this.x + r.nextGaussian() * 0.3, this.y + 0.2, this.z + r.nextGaussian() * 0.3, r.nextGaussian() * 0.05, 0.03, r.nextGaussian() * 0.05);
                        }
                    }
                }
                case MothVfx.WARD -> {
                    if (this.age % 5 == 0) {
                        double a = this.age * 0.08 + r.nextInt(6) * Math.PI / 3.0;
                        level.addParticle(ParticleTypes.END_ROD, this.x + Math.cos(a) * this.size, this.y + 1.5, this.z + Math.sin(a) * this.size, 0.0, 0.02, 0.0);
                    }
                }
                case MothVfx.TWIRL -> {
                    if (this.age % 3 == 0) {
                        double rad = this.twirlRadius(this.age);
                        double a = this.age * 0.9 + r.nextDouble() * Math.PI * 2.0;
                        level.addParticle(ParticleTypes.ELECTRIC_SPARK, this.x + Math.cos(a) * rad, this.y + 1.0, this.z + Math.sin(a) * rad, 0.0, 0.05, 0.0);
                    }
                }
                case MothVfx.WINGS -> {
                    if (this.age % 4 == 0) {
                        double side = r.nextBoolean() ? 1.0 : -1.0;
                        double sx = this.fz * side, sz = -this.fx * side;
                        double d = 1.0 + r.nextDouble() * this.size;
                        level.addParticle(ParticleTypes.END_ROD, this.x + sx * d - this.fx * 0.8, this.y + 2.0 + r.nextDouble() * 2.0, this.z + sz * d - this.fz * 0.8, 0.0, -0.04, 0.0);
                    }
                }
                default -> {
                }
            }
        }

        void render(Painter p, float t, Vec3 cam) {
            switch (this.kind) {
                case MothVfx.SLASH -> this.renderSlash(p, t);
                case MothVfx.SHOCKWAVE -> this.renderShockwave(p, t);
                case MothVfx.TELEGRAPH_RING -> this.renderTelegraphRing(p, t);
                case MothVfx.TELEGRAPH_LINE -> this.renderTelegraphLine(p, t);
                case MothVfx.BOLT_FALL -> this.renderBolt(p, t, true);
                case MothVfx.BOLT_RISE -> this.renderBolt(p, t, false);
                case MothVfx.LIGHTNING -> this.renderLightning(p, t);
                case MothVfx.LEVITATE -> this.renderLevitate(p, t);
                case MothVfx.CHARGE -> this.renderCharge(p, t, this.size, Math.max(2, this.extra));
                case MothVfx.PHASE_SHIFT -> this.renderPhaseShift(p, t);
                case MothVfx.BLINK -> this.renderBlink(p, t, cam);
                case MothVfx.DASH_TRAIL -> this.renderDash(p, t);
                case MothVfx.WARD -> this.renderWard(p, t);
                case MothVfx.TWIRL -> this.renderTwirl(p, t);
                case MothVfx.WINGS -> this.renderWings(p, t);
                default -> {
                }
            }
        }

        private void renderSlash(Painter p, float t) {
            boolean spin = this.extra == MothVfx.SLASH_SPIN;
            float reveal = easeOut(t / (spin ? 4.0f : 3.0f));
            float fade = 1.0f - clamp01((t - (spin ? 4.0f : 3.0f)) / 4.5f);
            if (fade <= 0.0f) {
                return;
            }
            double outer = this.size * 0.95;
            double inner = this.size * (spin ? 0.7 : 0.58);
            boolean overhead = this.extra == MothVfx.SLASH_OVERHEAD;
            double dir = this.extra == MothVfx.SLASH_RIGHT ? -1.0 : 1.0;
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
                p.quad(i0[0], i0[1], i0[2], deep(0.0f), o0[0], o0[1], o0[2], ghost(w0 * 0.8f), o1[0], o1[1], o1[2], ghost(w1 * 0.8f), i1[0], i1[1], i1[2], deep(0.0f));
                double[] e0 = this.slashPoint(a0, outer * 1.05, overhead, spin, dir);
                double[] e1 = this.slashPoint(a1, outer * 1.05, overhead, spin, dir);
                p.quad(o0[0], o0[1], o0[2], ivory(w0), e0[0], e0[1], e0[2], ghost(0.0f), e1[0], e1[1], e1[2], ghost(0.0f), o1[0], o1[1], o1[2], ivory(w1));
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

        private void renderShockwave(Painter p, float t) {
            int ticks = Math.max(2, this.extra);
            float prog = easeOut(t / ticks);
            float fade = 1.0f - clamp01((t - ticks) / 7.0f);
            double r = this.size * prog;
            double w = Math.max(0.3, this.size * 0.1);
            int segs = (int) Math.min(64, Math.max(16, this.size * 4.0));
            p.annulus(this.x, this.y + 0.06, this.z, Math.max(0.0, r - w), r, segs, deep(0.0f), ghost(0.85f * fade));
            p.annulus(this.x, this.y + 0.065, this.z, r, r + w * 0.4, segs, ivory(0.9f * fade), ghost(0.0f));
            p.annulus(this.x, this.y + 0.05, this.z, 0.0, r, segs, deep(0.15f * fade), deep(0.04f * fade));
            double h = 1.1 * (1.0 - prog) + 0.25;
            p.wall(this.x, this.y, this.y + h, this.z, r, segs, 0.0, Math.PI * 2.0, ghost(0.5f * fade), ghost(0.0f));
        }

        private void renderTelegraphRing(Painter p, float t) {
            int dur = Math.max(2, this.extra);
            float pulse = 0.75f + 0.25f * Mth.sin(t * 0.8f);
            int segs = (int) Math.min(72, Math.max(20, this.size * 4.0));
            if (t <= dur) {
                p.annulus(this.x, this.y + 0.07, this.z, this.size - 0.2, this.size, segs, ghost(0.5f * pulse), ghost(0.75f * pulse));
                double inner = this.size * clamp01(t / dur);
                p.annulus(this.x, this.y + 0.06, this.z, 0.0, inner, segs, deep(0.05f), deep(0.22f));
                p.annulus(this.x, this.y + 0.065, this.z, Math.max(0.0, inner - 0.22), inner, segs, ivory(0.0f), ivory(0.55f));
            } else {
                float flash = 1.0f - clamp01((t - dur) / 4.0f);
                p.annulus(this.x, this.y + 0.07, this.z, 0.0, this.size, segs, ivory(0.25f * flash), ghost(0.6f * flash));
            }
        }

        private void renderTelegraphLine(Painter p, float t) {
            int dur = Math.max(2, this.extra);
            float a = t <= dur ? 0.4f + 0.2f * Mth.sin(t * 1.2f) : 0.55f * (1.0f - clamp01((t - dur) / 5.0f));
            double sx = this.fz, sz = -this.fx;
            double w = 0.8;
            double len = this.size + 1.0;
            double fill = len * clamp01(t / dur);
            p.quad(this.x + sx * w, this.y + 0.07, this.z + sz * w, deep(0.1f), this.x - sx * w, this.y + 0.07, this.z - sz * w, deep(0.1f),
                    this.x - sx * w + this.fx * len, this.y + 0.07, this.z - sz * w + this.fz * len, ghost(a), this.x + sx * w + this.fx * len, this.y + 0.07, this.z + sz * w + this.fz * len, ghost(a));
            p.quad(this.x + sx * 0.15, this.y + 0.075, this.z + sz * 0.15, ivory(a), this.x - sx * 0.15, this.y + 0.075, this.z - sz * 0.15, ivory(a),
                    this.x - sx * 0.15 + this.fx * fill, this.y + 0.075, this.z - sz * 0.15 + this.fz * fill, ivory(a * 1.4f), this.x + sx * 0.15 + this.fx * fill, this.y + 0.075, this.z + sz * 0.15 + this.fz * fill, ivory(a * 1.4f));
            for (int i = 1; i <= 3; i++) {
                double d = len * i / 4.0;
                double cx = this.x + this.fx * d, cz = this.z + this.fz * d;
                p.quad(cx + sx * w, this.y + 0.08, cz + sz * w, ghost(0.0f), cx + this.fx * 0.9, this.y + 0.08, cz + this.fz * 0.9, ivory(a),
                        cx - sx * w, this.y + 0.08, cz - sz * w, ghost(0.0f), cx + this.fx * 0.5, this.y + 0.08, cz + this.fz * 0.5, ivory(a * 0.6f));
            }
        }

        private void renderBolt(Painter p, float t, boolean falling) {
            int dur = Math.max(2, this.extra);
            double height = falling ? 9.0 : this.size;
            double ground = falling ? this.y : this.y;
            if (t < dur) {
                float k = clamp01(t / dur);
                double tip = falling ? ground + height * (1.0 - k) : ground + height * k;
                double tail = falling ? tip + 3.0 : tip - 3.0;
                p.beam(this.x, tail, this.z, this.x, tip, this.z, 0.9, ivory(0.95f), ghost(0.0f));
                p.beam(this.x, tail, this.z, this.x, tip, this.z, 0.3, bolt(1.0f), bolt(0.4f));
                if (falling) {
                    double r = this.size * (0.6 + 0.4 * k);
                    p.annulus(this.x, ground + 0.07, this.z, r - 0.15, r, 24, ghost(0.3f + 0.4f * k), ghost(0.6f + 0.3f * k));
                    p.annulus(this.x, ground + 0.06, this.z, 0.0, r, 24, deep(0.25f * k), deep(0.1f));
                }
                return;
            }
            float local = t - dur;
            float fade = 1.0f - clamp01(local / 8.0f);
            if (fade <= 0.0f) {
                return;
            }
            double base = falling ? ground : ground + height * 0.35;
            double top = falling ? ground + height * 0.6 * fade : ground + height;
            p.beam(this.x, base, this.z, this.x, top, this.z, 1.2 * fade, bolt(0.9f * fade), ghost(0.0f));
            double r = this.size * (1.0 + 1.5 * easeOut(local / 4.0f));
            double ringY = falling ? ground + 0.07 : base;
            p.annulus(this.x, ringY, this.z, r * 0.7, r, 24, ghost(0.0f), ivory(0.7f * fade));
        }

        private void renderLightning(Painter p, float t) {
            float flicker = ((int) t % 2 == 0) ? 1.0f : 0.55f;
            float fade = (1.0f - clamp01(t / 8.0f)) * flicker;
            if (fade <= 0.0f) {
                return;
            }
            RandomSource r = RandomSource.create(this.seed);
            int segs = 8;
            double top = this.y + this.size;
            double px = this.x + (r.nextDouble() - 0.5) * 1.5, py = top, pz = this.z + (r.nextDouble() - 0.5) * 1.5;
            for (int i = 1; i <= segs; i++) {
                double k = (double) i / segs;
                double jitter = (1.0 - k) * Math.min(2.0, this.size * 0.12);
                double nx = i == segs ? this.x : this.x + (r.nextDouble() - 0.5) * 2.0 * jitter;
                double nz = i == segs ? this.z : this.z + (r.nextDouble() - 0.5) * 2.0 * jitter;
                double ny = top - this.size * k;
                p.beam(px, py, pz, nx, ny, nz, 1.1, ghost(0.6f * fade), ghost(0.0f));
                p.beam(px, py, pz, nx, ny, nz, 0.28, bolt(fade), bolt(0.5f * fade));
                if (i == 3 || i == 5) {
                    double bx = nx + (r.nextDouble() - 0.5) * 3.0, bz = nz + (r.nextDouble() - 0.5) * 3.0;
                    p.beam(nx, ny, nz, bx, ny - this.size * 0.18, bz, 0.18, bolt(0.7f * fade), bolt(0.2f * fade));
                }
                px = nx;
                py = ny;
                pz = nz;
            }
            double ring = 1.0 + this.size * 0.12 * easeOut(t / 4.0f);
            p.annulus(this.x, this.y + 0.07, this.z, 0.0, ring, 24, ivory(0.8f * fade), ghost(0.0f));
        }

        private void renderLevitate(Painter p, float t) {
            float fade = 1.0f - clamp01((t - (this.life - 5)) / 5.0f);
            for (int i = 0; i < 3; i++) {
                float local = (t + i * 6.0f) % 18.0f;
                double ry = this.y + local / 18.0f * 3.0;
                float a = (1.0f - local / 18.0f) * 0.7f * fade;
                double rad = this.size * (1.0 - local / 36.0f);
                p.annulus(this.x, ry, this.z, rad - 0.12, rad, 24, ghost(0.0f), ivory(a));
            }
            p.wall(this.x, this.y, this.y + 3.2, this.z, this.size * 0.9, 16, 0.0, Math.PI * 2.0, ghost(0.25f * fade), ghost(0.0f));
        }

        private void renderCharge(Painter p, float t, double radius, int dur) {
            float prog = clamp01(t / dur);
            if (t <= dur) {
                double r = radius * 1.4 * (1.0 - prog) + 0.3;
                p.annulus(this.x, this.y + 0.07, this.z, r - 0.25, r, 40, deep(0.0f), ghost(0.25f + 0.55f * prog));
                p.wall(this.x, this.y, this.y + 3.4, this.z, 0.45 + 0.3 * prog, 10, 0.0, Math.PI * 2.0, ghost(0.4f * prog), ghost(0.0f));
            } else {
                float fade = 1.0f - clamp01((t - dur) / 3.0f);
                p.annulus(this.x, this.y + 0.07, this.z, 0.0, radius * 0.8, 32, ivory(0.6f * fade), ghost(0.0f));
            }
        }

        private void renderPhaseShift(Painter p, float t) {
            if (t < 30.0f) {
                this.renderCharge(p, t, 5.0, 30);
                double r2 = 8.0 * (1.0 - t / 30.0f) + 0.5;
                p.annulus(this.x, this.y + 0.08, this.z, r2 - 0.22, r2, 48, deep(0.0f), ghost(0.55f));
                return;
            }
            float local = t - 30.0f;
            float fade = 1.0f - clamp01(local / 18.0f);
            double r = this.size * 2.8 * easeOut(local / 10.0f);
            p.annulus(this.x, this.y + 0.06, this.z, r * 0.82, r, 64, ghost(0.0f), ivory(0.85f * fade));
            p.wall(this.x, this.y, this.y + 2.0, this.z, r, 64, 0.0, Math.PI * 2.0, ghost(0.55f * fade), ghost(0.0f));
            double rise = 16.0 * easeOut(local / 4.0f);
            p.beam(this.x, this.y, this.z, this.x, this.y + rise, this.z, 2.6 * fade, ivory(0.7f * fade), ghost(0.0f));
        }

        private void renderBlink(Painter p, float t, Vec3 cam) {
            boolean arrive = this.extra == 1;
            float k = clamp01(t / 8.0f);
            float fade = 1.0f - k;
            double scale = arrive ? 0.6 + 0.6 * easeOut(t / 3.0f) : 1.0 - 0.7 * k;
            double hw = 0.75 * scale;
            double h = 3.3 * (arrive ? 1.0 : 1.0 + 0.3 * k);
            double dx = cam.x - this.x, dz = cam.z - this.z;
            double len = Math.sqrt(dx * dx + dz * dz);
            double sx = len < 1.0E-3 ? 1.0 : -dz / len, sz = len < 1.0E-3 ? 0.0 : dx / len;
            double cy = this.y + h * 0.5;
            p.quad(this.x - sx * hw, cy, this.z - sz * hw, ghost(0.0f), this.x, this.y, this.z, ivory(0.0f),
                    this.x + sx * hw, cy, this.z + sz * hw, ghost(0.0f), this.x, this.y + h, this.z, ivory(0.0f));
            p.quad(this.x - sx * hw, cy, this.z - sz * hw, ghost(0.0f), this.x, cy, this.z, ivory(0.95f * fade),
                    this.x + sx * hw, cy, this.z + sz * hw, ghost(0.0f), this.x, this.y + h, this.z, ivory(0.0f));
            p.quad(this.x - sx * hw, cy, this.z - sz * hw, ghost(0.0f), this.x, this.y, this.z, ivory(0.0f),
                    this.x + sx * hw, cy, this.z + sz * hw, ghost(0.0f), this.x, cy, this.z, ivory(0.95f * fade));
            double r = 0.6 + 2.2 * easeOut(t / 5.0f);
            p.annulus(this.x, this.y + 0.07, this.z, r * 0.75, r, 28, ghost(0.0f), ghost(0.7f * fade));
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
                p.quad(sx0, this.oy + hy, sz0, ghost(0.0f), ex, this.oy + hy - w, ez, ghost(0.0f), ex, this.oy + hy, ez, ivory(0.7f * fade), sx0, this.oy + hy, sz0, ghost(0.0f));
                p.quad(sx0, this.oy + hy, sz0, ghost(0.0f), ex, this.oy + hy, ez, ivory(0.7f * fade), ex, this.oy + hy + w, ez, ghost(0.0f), sx0, this.oy + hy, sz0, ghost(0.0f));
            }
            p.quad(sx0 + sx * 0.6, this.oy + 0.07, sz0 + sz * 0.6, deep(0.0f), sx0 - sx * 0.6, this.oy + 0.07, sz0 - sz * 0.6, deep(0.0f),
                    ex - sx * 0.6, this.oy + 0.07, ez - sz * 0.6, ghost(0.5f * fade), ex + sx * 0.6, this.oy + 0.07, ez + sz * 0.6, ghost(0.5f * fade));
        }

        private void renderWard(Painter p, float t) {
            float in = clamp01(t / 5.0f);
            float out = 1.0f - clamp01((t - (this.life - 5)) / 5.0f);
            float a = in * out * (0.8f + 0.2f * Mth.sin(t * 0.5f));
            if (a <= 0.0f) {
                return;
            }
            double rad = this.size;
            double spin = t * 0.06;
            for (int i = 0; i < 6; i++) {
                double ang = spin + i * Math.PI / 3.0;
                double cx = this.x + Math.cos(ang) * rad, cz = this.z + Math.sin(ang) * rad;
                double tx = -Math.sin(ang), tz = Math.cos(ang);
                double cy = this.y + 1.5 + 0.2 * Math.sin(t * 0.3 + i);
                p.quad(cx, cy + 0.9, cz, ivory(0.0f), cx + tx * 0.45, cy, cz + tz * 0.45, ghost(0.6f * a), cx, cy - 0.9, cz, ivory(0.0f), cx - tx * 0.45, cy, cz - tz * 0.45, ghost(0.6f * a));
                p.quad(cx, cy + 0.45, cz, ivory(0.0f), cx + tx * 0.2, cy, cz + tz * 0.2, ivory(0.9f * a), cx, cy - 0.45, cz, ivory(0.0f), cx - tx * 0.2, cy, cz - tz * 0.2, ivory(0.9f * a));
            }
            p.annulus(this.x, this.y + 0.07, this.z, rad - 0.15, rad + 0.05, 48, ghost(0.6f * a), ghost(0.2f * a));
            p.annulus(this.x, this.y + 0.07, this.z, rad * 0.55, rad * 0.6, 48, ivory(0.4f * a), ivory(0.4f * a));
            p.wall(this.x, this.y + 0.1, this.y + 2.8, this.z, rad, 32, 0.0, Math.PI * 2.0, ghost(0.18f * a), ghost(0.0f));
        }

        private double twirlRadius(float t) {
            return 2.5 + (this.size - 2.5) * clamp01(t / Math.max(1, this.extra));
        }

        private void renderTwirl(Painter p, float t) {
            float in = clamp01(t / 6.0f);
            float out = 1.0f - clamp01((t - (this.life - 4)) / 4.0f);
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            double rad = this.twirlRadius(t);
            double spin = t * 0.75;
            for (int i = 0; i < 3; i++) {
                double from = spin + i * Math.PI * 2.0 / 3.0;
                p.arcBand(this.x, this.y + 1.0, this.z, rad * 0.55, rad, from, from + 1.1, 10, deep(0.0f), ghost(0.55f * a));
                p.arcBand(this.x, this.y + 1.01, this.z, rad * 0.95, rad * 1.04, from, from + 1.1, 10, ivory(0.85f * a), ghost(0.0f));
            }
            p.annulus(this.x, this.y + 0.07, this.z, rad - 0.12, rad, 40, ghost(0.4f * a), ghost(0.5f * a));
        }

        private void renderWings(Painter p, float t) {
            float in = easeOut(t / 8.0f);
            float out = 1.0f - clamp01((t - (this.life - 8)) / 8.0f);
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            double span = this.size * in;
            double flap = 0.35 + 0.25 * Math.sin(t * 0.35);
            double bx = this.x - this.fx * 0.7, bz = this.z - this.fz * 0.7, by = this.y + 2.4;
            for (int side = -1; side <= 1; side += 2) {
                double sx = this.fz * side, sz = -this.fx * side;
                double back = flap;
                double[][] upper = {{0.0, 0.0}, {span * 0.55, 1.6}, {span, 2.4}, {span * 0.95, 0.9}, {span * 0.6, -0.1}};
                double[][] lower = {{0.0, -0.1}, {span * 0.55, -0.4}, {span * 0.62, -1.8}, {span * 0.3, -1.6}};
                this.wingFan(p, bx, by, bz, sx, sz, back, upper, a, 0.45f);
                this.wingFan(p, bx, by, bz, sx, sz, back, lower, a, 0.35f);
                double ex = bx + sx * span * 0.6 - this.fx * back * span * 0.6;
                double ez = bz + sz * span * 0.6 - this.fz * back * span * 0.6;
                double ey = by + 1.0;
                this.eyespot(p, ex, ey, ez, sx, sz, 0.45 * in, a);
            }
        }

        private void wingFan(Painter p, double bx, double by, double bz, double sx, double sz, double back, double[][] pts, float a, float alpha) {
            for (int i = 1; i < pts.length - 1; i++) {
                double[] p1 = this.wingPoint(bx, by, bz, sx, sz, back, pts[i]);
                double[] p2 = this.wingPoint(bx, by, bz, sx, sz, back, pts[i + 1]);
                double[] p0 = this.wingPoint(bx, by, bz, sx, sz, back, pts[0]);
                p.quad(p0[0], p0[1], p0[2], ivory(alpha * 0.9f * a), p1[0], p1[1], p1[2], ghost(alpha * a), p2[0], p2[1], p2[2], ghost(alpha * a), p0[0], p0[1], p0[2], ivory(alpha * 0.9f * a));
                double[] e1 = this.wingPoint(bx, by, bz, sx, sz, back, new double[]{pts[i][0] * 1.04, pts[i][1] * 1.04});
                double[] e2 = this.wingPoint(bx, by, bz, sx, sz, back, new double[]{pts[i + 1][0] * 1.04, pts[i + 1][1] * 1.04});
                p.quad(p1[0], p1[1], p1[2], ivory(0.8f * a), e1[0], e1[1], e1[2], ghost(0.0f), e2[0], e2[1], e2[2], ghost(0.0f), p2[0], p2[1], p2[2], ivory(0.8f * a));
            }
        }

        private double[] wingPoint(double bx, double by, double bz, double sx, double sz, double back, double[] pt) {
            double out = pt[0];
            return new double[]{bx + sx * out - this.fx * back * out, by + pt[1], bz + sz * out - this.fz * back * out};
        }

        private void eyespot(Painter p, double cx, double cy, double cz, double sx, double sz, double r, float a) {
            int segs = 16;
            for (int i = 0; i < segs; i++) {
                double a0 = Math.PI * 2.0 * i / segs, a1 = Math.PI * 2.0 * (i + 1) / segs;
                double x0 = cx + sx * Math.cos(a0) * r, y0 = cy + Math.sin(a0) * r, z0 = cz + sz * Math.cos(a0) * r;
                double x1 = cx + sx * Math.cos(a1) * r, y1 = cy + Math.sin(a1) * r, z1 = cz + sz * Math.cos(a1) * r;
                double x2 = cx + sx * Math.cos(a1) * r * 0.6, y2 = cy + Math.sin(a1) * r * 0.6, z2 = cz + sz * Math.cos(a1) * r * 0.6;
                double x3 = cx + sx * Math.cos(a0) * r * 0.6, y3 = cy + Math.sin(a0) * r * 0.6, z3 = cz + sz * Math.cos(a0) * r * 0.6;
                p.quad(x0, y0, z0, deep(0.6f * a), x1, y1, z1, deep(0.6f * a), x2, y2, z2, bolt(0.9f * a), x3, y3, z3, bolt(0.9f * a));
            }
        }
    }
}
