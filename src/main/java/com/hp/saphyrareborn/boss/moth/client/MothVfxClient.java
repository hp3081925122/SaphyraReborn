package com.hp.saphyrareborn.boss.moth.client;

import static com.hp.saphyrareborn.boss.client.BossVfxRender.BOLT;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.CROWN_WALL;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.GLYPH;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.LIGHTNING_STRIP;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.MOON_SIGIL;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.RING_BAND;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.RUNE_CIRCLE;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SCORCH_CRACKS;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SHARD;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SHIELD_HEX;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SHOCKWAVE_RING;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SIGIL_RING;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SLASH_CLAW;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SLASH_HEAVY;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.SLASH_MARK;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.STAR;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.TELEGRAPH_STRIP;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.clamp01;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.easeOut;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.rgba;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.client.BossVfxRender;
import com.hp.saphyrareborn.boss.client.BossVfxRender.TexBatch;
import com.hp.saphyrareborn.boss.moth.MothVfx;
import com.hp.saphyrareborn.entity.MothbossEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.ColorParticleOption;
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
public final class MothVfxClient {
    private static final int MAX_EFFECTS = 128;
    private static final double STORM_WAVE_SPEED = 1.25;
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
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 cam = camera.position();
        float partial = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
        PoseStack pose = event.getPoseStack();
        pose.pushPose();
        pose.translate(-cam.x, -cam.y, -cam.z);
        MultiBufferSource.BufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();
        TexBatch batch = new TexBatch(new Vector3f(camera.leftVector()), new Vector3f(camera.upVector()));
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

    private static float[] ivory(float a) {
        return rgba(1.0f, 0.96f, 0.85f, a);
    }

    private static float[] ghost(float a) {
        return rgba(0.42f, 0.72f, 1.0f, a);
    }

    private static float[] deep(float a) {
        return rgba(0.14f, 0.26f, 0.82f, a);
    }

