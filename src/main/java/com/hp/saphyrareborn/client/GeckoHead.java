package com.hp.saphyrareborn.client;

import com.geckolib.animation.state.BoneSnapshot;
import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.hp.saphyrareborn.entity.PuppetOfYivaNemorEntity;
import java.util.function.Consumer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public final class GeckoHead {
    public static final DataTicket<String> TEXTURE = DataTickets.create("saphyra_texture", String.class);
    public static final DataTicket<Float> PITCH = DataTickets.create("saphyra_head_pitch", Float.class);
    public static final DataTicket<Float> YAW = DataTickets.create("saphyra_head_yaw", Float.class);

    private GeckoHead() {
    }

    public static void capture(LivingEntity entity, GeoRenderState state) {
        if (entity.isDeadOrDying()) {
            return;
        }
        if (entity instanceof PuppetOfYivaNemorEntity puppet && puppet.isProcedureAnimating()) {
            return;
        }
        state.addGeckolibData(PITCH, entity.getXRot());
        state.addGeckolibData(YAW, Mth.wrapDegrees(entity.getYHeadRot() - entity.yBodyRot));
    }

    public static void apply(RenderPassInfo<?> info, BoneSnapshots bones) {
        float pitch = info.getOrDefaultGeckolibData(PITCH, 0.0F) * ((float) Math.PI / 180.0F);
        float yaw = info.getOrDefaultGeckolibData(YAW, 0.0F) * ((float) Math.PI / 180.0F);
        if (pitch == 0.0F && yaw == 0.0F) {
            return;
        }
        Consumer<BoneSnapshot> rotate = bone -> {
            if (bone.hasRotation()) {
                return;
            }
            bone.setRotX(pitch);
            bone.setRotY(yaw);
        };
        bones.ifPresent("Head", rotate);
        bones.ifPresent("head", rotate);
    }
}
