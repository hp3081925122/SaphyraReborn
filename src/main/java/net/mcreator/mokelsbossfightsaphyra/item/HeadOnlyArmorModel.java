package com.hp.saphyrareborn.item;

import java.util.List;
import java.util.Map;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

/** 只渲染头部部件的盔甲模型，用于萨菲拉之帽和噬草者之面。 */
final class HeadOnlyArmorModel {
    private HeadOnlyArmorModel() {}

    static HumanoidModel<LivingEntity> create(ModelPart head) {
        return new HumanoidModel<>(new ModelPart(List.of(), Map.of(
                "head", head,
                "hat", empty(),
                "body", empty(),
                "right_arm", empty(),
                "left_arm", empty(),
                "right_leg", empty(),
                "left_leg", empty())));
    }

    private static ModelPart empty() {
        return new ModelPart(List.of(), Map.of());
    }
}
