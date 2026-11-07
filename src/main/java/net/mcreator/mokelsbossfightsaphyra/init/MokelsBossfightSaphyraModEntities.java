/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraftforge.event.entity.EntityAttributeCreationEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 *  net.minecraftforge.registries.RegistryObject
 */
package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.entity.DisgustProjectileFallEntity;
import com.hp.saphyrareborn.entity.GhostSpiritEntity;
import com.hp.saphyrareborn.entity.GhostZombieEntity;
import com.hp.saphyrareborn.entity.InfestedWitchEntity;
import com.hp.saphyrareborn.entity.LinaraEntity;
import com.hp.saphyrareborn.entity.LinaraHallowedTower2Entity;
import com.hp.saphyrareborn.entity.LinaraHallowedTowerEntity;
import com.hp.saphyrareborn.entity.MarleneHumanFormDialog2Entity;
import com.hp.saphyrareborn.entity.MarleneHumanFormEntity;
import com.hp.saphyrareborn.entity.MirrorBladeProjectileEntity;
import com.hp.saphyrareborn.entity.MoonbeamEntity;
import com.hp.saphyrareborn.entity.MothbossEntity;
import com.hp.saphyrareborn.entity.NamelessEntity;
import com.hp.saphyrareborn.entity.NamelessInvisibleProjectileEntity;
import com.hp.saphyrareborn.entity.NamelessProjectileFallEntity;
import com.hp.saphyrareborn.entity.NamelessSpawnEntity;
import com.hp.saphyrareborn.entity.PuppetOfYivaNemorEntity;
import com.hp.saphyrareborn.entity.PutridRoseEntity;
import com.hp.saphyrareborn.entity.RotProjectileEntity;
import com.hp.saphyrareborn.entity.SaphyraDialog1Entity;
import com.hp.saphyrareborn.entity.SaphyraDialog2Entity;
import com.hp.saphyrareborn.entity.SaphyraDialog3Entity;
import com.hp.saphyrareborn.entity.SaphyraDialog4Entity;
import com.hp.saphyrareborn.entity.SaphyraSpawnEntity;
import com.hp.saphyrareborn.entity.ScorchedEntity;
import com.hp.saphyrareborn.entity.WitchBossProjectileEntity;
import com.hp.saphyrareborn.entity.WitchBossProjectilefallEntity;
import com.hp.saphyrareborn.entity.WitchbossEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class MokelsBossfightSaphyraModEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create((IForgeRegistry)ForgeRegistries.ENTITY_TYPES, (String)"mokels_bossfight_saphyra");
    public static final RegistryObject<EntityType<WitchbossEntity>> WITCHBOSS = MokelsBossfightSaphyraModEntities.register("witchboss", EntityType.Builder.m_20704_(WitchbossEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(WitchbossEntity::new).m_20699_(0.6f, 1.8f));
    public static final RegistryObject<EntityType<MoonbeamEntity>> MOONBEAM = MokelsBossfightSaphyraModEntities.register("moonbeam", EntityType.Builder.m_20704_(MoonbeamEntity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(MoonbeamEntity::new).m_20719_().m_20699_(0.5f, 0.5f));
    public static final RegistryObject<EntityType<WitchBossProjectileEntity>> WITCH_BOSS_PROJECTILE = MokelsBossfightSaphyraModEntities.register("witch_boss_projectile", EntityType.Builder.m_20704_(WitchBossProjectileEntity::new, (MobCategory)MobCategory.MISC).setCustomClientFactory(WitchBossProjectileEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).m_20699_(0.5f, 0.5f));
    public static final RegistryObject<EntityType<WitchBossProjectilefallEntity>> WITCH_BOSS_PROJECTILEFALL = MokelsBossfightSaphyraModEntities.register("witch_boss_projectilefall", EntityType.Builder.m_20704_(WitchBossProjectilefallEntity::new, (MobCategory)MobCategory.MISC).setCustomClientFactory(WitchBossProjectilefallEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).m_20699_(0.5f, 0.5f));
    public static final RegistryObject<EntityType<GhostSpiritEntity>> GHOST_SPIRIT = MokelsBossfightSaphyraModEntities.register("ghost_spirit", EntityType.Builder.m_20704_(GhostSpiritEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(GhostSpiritEntity::new).m_20699_(0.8f, 1.0f));
    public static final RegistryObject<EntityType<MothbossEntity>> MOTHBOSS = MokelsBossfightSaphyraModEntities.register("mothboss", EntityType.Builder.m_20704_(MothbossEntity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(MothbossEntity::new).m_20699_(0.9f, 3.0f));
    public static final RegistryObject<EntityType<PutridRoseEntity>> PUTRID_ROSE = MokelsBossfightSaphyraModEntities.register("putrid_rose", EntityType.Builder.m_20704_(PutridRoseEntity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(PutridRoseEntity::new).m_20699_(1.9f, 3.9f));
    public static final RegistryObject<EntityType<GhostZombieEntity>> GHOST_ZOMBIE = MokelsBossfightSaphyraModEntities.register("ghost_zombie", EntityType.Builder.m_20704_(GhostZombieEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(GhostZombieEntity::new).m_20719_().m_20699_(0.6f, 1.8f));
    public static final RegistryObject<EntityType<NamelessEntity>> NAMELESS = MokelsBossfightSaphyraModEntities.register("nameless", EntityType.Builder.m_20704_(NamelessEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(NamelessEntity::new).m_20699_(0.6f, 1.8f));
    public static final RegistryObject<EntityType<NamelessProjectileFallEntity>> NAMELESS_PROJECTILE_FALL = MokelsBossfightSaphyraModEntities.register("nameless_projectile_fall", EntityType.Builder.m_20704_(NamelessProjectileFallEntity::new, (MobCategory)MobCategory.MISC).setCustomClientFactory(NamelessProjectileFallEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).m_20699_(0.5f, 0.5f));
    public static final RegistryObject<EntityType<NamelessInvisibleProjectileEntity>> NAMELESS_INVISIBLE_PROJECTILE = MokelsBossfightSaphyraModEntities.register("nameless_invisible_projectile", EntityType.Builder.m_20704_(NamelessInvisibleProjectileEntity::new, (MobCategory)MobCategory.MISC).setCustomClientFactory(NamelessInvisibleProjectileEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).m_20699_(0.5f, 0.5f));
    public static final RegistryObject<EntityType<DisgustProjectileFallEntity>> DISGUST_PROJECTILE_FALL = MokelsBossfightSaphyraModEntities.register("disgust_projectile_fall", EntityType.Builder.m_20704_(DisgustProjectileFallEntity::new, (MobCategory)MobCategory.MISC).setCustomClientFactory(DisgustProjectileFallEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).m_20699_(0.5f, 0.5f));
    public static final RegistryObject<EntityType<LinaraEntity>> LINARA = MokelsBossfightSaphyraModEntities.register("linara", EntityType.Builder.m_20704_(LinaraEntity::new, (MobCategory)MobCategory.AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(LinaraEntity::new).m_20719_().m_20699_(0.8f, 1.2f));
    public static final RegistryObject<EntityType<PuppetOfYivaNemorEntity>> PUPPET_OF_YIVA_NEMOR = MokelsBossfightSaphyraModEntities.register("puppet_of_yiva_nemor", EntityType.Builder.m_20704_(PuppetOfYivaNemorEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(PuppetOfYivaNemorEntity::new).m_20699_(0.6f, 2.3f));
    public static final RegistryObject<EntityType<SaphyraDialog1Entity>> SAPHYRA_DIALOG_1 = MokelsBossfightSaphyraModEntities.register("saphyra_dialog_1", EntityType.Builder.m_20704_(SaphyraDialog1Entity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(SaphyraDialog1Entity::new).m_20719_().m_20699_(0.9f, 2.0f));
    public static final RegistryObject<EntityType<SaphyraDialog2Entity>> SAPHYRA_DIALOG_2 = MokelsBossfightSaphyraModEntities.register("saphyra_dialog_2", EntityType.Builder.m_20704_(SaphyraDialog2Entity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(SaphyraDialog2Entity::new).m_20719_().m_20699_(0.9f, 2.0f));
    public static final RegistryObject<EntityType<MirrorBladeProjectileEntity>> MIRROR_BLADE_PROJECTILE = MokelsBossfightSaphyraModEntities.register("mirror_blade_projectile", EntityType.Builder.m_20704_(MirrorBladeProjectileEntity::new, (MobCategory)MobCategory.MISC).setCustomClientFactory(MirrorBladeProjectileEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).m_20699_(0.5f, 0.5f));
    public static final RegistryObject<EntityType<RotProjectileEntity>> ROT_PROJECTILE = MokelsBossfightSaphyraModEntities.register("rot_projectile", EntityType.Builder.m_20704_(RotProjectileEntity::new, (MobCategory)MobCategory.MISC).setCustomClientFactory(RotProjectileEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).m_20699_(0.5f, 0.5f));
    public static final RegistryObject<EntityType<NamelessSpawnEntity>> NAMELESS_SPAWN = MokelsBossfightSaphyraModEntities.register("nameless_spawn", EntityType.Builder.m_20704_(NamelessSpawnEntity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(NamelessSpawnEntity::new).m_20719_().m_20699_(1.2f, 1.2f));
    public static final RegistryObject<EntityType<MarleneHumanFormEntity>> MARLENE_HUMAN_FORM = MokelsBossfightSaphyraModEntities.register("marlene_human_form", EntityType.Builder.m_20704_(MarleneHumanFormEntity::new, (MobCategory)MobCategory.AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(MarleneHumanFormEntity::new).m_20719_().m_20699_(0.6f, 1.9f));
    public static final RegistryObject<EntityType<SaphyraDialog3Entity>> SAPHYRA_DIALOG_3 = MokelsBossfightSaphyraModEntities.register("saphyra_dialog_3", EntityType.Builder.m_20704_(SaphyraDialog3Entity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(SaphyraDialog3Entity::new).m_20719_().m_20699_(0.9f, 2.0f));
    public static final RegistryObject<EntityType<MarleneHumanFormDialog2Entity>> MARLENE_HUMAN_FORM_DIALOG_2 = MokelsBossfightSaphyraModEntities.register("marlene_human_form_dialog_2", EntityType.Builder.m_20704_(MarleneHumanFormDialog2Entity::new, (MobCategory)MobCategory.AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(MarleneHumanFormDialog2Entity::new).m_20719_().m_20699_(0.6f, 1.9f));
    public static final RegistryObject<EntityType<LinaraHallowedTowerEntity>> LINARA_HALLOWED_TOWER = MokelsBossfightSaphyraModEntities.register("linara_hallowed_tower", EntityType.Builder.m_20704_(LinaraHallowedTowerEntity::new, (MobCategory)MobCategory.AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(LinaraHallowedTowerEntity::new).m_20719_().m_20699_(0.8f, 1.2f));
    public static final RegistryObject<EntityType<LinaraHallowedTower2Entity>> LINARA_HALLOWED_TOWER_2 = MokelsBossfightSaphyraModEntities.register("linara_hallowed_tower_2", EntityType.Builder.m_20704_(LinaraHallowedTower2Entity::new, (MobCategory)MobCategory.AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(LinaraHallowedTower2Entity::new).m_20719_().m_20699_(0.8f, 1.2f));
    public static final RegistryObject<EntityType<ScorchedEntity>> SCORCHED = MokelsBossfightSaphyraModEntities.register("scorched", EntityType.Builder.m_20704_(ScorchedEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ScorchedEntity::new).m_20699_(0.6f, 1.8f));
    public static final RegistryObject<EntityType<InfestedWitchEntity>> INFESTED_WITCH = MokelsBossfightSaphyraModEntities.register("infested_witch", EntityType.Builder.m_20704_(InfestedWitchEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(InfestedWitchEntity::new).m_20699_(1.0f, 1.4f));
    public static final RegistryObject<EntityType<SaphyraSpawnEntity>> SAPHYRA_SPAWN = MokelsBossfightSaphyraModEntities.register("saphyra_spawn", EntityType.Builder.m_20704_(SaphyraSpawnEntity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(SaphyraSpawnEntity::new).m_20719_().m_20699_(0.5f, 0.5f));
    public static final RegistryObject<EntityType<SaphyraDialog4Entity>> SAPHYRA_DIALOG_4 = MokelsBossfightSaphyraModEntities.register("saphyra_dialog_4", EntityType.Builder.m_20704_(SaphyraDialog4Entity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(SaphyraDialog4Entity::new).m_20719_().m_20699_(0.9f, 2.0f));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
        return REGISTRY.register(registryname, () -> entityTypeBuilder.m_20712_(registryname));
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            WitchbossEntity.init();
            MoonbeamEntity.init();
            GhostSpiritEntity.init();
            MothbossEntity.init();
            PutridRoseEntity.init();
            GhostZombieEntity.init();
            NamelessEntity.init();
            LinaraEntity.init();
            PuppetOfYivaNemorEntity.init();
            SaphyraDialog1Entity.init();
            SaphyraDialog2Entity.init();
            NamelessSpawnEntity.init();
            MarleneHumanFormEntity.init();
            SaphyraDialog3Entity.init();
            MarleneHumanFormDialog2Entity.init();
            LinaraHallowedTowerEntity.init();
            LinaraHallowedTower2Entity.init();
            ScorchedEntity.init();
            InfestedWitchEntity.init();
            SaphyraSpawnEntity.init();
            SaphyraDialog4Entity.init();
        });
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put((EntityType)WITCHBOSS.get(), WitchbossEntity.createAttributes().m_22265_());
        event.put((EntityType)MOONBEAM.get(), MoonbeamEntity.createAttributes().m_22265_());
        event.put((EntityType)GHOST_SPIRIT.get(), GhostSpiritEntity.createAttributes().m_22265_());
        event.put((EntityType)MOTHBOSS.get(), MothbossEntity.createAttributes().m_22265_());
        event.put((EntityType)PUTRID_ROSE.get(), PutridRoseEntity.createAttributes().m_22265_());
        event.put((EntityType)GHOST_ZOMBIE.get(), GhostZombieEntity.createAttributes().m_22265_());
        event.put((EntityType)NAMELESS.get(), NamelessEntity.createAttributes().m_22265_());
        event.put((EntityType)LINARA.get(), LinaraEntity.createAttributes().m_22265_());
        event.put((EntityType)PUPPET_OF_YIVA_NEMOR.get(), PuppetOfYivaNemorEntity.createAttributes().m_22265_());
        event.put((EntityType)SAPHYRA_DIALOG_1.get(), SaphyraDialog1Entity.createAttributes().m_22265_());
        event.put((EntityType)SAPHYRA_DIALOG_2.get(), SaphyraDialog2Entity.createAttributes().m_22265_());
        event.put((EntityType)NAMELESS_SPAWN.get(), NamelessSpawnEntity.createAttributes().m_22265_());
        event.put((EntityType)MARLENE_HUMAN_FORM.get(), MarleneHumanFormEntity.createAttributes().m_22265_());
        event.put((EntityType)SAPHYRA_DIALOG_3.get(), SaphyraDialog3Entity.createAttributes().m_22265_());
        event.put((EntityType)MARLENE_HUMAN_FORM_DIALOG_2.get(), MarleneHumanFormDialog2Entity.createAttributes().m_22265_());
        event.put((EntityType)LINARA_HALLOWED_TOWER.get(), LinaraHallowedTowerEntity.createAttributes().m_22265_());
        event.put((EntityType)LINARA_HALLOWED_TOWER_2.get(), LinaraHallowedTower2Entity.createAttributes().m_22265_());
        event.put((EntityType)SCORCHED.get(), ScorchedEntity.createAttributes().m_22265_());
        event.put((EntityType)INFESTED_WITCH.get(), InfestedWitchEntity.createAttributes().m_22265_());
        event.put((EntityType)SAPHYRA_SPAWN.get(), SaphyraSpawnEntity.createAttributes().m_22265_());
        event.put((EntityType)SAPHYRA_DIALOG_4.get(), SaphyraDialog4Entity.createAttributes().m_22265_());
    }
}


