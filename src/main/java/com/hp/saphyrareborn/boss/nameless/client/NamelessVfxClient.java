package com.hp.saphyrareborn.boss.nameless.client;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.client.BossVfxRender;
import com.hp.saphyrareborn.boss.client.BossVfxRender.TexBatch;
import com.hp.saphyrareborn.boss.nameless.NamelessVfx;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.clamp01;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.easeOut;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.rgba;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Camera;
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
import org.joml.Vector3f;

@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class NamelessVfxClient {
    private static final int MAX_EFFECTS = 128;
    private static final double WAVE_SPEED = 1.15;
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
        Camera camera = event.getCamera();
        Vec3 cam = camera.getPosition();
        float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
        PoseStack pose = event.getPoseStack();
        pose.pushPose();
        pose.translate(-cam.x, -cam.y, -cam.z);
        MultiBufferSource.BufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();
        TexBatch batch = new TexBatch(new Vector3f(camera.getLeftVector()), new Vector3f(camera.getUpVector()));
        for (Fx fx : ACTIVE) {
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

    private static ParticleOptions redFire() {
        return MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get();
    }

    private static float[] core(float a) {
        return rgba(1.0f, 0.55f, 0.28f, a);
    }

    private static float[] crimson(float a) {
        return rgba(0.92f, 0.08f, 0.12f, a);
    }

    private static float[] bloodDark(float a) {
        return rgba(0.55f, 0.03f, 0.06f, a);
    }

    private static float[] gold(float a) {
        return rgba(1.0f, 0.78f, 0.34f, a);
    }

    private static float[] hotWhite(float a) {
        return rgba(1.0f, 0.94f, 0.84f, a);
    }

    private static final class Fx {
        private final byte kind;
        private final double x;
        private final double y;
        private final double z;
        private final float size;
        private final int rawExtra;
        private final int extra;
        private final double innerRadius;
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
            this.size = Math.max(0.5f, Math.min(p.size(), 36.0f));
            this.rawExtra = p.extra();
            if (this.kind == NamelessVfx.TELEGRAPH_RING || this.kind == NamelessVfx.SLAM) {
                this.extra = NamelessVfx.ringTicks(this.rawExtra);
                this.innerRadius = NamelessVfx.ringInner(this.rawExtra);
            } else {
                this.extra = Math.max(0, Math.min(this.rawExtra, 400));
                this.innerRadius = 0.0;
            }
            this.yawRad = Math.toRadians(p.yaw());
            this.fx = -Math.sin(this.yawRad);
            this.fz = Math.cos(this.yawRad);
            this.life = switch (this.kind) {
                case NamelessVfx.SLASH -> 8;
                case NamelessVfx.SLAM -> Math.max(2, this.extra) + 8;
                case NamelessVfx.TELEGRAPH_RING, NamelessVfx.TELEGRAPH_LINE -> Math.max(2, this.extra) + 4;
                case NamelessVfx.ROT_PILLAR -> this.extra + 15;
                case NamelessVfx.ROAR -> 20;
                case NamelessVfx.SHIELD_SPARK -> 6;
                case NamelessVfx.SHIELD_RAISE -> Math.max(4, this.extra);
                case NamelessVfx.SHIELD_BREAK -> 12;
                case NamelessVfx.CHARGE -> Math.max(2, this.extra) + 4;
                case NamelessVfx.PHASE_SHIFT -> 54;
                case NamelessVfx.ROT_POOL -> Math.max(4, this.extra);
                case NamelessVfx.WAVE -> Math.max(6, this.extra);
                case NamelessVfx.SHIELD_COUNTER -> Math.max(6, this.extra) + 6;
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
                        level.addParticle(ParticleTypes.SWEEP_ATTACK, this.x + this.fx * 2.2, this.y + 1.25, this.z + this.fz * 2.2, 0.0, 0.0, 0.0);
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.2f, 13.0);
                    }
                    if (this.age == 2) {
                        double reach = this.size * 0.78;
                        for (int i = 0; i < 7; i++) {
                            double a = this.faceAngle() + (r.nextDouble() - 0.5) * 2.4;
                            level.addParticle(redFlame(), this.x + Math.cos(a) * reach, this.y + 0.6 + r.nextDouble() * 1.3, this.z + Math.sin(a) * reach, 0.0, 0.04, 0.0);
                        }
                    }
                }
                case NamelessVfx.WAVE -> {
                    double wx = this.x + this.fx * WAVE_SPEED * this.age;
                    double wz = this.z + this.fz * WAVE_SPEED * this.age;
                    for (int i = 0; i < 2; i++) {
                        level.addParticle(redFlame(), wx + (r.nextDouble() - 0.5) * 1.2, this.y + 0.4 + r.nextDouble() * 1.4, wz + (r.nextDouble() - 0.5) * 1.2,
                                -this.fx * 0.08, 0.04, -this.fz * 0.08);
                    }
                }
                case NamelessVfx.SLAM -> {
                    if (this.age == 0) {
                        if (this.innerRadius <= 0.1) {
                            BossVfxRender.debris(level, r, this.x, this.y, this.z, (int) Math.min(18, 6 + this.size), 0.35);
                        }
                        for (int i = 0; i < 10; i++) {
                            double a = r.nextDouble() * Math.PI * 2.0;
                            double rad = this.innerRadius + r.nextDouble() * Math.max(0.8, this.size - this.innerRadius);
                            level.addParticle(redFlame(), this.x + Math.cos(a) * rad, this.y + 0.2, this.z + Math.sin(a) * rad, r.nextGaussian() * 0.06, 0.2 + r.nextDouble() * 0.25, r.nextGaussian() * 0.06);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, Math.min(1.0f, 0.18f + this.size * 0.04f), this.size * 2.4 + 10.0);
                    }
                }
                case NamelessVfx.ROT_PILLAR -> {
                    int local = this.age - this.extra;
                    if (local == 0) {
                        BossVfxRender.debris(level, r, this.x, this.y, this.z, 5, 0.3);
                        for (int i = 0; i < 6; i++) {
                            level.addParticle(redFlame(), this.x + r.nextGaussian() * 0.6, this.y + 0.2, this.z + r.nextGaussian() * 0.6, 0.0, 0.3 + r.nextDouble() * 0.3, 0.0);
                        }
                        level.addParticle(ParticleTypes.LARGE_SMOKE, this.x, this.y + 0.6, this.z, 0.0, 0.08, 0.0);
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.26f, 11.0);
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
                        double px = this.x + this.fx * 1.35, py = this.y + 1.7, pz = this.z + this.fz * 1.35;
                        for (int i = 0; i < (int) (6 * this.size); i++) {
                            level.addParticle(ParticleTypes.CRIT, px, py, pz, this.fx * 0.3 + r.nextGaussian() * 0.2, r.nextGaussian() * 0.15 + 0.05, this.fz * 0.3 + r.nextGaussian() * 0.2);
                        }
                    }
                }
                case NamelessVfx.SHIELD_RAISE -> {
                    if (this.age == 0) {
                        for (int i = 0; i < 6; i++) {
                            level.addParticle(ParticleTypes.CRIT, this.x + this.fx * 1.25, this.y + 1.6, this.z + this.fz * 1.25, r.nextGaussian() * 0.2, r.nextGaussian() * 0.2, r.nextGaussian() * 0.2);
                        }
                    }
                }
                case NamelessVfx.SHIELD_BREAK -> {
                    if (this.age == 0) {
                        double px = this.x + this.fx * 1.25, py = this.y + 1.6, pz = this.z + this.fz * 1.25;
                        ItemParticleOption shard = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SHIELD));
                        for (int i = 0; i < 12; i++) {
                            level.addParticle(shard, px, py, pz, r.nextGaussian() * 0.25, 0.15 + r.nextDouble() * 0.3, r.nextGaussian() * 0.25);
                        }
                        for (int i = 0; i < 8; i++) {
                            level.addParticle(ParticleTypes.CRIT, px, py, pz, r.nextGaussian() * 0.4, r.nextGaussian() * 0.4, r.nextGaussian() * 0.4);
                        }
                        level.addParticle(ParticleTypes.FLASH, px, py, pz, 0.0, 0.0, 0.0);
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.65f, 16.0);
                    }
                }
                case NamelessVfx.SHIELD_COUNTER -> {
                    if (this.age == 0) {
                        for (int i = 0; i < 20; i++) {
                            double a = r.nextDouble() * Math.PI * 2.0;
                            level.addParticle(redFlame(), this.x + Math.cos(a) * 1.5, this.y + 1.2, this.z + Math.sin(a) * 1.5, Math.cos(a) * 0.45, 0.1, Math.sin(a) * 0.45);
                        }
                        BossVfxRender.addShake(this.x, this.y, this.z, 0.75f, 20.0);
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
                        level.addParticle(redFire(), this.x + r.nextGaussian(), this.y + 0.1, this.z + r.nextGaussian(), 0.0, 0.05, 0.0);
                    }
                }
                default -> {
                }
            }
        }

        void render(TexBatch b, float t) {
            switch (this.kind) {
                case NamelessVfx.SLASH -> this.renderSlash(b, t);
                case NamelessVfx.WAVE -> this.renderWave(b, t);
                case NamelessVfx.SLAM -> this.renderSlam(b, t);
                case NamelessVfx.TELEGRAPH_RING -> this.renderTelegraphRing(b, t);
                case NamelessVfx.TELEGRAPH_LINE -> this.renderTelegraphLine(b, t);
                case NamelessVfx.ROT_PILLAR -> this.renderPillar(b, t - this.extra, this.size * 0.65, 5.4, 1.0f);
                case NamelessVfx.ROAR -> this.renderRoar(b, t);
                case NamelessVfx.SHIELD_SPARK -> this.renderShieldSpark(b, t);
                case NamelessVfx.SHIELD_RAISE -> this.renderBarrier(b, t);
                case NamelessVfx.SHIELD_BREAK -> this.renderShieldBreak(b, t);
                case NamelessVfx.SHIELD_COUNTER -> this.renderShieldCounter(b, t);
                case NamelessVfx.CHARGE -> this.renderCharge(b, t, this.size, Math.max(2, this.extra));
                case NamelessVfx.PHASE_SHIFT -> this.renderPhaseShift(b, t);
                case NamelessVfx.ROT_POOL -> this.renderPool(b, t);
                default -> {
                }
            }
        }

        private void renderSlash(TexBatch b, float t) {
            float reveal = easeOut(t / 2.8f);
            float fade = 1.0f - clamp01((t - 2.8f) / 5.2f);
            if (fade <= 0.0f) {
                return;
            }
            boolean overhead = this.extra == NamelessVfx.SLASH_OVERHEAD;
            double dir = this.extra == NamelessVfx.SLASH_RIGHT ? -1.0 : 1.0;
            double from = overhead ? Math.toRadians(-40.0) : Math.toRadians(-92.0) * dir;
            double to = overhead ? Math.toRadians(115.0) : Math.toRadians(92.0) * dir;
            double end = from + (to - from) * reveal;
            double outer = this.size * 0.96;
            double inner = this.size * 0.42;
            b.slashArc(BossVfxRender.SLASH_HEAVY, this.x, this.y, this.z, this.fx, this.fz,
                    inner, outer, from, end, overhead, 0.55 * dir, overhead ? 0.5 : 1.35, 18,
                    hotWhite(0.95f * fade), crimson(0.78f * fade));
            b.slashArc(BossVfxRender.SLASH_HEAVY, this.x, this.y, this.z, this.fx, this.fz,
                    outer * 0.72, outer * 1.08, from + (end - from) * 0.2, end, overhead, 0.55 * dir, overhead ? 0.5 : 1.35, 12,
                    gold(0.85f * fade), crimson(0.25f * fade));
            if (overhead && t < 6.0f) {
                b.flat(BossVfxRender.SCORCH_CRACKS, this.x + this.fx * (this.size * 0.62), this.y + 0.05, this.z + this.fz * (this.size * 0.62),
                        1.8 * easeOut(t / 2.5f), this.yawRad, core(0.75f * fade));
            }
        }

        private void renderWave(TexBatch b, float t) {
            float fade = 1.0f - clamp01((t - (this.life - 4.0f)) / 4.0f);
            float in = easeOut(t / 2.0f);
            float a = in * fade;
            if (a <= 0.0f) {
                return;
            }
            double dist = WAVE_SPEED * t;
            double wx = this.x + this.fx * dist;
            double wz = this.z + this.fz * dist;
            b.crescentWave(wx, this.y + 1.35, wz, this.fx, this.fz, 0.14 * Mth.sin(t * 0.45f),
                    1.35, this.size * 0.95, crimson(0.92f * a), hotWhite(0.95f * a));
            double trailLen = Math.min(dist, 4.5);
            if (trailLen > 0.4) {
                b.tiledGroundLine(BossVfxRender.TELEGRAPH_STRIP, wx - this.fx * trailLen, this.y + 0.05, wz - this.fz * trailLen,
                        this.fx, this.fz, trailLen, 0.85, 2.2, t * 0.7f, bloodDark(0.45f * a), core(0.8f * a));
            }
        }

        private void renderSlam(TexBatch b, float t) {
            int ticks = Math.max(2, this.extra);
            float prog = easeOut(t / ticks);
            float fade = 1.0f - clamp01((t - ticks) / 8.0f);
            if (fade <= 0.0f) {
                return;
            }
            double startR = this.innerRadius;
            double r = startR + (this.size - startR) * prog;
            double bandW = Math.max(0.8, (this.size - startR) * 0.24);
            double bandIn = Math.max(startR, r - bandW);
            b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.06, this.z, bandIn, r + 0.2, 0.0, Math.PI * 2.0, 2.6,
                    hotWhite(0.92f * fade), crimson(0.82f * fade));
            double wallH = (1.35 * (1.0 - prog * 0.55) + 0.35) * fade;
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y, this.y + wallH, this.z, r, 0.0, Math.PI * 2.0, 2.8,
                    gold(0.85f * fade), crimson(0.92f * fade));
            if (startR <= 0.1) {
                b.flat(BossVfxRender.SCORCH_CRACKS, this.x, this.y + 0.04, this.z, Math.min(this.size * 0.88, r + 0.6), this.yawRad, core(0.75f * fade));
                b.flat(BossVfxRender.SHOCKWAVE_RING, this.x, this.y + 0.07, this.z, r, this.yawRad + t * 0.06, crimson(0.65f * fade));
            }
        }

        private void renderTelegraphRing(TexBatch b, float t) {
            int dur = Math.max(2, this.extra);
            float pulse = 0.78f + 0.22f * Mth.sin(t * 0.75f);
            double rOut = this.size;
            double rIn = this.innerRadius;
            if (t <= dur) {
                float prog = clamp01(t / dur);
                b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.07, this.z, Math.max(rIn, rOut - 0.55), rOut, 0.0, Math.PI * 2.0, 2.5,
                        gold(0.85f * pulse), crimson(0.85f * pulse));
                if (rIn > 0.1) {
                    b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.07, this.z, rIn, Math.min(rOut, rIn + 0.45), 0.0, Math.PI * 2.0, 2.2,
                            crimson(0.75f * pulse), gold(0.75f * pulse));
                    double fill = rIn + (rOut - rIn) * prog;
                    b.ringBand(BossVfxRender.TELEGRAPH_STRIP, this.x, this.y + 0.055, this.z, Math.max(rIn, fill - 0.8), fill, 0.0, Math.PI * 2.0, 3.0,
                            core(0.75f), crimson(0.3f));
                } else {
                    b.flat(BossVfxRender.SIGIL_RING, this.x, this.y + 0.05, this.z, rOut, t * 0.035, crimson(0.45f + 0.3f * prog));
                    double sweep = Math.max(0.4, rOut * prog);
                    b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.065, this.z, Math.max(0.0, sweep - 0.65), sweep, 0.0, Math.PI * 2.0, 2.4,
                            hotWhite(0.75f), crimson(0.35f));
                }
            } else {
                float flash = 1.0f - clamp01((t - dur) / 4.0f);
                b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.075, this.z, rIn, rOut, 0.0, Math.PI * 2.0, 2.8,
                        hotWhite(0.9f * flash), core(0.7f * flash));
            }
        }

        private void renderTelegraphLine(TexBatch b, float t) {
            int dur = Math.max(2, this.extra);
            float a = t <= dur ? 0.65f + 0.25f * Mth.sin(t * 0.9f) : 0.85f * (1.0f - clamp01((t - dur) / 4.0f));
            if (a <= 0.0f) {
                return;
            }
            b.tiledGroundLine(BossVfxRender.TELEGRAPH_STRIP, this.x + this.fx * 0.8, this.y + 0.065, this.z + this.fz * 0.8,
                    this.fx, this.fz, this.size, 1.05, 2.6, t * 0.65f, crimson(0.72f * a), gold(0.95f * a));
            int nodes = Math.max(3, Math.round(this.size / 4.5f));
            float prog = clamp01(t / dur);
            for (int i = 1; i <= nodes; i++) {
                double d = this.size * i / nodes;
                double cx = this.x + this.fx * d;
                double cz = this.z + this.fz * d;
                b.flat(BossVfxRender.SIGIL_RING, cx, this.y + 0.075, cz, 1.05 + 0.45 * prog, t * 0.08 + i, core(0.78f * a));
            }
        }

        private void renderPillar(TexBatch b, float local, double radius, double height, float strength) {
            if (local < 0.0f) {
                return;
            }
            float rise = easeOut(local / 2.8f);
            float fade = (1.0f - clamp01((local - 4.0f) / 10.0f)) * strength;
            if (fade <= 0.0f) {
                return;
            }
            double h = height * rise;
            b.pillar(this.x, this.y, this.z, radius, h, local * 0.14, crimson(0.92f * fade), hotWhite(0.95f * fade));
            b.flat(BossVfxRender.SCORCH_CRACKS, this.x, this.y + 0.045, this.z, radius * 1.55, this.yawRad, core(0.8f * fade));
            double ring = radius * (0.9 + 1.5 * easeOut(local / 4.5f));
            b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.065, this.z, ring * 0.65, ring, 0.0, Math.PI * 2.0, 2.0,
                    gold(0.8f * fade), crimson(0.6f * fade));
        }

        private void renderRoar(TexBatch b, float t) {
            for (int wave = 0; wave < 3; wave++) {
                float local = t - wave * 4.0f;
                if (local < 0.0f || local > 11.0f) {
                    continue;
                }
                float prog = easeOut(local / 11.0f);
                float fade = 1.0f - clamp01(local / 11.0f);
                double r = Math.max(0.8, this.size * prog);
                double cy = this.y + 0.25 + wave * 0.65;
                b.crownWall(BossVfxRender.CROWN_WALL, this.x, cy, cy + 2.0, this.z, r, 0.0, Math.PI * 2.0, 3.0,
                        wave == 1 ? gold(0.88f * fade) : core(0.85f * fade), crimson(0.88f * fade));
            }
            float fade = 1.0f - clamp01(t / 15.0f);
            double r = this.size * easeOut(t / 8.0f);
            b.flat(BossVfxRender.SHOCKWAVE_RING, this.x, this.y + 0.06, this.z, r, t * 0.05, crimson(0.8f * fade));
        }

        private void renderShieldSpark(TexBatch b, float t) {
            float fade = 1.0f - clamp01(t / 6.0f);
            if (fade <= 0.0f) {
                return;
            }
            double cx = this.x + this.fx * 1.38, cy = this.y + 1.68, cz = this.z + this.fz * 1.38;
            double r = (0.75 + 0.45 * this.size) * (0.65 + 0.45 * easeOut(t / 2.0f));
            b.billboardFrameV(BossVfxRender.IMPACT, cx, cy, cz, r, t * 0.35, (int) (t * 0.8f), 4, hotWhite(0.95f * fade));
            b.billboard(BossVfxRender.SHIELD_HEX, cx, cy, cz, r * 0.85, r * 0.85, 0.0, gold(0.85f * fade));
        }

        private void renderBarrier(TexBatch b, float t) {
            float in = clamp01(t / 4.0f);
            float out = 1.0f - clamp01((t - (this.life - 5.0f)) / 5.0f);
            float a = in * out * (0.8f + 0.2f * Mth.sin(t * 0.55f));
            if (a <= 0.0f) {
                return;
            }
            double face = this.faceAngle();
            double sx = this.fz, sz = -this.fx;
            double cx = this.x + this.fx * 1.45, cy = this.y + 1.55, cz = this.z + this.fz * 1.45;
            double hw = 1.35, hh = 1.35;
            b.quad(BossVfxRender.SHIELD_HEX,
                    cx - sx * hw, cy + hh, cz - sz * hw, 0.0f, 0.0f, gold(0.88f * a),
                    cx + sx * hw, cy + hh, cz + sz * hw, 1.0f, 0.0f, gold(0.88f * a),
                    cx + sx * hw, cy - hh, cz + sz * hw, 1.0f, 1.0f, core(0.92f * a),
                    cx - sx * hw, cy - hh, cz - sz * hw, 0.0f, 1.0f, core(0.92f * a));
            double span = Math.toRadians(68.0);
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y + 0.05, this.y + 0.95, this.z, 1.75, face - span, face + span, 2.0,
                    gold(0.75f * a), crimson(0.85f * a));
        }

        private void renderShieldBreak(TexBatch b, float t) {
            float fade = 1.0f - clamp01(t / 12.0f);
            if (fade <= 0.0f) {
                return;
            }
            double cx = this.x + this.fx * 1.35, cy = this.y + 1.65, cz = this.z + this.fz * 1.35;
            b.billboardFrameV(BossVfxRender.IMPACT, cx, cy, cz, 1.8 * easeOut(t / 4.0f), 0.2, (int) (t * 0.5f), 4, hotWhite(0.9f * fade));
            double sx = this.fz, sz = -this.fx;
            double spread = 2.6 * easeOut(t / 10.0f);
            for (int i = 0; i < 8; i++) {
                double ang = Math.PI * 2.0 * i / 8.0 + 0.35;
                double ox = (sx * Math.cos(ang) + this.fx * 0.35) * spread;
                double oy = Math.sin(ang) * spread - 0.04 * t * t;
                double oz = (sz * Math.cos(ang) + this.fz * 0.35) * spread;
                b.billboard(BossVfxRender.SHARD, cx + ox, cy + oy, cz + oz, 0.42, 0.42, ang + t * 0.35, gold(0.92f * fade));
            }
            double r = 1.5 + 2.4 * easeOut(t / 8.0f);
            b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.06, this.z, r * 0.68, r, 0.0, Math.PI * 2.0, 2.2,
                    gold(0.85f * fade), crimson(0.75f * fade));
        }

        private void renderShieldCounter(TexBatch b, float t) {
            int ticks = Math.max(4, this.extra);
            float prog = easeOut(t / ticks);
            float fade = 1.0f - clamp01((t - ticks) / 6.0f);
            if (fade <= 0.0f) {
                return;
            }
            double r = this.size * prog;
            b.flat(BossVfxRender.SIGIL_RING, this.x, this.y + 0.06, this.z, r, -t * 0.08, gold(0.88f * fade));
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y, this.y + 2.1 * (1.0 - prog * 0.4), this.z, r, 0.0, Math.PI * 2.0, 2.8,
                    hotWhite(0.92f * fade), gold(0.9f * fade));
            double cx = this.x + this.fx * 1.4, cy = this.y + 1.6, cz = this.z + this.fz * 1.4;
            b.billboard(BossVfxRender.SHIELD_HEX, cx, cy, cz, 1.5 + prog, 1.5 + prog, t * 0.12, hotWhite(0.85f * fade));
        }

        private void renderCharge(TexBatch b, float t, double radius, int dur) {
            float prog = clamp01(t / dur);
            if (t <= dur) {
                double r = radius * (1.35 - 0.65 * prog);
                b.flat(BossVfxRender.SIGIL_RING, this.x, this.y + 0.06, this.z, r, t * 0.08, crimson(0.45f + 0.45f * prog));
                b.pillar(this.x, this.y, this.z, 0.55 + 0.45 * prog, 3.4 * prog + 0.6, -t * 0.12, crimson(0.6f * prog), gold(0.75f * prog));
                for (int i = 0; i < 4; i++) {
                    double a = t * 0.18 + i * (Math.PI * 0.5);
                    double gr = r * 0.85;
                    b.billboardFrameU(BossVfxRender.GLYPH, this.x + Math.cos(a) * gr, this.y + 0.9 + 1.2 * prog, this.z + Math.sin(a) * gr,
                            0.38, 0.0, i + (int) (t * 0.25f), 4, gold(0.85f * prog));
                }
            } else {
                float fade = 1.0f - clamp01((t - dur) / 4.0f);
                b.flat(BossVfxRender.SHOCKWAVE_RING, this.x, this.y + 0.07, this.z, radius * 1.1, this.yawRad, hotWhite(0.85f * fade));
            }
        }

        private void renderPhaseShift(TexBatch b, float t) {
            if (t < 30.0f) {
                this.renderCharge(b, t, 5.2, 30);
                double r2 = 7.2 * (1.0 - t / 30.0f) + 0.8;
                b.ringBand(BossVfxRender.RING_BAND, this.x, this.y + 0.08, this.z, Math.max(0.2, r2 - 0.6), r2, 0.0, Math.PI * 2.0, 2.4,
                        gold(0.85f), crimson(0.8f));
                return;
            }
            float local = t - 30.0f;
            this.renderPillar(b, local, 2.1, 13.5, 1.25f);
            float fade = 1.0f - clamp01(local / 20.0f);
            double r = this.size * 2.4 * easeOut(local / 11.0f);
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y, this.y + 2.4 * fade, this.z, r, 0.0, Math.PI * 2.0, 3.2,
                    gold(0.9f * fade), crimson(0.95f * fade));
            b.flat(BossVfxRender.SIGIL_RING, this.x, this.y + 0.055, this.z, Math.min(8.5, r), local * 0.06, core(0.85f * fade));
        }

        private void renderPool(TexBatch b, float t) {
            float in = clamp01(t / 5.0f);
            float out = 1.0f - clamp01((t - (this.life - 8.0f)) / 8.0f);
            float a = in * out;
            if (a <= 0.0f) {
                return;
            }
            float pulse = 0.8f + 0.2f * Mth.sin(t * 0.45f);
            b.flat(BossVfxRender.SCORCH_CRACKS, this.x, this.y + 0.05, this.z, 2.7, this.yawRad, crimson(0.75f * a * pulse));
            b.flat(BossVfxRender.SIGIL_RING, this.x, this.y + 0.06, this.z, 2.85, t * 0.03, core(0.65f * a * pulse));
            b.crownWall(BossVfxRender.CROWN_WALL, this.x, this.y, this.y + 0.85 * pulse, this.z, 2.55, 0.0, Math.PI * 2.0, 2.4,
                    core(0.55f * a), crimson(0.75f * a));
        }
    }
}
