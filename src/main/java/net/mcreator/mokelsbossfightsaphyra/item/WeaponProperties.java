package com.hp.saphyrareborn.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

/**
 * Restores the 1.20.1 weapon stats lost in the port. Original MCreator swords used
 * SwordItem(tier, 3, speed, fireResistant()) with tier uses = 0 (= unbreakable in 1.20).
 * 1.21 no longer derives attributes from the constructor, so they are attached here.
 */
public final class WeaponProperties {
    private static final int BASE_DAMAGE = 3;

    private WeaponProperties() {
    }

    /** Same numbers as the original: tooltip damage = 1 + 3 + tierBonus, speed = 4 + attackSpeed. */
    public static Item.Properties sword(float tierBonus, float attackSpeed) {
        ItemAttributeModifiers modifiers = ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, BASE_DAMAGE + tierBonus, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
        return new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item)
                .attributes(modifiers)
                .component(DataComponents.UNBREAKABLE, net.minecraft.util.Unit.INSTANCE)
                .fireResistant();
    }
}
