package com.hp.saphyrareborn.client;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.client.model.Modelgrassvisage;
import com.hp.saphyrareborn.client.model.Modelsaphyrashat;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

/** 客户端物品与效果扩展。这些类不能从通用物品代码里引用，否则专用服务器会加载客户端渲染器。 */
@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID, value = Dist.CLIENT)
public final class ClientExtensions {
    private static final Identifier GRASS_TEXTURE = Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/entities/grassvisagetexture.png");
    private static final Identifier HAT_TEXTURE = Identifier.fromNamespaceAndPath("saphyra_reborn", "textures/entities/witchbosstexture2.png");

    private ClientExtensions() {
    }

    @SubscribeEvent
    public static void register(RegisterClientExtensionsEvent event) {
        event.registerItem(headArmor(Modelgrassvisage.LAYER_LOCATION, "headarmor", GRASS_TEXTURE), MokelsBossfightSaphyraModItems.GRASS_VISAGE_HELMET.get());
        event.registerItem(headArmor(Modelsaphyrashat.LAYER_LOCATION, "masterbody", HAT_TEXTURE), MokelsBossfightSaphyraModItems.SAPHYRAS_HAT_HELMET.get());
        IClientMobEffectExtensions hidden = new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInInventory(MobEffectInstance effect) {
                return false;
            }

            @Override
            public boolean isVisibleInGui(MobEffectInstance effect) {
                return false;
            }

            @Override
            public boolean renderInventoryText(MobEffectInstance instance, net.minecraft.client.gui.screens.inventory.AbstractContainerScreen<?> screen, net.minecraft.client.gui.GuiGraphicsExtractor graphics, int x, int y, int blitOffset) {
                return false;
            }
        };
        event.registerMobEffect(hidden,
                MokelsBossfightSaphyraModMobEffects.IMMUNE.get(),
                MokelsBossfightSaphyraModMobEffects.COOLDOWN.get(),
                MokelsBossfightSaphyraModMobEffects.STAY_STILL.get(),
                MokelsBossfightSaphyraModMobEffects.NO_GRAVITY.get(),
                MokelsBossfightSaphyraModMobEffects.SPRINT.get(),
                MokelsBossfightSaphyraModMobEffects.STUN.get(),
                MokelsBossfightSaphyraModMobEffects.BLESSING_OF_THE_WHITE_FLAME.get());
    }

    private static IClientItemExtensions headArmor(ModelLayerLocation layer, String part, Identifier texture) {
        return new IClientItemExtensions() {
            private HumanoidModel<HumanoidRenderState> model;

            @Override
            public Model getHumanoidArmorModel(ItemStack stack, EquipmentClientInfo.LayerType type, Model original) {
                if (this.model == null) {
                    ModelPart baked = Minecraft.getInstance().getEntityModels().bakeLayer(layer);
                    this.model = com.hp.saphyrareborn.item.HeadOnlyArmorModel.create(baked.getChild(part));
                }
                return this.model;
            }

            @Override
            public Identifier getArmorTexture(ItemStack stack, EquipmentClientInfo.LayerType type, EquipmentClientInfo.Layer layerInfo, Identifier fallback) {
                return texture;
            }
        };
    }
}
