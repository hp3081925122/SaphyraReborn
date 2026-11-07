/* Reconstructed registration source. */
package com.hp.saphyrareborn.init;
import com.hp.saphyrareborn.item.*;
import com.hp.saphyrareborn.block.display.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.*;
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
    public static final DeferredHolder<Item, SaphyrasCapeItem> SAPHYRAS_CAPE_CHESTPLATE = REGISTRY.register("saphyras_cape_chestplate", () -> new SaphyrasCapeItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
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
    public static final DeferredHolder<Item, Item> SAPHYRA_DIALOG_1_SPAWN_EGG = REGISTRY.register("saphyra_dialog_1_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.SAPHYRA_DIALOG_1, 0, 0, new Item.Properties()));
    public static final DeferredHolder<Item, Item> INFESTED_WITCH_SPAWN_EGG = REGISTRY.register("infested_witch_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.INFESTED_WITCH, 0, 0, new Item.Properties()));
    public static final DeferredHolder<Item, Item> MARLENE_HUMAN_FORM_SPAWN_EGG = REGISTRY.register("marlene_human_form_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.MARLENE_HUMAN_FORM, 0, 0, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SCORCHED_SPAWN_EGG = REGISTRY.register("scorched_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.SCORCHED, 0, 0, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NAMELESS_SPAWN_SPAWN_EGG = REGISTRY.register("nameless_spawn_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.NAMELESS_SPAWN, 0, 0, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SAPHYRA_SPAWN_SPAWN_EGG = REGISTRY.register("saphyra_spawn_spawn_egg", () -> new DeferredSpawnEggItem(MokelsBossfightSaphyraModEntities.SAPHYRA_SPAWN, 0, 0, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BROKEN_SIGIL_OF_THE_RED_SUN = REGISTRY.register("broken_sigil_of_the_red_sun", () -> new BrokenSigilOfTheRedSunItem());
    public static final DeferredHolder<Item, Item> BROKEN_SIGIL_OF_THE_RED_SUN_2 = REGISTRY.register("broken_sigil_of_the_red_sun_2", () -> new BrokenSigilOfTheRedSun2Item());
    public static final DeferredHolder<Item, Item> HALLOWED_KEY_1 = REGISTRY.register("hallowed_key_1", () -> new HallowedKey1Item());
    public static final DeferredHolder<Item, Item> HALLOWED_KEY_2 = REGISTRY.register("hallowed_key_2", () -> new HallowedKey2Item());
    public static final DeferredHolder<Item, Item> HALLOWED_KEY_3 = REGISTRY.register("hallowed_key_3", () -> new HallowedKey3Item());
    public static final DeferredHolder<Item, Item> INVITATION_TO_THE_HALLOWED_TOWER = REGISTRY.register("invitation_to_the_hallowed_tower", () -> new InvitationToTheHallowedTowerItem());
    public static final DeferredHolder<Item, Item> KATANA_HEAVY = REGISTRY.register("katana_heavy", () -> new KatanaHeavyItem());
    public static final DeferredHolder<Item, Item> KATANA_LIGHT = REGISTRY.register("katana_light", () -> new KatanaLightItem());
    public static final DeferredHolder<Item, Item> KATANA_POOL_OF_ROT = REGISTRY.register("katana_pool_of_rot", () -> new KatanaPoolOfRotItem());
    public static final DeferredHolder<Item, Item> KEY_1 = REGISTRY.register("key_1", () -> new Key1Item());
    public static final DeferredHolder<Item, Item> KEY_2 = REGISTRY.register("key_2", () -> new Key2Item());
    public static final DeferredHolder<Item, Item> KEY_3 = REGISTRY.register("key_3", () -> new Key3Item());
    public static final DeferredHolder<Item, Item> KEY_4 = REGISTRY.register("key_4", () -> new Key4Item());
    public static final DeferredHolder<Item, Item> LOURING_CLAYMORE = REGISTRY.register("louring_claymore", () -> new LouringClaymoreItem());
    public static final DeferredHolder<Item, Item> MANIC_EYE = REGISTRY.register("manic_eye", () -> new ManicEyeItem());
    public static final DeferredHolder<Item, Item> MIRROR_BLADE = REGISTRY.register("mirror_blade", () -> new MirrorBladeItem());
    public static final DeferredHolder<Item, Item> NAMELESS_GATEKEEPER_REMEMBRANCE = REGISTRY.register("nameless_gatekeeper_remembrance", () -> new NamelessGatekeeperRemembranceItem());
    public static final DeferredHolder<Item, Item> PUPPET_OF_YIVA_REMEMBRANCE = REGISTRY.register("puppet_of_yiva_remembrance", () -> new PuppetOfYivaRemembranceItem());
    public static final DeferredHolder<Item, Item> RULE_OF_DESPAIR = REGISTRY.register("rule_of_despair", () -> new RuleOfDespairItem());
    public static final DeferredHolder<Item, Item> RULE_OF_EXUDATION = REGISTRY.register("rule_of_exudation", () -> new RuleOfExudationItem());
    public static final DeferredHolder<Item, Item> RULE_OF_SOBBING = REGISTRY.register("rule_of_sobbing", () -> new RuleOfSobbingItem());
    public static final DeferredHolder<Item, Item> SAPHYRA_KEY = REGISTRY.register("saphyra_key", () -> new SaphyraKeyItem());
    public static final DeferredHolder<Item, Item> SAPHYRA_REMEMBRANCE = REGISTRY.register("saphyra_remembrance", () -> new SaphyraRemembranceItem());
    public static final DeferredHolder<Item, Item> SCROLL = REGISTRY.register("scroll", () -> new ScrollItem());
    public static final DeferredHolder<Item, Item> SIGIL_OF_THE_RED_SUN_STAGE_2 = REGISTRY.register("sigil_of_the_red_sun_stage_2", () -> new SigilOfTheRedSunStage2Item());
    public static final DeferredHolder<Item, Item> SIGIL_OF_THE_RED_SUN_STAGE_3 = REGISTRY.register("sigil_of_the_red_sun_stage_3", () -> new SigilOfTheRedSunStage3Item());
    public static final DeferredHolder<Item, Item> SINISTER_BAG = REGISTRY.register("sinister_bag", () -> new SinisterBagItem());
    public static final DeferredHolder<Item, Item> SUNVEIL = REGISTRY.register("sunveil", () -> new SunveilItem());
    public static final DeferredHolder<Item, Item> SWORD_TEAR_AND_DROPLET = REGISTRY.register("sword_tear_and_droplet", () -> new SwordTearAndDropletItem());
    public static final DeferredHolder<Item, Item> THE_FIRST_GOD = REGISTRY.register("the_first_god", () -> new TheFirstGodItem());
    private static DeferredHolder<Item, Item> block(DeferredHolder<Block, Block> block) {
        return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
}















