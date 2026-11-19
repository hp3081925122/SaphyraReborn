package com.hp.saphyrareborn.potion;

import java.util.function.Consumer;
import com.hp.saphyrareborn.procedures.BlessingOfTheWhiteFlameOnEffectActiveTickProcedure;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

public class BlessingOfTheWhiteFlameMobEffect
extends MobEffect {
    public BlessingOfTheWhiteFlameMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -3939872);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        BlessingOfTheWhiteFlameOnEffectActiveTickProcedure.execute();
        
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions(){

            public boolean isVisibleInInventory(MobEffectInstance effect) {
                return false;
            }

            public boolean renderInventoryText(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
                return false;
            }

            public boolean isVisibleInGui(MobEffectInstance effect) {
                return false;
            }
        });
    }
}