    private static float[] boltColor(float a) {
        return rgba(0.88f, 0.95f, 1.0f, a);
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
        private final double yawRad;
        private final int life;
        private final MothbossEntity anchor;
        private final long seed;
        private double fx;
        private double fz;
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
            this.rawExtra = p.extra();
            if (this.kind == MothVfx.TELEGRAPH_RING || this.kind == MothVfx.SHOCKWAVE) {
                this.extra = MothVfx.ringTicks(this.rawExtra);
                this.innerRadius = MothVfx.ringInner(this.rawExtra);
            } else {
                this.extra = Math.max(0, Math.min(this.rawExtra, 400));
                this.innerRadius = 0.0;
            }
            this.yawRad = Math.toRadians(p.yaw());
            this.fx = -Math.sin(this.yawRad);
            this.fz = Math.cos(this.yawRad);
            this.seed = Double.doubleToLongBits(p.x() * 31.0 + p.z()) ^ p.extra();
            boolean follows = this.kind == MothVfx.WARD || this.kind == MothVfx.TWIRL || this.kind == MothVfx.WINGS || this.kind == MothVfx.CHARGE;
            this.anchor = follows ? nearestMoth(level, this.ox, this.oy, this.oz) : null;
            this.life = switch (this.kind) {
                case MothVfx.SLASH -> this.extra == MothVfx.SLASH_SPIN ? 10 : 8;
                case MothVfx.SHOCKWAVE -> Math.max(2, this.extra) + 8;
                case MothVfx.TELEGRAPH_RING -> Math.max(2, this.extra) + 4;
                case MothVfx.TELEGRAPH_LINE -> Math.max(2, this.extra) + 5;
                case MothVfx.BOLT_FALL, MothVfx.BOLT_RISE -> Math.max(2, this.extra) + 9;
                case MothVfx.LIGHTNING -> 9;
                case MothVfx.LEVITATE, MothVfx.WARD, MothVfx.TWIRL, MothVfx.WINGS -> Math.max(4, this.extra);
                case MothVfx.CHARGE -> Math.max(2, this.extra) + 3;
                case MothVfx.PHASE_SHIFT -> 54;
                case MothVfx.BLINK -> 9;
                case MothVfx.DASH_TRAIL -> 10;
                case MothVfx.STORM_WAVE -> Math.max(6, this.extra);
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
                if (this.kind == MothVfx.WINGS || this.kind == MothVfx.WARD) {
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
                case MothVfx.SLASH -> {
                    if (this.age == 0) {
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.16f + this.size * 0.01f, 12.0);
                    }
                    if (this.age == 2) {
                        double reach = this.size * 0.8;
                        for (int i = 0; i < 6; i++) {
                            double a = this.extra == MothVfx.SLASH_SPIN ? r.nextDouble() * Math.PI * 2.0 : this.faceAngle() + (r.nextDouble() - 0.5) * 2.6;
                            level.addParticle(ParticleTypes.ELECTRIC_SPARK, this.x + Math.cos(a) * reach, this.y + 0.8 + r.nextDouble(), this.z + Math.sin(a) * reach, 0.0, 0.05, 0.0);
                        }
                    }
                }
                case MothVfx.STORM_WAVE -> {
                    double wx = this.ox + this.fx * STORM_WAVE_SPEED * this.age;
                    double wz = this.oz + this.fz * STORM_WAVE_SPEED * this.age;
                    level.addParticle(ParticleTypes.ELECTRIC_SPARK, wx + (r.nextDouble() - 0.5) * 0.9, this.oy + 0.8 + r.nextDouble() * 0.9, wz + (r.nextDouble() - 0.5) * 0.9,
                            -this.fx * 0.06, 0.03, -this.fz * 0.06);
                    if (this.age % 2 == 0) {
                        level.addParticle(ghostFlame(), wx, this.oy + 0.9, wz, 0.0, 0.04, 0.0);
                    }
                }
                case MothVfx.SHOCKWAVE -> {
                    if (this.age == 0) {
                        if (this.innerRadius <= 0.1) {
                            BossVfxRender.debris(level, r, this.x, this.y, this.z, (int) Math.min(16, 4 + this.size), 0.3);
                        }
                        for (int i = 0; i < 8; i++) {
                            double a = r.nextDouble() * Math.PI * 2.0;
                            double rad = this.innerRadius + r.nextDouble() * Math.max(0.8, this.size - this.innerRadius);
                            level.addParticle(ghostFlame(), this.x + Math.cos(a) * rad, this.y + 0.2, this.z + Math.sin(a) * rad, r.nextGaussian() * 0.06, 0.15 + r.nextDouble() * 0.2, r.nextGaussian() * 0.06);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(0.95f, 0.14f + this.size * 0.045f), this.size * 2.4 + 8.0);
                    }
                }
                case MothVfx.BOLT_FALL, MothVfx.BOLT_RISE -> {
                    if (this.age == Math.max(2, this.extra)) {
                        double py = this.kind == MothVfx.BOLT_FALL ? this.y + 0.2 : this.y + 2.5;
                        for (int i = 0; i < 8; i++) {
                            level.addParticle(ParticleTypes.ELECTRIC_SPARK, this.x + r.nextGaussian() * 0.45, py + r.nextDouble(), this.z + r.nextGaussian() * 0.45, r.nextGaussian() * 0.16, 0.1 + r.nextDouble() * 0.16, r.nextGaussian() * 0.16);
                        }
                        level.addParticle(ghostFlame(), this.x, py, this.z, 0.0, 0.08, 0.0);
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.24f, 11.0);
                    }
                }
                case MothVfx.LIGHTNING -> {
                    if (this.age == 0) {
                        level.addParticle(ColorParticleOption.create(ParticleTypes.FLASH, 0xFFFFFF), this.x, this.y + 1.0, this.z, 0.0, 0.0, 0.0);
                        for (int i = 0; i < 12; i++) {
                            level.addParticle(ParticleTypes.ELECTRIC_SPARK, this.x + r.nextGaussian() * 0.6, this.y + 0.3 + r.nextDouble() * 1.6, this.z + r.nextGaussian() * 0.6, r.nextGaussian() * 0.25, r.nextDouble() * 0.25, r.nextGaussian() * 0.25);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(1.2f, 0.26f + this.size * 0.03f), this.size * 1.5 + 10.0);
                    }
                }
                case MothVfx.LEVITATE -> {
                    if (this.age % 4 == 0) {
                        level.addParticle(ParticleTypes.END_ROD, this.x + r.nextGaussian() * 0.5, this.y + 0.2, this.z + r.nextGaussian() * 0.5, 0.0, 0.22, 0.0);
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
                        for (int i = 0; i < 5; i++) {
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
                    if (this.age % 3 == 0) {
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

        void render(TexBatch b, float t) {
            switch (this.kind) {
                case MothVfx.SLASH -> this.renderSlash(b, t);
                case MothVfx.SHOCKWAVE -> this.renderShockwave(b, t);
                case MothVfx.TELEGRAPH_RING -> this.renderTelegraphRing(b, t);
                case MothVfx.TELEGRAPH_LINE -> this.renderTelegraphLine(b, t);
                case MothVfx.BOLT_FALL -> this.renderBolt(b, t, true);
                case MothVfx.BOLT_RISE -> this.renderBolt(b, t, false);
                case MothVfx.LIGHTNING -> this.renderLightning(b, t);
                case MothVfx.LEVITATE -> this.renderLevitate(b, t);
                case MothVfx.CHARGE -> this.renderCharge(b, t, this.size, Math.max(2, this.extra));
                case MothVfx.PHASE_SHIFT -> this.renderPhaseShift(b, t);
                case MothVfx.BLINK -> this.renderBlink(b, t);
                case MothVfx.DASH_TRAIL -> this.renderDash(b, t);
                case MothVfx.WARD -> this.renderWard(b, t);
                case MothVfx.TWIRL -> this.renderTwirl(b, t);
                case MothVfx.WINGS -> this.renderWings(b, t);
                case MothVfx.STORM_WAVE -> this.renderStormWave(b, t);
                default -> {
                }
            }
        }

        private void renderSlash(TexBatch b, float t) {
            boolean spin = this.extra == MothVfx.SLASH_SPIN;
            float reveal = easeOut(t / (spin ? 3.5f : 2.8f));
            float fade = 1.0f - clamp01((t - (spin ? 3.8f : 3.0f)) / 5.0f);
            if (fade <= 0.0f) {
                return;
            }
            double outer = this.size * 0.96;
            double inner = this.size * (spin ? 0.56 : 0.48);
            if (spin) {
                double from = this.faceAngle();
                double end = from + Math.PI * 2.0 * reveal;
                b.slashArc(SLASH_HEAVY, this.x, this.y, this.z, 1.0, 0.0, inner, outer, from, end, false, 0.0, 1.15, 32, ivory(fade), ghost(0.35f * fade));
                b.slashArc(SLASH_CLAW, this.x, this.y, this.z, 1.0, 0.0, inner * 1.1, outer * 1.05, from + 0.3, end + 0.3, false, 0.15, 1.35, 28, boltColor(0.85f * fade), deep(0.25f * fade));
                b.flat(SIGIL_RING, this.x, this.y + 0.08, this.z, outer * 0.88, t * 0.22, ghost(0.55f * fade));
                return;
            }
            boolean overhead = this.extra == MothVfx.SLASH_OVERHEAD;
            double dir = this.extra == MothVfx.SLASH_RIGHT ? -1.0 : 1.0;
            double from = overhead ? Math.toRadians(-40.0) : Math.toRadians(-95.0) * dir;
            double to = overhead ? Math.toRadians(112.0) : Math.toRadians(95.0) * dir;
            double end = from + (to - from) * reveal;
            b.slashArc(SLASH_HEAVY, this.x, this.y, this.z, this.fx, this.fz, inner, outer, from, end, overhead, 0.52 * dir, overhead ? 0.45 : 1.4, 22, ivory(fade), ghost(0.3f * fade));
            b.slashArc(SLASH_CLAW, this.x, this.y, this.z, this.fx, this.fz, inner * 1.12, outer * 1.04, from, end, overhead, 0.52 * dir, overhead ? 0.5 : 1.48, 20, boltColor(0.8f * fade), deep(0.2f * fade));
        }

        private void renderStormWave(TexBatch b, float t) {
            int dur = Math.max(6, this.extra);
            float fade = 1.0f - clamp01((t - (dur - 4.0f)) / 4.5f);
            if (fade <= 0.0f) {
                return;
            }
            double dist = STORM_WAVE_SPEED * t;
            double wx = this.ox + this.fx * dist;
            double wz = this.oz + this.fz * dist;
            double cy = this.oy + 1.25;
            double halfSpan = Math.max(1.6, this.size * 1.05);
            double halfForward = Math.max(1.1, this.size * 0.68);
            double roll = Math.sin(t * 0.45) * 0.22;
            b.crescentWave(wx, cy, wz, this.fx, this.fz, roll, halfForward, halfSpan, ghost(0.92f * fade), ivory(fade));
            double wakeLen = Math.min(dist, 4.5);
            if (wakeLen > 0.25) {
                b.tiledGroundLine(TELEGRAPH_STRIP, wx - this.fx * wakeLen, this.oy + 0.06, wz - this.fz * wakeLen, this.fx, this.fz,
                        wakeLen, this.size * 0.55, 1.8, t * 0.85f, deep(0.35f * fade), boltColor(0.75f * fade));
            }
            b.billboard(STAR, wx + this.fx * 0.35, cy, wz + this.fz * 0.35, 0.55, 0.55, t * 0.35, ivory(0.85f * fade));
            b.billboardFrameV(BOLT, wx - this.fx * 0.45, cy, wz - this.fz * 0.45, 0.65, t * 0.2, (int) (t * 2.0f), 4, boltColor(0.75f * fade));
        }

        private void renderShockwave(TexBatch b, float t) {
            int ticks = Math.max(2, this.extra);
            float prog = easeOut(t / ticks);
            float fade = 1.0f - clamp01((t - ticks) / 7.5f);
            if (fade <= 0.0f) {
                return;
            }
            double rOut = this.innerRadius + (this.size - this.innerRadius) * prog;
            double bandW = Math.max(0.55, (this.size - this.innerRadius) * 0.22);
            double rIn = Math.max(this.innerRadius, rOut - bandW);
            if (this.innerRadius <= 0.1) {
                b.flat(SHOCKWAVE_RING, this.x, this.y + 0.06, this.z, rOut, t * 0.08, ghost(0.88f * fade));
                b.flat(SCORCH_CRACKS, this.x, this.y + 0.04, this.z, this.size * 0.78, (this.seed & 7) * 0.8, boltColor(0.55f * fade));
            } else {
                b.ringBand(RING_BAND, this.x, this.y + 0.065, this.z, this.innerRadius, rOut, 0.0, Math.PI * 2.0, 2.2, ghost(0.55f * fade), deep(0.3f * fade));
            }
            b.ringBand(RING_BAND, this.x, this.y + 0.075, this.z, rIn, rOut + 0.25, 0.0, Math.PI * 2.0, 2.0, ivory(0.92f * fade), ghost(0.5f * fade));
            double wallH = (1.35 * (1.0 - prog) + 0.3) * fade;
            b.crownWall(CROWN_WALL, this.x, this.y, this.y + wallH, this.z, Math.max(0.3, rOut), 0.0, Math.PI * 2.0, 2.2, ivory(0.75f * fade), ghost(0.65f * fade));
        }

        private void renderTelegraphRing(TexBatch b, float t) {
            int dur = Math.max(2, this.extra);
            float pulse = 0.76f + 0.24f * Mth.sin(t * 0.8f);
            if (t <= dur) {
                float k = clamp01(t / dur);
                if (this.innerRadius <= 0.1) {
                    b.flat(SIGIL_RING, this.x, this.y + 0.065, this.z, this.size, t * 0.04, ghost(0.78f * pulse));
                    b.flat(MOON_SIGIL, this.x, this.y + 0.055, this.z, this.size * k, -t * 0.06, ivory(0.52f + 0.32f * k));
                    b.ringBand(RING_BAND, this.x, this.y + 0.075, this.z, Math.max(0.0, this.size - 0.35), this.size, 0.0, Math.PI * 2.0, 2.0, ivory(0.85f * pulse), ghost(0.55f * pulse));
                } else {
                    double sweep = this.innerRadius + (this.size - this.innerRadius) * k;
                    b.ringBand(RING_BAND, this.x, this.y + 0.06, this.z, this.innerRadius, sweep, 0.0, Math.PI * 2.0, 2.4, ghost(0.55f * pulse), deep(0.35f * pulse));
                    b.ringBand(RING_BAND, this.x, this.y + 0.075, this.z, this.innerRadius, this.innerRadius + 0.32, 0.0, Math.PI * 2.0, 1.8, ivory(0.9f * pulse), ghost(0.65f * pulse));
                    b.ringBand(RING_BAND, this.x, this.y + 0.075, this.z, Math.max(this.innerRadius, this.size - 0.35), this.size, 0.0, Math.PI * 2.0, 2.2, ivory(0.9f * pulse), ghost(0.65f * pulse));
                    b.crownWall(CROWN_WALL, this.x, this.y, this.y + 0.55, this.z, this.innerRadius, 0.0, Math.PI * 2.0, 2.0, ivory(0.5f * pulse), deep(0.3f * pulse));
                }
            } else {
                float flash = 1.0f - clamp01((t - dur) / 4.0f);
                if (this.innerRadius <= 0.1) {
                    b.flat(SHOCKWAVE_RING, this.x, this.y + 0.08, this.z, this.size, 0.0, ivory(0.85f * flash));
                } else {
                    b.ringBand(RING_BAND, this.x, this.y + 0.08, this.z, this.innerRadius, this.size, 0.0, Math.PI * 2.0, 2.2, ivory(0.85f * flash), ghost(0.65f * flash));
                }
            }
        }

        private void renderTelegraphLine(TexBatch b, float t) {
            int dur = Math.max(2, this.extra);
            float alpha = t <= dur ? 0.65f + 0.25f * Mth.sin(t * 1.15f) : 0.75f * (1.0f - clamp01((t - dur) / 5.0f));
            if (alpha <= 0.01f) {
                return;
            }
            double len = this.size + 1.0;
            b.tiledGroundLine(TELEGRAPH_STRIP, this.x, this.y + 0.07, this.z, this.fx, this.fz, len, 0.85, 2.0, t * 0.6f, ghost(0.65f * alpha), ivory(alpha));
            b.flat(RUNE_CIRCLE, this.x, this.y + 0.08, this.z, 1.1, t * 0.12, ivory(0.8f * alpha));
            double fill = len * clamp01(t / dur);
            b.billboard(SHARD, this.x + this.fx * fill, this.y + 0.35, this.z + this.fz * fill, 0.45, 0.45, t * 0.25, boltColor(0.9f * alpha));
        }

        private void renderBolt(TexBatch b, float t, boolean falling) {
            int dur = Math.max(2, this.extra);
            double height = falling ? 9.5 : this.size;
            if (t < dur) {
                float k = clamp01(t / dur);
                double sealR = Math.max(1.1, this.size);
                b.flat(SIGIL_RING, this.x, this.y + 0.065, this.z, sealR, t * 0.12, ghost(0.55f + 0.35f * k));
                b.flat(MOON_SIGIL, this.x, this.y + 0.075, this.z, sealR * (0.45 + 0.55 * k), -t * 0.16, ivory(0.6f + 0.35f * k));
                double tip = falling ? this.y + height * (1.0 - k) : this.y + height * k;
                double tail = falling ? tip + 3.2 : Math.max(this.y, tip - 3.2);
                b.tiledStrip3D(LIGHTNING_STRIP, this.x, tail, this.z, this.x, tip, this.z, 0.65, 2.2, ghost(0.75f), boltColor(0.95f));
                b.billboard(STAR, this.x, tip, this.z, 0.55, 0.55, t * 0.4, ivory(0.95f));
                return;
            }
            float local = t - dur;
            float fade = 1.0f - clamp01(local / 9.0f);
            if (fade <= 0.0f) {
                return;
            }
            double colH = falling ? 9.5 : this.size;
            b.pillar(this.x, this.y, this.z, Math.max(0.75, this.size * 0.52) * (0.65 + 0.35 * fade), colH * (0.7 + 0.3 * fade), t * 0.18, ghost(0.8f * fade), boltColor(fade));
            this.jaggedBolt(b, this.x, this.y, this.z, colH, 0.75 * fade, fade, 6);
            double ringR = Math.max(1.2, this.size) * (0.85 + 0.75 * easeOut(local / 4.5f));
            b.flat(SHOCKWAVE_RING, this.x, this.y + 0.08, this.z, ringR, local * 0.1, ivory(0.88f * fade));
            b.billboard(STAR, this.x, this.y + 0.6, this.z, 0.9 * fade, 0.9 * fade, local * 0.3, boltColor(fade));
        }

        private void renderLightning(TexBatch b, float t) {
            float flicker = ((int) t % 2 == 0) ? 1.0f : 0.62f;
            float fade = (1.0f - clamp01(t / 9.0f)) * flicker;
            if (fade <= 0.0f) {
                return;
            }
            double h = Math.max(5.0, this.size);
            this.jaggedBolt(b, this.x, this.y, this.z, h, 1.05, fade, 8);
            b.pillar(this.x, this.y, this.z, Math.min(2.2, 0.65 + this.size * 0.06) * fade, Math.min(8.0, h * 0.65), t * 0.2, ghost(0.72f * fade), ivory(0.95f * fade));
            double ring = 1.4 + this.size * 0.18 * easeOut(t / 4.5f);
            b.flat(SHOCKWAVE_RING, this.x, this.y + 0.075, this.z, ring, t * 0.08, ivory(0.9f * fade));
            b.flat(SCORCH_CRACKS, this.x, this.y + 0.045, this.z, ring * 0.85, (this.seed & 7) * 0.7, boltColor(0.65f * fade));
            b.billboard(STAR, this.x, this.y + 0.9, this.z, 1.25 * fade, 1.25 * fade, t * 0.25, boltColor(fade));
        }

        private void jaggedBolt(TexBatch b, double gx, double gy, double gz, double height, double width, float fade, int segs) {
            RandomSource r = RandomSource.create(this.seed);
            double top = gy + height;
            double px = gx + (r.nextDouble() - 0.5) * 1.4;
            double py = top;
            double pz = gz + (r.nextDouble() - 0.5) * 1.4;
            for (int i = 1; i <= segs; i++) {
                double k = (double) i / segs;
                double jitter = (1.0 - k) * Math.min(1.8, height * 0.11);
                double nx = i == segs ? gx : gx + (r.nextDouble() - 0.5) * 2.0 * jitter;
                double nz = i == segs ? gz : gz + (r.nextDouble() - 0.5) * 2.0 * jitter;
                double ny = top - height * k;
                b.tiledStrip3D(LIGHTNING_STRIP, px, py, pz, nx, ny, nz, width, 2.4, boltColor(fade), ivory(0.92f * fade));
                if (i == segs / 2 || i == segs - 2) {
                    double bx = nx + (r.nextDouble() - 0.5) * 2.8;
                    double bz = nz + (r.nextDouble() - 0.5) * 2.8;
                    b.tiledStrip3D(LIGHTNING_STRIP, nx, ny, nz, bx, ny - height * 0.18, bz, width * 0.55, 1.8, ghost(0.8f * fade), boltColor(0.35f * fade));
                }
                px = nx;
                py = ny;
                pz = nz;
            }
        }

        private void renderLevitate(TexBatch b, float t) {
            float fade = 1.0f - clamp01((t - (this.life - 5.0f)) / 5.0f);
            if (fade <= 0.0f) {
                return;
            }
            for (int i = 0; i < 3; i++) {
                float local = (t + i * 6.0f) % 18.0f;
                double ry = this.y + local / 18.0f * 3.0;
                float a = (1.0f - local / 18.0f) * 0.8f * fade;
                double rad = this.size * (1.05 - local / 36.0f);
                b.flat(SIGIL_RING, this.x, ry, this.z, rad, t * 0.1 + i, ivory(a));
            }
            b.crownWall(CROWN_WALL, this.x, this.y, this.y + 3.0, this.z, this.size * 0.92, 0.0, Math.PI * 2.0, 2.0, ivory(0.45f * fade), ghost(0.35f * fade));
        }

        private void renderCharge(TexBatch b, float t, double radius, int dur) {
            float prog = clamp01(t / dur);
            if (t <= dur) {
                double r = radius * 1.35 * (1.0 - prog) + 0.45;
                float a = 0.35f + 0.6f * prog;
                b.flat(SIGIL_RING, this.x, this.y + 0.07, this.z, r, t * 0.08, ghost(a));
                b.flat(RUNE_CIRCLE, this.x, this.y + 0.08, this.z, radius * 0.72, -t * 0.1, ivory(a));
                b.crownWall(CROWN_WALL, this.x, this.y, this.y + 2.8 * prog + 0.4, this.z, 0.55 + 0.35 * prog, 0.0, Math.PI * 2.0, 1.8, ivory(0.65f * prog), ghost(0.45f * prog));
                for (int i = 0; i < 4; i++) {
                    double ang = t * 0.14 + i * Math.PI * 0.5;
                    b.billboardFrameU(GLYPH, this.x + Math.cos(ang) * r * 0.85, this.y + 1.2 + 0.3 * Math.sin(t * 0.25 + i), this.z + Math.sin(ang) * r * 0.85, 0.38, 0.0, i + (int) (t * 0.25f), 8, ivory(0.85f * a));
                }
            } else {
                float fade = 1.0f - clamp01((t - dur) / 3.0f);
                b.flat(SHOCKWAVE_RING, this.x, this.y + 0.08, this.z, radius * 0.95, 0.0, ivory(0.8f * fade));
            }
        }

        private void renderPhaseShift(TexBatch b, float t) {
            if (t < 30.0f) {
                this.renderCharge(b, t, 5.0, 30);
                double r2 = 8.0 * (1.0 - t / 30.0f) + 0.8;
                b.flat(MOON_SIGIL, this.x, this.y + 0.06, this.z, r2, -t * 0.06, ghost(0.7f));
                return;
            }
            float local = t - 30.0f;
            float fade = 1.0f - clamp01(local / 22.0f);
            if (fade <= 0.0f) {
                return;
            }
            double r = this.size * 2.6 * easeOut(local / 10.0f);
            b.flat(SHOCKWAVE_RING, this.x, this.y + 0.07, this.z, r, local * 0.05, ivory(0.9f * fade));
            b.crownWall(CROWN_WALL, this.x, this.y, this.y + 2.4 * fade + 0.3, this.z, Math.max(0.5, r), 0.0, Math.PI * 2.0, 2.5, ivory(0.8f * fade), ghost(0.65f * fade));
            double rise = 15.0 * easeOut(local / 4.0f);
            b.pillar(this.x, this.y, this.z, 2.2 * fade, rise, local * 0.16, ghost(0.85f * fade), ivory(fade));
            this.jaggedBolt(b, this.x, this.y, this.z, rise, 1.15 * fade, fade, 7);
        }

        private void renderBlink(TexBatch b, float t) {
            boolean arrive = this.extra == 1;
            float k = clamp01(t / 9.0f);
            float fade = 1.0f - k;
            if (fade <= 0.0f) {
                return;
            }
            double scale = arrive ? 0.65 + 0.65 * easeOut(t / 3.0f) : 1.05 - 0.65 * k;
            double h = 3.4 * (arrive ? 1.0 : 1.0 + 0.3 * k);
            b.tiledStrip3D(LIGHTNING_STRIP, this.x, this.y, this.z, this.x, this.y + h, this.z, 0.9 * scale, 2.0, ghost(0.8f * fade), ivory(fade));
            b.billboard(STAR, this.x, this.y + h * 0.5, this.z, 1.1 * scale, 1.6 * scale, t * 0.2, ivory(0.95f * fade));
            double r = 0.7 + 2.3 * easeOut(t / 5.0f);
            b.flat(SHOCKWAVE_RING, this.x, this.y + 0.07, this.z, r, t * 0.12, ghost(0.8f * fade));
        }

        private void renderDash(TexBatch b, float t) {
            float fade = 1.0f - clamp01(t / 10.0f);
            if (fade <= 0.0f) {
                return;
            }
            double len = this.size;
            double ex = this.ox + this.fx * len;
            double ez = this.oz + this.fz * len;
            b.tiledGroundLine(TELEGRAPH_STRIP, this.ox, this.oy + 0.07, this.oz, this.fx, this.fz, len, 0.65, 2.0, t * 0.8f, deep(0.45f * fade), ghost(0.8f * fade));
            b.tiledStrip3D(LIGHTNING_STRIP, this.ox, this.oy + 1.1, this.oz, ex, this.oy + 1.1, ez, 0.55 * fade, 2.2, ghost(0.65f * fade), ivory(0.9f * fade));
            b.tiledStrip3D(SLASH_MARK, this.ox, this.oy + 2.0, this.oz, ex, this.oy + 2.0, ez, 0.45 * fade, 2.4, deep(0.45f * fade), boltColor(0.8f * fade));
        }

        private void renderWard(TexBatch b, float t) {
            float in = clamp01(t / 5.0f);
            float out = 1.0f - clamp01((t - (this.life - 5.0f)) / 5.0f);
            float a = in * out * (0.82f + 0.18f * Mth.sin(t * 0.5f));
            if (a <= 0.0f) {
                return;
            }
            double rad = this.size;
            double spin = t * 0.06;
            for (int i = 0; i < 6; i++) {
                double ang = spin + i * Math.PI / 3.0;
                double cx = this.x + Math.cos(ang) * rad;
                double cz = this.z + Math.sin(ang) * rad;
                double cy = this.y + 1.55 + 0.18 * Math.sin(t * 0.3 + i);
                b.billboard(SHIELD_HEX, cx, cy, cz, 0.72, 0.92, 0.0, ghost(0.82f * a));
                b.billboard(STAR, cx, cy, cz, 0.32, 0.32, t * 0.2 + i, ivory(0.9f * a));
            }
            b.flat(SIGIL_RING, this.x, this.y + 0.07, this.z, rad * 1.05, spin, ghost(0.75f * a));
            b.flat(RUNE_CIRCLE, this.x, this.y + 0.08, this.z, rad * 0.65, -spin * 1.3, ivory(0.65f * a));
            b.crownWall(CROWN_WALL, this.x, this.y + 0.08, this.y + 2.6, this.z, rad, 0.0, Math.PI * 2.0, 2.2, ivory(0.42f * a), ghost(0.32f * a));
        }

        private double twirlRadius(float t) {
            return 2.6 + (this.size - 2.6) * clamp01(t / Math.max(1, this.extra));
        }

        private void renderTwirl(TexBatch b, float t) {
            float in = clamp01(t / 6.0f);
            float out = 1.0f - clamp01((t - (this.life - 4.0f)) / 4.0f);
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            double rad = this.twirlRadius(t);
            double spin = t * 0.72;
            for (int i = 0; i < 3; i++) {
                double from = spin + i * Math.PI * 2.0 / 3.0;
                b.slashArc(SLASH_HEAVY, this.x, this.y, this.z, 1.0, 0.0, rad * 0.58, rad, from, from + 1.25, false, 0.0, 1.05, 10, ivory(0.9f * a), ghost(0.45f * a));
            }
            b.flat(SIGIL_RING, this.x, this.y + 0.07, this.z, rad, -spin * 0.4, ghost(0.65f * a));
        }

        private void renderWings(TexBatch b, float t) {
            float in = easeOut(t / 7.0f);
            float out = 1.0f - clamp01((t - (this.life - 8.0f)) / 8.0f);
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            double span = this.size * (0.85 + 0.25 * in);
            double height = span * 0.92;
            double flap = 0.28 + 0.26 * Math.sin(t * 0.36);
            double cy = this.y + 2.35;
            b.mothWings(this.x, cy, this.z, this.fx, this.fz, span, height, flap, ghost(0.88f * a), ivory(0.96f * a));
            b.mothWings(this.x, cy + 0.05, this.z, this.fx, this.fz, span * 0.76, height * 0.76, flap * 0.85, boltColor(0.75f * a), ivory(a));
            double bx = this.x - this.fx * 0.65;
            double bz = this.z - this.fz * 0.65;
            b.billboard(STAR, bx, cy + 0.25, bz, 0.65 * in, 0.65 * in, t * 0.18, ivory(0.9f * a));
        }
    }
}
