package com.hp.saphyrareborn.boss.witch.client;

import static com.hp.saphyrareborn.boss.client.BossVfxRender.clamp01;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.easeOut;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.rgba;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.client.BossVfxRender;
import com.hp.saphyrareborn.boss.client.BossVfxRender.TexBatch;
import com.hp.saphyrareborn.boss.witch.WitchVfx;
import com.hp.saphyrareborn.entity.WitchbossEntity;
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
public final class WitchVfxClient {
    private static final int MAX_EFFECTS = 128;
    private static final double CRESCENT_SPEED = 1.2;
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

    private static ParticleOptions ghostFlame() {
        return MokelsBossfightSaphyraModParticleTypes.GHOSTLY_FLAME_PARTICLE.get();
    }

    private static float[] moon(float a) {
        return rgba(0.96f, 0.98f, 1.0f, a);
    }

    private static float[] violet(float a) {
        return rgba(0.66f, 0.36f, 1.0f, a);
    }

    private static float[] deep(float a) {
        return rgba(0.30f, 0.10f, 0.62f, a);
    }

    private static float[] soul(float a) {
        return rgba(0.40f, 0.95f, 0.94f, a);
    }

    private static final class Fx {
        private final byte kind;
        private final double ox;
        private final double oy;
        private final double oz;
        private final float size;
        private final int rawExtra;
        private final int extra;
        private final double innerRadius;
        private final double fx;
        private final double fz;
        private final double yawRad;
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
            this.rawExtra = p.extra();
            if (this.kind == WitchVfx.TELEGRAPH_RING || this.kind == WitchVfx.SHOCKWAVE) {
                this.extra = WitchVfx.ringTicks(this.rawExtra);
                this.innerRadius = WitchVfx.ringInner(this.rawExtra);
            } else {
                this.extra = Math.max(0, Math.min(this.rawExtra, 400));
                this.innerRadius = 0.0;
            }
            this.yawRad = Math.toRadians(p.yaw());
            this.fx = -Math.sin(this.yawRad);
            this.fz = Math.cos(this.yawRad);
            this.seed = Double.doubleToLongBits(p.x() * 31.0 + p.z()) ^ p.extra();
            boolean follows = this.kind == WitchVfx.WHIRL || this.kind == WitchVfx.CHARGE;
            this.anchor = follows ? nearestWitch(level, this.ox, this.oy, this.oz) : null;
            this.life = switch (this.kind) {
                case WitchVfx.SLASH -> this.extra == WitchVfx.SLASH_SPIN ? 11 : 9;
                case WitchVfx.CLAW -> 9;
                case WitchVfx.SHOCKWAVE -> Math.max(2, this.extra) + 8;
                case WitchVfx.TELEGRAPH_RING -> Math.max(2, this.extra) + 3;
                case WitchVfx.TELEGRAPH_LINE -> Math.max(2, this.extra) + 2;
                case WitchVfx.MOON_SIGIL -> Math.max(4, this.extra) + 6;
                case WitchVfx.MOON_LANCE -> 12;
                case WitchVfx.CHARGE -> Math.max(2, this.extra) + 3;
                case WitchVfx.PHASE_SHIFT -> 54;
                case WitchVfx.DASH_TRAIL -> 10;
                case WitchVfx.WHIRL -> Math.max(4, this.extra);
                case WitchVfx.FLAME_BURST -> 15;
                case WitchVfx.CRESCENT -> Math.max(6, this.extra);
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
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.16f + this.size * 0.01f, 12.0);
                    }
                    if (this.age == 2) {
                        double reach = this.size * 0.8;
                        for (int i = 0; i < 6; i++) {
                            double a = this.extra == WitchVfx.SLASH_SPIN && this.kind == WitchVfx.SLASH ? r.nextDouble() * Math.PI * 2.0 : this.faceAngle() + (r.nextDouble() - 0.5) * 2.6;
                            level.addParticle(ParticleTypes.WITCH, this.x + Math.cos(a) * reach, this.y + 0.8 + r.nextDouble(), this.z + Math.sin(a) * reach, 0.0, 0.05, 0.0);
                        }
                    }
                }
                case WitchVfx.CRESCENT -> {
                    double wx = this.ox + this.fx * CRESCENT_SPEED * this.age;
                    double wz = this.oz + this.fz * CRESCENT_SPEED * this.age;
                    level.addParticle(ParticleTypes.END_ROD, wx + (r.nextDouble() - 0.5) * 0.8, this.oy + 0.9 + r.nextDouble() * 0.8, wz + (r.nextDouble() - 0.5) * 0.8,
                            -this.fx * 0.05, 0.02, -this.fz * 0.05);
                }
                case WitchVfx.SHOCKWAVE -> {
                    if (this.age == 0) {
                        if (this.innerRadius <= 0.1) {
                            BossVfxRender.debris(level, r, this.x, this.y, this.z, (int) Math.min(16, 4 + this.size), 0.3);
                        }
                        for (int i = 0; i < 8; i++) {
                            double a = r.nextDouble() * Math.PI * 2.0;
                            double rad = this.innerRadius + r.nextDouble() * Math.max(0.8, this.size - this.innerRadius);
                            level.addParticle(ghostFlame(), this.x + Math.cos(a) * rad, this.y + 0.2, this.z + Math.sin(a) * rad, r.nextGaussian() * 0.06, 0.15 + r.nextDouble() * 0.2, r.nextGaussian() * 0.06);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(0.95f, 0.14f + this.size * 0.045f), this.size * 2.0 + 9.0);
                    }
                }
                case WitchVfx.MOON_SIGIL -> {
                    if (this.age % 3 == 0 && this.age < this.life - 5) {
                        double a = r.nextDouble() * Math.PI * 2.0;
                        level.addParticle(ParticleTypes.END_ROD, this.x + Math.cos(a) * this.size * 0.85, this.y, this.z + Math.sin(a) * this.size * 0.85, 0.0, -0.03, 0.0);
                    }
                }
                case WitchVfx.MOON_LANCE -> {
                    if (this.age == 0) {
                        for (int i = 0; i < 10; i++) {
                            double d = 1.0 + r.nextDouble() * (this.size - 1.0);
                            level.addParticle(ParticleTypes.END_ROD, this.x + this.fx * d + r.nextGaussian() * 0.16, this.y + 1.45 + r.nextGaussian() * 0.16, this.z + this.fz * d + r.nextGaussian() * 0.16, r.nextGaussian() * 0.03, 0.02, r.nextGaussian() * 0.03);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.30f, 16.0);
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
                        for (int i = 0; i < 5; i++) {
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
                        int n = (int) Math.min(14, 4 + this.size);
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

        void render(TexBatch b, float t) {
            switch (this.kind) {
                case WitchVfx.SLASH -> this.renderSlash(b, t);
                case WitchVfx.CLAW -> this.renderClaw(b, t);
                case WitchVfx.CRESCENT -> this.renderCrescent(b, t);
                case WitchVfx.SHOCKWAVE -> this.renderShockwave(b, t);
                case WitchVfx.TELEGRAPH_RING -> this.renderTelegraphRing(b, t);
                case WitchVfx.TELEGRAPH_LINE -> this.renderTelegraphLine(b, t);
                case WitchVfx.MOON_SIGIL -> this.renderSigil(b, t);
                case WitchVfx.MOON_LANCE -> this.renderLance(b, t);
                case WitchVfx.CHARGE -> this.renderCharge(b, t, this.size, Math.max(2, this.extra));
                case WitchVfx.PHASE_SHIFT -> this.renderPhaseShift(b, t);
                case WitchVfx.DASH_TRAIL -> this.renderDash(b, t);
                case WitchVfx.WHIRL -> this.renderWhirl(b, t);
                case WitchVfx.FLAME_BURST -> this.renderFlameBurst(b, t);
                default -> {
                }
            }
        }

        private void renderSlash(TexBatch b, float t) {
            boolean spin = this.extra == WitchVfx.SLASH_SPIN;
            float fade = 1.0f - clamp01((t - (spin ? 3.5f : 2.5f)) / 5.5f);
            if (fade <= 0.0f) {
                return;
            }
            if (spin) {
                for (int k = 0; k < 2; k++) {
                    double base = k * Math.PI + t * 0.34;
                    b.slashSwing(BossVfxRender.SLASH_HEAVY, this.x, this.y, this.z, Math.cos(base), Math.sin(base),
                            this.size * 0.64, this.size * 0.98, -Math.PI * 0.72, Math.PI * 0.72,
                            t, 3.2f, 5.0f, false, 0.14, 1.15 + k * 0.18, 20, moon(0.96f * fade), violet(0.55f * fade));
                }
                b.flat(BossVfxRender.MOON_SIGIL, this.x, this.y + 0.06, this.z, this.size * 0.86, -t * 0.09, soul(0.55f * fade));
                return;
            }
            boolean overhead = this.extra == WitchVfx.SLASH_OVERHEAD;
            double dir = this.extra == WitchVfx.SLASH_RIGHT ? -1.0 : 1.0;
            double from = overhead ? Math.toRadians(-42.0) : Math.toRadians(-96.0) * dir;
            double to = overhead ? Math.toRadians(116.0) : Math.toRadians(92.0) * dir;
            b.slashSwing(BossVfxRender.SLASH_HEAVY, this.x, this.y, this.z, this.fx, this.fz,
                    this.size * 0.60, this.size * 0.98, from, to, t, 2.3f, 4.8f,
                    overhead, 0.50 * dir, overhead ? 0.48 : 1.38, 20,
                    moon(0.98f * fade), violet(0.55f * fade));
            b.slashSwing(BossVfxRender.SLASH_HEAVY, this.x, this.y, this.z, this.fx, this.fz,
                    this.size * 0.76, this.size * 1.04, from, to, t - 0.35f, 2.3f, 4.4f,
                    overhead, 0.50 * dir, overhead ? 0.52 : 1.42, 16,
                    soul(0.82f * fade), deep(0.32f * fade));
        }

        private void renderClaw(TexBatch b, float t) {
            float fade = 1.0f - clamp01((t - 2.3f) / 5.5f);
            if (fade <= 0.0f) {
                return;
            }
            double dir = this.extra == 1 ? -1.0 : 1.0;
            double from = Math.toRadians(-78.0) * dir;
            double to = Math.toRadians(78.0) * dir;
            b.slashSwing(BossVfxRender.SLASH_CLAW, this.x, this.y, this.z, this.fx, this.fz,
                    this.size * 0.56, this.size * 0.98, from, to, t, 2.1f, 4.8f,
                    false, -0.58 * dir, 1.44, 18,
                    moon(0.98f * fade), violet(0.65f * fade));
            b.slashSwing(BossVfxRender.SLASH_CLAW, this.x, this.y, this.z, this.fx, this.fz,
                    this.size * 0.66, this.size * 1.03, from, to, t - 0.3f, 2.1f, 4.4f,
                    false, -0.44 * dir, 1.18, 16,
                    soul(0.80f * fade), deep(0.35f * fade));
        }

        private void renderCrescent(TexBatch b, float t) {
            float in = easeOut(t / 2.0f);
            float fade = 1.0f - clamp01((t - (this.life - 4.0f)) / 4.0f);
            float a = in * fade;
            if (a <= 0.0f) {
                return;
            }
            double dist = CRESCENT_SPEED * t;
            double wx = this.ox + this.fx * dist;
            double wz = this.oz + this.fz * dist;
            b.crescentWave(wx, this.oy + 1.32, wz, this.fx, this.fz, 0.20 * Mth.sin(t * 0.5f),
                    1.15, Math.min(2.2, this.size * 0.88), violet(0.88f * a), moon(0.98f * a));
            b.billboardFrameV(BossVfxRender.STAR, wx + this.fx * 0.25, this.oy + 1.32, wz + this.fz * 0.25,
                    0.48, t * 0.28, (int) (t * 0.6f), 4, soul(0.85f * a));
            double trailLen = Math.min(dist, 3.6);
            if (trailLen > 0.4) {
                b.tiledGroundLine(BossVfxRender.TELEGRAPH_STRIP, wx - this.fx * trailLen, this.oy + 0.05, wz - this.fz * trailLen,
                        this.fx, this.fz, trailLen, 0.48, 2.0, t * 0.75f, deep(0.35f * a), soul(0.62f * a));
            }
        }

        private void renderShockwave(TexBatch b, float t) {
            int ticks = Math.max(2, this.extra);
            float prog = easeOut(t / ticks);
            float fade = 1.0f - clamp01((t - ticks) / 7.5f);
            if (fade <= 0.0f) {
                return;
            }
            double startR = this.innerRadius;
            double r = startR + (this.size - startR) * prog;
            double bandW = Math.max(0.65, (this.size - startR) * 0.18);
            b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.065, this.z, Math.max(startR, r - bandW), r + 0.18,
                    0.0, Math.PI * 2.0, 2.4, moon(0.92f * fade), violet(0.68f * fade));
            double wallH = (0.92 * (1.0 - prog * 0.55) + 0.22) * fade;
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y, this.y + wallH, this.z, Math.max(0.3, r), 0.0, Math.PI * 2.0, 2.6,
                    soul(0.58f * fade), violet(0.52f * fade));
            if (startR <= 0.1) {
                b.flat(BossVfxRender.MOON_SIGIL, this.x, this.y + 0.05, this.z, Math.min(this.size * 0.85, r + 0.4), this.yawRad + t * 0.04, violet(0.55f * fade));
                b.flat(BossVfxRender.SHOCKWAVE_RING, this.x, this.y + 0.072, this.z, Math.max(0.4, r), -t * 0.06, soul(0.75f * fade));
            }
        }

        private void renderTelegraphRing(TexBatch b, float t) {
            int dur = Math.max(2, this.extra);
            float pulse = 0.78f + 0.22f * Mth.sin(t * 0.8f);
            double rOut = this.size;
            double rIn = this.innerRadius;
            if (t <= dur) {
                float prog = clamp01(t / dur);
                b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.07, this.z, Math.max(rIn, rOut - 0.45), rOut, 0.0, Math.PI * 2.0, 2.4,
                        moon(0.86f * pulse), violet(0.68f * pulse));
                if (rIn > 0.1) {
                    b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.07, this.z, rIn, Math.min(rOut, rIn + 0.42), 0.0, Math.PI * 2.0, 2.2,
                            soul(0.78f * pulse), violet(0.65f * pulse));
                    double fill = rIn + (rOut - rIn) * prog;
                    b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.058, this.z, Math.max(rIn, fill - 0.65), fill, 0.0, Math.PI * 2.0, 2.6,
                            soul(0.68f), deep(0.28f));
                } else {
                    b.flat(BossVfxRender.MOON_SIGIL, this.x, this.y + 0.05, this.z, rOut * 0.96, t * 0.04, violet(0.38f + 0.30f * prog));
                    double sweep = Math.max(0.4, rOut * prog);
                    b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.065, this.z, Math.max(0.0, sweep - 0.52), sweep, 0.0, Math.PI * 2.0, 2.2,
                            moon(0.80f), soul(0.36f));
                }
            } else {
                float flash = 1.0f - clamp01((t - dur) / 3.0f);
                b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.075, this.z, Math.max(rIn, rOut - 0.6), rOut, 0.0, Math.PI * 2.0, 2.6,
                        moon(0.85f * flash), soul(0.55f * flash));
            }
        }

        private void renderTelegraphLine(TexBatch b, float t) {
            int dur = Math.max(2, this.extra);
            float a = t <= dur ? 0.64f + 0.22f * Mth.sin(t * 1.1f) : 0.72f * (1.0f - clamp01((t - dur) / 2.0f));
            if (a <= 0.01f) {
                return;
            }
            float prog = clamp01(t / dur);
            b.tiledGroundLine(BossVfxRender.TELEGRAPH_STRIP, this.x + this.fx * 0.7, this.y + 0.065, this.z + this.fz * 0.7,
                    this.fx, this.fz, Math.max(0.5, this.size - 0.7), 0.52, 2.4, t * 0.85f, deep(0.42f * a), violet(0.68f * a));
            double fill = Math.max(0.4, (this.size - 0.7) * prog);
            b.tiledGroundLine(BossVfxRender.TELEGRAPH_STRIP, this.x + this.fx * 0.7, this.y + 0.074, this.z + this.fz * 0.7,
                    this.fx, this.fz, fill, 0.24, 1.8, t * 1.15f, soul(0.75f * a), moon(0.92f * a));
            b.billboardFrameV(BossVfxRender.STAR, this.x + this.fx * (0.7 + fill), this.y + 0.26, this.z + this.fz * (0.7 + fill),
                    0.34, t * 0.3, (int) (t * 0.7f), 4, moon(0.88f * a));
        }

        private void renderSigil(TexBatch b, float t) {
            float in = easeOut(t / 4.0f);
            float out = 1.0f - clamp01((t - (this.life - 5.0f)) / 5.0f);
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            double r = this.size * (0.65 + 0.35 * in);
            b.flat(BossVfxRender.MOON_SIGIL, this.x, this.y, this.z, r, t * 0.07, moon(0.90f * a));
            b.flat(BossVfxRender.RUNE_CIRCLE, this.x, this.y + 0.015, this.z, r * 0.70, -t * 0.09, soul(0.74f * a));
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y - 0.75 * a, this.y, this.z, r * 0.66, 0.0, Math.PI * 2.0, 2.0,
                    moon(0.48f * a), violet(0.28f * a));
        }

        private void renderLance(TexBatch b, float t) {
            float reveal = easeOut(t / 1.8f);
            float fade = 1.0f - clamp01((t - 2.0f) / 9.5f);
            if (fade <= 0.0f) {
                return;
            }
            double startOff = 0.85;
            double len = Math.max(0.8, this.size * reveal);
            double sy = this.y + 1.42;
            double sx0 = this.x + this.fx * startOff, sz0 = this.z + this.fz * startOff;
            double ex = this.x + this.fx * len, ez = this.z + this.fz * len;
            double pulseW = 1.0 + 0.18 * Mth.sin(t * 1.4f);
            b.beam(BossVfxRender.BEAM_STRIP, sx0, sy, sz0, ex, sy, ez, 0.90 * fade * pulseW, 2.6,
                    violet(0.78f * fade), soul(0.62f * fade));
            b.beam(BossVfxRender.BEAM_STRIP, sx0, sy, sz0, ex, sy, ez, 0.42 * fade * pulseW, 2.0,
                    moon(0.98f * fade), soul(0.92f * fade));
            b.beam(BossVfxRender.LIGHTNING_STRIP, sx0, sy, sz0, ex, sy, ez, 0.54 * fade, 2.8,
                    soul(0.84f * fade), moon(0.76f * fade));
            b.tiledGroundLine(BossVfxRender.TELEGRAPH_STRIP, sx0, this.y + 0.055, sz0, this.fx, this.fz,
                    Math.max(0.4, len - startOff), 0.36 * fade, 2.2, t * 1.2f, deep(0.32f * fade), soul(0.56f * fade));
            b.billboardFrameV(BossVfxRender.STAR, sx0, sy, sz0, 0.68 * fade, t * 0.35, (int) (t * 0.8f), 4, moon(0.95f * fade));
            if (reveal >= 0.95f) {
                double ir = 0.65 + 1.65 * easeOut((t - 1.8f) / 4.5f);
                b.flat(BossVfxRender.SHOCKWAVE_RING, ex, this.y + 0.07, ez, ir, t * 0.12, soul(0.78f * fade));
                b.billboardFrameV(BossVfxRender.IMPACT, ex, sy, ez, 0.85 * fade, t * 0.25, (int) (t * 0.6f), 4, moon(0.90f * fade));
            }
        }

        private void renderCharge(TexBatch b, float t, double radius, int dur) {
            float prog = clamp01(t / dur);
            if (t <= dur) {
                double r = radius * (1.30 - 0.60 * prog);
                b.flat(BossVfxRender.MOON_SIGIL, this.x, this.y + 0.06, this.z, r, t * 0.08, violet(0.42f + 0.42f * prog));
                b.pillar(this.x, this.y, this.z, 0.44 + 0.34 * prog, 3.2 * prog + 0.5, t * 0.14, violet(0.55f * prog), moon(0.82f * prog));
                for (int i = 0; i < 4; i++) {
                    double a = -t * 0.16 + i * (Math.PI * 0.5);
                    double gr = r * 0.85;
                    b.billboardFrameV(BossVfxRender.STAR, this.x + Math.cos(a) * gr, this.y + 1.0 + 1.2 * prog, this.z + Math.sin(a) * gr,
                            0.36, t * 0.2, i + (int) (t * 0.3f), 4, soul(0.88f * prog));
                }
            } else {
                float fade = 1.0f - clamp01((t - dur) / 3.0f);
                b.flat(BossVfxRender.SHOCKWAVE_RING, this.x, this.y + 0.07, this.z, radius * 1.05, this.yawRad, moon(0.82f * fade));
            }
        }

        private void renderPhaseShift(TexBatch b, float t) {
            if (t < 30.0f) {
                this.renderCharge(b, t, 5.0, 30);
                double r2 = 7.4 * (1.0 - t / 30.0f) + 0.8;
                b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.08, this.z, Math.max(0.2, r2 - 0.5), r2, 0.0, Math.PI * 2.0, 2.4,
                        soul(0.82f), violet(0.68f));
                return;
            }
            float local = t - 30.0f;
            float fade = 1.0f - clamp01(local / 22.0f);
            double r = this.size * 2.5 * easeOut(local / 10.0f);
            b.pillar(this.x, this.y, this.z, 1.85 * fade, 14.0 * easeOut(local / 4.0f), local * 0.15, violet(0.82f * fade), moon(0.95f * fade));
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y, this.y + 1.55 * fade, this.z, Math.max(0.4, r), 0.0, Math.PI * 2.0, 3.0,
                    soul(0.65f * fade), violet(0.58f * fade));
            b.flat(BossVfxRender.SHOCKWAVE_RING, this.x, this.y + 0.07, this.z, Math.max(0.5, r), local * 0.06, moon(0.88f * fade));
            double haloR = 2.8 * easeOut(local / 6.0f);
            double hx = this.x - this.fx * 0.7, hy = this.y + 3.2, hz = this.z - this.fz * 0.7;
            double sx = this.fz, sz = -this.fx;
            b.quad(BossVfxRender.MOON_SIGIL,
                    hx - sx * haloR, hy + haloR, hz - sz * haloR, 0.0f, 0.0f, moon(0.92f * fade),
                    hx + sx * haloR, hy + haloR, hz + sz * haloR, 1.0f, 0.0f, moon(0.92f * fade),
                    hx + sx * haloR, hy - haloR, hz + sz * haloR, 1.0f, 1.0f, soul(0.82f * fade),
                    hx - sx * haloR, hy - haloR, hz - sz * haloR, 0.0f, 1.0f, soul(0.82f * fade));
        }

        private void renderDash(TexBatch b, float t) {
            float fade = 1.0f - clamp01(t / 10.0f);
            if (fade <= 0.0f) {
                return;
            }
            b.tiledGroundLine(BossVfxRender.TELEGRAPH_STRIP, this.ox, this.oy + 0.07, this.oz,
                    this.fx, this.fz, this.size, 0.55, 2.2, t * 0.9f, deep(0.45f * fade), soul(0.75f * fade));
            double ex = this.ox + this.fx * this.size, ez = this.oz + this.fz * this.size;
            b.beam(BossVfxRender.BEAM_STRIP, this.ox, this.oy + 1.25, this.oz, ex, this.oy + 1.25, ez,
                    0.62 * fade, 2.4, violet(0.65f * fade), moon(0.92f * fade));
            b.beam(BossVfxRender.LIGHTNING_STRIP, this.ox, this.oy + 1.25, this.oz, ex, this.oy + 1.25, ez,
                    0.38 * fade, 2.2, soul(0.72f * fade), moon(0.85f * fade));
        }

        private void renderWhirl(TexBatch b, float t) {
            float in = clamp01(t / 5.0f);
            float out = 1.0f - clamp01((t - (this.life - 4.0f)) / 4.0f);
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            double rad = this.size;
            double spin = t * 0.85;
            for (int i = 0; i < 3; i++) {
                double ang = spin + i * (Math.PI * 2.0 / 3.0);
                b.slashArc(BossVfxRender.SLASH_HEAVY, this.x, this.y, this.z, Math.cos(ang), Math.sin(ang),
                        rad * 0.64, rad * 1.02, -0.78, 0.78, false, 0.20, 1.05 + (i % 2) * 0.45, 12,
                        moon(0.92f * a), violet(0.58f * a));
            }
            b.flat(BossVfxRender.MOON_SIGIL, this.x, this.y + 0.06, this.z, rad * 0.92, spin * 0.35, soul(0.58f * a));
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y + 0.05, this.y + 1.25, this.z, rad * 0.88, 0.0, Math.PI * 2.0, 2.6,
                    soul(0.45f * a), violet(0.48f * a));
        }

        private void renderFlameBurst(TexBatch b, float t) {
            float fade = 1.0f - clamp01((t - 3.5f) / 11.0f);
            if (fade <= 0.0f) {
                return;
            }
            RandomSource r = RandomSource.create(this.seed);
            int n = (int) Math.min(9, 4 + this.size * 0.6);
            float grow = easeOut(t / 4.0f);
            for (int i = 0; i < n; i++) {
                double ang = Math.PI * 2.0 * i / n + r.nextDouble() * 0.35;
                double d = this.size * (0.38 + 0.58 * r.nextDouble()) * (0.65 + 0.35 * grow);
                double bx = this.x + Math.cos(ang) * d, bz = this.z + Math.sin(ang) * d;
                double h = (1.4 + r.nextDouble() * 1.9) * grow * (0.55 + 0.45 * fade);
                b.pillar(bx, this.y, bz, 0.44 * (0.7 + 0.3 * fade), h, ang + t * 0.12, soul(0.75f * fade), moon(0.92f * fade));
            }
            b.flat(BossVfxRender.SCORCH_CRACKS, this.x, this.y + 0.045, this.z, this.size * 0.82 * grow, this.yawRad, violet(0.58f * fade));
        }
    }
}
