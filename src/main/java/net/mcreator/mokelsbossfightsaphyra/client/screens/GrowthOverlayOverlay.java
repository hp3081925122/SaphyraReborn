/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.neoforge.client.event.RenderGuiEvent$Pre
 *  net.neoforged.bus.api.EventPriority
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 */
package com.hp.saphyrareborn.client.screens;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.hp.saphyrareborn.procedures.DevotionBarProc0Procedure;
import com.hp.saphyrareborn.procedures.DevotionBarProc1Procedure;
import com.hp.saphyrareborn.procedures.DevotionBarProc2Procedure;
import com.hp.saphyrareborn.procedures.DevotionBarProc3Procedure;
import com.hp.saphyrareborn.procedures.DevotionBarProc4Procedure;
import com.hp.saphyrareborn.procedures.DevotionBarProc5Procedure;
import com.hp.saphyrareborn.procedures.DevotionBarProc6Procedure;
import com.hp.saphyrareborn.procedures.EnduranceOverlayProcedure2Procedure;
import com.hp.saphyrareborn.procedures.EnduranceOverlayProcedure3Procedure;
import com.hp.saphyrareborn.procedures.EnduranceOverlayProcedureProcedure;
import com.hp.saphyrareborn.procedures.EnduranceOverlayUltimativeProcedure;
import com.hp.saphyrareborn.procedures.GriefBarProc0Procedure;
import com.hp.saphyrareborn.procedures.GriefBarProc2Procedure;
import com.hp.saphyrareborn.procedures.GriefBarProc3Procedure;
import com.hp.saphyrareborn.procedures.GriefBarProc4Procedure;
import com.hp.saphyrareborn.procedures.GriefBarProc5Procedure;
import com.hp.saphyrareborn.procedures.GriefBarProc6Procedure;
import com.hp.saphyrareborn.procedures.GriefBarProcProcedure;
import com.hp.saphyrareborn.procedures.ManicBarProc0Procedure;
import com.hp.saphyrareborn.procedures.ManicBarProc1Procedure;
import com.hp.saphyrareborn.procedures.ManicBarProc2Procedure;
import com.hp.saphyrareborn.procedures.ManicBarProc3Procedure;
import com.hp.saphyrareborn.procedures.ManicBarProc4Procedure;
import com.hp.saphyrareborn.procedures.ManicBarProc5Procedure;
import com.hp.saphyrareborn.procedures.ManicBarProc6Procedure;
import com.hp.saphyrareborn.procedures.RottingBarProc0Procedure;
import com.hp.saphyrareborn.procedures.RottingBarProc2Procedure;
import com.hp.saphyrareborn.procedures.RottingBarProc3Procedure;
import com.hp.saphyrareborn.procedures.RottingBarProc4Procedure;
import com.hp.saphyrareborn.procedures.RottingBarProc5Procedure;
import com.hp.saphyrareborn.procedures.RottingBarProc6Procedure;
import com.hp.saphyrareborn.procedures.RottingBarProcProcedure;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(value={Dist.CLIENT})
public class GrowthOverlayOverlay {
    @SubscribeEvent(priority=EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        int w = event.getGuiGraphics().guiWidth();
        int h = event.getGuiGraphics().guiHeight();
        Level world = null;
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        LocalPlayer entity = Minecraft.getInstance().player;
        if (entity != null) {
            world = entity.level();
            x = entity.getX();
            y = entity.getY();
            z = entity.getZ();
        }
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask((boolean)false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.blendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (DevotionBarProc0Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rooting_bar.png"), w / 2 + -60, h / 2 + 10, 0.0f, 0.0f, 109, 12, 109, 12);
        }
        if (DevotionBarProc0Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/devotion_bar.png"), w / 2 + -60, h / 2 + 19, 0.0f, 0.0f, 109, 12, 109, 12);
        }
        if (DevotionBarProc0Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/disgust_bar.png"), w / 2 + -60, h / 2 + 28, 0.0f, 0.0f, 109, 12, 109, 12);
        }
        if (DevotionBarProc0Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/affection_bar.png"), w / 2 + -60, h / 2 + 37, 0.0f, 0.0f, 109, 12, 109, 12);
        }
        if (ManicBarProc0Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/manic_bar.png"), w / 2 + -60, h / 2 + 46, 0.0f, 0.0f, 109, 12, 109, 12);
        }
        if (RottingBarProc0Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rotting_bar.png"), w / 2 + -60, h / 2 + 55, 0.0f, 0.0f, 109, 12, 109, 12);
        }
        if (RottingBarProcProcedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rotting_part.png"), w / 2 + -47, h / 2 + 53, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (RottingBarProc2Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rotting_part.png"), w / 2 + -31, h / 2 + 53, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (RottingBarProc3Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rotting_part.png"), w / 2 + -15, h / 2 + 53, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (RottingBarProc4Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rotting_part.png"), w / 2 + 1, h / 2 + 53, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (RottingBarProc5Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rotting_part.png"), w / 2 + 17, h / 2 + 53, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (RottingBarProc6Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rotting_part_end.png"), w / 2 + 33, h / 2 + 53, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (GriefBarProc0Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/grief_bar.png"), w / 2 + -60, h / 2 + 64, 0.0f, 0.0f, 109, 12, 109, 12);
        }
        if (GriefBarProcProcedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/grief_part.png"), w / 2 + -47, h / 2 + 62, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (GriefBarProc2Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/grief_part.png"), w / 2 + -31, h / 2 + 62, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (GriefBarProc3Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/grief_part.png"), w / 2 + -15, h / 2 + 62, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (GriefBarProc4Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/grief_part.png"), w / 2 + 1, h / 2 + 62, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (GriefBarProc5Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/grief_part.png"), w / 2 + 17, h / 2 + 62, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (GriefBarProc6Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/grief_part_end.png"), w / 2 + 33, h / 2 + 62, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (ManicBarProc1Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/manic_part.png"), w / 2 + -47, h / 2 + 44, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (ManicBarProc2Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/manic_part.png"), w / 2 + -31, h / 2 + 44, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (ManicBarProc3Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/manic_part.png"), w / 2 + -15, h / 2 + 44, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (ManicBarProc4Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/manic_part.png"), w / 2 + 1, h / 2 + 44, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (ManicBarProc5Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/manic_part.png"), w / 2 + 17, h / 2 + 44, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (ManicBarProc6Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/manic_part_end.png"), w / 2 + 33, h / 2 + 44, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc1Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/disgust_part.png"), w / 2 + -47, h / 2 + 26, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc2Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/disgust_part.png"), w / 2 + -31, h / 2 + 26, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc3Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/disgust_part.png"), w / 2 + -15, h / 2 + 26, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc4Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/disgust_part.png"), w / 2 + 1, h / 2 + 26, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc5Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/disgust_part.png"), w / 2 + 17, h / 2 + 26, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc6Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/disgust_part_end.png"), w / 2 + 33, h / 2 + 26, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc1Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/affection_part.png"), w / 2 + -47, h / 2 + 35, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc2Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/affection_part.png"), w / 2 + -31, h / 2 + 35, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc3Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/affection_part.png"), w / 2 + -15, h / 2 + 35, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc4Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/affection_part.png"), w / 2 + 1, h / 2 + 35, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc5Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/affection_part.png"), w / 2 + 17, h / 2 + 35, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc6Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/affection_part_end.png"), w / 2 + 33, h / 2 + 35, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc1Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/devotion_part.png"), w / 2 + -47, h / 2 + 17, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc2Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/devotion_part.png"), w / 2 + -31, h / 2 + 17, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc3Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/devotion_part.png"), w / 2 + -15, h / 2 + 17, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc4Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/devotion_part.png"), w / 2 + 1, h / 2 + 17, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc5Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/devotion_part.png"), w / 2 + 17, h / 2 + 17, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc6Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/devotion_part_end.png"), w / 2 + 33, h / 2 + 17, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc6Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rooting_part_end.png"), w / 2 + 33, h / 2 + 8, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc1Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rooting_part.png"), w / 2 + -47, h / 2 + 8, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc2Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rooting_part.png"), w / 2 + -31, h / 2 + 8, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc3Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rooting_part.png"), w / 2 + -15, h / 2 + 8, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc4Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rooting_part.png"), w / 2 + 1, h / 2 + 8, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (DevotionBarProc5Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/rooting_part.png"), w / 2 + 17, h / 2 + 8, 0.0f, 0.0f, 16, 16, 16, 16);
        }
        if (EnduranceOverlayUltimativeProcedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/guidashbar.png"), w / 2 + 93, h / 2 + 104, 0.0f, 0.0f, 7, 22, 7, 22);
        }
        if (EnduranceOverlayProcedure3Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/dashpart.png"), w / 2 + 94, h / 2 + 105, 0.0f, 0.0f, 5, 7, 5, 7);
        }
        if (EnduranceOverlayProcedureProcedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/dashpart3.png"), w / 2 + 94, h / 2 + 119, 0.0f, 0.0f, 5, 7, 5, 7);
        }
        if (EnduranceOverlayProcedure2Procedure.execute((Entity)entity)) {
            event.getGuiGraphics().blit(ResourceLocation.parse("saphyra_reborn:textures/screens/dashpart2.png"), w / 2 + 94, h / 2 + 112, 0.0f, 0.0f, 5, 7, 5, 7);
        }
        RenderSystem.depthMask((boolean)true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

















