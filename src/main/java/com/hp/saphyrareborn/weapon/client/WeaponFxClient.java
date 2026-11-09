package com.hp.saphyrareborn.weapon.client;

import static com.hp.saphyrareborn.boss.client.BossVfxRender.clamp01;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.easeOut;
import static com.hp.saphyrareborn.boss.client.BossVfxRender.rgba;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.boss.client.BossVfxRender;
import com.hp.saphyrareborn.boss.client.BossVfxRender.Painter;
import com.hp.saphyrareborn.weapon.WeaponFx;
import com.hp.saphyrareborn.weapon.WeaponStyle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Vector3f;

@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class WeaponFxClient {
    private static final int MAX_EFFECTS = 128;
    private static final double SWING_RANGE_SQR = 64.0 * 64.0;
    private static final byte TRAIL = -1;
    private static final ResourceLocation SPARK = tex("spark");
    private static final ResourceLocation RUNE = tex("rune_circle");
    private static final ResourceLocation BOLT = tex("bolt");
    private static final ResourceLocation SPLASH = tex("splash");
    private static final ResourceLocation SUN = tex("sun");
    private static final List<Fx> ACTIVE = new ArrayList<>();
    private static final Map<Integer, Integer> LAST_SWING = new HashMap<>();
    private static final Map<Integer, Integer> SWING_COUNT = new HashMap<>();
    private static ClientLevel lastLevel;

    private WeaponFxClient() {
    }

    private static ResourceLocation tex(String name) {
        return ResourceLocation.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "textures/vfx/" + name + ".png");
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level != lastLevel) {
            ACTIVE.clear();
            LAST_SWING.clear();
            SWING_COUNT.clear();
            WeaponFx.INBOX.clear();
            lastLevel = level;
        }
        if (level == null || mc.player == null) {
            return;
        }
        while (!WeaponFx.INBOX.isEmpty()) {
            WeaponFx payload = WeaponFx.INBOX.poll();
            WeaponStyle style = WeaponStyle.byId(payload.weapon());
            if (style != null && ACTIVE.size() < MAX_EFFECTS) {
                ACTIVE.add(new Fx(payload.kind(), style, new Vec3(payload.x(), payload.y(), payload.z()), payload.yaw(),
                        Math.max(0.3f, Math.min(payload.size(), 24.0f)), Math.max(-400, Math.min(payload.extra(), 400)), null));
            }
        }
        if (mc.isPaused()) {
            return;
        }
        detectSwings(level, mc.player);
        Iterator<Fx> it = ACTIVE.iterator();
        while (it.hasNext()) {
            Fx fx = it.next();
            fx.tickParticles(level, level.random);
            fx.age++;
            if (fx.age > fx.life || fx.owner != null && fx.owner.isRemoved()) {
                it.remove();
            }
        }
    }

    private static void detectSwings(ClientLevel level, Player self) {
        for (Player player : level.players()) {
            int id = player.getId();
            if (!player.swinging) {
                LAST_SWING.remove(id);
                continue;
            }
            int last = LAST_SWING.getOrDefault(id, Integer.MAX_VALUE);
            LAST_SWING.put(id, player.swingTime);
            if (player.swingTime >= last || player.swingingArm != InteractionHand.MAIN_HAND || player.distanceToSqr(self) > SWING_RANGE_SQR) {
                continue;
            }
            WeaponStyle style = WeaponStyle.of(player.getMainHandItem());
            if (style == null || ACTIVE.size() >= MAX_EFFECTS) {
                continue;
            }
            int count = SWING_COUNT.merge(id, 1, Integer::sum);
            ACTIVE.add(new Fx(TRAIL, style, player.position(), player.getYRot(), style.radius, count, player));
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
        Canvas canvas = new Canvas(buffers, pose.last(), camera.getLeftVector(), camera.getUpVector());
        for (Fx fx : ACTIVE) {
            if (cam.distanceToSqr(fx.pos) < BossVfxRender.RENDER_RANGE_SQR) {
                fx.render(canvas, fx.age + partial, partial);
            }
        }
        buffers.endLastBatch();
        buffers.endBatch(BossVfxRender.GLOW);
        pose.popPose();
    }

    private static float[] color(int rgb, float a) {
        return rgba(((rgb >> 16) & 255) / 255.0f, ((rgb >> 8) & 255) / 255.0f, (rgb & 255) / 255.0f, a);
    }

    private static float[] white(float a) {
        return rgba(1.0f, 1.0f, 1.0f, a);
    }

    private record Canvas(MultiBufferSource.BufferSource buffers, PoseStack.Pose pose, Vector3f left, Vector3f up) {
        Painter glow() {
            return new Painter(this.buffers.getBuffer(BossVfxRender.GLOW), this.pose);
        }

        Sprite sprite(ResourceLocation texture) {
            return new Sprite(this.buffers.getBuffer(RenderType.entityTranslucentEmissive(texture)), this.pose);
        }
    }

    private record Sprite(VertexConsumer vc, PoseStack.Pose pose) {
        void v(double[] p, float u, float v, float[] c) {
            this.vc.addVertex(this.pose, (float) p[0], (float) p[1], (float) p[2]).setColor(c[0], c[1], c[2], c[3]).setUv(u, v)
                    .setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(this.pose, 0.0f, 1.0f, 0.0f);
        }

        void quad(double[] p0, float u0, float v0, double[] p1, float u1, float v1, double[] p2, float u2, float v2, double[] p3, float u3, float v3, float[] c0, float[] c1) {
            this.v(p0, u0, v0, c0);
            this.v(p1, u1, v1, c0);
            this.v(p2, u2, v2, c1);
            this.v(p3, u3, v3, c1);
            this.v(p3, u3, v3, c1);
            this.v(p2, u2, v2, c1);
            this.v(p1, u1, v1, c0);
            this.v(p0, u0, v0, c0);
        }

        void flat(double cx, double y, double cz, double half, double angle, float vMin, float vMax, float[] c) {
            double ca = Math.cos(angle) * half, sa = Math.sin(angle) * half;
            this.quad(new double[]{cx - ca + sa, y, cz - sa - ca}, 0.0f, vMin, new double[]{cx + ca + sa, y, cz + sa - ca}, 1.0f, vMin,
                    new double[]{cx + ca - sa, y, cz + sa + ca}, 1.0f, vMax, new double[]{cx - ca - sa, y, cz - sa + ca}, 0.0f, vMax, c, c);
        }

        void billboard(double cx, double cy, double cz, double half, Vector3f left, Vector3f up, float vMin, float vMax, float[] c) {
            double lx = left.x() * half, ly = left.y() * half, lz = left.z() * half;
            double ux = up.x() * half, uy = up.y() * half, uz = up.z() * half;
            this.quad(new double[]{cx + lx + ux, cy + ly + uy, cz + lz + uz}, 0.0f, vMin, new double[]{cx - lx + ux, cy - ly + uy, cz - lz + uz}, 1.0f, vMin,
                    new double[]{cx - lx - ux, cy - ly - uy, cz - lz - uz}, 1.0f, vMax, new double[]{cx + lx - ux, cy + ly - uy, cz + lz - uz}, 0.0f, vMax, c, c);
        }

        void strip(double[] a, double[] b, double[] side, double width, float[] c) {
            double h = width * 0.5;
            this.quad(new double[]{a[0] + side[0] * h, a[1] + side[1] * h, a[2] + side[2] * h}, 0.0f, 0.0f,
                    new double[]{b[0] + side[0] * h, b[1] + side[1] * h, b[2] + side[2] * h}, 1.0f, 0.0f,
                    new double[]{b[0] - side[0] * h, b[1] - side[1] * h, b[2] - side[2] * h}, 1.0f, 1.0f,
                    new double[]{a[0] - side[0] * h, a[1] - side[1] * h, a[2] - side[2] * h}, 0.0f, 1.0f, c, c);
        }
    }

    private static final class Fx {
        private final byte kind;
        private final WeaponStyle style;
        private final float yaw;
        private final float size;
        private final int extra;
        private final Player owner;
        private final int life;
        private final long seed;
        private final double fx;
        private final double fz;
        private final float pitch;
        private Vec3 pos;
        private int age;

        Fx(byte kind, WeaponStyle style, Vec3 pos, float yaw, float size, int extra, Player owner) {
            this.kind = kind;
            this.style = style;
            this.pos = pos;
            this.yaw = yaw;
            this.size = size;
            this.extra = extra;
            this.owner = owner;
            this.pitch = owner != null ? owner.getXRot() : extra;
            double rad = Math.toRadians(yaw);
            this.fx = -Math.sin(rad);
            this.fz = Math.cos(rad);
            this.seed = Double.doubleToLongBits(pos.x * 31.0 + pos.z) ^ kind;
            this.life = switch (kind) {
                case TRAIL -> 9;
                case WeaponFx.SPARK, WeaponFx.STAFF_CAST -> 7;
                case WeaponFx.CROSS_SLASH -> 9;
                case WeaponFx.DASH_SLASH -> 12;
                case WeaponFx.RUNE_CIRCLE -> Math.max(2, extra) + 2;
                case WeaponFx.RUNE_BURST, WeaponFx.STAFF_NOVA, WeaponFx.GROUND_SLAM -> 16;
                case WeaponFx.GRIEF_BURST, WeaponFx.MIRROR_BURST -> 12;
                case WeaponFx.RAIN_SPLASH, WeaponFx.LIGHTNING, WeaponFx.SUN_FLARE -> 10;
                case WeaponFx.ROT_SPLASH -> 24;
                default -> 0;
            };
        }

        void tickParticles(ClientLevel level, RandomSource r) {
            if (this.age != 0) {
                return;
            }
            double x = this.pos.x, y = this.pos.y, z = this.pos.z;
            switch (this.kind) {
                case WeaponFx.DASH_SLASH -> {
                    for (int i = 0; i < 6; i++) {
                        double d = r.nextDouble() * this.size;
                        level.addParticle(ParticleTypes.CLOUD, x + this.fx * d, y + 0.2, z + this.fz * d, 0.0, 0.02, 0.0);
                    }
                    BossVfxRender.addShake(x, y, z, 0.25f, 10.0);
                }
                case WeaponFx.GROUND_SLAM -> {
                    BossVfxRender.debris(level, r, x, y, z, 14, 0.3);
                    BossVfxRender.addShake(x, y, z, 0.45f, 14.0);
                }
                case WeaponFx.RUNE_BURST -> {
                    BossVfxRender.addShake(x, y, z, 0.35f, 16.0);
                    for (int i = 0; i < 10; i++) {
                        double a = r.nextDouble() * Math.PI * 2.0;
                        level.addParticle(ParticleTypes.END_ROD, x + Math.cos(a) * this.size * 0.7, y + 0.2, z + Math.sin(a) * this.size * 0.7, 0.0, 0.12 + r.nextDouble() * 0.1, 0.0);
                    }
                }
                case WeaponFx.STAFF_NOVA -> {
                    BossVfxRender.addShake(x, y, z, 0.4f, 14.0);
                    BossVfxRender.debris(level, r, x, y, z, 10, 0.25);
                }
                case WeaponFx.RAIN_SPLASH -> {
                    for (int i = 0; i < 10; i++) {
                        level.addParticle(ParticleTypes.SPLASH, x + r.nextGaussian() * 0.4, y + 0.2, z + r.nextGaussian() * 0.4, 0.0, 0.2, 0.0);
                    }
                }
                case WeaponFx.LIGHTNING -> {
                    for (int i = 0; i < 8; i++) {
                        level.addParticle(ParticleTypes.ELECTRIC_SPARK, x + r.nextGaussian() * 0.4, y + r.nextDouble() * this.size, z + r.nextGaussian() * 0.4, r.nextGaussian() * 0.1, 0.05, r.nextGaussian() * 0.1);
                    }
                    level.addParticle(ParticleTypes.FLASH, x, y + this.size * 0.5, z, 0.0, 0.0, 0.0);
                    BossVfxRender.addShake(x, y, z, 0.3f, 12.0);
                }
                case WeaponFx.SUN_FLARE -> {
                    for (int i = 0; i < (this.extra == 1 ? 6 : 3); i++) {
                        level.addParticle(ParticleTypes.FLAME, x + r.nextGaussian() * 0.3, y + r.nextGaussian() * 0.3, z + r.nextGaussian() * 0.3, r.nextGaussian() * 0.03, 0.03, r.nextGaussian() * 0.03);
                    }
                }
                case WeaponFx.ROT_SPLASH -> {
                    DustParticleOptions dust = new DustParticleOptions(new Vector3f(0.5f, 0.23f, 0.16f), 1.2f);
                    for (int i = 0; i < (this.extra == 1 ? 10 : 5); i++) {
                        level.addParticle(dust, x + r.nextGaussian() * 0.4, y + 0.3 + r.nextDouble() * 1.2, z + r.nextGaussian() * 0.4, 0.0, -0.05, 0.0);
                    }
                }
                case WeaponFx.GRIEF_BURST -> {
                    for (int i = 0; i < 6; i++) {
                        level.addParticle(ParticleTypes.SOUL, x + r.nextGaussian() * 0.4, y + 0.5 + r.nextDouble(), z + r.nextGaussian() * 0.4, 0.0, 0.06, 0.0);
                    }
                }
                case WeaponFx.MIRROR_BURST -> {
                    for (int i = 0; i < 8; i++) {
                        double a = r.nextDouble() * Math.PI * 2.0;
                        level.addParticle(ParticleTypes.END_ROD, x, y, z, Math.cos(a) * 0.25, r.nextGaussian() * 0.1, Math.sin(a) * 0.25);
                    }
                }
                default -> {
                }
            }
        }

        void render(Canvas c, float t, float partial) {
            if (this.owner != null) {
                this.pos = this.owner.getPosition(partial);
            }
            switch (this.kind) {
                case TRAIL -> this.renderTrail(c, t, partial);
                case WeaponFx.SPARK -> this.renderSpark(c, t, this.pos, this.size);
                case WeaponFx.STAFF_CAST -> this.renderStaffCast(c, t);
                case WeaponFx.CROSS_SLASH -> this.renderCross(c, t);
                case WeaponFx.DASH_SLASH -> this.renderDash(c, t);
                case WeaponFx.RUNE_CIRCLE -> this.renderRune(c, t);
                case WeaponFx.RUNE_BURST -> this.renderRuneBurst(c, t);
                case WeaponFx.GROUND_SLAM -> this.renderSlam(c, t);
                case WeaponFx.STAFF_NOVA -> this.renderNova(c, t);
                case WeaponFx.GRIEF_BURST -> this.renderGrief(c, t);
                case WeaponFx.RAIN_SPLASH -> this.renderSplash(c, t);
                case WeaponFx.LIGHTNING -> this.renderLightning(c, t);
                case WeaponFx.SUN_FLARE -> this.renderSun(c, t);
                case WeaponFx.ROT_SPLASH -> this.renderRot(c, t);
                case WeaponFx.MIRROR_BURST -> this.renderMirror(c, t);
                default -> {
                }
            }
        }

        private void renderTrail(Canvas c, float t, float partial) {
            float reveal = easeOut(t / 2.5f);
            float fade = 1.0f - clamp01((t - 2.5f) / 5.5f);
            if (fade <= 0.0f) {
                return;
            }
            Minecraft mc = Minecraft.getInstance();
            boolean firstPerson = this.owner == mc.player && mc.options.getCameraType().isFirstPerson();
            float alpha = fade * (firstPerson ? 0.55f : 0.95f);
            boolean even = this.extra % 2 == 0;
            double dir = even ? 1.0 : -1.0;
            double tilt = this.style.heavy && even ? Math.toRadians(68.0) : Math.toRadians(even ? 18.0 : -22.0);
            Vec3 f = Vec3.directionFromRotation(Mth.clamp(this.pitch, -60.0f, 60.0f), this.yaw);
            Vec3 r = f.cross(new Vec3(0.0, 1.0, 0.0));
            if (r.lengthSqr() < 1.0E-4) {
                r = new Vec3(-this.fz, 0.0, this.fx);
            }
            r = r.normalize();
            Vec3 u = r.cross(f).normalize();
            Vec3 side = r.scale(Math.cos(tilt)).add(u.scale(Math.sin(tilt)));
            Vec3 center = this.pos.add(0.0, this.owner.getEyeHeight() - 0.4, 0.0).add(f.scale(firstPerson ? 0.45 : 0.15));
            double from = Math.toRadians(-105.0) * dir;
            double to = Math.toRadians(85.0) * dir;
            double head = from + (to - from) * reveal;
            double tail = from + (to - from) * clamp01((t - 1.5f) / 6.0f) * 0.9;
            double outer = this.size;
            double inner = this.size * 0.5;
            Sprite s = c.sprite(this.style.trail);
            float[] col = white(alpha);
            int segs = 18;
            for (int i = 0; i < segs; i++) {
                double k0 = (double) i / segs, k1 = (double) (i + 1) / segs;
                double a0 = tail + (head - tail) * k0, a1 = tail + (head - tail) * k1;
                double[] o0 = arc(center, f, side, a0, outer), o1 = arc(center, f, side, a1, outer);
                double[] i0 = arc(center, f, side, a0, inner), i1 = arc(center, f, side, a1, inner);
                s.quad(o0, (float) k0, 0.0f, o1, (float) k1, 0.0f, i1, (float) k1, 1.0f, i0, (float) k0, 1.0f, col, col);
            }
        }

        private static double[] arc(Vec3 c, Vec3 f, Vec3 side, double a, double radius) {
            double cos = Math.cos(a) * radius, sin = Math.sin(a) * radius;
            return new double[]{c.x + f.x * cos + side.x * sin, c.y + f.y * cos + side.y * sin, c.z + f.z * cos + side.z * sin};
        }

        private void renderSpark(Canvas c, float t, Vec3 at, double scale) {
            int frame = Math.min(3, (int) (t / 1.6f));
            float a = 1.0f - clamp01((t - 4.0f) / 3.0f);
            if (a <= 0.0f) {
                return;
            }
            c.sprite(SPARK).billboard(at.x, at.y, at.z, 0.55 * scale * (1.0 + frame * 0.15), c.left(), c.up(), frame * 0.25f, (frame + 1) * 0.25f, color(this.style.light, a));
        }

        private void renderStaffCast(Canvas c, float t) {
            Vec3 look = Vec3.directionFromRotation(this.pitch, this.yaw);
            this.renderSpark(c, t, this.pos.add(look.scale(1.0)), 1.6);
            float a = 1.0f - clamp01(t / 7.0f);
            Vec3 at = this.pos.add(look.scale(1.2));
            Vec3 rr = look.cross(new Vec3(0.0, 1.0, 0.0));
            if (rr.lengthSqr() < 1.0E-4) {
                return;
            }
            rr = rr.normalize();
            Vec3 uu = rr.cross(look).normalize();
            double half = 0.6 + 0.5 * easeOut(t / 4.0f);
            double[] p0 = {at.x + (rr.x + uu.x) * half, at.y + (rr.y + uu.y) * half, at.z + (rr.z + uu.z) * half};
            double[] p1 = {at.x + (-rr.x + uu.x) * half, at.y + (-rr.y + uu.y) * half, at.z + (-rr.z + uu.z) * half};
            double[] p2 = {at.x + (-rr.x - uu.x) * half, at.y + (-rr.y - uu.y) * half, at.z + (-rr.z - uu.z) * half};
            double[] p3 = {at.x + (rr.x - uu.x) * half, at.y + (rr.y - uu.y) * half, at.z + (rr.z - uu.z) * half};
            c.sprite(RUNE).quad(p0, 0.0f, 0.0f, p1, 1.0f, 0.0f, p2, 1.0f, 1.0f, p3, 0.0f, 1.0f, color(this.style.light, a), color(this.style.light, a));
        }

        private void renderCross(Canvas c, float t) {
            float reveal = easeOut(t / 2.0f);
            float fade = 1.0f - clamp01((t - 3.0f) / 5.0f);
            if (fade <= 0.0f) {
                return;
            }
            double rx = this.fz, rz = -this.fx;
            Sprite s = c.sprite(this.style.trail);
            for (int k = 0; k < 2; k++) {
                double sgn = k == 0 ? 1.0 : -1.0;
                double dx = rx * sgn * 0.7071, dy = 0.7071, dz = rz * sgn * 0.7071;
                double len = this.size * reveal;
                double[] a = {this.pos.x - dx * this.size, this.pos.y - dy * this.size, this.pos.z - dz * this.size};
                double[] b = {a[0] + dx * 2.0 * len, a[1] + dy * 2.0 * len, a[2] + dz * 2.0 * len};
                double[] side = {-rx * sgn * 0.7071, 0.7071, -rz * sgn * 0.7071};
                s.strip(a, b, side, 0.45, white(fade));
            }
            this.renderSpark(c, t, this.pos, 1.4);
        }

        private void renderDash(Canvas c, float t) {
            float fade = 1.0f - clamp01((t - 2.0f) / 9.0f);
            if (fade <= 0.0f) {
                return;
            }
            double len = this.size * easeOut(t / 2.0f);
            double rx = this.fz, rz = -this.fx;
            Sprite s = c.sprite(this.style.trail);
            for (double hy : new double[]{0.9, 1.5}) {
                double[] a = {this.pos.x, this.pos.y + hy, this.pos.z};
                double[] b = {this.pos.x + this.fx * len, this.pos.y + hy, this.pos.z + this.fz * len};
                s.strip(a, b, new double[]{0.0, 1.0, 0.0}, hy > 1.0 ? 0.5 : 0.8, white(fade));
            }
            Painter p = c.glow();
            p.quad(this.pos.x + rx * 1.2, this.pos.y + 0.06, this.pos.z + rz * 1.2, color(this.style.main, 0.0f), this.pos.x - rx * 1.2, this.pos.y + 0.06, this.pos.z - rz * 1.2, color(this.style.main, 0.0f),
                    this.pos.x - rx * 1.2 + this.fx * len, this.pos.y + 0.06, this.pos.z - rz * 1.2 + this.fz * len, color(this.style.light, 0.45f * fade),
                    this.pos.x + rx * 1.2 + this.fx * len, this.pos.y + 0.06, this.pos.z + rz * 1.2 + this.fz * len, color(this.style.light, 0.45f * fade));
        }

        private void renderRune(Canvas c, float t) {
            int dur = Math.max(2, this.extra);
            float in = easeOut(t / 4.0f);
            float pulse = 0.8f + 0.2f * Mth.sin(t * 1.3f);
            float a = in * pulse * (t > dur ? 1.0f - clamp01((t - dur) / 2.0f) : 1.0f);
            double r = this.size * (0.7 + 0.3 * in);
            c.sprite(RUNE).flat(this.pos.x, this.pos.y + 0.08, this.pos.z, r, t * 0.06, 0.0f, 1.0f, color(this.style.light, a));
            float fill = clamp01(t / dur);
            Painter p = c.glow();
            p.annulus(this.pos.x, this.pos.y + 0.06, this.pos.z, 0.0, this.size * fill, 40, color(this.style.main, 0.05f), color(this.style.main, 0.3f * a));
            p.wall(this.pos.x, this.pos.y, this.pos.y + 1.2 * fill, this.pos.z, this.size, 40, 0.0, Math.PI * 2.0, color(this.style.light, 0.4f * a), color(this.style.light, 0.0f));
        }

        private void renderRuneBurst(Canvas c, float t) {
            float fade = 1.0f - clamp01((t - 3.0f) / 12.0f);
            if (fade <= 0.0f) {
                return;
            }
            float grow = easeOut(t / 4.0f);
            c.sprite(RUNE).flat(this.pos.x, this.pos.y + 0.09, this.pos.z, this.size * (1.0 + 0.35 * grow), 0.4 + t * 0.12, 0.0f, 1.0f, color(this.style.light, fade));
            Painter p = c.glow();
            double rise = 7.0 * grow;
            p.beam(this.pos.x, this.pos.y, this.pos.z, this.pos.x, this.pos.y + rise, this.pos.z, this.size * 0.9 * fade, color(this.style.light, 0.75f * fade), color(this.style.main, 0.0f));
            p.annulus(this.pos.x, this.pos.y + 0.07, this.pos.z, this.size * grow * 0.85, this.size * grow * 1.05, 48, color(this.style.main, 0.0f), white(0.8f * fade));
            RandomSource r = RandomSource.create(this.seed);
            for (int i = 0; i < 8; i++) {
                double ang = Math.PI * 2.0 * i / 8 + r.nextDouble() * 0.3;
                double d = this.size * (0.4 + 0.5 * r.nextDouble());
                double bx = this.pos.x + Math.cos(ang) * d, bz = this.pos.z + Math.sin(ang) * d;
                p.beam(bx, this.pos.y, bz, bx, this.pos.y + (1.5 + r.nextDouble() * 2.0) * grow, bz, 0.35, color(this.style.light, 0.7f * fade), color(this.style.main, 0.0f));
            }
        }

        private void renderSlam(Canvas c, float t) {
            float prog = easeOut(t / 6.0f);
            float fade = 1.0f - clamp01((t - 4.0f) / 11.0f);
            if (fade <= 0.0f) {
                return;
            }
            double r = this.size * prog;
            Painter p = c.glow();
            p.annulus(this.pos.x, this.pos.y + 0.06, this.pos.z, Math.max(0.0, r - 0.45), r, 40, color(this.style.main, 0.0f), color(this.style.light, 0.85f * fade));
            p.annulus(this.pos.x, this.pos.y + 0.05, this.pos.z, 0.0, r, 40, color(this.style.main, 0.25f * fade), color(this.style.main, 0.05f * fade));
            p.wall(this.pos.x, this.pos.y, this.pos.y + 1.0 * (1.0 - prog) + 0.2, this.pos.z, r, 40, 0.0, Math.PI * 2.0, color(this.style.light, 0.5f * fade), color(this.style.light, 0.0f));
            RandomSource rs = RandomSource.create(this.seed);
            for (int i = 0; i < 7; i++) {
                double ang = Math.PI * 2.0 * i / 7 + rs.nextDouble() * 0.5;
                double len = this.size * (0.6 + 0.4 * rs.nextDouble()) * prog;
                double ex = this.pos.x + Math.cos(ang) * len, ez = this.pos.z + Math.sin(ang) * len;
                double sx = -Math.sin(ang) * 0.12, sz = Math.cos(ang) * 0.12;
                p.quad(this.pos.x + sx, this.pos.y + 0.07, this.pos.z + sz, color(this.style.light, 0.9f * fade), this.pos.x - sx, this.pos.y + 0.07, this.pos.z - sz, color(this.style.light, 0.9f * fade),
                        ex, this.pos.y + 0.07, ez, color(this.style.main, 0.0f), ex, this.pos.y + 0.07, ez, color(this.style.main, 0.0f));
            }
            this.renderSpark(c, t, this.pos.add(0.0, 0.4, 0.0), 2.2);
        }

        private void renderNova(Canvas c, float t) {
            float grow = easeOut(t / 5.0f);
            float fade = 1.0f - clamp01((t - 5.0f) / 10.0f);
            if (fade <= 0.0f) {
                return;
            }
            c.sprite(RUNE).flat(this.pos.x, this.pos.y + 0.08, this.pos.z, this.size * grow, t * 0.1, 0.0f, 1.0f, color(this.style.light, fade));
            Painter p = c.glow();
            double r = this.size * grow;
            p.annulus(this.pos.x, this.pos.y + 0.06, this.pos.z, Math.max(0.0, r - 0.5), r, 56, color(this.style.main, 0.0f), white(0.75f * fade));
            p.wall(this.pos.x, this.pos.y, this.pos.y + 1.6 * (1.0 - grow) + 0.3, this.pos.z, r, 56, 0.0, Math.PI * 2.0, color(this.style.main, 0.5f * fade), color(this.style.main, 0.0f));
        }

        private void renderGrief(Canvas c, float t) {
            float fade = 1.0f - clamp01((t - 2.0f) / 9.0f);
            if (fade <= 0.0f) {
                return;
            }
            float grow = easeOut(t / 3.0f);
            Painter p = c.glow();
            RandomSource rs = RandomSource.create(this.seed);
            for (int i = 0; i < 6; i++) {
                double ang = Math.PI * 2.0 * i / 6 + rs.nextDouble() * 0.4;
                double bx = this.pos.x + Math.cos(ang) * this.size * 0.6, bz = this.pos.z + Math.sin(ang) * this.size * 0.6;
                p.beam(bx, this.pos.y, bz, bx, this.pos.y + (1.8 + rs.nextDouble()) * grow, bz, 0.4, color(this.style.light, 0.8f * fade), color(this.style.main, 0.0f));
            }
            p.annulus(this.pos.x, this.pos.y + 0.06, this.pos.z, this.size * 0.5 * grow, this.size * grow, 32, color(this.style.main, 0.0f), color(this.style.light, 0.7f * fade));
            this.renderSpark(c, t, this.pos.add(0.0, 1.0, 0.0), 1.8);
        }

        private void renderSplash(Canvas c, float t) {
            int frame = Math.min(3, (int) (t / 2.0f));
            float a = 1.0f - clamp01((t - 6.0f) / 4.0f);
            if (a <= 0.0f) {
                return;
            }
            c.sprite(SPLASH).flat(this.pos.x, this.pos.y + 0.1, this.pos.z, this.size * (0.8 + frame * 0.2), 0.0, frame * 0.25f, (frame + 1) * 0.25f, color(this.style.light, a));
            c.sprite(SPLASH).billboard(this.pos.x, this.pos.y + 0.6, this.pos.z, this.size * 0.6, c.left(), c.up(), frame * 0.25f, (frame + 1) * 0.25f, color(this.style.light, a * 0.8f));
        }

        private void renderLightning(Canvas c, float t) {
            boolean on = ((int) t) % 3 != 2;
            float a = on ? 1.0f - clamp01((t - 5.0f) / 5.0f) : 0.25f;
            if (a <= 0.0f) {
                return;
            }
            double top = 7.0;
            double half = 0.7;
            double lx = c.left().x() * half, lz = c.left().z() * half;
            double[] p0 = {this.pos.x + lx, this.pos.y + top, this.pos.z + lz};
            double[] p1 = {this.pos.x - lx, this.pos.y + top, this.pos.z - lz};
            double[] p2 = {this.pos.x - lx, this.pos.y, this.pos.z - lz};
            double[] p3 = {this.pos.x + lx, this.pos.y, this.pos.z + lz};
            c.sprite(BOLT).quad(p0, 0.0f, 0.0f, p1, 1.0f, 0.0f, p2, 1.0f, 1.0f, p3, 0.0f, 1.0f, white(a), white(a));
            Painter p = c.glow();
            p.annulus(this.pos.x, this.pos.y + 0.06, this.pos.z, 0.0, 1.2 + 0.4 * this.size, 24, white(0.6f * a), color(this.style.light, 0.0f));
            this.renderSpark(c, t, this.pos.add(0.0, this.size * 0.6, 0.0), 1.6);
        }

        private void renderSun(Canvas c, float t) {
            float a = 1.0f - clamp01((t - 3.0f) / 7.0f);
            if (a <= 0.0f) {
                return;
            }
            double half = 0.45 * this.size * (0.7 + 0.5 * easeOut(t / 4.0f));
            c.sprite(SUN).billboard(this.pos.x, this.pos.y, this.pos.z, half, c.left(), c.up(), 0.0f, 1.0f, color(this.extra == 1 ? 0xFFF0A0 : this.style.light, a));
            if (this.extra == 1) {
                c.glow().beam(this.pos.x, this.pos.y + 4.0, this.pos.z, this.pos.x, this.pos.y - 0.6, this.pos.z, 0.5 * a, color(0xFFF6B0, 0.6f * a), color(0xF0D860, 0.0f));
            }
        }

        private void renderRot(Canvas c, float t) {
            float in = easeOut(t / 3.0f);
            float a = 1.0f - clamp01((t - 8.0f) / 16.0f);
            if (a <= 0.0f) {
                return;
            }
            double r = (0.6 + 0.5 * this.size) * in;
            Painter p = c.glow();
            p.annulus(this.pos.x, this.pos.y + 0.04, this.pos.z, 0.0, r, 24, color(0x4A1A18, 0.45f * a), color(this.style.main, 0.25f * a));
            p.annulus(this.pos.x, this.pos.y + 0.045, this.pos.z, r * 0.85, r, 24, color(this.style.main, 0.0f), color(this.style.light, 0.55f * a));
            if (t < 7.0f) {
                this.renderSpark(c, t, this.pos.add(0.0, 1.0, 0.0), this.extra == 1 ? 1.5 : 1.0);
            }
        }

        private void renderMirror(Canvas c, float t) {
            float fade = 1.0f - clamp01((t - 2.0f) / 9.0f);
            if (fade <= 0.0f) {
                return;
            }
            float grow = easeOut(t / 4.0f);
            Painter p = c.glow();
            RandomSource rs = RandomSource.create(this.seed);
            for (int i = 0; i < 10; i++) {
                double ang = Math.PI * 2.0 * i / 10 + rs.nextDouble() * 0.3;
                double elev = (rs.nextDouble() - 0.3) * 0.8;
                double d = this.size * grow;
                p.beam(this.pos.x, this.pos.y, this.pos.z, this.pos.x + Math.cos(ang) * d, this.pos.y + elev * d, this.pos.z + Math.sin(ang) * d, 0.18, white(0.9f * fade), color(this.style.main, 0.0f));
            }
            p.annulus(this.pos.x, this.pos.y, this.pos.z, this.size * grow * 0.85, this.size * grow, 32, color(this.style.main, 0.0f), color(this.style.light, 0.7f * fade));
            this.renderSpark(c, t, this.pos, 2.0);
        }
    }
}
