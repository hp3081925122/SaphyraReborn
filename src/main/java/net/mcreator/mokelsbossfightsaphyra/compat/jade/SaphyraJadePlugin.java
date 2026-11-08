package com.hp.saphyrareborn.compat.jade;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Optional Jade integration: tells the player what to right-click each story block with.
 * Jade only loads classes annotated with {@link WailaPlugin}, so nothing here runs without Jade.
 */
@WailaPlugin
public class SaphyraJadePlugin implements IWailaPlugin {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath("saphyra_reborn", "right_click_hint");

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(HintProvider.INSTANCE, Block.class);
    }

    enum HintProvider implements IBlockComponentProvider {
        INSTANCE;

        /** null value = no item needed, empty hand / any right-click. */
        private Map<Block, Supplier<? extends Item>> hints;

        private Map<Block, Supplier<? extends Item>> hints() {
            if (hints == null) {
                Map<Block, Supplier<? extends Item>> m = new IdentityHashMap<>();
                m.put(MokelsBossfightSaphyraModBlocks.ODDMENT_STAGE_2.get(), MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN);
                m.put(MokelsBossfightSaphyraModBlocks.ODDMENT_HALLOWED_TOWER.get(), MokelsBossfightSaphyraModItems.SIGIL_OF_THE_RED_SUN_STAGE_2);
                m.put(MokelsBossfightSaphyraModBlocks.LYIOR_STATUE.get(), null);
                m.put(MokelsBossfightSaphyraModBlocks.LYIOR_STATUE_AKTIVATED.get(), MokelsBossfightSaphyraModItems.BROKEN_SIGIL_OF_THE_RED_SUN);
                m.put(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_STEPS.get(), MokelsBossfightSaphyraModItems.KEY_1);
                m.put(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_KNIFE.get(), MokelsBossfightSaphyraModItems.KEY_2);
                m.put(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get(), MokelsBossfightSaphyraModItems.KEY_3);
                m.put(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_HATERED.get(), MokelsBossfightSaphyraModItems.KEY_4);
                m.put(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_WORSHIP.get(), MokelsBossfightSaphyraModItems.HALLOWED_KEY_1);
                m.put(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_CHAIN.get(), MokelsBossfightSaphyraModItems.HALLOWED_KEY_2);
                m.put(MokelsBossfightSaphyraModBlocks.LOCKED_FOG_SHATTERED.get(), MokelsBossfightSaphyraModItems.HALLOWED_KEY_3);
                m.put(MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_1.get(), MokelsBossfightSaphyraModItems.SAPHYRA_KEY);
                m.put(MokelsBossfightSaphyraModBlocks.BOSS_DOOR_1.get(), null);
                hints = m;
            }
            return hints;
        }

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            Block block = accessor.getBlock();
            Map<Block, Supplier<? extends Item>> map = hints();
            if (!map.containsKey(block)) {
                return;
            }
            Supplier<? extends Item> item = map.get(block);
            Component line = item == null
                    ? Component.translatable("jade.saphyra_reborn.right_click_empty")
                    : Component.translatable("jade.saphyra_reborn.right_click_with",
                            Component.translatable(item.get().getDescriptionId()).withStyle(ChatFormatting.GOLD));
            tooltip.add(line.copy().withStyle(ChatFormatting.GRAY));
        }

        @Override
        public ResourceLocation getUid() {
            return UID;
        }
    }
}
