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
        return rgba(0.94f, 0.96f, 1.0f, a);
    }

    private static float[] violet(float a) {
        return rgba(0.70f, 0.42f, 1.0f, a);
    }

    private static float[] deep(float a) {
        return rgba(0.32f, 0.10f, 0.58f, a);
    }

    private static float[] soul(float a) {
        return rgba(0.45f, 0.96f, 0.92f, a);
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
                case WitchVfx.SLASH -> this.extra == WitchVfx.SLASH_SPIN ? 10 : 8;
                case WitchVfx.CLAW -> 9;
                case WitchVfx.SHOCKWAVE -> Math.max(2, this.extra) + 8;
                case WitchVfx.TELEGRAPH_RING, WitchVfx.TELEGRAPH_LINE -> Math.max(2, this.extra) + 4;
                case WitchVfx.MOON_SIGIL -> Math.max(4, this.extra) + 6;
                case WitchVfx.MOON_LANCE -> 13;
                case WitchVfx.CHARGE -> Math.max(2, this.extra) + 3;
                case WitchVfx.PHASE_SHIFT -> 54;
                case WitchVfx.DASH_TRAIL -> 10;
                case WitchVfx.WHIRL -> Math.max(4, this.extra);
                case WitchVfx.FLAME_BURST -> 16;
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
                        for (int i = 0; i < 12; i++) {
                            double d = r.nextDouble() * this.size;
                            level.addParticle(ParticleTypes.END_ROD, this.x + this.fx * d + r.nextGaussian() * 0.2, this.y + 1.9 + r.nextGaussian() * 0.2, this.z + this.fz * d + r.nextGaussian() * 0.2, r.nextGaussian() * 0.03, 0.02, r.nextGaussian() * 0.03);
                        }
                        double tx = this.x + this.fx * this.size, tz = this.z + this.fz * this.size;
                        level.addParticle(ParticleTypes.FLASH, tx, this.y + 1.9, tz, 0.0, 0.0, 0.0);
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.32f, 16.0);
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
            float reveal = easeOut(t / (spin ? 3.8f : 2.8f));
            float fade = 1.0f - clamp01((t - (spin ? 3.8f : 2.8f)) / 5.0f);
            if (fade <= 0.0f) {
                return;
            }
            if (spin) {
                for (int k = 0; k < 2; k++) {
                    double base = k * Math.PI + t * 0.28;
                    b.slashArc(BossVfxRender.SLASH_HEAVY, this.x, this.y, this.z, Math.cos(base), Math.sin(base),
                            this.size * 0.48, this.size * 0.98, -Math.PI * 0.65, Math.PI * 0.65 * reveal,
                            false, 0.18, 1.15 + k * 0.2, 18, moon(0.95f * fade), violet(0.75f * fade));
                }
                b.flat(BossVfxRender.MOON_SIGIL, this.x, this.y + 0.06, this.z, this.size * 0.92, -t * 0.09, soul(0.65f * fade));
                return;
            }
            boolean overhead = this.extra == WitchVfx.SLASH_OVERHEAD;
            double dir = this.extra == WitchVfx.SLASH_RIGHT ? -1.0 : 1.0;
            double from = overhead ? Math.toRadians(-40.0) : Math.toRadians(-92.0) * dir;
            double to = overhead ? Math.toRadians(115.0) : Math.toRadians(92.0) * dir;
            double end = from + (to - from) * reveal;
            b.slashArc(BossVfxRender.SLASH_HEAVY, this.x, this.y, this.z, this.fx, this.fz,
                    this.size * 0.45, this.size * 0.98, from, end, overhead, 0.52 * dir, overhead ? 0.5 : 1.38, 18,
                    moon(0.95f * fade), violet(0.78f * fade));
            b.slashArc(BossVfxRender.SLASH_HEAVY, this.x, this.y, this.z, this.fx, this.fz,
                    this.size * 0.72, this.size * 1.06, from + (end - from) * 0.25, end, overhead, 0.52 * dir, overhead ? 0.5 : 1.38, 12,
                    soul(0.82f * fade), deep(0.3f * fade));
        }

        private void renderClaw(TexBatch b, float t) {
            float reveal = easeOut(t / 2.5f);
            float fade = 1.0f - clamp01((t - 2.5f) / 6.0f);
            if (fade <= 0.0f) {
                return;
            }
            double dir = this.extra == 1 ? -1.0 : 1.0;
            double from = Math.toRadians(-76.0) * dir;
            double to = Math.toRadians(76.0) * dir;
            double end = from + (to - from) * reveal;
            b.slashArc(BossVfxRender.SLASH_CLAW, this.x, this.y, this.z, this.fx, this.fz,
                    this.size * 0.42, this.size * 0.98, from, end, false, -0.62 * dir, 1.45, 16,
                    moon(0.96f * fade), violet(0.8f * fade));
            b.slashArc(BossVfxRender.SLASH_CLAW, this.x, this.y, this.z, this.fx, this.fz,
                    this.size * 0.52, this.size * 1.04, from, end, false, -0.45 * dir, 1.15, 14,
                    soul(0.82f * fade), deep(0.45f * fade));
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
            b.crescentWave(wx, this.oy + 1.35, wz, this.fx, this.fz, 0.22 * Mth.sin(t * 0.5f),
                    1.25, this.size * 0.95, violet(0.9f * a), moon(0.96f * a));
            b.billboardFrameV(BossVfxRender.STAR, wx - this.fx * 0.6, this.oy + 1.35, wz - this.fz * 0.6,
                    0.55, t * 0.25, (int) (t * 0.5f), 4, soul(0.85f * a));
            double trailLen = Math.min(dist, 4.2);
            if (trailLen > 0.4) {
                b.tiledGroundLine(BossVfxRender.TELEGRAPH_STRIP, wx - this.fx * trailLen, this.oy + 0.05, wz - this.fz * trailLen,
                        this.fx, this.fz, trailLen, 0.78, 2.2, t * 0.75f, deep(0.45f * a), soul(0.75f * a));
            }
        }

        private void renderShockwave(TexBatch b, float t) {
            int ticks = Math.max(2, this.extra);
            float prog = easeOut(t / ticks);
            float fade = 1.0f - clamp01((t - ticks) / 8.0f);
            if (fade <= 0.0f) {
                return;
            }
            double startR = this.innerRadius;
            double r = startR + (this.size - startR) * prog;
            double bandW = Math.max(0.8, (this.size - startR) * 0.22);
            b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.065, this.z, Math.max(startR, r - bandW), r + 0.2,
                    0.0, Math.PI * 2.0, 2.6, moon(0.92f * fade), violet(0.82f * fade));
            double wallH = (1.4 * (1.0 - prog * 0.5) + 0.35) * fade;
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y, this.y + wallH, this.z, r, 0.0, Math.PI * 2.0, 2.8,
                    soul(0.85f * fade), violet(0.9f * fade));
            if (startR <= 0.1) {
                b.flat(BossVfxRender.MOON_SIGIL, this.x, this.y + 0.05, this.z, Math.min(this.size * 0.9, r + 0.5), this.yawRad + t * 0.04, violet(0.68f * fade));
            }
        }

        private void renderTelegraphRing(TexBatch b, float t) {
            int dur = Math.max(2, this.extra);
            float pulse = 0.78f + 0.22f * Mth.sin(t * 0.8f);
            double rOut = this.size;
            double rIn = this.innerRadius;
            if (t <= dur) {
                float prog = clamp01(t / dur);
                b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.07, this.z, Math.max(rIn, rOut - 0.55), rOut, 0.0, Math.PI * 2.0, 2.6,
                        moon(0.88f * pulse), violet(0.85f * pulse));
                if (rIn > 0.1) {
                    b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.07, this.z, rIn, Math.min(rOut, rIn + 0.48), 0.0, Math.PI * 2.0, 2.3,
                            soul(0.82f * pulse), violet(0.8f * pulse));
                    double fill = rIn + (rOut - rIn) * prog;
                    b.ringBand(BossVfxRender.TELEGRAPH_STRIP, this.x, this.y + 0.055, this.z, Math.max(rIn, fill - 0.85), fill, 0.0, Math.PI * 2.0, 3.0,
                            soul(0.75f), violet(0.35f));
                } else {
                    b.flat(BossVfxRender.MOON_SIGIL, this.x, this.y + 0.05, this.z, rOut, t * 0.04, violet(0.42f + 0.35f * prog));
                    double sweep = Math.max(0.4, rOut * prog);
                    b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.065, this.z, Math.max(0.0, sweep - 0.65), sweep, 0.0, Math.PI * 2.0, 2.4,
                            moon(0.82f), soul(0.4f));
                }
            } else {
                float flash = 1.0f - clamp01((t - dur) / 4.0f);
                b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.075, this.z, rIn, rOut, 0.0, Math.PI * 2.0, 2.8,
                        moon(0.92f * flash), soul(0.75f * flash));
            }
        }

        private void renderTelegraphLine(TexBatch b, float t) {
            int dur = Math.max(2, this.extra);
            float a = t <= dur ? 0.68f + 0.24f * Mth.sin(t * 1.1f) : 0.85f * (1.0f - clamp01((t - dur) / 4.0f));
            if (a <= 0.0f) {
                return;
            }
            b.tiledGroundLine(BossVfxRender.TELEGRAPH_STRIP, this.x, this.y + 0.07, this.z,
                    this.fx, this.fz, this.size, 0.95, 2.6, t * 0.8f, violet(0.72f * a), moon(0.95f * a));
            int stars = Math.max(3, Math.round(this.size / 5.0f));
            for (int i = 1; i <= stars; i++) {
                double d = this.size * i / (stars + 0.5);
                b.flat(BossVfxRender.MOON_SIGIL, this.x + this.fx * d, this.y + 0.08, this.z + this.fz * d,
                        0.95, -t * 0.08 + i, soul(0.78f * a));
            }
        }

        private void renderSigil(TexBatch b, float t) {
            float in = easeOut(t / 4.0f);
            float out = 1.0f - clamp01((t - (this.life - 5.0f)) / 5.0f);
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            double r = this.size * (0.65 + 0.35 * in);
            b.flat(BossVfxRender.MOON_SIGIL, this.x, this.y, this.z, r, t * 0.07, moon(0.92f * a));
            b.flat(BossVfxRender.RUNE_CIRCLE, this.x, this.y + 0.015, this.z, r * 0.72, -t * 0.09, soul(0.78f * a));
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y - 1.1 * a, this.y, this.z, r * 0.68, 0.0, Math.PI * 2.0, 2.0,
                    moon(0.65f * a), violet(0.35f * a));
        }

        private void renderLance(TexBatch b, float t) {
            float reveal = easeOut(t / 2.0f);
            float fade = 1.0f - clamp01((t - 2.0f) / 10.0f);
            if (fade <= 0.0f) {
                return;
            }
            double len = this.size * reveal;
            double sy = this.y + 1.9;
            double ex = this.x + this.fx * len, ez = this.z + this.fz * len;
            double width = 1.25 + 0.45 * (1.0 - fade);
            b.tiledStrip3D(BossVfxRender.BEAM_STRIP, this.x, sy, this.z, ex, sy, ez, width * 1.45, 3.0,
                    violet(0.85f * fade), soul(0.75f * fade));
            b.tiledStrip3D(BossVfxRender.LIGHTNING_STRIP, this.x, sy, this.z, ex, sy, ez, width * 0.9, 2.4,
                    moon(0.98f * fade), soul(0.9f * fade));
            double mx = this.x + this.fx * 0.9, mz = this.z + this.fz * 0.9;
            double sx = this.fz, sz = -this.fx;
            double mr = 1.35 * fade;
            b.quad(BossVfxRender.MOON_SIGIL,
                    mx - sx * mr, sy + mr, mz - sz * mr, 0.0f, 0.0f, moon(0.95f * fade),
                    mx + sx * mr, sy + mr, mz + sz * mr, 1.0f, 0.0f, moon(0.95f * fade),
                    mx + sx * mr, sy - mr, mz + sz * mr, 1.0f, 1.0f, soul(0.85f * fade),
                    mx - sx * mr, sy - mr, mz - sz * mr, 0.0f, 1.0f, soul(0.85f * fade));
            if (reveal >= 0.98f) {
                double ir = 0.8 + 2.4 * easeOut((t - 2.0f) / 5.0f);
                b.flat(BossVfxRender.SHOCKWAVE_RING, ex, this.y + 0.07, ez, ir, t * 0.1, moon(0.85f * fade));
            }
        }

        private void renderCharge(TexBatch b, float t, double radius, int dur) {
            float prog = clamp01(t / dur);
            if (t <= dur) {
                double r = radius * (1.35 - 0.65 * prog);
                b.flat(BossVfxRender.MOON_SIGIL, this.x, this.y + 0.06, this.z, r, t * 0.08, violet(0.45f + 0.45f * prog));
                b.pillar(this.x, this.y, this.z, 0.5 + 0.4 * prog, 3.6 * prog + 0.6, t * 0.14, violet(0.65f * prog), moon(0.85f * prog));
                for (int i = 0; i < 4; i++) {
                    double a = -t * 0.16 + i * (Math.PI * 0.5);
                    double gr = r * 0.85;
                    b.billboardFrameV(BossVfxRender.STAR, this.x + Math.cos(a) * gr, this.y + 1.0 + 1.3 * prog, this.z + Math.sin(a) * gr,
                            0.38, t * 0.2, i + (int) (t * 0.3f), 4, soul(0.88f * prog));
                }
            } else {
                float fade = 1.0f - clamp01((t - dur) / 3.0f);
                b.flat(BossVfxRender.SHOCKWAVE_RING, this.x, this.y + 0.07, this.z, radius * 1.1, this.yawRad, moon(0.85f * fade));
            }
        }

        private void renderPhaseShift(TexBatch b, float t) {
            if (t < 30.0f) {
                this.renderCharge(b, t, 5.2, 30);
                double r2 = 7.8 * (1.0 - t / 30.0f) + 0.8;
                b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.08, this.z, Math.max(0.2, r2 - 0.6), r2, 0.0, Math.PI * 2.0, 2.5,
                        soul(0.85f), violet(0.8f));
                return;
            }
            float local = t - 30.0f;
            float fade = 1.0f - clamp01(local / 22.0f);
            double r = this.size * 2.6 * easeOut(local / 10.0f);
            b.pillar(this.x, this.y, this.z, 2.2, 15.0 * easeOut(local / 4.0f), local * 0.15, violet(0.9f * fade), moon(0.95f * fade));
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y, this.y + 2.4 * fade, this.z, r, 0.0, Math.PI * 2.0, 3.2,
                    soul(0.9f * fade), violet(0.92f * fade));
            double haloR = 3.2 * easeOut(local / 6.0f);
            double hx = this.x - this.fx * 0.8, hy = this.y + 3.5, hz = this.z - this.fz * 0.8;
            double sx = this.fz, sz = -this.fx;
            b.quad(BossVfxRender.MOON_SIGIL,
                    hx - sx * haloR, hy + haloR, hz - sz * haloR, 0.0f, 0.0f, moon(0.96f * fade),
                    hx + sx * haloR, hy + haloR, hz + sz * haloR, 1.0f, 0.0f, moon(0.96f * fade),
                    hx + sx * haloR, hy - haloR, hz + sz * haloR, 1.0f, 1.0f, soul(0.88f * fade),
                    hx - sx * haloR, hy - haloR, hz - sz * haloR, 0.0f, 1.0f, soul(0.88f * fade));
        }

        private void renderDash(TexBatch b, float t) {
            float fade = 1.0f - clamp01(t / 10.0f);
            if (fade <= 0.0f) {
                return;
            }
            b.tiledGroundLine(BossVfxRender.TELEGRAPH_STRIP, this.ox, this.oy + 0.07, this.oz,
                    this.fx, this.fz, this.size, 0.8, 2.4, t * 0.9f, violet(0.65f * fade), moon(0.9f * fade));
            double ex = this.ox + this.fx * this.size, ez = this.oz + this.fz * this.size;
            b.tiledStrip3D(BossVfxRender.SLASH_HEAVY, this.ox, this.oy + 1.35, this.oz, ex, this.oy + 1.35, ez,
                    1.1 * fade, 3.0, deep(0.35f * fade), soul(0.85f * fade));
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
                        rad * 0.55, rad * 1.02, -0.75, 0.75, false, 0.22, 1.05 + (i % 2) * 0.55, 10,
                        moon(0.9f * a), violet(0.75f * a));
            }
            b.flat(BossVfxRender.MOON_SIGIL, this.x, this.y + 0.06, this.z, rad * 0.95, spin * 0.35, soul(0.65f * a));
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y + 0.05, this.y + 1.85, this.z, rad * 0.88, 0.0, Math.PI * 2.0, 2.8,
                    soul(0.55f * a), violet(0.65f * a));
        }

        private void renderFlameBurst(TexBatch b, float t) {
            float fade = 1.0f - clamp01((t - 4.0f) / 12.0f);
            if (fade <= 0.0f) {
                return;
            }
            RandomSource r = RandomSource.create(this.seed);
            int n = (int) Math.min(14, 5 + this.size * 0.9);
            float grow = easeOut(t / 4.5f);
            for (int i = 0; i < n; i++) {
                double ang = Math.PI * 2.0 * i / n + r.nextDouble() * 0.35;
                double d = this.size * (0.35 + 0.65 * r.nextDouble()) * (0.6 + 0.4 * grow);
                double bx = this.x + Math.cos(ang) * d, bz = this.z + Math.sin(ang) * d;
                double h = (1.6 + r.nextDouble() * 2.4) * grow * (0.55 + 0.45 * fade);
                b.pillar(bx, this.y, bz, 0.58, h, ang + t * 0.12, soul(0.85f * fade), moon(0.92f * fade));
            }
            b.flat(BossVfxRender.SCORCH_CRACKS, this.x, this.y + 0.045, this.z, this.size * 0.85 * grow, this.yawRad, violet(0.65f * fade));
        }
    }
}
