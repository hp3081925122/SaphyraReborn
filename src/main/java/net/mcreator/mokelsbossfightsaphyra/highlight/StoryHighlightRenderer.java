package com.hp.saphyrareborn.highlight;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;

@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class StoryHighlightRenderer {
    private static final double MAX_DIST_SQR = 64.0 * 64.0;
    private static final float FILL_ALPHA = 0.28f;
    private static final float LINE_WIDTH = 4.0f;
    private static final float[] GOLD = {1.0f, 0.75f, 0.0f};
    private static final float[] AQUA = {0.1f, 0.9f, 1.0f};
    private static final float[] VIOLET = {0.85f, 0.3f, 1.0f};

    public static final RenderPipeline HIGHLIGHT_FILL_PIPELINE = RenderPipelines.DEBUG_FILLED_BOX.toBuilder()
            .withLocation(Identifier.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "pipeline/story_highlight_fill"))
            .withCull(false)
            .withDepthStencilState(Optional.empty())
            .build();

    public static final RenderPipeline HIGHLIGHT_LINE_PIPELINE = RenderPipelines.LINES.toBuilder()
            .withLocation(Identifier.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "pipeline/story_highlight_lines"))
            .withCull(false)
            .withDepthStencilState(Optional.empty())
            .build();

    public static final RenderType HIGHLIGHT_FILL = RenderType.create("saphyra_story_highlight_fill",
            RenderSetup.builder(HIGHLIGHT_FILL_PIPELINE)
                    .sortOnUpload()
                    .createRenderSetup());

    public static final RenderType HIGHLIGHT_LINES = RenderType.create("saphyra_story_highlight_lines",
            RenderSetup.builder(HIGHLIGHT_LINE_PIPELINE)
                    .createRenderSetup());

    private static int lastLoggedHash;

    private StoryHighlightRenderer() {
    }

    @SubscribeEvent
    public static void registerPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(HIGHLIGHT_FILL_PIPELINE);
        event.registerPipeline(HIGHLIGHT_LINE_PIPELINE);
        MokelsBossfightSaphyraMod.LOGGER.info("[StoryHighlight] Registered see-through highlight pipelines (lineWidth={})", LINE_WIDTH);
    }

    @SubscribeEvent
    public static void onRender(SubmitCustomGeometryEvent event) {
        List<BlockPos> containers = List.copyOf(StoryHighlightState.containers());
        List<BlockPos> targets = List.copyOf(StoryHighlightState.targets());
        List<BlockPos> locks = List.copyOf(KeyLockHighlight.locks());
        if ((containers.isEmpty() && targets.isEmpty() && locks.isEmpty()) || Minecraft.getInstance().level == null) {
            lastLoggedHash = 0;
            return;
        }
        int stateHash = (containers.size() * 31 + targets.size()) * 31 + locks.size() + 1;
        if (stateHash != lastLoggedHash) {
            lastLoggedHash = stateHash;
            MokelsBossfightSaphyraMod.LOGGER.info("[StoryHighlight] Rendering highlight batch: containers={}, targets={}, locks={}, lineWidth={}",
                    containers.size(), targets.size(), locks.size(), LINE_WIDTH);
        }
        Vec3 cam = Minecraft.getInstance().gameRenderer.mainCamera().position();
        PoseStack pose = event.getPoseStack();
        pose.pushPose();
        pose.translate(-cam.x, -cam.y, -cam.z);
        SubmitNodeCollector collector = event.getSubmitNodeCollector();
        collector.submitCustomGeometry(pose, HIGHLIGHT_FILL, (last, fill) -> {
            for (BlockPos p : containers) if (near(p, cam)) fillBox(fill, last, p, GOLD);
            for (BlockPos p : targets) if (near(p, cam)) fillBox(fill, last, p, AQUA);
            for (BlockPos p : locks) if (near(p, cam)) fillBox(fill, last, p, VIOLET);
        });
        collector.submitCustomGeometry(pose, HIGHLIGHT_LINES, (last, lines) -> {
            for (BlockPos p : containers) if (near(p, cam)) lineBox(lines, last, box(p), GOLD);
            for (BlockPos p : targets) if (near(p, cam)) lineBox(lines, last, box(p), AQUA);
            for (BlockPos p : locks) if (near(p, cam)) lineBox(lines, last, box(p), VIOLET);
        });
        pose.popPose();
    }

    private static boolean near(BlockPos p, Vec3 cam) {
        return p.distToCenterSqr(cam) <= MAX_DIST_SQR;
    }

    private static AABB box(BlockPos p) {
        return new AABB(p).inflate(0.005);
    }

    private static void fillBox(VertexConsumer b, PoseStack.Pose pose, BlockPos p, float[] c) {
        AABB a = box(p);
        float x0 = (float) a.minX, y0 = (float) a.minY, z0 = (float) a.minZ;
        float x1 = (float) a.maxX, y1 = (float) a.maxY, z1 = (float) a.maxZ;
        float r = c[0], g = c[1], bl = c[2], al = FILL_ALPHA;
        quad(b, pose, x0, y0, z0, x1, y0, z0, x1, y0, z1, x0, y0, z1, r, g, bl, al);
        quad(b, pose, x0, y1, z0, x0, y1, z1, x1, y1, z1, x1, y1, z0, r, g, bl, al);
        quad(b, pose, x0, y0, z0, x0, y1, z0, x1, y1, z0, x1, y0, z0, r, g, bl, al);
        quad(b, pose, x0, y0, z1, x1, y0, z1, x1, y1, z1, x0, y1, z1, r, g, bl, al);
        quad(b, pose, x0, y0, z0, x0, y0, z1, x0, y1, z1, x0, y1, z0, r, g, bl, al);
        quad(b, pose, x1, y0, z0, x1, y1, z0, x1, y1, z1, x1, y0, z1, r, g, bl, al);
    }

    private static void quad(VertexConsumer b, PoseStack.Pose pose,
                             float ax, float ay, float az, float bx, float by, float bz,
                             float cx, float cy, float cz, float dx, float dy, float dz,
                             float r, float g, float bl, float a) {
        b.addVertex(pose, ax, ay, az).setColor(r, g, bl, a);
        b.addVertex(pose, bx, by, bz).setColor(r, g, bl, a);
        b.addVertex(pose, cx, cy, cz).setColor(r, g, bl, a);
        b.addVertex(pose, dx, dy, dz).setColor(r, g, bl, a);
    }

    private static void lineBox(VertexConsumer b, PoseStack.Pose pose, AABB a, float[] c) {
        float x0 = (float) a.minX, y0 = (float) a.minY, z0 = (float) a.minZ;
        float x1 = (float) a.maxX, y1 = (float) a.maxY, z1 = (float) a.maxZ;
        edge(b, pose, x0, y0, z0, x1, y0, z0, c);
        edge(b, pose, x1, y0, z0, x1, y0, z1, c);
        edge(b, pose, x1, y0, z1, x0, y0, z1, c);
        edge(b, pose, x0, y0, z1, x0, y0, z0, c);
        edge(b, pose, x0, y1, z0, x1, y1, z0, c);
        edge(b, pose, x1, y1, z0, x1, y1, z1, c);
        edge(b, pose, x1, y1, z1, x0, y1, z1, c);
        edge(b, pose, x0, y1, z1, x0, y1, z0, c);
        edge(b, pose, x0, y0, z0, x0, y1, z0, c);
        edge(b, pose, x1, y0, z0, x1, y1, z0, c);
        edge(b, pose, x1, y0, z1, x1, y1, z1, c);
        edge(b, pose, x0, y0, z1, x0, y1, z1, c);
    }

    private static void edge(VertexConsumer b, PoseStack.Pose pose, float x0, float y0, float z0, float x1, float y1, float z1, float[] c) {
        float dx = x1 - x0, dy = y1 - y0, dz = z1 - z0;
        float len = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
        float nx = len > 1.0E-5f ? dx / len : 0.0f;
        float ny = len > 1.0E-5f ? dy / len : 1.0f;
        float nz = len > 1.0E-5f ? dz / len : 0.0f;
        b.addVertex(pose, x0, y0, z0).setColor(c[0], c[1], c[2], 1.0f).setNormal(pose, nx, ny, nz).setLineWidth(LINE_WIDTH);
        b.addVertex(pose, x1, y1, z1).setColor(c[0], c[1], c[2], 1.0f).setNormal(pose, nx, ny, nz).setLineWidth(LINE_WIDTH);
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        lastLoggedHash = 0;
        StoryHighlightState.clear();
    }
}
