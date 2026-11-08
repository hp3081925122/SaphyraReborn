package com.hp.saphyrareborn.highlight;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

import java.util.List;

/**
 * Draws a see-through highlight on story blocks: a translucent filled box plus a thick solid outline,
 * both with the depth test switched off. Gold = container with a story item, aqua = block to use next.
 */
@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class StoryHighlightRenderer {
    private static final double MAX_DIST_SQR = 64.0 * 64.0;
    private static final float FILL_ALPHA = 0.28f;
    private static final float LINE_WIDTH = 4.0f;
    private static final float[] GOLD = {1.0f, 0.75f, 0.0f};
    private static final float[] AQUA = {0.1f, 0.9f, 1.0f};

    private StoryHighlightRenderer() {
    }

    @SubscribeEvent
    public static void onRender(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            return;
        }
        List<BlockPos> containers = StoryHighlightState.containers();
        List<BlockPos> targets = StoryHighlightState.targets();
        if ((containers.isEmpty() && targets.isEmpty()) || Minecraft.getInstance().level == null) {
            return;
        }
        Vec3 cam = event.getCamera().getPosition();
        PoseStack pose = event.getPoseStack();
        pose.pushPose();
        pose.translate(-cam.x, -cam.y, -cam.z);

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();

        // translucent fill
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder fill = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        Matrix4f m = pose.last().pose();
        for (BlockPos p : containers) if (near(p, cam)) fillBox(fill, m, p, GOLD);
        for (BlockPos p : targets) if (near(p, cam)) fillBox(fill, m, p, AQUA);
        draw(fill);

        // solid outline
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        RenderSystem.lineWidth(LINE_WIDTH);
        BufferBuilder lines = Tesselator.getInstance().begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        for (BlockPos p : containers) if (near(p, cam)) LevelRenderer.renderLineBox(pose, lines, box(p), GOLD[0], GOLD[1], GOLD[2], 1.0f);
        for (BlockPos p : targets) if (near(p, cam)) LevelRenderer.renderLineBox(pose, lines, box(p), AQUA[0], AQUA[1], AQUA[2], 1.0f);
        draw(lines);
        RenderSystem.lineWidth(1.0f);

        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        pose.popPose();
    }

    private static boolean near(BlockPos p, Vec3 cam) {
        return p.distToCenterSqr(cam) <= MAX_DIST_SQR;
    }

    private static AABB box(BlockPos p) {
        return new AABB(p).inflate(0.005);
    }

    private static void draw(BufferBuilder builder) {
        MeshData mesh = builder.build();
        if (mesh != null) {
            BufferUploader.drawWithShader(mesh);
        }
    }

    private static void fillBox(BufferBuilder b, Matrix4f m, BlockPos p, float[] c) {
        AABB a = box(p);
        float x0 = (float) a.minX, y0 = (float) a.minY, z0 = (float) a.minZ;
        float x1 = (float) a.maxX, y1 = (float) a.maxY, z1 = (float) a.maxZ;
        float r = c[0], g = c[1], bl = c[2], al = FILL_ALPHA;
        // -Y, +Y, -Z, +Z, -X, +X
        quad(b, m, x0, y0, z0, x1, y0, z0, x1, y0, z1, x0, y0, z1, r, g, bl, al);
        quad(b, m, x0, y1, z0, x0, y1, z1, x1, y1, z1, x1, y1, z0, r, g, bl, al);
        quad(b, m, x0, y0, z0, x0, y1, z0, x1, y1, z0, x1, y0, z0, r, g, bl, al);
        quad(b, m, x0, y0, z1, x1, y0, z1, x1, y1, z1, x0, y1, z1, r, g, bl, al);
        quad(b, m, x0, y0, z0, x0, y0, z1, x0, y1, z1, x0, y1, z0, r, g, bl, al);
        quad(b, m, x1, y0, z0, x1, y1, z0, x1, y1, z1, x1, y0, z1, r, g, bl, al);
    }

    private static void quad(BufferBuilder b, Matrix4f m,
                             float ax, float ay, float az, float bx, float by, float bz,
                             float cx, float cy, float cz, float dx, float dy, float dz,
                             float r, float g, float bl, float a) {
        b.addVertex(m, ax, ay, az).setColor(r, g, bl, a);
        b.addVertex(m, bx, by, bz).setColor(r, g, bl, a);
        b.addVertex(m, cx, cy, cz).setColor(r, g, bl, a);
        b.addVertex(m, dx, dy, dz).setColor(r, g, bl, a);
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        StoryHighlightState.clear();
    }
}
