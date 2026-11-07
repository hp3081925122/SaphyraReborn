/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.LevelAccessor
 */
package com.hp.saphyrareborn.procedures;

import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlocks;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;

public class RightClickKeyLoversProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        block66: {
            block79: {
                ItemStack itemStack;
                ItemStack itemStack2;
                block78: {
                    block77: {
                        ItemStack itemStack3;
                        ItemStack itemStack4;
                        block76: {
                            block75: {
                                ItemStack itemStack5;
                                ItemStack itemStack6;
                                block74: {
                                    block73: {
                                        ItemStack itemStack7;
                                        ItemStack itemStack8;
                                        block72: {
                                            block71: {
                                                ItemStack itemStack9;
                                                ItemStack itemStack10;
                                                block70: {
                                                    block69: {
                                                        ItemStack itemStack11;
                                                        ItemStack itemStack12;
                                                        block68: {
                                                            block67: {
                                                                ItemStack itemStack13;
                                                                ItemStack itemStack14;
                                                                block65: {
                                                                    block64: {
                                                                        ItemStack itemStack15;
                                                                        ItemStack itemStack16;
                                                                        if (entity == null) {
                                                                            return;
                                                                        }
                                                                        if (entity instanceof LivingEntity) {
                                                                            _livEnt1ntity _livEnt1 = (LivingEntity)entity;
                                                                            itemStack16 = _livEnt1.getOffhandItem();
                                                                        } else {
                                                                            itemStack16 = ItemStack.EMPTY;
                                                                        }
                                                                        if (itemStack16.getItem() == MokelsBossfightSaphyraModItems.KEY_3.get()) break block64;
                                                                        if (entity instanceof LivingEntity) {
                                                                            _livEnt2ntity _livEnt2 = (LivingEntity)entity;
                                                                            itemStack15 = _livEnt2.getMainHandItem();
                                                                        } else {
                                                                            itemStack15 = ItemStack.EMPTY;
                                                                        }
                                                                        if (itemStack15.getItem() != MokelsBossfightSaphyraModItems.KEY_3.get()) break block65;
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)x, (double)y, (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)x, (double)y, (double)z), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)x, (double)(1.0 + y), (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)x, (double)(1.0 + y), (double)z), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)x, (double)(-1.0 + y), (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)x, (double)(-1.0 + y), (double)z), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)x, (double)y, (double)(1.0 + z))).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)x, (double)y, (double)(1.0 + z)), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)x, (double)(1.0 + y), (double)(1.0 + z))).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)x, (double)(1.0 + y), (double)(1.0 + z)), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)x, (double)(-1.0 + y), (double)(1.0 + z))).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)x, (double)(-1.0 + y), (double)(1.0 + z)), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)x, (double)(1.0 + y), (double)(-1.0 + z))).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)x, (double)(1.0 + y), (double)(-1.0 + z)), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)x, (double)y, (double)(-1.0 + z))).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)x, (double)y, (double)(-1.0 + z)), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)x, (double)(-1.0 + y), (double)(-1.0 + z))).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)x, (double)(-1.0 + y), (double)(-1.0 + z)), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)x, (double)y, (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)x, (double)y, (double)z), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)x, (double)(1.0 + y), (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)x, (double)(1.0 + y), (double)z), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)x, (double)(-1.0 + y), (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)x, (double)(-1.0 + y), (double)z), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)(1.0 + x), (double)y, (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)(1.0 + x), (double)y, (double)z), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)(1.0 + x), (double)(1.0 + y), (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)(1.0 + x), (double)(1.0 + y), (double)z), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)(1.0 + x), (double)(-1.0 + y), (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)(1.0 + x), (double)(-1.0 + y), (double)z), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)(-1.0 + x), (double)(1.0 + y), (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)(-1.0 + x), (double)(1.0 + y), (double)z), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)(-1.0 + x), (double)y, (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)(-1.0 + x), (double)y, (double)z), false);
                                                                    }
                                                                    if (world.getBlockState(BlockPos.containing((double)(-1.0 + x), (double)(-1.0 + y), (double)z)).getBlock() == MokelsBossfightSaphyraModBlocks.LOCKED_FOG_LOVERS.get()) {
                                                                        world.destroyBlock(BlockPos.containing((double)(-1.0 + x), (double)(-1.0 + y), (double)z), false);
                                                                    }
                                                                    if (entity instanceof Player) {
                                                                        Player _player = (Player)entity;
                                                                        ItemStack _stktoremove = new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.KEY_3.get());
                                                                        _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, (Container)_player.getInventory());
                                                                    }
                                                                    if (!world.isClientSide() && world.getServer() != null) {
                                                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"the barrier broke"), false);
                                                                    }
                                                                    break block66;
                                                                }
                                                                if (entity instanceof LivingEntity) {
                                                                    _livEnt3ntity _livEnt3 = (LivingEntity)entity;
                                                                    itemStack14 = _livEnt3.getOffhandItem();
                                                                } else {
                                                                    itemStack14 = ItemStack.EMPTY;
                                                                }
                                                                if (itemStack14.getItem() == ItemStack.EMPTY.getItem()) break block67;
                                                                if (entity instanceof LivingEntity) {
                                                                    _livEnt4ntity _livEnt4 = (LivingEntity)entity;
                                                                    itemStack13 = _livEnt4.getMainHandItem();
                                                                } else {
                                                                    itemStack13 = ItemStack.EMPTY;
                                                                }
                                                                if (itemStack13.getItem() != ItemStack.EMPTY.getItem()) break block68;
                                                            }
                                                            if (!world.isClientSide() && world.getServer() != null) {
                                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Lovers\" key"), false);
                                                            }
                                                            break block66;
                                                        }
                                                        if (entity instanceof LivingEntity) {
                                                            _livEnt5ntity _livEnt5 = (LivingEntity)entity;
                                                            itemStack12 = _livEnt5.getOffhandItem();
                                                        } else {
                                                            itemStack12 = ItemStack.EMPTY;
                                                        }
                                                        if (itemStack12.getItem() == MokelsBossfightSaphyraModItems.HALLOWED_KEY_1.get()) break block69;
                                                        if (entity instanceof LivingEntity) {
                                                            _livEnt6ntity _livEnt6 = (LivingEntity)entity;
                                                            itemStack11 = _livEnt6.getMainHandItem();
                                                        } else {
                                                            itemStack11 = ItemStack.EMPTY;
                                                        }
                                                        if (itemStack11.getItem() != MokelsBossfightSaphyraModItems.HALLOWED_KEY_1.get()) break block70;
                                                    }
                                                    if (!world.isClientSide() && world.getServer() != null) {
                                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Lovers\" key"), false);
                                                    }
                                                    break block66;
                                                }
                                                if (entity instanceof LivingEntity) {
                                                    _livEnt7ntity _livEnt7 = (LivingEntity)entity;
                                                    itemStack10 = _livEnt7.getOffhandItem();
                                                } else {
                                                    itemStack10 = ItemStack.EMPTY;
                                                }
                                                if (itemStack10.getItem() == MokelsBossfightSaphyraModItems.KEY_4.get()) break block71;
                                                if (entity instanceof LivingEntity) {
                                                    _livEnt8ntity _livEnt8 = (LivingEntity)entity;
                                                    itemStack9 = _livEnt8.getMainHandItem();
                                                } else {
                                                    itemStack9 = ItemStack.EMPTY;
                                                }
                                                if (itemStack9.getItem() != MokelsBossfightSaphyraModItems.KEY_4.get()) break block72;
                                            }
                                            if (!world.isClientSide() && world.getServer() != null) {
                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Lovers\" key"), false);
                                            }
                                            break block66;
                                        }
                                        if (entity instanceof LivingEntity) {
                                            LivingEntity _livEnt9 = (LivingEntity)entity;
                                            itemStack8 = _livEnt9.getOffhandItem();
                                        } else {
                                            itemStack8 = ItemStack.EMPTY;
                                        }
                                        if (itemStack8.getItem() == MokelsBossfightSaphyraModItems.KEY_2.get()) break block73;
                                        if (entity instanceof LivingEntity) {
                                            LivingEntity _livEnt10 = (LivingEntity)entity;
                                            itemStack7 = _livEnt10.getMainHandItem();
                                        } else {
                                            itemStack7 = ItemStack.EMPTY;
                                        }
                                        if (itemStack7.getItem() != MokelsBossfightSaphyraModItems.KEY_2.get()) break block74;
                                    }
                                    if (!world.isClientSide() && world.getServer() != null) {
                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Lovers\" key"), false);
                                    }
                                    break block66;
                                }
                                if (entity instanceof LivingEntity) {
                                    LivingEntity _livEnt11 = (LivingEntity)entity;
                                    itemStack6 = _livEnt11.getOffhandItem();
                                } else {
                                    itemStack6 = ItemStack.EMPTY;
                                }
                                if (itemStack6.getItem() == MokelsBossfightSaphyraModItems.HALLOWED_KEY_3.get()) break block75;
                                if (entity instanceof LivingEntity) {
                                    LivingEntity _livEnt12 = (LivingEntity)entity;
                                    itemStack5 = _livEnt12.getMainHandItem();
                                } else {
                                    itemStack5 = ItemStack.EMPTY;
                                }
                                if (itemStack5.getItem() != MokelsBossfightSaphyraModItems.HALLOWED_KEY_3.get()) break block76;
                            }
                            if (!world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Lovers\" key"), false);
                            }
                            break block66;
                        }
                        if (entity instanceof LivingEntity) {
                            LivingEntity _livEnt13 = (LivingEntity)entity;
                            itemStack4 = _livEnt13.getOffhandItem();
                        } else {
                            itemStack4 = ItemStack.EMPTY;
                        }
                        if (itemStack4.getItem() == MokelsBossfightSaphyraModItems.KEY_1.get()) break block77;
                        if (entity instanceof LivingEntity) {
                            LivingEntity _livEnt14 = (LivingEntity)entity;
                            itemStack3 = _livEnt14.getMainHandItem();
                        } else {
                            itemStack3 = ItemStack.EMPTY;
                        }
                        if (itemStack3.getItem() != MokelsBossfightSaphyraModItems.KEY_1.get()) break block78;
                    }
                    if (!world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Lovers\" key"), false);
                    }
                    break block66;
                }
                if (entity instanceof LivingEntity) {
                    LivingEntity _livEnt15 = (LivingEntity)entity;
                    itemStack2 = _livEnt15.getOffhandItem();
                } else {
                    itemStack2 = ItemStack.EMPTY;
                }
                if (itemStack2.getItem() == MokelsBossfightSaphyraModItems.HALLOWED_KEY_2.get()) break block79;
                if (entity instanceof LivingEntity) {
                    LivingEntity _livEnt16 = (LivingEntity)entity;
                    itemStack = _livEnt16.getMainHandItem();
                } else {
                    itemStack = ItemStack.EMPTY;
                }
                if (itemStack.getItem() != MokelsBossfightSaphyraModItems.HALLOWED_KEY_2.get()) break block66;
            }
            if (!world.isClientSide() && world.getServer() != null) {
                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Lovers\" key"), false);
            }
        }
    }
}





















