/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AdddCurseProcedure {
    public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
        if (entity == null) {
            return;
        }
        if (MokelsBossfightSaphyraModItems.ADD_CURSE.get() == itemstack.getItem()) {
            if (entity.isShiftKeyDown()) {
                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Rotting + 10.0;
                MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                    capability.Rotting = _setval;
                    capability.syncPlayerVariables(entity);
                });
                if (world instanceof Level) {
                    Level _level1 = (Level)world;
                    if (!_level1.isClientSide()) {
                        _level1.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("saphyra_reborn:rule_spell")).map(net.minecraft.core.Holder::value).orElseThrow(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                    } else {
                        _level1.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("saphyra_reborn:rule_spell")).map(net.minecraft.core.Holder::value).orElseThrow(), SoundSource.NEUTRAL, 1.0f, 1.0f, false);
                    }
                }
            } else if (!entity.isShiftKeyDown()) {
                double _setval = MokelsBossfightSaphyraModVariables.getPlayerVariables(entity).Grief + 10.0;
                MokelsBossfightSaphyraModVariables.withPlayerVariables(entity, capability -> {
                    capability.Grief = _setval;
                    capability.syncPlayerVariables(entity);
                });
                if (world instanceof Level) {
                    Level _level2 = (Level)world;
                    if (!_level2.isClientSide()) {
                        _level2.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("saphyra_reborn:rule_spell")).map(net.minecraft.core.Holder::value).orElseThrow(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                    } else {
                        _level2.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.get(Identifier.parse("saphyra_reborn:rule_spell")).map(net.minecraft.core.Holder::value).orElseThrow(), SoundSource.NEUTRAL, 1.0f, 1.0f, false);
                    }
                }
            }
        }
    }
}





