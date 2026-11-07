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
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package com.hp.saphyrareborn.procedures;

import java.util.Map;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class RightClickSaphyraDoorKeyProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        block82: {
            block97: {
                ItemStack itemStack;
                ItemStack itemStack2;
                block96: {
                    block95: {
                        ItemStack itemStack3;
                        ItemStack itemStack4;
                        block94: {
                            block93: {
                                ItemStack itemStack5;
                                ItemStack itemStack6;
                                block92: {
                                    block91: {
                                        ItemStack itemStack7;
                                        ItemStack itemStack8;
                                        block90: {
                                            block89: {
                                                ItemStack itemStack9;
                                                ItemStack itemStack10;
                                                block88: {
                                                    block87: {
                                                        ItemStack itemStack11;
                                                        ItemStack itemStack12;
                                                        block86: {
                                                            block85: {
                                                                ItemStack itemStack13;
                                                                ItemStack itemStack14;
                                                                block84: {
                                                                    block83: {
                                                                        ItemStack itemStack15;
                                                                        ItemStack itemStack16;
                                                                        block81: {
                                                                            block80: {
                                                                                ItemStack itemStack17;
                                                                                ItemStack itemStack18;
                                                                                if (entity == null) {
                                                                                    return;
                                                                                }
                                                                                if (entity instanceof LivingEntity) {
                                                                                    _livEnt1ntity _livEnt1 = (LivingEntity)entity;
                                                                                    itemStack18 = _livEnt1.getOffhandItem();
                                                                                } else {
                                                                                    itemStack18 = ItemStack.EMPTY;
                                                                                }
                                                                                if (itemStack18.getItem() == MokelsBossfightSaphyraModItems.SAPHYRA_KEY.get()) break block80;
                                                                                if (entity instanceof LivingEntity) {
                                                                                    _livEnt2ntity _livEnt2 = (LivingEntity)entity;
                                                                                    itemStack17 = _livEnt2.getMainHandItem();
                                                                                } else {
                                                                                    itemStack17 = ItemStack.EMPTY;
                                                                                }
                                                                                if (itemStack17.getItem() != MokelsBossfightSaphyraModItems.SAPHYRA_KEY.get()) break block81;
                                                                            }
                                                                            BlockPos _bp = BlockPos.containing((double)x, (double)y, (double)z);
                                                                            BlockState _bs = ((Block)MokelsBossfightSaphyraModBlocks.SAPHYRA_DOOR_2.get()).defaultBlockState();
                                                                            BlockState _bso = world.getBlockState(_bp);
                                                                            for (Map.Entry entry : _bso.getValues().entrySet()) {
                                                                                Property _property = _bs.getBlock().getStateDefinition().getProperty(((Property)entry.getKey()).getName());
                                                                                if (_property == null || _bs.getValue(_property) == null) continue;
                                                                                try {
                                                                                    _bs = (BlockState)_bs.setValue(_property, (Comparable)entry.getValue());
                                                                                }
                                                                                catch (Exception exception) {}
                                                                            }
                                                                            world.setBlock(_bp, _bs, 3);
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)y, (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)y, (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)(1.0 + y), (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)(1.0 + y), (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)(2.0 + y), (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)(2.0 + y), (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)(3.0 + y), (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)(3.0 + y), (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)(4.0 + y), (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)(4.0 + y), (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)y, (double)(1.0 + z))).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)y, (double)(1.0 + z)), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)y, (double)(-1.0 + z))).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)y, (double)(-1.0 + z)), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)(1.0 + y), (double)(1.0 + z))).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)(1.0 + y), (double)(1.0 + z)), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)(2.0 + y), (double)(1.0 + z))).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)(2.0 + y), (double)(1.0 + z)), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)(3.0 + y), (double)(1.0 + z))).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)(3.0 + y), (double)(1.0 + z)), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)(4.0 + y), (double)(1.0 + z))).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)(4.0 + y), (double)(1.0 + z)), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)(1.0 + y), (double)(-1.0 + z))).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)(1.0 + y), (double)(-1.0 + z)), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)(2.0 + y), (double)(-1.0 + z))).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)(2.0 + y), (double)(-1.0 + z)), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)(3.0 + y), (double)(-1.0 + z))).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)(3.0 + y), (double)(-1.0 + z)), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)x, (double)(4.0 + y), (double)(-1.0 + z))).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)x, (double)(4.0 + y), (double)(-1.0 + z)), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)(-1.0 + x), (double)y, (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)(-1.0 + x), (double)y, (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)(1.0 + x), (double)y, (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)(1.0 + x), (double)y, (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)(1.0 + x), (double)(1.0 + y), (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)(1.0 + x), (double)(1.0 + y), (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)(1.0 + x), (double)(2.0 + y), (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)(1.0 + x), (double)(2.0 + y), (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)(1.0 + x), (double)(3.0 + y), (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)(1.0 + x), (double)(3.0 + y), (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)(1.0 + x), (double)(4.0 + y), (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)(1.0 + x), (double)(4.0 + y), (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)(-1.0 + x), (double)(1.0 + y), (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)(-1.0 + x), (double)(1.0 + y), (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)(-1.0 + x), (double)(2.0 + y), (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)(-1.0 + x), (double)(2.0 + y), (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)(-1.0 + x), (double)(3.0 + y), (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)(-1.0 + x), (double)(3.0 + y), (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)(-1.0 + x), (double)(4.0 + y), (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)(-1.0 + x), (double)(4.0 + y), (double)z), false);
                                                                            }
                                                                            if (world.getBlockState(BlockPos.containing((double)(-1.0 + x), (double)y, (double)z)).getBlock() == Blocks.BARRIER) {
                                                                                world.destroyBlock(BlockPos.containing((double)(-1.0 + x), (double)y, (double)z), false);
                                                                            }
                                                                            if (entity instanceof Player) {
                                                                                Player _player = (Player)entity;
                                                                                ItemStack _stktoremove = new ItemStack((ItemLike)MokelsBossfightSaphyraModItems.SAPHYRA_KEY.get());
                                                                                _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, (Container)_player.getInventory());
                                                                            }
                                                                            if (!world.isClientSide() && world.getServer() != null) {
                                                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"The Door Opens"), false);
                                                                            }
                                                                            break block82;
                                                                        }
                                                                        if (entity instanceof LivingEntity) {
                                                                            _livEnt3ntity _livEnt3 = (LivingEntity)entity;
                                                                            itemStack16 = _livEnt3.getOffhandItem();
                                                                        } else {
                                                                            itemStack16 = ItemStack.EMPTY;
                                                                        }
                                                                        if (itemStack16.getItem() == ItemStack.EMPTY.getItem()) break block83;
                                                                        if (entity instanceof LivingEntity) {
                                                                            _livEnt4ntity _livEnt4 = (LivingEntity)entity;
                                                                            itemStack15 = _livEnt4.getMainHandItem();
                                                                        } else {
                                                                            itemStack15 = ItemStack.EMPTY;
                                                                        }
                                                                        if (itemStack15.getItem() != ItemStack.EMPTY.getItem()) break block84;
                                                                    }
                                                                    if (!world.isClientSide() && world.getServer() != null) {
                                                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Sword\" key"), false);
                                                                    }
                                                                    break block82;
                                                                }
                                                                if (entity instanceof LivingEntity) {
                                                                    _livEnt5ntity _livEnt5 = (LivingEntity)entity;
                                                                    itemStack14 = _livEnt5.getOffhandItem();
                                                                } else {
                                                                    itemStack14 = ItemStack.EMPTY;
                                                                }
                                                                if (itemStack14.getItem() == MokelsBossfightSaphyraModItems.KEY_3.get()) break block85;
                                                                if (entity instanceof LivingEntity) {
                                                                    _livEnt6ntity _livEnt6 = (LivingEntity)entity;
                                                                    itemStack13 = _livEnt6.getMainHandItem();
                                                                } else {
                                                                    itemStack13 = ItemStack.EMPTY;
                                                                }
                                                                if (itemStack13.getItem() != MokelsBossfightSaphyraModItems.KEY_3.get()) break block86;
                                                            }
                                                            if (!world.isClientSide() && world.getServer() != null) {
                                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Sword\" key"), false);
                                                            }
                                                            break block82;
                                                        }
                                                        if (entity instanceof LivingEntity) {
                                                            _livEnt7ntity _livEnt7 = (LivingEntity)entity;
                                                            itemStack12 = _livEnt7.getOffhandItem();
                                                        } else {
                                                            itemStack12 = ItemStack.EMPTY;
                                                        }
                                                        if (itemStack12.getItem() == MokelsBossfightSaphyraModItems.KEY_1.get()) break block87;
                                                        if (entity instanceof LivingEntity) {
                                                            _livEnt8ntity _livEnt8 = (LivingEntity)entity;
                                                            itemStack11 = _livEnt8.getMainHandItem();
                                                        } else {
                                                            itemStack11 = ItemStack.EMPTY;
                                                        }
                                                        if (itemStack11.getItem() != MokelsBossfightSaphyraModItems.KEY_1.get()) break block88;
                                                    }
                                                    if (!world.isClientSide() && world.getServer() != null) {
                                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Sword\" key"), false);
                                                    }
                                                    break block82;
                                                }
                                                if (entity instanceof LivingEntity) {
                                                    LivingEntity _livEnt9 = (LivingEntity)entity;
                                                    itemStack10 = _livEnt9.getOffhandItem();
                                                } else {
                                                    itemStack10 = ItemStack.EMPTY;
                                                }
                                                if (itemStack10.getItem() == MokelsBossfightSaphyraModItems.HALLOWED_KEY_3.get()) break block89;
                                                if (entity instanceof LivingEntity) {
                                                    LivingEntity _livEnt10 = (LivingEntity)entity;
                                                    itemStack9 = _livEnt10.getMainHandItem();
                                                } else {
                                                    itemStack9 = ItemStack.EMPTY;
                                                }
                                                if (itemStack9.getItem() != MokelsBossfightSaphyraModItems.HALLOWED_KEY_3.get()) break block90;
                                            }
                                            if (!world.isClientSide() && world.getServer() != null) {
                                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Sword\" key"), false);
                                            }
                                            break block82;
                                        }
                                        if (entity instanceof LivingEntity) {
                                            LivingEntity _livEnt11 = (LivingEntity)entity;
                                            itemStack8 = _livEnt11.getOffhandItem();
                                        } else {
                                            itemStack8 = ItemStack.EMPTY;
                                        }
                                        if (itemStack8.getItem() == MokelsBossfightSaphyraModItems.HALLOWED_KEY_1.get()) break block91;
                                        if (entity instanceof LivingEntity) {
                                            LivingEntity _livEnt12 = (LivingEntity)entity;
                                            itemStack7 = _livEnt12.getMainHandItem();
                                        } else {
                                            itemStack7 = ItemStack.EMPTY;
                                        }
                                        if (itemStack7.getItem() != MokelsBossfightSaphyraModItems.HALLOWED_KEY_1.get()) break block92;
                                    }
                                    if (!world.isClientSide() && world.getServer() != null) {
                                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Sword\" key"), false);
                                    }
                                    break block82;
                                }
                                if (entity instanceof LivingEntity) {
                                    LivingEntity _livEnt13 = (LivingEntity)entity;
                                    itemStack6 = _livEnt13.getOffhandItem();
                                } else {
                                    itemStack6 = ItemStack.EMPTY;
                                }
                                if (itemStack6.getItem() == MokelsBossfightSaphyraModItems.KEY_2.get()) break block93;
                                if (entity instanceof LivingEntity) {
                                    LivingEntity _livEnt14 = (LivingEntity)entity;
                                    itemStack5 = _livEnt14.getMainHandItem();
                                } else {
                                    itemStack5 = ItemStack.EMPTY;
                                }
                                if (itemStack5.getItem() != MokelsBossfightSaphyraModItems.KEY_2.get()) break block94;
                            }
                            if (!world.isClientSide() && world.getServer() != null) {
                                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Sword\" key"), false);
                            }
                            break block82;
                        }
                        if (entity instanceof LivingEntity) {
                            LivingEntity _livEnt15 = (LivingEntity)entity;
                            itemStack4 = _livEnt15.getOffhandItem();
                        } else {
                            itemStack4 = ItemStack.EMPTY;
                        }
                        if (itemStack4.getItem() == MokelsBossfightSaphyraModItems.HALLOWED_KEY_2.get()) break block95;
                        if (entity instanceof LivingEntity) {
                            LivingEntity _livEnt16 = (LivingEntity)entity;
                            itemStack3 = _livEnt16.getMainHandItem();
                        } else {
                            itemStack3 = ItemStack.EMPTY;
                        }
                        if (itemStack3.getItem() != MokelsBossfightSaphyraModItems.HALLOWED_KEY_2.get()) break block96;
                    }
                    if (!world.isClientSide() && world.getServer() != null) {
                        world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Sword\" key"), false);
                    }
                    break block82;
                }
                if (entity instanceof LivingEntity) {
                    LivingEntity _livEnt17 = (LivingEntity)entity;
                    itemStack2 = _livEnt17.getOffhandItem();
                } else {
                    itemStack2 = ItemStack.EMPTY;
                }
                if (itemStack2.getItem() == MokelsBossfightSaphyraModItems.KEY_4.get()) break block97;
                if (entity instanceof LivingEntity) {
                    LivingEntity _livEnt18 = (LivingEntity)entity;
                    itemStack = _livEnt18.getMainHandItem();
                } else {
                    itemStack = ItemStack.EMPTY;
                }
                if (itemStack.getItem() != MokelsBossfightSaphyraModItems.KEY_4.get()) break block82;
            }
            if (!world.isClientSide() && world.getServer() != null) {
                world.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)"you need a \"Sword\" key"), false);
            }
        }
    }
}




















