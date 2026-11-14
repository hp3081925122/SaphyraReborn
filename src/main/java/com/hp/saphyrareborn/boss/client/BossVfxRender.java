package com.hp.saphyrareborn.boss.client;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.joml.Vector3f;

@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class BossVfxRender {
    public static final RenderPipeline GLOW_PIPELINE = RenderPipelines.LIGHTNING.toBuilder()
            .withLocation(Identifier.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "pipeline/boss_glow"))
            .withCull(false)
            .build();
    public static final RenderType GLOW = RenderType.create("saphyra_boss_glow", RenderSetup.builder(GLOW_PIPELINE)
            .setOutputTarget(OutputTarget.WEATHER_TARGET)
            .sortOnUpload()
            .createRenderSetup());
    public static final double RENDER_RANGE_SQR = 128.0 * 128.0;

    public static final Identifier BEAM_STRIP = vfxTex("boss_beam_strip");
    public static final Identifier SLASH_HEAVY = vfxTex("boss_slash_heavy");
    public static final Identifier SLASH_CLAW = vfxTex("boss_slash_claw");
    public static final Identifier CRESCENT_WAVE = vfxTex("boss_crescent_wave");
    public static final Identifier SIGIL_RING = vfxTex("boss_sigil_ring");
    public static final Identifier MOON_SIGIL = vfxTex("boss_moon_sigil");
    public static final Identifier SHOCKWAVE_RING = vfxTex("boss_shockwave_ring");
    public static final Identifier RING_BAND = vfxTex("boss_ring_band");
    public static final Identifier CROWN_WALL = vfxTex("boss_crown_wall");
    public static final Identifier PILLAR_STRIP = vfxTex("boss_pillar_strip");
    public static final Identifier TELEGRAPH_STRIP = vfxTex("boss_telegraph_strip");
    public static final Identifier SHIELD_HEX = vfxTex("boss_shield_hex");
    public static final Identifier MOTH_WING = vfxTex("boss_moth_wing");
    public static final Identifier SCORCH_CRACKS = vfxTex("boss_scorch_cracks");
    public static final Identifier CHAIN_STRIP = vfxTex("boss_chain_strip");
    public static final Identifier LIGHTNING_STRIP = vfxTex("boss_lightning_strip");

    public static final Identifier SPARK = vfxTex("spark");
    public static final Identifier STAR = vfxTex("star");
    public static final Identifier BOLT = vfxTex("bolt");
    public static final Identifier IMPACT = vfxTex("impact");
    public static final Identifier GLYPH = vfxTex("glyph");
    public static final Identifier SHARD = vfxTex("shard");
    public static final Identifier SLASH_MARK = vfxTex("slash_mark");
    public static final Identifier RUNE_CIRCLE = vfxTex("rune_circle");
    public static final Identifier SUN = vfxTex("sun");

    private static float shake;
    private static ClientLevel lastLevel;

    private BossVfxRender() {
    }

    public static Identifier vfxTex(String name) {
        return Identifier.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "textures/vfx/" + name + ".png");
    }

    @SubscribeEvent
    public static void registerPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(GLOW_PIPELINE);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != lastLevel) {
            shake = 0.0f;
            lastLevel = mc.level;
        }
        if (mc.isPaused()) {
            return;
        }
        shake *= 0.82f;
        if (shake < 0.01f) {
            shake = 0.0f;
        }
    }

    @SubscribeEvent
    public static void onCamera(ViewportEvent.ComputeCameraAngles event) {
        if (shake <= 0.0f) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        float t = mc.player.tickCount + (float) event.getPartialTick();
        event.setYaw(event.getYaw() + Mth.sin(t * 2.7f) * shake * 1.6f);
        event.setPitch(event.getPitch() + Mth.cos(t * 3.1f) * shake * 1.2f);
        event.setRoll(event.getRoll() + Mth.sin(t * 1.9f) * shake);
    }

    public static void addShake(double x, double y, double z, float intensity, double range) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        double d = Math.sqrt(player.distanceToSqr(x, y, z));
        if (d >= range) {
            return;
        }
        shake = Math.min(1.5f, Math.max(shake, (float) (intensity * (1.0 - d / range))));
    }

    public static float clamp01(float v) {
        return v < 0.0f ? 0.0f : Math.min(v, 1.0f);
    }

    public static float easeOut(float v) {
        float c = clamp01(v);
        return 1.0f - (1.0f - c) * (1.0f - c);
    }

    public static float[] rgba(float r, float g, float b, float a) {
        return new float[]{r, g, b, clamp01(a)};
    }

    public static void debris(ClientLevel level, RandomSource r, double px, double py, double pz, int count, double speed) {
        BlockState state = level.getBlockState(BlockPos.containing(px, py - 0.5, pz));
        if (state.isAir() || state.getRenderShape() == RenderShape.INVISIBLE) {
            return;
        }
        BlockParticleOption particle = new BlockParticleOption(ParticleTypes.BLOCK, state);
        for (int i = 0; i < count; i++) {
            level.addParticle(particle, px + r.nextGaussian() * 0.8, py + 0.1, pz + r.nextGaussian() * 0.8, r.nextGaussian() * speed * 0.4, speed + r.nextDouble() * speed, r.nextGaussian() * speed * 0.4);
        }
    }

    public static final class TexBatch {
        private final Map<Identifier, List<TexQuad>> quads = new LinkedHashMap<>();
        private final Vector3f left;
        private final Vector3f up;

        public TexBatch(Vector3f left, Vector3f up) {
            this.left = left;
            this.up = up;
        }

        public Vector3f left() {
            return this.left;
        }

        public Vector3f up() {
            return this.up;
        }

        public void quad(Identifier tex,
                         double x0, double y0, double z0, float u0, float v0, float[] c0,
                         double x1, double y1, double z1, float u1, float v1, float[] c1,
                         double x2, double y2, double z2, float u2, float v2, float[] c2,
                         double x3, double y3, double z3, float u3, float v3, float[] c3) {
            if (c0[3] <= 0.002f && c1[3] <= 0.002f && c2[3] <= 0.002f && c3[3] <= 0.002f) {
                return;
            }
            this.quads.computeIfAbsent(tex, k -> new ArrayList<>()).add(new TexQuad(
                    (float) x0, (float) y0, (float) z0, u0, v0, c0[0], c0[1], c0[2], c0[3],
                    (float) x1, (float) y1, (float) z1, u1, v1, c1[0], c1[1], c1[2], c1[3],
                    (float) x2, (float) y2, (float) z2, u2, v2, c2[0], c2[1], c2[2], c2[3],
                    (float) x3, (float) y3, (float) z3, u3, v3, c3[0], c3[1], c3[2], c3[3]));
        }

        public void flush(MultiBufferSource.BufferSource buffers, PoseStack.Pose pose) {
            for (Map.Entry<Identifier, List<TexQuad>> entry : this.quads.entrySet()) {
                List<TexQuad> list = entry.getValue();
                if (list.isEmpty()) {
                    continue;
                }
                RenderType type = RenderTypes.entityTranslucentEmissive(entry.getKey());
                VertexConsumer vc = buffers.getBuffer(type);
                for (TexQuad q : list) {
                    emit(vc, pose, q.x0, q.y0, q.z0, q.u0, q.v0, q.r0, q.g0, q.b0, q.a0);
                    emit(vc, pose, q.x1, q.y1, q.z1, q.u1, q.v1, q.r1, q.g1, q.b1, q.a1);
                    emit(vc, pose, q.x2, q.y2, q.z2, q.u2, q.v2, q.r2, q.g2, q.b2, q.a2);
                    emit(vc, pose, q.x3, q.y3, q.z3, q.u3, q.v3, q.r3, q.g3, q.b3, q.a3);
                    emit(vc, pose, q.x3, q.y3, q.z3, q.u3, q.v3, q.r3, q.g3, q.b3, q.a3);
                    emit(vc, pose, q.x2, q.y2, q.z2, q.u2, q.v2, q.r2, q.g2, q.b2, q.a2);
                    emit(vc, pose, q.x1, q.y1, q.z1, q.u1, q.v1, q.r1, q.g1, q.b1, q.a1);
                    emit(vc, pose, q.x0, q.y0, q.z0, q.u0, q.v0, q.r0, q.g0, q.b0, q.a0);
                }
                buffers.endBatch(type);
            }
            this.quads.clear();
        }

        private static void emit(VertexConsumer vc, PoseStack.Pose pose, float x, float y, float z, float u, float v, float r, float g, float b, float a) {
            vc.addVertex(pose, x, y, z).setColor(r, g, b, a).setUv(u, v)
                    .setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(pose, 0.0f, 1.0f, 0.0f);
        }

        public void flat(Identifier tex, double cx, double y, double cz, double half, double angle, float[] c) {
            if (half <= 0.01) {
                return;
            }
            double ca = Math.cos(angle) * half, sa = Math.sin(angle) * half;
            this.quad(tex,
                    cx - ca + sa, y, cz - sa - ca, 0.0f, 0.0f, c,
                    cx + ca + sa, y, cz + sa - ca, 1.0f, 0.0f, c,
                    cx + ca - sa, y, cz + sa + ca, 1.0f, 1.0f, c,
                    cx - ca - sa, y, cz - sa + ca, 0.0f, 1.0f, c);
        }

        public void billboard(Identifier tex, double cx, double cy, double cz, double halfW, double halfH, double roll, float[] c) {
            this.billboardUV(tex, cx, cy, cz, halfW, halfH, roll, 0.0f, 1.0f, 0.0f, 1.0f, c);
        }

        public void billboardFrameV(Identifier tex, double cx, double cy, double cz, double half, double roll, int frame, int totalFrames, float[] c) {
            int f = Math.floorMod(frame, Math.max(1, totalFrames));
            float step = 1.0f / Math.max(1, totalFrames);
            this.billboardUV(tex, cx, cy, cz, half, half, roll, 0.0f, 1.0f, f * step, (f + 1) * step, c);
        }

        public void billboardFrameU(Identifier tex, double cx, double cy, double cz, double half, double roll, int frame, int totalFrames, float[] c) {
            int f = Math.floorMod(frame, Math.max(1, totalFrames));
            float step = 1.0f / Math.max(1, totalFrames);
            this.billboardUV(tex, cx, cy, cz, half, half, roll, f * step, (f + 1) * step, 0.0f, 1.0f, c);
        }

        public void billboardUV(Identifier tex, double cx, double cy, double cz, double halfW, double halfH, double roll,
                                float u0, float u1, float v0, float v1, float[] c) {
            if (halfW <= 0.005 || halfH <= 0.005) {
                return;
            }
            double cos = Math.cos(roll), sin = Math.sin(roll);
            double lx = (this.left.x() * cos + this.up.x() * sin) * halfW;
            double ly = (this.left.y() * cos + this.up.y() * sin) * halfW;
            double lz = (this.left.z() * cos + this.up.z() * sin) * halfW;
            double ux = (this.up.x() * cos - this.left.x() * sin) * halfH;
            double uy = (this.up.y() * cos - this.left.y() * sin) * halfH;
            double uz = (this.up.z() * cos - this.left.z() * sin) * halfH;
            this.quad(tex,
                    cx + lx + ux, cy + ly + uy, cz + lz + uz, u0, v0, c,
                    cx - lx + ux, cy - ly + uy, cz - lz + uz, u1, v0, c,
                    cx - lx - ux, cy - ly - uy, cz - lz - uz, u1, v1, c,
                    cx + lx - ux, cy + ly - uy, cz + lz - uz, u0, v1, c);
        }

        public void ringBand(Identifier tex, double cx, double y, double cz, double rIn, double rOut,
                             double fromAngle, double toAngle, double tileArcLen, float[] outerColor, float[] innerColor) {
            if (rOut <= 0.02 || rOut <= rIn) {
                return;
            }
            double span = toAngle - fromAngle;
            if (Math.abs(span) < 1.0E-4) {
                return;
            }
            double midR = Math.max(0.5, (rIn + rOut) * 0.5);
            double arcLen = Math.abs(span) * midR;
            int tiles = Math.max(4, (int) Math.ceil(arcLen / Math.max(0.6, tileArcLen)));
            int subPerTile = Math.max(1, (int) Math.ceil(Math.abs(span) / tiles / (Math.PI / 16.0)));
            for (int t = 0; t < tiles; t++) {
                double ta0 = fromAngle + span * t / tiles;
                double ta1 = fromAngle + span * (t + 1) / tiles;
                for (int s = 0; s < subPerTile; s++) {
                    float u0 = (float) s / subPerTile;
                    float u1 = (float) (s + 1) / subPerTile;
                    double a0 = ta0 + (ta1 - ta0) * u0;
                    double a1 = ta0 + (ta1 - ta0) * u1;
                    double c0 = Math.cos(a0), s0 = Math.sin(a0);
                    double c1 = Math.cos(a1), s1 = Math.sin(a1);
                    this.quad(tex,
                            cx + c0 * rOut, y, cz + s0 * rOut, u0, 0.0f, outerColor,
                            cx + c1 * rOut, y, cz + s1 * rOut, u1, 0.0f, outerColor,
                            cx + c1 * rIn, y, cz + s1 * rIn, u1, 1.0f, innerColor,
                            cx + c0 * rIn, y, cz + s0 * rIn, u0, 1.0f, innerColor);
                }
            }
        }

        public void crownWall(Identifier tex, double cx, double y0, double y1, double cz, double r,
                              double fromAngle, double toAngle, double tileArcLen, float[] topColor, float[] bottomColor) {
            if (r <= 0.02 || Math.abs(y1 - y0) <= 0.01) {
                return;
            }
            double span = toAngle - fromAngle;
            if (Math.abs(span) < 1.0E-4) {
                return;
            }
            double arcLen = Math.abs(span) * r;
            int tiles = Math.max(4, (int) Math.ceil(arcLen / Math.max(0.8, tileArcLen)));
            int subPerTile = Math.max(1, (int) Math.ceil(Math.abs(span) / tiles / (Math.PI / 14.0)));
            for (int t = 0; t < tiles; t++) {
                double ta0 = fromAngle + span * t / tiles;
                double ta1 = fromAngle + span * (t + 1) / tiles;
                for (int s = 0; s < subPerTile; s++) {
                    float u0 = (float) s / subPerTile;
                    float u1 = (float) (s + 1) / subPerTile;
                    double a0 = ta0 + (ta1 - ta0) * u0;
                    double a1 = ta0 + (ta1 - ta0) * u1;
                    double x0 = cx + Math.cos(a0) * r, z0 = cz + Math.sin(a0) * r;
                    double x1 = cx + Math.cos(a1) * r, z1 = cz + Math.sin(a1) * r;
                    this.quad(tex,
                            x0, y1, z0, u0, 0.0f, topColor,
                            x1, y1, z1, u1, 0.0f, topColor,
                            x1, y0, z1, u1, 1.0f, bottomColor,
                            x0, y0, z0, u0, 1.0f, bottomColor);
                }
            }
        }

        public void slashArc(Identifier tex, double cx, double cy, double cz, double fx, double fz,
                             double rIn, double rOut, double fromRad, double toRad,
                             boolean vertical, double tiltY, double heightOffset, int segs,
                             float[] tipColor, float[] tailColor) {
            if (rOut <= rIn || segs <= 0 || Math.abs(toRad - fromRad) < 1.0E-4) {
                return;
            }
            for (int i = 0; i < segs; i++) {
                float u0 = (float) i / segs;
                float u1 = (float) (i + 1) / segs;
                double a0 = fromRad + (toRad - fromRad) * u0;
                double a1 = fromRad + (toRad - fromRad) * u1;
                float[] c0 = lerpColor(tailColor, tipColor, u0);
                float[] c1 = lerpColor(tailColor, tipColor, u1);
                double[] o0 = slashPoint(cx, cy, cz, fx, fz, a0, rOut, vertical, tiltY, heightOffset);
                double[] o1 = slashPoint(cx, cy, cz, fx, fz, a1, rOut, vertical, tiltY, heightOffset);
                double[] i0 = slashPoint(cx, cy, cz, fx, fz, a0, rIn, vertical, tiltY, heightOffset);
                double[] i1 = slashPoint(cx, cy, cz, fx, fz, a1, rIn, vertical, tiltY, heightOffset);
                this.quad(tex,
                        o0[0], o0[1], o0[2], u0, 0.0f, c0,
                        o1[0], o1[1], o1[2], u1, 0.0f, c1,
                        i1[0], i1[1], i1[2], u1, 1.0f, c1,
                        i0[0], i0[1], i0[2], u0, 1.0f, c0);
            }
        }

        private static double[] slashPoint(double cx, double cy, double cz, double fx, double fz,
                                           double a, double radius, boolean vertical, double tiltY, double heightOffset) {
            if (vertical) {
                double upComp = Math.cos(a) * radius;
                double fwComp = Math.sin(a) * radius;
                return new double[]{cx + fx * fwComp, cy + heightOffset + Math.max(-0.2, upComp * 0.72), cz + fz * fwComp};
            }
            double cos = Math.cos(a);
            double sin = Math.sin(a);
            double dx = fx * cos + fz * sin;
            double dz = fz * cos - fx * sin;
            return new double[]{cx + dx * radius, cy + heightOffset + sin * tiltY, cz + dz * radius};
        }

        public void crescentWave(double cx, double cy, double cz, double fx, double fz, double rollRad,
                                 double halfForward, double halfSpan, float[] mainColor, float[] coreColor) {
            double sx = fz, sz = -fx;
            double cos = Math.cos(rollRad), sin = Math.sin(rollRad);
            double ax = sx * sin, ay = cos, az = sz * sin;
            double bx = sx * cos, by = -sin, bz = sz * cos;
            emitCrescentPlane(CRESCENT_WAVE, cx, cy, cz, fx, fz, ax, ay, az, halfForward, halfSpan, mainColor);
            emitCrescentPlane(CRESCENT_WAVE, cx, cy, cz, fx, fz, ax, ay, az, halfForward * 0.78, halfSpan * 0.72, coreColor);
            emitCrescentPlane(SLASH_HEAVY, cx, cy, cz, fx, fz, bx, by, bz, halfForward * 0.85, halfSpan * 0.28, coreColor);
        }

        private void emitCrescentPlane(Identifier tex, double cx, double cy, double cz, double fx, double fz,
                                       double ux, double uy, double uz, double halfF, double halfS, float[] c) {
            double frontX = cx + fx * halfF, frontY = cy, frontZ = cz + fz * halfF;
            double backX = cx - fx * halfF, backY = cy, backZ = cz - fz * halfF;
            this.quad(tex,
                    backX + ux * halfS, backY + uy * halfS, backZ + uz * halfS, 0.0f, 0.0f, c,
                    frontX + ux * halfS, frontY + uy * halfS, frontZ + uz * halfS, 1.0f, 0.0f, c,
                    frontX - ux * halfS, frontY - uy * halfS, frontZ - uz * halfS, 1.0f, 1.0f, c,
                    backX - ux * halfS, backY - uy * halfS, backZ - uz * halfS, 0.0f, 1.0f, c);
        }

        public void pillar(double cx, double y0, double cz, double radius, double height, double rotRad,
                           float[] mainColor, float[] coreColor) {
            if (radius <= 0.02 || height <= 0.02) {
                return;
            }
            for (int k = 0; k < 2; k++) {
                double a = rotRad + k * (Math.PI * 0.5);
                double dx = Math.cos(a) * radius, dz = Math.sin(a) * radius;
                this.quad(PILLAR_STRIP,
                        cx - dx, y0 + height, cz - dz, 0.0f, 0.0f, mainColor,
                        cx + dx, y0 + height, cz + dz, 1.0f, 0.0f, mainColor,
                        cx + dx, y0, cz + dz, 1.0f, 1.0f, mainColor,
                        cx - dx, y0, cz - dz, 0.0f, 1.0f, mainColor);
            }
            double cr = radius * 0.62;
            double ch = height * 0.92;
            for (int k = 0; k < 2; k++) {
                double a = rotRad + Math.PI * 0.25 + k * (Math.PI * 0.5);
                double dx = Math.cos(a) * cr, dz = Math.sin(a) * cr;
                this.quad(PILLAR_STRIP,
                        cx - dx, y0 + ch, cz - dz, 0.0f, 0.0f, coreColor,
                        cx + dx, y0 + ch, cz + dz, 1.0f, 0.0f, coreColor,
                        cx + dx, y0, cz + dz, 1.0f, 1.0f, coreColor,
                        cx - dx, y0, cz - dz, 0.0f, 1.0f, coreColor);
            }
        }

        public void tiledGroundLine(Identifier tex, double x0, double y, double z0, double fx, double fz,
                                    double length, double halfWidth, double tileLen, float pulsePhase,
                                    float[] baseColor, float[] highlightColor) {
            if (length <= 0.05 || halfWidth <= 0.01) {
                return;
            }
            double sx = fz * halfWidth, sz = -fx * halfWidth;
            double step = Math.max(0.6, tileLen);
            int segs = Math.max(1, (int) Math.ceil(length / step));
            for (int i = 0; i < segs; i++) {
                double d0 = i * step;
                double d1 = Math.min(length, (i + 1) * step);
                float u1 = (float) Math.min(1.0, (d1 - d0) / step);
                float wave0 = 0.5f + 0.5f * Mth.sin((float) (d0 * 0.45 - pulsePhase));
                float wave1 = 0.5f + 0.5f * Mth.sin((float) (d1 * 0.45 - pulsePhase));
                float[] c0 = lerpColor(baseColor, highlightColor, wave0);
                float[] c1 = lerpColor(baseColor, highlightColor, wave1);
                double ax = x0 + fx * d0, az = z0 + fz * d0;
                double bx = x0 + fx * d1, bz = z0 + fz * d1;
                this.quad(tex,
                        ax + sx, y, az + sz, 0.0f, 0.0f, c0,
                        bx + sx, y, bz + sz, u1, 0.0f, c1,
                        bx - sx, y, bz - sz, u1, 1.0f, c1,
                        ax - sx, y, az - sz, 0.0f, 1.0f, c0);
            }
        }

        public void tiledStrip3D(Identifier tex, double x0, double y0, double z0,
                                 double x1, double y1, double z1, double width, double tileLen,
                                 float[] startColor, float[] endColor) {
            double dx = x1 - x0, dy = y1 - y0, dz = z1 - z0;
            double len = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (len < 1.0E-3 || width <= 0.005) {
                return;
            }
            double nx = dx / len, ny = dy / len, nz = dz / len;
            double sx, sy, sz;
            if (Math.abs(ny) > 0.92) {
                sx = 1.0;
                sy = 0.0;
                sz = 0.0;
            } else {
                sx = -nz;
                sy = 0.0;
                sz = nx;
                double sl = Math.sqrt(sx * sx + sz * sz);
                sx /= sl;
                sz /= sl;
            }
            double ux = ny * sz - nz * sy, uy = nz * sx - nx * sz, uz = nx * sy - ny * sx;
            double ul = Math.sqrt(ux * ux + uy * uy + uz * uz);
            ux /= ul;
            uy /= ul;
            uz /= ul;
            double hw = width * 0.5;
            double step = Math.max(0.5, tileLen);
            int segs = Math.max(1, (int) Math.ceil(len / step));
            for (int i = 0; i < segs; i++) {
                double d0 = i * step;
                double d1 = Math.min(len, (i + 1) * step);
                float f0 = (float) (d0 / len);
                float f1 = (float) (d1 / len);
                float v1 = (float) Math.min(1.0, (d1 - d0) / step);
                float[] c0 = lerpColor(startColor, endColor, f0);
                float[] c1 = lerpColor(startColor, endColor, f1);
                double ax = x0 + nx * d0, ay = y0 + ny * d0, az = z0 + nz * d0;
                double bx = x0 + nx * d1, by = y0 + ny * d1, bz = z0 + nz * d1;
                this.quad(tex,
                        ax - sx * hw, ay - sy * hw, az - sz * hw, 0.0f, 0.0f, c0,
                        ax + sx * hw, ay + sy * hw, az + sz * hw, 1.0f, 0.0f, c0,
                        bx + sx * hw, by + sy * hw, bz + sz * hw, 1.0f, v1, c1,
                        bx - sx * hw, by - sy * hw, bz - sz * hw, 0.0f, v1, c1);
                this.quad(tex,
                        ax - ux * hw, ay - uy * hw, az - uz * hw, 0.0f, 0.0f, c0,
                        ax + ux * hw, ay + uy * hw, az + uz * hw, 1.0f, 0.0f, c0,
                        bx + ux * hw, by + uy * hw, bz + uz * hw, 1.0f, v1, c1,
                        bx - ux * hw, by - uy * hw, bz - uz * hw, 0.0f, v1, c1);
            }
        }

        public void mothWings(double cx, double cy, double cz, double fx, double fz,
                              double span, double height, double flapBack, float[] tint, float[] coreTint) {
            if (span <= 0.05 || height <= 0.05) {
                return;
            }
            double bx = cx - fx * 0.55;
            double bz = cz - fz * 0.55;
            double topY = cy + height * 0.58;
            double botY = cy - height * 0.42;
            for (int side = -1; side <= 1; side += 2) {
                double sx = fz * side, sz = -fx * side;
                double wx = (sx - fx * flapBack) * span;
                double wz = (sz - fz * flapBack) * span;
                this.quad(MOTH_WING,
                        bx, topY, bz, 0.0f, 0.0f, coreTint,
                        bx + wx, topY + height * 0.14, bz + wz, 1.0f, 0.0f, tint,
                        bx + wx, botY + height * 0.14, bz + wz, 1.0f, 1.0f, tint,
                        bx, botY, bz, 0.0f, 1.0f, coreTint);
            }
        }

        private static float[] lerpColor(float[] a, float[] b, float t) {
            float k = clamp01(t);
            return new float[]{
                    a[0] + (b[0] - a[0]) * k,
                    a[1] + (b[1] - a[1]) * k,
                    a[2] + (b[2] - a[2]) * k,
                    clamp01(a[3] + (b[3] - a[3]) * k)
            };
        }
    }

    private record TexQuad(
            float x0, float y0, float z0, float u0, float v0, float r0, float g0, float b0, float a0,
            float x1, float y1, float z1, float u1, float v1, float r1, float g1, float b1, float a1,
            float x2, float y2, float z2, float u2, float v2, float r2, float g2, float b2, float a2,
            float x3, float y3, float z3, float u3, float v3, float r3, float g3, float b3, float a3) {
    }

    public record Painter(VertexConsumer vc, PoseStack.Pose pose) {
        public void quad(double x0, double y0, double z0, float[] c0, double x1, double y1, double z1, float[] c1,
                         double x2, double y2, double z2, float[] c2, double x3, double y3, double z3, float[] c3) {
            this.v(x0, y0, z0, c0);
            this.v(x1, y1, z1, c1);
            this.v(x2, y2, z2, c2);
            this.v(x3, y3, z3, c3);
        }

        public void v(double x, double y, double z, float[] c) {
            this.vc.addVertex(this.pose, (float) x, (float) y, (float) z).setColor(c[0], c[1], c[2], c[3]);
        }

        public void annulus(double cx, double y, double cz, double rIn, double rOut, int segs, float[] inner, float[] outer) {
            if (rOut <= 0.0 || rOut <= rIn) {
                return;
            }
            for (int i = 0; i < segs; i++) {
                double a0 = Math.PI * 2.0 * i / segs;
                double a1 = Math.PI * 2.0 * (i + 1) / segs;
                double c0 = Math.cos(a0), s0 = Math.sin(a0), c1 = Math.cos(a1), s1 = Math.sin(a1);
                this.quad(cx + c0 * rIn, y, cz + s0 * rIn, inner, cx + c0 * rOut, y, cz + s0 * rOut, outer,
                        cx + c1 * rOut, y, cz + s1 * rOut, outer, cx + c1 * rIn, y, cz + s1 * rIn, inner);
            }
        }

        public void arcBand(double cx, double y, double cz, double rIn, double rOut, double from, double to, int segs, float[] inner, float[] outer) {
            if (rOut <= 0.0 || rOut <= rIn) {
                return;
            }
            for (int i = 0; i < segs; i++) {
                double a0 = from + (to - from) * i / segs;
                double a1 = from + (to - from) * (i + 1) / segs;
                double c0 = Math.cos(a0), s0 = Math.sin(a0), c1 = Math.cos(a1), s1 = Math.sin(a1);
                this.quad(cx + c0 * rIn, y, cz + s0 * rIn, inner, cx + c0 * rOut, y, cz + s0 * rOut, outer,
                        cx + c1 * rOut, y, cz + s1 * rOut, outer, cx + c1 * rIn, y, cz + s1 * rIn, inner);
            }
        }

        public void wall(double cx, double y0, double y1, double cz, double r, int segs, double fromAngle, double toAngle, float[] bottom, float[] top) {
            for (int i = 0; i < segs; i++) {
                double a0 = fromAngle + (toAngle - fromAngle) * i / segs;
                double a1 = fromAngle + (toAngle - fromAngle) * (i + 1) / segs;
                double x0 = cx + Math.cos(a0) * r, z0 = cz + Math.sin(a0) * r;
                double x1 = cx + Math.cos(a1) * r, z1 = cz + Math.sin(a1) * r;
                this.quad(x0, y0, z0, bottom, x1, y0, z1, bottom, x1, y1, z1, top, x0, y1, z0, top);
            }
        }

        public void beam(double x0, double y0, double z0, double x1, double y1, double z1, double width, float[] core, float[] edge) {
            double dx = x1 - x0, dy = y1 - y0, dz = z1 - z0;
            double len = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (len < 1.0E-4) {
                return;
            }
            double sx, sy, sz;
            if (Math.abs(dy) > len * 0.9) {
                sx = 1.0;
                sy = 0.0;
                sz = 0.0;
            } else {
                sx = -dz;
                sy = 0.0;
                sz = dx;
                double l = Math.sqrt(sx * sx + sz * sz);
                sx /= l;
                sz /= l;
            }
            double ux = dy * sz - dz * sy, uy = dz * sx - dx * sz, uz = dx * sy - dy * sx;
            double ul = Math.sqrt(ux * ux + uy * uy + uz * uz);
            ux /= ul;
            uy /= ul;
            uz /= ul;
            double h = width * 0.5;
            this.quad(x0 - sx * h, y0 - sy * h, z0 - sz * h, edge, x0, y0, z0, core, x1, y1, z1, core, x1 - sx * h, y1 - sy * h, z1 - sz * h, edge);
            this.quad(x0, y0, z0, core, x0 + sx * h, y0 + sy * h, z0 + sz * h, edge, x1 + sx * h, y1 + sy * h, z1 + sz * h, edge, x1, y1, z1, core);
            this.quad(x0 - ux * h, y0 - uy * h, z0 - uz * h, edge, x0, y0, z0, core, x1, y1, z1, core, x1 - ux * h, y1 - uy * h, z1 - uz * h, edge);
            this.quad(x0, y0, z0, core, x0 + ux * h, y0 + uy * h, z0 + uz * h, edge, x1 + ux * h, y1 + uy * h, z1 + uz * h, edge, x1, y1, z1, core);
        }
    }
}
