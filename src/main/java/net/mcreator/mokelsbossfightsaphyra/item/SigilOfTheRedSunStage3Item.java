package com.hp.saphyrareborn.item;

import com.hp.saphyrareborn.entity.PuppetOfYivaNemorEntity;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModEntities;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import com.hp.saphyrareborn.procedures.StoryDialog;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;

/** 第三阶段赤日印记：玛琳转换萨菲拉的记忆后给出，在破碎钟楼内右键召唤伊瓦的傀儡。 */
public class SigilOfTheRedSunStage3Item extends Item {
    private static final ResourceKey<Structure> CLOCK_TOWER = ResourceKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath("saphyra_reborn", "fractured_clock_tower"));

    public SigilOfTheRedSunStage3Item() {
        super(new Item.Properties().setId(com.hp.saphyrareborn.init.RegistryIds.item).stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, display, list, flag);
        list.accept(Component.translatable("text.saphyra_reborn.hardcoded.8c7a1e042576"));
        list.accept(Component.translatable("text.saphyra_reborn.story.sigil3_hint"));
        list.accept(Component.literal(""));
        list.accept(Component.translatable("text.saphyra_reborn.hardcoded.d38b85ee6dba"));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!(level instanceof ServerLevel server)) {
            return InteractionResult.SUCCESS;
        }
        if (!inClockTower(server, player.blockPosition())) {
            player.sendOverlayMessage(Component.translatable("text.saphyra_reborn.story.sigil3_wrong_place"));
            return InteractionResult.FAIL;
        }
        if (!server.getEntitiesOfClass(PuppetOfYivaNemorEntity.class, new AABB(player.blockPosition()).inflate(64.0)).isEmpty()) {
            return InteractionResult.FAIL;
        }
        stack.consume(1, player);
        BlockPos pos = player.blockPosition().relative(player.getDirection(), 4);
        server.sendParticles(MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get(), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 300, 0.4, 1.5, 0.4, 0.05);
        server.playSound(null, pos, SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 0.6f, 0.6f);
        StoryDialog.say(server, "puppet_awakens");
        MokelsBossfightSaphyraModEntities.PUPPET_OF_YIVA_NEMOR.get().spawn(server, pos, EntitySpawnReason.MOB_SUMMONED);
        return InteractionResult.CONSUME;
    }

    private static boolean inClockTower(ServerLevel level, BlockPos pos) {
        Structure tower = level.registryAccess().lookupOrThrow(Registries.STRUCTURE).get(CLOCK_TOWER).map(net.minecraft.core.Holder::value).orElse(null);
        return tower != null && level.structureManager().getStructureAt(pos, tower).isValid();
    }
}
