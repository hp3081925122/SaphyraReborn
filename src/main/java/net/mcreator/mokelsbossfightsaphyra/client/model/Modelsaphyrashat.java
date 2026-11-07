/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeDeformation
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 */
package com.hp.saphyrareborn.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class Modelsaphyrashat<T extends Entity>
extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "modelsaphyrashat"), "main");
    public final ModelPart masterbody;

    public Modelsaphyrashat(ModelPart root) {
        this.masterbody = root.getChild("masterbody");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition masterbody = partdefinition.addOrReplaceChild("masterbody", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        PartDefinition hat = masterbody.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 37).addBox(-4.0f, -6.3f, -4.0f, 8.0f, 5.0f, 8.0f, new CubeDeformation(0.8f)).texOffs(0, 0).addBox(-8.0f, -1.0f, -8.0f, 16.0f, 1.0f, 16.0f, new CubeDeformation(0.8f)), PartPose.offsetAndRotation((float)0.0f, (float)-4.0f, (float)-1.0f, (float)-0.5411f, (float)0.0f, (float)0.0f));
        PartDefinition hat2 = hat.addOrReplaceChild("hat2", CubeListBuilder.create().texOffs(32, 17).addBox(-3.0f, -5.0f, -3.0f, 6.0f, 5.0f, 6.0f, new CubeDeformation(0.8f)), PartPose.offsetAndRotation((float)0.0f, (float)-5.0f, (float)0.0f, (float)-0.6109f, (float)0.0f, (float)0.0f));
        PartDefinition hat3 = hat2.addOrReplaceChild("hat3", CubeListBuilder.create().texOffs(8, 66).addBox(-2.0f, -4.0f, -2.0f, 4.0f, 4.0f, 4.0f, new CubeDeformation(0.8f)), PartPose.offsetAndRotation((float)0.0f, (float)-4.0f, (float)0.0f, (float)-0.6109f, (float)0.0f, (float)0.0f));
        PartDefinition hat4 = hat3.addOrReplaceChild("hat4", CubeListBuilder.create().texOffs(0, 17).addBox(-2.0f, -2.0f, 0.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.6f)), PartPose.offset((float)1.0f, (float)-7.0f, (float)-1.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)128, (int)128);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        this.masterbody.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}




















