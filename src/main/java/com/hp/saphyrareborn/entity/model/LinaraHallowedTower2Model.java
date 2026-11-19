package com.hp.saphyrareborn.entity.model;

import com.hp.saphyrareborn.entity.LinaraHallowedTower2Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class LinaraHallowedTower2Model
extends GeoModel<LinaraHallowedTower2Entity> {
    public ResourceLocation getAnimationResource(LinaraHallowedTower2Entity entity) {
        return new ResourceLocation("saphyra_reborn", "animations/marne.animation.json");
    }

    public ResourceLocation getModelResource(LinaraHallowedTower2Entity entity) {
        return new ResourceLocation("saphyra_reborn", "geo/marne.geo.json");
    }

    public ResourceLocation getTextureResource(LinaraHallowedTower2Entity entity) {
        return new ResourceLocation("saphyra_reborn", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(LinaraHallowedTower2Entity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float)Math.PI / 180));
            head.setRotY(entityData.netHeadYaw() * ((float)Math.PI / 180));
        }
    }
}
