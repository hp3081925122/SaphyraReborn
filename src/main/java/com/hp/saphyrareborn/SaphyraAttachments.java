package com.hp.saphyrareborn;

import com.hp.saphyrareborn.network.MokelsBossfightSaphyraModVariables;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class SaphyraAttachments {
    public static final DeferredRegister<AttachmentType<?>> REGISTRY = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SaphyraReborn.MOD_ID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MokelsBossfightSaphyraModVariables.PlayerVariables>> PLAYER_VARIABLES = REGISTRY.register("player_variables", () -> AttachmentType.serializable(MokelsBossfightSaphyraModVariables.PlayerVariables::new).copyOnDeath().build());

    private SaphyraAttachments() {
    }
}














