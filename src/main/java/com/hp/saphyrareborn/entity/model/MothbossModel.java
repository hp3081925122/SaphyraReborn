package com.hp.saphyrareborn.entity.model;

import com.hp.saphyrareborn.entity.MothbossEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MothbossModel
extends GeoModel<MothbossEntity> {
    public ResourceLocation getAnimationResource(MothbossEntity entity) {
        return new ResourceLocation("saphyra_reborn", "animations/saphyramothboss.animation.json");
    }

    public ResourceLocation getModelResource(MothbossEntity entity) {
        return new ResourceLocation("saphyra_reborn", "geo/saphyramothboss.geo.json");
    }

    public ResourceLocation getTextureResource(MothbossEntity entity) {
        return new ResourceLocation("saphyra_reborn", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(MothbossEntity animatable, long instanceId, AnimationState animationState) {
        if (animatable.isDeadOrDying()) {
            return;
        }
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            if (animatable.isProcedureAnimating() && (head.getRotX() != 0.0f || head.getRotY() != 0.0f || head.getRotZ() != 0.0f)) {
                return;
            }
            EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float)Math.PI / 180));
            head.setRotY(entityData.netHeadYaw() * ((float)Math.PI / 180));
        }
    }
}
