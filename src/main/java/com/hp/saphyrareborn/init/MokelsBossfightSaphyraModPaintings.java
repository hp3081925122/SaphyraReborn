package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class MokelsBossfightSaphyraModPaintings {
    public static final DeferredRegister<PaintingVariant> REGISTRY = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, MokelsBossfightSaphyraMod.MODID);
    public static final RegistryObject<PaintingVariant> SAPHYRA_BY_ALLAN_72A = REGISTRY.register("saphyra_by_allan_72a", () -> new PaintingVariant(64, 32));

    private MokelsBossfightSaphyraModPaintings() {
    }
}
