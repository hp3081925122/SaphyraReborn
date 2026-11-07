/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.RegistryObject
 */
package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class MokelsBossfightSaphyraModTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create((ResourceKey)Registries.CREATIVE_MODE_TAB, (String)"saphyra_reborn");
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOKELS_WITCH_BOSS = REGISTRY.register("mokels_witch_boss", () -> CreativeModeTab.builder().title((Component)Component.translatable((String)"item_group.saphyra_reborn.mokels_witch_boss")).icon(() -> new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.BOTTOMLESS_BAG.get())).displayItems((parameters, tabData) -> {
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.LINARA_SPAWN_EGG.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SAPHYRA_DIALOG_1_SPAWN_EGG.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.MARLENE_HUMAN_FORM_SPAWN_EGG.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.INFESTED_WITCH_SPAWN_EGG.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SCORCHED_SPAWN_EGG.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.NAMELESS_SPAWN_EGG.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.WITCHBOSS_SPAWN_EGG.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.MOTHBOSS_SPAWN_EGG.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.NAMELESS_SPAWN_SPAWN_EGG.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SAPHYRA_SPAWN_SPAWN_EGG.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.NAMELESS_GATEKEEPER_REMEMBRANCE.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.PUPPET_OF_YIVA_REMEMBRANCE.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SAPHYRA_REMEMBRANCE.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SINISTER_BAG.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.BOTTOMLESS_BAG.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.RULE_OF_PURIFICATION.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.RULE_OF_VISION.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.RULE_OF_HATERED.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.RULE_OF_HUNGER.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.RULE_OF_THE_P_LAGUED.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.RULE_OF_EXUDATION.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.RULE_OF_DESPAIR.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.RULE_OF_SOBBING.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.BROKEN_SIGIL_OF_THE_RED_SUN.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN_STAGE_2.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.BROKEN_SIGIL_OF_THE_RED_SUN_2.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN_STAGE_3.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.INVITATION_TO_THE_HALLOWED_TOWER.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.THE_FIRST_GOD.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.VOCAL_CORDS.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.MANIC_EYE.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SCROLL.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.WARRIOR_KEY.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SAPHYRA_KEY.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.HALLOWED_KEY_2.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.HALLOWED_KEY_3.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.HALLOWED_KEY_1.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.KEY_1.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.KEY_2.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.KEY_3.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.KEY_4.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.TOTEM_OF_ROT_AND_DECAY.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SHATTERED_BLOOD_VAIL.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.FLASK_OF_HEALING.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.FLASK_OF_HEALINGLVLTWO.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SPRIRITUAL_RUNE.get());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.SPACE_BLOCK.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.ODDMENT_STAGE_2.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.ODDMENT_HALLOWED_TOWER.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.BLOOD_DOOR_1.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.LYIOR_STATUE.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.BOSS_DOOR_1.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_1.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.BOSS_FOG.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.AZURE_TILES.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.AZURE_BRICKS_ANIMATED.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.AZURE_BRICKS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.BLOOD_BRICKS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.AZURE_STAIRS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.BLOOD_BRICKS_STAIRS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.AZURE_SLAB.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.BLOOD_BRICKS_SLAB.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.AZURE_WALL.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.BLOOD_WALL.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.POLISHED_BLOOD_BRICKS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.POLISHED_AZURE_BRICKS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.DARK_POLISHED_BLOOD_BRICKS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.DARK_POLISHED_AZURE_BRICKS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.DARK_POLISHED_BLOOD_STAIRS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.DARK_POLISHED_AZURE_STAIRS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.DARK_POLISHED_BLOOD_SLAB.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.DARK_POLISHED_AZURE_SLAB.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.DARK_POLISHED_BLOOD_WALL.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.DARK_POLISHED_AZURE_WALL.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.BLOOD_LANTERN.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.AZURE_LANTERN.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.RED_GLASS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.LOCKED_FOG_CHAIN.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.LOCKED_FOG_SHATTERED.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.LOCKED_FOG_WORSHIP.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.LOCKED_FOG_STEPS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.LOCKED_FOG_KNIFE.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()).asItem());
        tabData.accept((ItemLike)((Block)MokelsBossfightSaphyraModBlocks.LOCKED_FOG_HATERED.get()).asItem());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SAPHYRAS_HAT_HELMET.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.GRASS_VISAGE_HELMET.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SAPHYRAS_CAPE_CHESTPLATE.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.KATANA_LIGHT.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.KATANA_HEAVY.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SUNVEIL.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.MIRROR_BLADE.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SWORD_TEAR_AND_DROPLET.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.KATANA_POOL_OF_ROT.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SWORD_OF_THE_NAMELESS_GATEKEEPER.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.LOURING_CLAYMORE.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.SPIRIT_RUNE_SWORD.get());
        tabData.accept((ItemLike)MokelsBossfightSaphyraModItems.WITCH_STAFF.get());
    }).build());
}



















