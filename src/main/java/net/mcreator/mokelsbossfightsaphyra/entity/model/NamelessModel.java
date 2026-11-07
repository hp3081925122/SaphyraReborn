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

import com.hp.saphyrareborn.entity.NamelessEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class NamelessModel
extends GeoModel<NamelessEntity> {
    public ResourceLocation getAnimationResource(NamelessEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "animations/knightboss.animation.json");
    }

    public ResourceLocation getModelResource(NamelessEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "geo/knightboss.geo.json");
    }

    public ResourceLocation getTextureResource(NamelessEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "textures/entities/" + entity.getTexture() + ".png");
    }

    public void setCustomAnimations(NamelessEntity animatable, long instanceId, AnimationState animationState) {
        GeoBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * ((float)Math.PI / 180));
            head.setRotY(entityData.netHeadYaw() * ((float)Math.PI / 180));
        }
    }
}
















