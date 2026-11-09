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
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;

public class MokelsBossfightSaphyraModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(Registries.ITEM, "saphyra_reborn");
    public static final DeferredHolder<Item, Item> WITCHBOSS_SPAWN_EGG = REGISTRY.register("witchboss_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.WITCHBOSS, -10263709, -6684710, new Item.Properties()));
    public static final DeferredHolder<Item, Item> TOTEM_OF_ROT_AND_DECAY = REGISTRY.register("totem_of_rot_and_decay", () -> new TotemOfRotAndDecayItem());
    public static final DeferredHolder<Item, Item> BOTTOMLESS_BAG = REGISTRY.register("bottomless_bag", () -> new BottomlessBagItem());
    public static final DeferredHolder<Item, Item> WITCH_STAFF = REGISTRY.register("witch_staff", () -> new WitchStaffItem());
    public static final DeferredHolder<Item, Item> FLASK_OF_HEALING = REGISTRY.register("flask_of_healing", () -> new FlaskOfHealingItem());
    public static final DeferredHolder<Item, Item> FLASK_OF_HEALINGLVLTWO = REGISTRY.register("flask_of_healinglvltwo", () -> new FlaskOfHealinglvltwoItem());
    public static final DeferredHolder<Item, Item> SPRIRITUAL_RUNE = REGISTRY.register("spriritual_rune", () -> new SpriritualRuneItem());
    public static final DeferredHolder<Item, Item> SAPHYRAS_HAT_HELMET = REGISTRY.register("saphyras_hat_helmet", () -> new SaphyrasHatItem.Helmet());
    public static final DeferredHolder<Item, Item> SPIRIT_RUNE_SWORD = REGISTRY.register("spirit_rune_sword", () -> new SpiritRuneSwordItem());
    public static final DeferredHolder<Item, Item> BELL_SPIRIT = REGISTRY.register("bell_spirit", () -> new BellSpiritItem());
    public static final DeferredHolder<Item, Item> MOTHBOSS_SPAWN_EGG = REGISTRY.register("mothboss_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.MOTHBOSS, -5855578, -12527732, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PUPPET_OF_YIVA_NEMOR_SPAWN_EGG = REGISTRY.register("puppet_of_yiva_nemor_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.PUPPET_OF_YIVA_NEMOR, 0xB89478, 0x7A1010, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RULE_OF_PURIFICATION = REGISTRY.register("rule_of_purification", () -> new RuleOfPurificationItem());
    public static final DeferredHolder<Item, Item> EMPTY_RULE = REGISTRY.register("empty_rule", () -> new EmptyRuleItem());
    public static final DeferredHolder<Item, Item> RULE_OF_VISION = REGISTRY.register("rule_of_vision", () -> new RuleOfVisionItem());
    public static final DeferredHolder<Item, Item> NAMELESS_SPAWN_EGG = REGISTRY.register("nameless_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.NAMELESS, -11711155, -6222065, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BOSS_DOOR2 = REGISTRY.register(MokelsBossfightSaphyraModBlocks.BOSS_DOOR2.getId().getPath(), () -> new BossDoorDisplayItem((Block)MokelsBossfightSaphyraModBlocks.BOSS_DOOR2.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> LYIOR_STATUE = REGISTRY.register(MokelsBossfightSaphyraModBlocks.LYIOR_STATUE.getId().getPath(), () -> new LyiorStatueDisplayItem((Block)MokelsBossfightSaphyraModBlocks.LYIOR_STATUE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> LYIOR_STATUE_AKTIVATED = REGISTRY.register(MokelsBossfightSaphyraModBlocks.LYIOR_STATUE_AKTIVATED.getId().getPath(), () -> new LyiorStatueAktivatedDisplayItem((Block)MokelsBossfightSaphyraModBlocks.LYIOR_STATUE_AKTIVATED.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> AZURE_BRICKS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_BRICKS);
    public static final DeferredHolder<Item, Item> AZURE_SLAB = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_SLAB);
    public static final DeferredHolder<Item, Item> AZURE_STAIRS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_STAIRS);
    public static final DeferredHolder<Item, Item> AZURE_WALL = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_WALL);
    public static final DeferredHolder<Item, Item> AZURE_BRICKS_ANIMATED = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_BRICKS_ANIMATED);
    public static final DeferredHolder<Item, Item> BOSS_DOOR_1 = REGISTRY.register(MokelsBossfightSaphyraModBlocks.BOSS_DOOR_1.getId().getPath(), () -> new BossDoor1DisplayItem((Block)MokelsBossfightSaphyraModBlocks.BOSS_DOOR_1.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> WARRIOR_KEY = REGISTRY.register("warrior_key", () -> new WarriorKeyItem());
    public static final DeferredHolder<Item, Item> BOSS_DOOR_3 = REGISTRY.register(MokelsBossfightSaphyraModBlocks.BOSS_DOOR_3.getId().getPath(), () -> new BossDoor3DisplayItem((Block)MokelsBossfightSaphyraModBlocks.BOSS_DOOR_3.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> ADD_CURSE = REGISTRY.register("add_curse", () -> new AddCurseItem());
    public static final DeferredHolder<Item, Item> SHATTERED_BLOOD_VAIL = REGISTRY.register("shattered_blood_vail", () -> new ShatteredBloodVailItem());
    public static final DeferredHolder<Item, Item> RULE_OF_HATERED = REGISTRY.register("rule_of_hatered", () -> new RuleOfHateredItem());
    public static final DeferredHolder<Item, Item> GLUTTONOUS_FLASK = REGISTRY.register("gluttonous_flask", () -> new GluttonousFlaskItem());
    public static final DeferredHolder<Item, Item> SPACE_BLOCK = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.SPACE_BLOCK);
    public static final DeferredHolder<Item, SaphyrasCapeItem> SAPHYRAS_CAPE_CHESTPLATE = REGISTRY.register("saphyras_cape_chestplate", () -> new SaphyrasCapeItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(16 * 25).fireResistant()));
    public static final DeferredHolder<Item, Item> ODDMENT_STAGE_2 = REGISTRY.register(MokelsBossfightSaphyraModBlocks.ODDMENT_STAGE_2.getId().getPath(), () -> new OddmentStage2DisplayItem((Block)MokelsBossfightSaphyraModBlocks.ODDMENT_STAGE_2.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RULE_OF_HUNGER = REGISTRY.register("rule_of_hunger", () -> new RuleOfHungerItem());
    public static final DeferredHolder<Item, Item> RULE_OF_THE_P_LAGUED = REGISTRY.register("rule_of_the_p_lagued", () -> new RuleOfThePLaguedItem());
    public static final DeferredHolder<Item, Item> SIGIL_OF_THE_RED_SUN = REGISTRY.register("sigil_of_the_red_sun", () -> new SigilOfTheRedSunItem());
    public static final DeferredHolder<Item, Item> VOCAL_CORDS = REGISTRY.register("vocal_cords", () -> new VocalCordsItem());
    public static final DeferredHolder<Item, Item> LINARA_SPAWN_EGG = REGISTRY.register("linara_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.LINARA, -10914729, -11126220, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GRASS_VISAGE_HELMET = REGISTRY.register("grass_visage_helmet", () -> new GrassVisageItem.Helmet());
    public static final DeferredHolder<Item, Item> SWORD_OF_THE_NAMELESS_GATEKEEPER = REGISTRY.register("sword_of_the_nameless_gatekeeper", () -> new SwordOfTheNamelessGatekeeperItem());
    public static final DeferredHolder<Item, Item> BLOOD_WALL = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.BLOOD_WALL);
    public static final DeferredHolder<Item, Item> BLOOD_BRICKS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.BLOOD_BRICKS);
    public static final DeferredHolder<Item, Item> POLISHED_BLOOD_BRICKS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.POLISHED_BLOOD_BRICKS);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_BLOOD_BRICKS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_BLOOD_BRICKS);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_BLOOD_WALL = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_BLOOD_WALL);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_BLOOD_STAIRS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_BLOOD_STAIRS);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_BLOOD_SLAB = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_BLOOD_SLAB);
    public static final DeferredHolder<Item, Item> BLOOD_BRICKS_SLAB = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.BLOOD_BRICKS_SLAB);
    public static final DeferredHolder<Item, Item> BLOOD_BRICKS_STAIRS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.BLOOD_BRICKS_STAIRS);
    public static final DeferredHolder<Item, Item> PUPPET_OF_YIVA_REMEMBRANCE = REGISTRY.register("puppet_of_yiva_remembrance", () -> new PuppetOfYivaRemembranceItem());
    public static final DeferredHolder<Item, Item> SAPHYRA_REMEMBRANCE = REGISTRY.register("saphyra_remembrance", () -> new SaphyraRemembranceItem());
    public static final DeferredHolder<Item, Item> BLOOD_LANTERN = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.BLOOD_LANTERN);
    public static final DeferredHolder<Item, Item> RULE_OF_EXUDATION = REGISTRY.register("rule_of_exudation", () -> new RuleOfExudationItem());
    public static final DeferredHolder<Item, Item> WHITE_FLAME = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.WHITE_FLAME);
    public static final DeferredHolder<Item, Item> BLOOD_DOOR_1 = MokelsBossfightSaphyraModItems.doubleBlock(MokelsBossfightSaphyraModBlocks.BLOOD_DOOR_1);
    public static final DeferredHolder<Item, Item> KEY_1 = REGISTRY.register("key_1", () -> new Key1Item());
    public static final DeferredHolder<Item, Item> KEY_2 = REGISTRY.register("key_2", () -> new Key2Item());
    public static final DeferredHolder<Item, Item> KEY_3 = REGISTRY.register("key_3", () -> new Key3Item());
    public static final DeferredHolder<Item, Item> KEY_4 = REGISTRY.register("key_4", () -> new Key4Item());
    public static final DeferredHolder<Item, Item> NAMELESS_GATEKEEPER_REMEMBRANCE = REGISTRY.register("nameless_gatekeeper_remembrance", () -> new NamelessGatekeeperRemembranceItem());
    public static final DeferredHolder<Item, Item> POLISHED_AZURE_BRICKS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.POLISHED_AZURE_BRICKS);
    public static final DeferredHolder<Item, Item> AZURE_LANTERN = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_LANTERN);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_AZURE_BRICKS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_AZURE_BRICKS);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_AZURE_SLAB = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_AZURE_SLAB);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_AZURE_STAIRS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_AZURE_STAIRS);
    public static final DeferredHolder<Item, Item> DARK_POLISHED_AZURE_WALL = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.DARK_POLISHED_AZURE_WALL);
    public static final DeferredHolder<Item, Item> SAPHYRA_DIALOG_1_SPAWN_EGG = REGISTRY.register("saphyra_dialog_1_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.SAPHYRA_DIALOG_1, -1, -3342337, new Item.Properties()));
    public static final DeferredHolder<Item, Item> INVITATION_TO_THE_HALLOWED_TOWER = REGISTRY.register("invitation_to_the_hallowed_tower", () -> new InvitationToTheHallowedTowerItem());
    public static final DeferredHolder<Item, Item> LOURING_CLAYMORE = REGISTRY.register("louring_claymore", () -> new LouringClaymoreItem());
    public static final DeferredHolder<Item, Item> SUNVEIL = REGISTRY.register("sunveil", () -> new SunveilItem());
    public static final DeferredHolder<Item, Item> MIRROR_BLADE = REGISTRY.register("mirror_blade", () -> new MirrorBladeItem());
    public static final DeferredHolder<Item, Item> NAMELESS_SPAWN_SPAWN_EGG = REGISTRY.register("nameless_spawn_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.NAMELESS_SPAWN, -13421773, -3407872, new Item.Properties()));
    public static final DeferredHolder<Item, Item> MARLENE_HUMAN_FORM_SPAWN_EGG = REGISTRY.register("marlene_human_form_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.MARLENE_HUMAN_FORM, -15066598, -10473907, new Item.Properties()));
    public static final DeferredHolder<Item, Item> HALLOWED_KEY_1 = REGISTRY.register("hallowed_key_1", () -> new HallowedKey1Item());
    public static final DeferredHolder<Item, Item> HALLOWED_KEY_2 = REGISTRY.register("hallowed_key_2", () -> new HallowedKey2Item());
    public static final DeferredHolder<Item, Item> HALLOWED_KEY_3 = REGISTRY.register("hallowed_key_3", () -> new HallowedKey3Item());
    public static final DeferredHolder<Item, Item> LOCKED_FOG_CHAIN = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_CHAIN);
    public static final DeferredHolder<Item, Item> LOCKED_FOG_SHATTERED = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_SHATTERED);
    public static final DeferredHolder<Item, Item> LOCKED_FOG_WORSHIP = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_WORSHIP);
    public static final DeferredHolder<Item, Item> LOCKED_FOG_KNIFE = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_KNIFE);
    public static final DeferredHolder<Item, Item> LOCKED_FOG_STEPS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_STEPS);
    public static final DeferredHolder<Item, Item> LOCKED_FOG_LOVERS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS);
    public static final DeferredHolder<Item, Item> LOCKED_FOG_HATERED = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_HATERED);
    public static final DeferredHolder<Item, Item> SIGIL_OF_THE_RED_SUN_STAGE_2 = REGISTRY.register("sigil_of_the_red_sun_stage_2", () -> new SigilOfTheRedSunStage2Item());
    public static final DeferredHolder<Item, Item> SIGIL_OF_THE_RED_SUN_STAGE_3 = REGISTRY.register("sigil_of_the_red_sun_stage_3", () -> new SigilOfTheRedSunStage3Item());
    public static final DeferredHolder<Item, Item> THE_FIRST_GOD = REGISTRY.register("the_first_god", () -> new TheFirstGodItem());
    public static final DeferredHolder<Item, Item> SINISTER_BAG = REGISTRY.register("sinister_bag", () -> new SinisterBagItem());
    public static final DeferredHolder<Item, Item> BROKEN_SIGIL_OF_THE_RED_SUN = REGISTRY.register("broken_sigil_of_the_red_sun", () -> new BrokenSigilOfTheRedSunItem());
    public static final DeferredHolder<Item, Item> MANIC_EYE = REGISTRY.register("manic_eye", () -> new ManicEyeItem());
    public static final DeferredHolder<Item, Item> ODDMENT_HALLOWED_TOWER = REGISTRY.register(MokelsBossfightSaphyraModBlocks.ODDMENT_HALLOWED_TOWER.getId().getPath(), () -> new OddmentHallowedTowerDisplayItem((Block)MokelsBossfightSaphyraModBlocks.ODDMENT_HALLOWED_TOWER.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RED_GLASS = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.RED_GLASS);
    public static final DeferredHolder<Item, Item> BOSS_FOG = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.BOSS_FOG);
    public static final DeferredHolder<Item, Item> SCORCHED_SPAWN_EGG = REGISTRY.register("scorched_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.SCORCHED, -13228507, -12120819, new Item.Properties()));
    public static final DeferredHolder<Item, Item> INFESTED_WITCH_SPAWN_EGG = REGISTRY.register("infested_witch_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.INFESTED_WITCH, -9300972, -12120819, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BROKEN_SIGIL_OF_THE_RED_SUN_2 = REGISTRY.register("broken_sigil_of_the_red_sun_2", () -> new BrokenSigilOfTheRedSun2Item());
    public static final DeferredHolder<Item, Item> SAPHYRA_SPAWN_SPAWN_EGG = REGISTRY.register("saphyra_spawn_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.SAPHYRA_SPAWN, -13369345, -16711681, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SCROLL = REGISTRY.register("scroll", () -> new ScrollItem());
    public static final DeferredHolder<Item, Item> KATANA_HEAVY = REGISTRY.register("katana_heavy", () -> new KatanaHeavyItem());
    public static final DeferredHolder<Item, Item> KATANA_LIGHT = REGISTRY.register("katana_light", () -> new KatanaLightItem());
    public static final DeferredHolder<Item, Item> KATANA_POOL_OF_ROT = REGISTRY.register("katana_pool_of_rot", () -> new KatanaPoolOfRotItem());
    public static final DeferredHolder<Item, Item> SWORD_TEAR_AND_DROPLET = REGISTRY.register("sword_tear_and_droplet", () -> new SwordTearAndDropletItem());
    public static final DeferredHolder<Item, Item> RULE_OF_DESPAIR = REGISTRY.register("rule_of_despair", () -> new RuleOfDespairItem());
    public static final DeferredHolder<Item, Item> RULE_OF_SOBBING = REGISTRY.register("rule_of_sobbing", () -> new RuleOfSobbingItem());
    public static final DeferredHolder<Item, Item> AZURE_TILES = MokelsBossfightSaphyraModItems.block(MokelsBossfightSaphyraModBlocks.AZURE_TILES);
    public static final DeferredHolder<Item, Item> SAPHYRA_DOOR_1 = REGISTRY.register(MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_1.getId().getPath(), () -> new SaphyraDoor1DisplayItem((Block)MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_1.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> SAPHYRA_DOOR_2 = REGISTRY.register(MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_2.getId().getPath(), () -> new SaphyraDoor2DisplayItem((Block)MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_2.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> SAPHYRA_DOOR_3 = REGISTRY.register(MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_3.getId().getPath(), () -> new SaphyraDoor3DisplayItem((Block)MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_3.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> SAPHYRA_KEY = REGISTRY.register("saphyra_key", () -> new SaphyraKeyItem());

    private static DeferredHolder<Item, Item> block(DeferredHolder<Block, Block> block) {
        return REGISTRY.register(block.getId().getPath(), () -> new BlockItem((Block)block.get(), new Item.Properties()));
    }

    private static DeferredHolder<Item, Item> doubleBlock(DeferredHolder<Block, Block> block) {
        return REGISTRY.register(block.getId().getPath(), () -> new DoubleHighBlockItem((Block)block.get(), new Item.Properties()));
    }
}





