package com.hp.saphyrareborn.weapon;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public enum WeaponStyle {
    KATANA_HEAVY(MokelsBossfightSaphyraModItems.KATANA_HEAVY, "katana_heavy", 0xD8F0F0, 0xA8C0D8, 2.4f, true),
    KATANA_LIGHT(MokelsBossfightSaphyraModItems.KATANA_LIGHT, "katana_light", 0xE6F6FF, 0xB8D4EC, 2.0f, false),
    KATANA_POOL_OF_ROT(MokelsBossfightSaphyraModItems.KATANA_POOL_OF_ROT, "katana_pool_of_rot", 0xB06838, 0x803A28, 2.1f, false),
    LOURING_CLAYMORE(MokelsBossfightSaphyraModItems.LOURING_CLAYMORE, "louring_claymore", 0x90D8F0, 0x3078A8, 2.8f, true),
    MIRROR_BLADE(MokelsBossfightSaphyraModItems.MIRROR_BLADE, "mirror_blade", 0xF0E0A0, 0xC8A860, 1.7f, false),
    SPIRIT_RUNE_SWORD(MokelsBossfightSaphyraModItems.SPIRIT_RUNE_SWORD, "spirit_rune_sword", 0xC0D8F0, 0x5A88B8, 2.4f, false),
    SUNVEIL(MokelsBossfightSaphyraModItems.SUNVEIL, "sunveil", 0xFFF6B0, 0xF0D860, 2.2f, false),
    NAMELESS(MokelsBossfightSaphyraModItems.SWORD_OF_THE_NAMELESS_GATEKEEPER, "sword_of_the_nameless_gatekeeper", 0xF03030, 0x902020, 2.6f, true),
    TEAR_AND_DROPLET(MokelsBossfightSaphyraModItems.SWORD_TEAR_AND_DROPLET, "sword_tear_and_droplet", 0x9FE6F0, 0x30A8C0, 2.2f, false),
    WITCH_STAFF(MokelsBossfightSaphyraModItems.WITCH_STAFF, "witch_staff", 0x90D8C0, 0x30D890, 2.0f, false);

    private static final WeaponStyle[] VALUES = values();

    private final Supplier<Item> item;
    public final ResourceLocation trail;
    public final int light;
    public final int main;
    public final float radius;
    public final boolean heavy;

    WeaponStyle(Supplier<Item> item, String name, int light, int main, float radius, boolean heavy) {
        this.item = item;
        this.trail = ResourceLocation.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "textures/vfx/trail_" + name + ".png");
        this.light = light;
        this.main = main;
        this.radius = radius;
        this.heavy = heavy;
    }

    public Item item() {
        return this.item.get();
    }

    public static WeaponStyle of(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        Item held = stack.getItem();
        for (WeaponStyle style : VALUES) {
            if (style.item() == held) {
                return style;
            }
        }
        return null;
    }

    public static WeaponStyle byId(int id) {
        return id >= 0 && id < VALUES.length ? VALUES[id] : null;
    }
}
