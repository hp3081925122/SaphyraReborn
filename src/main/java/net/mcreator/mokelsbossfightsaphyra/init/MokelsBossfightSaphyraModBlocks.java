/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 *  net.neoforged.neoforge.registries.IForgeRegistry
 *  net.neoforged.neoforge.registries.RegistryObject
 */
package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.block.AzureBricksAnimatedBlock;
import com.hp.saphyrareborn.block.AzureBricksBlock;
import com.hp.saphyrareborn.block.AzureLanternBlock;
import com.hp.saphyrareborn.block.AzureSlabBlock;
import com.hp.saphyrareborn.block.AzureStairsBlock;
import com.hp.saphyrareborn.block.AzureTilesBlock;
import com.hp.saphyrareborn.block.AzureWallBlock;
import com.hp.saphyrareborn.block.BloodBricksBlock;
import com.hp.saphyrareborn.block.BloodBricksSlabBlock;
import com.hp.saphyrareborn.block.BloodBricksStairsBlock;
import com.hp.saphyrareborn.block.BloodDoor1Block;
import com.hp.saphyrareborn.block.BloodLanternBlock;
import com.hp.saphyrareborn.block.BloodWallBlock;
import com.hp.saphyrareborn.block.BossDoor1Block;
import com.hp.saphyrareborn.block.BossDoor3Block;
import com.hp.saphyrareborn.block.BossDoorBlock;
import com.hp.saphyrareborn.block.BossFogBlock;
import com.hp.saphyrareborn.block.DarkPolishedAzureBricksBlock;
import com.hp.saphyrareborn.block.DarkPolishedAzureSlabBlock;
import com.hp.saphyrareborn.block.DarkPolishedAzureStairsBlock;
import com.hp.saphyrareborn.block.DarkPolishedAzureWallBlock;
import com.hp.saphyrareborn.block.DarkPolishedBloodBricksBlock;
import com.hp.saphyrareborn.block.DarkPolishedBloodSlabBlock;
import com.hp.saphyrareborn.block.DarkPolishedBloodStairsBlock;
import com.hp.saphyrareborn.block.DarkPolishedBloodWallBlock;
import com.hp.saphyrareborn.block.LockedFogChainBlock;
import com.hp.saphyrareborn.block.LockedFogHateredBlock;
import com.hp.saphyrareborn.block.LockedFogKnifeBlock;
import com.hp.saphyrareborn.block.LockedFogLoversBlock;
import com.hp.saphyrareborn.block.LockedFogShatteredBlock;
import com.hp.saphyrareborn.block.LockedFogStepsBlock;
import com.hp.saphyrareborn.block.LockedFogWorshipBlock;
import com.hp.saphyrareborn.block.LyiorStatueAktivatedBlock;
import com.hp.saphyrareborn.block.LyiorStatueBlock;
import com.hp.saphyrareborn.block.OddmentHallowedTowerBlock;
import com.hp.saphyrareborn.block.OddmentStage2Block;
import com.hp.saphyrareborn.block.PolishedAzureBricksBlock;
import com.hp.saphyrareborn.block.PolishedBloodBricksBlock;
import com.hp.saphyrareborn.block.RedGlassBlock;
import com.hp.saphyrareborn.block.SaphyraDoor1Block;
import com.hp.saphyrareborn.block.SaphyraDoor2Block;
import com.hp.saphyrareborn.block.SaphyraDoor3Block;
import com.hp.saphyrareborn.block.SpaceBlockBlock;
import com.hp.saphyrareborn.block.WhiteFlameBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class MokelsBossfightSaphyraModBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(Registries.BLOCK, "saphyra_reborn");
    public static final DeferredHolder<Block, Block> BOSS_DOOR2 = REGISTRY.register("boss_door2", () -> new BossDoorBlock());
    public static final DeferredHolder<Block, Block> LYIOR_STATUE = REGISTRY.register("lyior_statue", () -> new LyiorStatueBlock());
    public static final DeferredHolder<Block, Block> LYIOR_STATUE_AKTIVATED = REGISTRY.register("lyior_statue_aktivated", () -> new LyiorStatueAktivatedBlock());
    public static final DeferredHolder<Block, Block> AZURE_BRICKS = REGISTRY.register("azure_bricks", () -> new AzureBricksBlock());
    public static final DeferredHolder<Block, Block> AZURE_SLAB = REGISTRY.register("azure_slab", () -> new AzureSlabBlock());
    public static final DeferredHolder<Block, Block> AZURE_STAIRS = REGISTRY.register("azure_stairs", () -> new AzureStairsBlock());
    public static final DeferredHolder<Block, Block> AZURE_WALL = REGISTRY.register("azure_wall", () -> new AzureWallBlock());
    public static final DeferredHolder<Block, Block> AZURE_BRICKS_ANIMATED = REGISTRY.register("azure_bricks_animated", () -> new AzureBricksAnimatedBlock());
    public static final DeferredHolder<Block, Block> BOSS_DOOR_1 = REGISTRY.register("boss_door_1", () -> new BossDoor1Block());
    public static final DeferredHolder<Block, Block> BOSS_DOOR_3 = REGISTRY.register("boss_door_3", () -> new BossDoor3Block());
    public static final DeferredHolder<Block, Block> SPACE_BLOCK = REGISTRY.register("space_block", () -> new SpaceBlockBlock());
    public static final DeferredHolder<Block, Block> ODDMENT_STAGE_2 = REGISTRY.register("oddment_stage_2", () -> new OddmentStage2Block());
    public static final DeferredHolder<Block, Block> BLOOD_WALL = REGISTRY.register("blood_wall", () -> new BloodWallBlock());
    public static final DeferredHolder<Block, Block> BLOOD_BRICKS = REGISTRY.register("blood_bricks", () -> new BloodBricksBlock());
    public static final DeferredHolder<Block, Block> POLISHED_BLOOD_BRICKS = REGISTRY.register("polished_blood_bricks", () -> new PolishedBloodBricksBlock());
    public static final DeferredHolder<Block, Block> DARK_POLISHED_BLOOD_BRICKS = REGISTRY.register("dark_polished_blood_bricks", () -> new DarkPolishedBloodBricksBlock());
    public static final DeferredHolder<Block, Block> DARK_POLISHED_BLOOD_WALL = REGISTRY.register("dark_polished_blood_wall", () -> new DarkPolishedBloodWallBlock());
    public static final DeferredHolder<Block, Block> DARK_POLISHED_BLOOD_STAIRS = REGISTRY.register("dark_polished_blood_stairs", () -> new DarkPolishedBloodStairsBlock());
    public static final DeferredHolder<Block, Block> DARK_POLISHED_BLOOD_SLAB = REGISTRY.register("dark_polished_blood_slab", () -> new DarkPolishedBloodSlabBlock());
    public static final DeferredHolder<Block, Block> BLOOD_BRICKS_SLAB = REGISTRY.register("blood_bricks_slab", () -> new BloodBricksSlabBlock());
    public static final DeferredHolder<Block, Block> BLOOD_BRICKS_STAIRS = REGISTRY.register("blood_bricks_stairs", () -> new BloodBricksStairsBlock());
    public static final DeferredHolder<Block, Block> BLOOD_LANTERN = REGISTRY.register("blood_lantern", () -> new BloodLanternBlock());
    public static final DeferredHolder<Block, Block> WHITE_FLAME = REGISTRY.register("white_flame", () -> new WhiteFlameBlock());
    public static final DeferredHolder<Block, Block> BLOOD_DOOR_1 = REGISTRY.register("blood_door_1", () -> new BloodDoor1Block());
    public static final DeferredHolder<Block, Block> POLISHED_AZURE_BRICKS = REGISTRY.register("polished_azure_bricks", () -> new PolishedAzureBricksBlock());
    public static final DeferredHolder<Block, Block> AZURE_LANTERN = REGISTRY.register("azure_lantern", () -> new AzureLanternBlock());
    public static final DeferredHolder<Block, Block> DARK_POLISHED_AZURE_BRICKS = REGISTRY.register("dark_polished_azure_bricks", () -> new DarkPolishedAzureBricksBlock());
    public static final DeferredHolder<Block, Block> DARK_POLISHED_AZURE_SLAB = REGISTRY.register("dark_polished_azure_slab", () -> new DarkPolishedAzureSlabBlock());
    public static final DeferredHolder<Block, Block> DARK_POLISHED_AZURE_STAIRS = REGISTRY.register("dark_polished_azure_stairs", () -> new DarkPolishedAzureStairsBlock());
    public static final DeferredHolder<Block, Block> DARK_POLISHED_AZURE_WALL = REGISTRY.register("dark_polished_azure_wall", () -> new DarkPolishedAzureWallBlock());
    public static final DeferredHolder<Block, Block> LOCKED_FOG_CHAIN = REGISTRY.register("locked_fog_chain", () -> new LockedFogChainBlock());
    public static final DeferredHolder<Block, Block> LOCKED_FOG_SHATTERED = REGISTRY.register("locked_fog_shattered", () -> new LockedFogShatteredBlock());
    public static final DeferredHolder<Block, Block> LOCKED_FOG_WORSHIP = REGISTRY.register("locked_fog_worship", () -> new LockedFogWorshipBlock());
    public static final DeferredHolder<Block, Block> LOCKED_FOG_KNIFE = REGISTRY.register("locked_fog_knife", () -> new LockedFogKnifeBlock());
    public static final DeferredHolder<Block, Block> LOCKED_FOG_STEPS = REGISTRY.register("locked_fog_steps", () -> new LockedFogStepsBlock());
    public static final DeferredHolder<Block, Block> LOCKED_FOG_LOVERS = REGISTRY.register("locked_fog_lovers", () -> new LockedFogLoversBlock());
    public static final DeferredHolder<Block, Block> LOCKED_FOG_HATERED = REGISTRY.register("locked_fog_hatered", () -> new LockedFogHateredBlock());
    public static final DeferredHolder<Block, Block> ODDMENT_HALLOWED_TOWER = REGISTRY.register("oddment_hallowed_tower", () -> new OddmentHallowedTowerBlock());
    public static final DeferredHolder<Block, Block> RED_GLASS = REGISTRY.register("red_glass", () -> new RedGlassBlock());
    public static final DeferredHolder<Block, Block> BOSS_FOG = REGISTRY.register("boss_fog", () -> new BossFogBlock());
    public static final DeferredHolder<Block, Block> AZURE_TILES = REGISTRY.register("azure_tiles", () -> new AzureTilesBlock());
    public static final DeferredHolder<Block, Block> SAPHYRA_DOOR_1 = REGISTRY.register("saphyra_door_1", () -> new SaphyraDoor1Block());
    public static final DeferredHolder<Block, Block> SAPHYRA_DOOR_2 = REGISTRY.register("saphyra_door_2", () -> new SaphyraDoor2Block());
    public static final DeferredHolder<Block, Block> SAPHYRA_DOOR_3 = REGISTRY.register("saphyra_door_3", () -> new SaphyraDoor3Block());
}

















