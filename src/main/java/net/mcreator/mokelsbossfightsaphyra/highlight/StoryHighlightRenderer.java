package com.hp.saphyrareborn.highlight;

import net.neoforged.api.distmarker.Dist;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.List;

/**
 * Draws a see-through highlight on story blocks: a translucent filled box plus a thick solid outline,
 * both with the depth test switched off. Gold = container with a story item, aqua = block to use next.
 */
@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class StoryHighlightRenderer {
    private static final double MAX_DIST_SQR = 64.0 * 64.0;
    private static final float FILL_ALPHA = 0.28f;
    private static final float[] GOLD = {1.0f, 0.75f, 0.0f};
    private static final float[] AQUA = {0.1f, 0.9f, 1.0f};
    private static final float[] VIOLET = {0.85f, 0.3f, 1.0f};

    private StoryHighlightRenderer() {
    }

    @SubscribeEvent
    public static void onRender(RenderLevelStageEvent.AfterTranslucentBlocks event) {
        List<BlockPos> containers = StoryHighlightState.containers();
        List<BlockPos> targets = StoryHighlightState.targets();
        List<BlockPos> locks = KeyLockHighlight.locks();
        if ((containers.isEmpty() && targets.isEmpty() && locks.isEmpty()) || Minecraft.getInstance().level == null) {
            return;
        }
        Vec3 cam = Minecraft.getInstance().gameRenderer.getMainCamera().position();
        PoseStack pose = event.getPoseStack();
        pose.pushPose();
        pose.translate(-cam.x, -cam.y, -cam.z);
        MultiBufferSource.BufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();
        PoseStack.Pose last = pose.last();
        VertexConsumer fill = buffers.getBuffer(RenderTypes.debugFilledBox());
        for (BlockPos p : containers) if (near(p, cam)) fillBox(fill, last, p, GOLD);
        for (BlockPos p : targets) if (near(p, cam)) fillBox(fill, last, p, AQUA);
        for (BlockPos p : locks) if (near(p, cam)) fillBox(fill, last, p, VIOLET);
        VertexConsumer lines = buffers.getBuffer(RenderTypes.lines());
        for (BlockPos p : containers) if (near(p, cam)) lineBox(lines, last, box(p), GOLD);
        for (BlockPos p : targets) if (near(p, cam)) lineBox(lines, last, box(p), AQUA);
        for (BlockPos p : locks) if (near(p, cam)) lineBox(lines, last, box(p), VIOLET);
        buffers.endBatch(RenderTypes.debugFilledBox());
        buffers.endBatch(RenderTypes.lines());
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
        float nx = x1 - x0, ny = y1 - y0, nz = z1 - z0;
        b.addVertex(pose, x0, y0, z0).setColor(c[0], c[1], c[2], 1.0f).setNormal(pose, nx, ny, nz);
        b.addVertex(pose, x1, y1, z1).setColor(c[0], c[1], c[2], 1.0f).setNormal(pose, nx, ny, nz);
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        StoryHighlightState.clear();
    }
}
