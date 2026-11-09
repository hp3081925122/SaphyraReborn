/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.DoubleHighBlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.level.block.Block
 *  net.neoforged.neoforge.common.DeferredSpawnEggItem
 *  net.minecraftforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 *  net.minecraftforge.registries.RegistryObject
 */
package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.block.display.BossDoor1DisplayItem;
import com.hp.saphyrareborn.block.display.BossDoor3DisplayItem;
import com.hp.saphyrareborn.block.display.BossDoorDisplayItem;
import com.hp.saphyrareborn.block.display.LyiorStatueAktivatedDisplayItem;
import com.hp.saphyrareborn.block.display.LyiorStatueDisplayItem;
import com.hp.saphyrareborn.block.display.OddmentHallowedTowerDisplayItem;
import com.hp.saphyrareborn.block.display.OddmentStage2DisplayItem;
import com.hp.saphyrareborn.block.display.SaphyraDoor1DisplayItem;
import com.hp.saphyrareborn.block.display.SaphyraDoor2DisplayItem;
import com.hp.saphyrareborn.block.display.SaphyraDoor3DisplayItem;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.item.AddCurseItem;
import com.hp.saphyrareborn.item.BellSpiritItem;
import com.hp.saphyrareborn.item.BottomlessBagItem;
import com.hp.saphyrareborn.item.BrokenSigilOfTheRedSun2Item;
import com.hp.saphyrareborn.item.BrokenSigilOfTheRedSunItem;
import com.hp.saphyrareborn.item.EmptyRuleItem;
import com.hp.saphyrareborn.item.FlaskOfHealingItem;
import com.hp.saphyrareborn.item.FlaskOfHealinglvltwoItem;
import com.hp.saphyrareborn.item.GluttonousFlaskItem;
import com.hp.saphyrareborn.item.GrassVisageItem;
import com.hp.saphyrareborn.item.HallowedKey1Item;
import com.hp.saphyrareborn.item.HallowedKey2Item;
import com.hp.saphyrareborn.item.HallowedKey3Item;
import com.hp.saphyrareborn.item.InvitationToTheHallowedTowerItem;
import com.hp.saphyrareborn.item.KatanaHeavyItem;
import com.hp.saphyrareborn.item.KatanaLightItem;
import com.hp.saphyrareborn.item.KatanaPoolOfRotItem;
import com.hp.saphyrareborn.item.Key1Item;
import com.hp.saphyrareborn.item.Key2Item;
import com.hp.saphyrareborn.item.Key3Item;
import com.hp.saphyrareborn.item.Key4Item;
import com.hp.saphyrareborn.item.LouringClaymoreItem;
import com.hp.saphyrareborn.item.ManicEyeItem;
import com.hp.saphyrareborn.item.MirrorBladeItem;
import com.hp.saphyrareborn.item.NamelessGatekeeperRemembranceItem;
import com.hp.saphyrareborn.item.PuppetOfYivaRemembranceItem;
import com.hp.saphyrareborn.item.RuleOfDespairItem;
import com.hp.saphyrareborn.item.RuleOfExudationItem;
import com.hp.saphyrareborn.item.RuleOfHateredItem;
import com.hp.saphyrareborn.item.RuleOfHungerItem;
import com.hp.saphyrareborn.item.RuleOfPurificationItem;
import com.hp.saphyrareborn.item.RuleOfSobbingItem;
import com.hp.saphyrareborn.item.RuleOfThePLaguedItem;
import com.hp.saphyrareborn.item.RuleOfVisionItem;
import com.hp.saphyrareborn.item.SaphyraKeyItem;
import com.hp.saphyrareborn.item.SaphyraRemembranceItem;
import com.hp.saphyrareborn.item.SaphyrasCapeItem;
import com.hp.saphyrareborn.item.SaphyrasHatItem;
import com.hp.saphyrareborn.item.ScrollItem;
import com.hp.saphyrareborn.item.ShatteredBloodVailItem;
import com.hp.saphyrareborn.item.SigilOfTheRedSunItem;
import com.hp.saphyrareborn.item.SigilOfTheRedSunStage2Item;
import com.hp.saphyrareborn.item.SigilOfTheRedSunStage3Item;
import com.hp.saphyrareborn.item.SinisterBagItem;
import com.hp.saphyrareborn.item.SpiritRuneSwordItem;
import com.hp.saphyrareborn.item.SpriritualRuneItem;
import com.hp.saphyrareborn.item.SunveilItem;
import com.hp.saphyrareborn.item.SwordOfTheNamelessGatekeeperItem;
import com.hp.saphyrareborn.item.SwordTearAndDropletItem;
import com.hp.saphyrareborn.item.TheFirstGodItem;
import com.hp.saphyrareborn.item.TotemOfRotAndDecayItem;
import com.hp.saphyrareborn.item.VocalCordsItem;
import com.hp.saphyrareborn.item.WarriorKeyItem;
import com.hp.saphyrareborn.item.WitchStaffItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;

public class MokelsBossfightSaphyraModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(Registries.ITEM, "saphyra_reborn");
    public static final DeferredHolder<Item, Item> WITCHBOSS_SPAWN_EGG = item("witchboss_spawn_egg", () -> new SpawnEggItem(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).spawnEgg(MokelsBossfightSaphyraModEntities.WITCHBOSS.get())));
    public static final DeferredHolder<Item, Item> TOTEM_OF_ROT_AND_DECAY = item("totem_of_rot_and_decay", () -> new TotemOfRotAndDecayItem());
    public static final DeferredHolder<Item, Item> BOTTOMLESS_BAG = item("bottomless_bag", () -> new BottomlessBagItem());
    public static final DeferredHolder<Item, Item> WITCH_STAFF = item("witch_staff", () -> new WitchStaffItem());
    public static final DeferredHolder<Item, Item> FLASK_OF_HEALING = item("flask_of_healing", () -> new FlaskOfHealingItem());
    public static final DeferredHolder<Item, Item> FLASK_OF_HEALINGLVLTWO = item("flask_of_healinglvltwo", () -> new FlaskOfHealinglvltwoItem());
    public static final DeferredHolder<Item, Item> SPRIRITUAL_RUNE = item("spriritual_rune", () -> new SpriritualRuneItem());
    public static final DeferredHolder<Item, Item> SAPHYRAS_HAT_HELMET = item("saphyras_hat_helmet", () -> new SaphyrasHatItem.Helmet());
    public static final DeferredHolder<Item, Item> SPIRIT_RUNE_SWORD = item("spirit_rune_sword", () -> new SpiritRuneSwordItem());
    public static final DeferredHolder<Item, Item> BELL_SPIRIT = item("bell_spirit", () -> new BellSpiritItem());
    public static final DeferredHolder<Item, Item> MOTHBOSS_SPAWN_EGG = item("mothboss_spawn_egg", () -> new SpawnEggItem(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).spawnEgg(MokelsBossfightSaphyraModEntities.MOTHBOSS.get())));
    public static final DeferredHolder<Item, Item> PUPPET_OF_YIVA_NEMOR_SPAWN_EGG = item("puppet_of_yiva_nemor_spawn_egg", () -> new SpawnEggItem(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).spawnEgg(MokelsBossfightSaphyraModEntities.PUPPET_OF_YIVA_NEMOR.get())));
    public static final DeferredHolder<Item, Item> RULE_OF_PURIFICATION = item("rule_of_purification", () -> new RuleOfPurificationItem());
    public static final DeferredHolder<Item, Item> EMPTY_RULE = item("empty_rule", () -> new EmptyRuleItem());
    public static final DeferredHolder<Item, Item> RULE_OF_VISION = item("rule_of_vision", () -> new RuleOfVisionItem());
    public static final DeferredHolder<Item, Item> NAMELESS_SPAWN_EGG = item("nameless_spawn_egg", () -> new SpawnEggItem(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).spawnEgg(MokelsBossfightSaphyraModEntities.NAMELESS.get())));
    public static final DeferredHolder<Item, Item> BOSS_DOOR2 = item(MokelsBossfightSaphyraModBlocks.BOSS_DOOR2.getId().getPath(), () -> new BossDoorDisplayItem((Block)MokelsBossfightSaphyraModBlocks.BOSS_DOOR2.get(), new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)));
    public static final DeferredHolder<Item, Item> LYIOR_STATUE = item(MokelsBossfightSaphyraModBlocks.LYIOR_STATUE.getId().getPath(), () -> new LyiorStatueDisplayItem((Block)MokelsBossfightSaphyraModBlocks.LYIOR_STATUE.get(), new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)));
    public static final DeferredHolder<Item, Item> LYIOR_STATUE_AKTIVATED = item(MokelsBossfightSaphyraModBlocks.LYIOR_STATUE_AKTIVATED.getId().getPath(), () -> new LyiorStatueAktivatedDisplayItem((Block)MokelsBossfightSaphyraModBlocks.LYIOR_STATUE_AKTIVATED.get(), new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)));
    public static final DeferredHolder<Item, Item> AZURE_BRICKS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_BRICKS);
    public static final DeferredHolder<Item, Item> AZURE_SLAB = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_SLAB);
    public static final DeferredHolder<Item, Item> AZURE_STAIRS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_STAIRS);
    public static final DeferredHolder<Item, Item> AZURE_WALL = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_WALL);
    public static final DeferredHolder<Item, Item> AZURE_BRICKS_ANIMATED = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_BRICKS_ANIMATED);
    public static final DeferredHolder<Item, Item> BOSS_DOOR_1 = item(MokelsBossfightSaphyraModBlocks.BOSS_DOOR_1.getId().getPath(), () -> new BossDoor1DisplayItem((Block)MokelsBossfightSaphyraModBlocks.BOSS_DOOR_1.get(), new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)));
    public static final DeferredHolder<Item, Item> WARRIOR_KEY = item("warrior_key", () -> new WarriorKeyItem());
    public static final DeferredHolder<Item, Item> BOSS_DOOR_3 = item(MokelsBossfightSaphyraModBlocks.BOSS_DOOR_3.getId().getPath(), () -> new BossDoor3DisplayItem((Block)MokelsBossfightSaphyraModBlocks.BOSS_DOOR_3.get(), new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)));
    public static final DeferredHolder<Item, Item> ADD_CURSE = item("add_curse", () -> new AddCurseItem());
    public static final DeferredHolder<Item, Item> SHATTERED_BLOOD_VAIL = item("shattered_blood_vail", () -> new ShatteredBloodVailItem());
    public static final DeferredHolder<Item, Item> RULE_OF_HATERED = item("rule_of_hatered", () -> new RuleOfHateredItem());
    public static final DeferredHolder<Item, Item> GLUTTONOUS_FLASK = item("gluttonous_flask", () -> new GluttonousFlaskItem());
    public static final DeferredHolder<Item, Item> SPACE_BLOCK = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.SPACE_BLOCK);
    public static final DeferredHolder<Item, SaphyrasCapeItem> SAPHYRAS_CAPE_CHESTPLATE = item("saphyras_cape_chestplate", () -> new SaphyrasCapeItem(ArmorType.CHESTPLATE, new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).fireResistant()));
    public static final DeferredHolder<Item, Item> ODDMENT_STAGE_2 = item(MokelsBossfightSaphyraModBlocks.ODDMENT_STAGE_2.getId().getPath(), () -> new OddmentStage2DisplayItem((Block)MokelsBossfightSaphyraModBlocks.ODDMENT_STAGE_2.get(), new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)));
    public static final DeferredHolder<Item, Item> RULE_OF_HUNGER = item("rule_of_hunger", () -> new RuleOfHungerItem());
    public static final DeferredHolder<Item, Item> RULE_OF_THE_P_LAGUED = item("rule_of_the_p_lagued", () -> new RuleOfThePLaguedItem());
    public static final DeferredHolder<Item, Item> SIGIL_OF_THE_RED_SUN = item("sigil_of_the_red_sun", () -> new SigilOfTheRedSunItem());
    public static final DeferredHolder<Item, Item> VOCAL_CORDS = item("vocal_cords", () -> new VocalCordsItem());
    public static final DeferredHolder<Item, Item> LINARA_SPAWN_EGG = item("linara_spawn_egg", () -> new SpawnEggItem(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).spawnEgg(MokelsBossfightSaphyraModEntities.LINARA.get())));
    public static final DeferredHolder<Item, Item> GRASS_VISAGE_HELMET = item("grass_visage_helmet", () -> new GrassVisageItem.Helmet());
    public static final DeferredHolder<Item, Item> SWORD_OF_THE_NAMELESS_GATEKEEPER = item("sword_of_the_nameless_gatekeeper", () -> new SwordOfTheNamelessGatekeeperItem());
    public static final DeferredHolder<Item, Item> BLOOD_WALL = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.BLOOD_WALL);
    public static final DeferredHolder<Item, Item> BLOOD_BRICKS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.BLOOD_BRICKS);
    public static final DeferredHolder<Item, Item> POLISHED_BLOOD_BRICKS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.POLISHED_BLOOD_BRICKS);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_BLOOD_BRICKS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_BLOOD_BRICKS);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_BLOOD_WALL = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_BLOOD_WALL);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_BLOOD_STAIRS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_BLOOD_STAIRS);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_BLOOD_SLAB = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_BLOOD_SLAB);
    public static final DeferredHolder<Item, Item> BLOOD_BRICKS_SLAB = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.BLOOD_BRICKS_SLAB);
    public static final DeferredHolder<Item, Item> BLOOD_BRICKS_STAIRS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.BLOOD_BRICKS_STAIRS);
    public static final DeferredHolder<Item, Item> PUPPET_OF_YIVA_REMEMBRANCE = item("puppet_of_yiva_remembrance", () -> new PuppetOfYivaRemembranceItem());
    public static final DeferredHolder<Item, Item> SAPHYRA_REMEMBRANCE = item("saphyra_remembrance", () -> new SaphyraRemembranceItem());
    public static final DeferredHolder<Item, Item> BLOOD_LANTERN = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.BLOOD_LANTERN);
    public static final DeferredHolder<Item, Item> RULE_OF_EXUDATION = item("rule_of_exudation", () -> new RuleOfExudationItem());
    public static final DeferredHolder<Item, Item> WHITE_FLAME = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.WHITE_FLAME);
    public static final DeferredHolder<Item, Item> BLOOD_DOOR_1 = MokelsBossfightSaphyraModItems.doubleBlock(MokelsBossfightSaphyraModBlocks.BLOOD_DOOR_1);
    public static final DeferredHolder<Item, Item> KEY_1 = item("key_1", () -> new Key1Item());
    public static final DeferredHolder<Item, Item> KEY_2 = item("key_2", () -> new Key2Item());
    public static final DeferredHolder<Item, Item> KEY_3 = item("key_3", () -> new Key3Item());
    public static final DeferredHolder<Item, Item> KEY_4 = item("key_4", () -> new Key4Item());
    public static final DeferredHolder<Item, Item> NAMELESS_GATEKEEPER_REMEMBRANCE = item("nameless_gatekeeper_remembrance", () -> new NamelessGatekeeperRemembranceItem());
    public static final DeferredHolder<Item, Item> POLISHED_AZURE_BRICKS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.POLISHED_AZURE_BRICKS);
    public static final DeferredHolder<Item, Item> AZURE_LANTERN = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_LANTERN);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_AZURE_BRICKS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_AZURE_BRICKS);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_AZURE_SLAB = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_AZURE_SLAB);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_AZURE_STAIRS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_AZURE_STAIRS);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_AZURE_WALL = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_AZURE_WALL);
    public static final DeferredHolder<Item, Item> SAPHYRA_DIALOG_1_SPAWN_EGG = item("saphyra_dialog_1_spawn_egg", () -> new SpawnEggItem(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).spawnEgg(MokelsBossfightSaphyraModEntities.SAPHYRA_DIALOG_1.get())));
    public static final DeferredHolder<Item, Item> INVITATION_TO_THE_HALLOWED_TOWER = item("invitation_to_the_hallowed_tower", () -> new InvitationToTheHallowedTowerItem());
    public static final DeferredHolder<Item, Item> LOURING_CLAYMORE = item("louring_claymore", () -> new LouringClaymoreItem());
    public static final DeferredHolder<Item, Item> SUNVEIL = item("sunveil", () -> new SunveilItem());
    public static final DeferredHolder<Item, Item> MIRROR_BLADE = item("mirror_blade", () -> new MirrorBladeItem());
    public static final DeferredHolder<Item, Item> NAMELESS_SPAWN_SPAWN_EGG = item("nameless_spawn_spawn_egg", () -> new SpawnEggItem(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).spawnEgg(MokelsBossfightSaphyraModEntities.NAMELESS_SPAWN.get())));
    public static final DeferredHolder<Item, Item> MARLENE_HUMAN_FORM_SPAWN_EGG = item("marlene_human_form_spawn_egg", () -> new SpawnEggItem(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).spawnEgg(MokelsBossfightSaphyraModEntities.MARLENE_HUMAN_FORM.get())));
    public static final DeferredHolder<Item, Item> HALLOWED_KEY_1 = item("hallowed_key_1", () -> new HallowedKey1Item());
    public static final DeferredHolder<Item, Item> HALLOWED_KEY_2 = item("hallowed_key_2", () -> new HallowedKey2Item());
    public static final DeferredHolder<Item, Item> HALLOWED_KEY_3 = item("hallowed_key_3", () -> new HallowedKey3Item());
    public static final DeferredHolder<Item, Item> LOCKED_FOG_CHAIN = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_CHAIN);
    public static final DeferredHolder<Item, Item> LOCKED_FOG_SHATTERED = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_SHATTERED);
    public static final DeferredHolder<Item, Item> LOCKED_FOG_WORSHIP = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_WORSHIP);
    public static final DeferredHolder<Item, Item> LOCKED_FOG_KNIFE = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_KNIFE);
    public static final DeferredHolder<Item, Item> LOCKED_FOG_STEPS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_STEPS);
    public static final DeferredHolder<Item, Item> LOCKED_FOG_LOVERS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS);
    public static final DeferredHolder<Item, Item> LOCKED_FOG_HATERED = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_HATERED);
    public static final DeferredHolder<Item, Item> SIGIL_OF_THE_RED_SUN_STAGE_2 = item("sigil_of_the_red_sun_stage_2", () -> new SigilOfTheRedSunStage2Item());
    public static final DeferredHolder<Item, Item> SIGIL_OF_THE_RED_SUN_STAGE_3 = item("sigil_of_the_red_sun_stage_3", () -> new SigilOfTheRedSunStage3Item());
    public static final DeferredHolder<Item, Item> THE_FIRST_GOD = item("the_first_god", () -> new TheFirstGodItem());
    public static final DeferredHolder<Item, Item> SINISTER_BAG = item("sinister_bag", () -> new SinisterBagItem());
    public static final DeferredHolder<Item, Item> BROKEN_SIGIL_OF_THE_RED_SUN = item("broken_sigil_of_the_red_sun", () -> new BrokenSigilOfTheRedSunItem());
    public static final DeferredHolder<Item, Item> MANIC_EYE = item("manic_eye", () -> new ManicEyeItem());
    public static final DeferredHolder<Item, Item> ODDMENT_HALLOWED_TOWER = item(MokelsBossfightSaphyraModBlocks.ODDMENT_HALLOWED_TOWER.getId().getPath(), () -> new OddmentHallowedTowerDisplayItem((Block)MokelsBossfightSaphyraModBlocks.ODDMENT_HALLOWED_TOWER.get(), new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)));
    public static final DeferredHolder<Item, Item> RED_GLASS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.RED_GLASS);
    public static final DeferredHolder<Item, Item> BOSS_FOG = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.BOSS_FOG);
    public static final DeferredHolder<Item, Item> SCORCHED_SPAWN_EGG = item("scorched_spawn_egg", () -> new SpawnEggItem(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).spawnEgg(MokelsBossfightSaphyraModEntities.SCORCHED.get())));
    public static final DeferredHolder<Item, Item> INFESTED_WITCH_SPAWN_EGG = item("infested_witch_spawn_egg", () -> new SpawnEggItem(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).spawnEgg(MokelsBossfightSaphyraModEntities.INFESTED_WITCH.get())));
    public static final DeferredHolder<Item, Item> BROKEN_SIGIL_OF_THE_RED_SUN_2 = item("broken_sigil_of_the_red_sun_2", () -> new BrokenSigilOfTheRedSun2Item());
    public static final DeferredHolder<Item, Item> SAPHYRA_SPAWN_SPAWN_EGG = item("saphyra_spawn_spawn_egg", () -> new SpawnEggItem(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).spawnEgg(MokelsBossfightSaphyraModEntities.SAPHYRA_SPAWN.get())));
    public static final DeferredHolder<Item, Item> SCROLL = item("scroll", () -> new ScrollItem());
    public static final DeferredHolder<Item, Item> KATANA_HEAVY = item("katana_heavy", () -> new KatanaHeavyItem());
    public static final DeferredHolder<Item, Item> KATANA_LIGHT = item("katana_light", () -> new KatanaLightItem());
    public static final DeferredHolder<Item, Item> KATANA_POOL_OF_ROT = item("katana_pool_of_rot", () -> new KatanaPoolOfRotItem());
    public static final DeferredHolder<Item, Item> SWORD_TEAR_AND_DROPLET = item("sword_tear_and_droplet", () -> new SwordTearAndDropletItem());
    public static final DeferredHolder<Item, Item> RULE_OF_DESPAIR = item("rule_of_despair", () -> new RuleOfDespairItem());
    public static final DeferredHolder<Item, Item> RULE_OF_SOBBING = item("rule_of_sobbing", () -> new RuleOfSobbingItem());
    public static final DeferredHolder<Item, Item> AZURE_TILES = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_TILES);
    public static final DeferredHolder<Item, Item> SAPHYRA_DOOR_1 = item(MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_1.getId().getPath(), () -> new SaphyraDoor1DisplayItem((Block)MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_1.get(), new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)));
    public static final DeferredHolder<Item, Item> SAPHYRA_DOOR_2 = item(MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_2.getId().getPath(), () -> new SaphyraDoor2DisplayItem((Block)MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_2.get(), new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)));
    public static final DeferredHolder<Item, Item> SAPHYRA_DOOR_3 = item(MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_3.getId().getPath(), () -> new SaphyraDoor3DisplayItem((Block)MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_3.get(), new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)));
    public static final DeferredHolder<Item, Item> SAPHYRA_KEY = item("saphyra_key", () -> new SaphyraKeyItem());

    private static DeferredHolder<Item, Item> block(DeferredHolder<Block, Block> block) {
        return item(block.getId().getPath(), () -> new BlockItem((Block)block.get(), new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)));
    }

    private static DeferredHolder<Item, Item> doubleBlock(DeferredHolder<Block, Block> block) {
        return item(block.getId().getPath(), () -> new DoubleHighBlockItem((Block)block.get(), new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)));
    }

    private static <I extends Item> DeferredHolder<Item, I> item(String name, java.util.function.Supplier<I> factory) {
        return REGISTRY.register(name, id -> {
            RegistryIds.item = net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.ITEM, id);
            return factory.get();
        });
    }

}
