package com.hp.saphyrareborn.boss.client;

import net.neoforged.api.distmarker.Dist;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

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
    private static float shake;
    private static ClientLevel lastLevel;

    private BossVfxRender() {
    }

    @SubscribeEvent
    public static void registerPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(GLOW_PIPELINE);
        MokelsBossfightSaphyraMod.LOGGER.info("[MothWingDebug] boss glow pipeline registered cull={}", GLOW_PIPELINE.isCull());
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
