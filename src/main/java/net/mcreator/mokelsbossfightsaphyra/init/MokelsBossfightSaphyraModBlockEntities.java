/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier
 *  net.minecraft.world.level.block.entity.BlockEntityType$Builder
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 *  net.neoforged.neoforge.registries.IForgeRegistry
 *  net.neoforged.neoforge.registries.RegistryObject
 */
package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.block.entity.BossDoor1TileEntity;
import com.hp.saphyrareborn.block.entity.BossDoor3TileEntity;
import com.hp.saphyrareborn.block.entity.BossDoorTileEntity;
import com.hp.saphyrareborn.block.entity.LyiorStatueAktivatedTileEntity;
import com.hp.saphyrareborn.block.entity.LyiorStatueTileEntity;
import com.hp.saphyrareborn.block.entity.OddmentHallowedTowerTileEntity;
import com.hp.saphyrareborn.block.entity.OddmentStage2TileEntity;
import com.hp.saphyrareborn.block.entity.SaphyraDoor1TileEntity;
import com.hp.saphyrareborn.block.entity.SaphyraDoor2TileEntity;
import com.hp.saphyrareborn.block.entity.SaphyraDoor3TileEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class MokelsBossfightSaphyraModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "saphyra_reborn");
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BossDoorTileEntity>> BOSS_DOOR2 = REGISTRY.register("boss_door2", () -> BlockEntityType.Builder.of(BossDoorTileEntity::new, (Block[])new Block[]{(Block)MokelsBossfightSaphyraModBlocks.BOSS_DOOR2.get()}).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LyiorStatueTileEntity>> LYIOR_STATUE = REGISTRY.register("lyior_statue", () -> BlockEntityType.Builder.of(LyiorStatueTileEntity::new, (Block[])new Block[]{(Block)MokelsBossfightSaphyraModBlocks.LYIOR_STATUE.get()}).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LyiorStatueAktivatedTileEntity>> LYIOR_STATUE_AKTIVATED = REGISTRY.register("lyior_statue_aktivated", () -> BlockEntityType.Builder.of(LyiorStatueAktivatedTileEntity::new, (Block[])new Block[]{(Block)MokelsBossfightSaphyraModBlocks.LYIOR_STATUE_AKTIVATED.get()}).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BossDoor1TileEntity>> BOSS_DOOR_1 = REGISTRY.register("boss_door_1", () -> BlockEntityType.Builder.of(BossDoor1TileEntity::new, (Block[])new Block[]{(Block)MokelsBossfightSaphyraModBlocks.BOSS_DOOR_1.get()}).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BossDoor3TileEntity>> BOSS_DOOR_3 = REGISTRY.register("boss_door_3", () -> BlockEntityType.Builder.of(BossDoor3TileEntity::new, (Block[])new Block[]{(Block)MokelsBossfightSaphyraModBlocks.BOSS_DOOR_3.get()}).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OddmentStage2TileEntity>> ODDMENT_STAGE_2 = REGISTRY.register("oddment_stage_2", () -> BlockEntityType.Builder.of(OddmentStage2TileEntity::new, (Block[])new Block[]{(Block)MokelsBossfightSaphyraModBlocks.ODDMENT_STAGE_2.get()}).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OddmentHallowedTowerTileEntity>> ODDMENT_HALLOWED_TOWER = REGISTRY.register("oddment_hallowed_tower", () -> BlockEntityType.Builder.of(OddmentHallowedTowerTileEntity::new, (Block[])new Block[]{(Block)MokelsBossfightSaphyraModBlocks.ODDMENT_HALLOWED_TOWER.get()}).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SaphyraDoor1TileEntity>> SAPHYRA_DOOR_1 = REGISTRY.register("saphyra_door_1", () -> BlockEntityType.Builder.of(SaphyraDoor1TileEntity::new, (Block[])new Block[]{(Block)MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_1.get()}).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SaphyraDoor2TileEntity>> SAPHYRA_DOOR_2 = REGISTRY.register("saphyra_door_2", () -> BlockEntityType.Builder.of(SaphyraDoor2TileEntity::new, (Block[])new Block[]{(Block)MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_2.get()}).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SaphyraDoor3TileEntity>> SAPHYRA_DOOR_3 = REGISTRY.register("saphyra_door_3", () -> BlockEntityType.Builder.of(SaphyraDoor3TileEntity::new, (Block[])new Block[]{(Block)MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_3.get()}).build(null));

    private static DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
        return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of((BlockEntityType.BlockEntitySupplier)supplier, (Block[])new Block[]{(Block)block.get()}).build(null));
    }
}




















