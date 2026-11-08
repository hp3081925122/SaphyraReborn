package com.hp.saphyrareborn.procedures;

import javax.annotation.Nullable;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class RightclickBagProcedure {
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != net.minecraft.world.InteractionHand.MAIN_HAND) return;
        if (event.getHand() != event.getEntity().getUsedItemHand()) {
            return;
        }
        RightclickBagProcedure.execute((Event)event, (LevelAccessor)event.getLevel(), (Entity)event.getEntity());
    }

    public static void execute(LevelAccessor world, Entity entity) {
        RightclickBagProcedure.execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        ItemStack itemStack;
        if (entity == null) {
            return;
        }
        if (entity instanceof LivingEntity) {
            LivingEntity _livEnt = (LivingEntity)entity;
            itemStack = _livEnt.getMainHandItem();
        } else {
            itemStack = ItemStack.EMPTY;
        }
        if (itemStack.getItem() == MokelsBossfightSaphyraModItems.BOTTOMLESS_BAG.get()) {
            ItemEntity entityToSpawn;
            ServerLevel _level;
            if (world.isClientSide()) {
                Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.BOTTOMLESS_BAG.get()));
            }
            if (entity instanceof Player) {
                Player _player = (Player)entity;
                ItemStack _stktoremove = new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.BOTTOMLESS_BAG.get());
                _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, (Container)_player.inventoryMenu.getCraftSlots());
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                entityToSpawn = new ItemEntity((Level)_level, entity.getX(), 0.5 + entity.getY(), entity.getZ(), new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.SAPHYRAS_HAT_HELMET.get()));
                entityToSpawn.setPickUpDelay(0);
                entityToSpawn.setUnlimitedLifetime();
                _level.addFreshEntity((Entity)entityToSpawn);
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                entityToSpawn = new ItemEntity((Level)_level, entity.getX(), 0.5 + entity.getY(), entity.getZ(), new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.WITCH_STAFF.get()));
                entityToSpawn.setPickUpDelay(0);
                entityToSpawn.setUnlimitedLifetime();
                _level.addFreshEntity((Entity)entityToSpawn);
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                entityToSpawn = new ItemEntity((Level)_level, entity.getX(), 0.5 + entity.getY(), entity.getZ(), new ItemStack((ItemLike)Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE));
                entityToSpawn.setPickUpDelay(0);
                entityToSpawn.setUnlimitedLifetime();
                _level.addFreshEntity((Entity)entityToSpawn);
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                entityToSpawn = new ItemEntity((Level)_level, entity.getX(), 0.5 + entity.getY(), entity.getZ(), new ItemStack((ItemLike)Items.NETHERITE_INGOT));
                entityToSpawn.setPickUpDelay(0);
                entityToSpawn.setUnlimitedLifetime();
                _level.addFreshEntity((Entity)entityToSpawn);
            }
            // Saphyra Reborn: items the original never handed out
            if (world instanceof ServerLevel _extraLevel) {
                for (ItemLike extra : new ItemLike[]{MokelsBossfightSaphyraModItems.SAPHYRAS_CAPE_CHESTPLATE.get(), MokelsBossfightSaphyraModItems.GLUTTONOUS_FLASK.get()}) {
                    ItemEntity drop = new ItemEntity((Level)_extraLevel, entity.getX(), 0.5 + entity.getY(), entity.getZ(), new ItemStack(extra));
                    drop.setPickUpDelay(0);
                    drop.setUnlimitedLifetime();
                    _extraLevel.addFreshEntity((Entity)drop);
                }
            }
        }
    }
}

