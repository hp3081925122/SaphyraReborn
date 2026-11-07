/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.LevelAccessor
 *  software.bernie.geckolib.animatable.GeoItem
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.item.SaphyrasCapeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.LevelAccessor;
import software.bernie.geckolib.animatable.GeoItem;

public class SaphyrasCapeChestplateTickEventProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null) {
            return;
        }
        if (world.isEmptyBlock(BlockPos.containing((double)x, (double)(y - 2.0), (double)z)) && world.isEmptyBlock(BlockPos.containing((double)x, (double)(y - 1.0), (double)z))) {
            SaphyrasCapeItem armor;
            Item item;
            LivingEntity _entity1;
            if (entity instanceof LivingEntity && !(_entity1 = (LivingEntity)entity).level().isClientSide()) {
                _entity1.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 2, 2, false, false));
            }
            if ((item = itemstack.getItem()) instanceof SaphyrasCapeItem && (armor = (SaphyrasCapeItem)item) instanceof GeoItem) {
                CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString("geckoAnim", "idle"));
            }
        }
    }
}






















