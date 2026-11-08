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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MobSpawnType;
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
    private static final ResourceKey<Structure> CLOCK_TOWER = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "fractured_clock_tower"));

    public SigilOfTheRedSunStage3Item() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(Component.translatable("text.saphyra_reborn.hardcoded.8c7a1e042576"));
        list.add(Component.translatable("text.saphyra_reborn.story.sigil3_hint"));
        list.add(Component.literal(""));
        list.add(Component.translatable("text.saphyra_reborn.hardcoded.d38b85ee6dba"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!(level instanceof ServerLevel server)) {
            return InteractionResultHolder.success(stack);
        }
        if (!inClockTower(server, player.blockPosition())) {
            player.displayClientMessage(Component.translatable("text.saphyra_reborn.story.sigil3_wrong_place"), true);
            return InteractionResultHolder.fail(stack);
        }
        if (!server.getEntitiesOfClass(PuppetOfYivaNemorEntity.class, new AABB(player.blockPosition()).inflate(64.0)).isEmpty()) {
            return InteractionResultHolder.fail(stack);
        }
        stack.consume(1, player);
        BlockPos pos = player.blockPosition().relative(player.getDirection(), 4);
        server.sendParticles(MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get(), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 300, 0.4, 1.5, 0.4, 0.05);
        server.playSound(null, pos, SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 0.6f, 0.6f);
        StoryDialog.say(server, "puppet_awakens");
        MokelsBossfightSaphyraModEntities.PUPPET_OF_YIVA_NEMOR.get().spawn(server, pos, MobSpawnType.MOB_SUMMONED);
        return InteractionResultHolder.consume(stack);
    }

    private static boolean inClockTower(ServerLevel level, BlockPos pos) {
        Structure tower = level.registryAccess().registryOrThrow(Registries.STRUCTURE).get(CLOCK_TOWER);
        return tower != null && level.structureManager().getStructureAt(pos, tower).isValid();
    }
}
