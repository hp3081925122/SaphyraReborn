/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 *  net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.Mod$EventBusSubscriber
 *  net.neoforged.fml.common.Mod$EventBusSubscriber$Bus
 *  net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.NeoForgeRegistries
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
import com.hp.saphyrareborn.entity.LinaraClockTowerEntity;
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
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
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

@EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD)
public class MokelsBossfightSaphyraModEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, "saphyra_reborn");
    public static final DeferredHolder<EntityType<?>, EntityType<WitchbossEntity>> WITCHBOSS = MokelsBossfightSaphyraModEntities.register("witchboss", EntityType.Builder.of(WitchbossEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).sized(0.6f, 1.8f));
    public static final DeferredHolder<EntityType<?>, EntityType<MoonbeamEntity>> MOONBEAM = MokelsBossfightSaphyraModEntities.register("moonbeam", EntityType.Builder.of(MoonbeamEntity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(0.5f, 0.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<WitchBossProjectileEntity>> WITCH_BOSS_PROJECTILE = MokelsBossfightSaphyraModEntities.register("witch_boss_projectile", EntityType.Builder.<WitchBossProjectileEntity>of((type, level) -> new WitchBossProjectileEntity((EntityType<? extends WitchBossProjectileEntity>)(EntityType<?>)type, level), (MobCategory)MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<WitchBossProjectilefallEntity>> WITCH_BOSS_PROJECTILEFALL = MokelsBossfightSaphyraModEntities.register("witch_boss_projectilefall", EntityType.Builder.<WitchBossProjectilefallEntity>of((type, level) -> new WitchBossProjectilefallEntity((EntityType<? extends WitchBossProjectilefallEntity>)(EntityType<?>)type, level), (MobCategory)MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<GhostSpiritEntity>> GHOST_SPIRIT = MokelsBossfightSaphyraModEntities.register("ghost_spirit", EntityType.Builder.of(GhostSpiritEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).sized(0.8f, 1.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<MothbossEntity>> MOTHBOSS = MokelsBossfightSaphyraModEntities.register("mothboss", EntityType.Builder.of(MothbossEntity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).sized(0.9f, 3.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<PutridRoseEntity>> PUTRID_ROSE = MokelsBossfightSaphyraModEntities.register("putrid_rose", EntityType.Builder.of(PutridRoseEntity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).sized(1.9f, 3.9f));
    public static final DeferredHolder<EntityType<?>, EntityType<GhostZombieEntity>> GHOST_ZOMBIE = MokelsBossfightSaphyraModEntities.register("ghost_zombie", EntityType.Builder.of(GhostZombieEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(0.6f, 1.8f));
    public static final DeferredHolder<EntityType<?>, EntityType<NamelessEntity>> NAMELESS = MokelsBossfightSaphyraModEntities.register("nameless", EntityType.Builder.of(NamelessEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).sized(0.6f, 1.8f));
    public static final DeferredHolder<EntityType<?>, EntityType<NamelessProjectileFallEntity>> NAMELESS_PROJECTILE_FALL = MokelsBossfightSaphyraModEntities.register("nameless_projectile_fall", EntityType.Builder.<NamelessProjectileFallEntity>of((type, level) -> new NamelessProjectileFallEntity((EntityType<? extends NamelessProjectileFallEntity>)(EntityType<?>)type, level), (MobCategory)MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<NamelessInvisibleProjectileEntity>> NAMELESS_INVISIBLE_PROJECTILE = MokelsBossfightSaphyraModEntities.register("nameless_invisible_projectile", EntityType.Builder.<NamelessInvisibleProjectileEntity>of((type, level) -> new NamelessInvisibleProjectileEntity((EntityType<? extends NamelessInvisibleProjectileEntity>)(EntityType<?>)type, level), (MobCategory)MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<DisgustProjectileFallEntity>> DISGUST_PROJECTILE_FALL = MokelsBossfightSaphyraModEntities.register("disgust_projectile_fall", EntityType.Builder.<DisgustProjectileFallEntity>of((type, level) -> new DisgustProjectileFallEntity((EntityType<? extends DisgustProjectileFallEntity>)(EntityType<?>)type, level), (MobCategory)MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<LinaraEntity>> LINARA = MokelsBossfightSaphyraModEntities.register("linara", EntityType.Builder.of(LinaraEntity::new, (MobCategory)MobCategory.AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(0.8f, 1.2f));
    public static final DeferredHolder<EntityType<?>, EntityType<PuppetOfYivaNemorEntity>> PUPPET_OF_YIVA_NEMOR = MokelsBossfightSaphyraModEntities.register("puppet_of_yiva_nemor", EntityType.Builder.of(PuppetOfYivaNemorEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).sized(0.6f, 2.3f));
    public static final DeferredHolder<EntityType<?>, EntityType<SaphyraDialog1Entity>> SAPHYRA_DIALOG_1 = MokelsBossfightSaphyraModEntities.register("saphyra_dialog_1", EntityType.Builder.of(SaphyraDialog1Entity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(0.9f, 2.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<SaphyraDialog2Entity>> SAPHYRA_DIALOG_2 = MokelsBossfightSaphyraModEntities.register("saphyra_dialog_2", EntityType.Builder.of(SaphyraDialog2Entity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(0.9f, 2.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<MirrorBladeProjectileEntity>> MIRROR_BLADE_PROJECTILE = MokelsBossfightSaphyraModEntities.register("mirror_blade_projectile", EntityType.Builder.<MirrorBladeProjectileEntity>of((type, level) -> new MirrorBladeProjectileEntity((EntityType<? extends MirrorBladeProjectileEntity>)(EntityType<?>)type, level), (MobCategory)MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<RotProjectileEntity>> ROT_PROJECTILE = MokelsBossfightSaphyraModEntities.register("rot_projectile", EntityType.Builder.<RotProjectileEntity>of((type, level) -> new RotProjectileEntity((EntityType<? extends RotProjectileEntity>)(EntityType<?>)type, level), (MobCategory)MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<NamelessSpawnEntity>> NAMELESS_SPAWN = MokelsBossfightSaphyraModEntities.register("nameless_spawn", EntityType.Builder.of(NamelessSpawnEntity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(1.2f, 1.2f));
    public static final DeferredHolder<EntityType<?>, EntityType<MarleneHumanFormEntity>> MARLENE_HUMAN_FORM = MokelsBossfightSaphyraModEntities.register("marlene_human_form", EntityType.Builder.of(MarleneHumanFormEntity::new, (MobCategory)MobCategory.AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(0.6f, 1.9f));
    public static final DeferredHolder<EntityType<?>, EntityType<SaphyraDialog3Entity>> SAPHYRA_DIALOG_3 = MokelsBossfightSaphyraModEntities.register("saphyra_dialog_3", EntityType.Builder.of(SaphyraDialog3Entity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(0.9f, 2.0f));
    public static final DeferredHolder<EntityType<?>, EntityType<MarleneHumanFormDialog2Entity>> MARLENE_HUMAN_FORM_DIALOG_2 = MokelsBossfightSaphyraModEntities.register("marlene_human_form_dialog_2", EntityType.Builder.of(MarleneHumanFormDialog2Entity::new, (MobCategory)MobCategory.AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(0.6f, 1.9f));
    public static final DeferredHolder<EntityType<?>, EntityType<LinaraHallowedTowerEntity>> LINARA_HALLOWED_TOWER = MokelsBossfightSaphyraModEntities.register("linara_hallowed_tower", EntityType.Builder.of(LinaraHallowedTowerEntity::new, (MobCategory)MobCategory.AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(0.8f, 1.2f));
    public static final DeferredHolder<EntityType<?>, EntityType<LinaraHallowedTower2Entity>> LINARA_HALLOWED_TOWER_2 = MokelsBossfightSaphyraModEntities.register("linara_hallowed_tower_2", EntityType.Builder.of(LinaraHallowedTower2Entity::new, (MobCategory)MobCategory.AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(0.8f, 1.2f));
    public static final DeferredHolder<EntityType<?>, EntityType<LinaraClockTowerEntity>> LINARA_CLOCK_TOWER = MokelsBossfightSaphyraModEntities.register("linara_clock_tower", EntityType.Builder.of(LinaraClockTowerEntity::new, (MobCategory)MobCategory.AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).sized(0.8f, 1.2f));
    public static final DeferredHolder<EntityType<?>, EntityType<ScorchedEntity>> SCORCHED = MokelsBossfightSaphyraModEntities.register("scorched", EntityType.Builder.of(ScorchedEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).sized(0.6f, 1.8f));
    public static final DeferredHolder<EntityType<?>, EntityType<InfestedWitchEntity>> INFESTED_WITCH = MokelsBossfightSaphyraModEntities.register("infested_witch", EntityType.Builder.of(InfestedWitchEntity::new, (MobCategory)MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).sized(1.0f, 1.4f));
    public static final DeferredHolder<EntityType<?>, EntityType<SaphyraSpawnEntity>> SAPHYRA_SPAWN = MokelsBossfightSaphyraModEntities.register("saphyra_spawn", EntityType.Builder.of(SaphyraSpawnEntity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(0.5f, 0.5f));
    public static final DeferredHolder<EntityType<?>, EntityType<SaphyraDialog4Entity>> SAPHYRA_DIALOG_4 = MokelsBossfightSaphyraModEntities.register("saphyra_dialog_4", EntityType.Builder.of(SaphyraDialog4Entity::new, (MobCategory)MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).noSave().sized(0.9f, 2.0f));

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
        return REGISTRY.register(registryname, () -> entityTypeBuilder.build(registryname));
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
            LinaraClockTowerEntity.init();
            ScorchedEntity.init();
            InfestedWitchEntity.init();
            SaphyraSpawnEntity.init();
            SaphyraDialog4Entity.init();
        });
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put((EntityType)WITCHBOSS.get(), WitchbossEntity.createAttributes().build());
        event.put((EntityType)MOONBEAM.get(), MoonbeamEntity.createAttributes().build());
        event.put((EntityType)GHOST_SPIRIT.get(), GhostSpiritEntity.createAttributes().build());
        event.put((EntityType)MOTHBOSS.get(), MothbossEntity.createAttributes().build());
        event.put((EntityType)PUTRID_ROSE.get(), PutridRoseEntity.createAttributes().build());
        event.put((EntityType)GHOST_ZOMBIE.get(), GhostZombieEntity.createAttributes().build());
        event.put((EntityType)NAMELESS.get(), NamelessEntity.createAttributes().build());
        event.put((EntityType)LINARA.get(), LinaraEntity.createAttributes().build());
        event.put((EntityType)PUPPET_OF_YIVA_NEMOR.get(), PuppetOfYivaNemorEntity.createAttributes().build());
        event.put((EntityType)SAPHYRA_DIALOG_1.get(), SaphyraDialog1Entity.createAttributes().build());
        event.put((EntityType)SAPHYRA_DIALOG_2.get(), SaphyraDialog2Entity.createAttributes().build());
        event.put((EntityType)NAMELESS_SPAWN.get(), NamelessSpawnEntity.createAttributes().build());
        event.put((EntityType)MARLENE_HUMAN_FORM.get(), MarleneHumanFormEntity.createAttributes().build());
        event.put((EntityType)SAPHYRA_DIALOG_3.get(), SaphyraDialog3Entity.createAttributes().build());
        event.put((EntityType)MARLENE_HUMAN_FORM_DIALOG_2.get(), MarleneHumanFormDialog2Entity.createAttributes().build());
        event.put((EntityType)LINARA_HALLOWED_TOWER.get(), LinaraHallowedTowerEntity.createAttributes().build());
        event.put((EntityType)LINARA_HALLOWED_TOWER_2.get(), LinaraHallowedTower2Entity.createAttributes().build());
        event.put((EntityType)LINARA_CLOCK_TOWER.get(), LinaraClockTowerEntity.createAttributes().build());
        event.put((EntityType)SCORCHED.get(), ScorchedEntity.createAttributes().build());
        event.put((EntityType)INFESTED_WITCH.get(), InfestedWitchEntity.createAttributes().build());
        event.put((EntityType)SAPHYRA_SPAWN.get(), SaphyraSpawnEntity.createAttributes().build());
        event.put((EntityType)SAPHYRA_DIALOG_4.get(), SaphyraDialog4Entity.createAttributes().build());
    }
}





