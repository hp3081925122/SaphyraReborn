package com.hp.saphyrareborn.highlight;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.List;
import java.util.OptionalDouble;

/** Draws pulsing outlines that show through walls: gold = container with a story item, aqua = block to use next. */
@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class StoryHighlightRenderer {
    private static final double MAX_DIST_SQR = 64.0 * 64.0;

    private StoryHighlightRenderer() {
    }

    @SubscribeEvent
    public static void onRender(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            return;
        }
        List<BlockPos> containers = StoryHighlightState.containers();
        List<BlockPos> targets = StoryHighlightState.targets();
        if (containers.isEmpty() && targets.isEmpty()) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            return;
        }
        Vec3 cam = event.getCamera().getPosition();
        float pulse = 0.55f + 0.45f * (float) Math.sin((mc.level.getGameTime() + event.getPartialTick().getGameTimeDeltaPartialTick(false)) * 0.15);

        PoseStack pose = event.getPoseStack();
        pose.pushPose();
        pose.translate(-cam.x, -cam.y, -cam.z);
        MultiBufferSource.BufferSource buffers = mc.renderBuffers().bufferSource();
        VertexConsumer lines = buffers.getBuffer(Types.LINES_THROUGH_WALLS);
        for (BlockPos p : containers) {
            box(pose, lines, cam, p, 1.0f, 0.78f, 0.15f, pulse);
        }
        for (BlockPos p : targets) {
            box(pose, lines, cam, p, 0.25f, 0.95f, 1.0f, pulse);
        }
        buffers.endBatch(Types.LINES_THROUGH_WALLS);
        pose.popPose();
    }

    private static void box(PoseStack pose, VertexConsumer lines, Vec3 cam, BlockPos p, float r, float g, float b, float a) {
        if (p.distToCenterSqr(cam) > MAX_DIST_SQR) {
            return;
        }
        LevelRenderer.renderLineBox(pose, lines, new AABB(p).inflate(0.02), r, g, b, a);
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        StoryHighlightState.clear();
    }

    /** Subclass only to reach RenderType's protected render-state shards. */
    private static final class Types extends RenderType {
        static final RenderType LINES_THROUGH_WALLS = RenderType.create(
                "saphyra_reborn_story_highlight", DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.LINES, 1024, false, false,
                CompositeState.builder()
                        .setShaderState(RENDERTYPE_LINES_SHADER)
                        .setLineState(new LineStateShard(OptionalDouble.of(2.5)))
                        .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setWriteMaskState(COLOR_WRITE)
                        .setCullState(NO_CULL)
                        .setDepthTestState(NO_DEPTH_TEST)
                        .createCompositeState(false));

        private Types(String name, VertexFormat format, VertexFormat.Mode mode, int size, boolean crumbling, boolean sort, Runnable setup, Runnable clear) {
            super(name, format, mode, size, crumbling, sort, setup, clear);
        }
    }
}
