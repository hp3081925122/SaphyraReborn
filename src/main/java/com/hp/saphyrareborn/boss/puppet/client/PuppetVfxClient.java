package com.hp.saphyrareborn.boss.puppet.client;

import static com.hp.saphyrareborn.boss.client.BossVfxRender.BEAM_STRIP;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.CHAIN_STRIP;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.CROWN_WALL;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.GLYPH;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.IMPACT;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.PILLAR_STRIP;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.RING_BAND;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.RUNE_CIRCLE;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SCORCH_CRACKS;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SHOCKWAVE_RING;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SIGIL_RING;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SLASH_CLAW;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SLASH_HEAVY;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SPARK;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SUN;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.TELEGRAPH_STRIP;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.clamp01;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.easeOut;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.rgba;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.client.BossVfxRender;
import com.hp.saphyrareborn.boss.client.BossVfxRender.TexBatch;
import com.hp.saphyrareborn.boss.puppet.PuppetVfx;
import com.hp.saphyrareborn.entity.PuppetOfYivaNemorEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Camera;
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
import org.joml.Vector3f;

@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class PuppetVfxClient {
    private static final int MAX_EFFECTS = 128;
    private static final double INFERNO_WAVE_SPEED = 1.1;
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
        Camera camera = event.getCamera();
        Vec3 cam = camera.getPosition();
        float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
        PoseStack pose = event.getPoseStack();
        pose.pushPose();
        pose.translate(-cam.x, -cam.y, -cam.z);
        MultiBufferSource.BufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();
        TexBatch batch = new TexBatch(new Vector3f(camera.getLeftVector()), new Vector3f(camera.getUpVector()));
        for (Fx fx : ACTIVE) {
            fx.follow(partial);
            if (cam.distanceToSqr(fx.x, fx.y, fx.z) < BossVfxRender.RENDER_RANGE_SQR) {
                fx.render(batch, fx.age + partial);
            }
        }
        batch.flush(buffers, pose.last());
        pose.popPose();
    }

    private static ParticleOptions redFlame() {
        return MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get();
    }

    private static float[] core(float a) {
        return rgba(1.0f, 0.95f, 0.74f, a);
    }

    private static float[] flame(float a) {
        return rgba(1.0f, 0.68f, 0.22f, a);
    }

    private static float[] ember(float a) {
        return rgba(1.0f, 0.36f, 0.10f, a);
    }

    private static float[] crimson(float a) {
        return rgba(0.56f, 0.08f, 0.06f, a);
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
        private final int life;
        private final PuppetOfYivaNemorEntity anchor;
        private final long seed;
        private double fx;
        private double fz;
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
                this.extra = PuppetVfx.lineTicks(raw);
                this.halfWidth = Mth.clamp(PuppetVfx.lineWidth(raw), 0.3, 6.0);
            } else {
                this.extra = Math.min(raw, 400);
                this.halfWidth = 1.0;
            }
            this.dur = Math.max(2, this.extra);
            double yawRad = Math.toRadians(p.yaw());
            this.fx = -Math.sin(yawRad);
            this.fz = Math.cos(yawRad);
            this.seed = Double.doubleToLongBits(p.x() * 31.0 + p.z()) ^ raw;
            boolean follows = this.kind == PuppetVfx.CHARGE || this.kind == PuppetVfx.STRINGS || this.kind == PuppetVfx.PHASE_SHIFT;
            this.anchor = follows ? nearestPuppet(level, this.ox, this.oy, this.oz) : null;
            this.life = switch (this.kind) {
                case PuppetVfx.SLASH -> 9;
                case PuppetVfx.FLAME_PILLAR -> this.extra + 18;
                case PuppetVfx.SHOCKWAVE -> this.dur + 8;
                case PuppetVfx.TELEGRAPH_RING, PuppetVfx.TELEGRAPH_LINE -> this.dur + 3;
                case PuppetVfx.CHARGE -> this.dur + 3;
                case PuppetVfx.PHASE_SHIFT -> 56;
                case PuppetVfx.DASH_TRAIL -> 10;
                case PuppetVfx.EMBER_BURST -> 16;
                case PuppetVfx.SCORCH -> Math.max(10, this.extra);
                case PuppetVfx.STRINGS -> Math.max(8, this.extra);
                case PuppetVfx.INFERNO_WAVE -> Math.max(6, this.extra);
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
                if (this.kind == PuppetVfx.STRINGS) {
                    double bodyRad = Math.toRadians(Mth.rotLerp(partial, this.anchor.yBodyRotO, this.anchor.yBodyRot));
                    this.fx = -Math.sin(bodyRad);
                    this.fz = Math.cos(bodyRad);
                }
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
                case PuppetVfx.INFERNO_WAVE -> {
                    double wx = this.ox + this.fx * INFERNO_WAVE_SPEED * this.age;
                    double wz = this.oz + this.fz * INFERNO_WAVE_SPEED * this.age;
                    level.addParticle(redFlame(), wx + (r.nextDouble() - 0.5) * 0.8, this.oy + 0.7 + r.nextDouble() * 0.9, wz + (r.nextDouble() - 0.5) * 0.8,
                            -this.fx * 0.05, 0.04, -this.fz * 0.05);
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
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(0.75f, 0.16f + this.size * 0.08f), this.size * 3.0 + 10.0);
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

        void render(TexBatch b, float t) {
            switch (this.kind) {
                case PuppetVfx.SLASH -> this.renderSlash(b, t);
                case PuppetVfx.FLAME_PILLAR -> this.renderPillar(b, t - this.extra);
                case PuppetVfx.SHOCKWAVE -> this.renderShockwave(b, t);
                case PuppetVfx.TELEGRAPH_RING -> this.renderTelegraphRing(b, t);
                case PuppetVfx.TELEGRAPH_LINE -> this.renderTelegraphLine(b, t);
                case PuppetVfx.CHARGE -> this.renderCharge(b, t, this.size, this.dur);
                case PuppetVfx.PHASE_SHIFT -> this.renderPhaseShift(b, t);
                case PuppetVfx.DASH_TRAIL -> this.renderDash(b, t);
                case PuppetVfx.EMBER_BURST -> this.renderEmberBurst(b, t);
                case PuppetVfx.SCORCH -> this.renderScorch(b, t);
                case PuppetVfx.STRINGS -> this.renderStrings(b, t);
                case PuppetVfx.INFERNO_WAVE -> this.renderInfernoWave(b, t);
                default -> {
                }
            }
        }

        private void renderSlash(TexBatch b, float t) {
            if (this.extra == PuppetVfx.SLASH_DOUBLE) {
                this.drawSlashArc(b, t, 1.0, false, false, 1.15);
                this.drawSlashArc(b, t - 0.35f, -1.0, false, false, 1.66);
                return;
            }
            boolean overhead = this.extra == PuppetVfx.SLASH_OVERHEAD;
            boolean rising = this.extra == PuppetVfx.SLASH_RISING;
            double dir = this.extra == PuppetVfx.SLASH_RIGHT ? -1.0 : 1.0;
            this.drawSlashArc(b, t, dir, overhead, rising, 1.42);
        }

        private void drawSlashArc(TexBatch b, float t, double dir, boolean overhead, boolean rising, double height) {
            if (t < 0.0f) {
                return;
            }
            float fade = 1.0f - clamp01((t - 2.6f) / 5.0f);
            if (fade <= 0.0f) {
                return;
            }
            double outer = this.size * 0.98;
            double inner = this.size * 0.60;
            double from;
            double to;
            if (overhead) {
                from = Math.toRadians(-38.0);
                to = Math.toRadians(112.0);
            } else if (rising) {
                from = Math.toRadians(112.0);
                to = Math.toRadians(-24.0);
            } else {
                from = Math.toRadians(-95.0) * dir;
                to = Math.toRadians(95.0) * dir;
            }
            boolean vertical = overhead || rising;
            b.slashSwing(SLASH_HEAVY, this.x, this.y, this.z, this.fx, this.fz, inner, outer, from, to, t, 2.3f, 4.8f, vertical, 0.50 * dir, vertical ? 0.45 : height, 22, core(fade), ember(0.42f * fade));
            b.slashSwing(SLASH_CLAW, this.x, this.y, this.z, this.fx, this.fz, inner * 1.12, outer * 1.05, from, to, t - 0.3f, 2.3f, 4.4f, vertical, 0.50 * dir, vertical ? 0.52 : height + 0.08, 20, flame(0.85f * fade), crimson(0.28f * fade));
        }

        private void renderInfernoWave(TexBatch b, float t) {
            int maxT = Math.max(6, this.extra);
            float fade = 1.0f - clamp01((t - (maxT - 4.0f)) / 4.5f);
            if (fade <= 0.0f) {
                return;
            }
            double dist = INFERNO_WAVE_SPEED * t;
            double wx = this.ox + this.fx * dist;
            double wz = this.oz + this.fz * dist;
            double cy = this.oy + 1.22;
            double halfSpan = Math.min(2.3, Math.max(1.4, this.size * 0.88));
            double halfForward = Math.min(1.4, Math.max(0.95, this.size * 0.58));
            double roll = Math.sin(t * 0.42) * 0.2;
            b.crescentWave(wx, cy, wz, this.fx, this.fz, roll, halfForward, halfSpan, ember(0.92f * fade), core(fade));
            double wakeLen = Math.min(dist, 3.8);
            if (wakeLen > 0.25) {
                b.tiledGroundLine(TELEGRAPH_STRIP, wx - this.fx * wakeLen, this.oy + 0.06, wz - this.fz * wakeLen, this.fx, this.fz,
                        wakeLen, 0.46, 1.8, t * 0.8f, crimson(0.42f * fade), flame(0.75f * fade));
            }
            b.billboardFrameV(IMPACT, wx + this.fx * 0.28, cy, wz + this.fz * 0.28, 0.58, t * 0.25, (int) (t * 0.8f), 4, core(0.86f * fade));
            b.billboardFrameV(SPARK, wx - this.fx * 0.42, cy, wz - this.fz * 0.42, 0.48, -t * 0.3, (int) (t * 1.2f), 4, flame(0.82f * fade));
        }

        private void renderPillar(TexBatch b, float t) {
            if (t < 0.0f) {
                float warm = clamp01((t + 4.0f) / 4.0f);
                b.flat(SIGIL_RING, this.x, this.y + 0.07, this.z, this.size * 0.85, t * 0.1, ember(0.62f * warm));
                return;
            }
            float rise = easeOut(t / 3.0f);
            float fade = 1.0f - clamp01((t - 5.0f) / 13.0f);
            if (fade <= 0.0f) {
                return;
            }
            double h = (3.3 + this.size * 0.85) * rise * (0.65 + 0.35 * fade);
            double w = this.size * 0.68 * (0.72 + 0.28 * fade);
            b.pillar(this.x, this.y, this.z, w, h, t * 0.14 + (this.seed & 7), ember(0.88f * fade), core(fade));
            b.beam(BEAM_STRIP, this.x, this.y, this.z, this.x, this.y + h * 1.05, this.z, w * 0.62, 2.2, flame(0.85f * fade), core(0.95f * fade));
            RandomSource r = RandomSource.create(this.seed);
            int tongues = 4;
            for (int i = 0; i < tongues; i++) {
                double ang = Math.PI * 2.0 * i / tongues + r.nextDouble() * 0.5;
                double d = this.size * (0.45 + 0.32 * r.nextDouble());
                double bx = this.x + Math.cos(ang) * d;
                double bz = this.z + Math.sin(ang) * d;
                float grow = easeOut(clamp01((t - i * 0.35f) / 2.5f));
                double th = h * (0.34 + 0.24 * r.nextDouble()) * grow;
                if (th > 0.1) {
                    double lean = th * 0.22;
                    b.tiledStrip3D(PILLAR_STRIP, bx, this.y, bz, bx + Math.cos(ang) * lean, this.y + th, bz + Math.sin(ang) * lean, 0.48 * fade, 2.2, flame(0.82f * fade), ember(0.30f * fade));
                }
            }
            double ring = this.size * (0.65 + 0.42 * easeOut(t / 4.0f));
            b.flat(SHOCKWAVE_RING, this.x, this.y + 0.08, this.z, ring, t * 0.08, core(0.85f * fade));
            b.crownWall(CROWN_WALL, this.x, this.y, this.y + 0.78 * fade, this.z, Math.max(0.3, ring), 0.0, Math.PI * 2.0, 2.2, flame(0.55f * fade), ember(0.45f * fade));
            b.billboard(SUN, this.x, this.y + 0.65, this.z, 0.78 * fade, 0.78 * fade, t * 0.2, core(0.85f * fade));
        }

        private void renderShockwave(TexBatch b, float t) {
            float prog = easeOut(t / this.dur);
            float fade = 1.0f - clamp01((t - this.dur) / 7.5f);
            if (fade <= 0.0f) {
                return;
            }
            double r = this.size * prog;
            double bandW = Math.max(0.5, this.size * 0.18);
            b.flat(SHOCKWAVE_RING, this.x, this.y + 0.06, this.z, r, t * 0.07, ember(0.84f * fade));
            b.ringBand(RING_BAND, this.x, this.y + 0.075, this.z, Math.max(0.0, r - bandW), r + 0.18, 0.0, Math.PI * 2.0, 2.2, core(0.90f * fade), flame(0.54f * fade));
            double h = (0.92 * (1.0 - prog) + 0.22) * fade;
            b.crownWall(CROWN_WALL, this.x, this.y, this.y + h, this.z, Math.max(0.3, r), 0.0, Math.PI * 2.0, 2.4, core(0.56f * fade), ember(0.48f * fade));
        }

        private void renderTelegraphRing(TexBatch b, float t) {
            float pulse = 0.76f + 0.24f * Mth.sin(t * 0.8f);
            if (t <= this.dur) {
                float k = clamp01(t / this.dur);
                b.flat(SIGIL_RING, this.x, this.y + 0.065, this.z, this.size * 0.96, t * 0.05, ember(0.68f * pulse));
                b.flat(RUNE_CIRCLE, this.x, this.y + 0.055, this.z, this.size * k, -t * 0.08, flame(0.50f + 0.32f * k));
                b.ringBand(RING_BAND, this.x, this.y + 0.075, this.z, Math.max(0.0, this.size - 0.36), this.size, 0.0, Math.PI * 2.0, 2.2, core(0.86f * pulse), ember(0.56f * pulse));
            } else {
                float flash = 1.0f - clamp01((t - this.dur) / 3.0f);
                b.flat(SHOCKWAVE_RING, this.x, this.y + 0.08, this.z, this.size, 0.0, core(0.85f * flash));
            }
        }

        private void renderTelegraphLine(TexBatch b, float t) {
            float alpha = t <= this.dur ? 0.66f + 0.22f * Mth.sin(t * 1.15f) : 0.74f * (1.0f - clamp01((t - this.dur) / 3.0f));
            if (alpha <= 0.01f) {
                return;
            }
            double len = this.size;
            double hw = Math.min(0.85, this.halfWidth * 0.72);
            b.tiledGroundLine(TELEGRAPH_STRIP, this.x, this.y + 0.07, this.z, this.fx, this.fz, len, hw, 2.2, t * 0.65f, ember(0.62f * alpha), core(0.92f * alpha));
            double fill = len * clamp01(t / this.dur);
            b.tiledGroundLine(TELEGRAPH_STRIP, this.x, this.y + 0.08, this.z, this.fx, this.fz, fill, Math.max(0.22, hw * 0.38), 1.8, t * 0.95f, flame(0.82f * alpha), core(alpha));
            b.flat(RUNE_CIRCLE, this.x + this.fx * (len * 0.5), this.y + 0.085, this.z + this.fz * (len * 0.5), Math.min(1.3, hw * 0.85), t * 0.09, core(0.72f * alpha));
        }

        private void renderCharge(TexBatch b, float t, double radius, int duration) {
            float prog = clamp01(t / duration);
            if (t <= duration) {
                double r = radius * 1.30 * (1.0 - prog) + 0.4;
                float a = 0.35f + 0.58f * prog;
                b.flat(SIGIL_RING, this.x, this.y + 0.07, this.z, r, t * 0.08, ember(a));
                b.flat(RUNE_CIRCLE, this.x, this.y + 0.08, this.z, radius * 0.68, -t * 0.12, core(0.82f * a));
                b.pillar(this.x, this.y, this.z, 0.44 + 0.32 * prog, 2.8 * prog + 0.35, t * 0.14, ember(0.55f * prog), core(0.82f * prog));
                for (int i = 0; i < 4; i++) {
                    double ang = t * 0.14 + i * Math.PI * 0.5;
                    b.billboardFrameU(GLYPH, this.x + Math.cos(ang) * r * 0.85, this.y + 1.2 + 0.28 * Math.sin(t * 0.25 + i), this.z + Math.sin(ang) * r * 0.85, 0.36, 0.0, i + (int) (t * 0.25f), 4, core(0.85f * a));
                }
            } else {
                float fade = 1.0f - clamp01((t - duration) / 3.0f);
                b.flat(SHOCKWAVE_RING, this.x, this.y + 0.08, this.z, radius * 0.92, 0.0, core(0.80f * fade));
            }
        }

        private void renderStrings(TexBatch b, float t) {
            float in = easeOut(t / 5.0f);
            float out = 1.0f - clamp01((t - (this.life - 6.0f)) / 6.0f);
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            double topY = this.y + 10.5;
            double barHalf = Math.max(2.2, this.size * 0.85);
            double sx = this.fz;
            double sz = -this.fx;
            b.flat(SIGIL_RING, this.x, topY, this.z, barHalf * 1.12, t * 0.05, ember(0.72f * a));
            b.tiledStrip3D(CHAIN_STRIP, this.x - sx * barHalf, topY - 0.1, this.z - sz * barHalf, this.x + sx * barHalf, topY - 0.1, this.z + sz * barHalf, 0.34, 2.0, flame(0.82f * a), core(0.95f * a));
            b.tiledStrip3D(CHAIN_STRIP, this.x - this.fx * barHalf, topY - 0.1, this.z - this.fz * barHalf, this.x + this.fx * barHalf, topY - 0.1, this.z + this.fz * barHalf, 0.34, 2.0, flame(0.82f * a), core(0.95f * a));
            double[][] anchors = {
                    {0.0, 0.0, 2.9},
                    {-0.95, 0.15, 2.2},
                    {0.95, 0.15, 2.2},
                    {-1.35, 0.55, 1.55},
                    {1.35, 0.55, 1.55}
            };
            double[][] tops = {
                    {0.0, 0.0},
                    {-barHalf * 0.65, 0.0},
                    {barHalf * 0.65, 0.0},
                    {-barHalf * 0.95, barHalf * 0.45},
                    {barHalf * 0.95, barHalf * 0.45}
            };
            for (int i = 0; i < anchors.length; i++) {
                double tug = 0.12 * Math.sin(t * 0.4 + i * 1.3);
                double tx = this.x + sx * tops[i][0] + this.fx * tops[i][1];
                double tz = this.z + sz * tops[i][0] + this.fz * tops[i][1];
                double jx = this.x + sx * anchors[i][0] + this.fx * anchors[i][1];
                double jy = this.y + anchors[i][2] + tug;
                double jz = this.z + sz * anchors[i][0] + this.fz * anchors[i][1];
                b.tiledStrip3D(CHAIN_STRIP, tx, topY - 0.15, tz, jx, jy, jz, 0.28, 2.0, ember(0.82f * a), core(0.95f * a));
                b.billboardFrameV(SPARK, jx, jy, jz, 0.26, t * 0.2 + i, i + (int) (t * 0.5f), 4, core(0.90f * a));
            }
        }

        private void renderPhaseShift(TexBatch b, float t) {
            if (t < 30.0f) {
                this.renderCharge(b, t, 5.0, 30);
                double r2 = 7.8 * (1.0 - t / 30.0f) + 0.6;
                b.flat(SIGIL_RING, this.x, this.y + 0.085, this.z, r2, -t * 0.06, ember(0.68f));
                return;
            }
            float local = t - 30.0f;
            float fade = 1.0f - clamp01(local / 22.0f);
            if (fade <= 0.0f) {
                return;
            }
            double r = this.size * 2.5 * easeOut(local / 10.0f);
            b.flat(SHOCKWAVE_RING, this.x, this.y + 0.065, this.z, r, local * 0.05, core(0.90f * fade));
            b.flat(SCORCH_CRACKS, this.x, this.y + 0.045, this.z, r * 0.76, (this.seed & 7) * 0.6, flame(0.72f * fade));
            b.crownWall(CROWN_WALL, this.x, this.y, this.y + 1.55 * fade + 0.2, this.z, Math.max(0.5, r), 0.0, Math.PI * 2.0, 2.6, core(0.62f * fade), ember(0.52f * fade));
            double rise = 14.5 * easeOut(local / 4.0f);
            b.pillar(this.x, this.y, this.z, 1.95 * fade, rise, local * 0.15, ember(0.86f * fade), core(fade));
            double recoil = easeOut(local / 6.0f);
            for (int i = 0; i < 6; i++) {
                double ang = Math.PI * 2.0 * i / 6.0 + 0.3;
                double cx = Math.cos(ang), cz = Math.sin(ang);
                double lowX = this.x + cx * (1.2 + 2.8 * recoil);
                double lowZ = this.z + cz * (1.2 + 2.8 * recoil);
                b.tiledStrip3D(CHAIN_STRIP, this.x + cx * 0.6, this.y + 2.2, this.z + cz * 0.6, lowX, this.y + 4.8 + 2.5 * recoil, lowZ, 0.32 * fade, 2.0, core(fade), ember(0.62f * fade));
            }
            b.billboard(SUN, this.x, this.y + 1.6, this.z, 1.55 * fade, 1.55 * fade, local * 0.2, core(0.92f * fade));
        }

        private void renderDash(TexBatch b, float t) {
            float fade = 1.0f - clamp01(t / 10.0f);
            if (fade <= 0.0f) {
                return;
            }
            double len = this.size;
            double ex = this.ox + this.fx * len;
            double ez = this.oz + this.fz * len;
            b.tiledGroundLine(TELEGRAPH_STRIP, this.ox, this.oy + 0.07, this.oz, this.fx, this.fz, len, 0.52, 2.0, t * 0.8f, crimson(0.45f * fade), ember(0.80f * fade));
            b.tiledStrip3D(CHAIN_STRIP, this.ox, this.oy + 1.0, this.oz, ex, this.oy + 1.0, ez, 0.38 * fade, 2.0, ember(0.75f * fade), core(0.90f * fade));
            b.beam(BEAM_STRIP, this.ox, this.oy + 1.65, this.oz, ex, this.oy + 1.65, ez, 0.48 * fade, 2.2, flame(0.72f * fade), core(0.90f * fade));
        }

        private void renderEmberBurst(TexBatch b, float t) {
            float fade = 1.0f - clamp01((t - 4.0f) / 12.0f);
            if (fade <= 0.0f) {
                return;
            }
            RandomSource r = RandomSource.create(this.seed);
            int n = (int) Math.min(12, 5 + this.size * 0.9);
            float grow = easeOut(t / 5.0f);
            for (int i = 0; i < n; i++) {
                double ang = Math.PI * 2.0 * i / n + r.nextDouble() * 0.3;
                double d = this.size * (0.45 + 0.5 * r.nextDouble()) * (0.6 + 0.4 * grow);
                double bx = this.x + Math.cos(ang) * d;
                double bz = this.z + Math.sin(ang) * d;
                double h = (1.3 + r.nextDouble() * 1.9) * grow * (0.5 + 0.5 * fade);
                double lean = 0.32 * grow;
                b.tiledStrip3D(PILLAR_STRIP, bx, this.y, bz, bx + Math.cos(ang) * lean, this.y + h, bz + Math.sin(ang) * lean, 0.48 * fade, 2.0, core(0.88f * fade), ember(0.48f * fade));
            }
            b.flat(SHOCKWAVE_RING, this.x, this.y + 0.065, this.z, this.size * grow, t * 0.08, ember(0.78f * fade));
            b.flat(SCORCH_CRACKS, this.x, this.y + 0.045, this.z, this.size * 0.82 * grow, (this.seed & 7) * 0.5, flame(0.66f * fade));
        }

        private void renderScorch(TexBatch b, float t) {
            float in = clamp01(t / 3.0f);
            float out = 1.0f - clamp01((t - this.life * 0.4f) / (this.life * 0.6f));
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            float flicker = 0.82f + 0.18f * Mth.sin(t * 0.9f + (float) (this.seed & 7));
            double rot = (this.seed & 15) * 0.4;
            b.flat(SCORCH_CRACKS, this.x, this.y + 0.035, this.z, this.size, rot, ember(0.84f * a * flicker));
            b.flat(SCORCH_CRACKS, this.x, this.y + 0.042, this.z, this.size * 0.68, rot + 1.1, core(0.68f * a * flicker));
            b.ringBand(RING_BAND, this.x, this.y + 0.048, this.z, this.size * 0.84, this.size, 0.0, Math.PI * 2.0, 2.0, flame(0.50f * a * flicker), crimson(0.24f * a));
        }
    }
}
