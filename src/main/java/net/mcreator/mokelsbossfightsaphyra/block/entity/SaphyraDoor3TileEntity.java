package com.hp.saphyrareborn.block.entity;

import java.util.stream.IntStream;
import javax.annotation.Nullable;
import com.hp.saphyrareborn.block.SaphyraDoor3Block;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import com.geckolib.animatable.GeoBlockEntity;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;

public class SaphyraDoor3TileEntity
extends RandomizableContainerBlockEntity
implements GeoBlockEntity,
WorldlyContainer {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
    String prevAnim = "0";

    public SaphyraDoor3TileEntity(BlockPos pos, BlockState state) {
        super((BlockEntityType)MokelsBossfightSaphyraModBlockEntities.SAPHYRA_DOOR_3.get(), pos, state);
    }

    private PlayState predicate(AnimationTest event) {
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationTest event) {
        String animationprocedure = "" + this.getBlockState().getValue((Property)SaphyraDoor3Block.ANIMATION);
        if (!animationprocedure.equals("0") && event.controller().hasAnimationFinished() || !animationprocedure.equals(this.prevAnim) && !animationprocedure.equals("0")) {
            if (!animationprocedure.equals(this.prevAnim)) {
                event.controller().reset();
            }
            event.controller().setAnimation(RawAnimation.begin().thenPlay(animationprocedure));
            if (event.controller().hasAnimationFinished()) {
                Property property = this.getBlockState().getBlock().getStateDefinition().getProperty("animation");
                if (property instanceof IntegerProperty) {
                    IntegerProperty _integerProp = (IntegerProperty)property;
                    this.level.setBlock(this.getBlockPos(), (BlockState)this.getBlockState().setValue((Property)_integerProp, (Comparable)Integer.valueOf(0)), 3);
                }
                event.controller().reset();
            }
        } else if (animationprocedure.equals("0")) {
            this.prevAnim = "0";
            return PlayState.STOP;
        }
        this.prevAnim = animationprocedure;
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController[]{new AnimationController<>("controller", 0, this::predicate)});
        data.add(new AnimationController[]{new AnimationController<>("procedurecontroller", 0, this::procedurePredicate)});
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    protected void loadAdditional(net.minecraft.world.level.storage.ValueInput compound) {
        super.loadAdditional(compound);
        if (!this.tryLoadLootTable(compound)) {
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(compound, this.stacks);
        }
    }

    protected void saveAdditional(net.minecraft.world.level.storage.ValueOutput compound) {
        super.saveAdditional(compound);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks);
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create((BlockEntity)this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return super.getUpdateTag(provider);
    }

    public int getContainerSize() {
        return this.stacks.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks) {
            if (itemstack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    protected Component getDefaultName() {
        return Component.translatable("text.saphyra_reborn.hardcoded.eec4598baac0");
    }

    public int getMaxStackSize() {
        return 64;
    }

    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows((int)id, (Inventory)inventory);
    }

    public Component getDisplayName() {
        return Component.translatable("text.saphyra_reborn.hardcoded.709010d24b2b");
    }

    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    protected void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }
}
