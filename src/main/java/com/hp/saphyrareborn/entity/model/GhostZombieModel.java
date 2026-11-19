package com.hp.saphyrareborn.entity.model;

import com.hp.saphyrareborn.entity.GhostZombieEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GhostZombieModel
extends GeoModel<GhostZombieEntity> {
    public ResourceLocation getAnimationResource(GhostZombieEntity entity) {
        return new ResourceLocation("saphyra_reborn", "animations/sulfurzombie.animation.json");
    }

    public ResourceLocation getModelResource(GhostZombieEntity entity) {
        return new ResourceLocation("saphyra_reborn", "geo/sulfurzombie.geo.json");
    }

    public ResourceLocation getTextureResource(GhostZombieEntity entity) {
        return new ResourceLocation("saphyra_reborn", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(GhostZombieEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float)Math.PI / 180));
            head.setRotY(entityData.netHeadYaw() * ((float)Math.PI / 180));
        }
    }
}
