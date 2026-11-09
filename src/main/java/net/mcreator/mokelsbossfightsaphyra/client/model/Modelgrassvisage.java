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
 *  net.minecraft.resources.Identifier
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
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public class Modelgrassvisage extends EntityModel<net.minecraft.client.renderer.entity.state.EntityRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("saphyra_reborn", "modelgrassvisage"), "main");
    public final ModelPart headarmor;

    public Modelgrassvisage(ModelPart root) {
        super(root);
        this.headarmor = root.getChild("headarmor");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition headarmor = partdefinition.addOrReplaceChild("headarmor", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.75f)), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        PartDefinition cube_r1 = headarmor.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-12.0f, -12.0f, 0.0f, 22.0f, 22.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)-4.0f, (float)-3.0f, (float)0.0f, (float)0.0f, (float)0.7854f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)64, (int)64);
    }

    public void setupAnim(net.minecraft.client.renderer.entity.state.EntityRenderState state) {
    }

    
}





















