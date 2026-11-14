/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.constant.DataTickets
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.animation.AnimationState
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.model.data.EntityModelData
 */
package com.hp.saphyrareborn.entity.model;

import com.hp.saphyrareborn.entity.MothbossEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MothbossModel
extends GeoModel<MothbossEntity> {
    public ResourceLocation getAnimationResource(MothbossEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "animations/saphyramothboss.animation.json");
    }

    public ResourceLocation getModelResource(MothbossEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "geo/saphyramothboss.geo.json");
    }

    public ResourceLocation getTextureResource(MothbossEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(MothbossEntity animatable, long instanceId, AnimationState animationState) {
        if (animatable.isDeadOrDying()) {
            return;
        }
        GeoBone head = this.getAnimationProcessor().getBone("head");
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
















