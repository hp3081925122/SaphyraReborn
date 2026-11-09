package com.hp.saphyrareborn.item;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;

/** Right-click locates the nearest story structure. Block interactions still run first. */
public final class SigilLocate {
    public static final Target CLOCK_TOWER = structure("fractured_clock_tower", "text.saphyra_reborn.story.structure_clocktower");
    public static final Target CHURCH = structure("church_of_the_oath_pus", "text.saphyra_reborn.story.structure_church");
    public static final Target HALLOWED_TOWERS = structure("the_three_hallowed_towers", "text.saphyra_reborn.story.structure_towers");

    private static final int COOLDOWN_TICKS = 20;

    private SigilLocate() {}

    public static void hint(List<Component> tooltip, Target target) {
        tooltip.add(Component.translatable("text.saphyra_reborn.story.locate_hint", Component.translatable(target.nameKey())).withStyle(ChatFormatting.GRAY));
    }

    public static InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand, Target target) {
        ItemStack stack = player.getItemInHand(hand);
        InteractionResult result = locate(player, stack, target);
        return result == InteractionResult.SUCCESS ? InteractionResultHolder.success(stack) : InteractionResultHolder.fail(stack);
    }

    public static InteractionResult useOn(UseOnContext context, Target target) {
        return locate(context.getPlayer(), context.getItemInHand(), target);
    }

    private static InteractionResult locate(Player player, ItemStack stack, Target target) {
        if (player == null) {
            return InteractionResult.PASS;
        }
        if (player.level().isClientSide()) {
            return player.getCooldowns().isOnCooldown(stack.getItem()) ? InteractionResult.FAIL : InteractionResult.SUCCESS;
        }
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            return InteractionResult.FAIL;
        }
        player.getCooldowns().addCooldown(stack.getItem(), COOLDOWN_TICKS);
        ServerLevel level = (ServerLevel) player.level();
        Component name = Component.translatable(target.nameKey());
        Structure structure = level.registryAccess().registryOrThrow(Registries.STRUCTURE).get(target.structure());
        if (structure != null && level.structureManager().getStructureAt(player.blockPosition(), structure).isValid()) {
            player.displayClientMessage(Component.translatable("text.saphyra_reborn.story.locate_here", name), false);
            return InteractionResult.SUCCESS;
        }
        BlockPos found = nearest(level, target.structure(), player.blockPosition());
        if (found == null) {
            player.displayClientMessage(Component.translatable("text.saphyra_reborn.story.locate_missing", name), false);
            return InteractionResult.FAIL;
        }
        int distance = (int) Math.sqrt(player.blockPosition().distToCenterSqr(found.getX() + 0.5, player.getY(), found.getZ() + 0.5));
        player.displayClientMessage(Component.translatable("text.saphyra_reborn.story.locate_found", name, found.getX(), found.getZ(), distance), false);
        return InteractionResult.SUCCESS;
    }

    private static BlockPos nearest(ServerLevel level, ResourceKey<Structure> structure, BlockPos origin) {
        Holder<Structure> holder = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getHolder(structure).orElse(null);
        if (holder == null) {
            return null;
        }
        Pair<BlockPos, Holder<Structure>> found = level.getChunkSource().getGenerator().findNearestMapStructure(level, HolderSet.direct(holder), origin, searchRadius(level, structure), false);
        return found == null ? null : found.getFirst();
    }

    private static int searchRadius(ServerLevel level, ResourceKey<Structure> structure) {
        int radius = 100;
        for (StructureSet set : level.registryAccess().registryOrThrow(Registries.STRUCTURE_SET)) {
            boolean matches = false;
            for (StructureSet.StructureSelectionEntry entry : set.structures()) {
                if (entry.structure().is(structure)) {
                    matches = true;
                    break;
                }
            }
            if (matches && set.placement() instanceof RandomSpreadStructurePlacement spread) {
                radius = Math.max(radius, spread.spacing());
            }
        }
        return radius;
    }

    private static Target structure(String path, String nameKey) {
        return new Target(ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("saphyra_reborn", path)), nameKey);
    }

    public record Target(ResourceKey<Structure> structure, String nameKey) {}
}
