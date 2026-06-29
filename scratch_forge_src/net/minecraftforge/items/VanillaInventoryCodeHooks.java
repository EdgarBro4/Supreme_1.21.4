/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.items;

import net.minecraft.world.level.block.DropperBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

//TODO: [Forge][1.21.9][Cleanup] Mark as @ApiStatus.Internal
public class VanillaInventoryCodeHooks {
    /**
     * Copied from HopperBlockEntity#suckInItems and added capability support
     * @return Null if we did nothing {no IItemHandler}, True if we moved an item, False if we moved no items
     */
    @Nullable
    public static Boolean extractHook(Level level, Hopper dest) {
        var handler = getItemHandler(level, dest, Direction.UP).map(Pair::getKey).orElse(null);
        if (handler == null)
            return null;

        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack extractItem = handler.extractItem(i, 1, true);
            if (!extractItem.m_41619_()) {
                for (int j = 0; j < dest.m_6643_(); j++) {
                    ItemStack destStack = dest.m_8020_(j);
                    if (dest.m_7013_(j, extractItem) && (destStack.m_41619_() || destStack.m_41613_() < destStack.m_41741_() && destStack.m_41613_() < dest.m_6893_() && ItemHandlerHelper.canItemStacksStack(extractItem, destStack))) {
                        extractItem = handler.extractItem(i, 1, false);
                        if (destStack.m_41619_())
                            dest.m_6836_(j, extractItem);
                        else {
                            destStack.m_41769_(1);
                            dest.m_6836_(j, destStack);
                        }
                        dest.m_6596_();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Copied from DropperBlock#dispenseFrom and added capability support
     */
    public static boolean dropperInsertHook(Level level, BlockPos pos, DispenserBlockEntity dropper, int slot, @NotNull ItemStack stack) {
        var direction = level.m_8055_(pos).m_61143_(DropperBlock.f_52659_);
        var handler = getItemHandlerPair(level, pos.m_121945_(direction), direction.m_122424_());
        if (handler.isEmpty())
            return true;

        var itemHandler = handler.get().getKey();
        var destination = handler.get().getValue();

        ItemStack dispensedStack = stack.m_41777_().m_41620_(1);
        ItemStack remainder = putStackInInventoryAllSlots(dropper, destination, itemHandler, dispensedStack);

        if (remainder.m_41619_()) {
            remainder = stack.m_41777_();
            remainder.m_41774_(1);
        } else
            remainder = stack.m_41777_();

        dropper.m_6836_(slot, remainder);
        return false;
    }

    /**
     * Copied from HopperBlockEntity#ejectItems and added capability support
     */
    public static boolean insertHook(HopperBlockEntity hopper) {
        var handler = getItemHandler(hopper.m_58904_(), hopper, hopper.m_58900_().m_61143_(HopperBlock.f_54021_));
        if (handler.isEmpty())
            return false;

        var itemHandler = handler.get().getKey();
        var destination = handler.get().getValue();

        if (isFull(itemHandler))
            return false;

        for (int i = 0; i < hopper.m_6643_(); ++i) {
            if (!hopper.m_8020_(i).m_41619_()) {
                ItemStack originalSlotContents = hopper.m_8020_(i).m_41777_();
                ItemStack insertStack = hopper.m_7407_(i, 1);
                ItemStack remainder = putStackInInventoryAllSlots(hopper, destination, itemHandler, insertStack);

                if (remainder.m_41619_())
                    return true;

                hopper.m_6836_(i, originalSlotContents);
            }
        }

        return false;
    }

    private static ItemStack putStackInInventoryAllSlots(BlockEntity source, Object destination, IItemHandler destInventory, ItemStack stack) {
        for (int slot = 0; slot < destInventory.getSlots() && !stack.m_41619_(); slot++)
            stack = insertStack(source, destination, destInventory, stack, slot);
        return stack;
    }

    /**
     * Copied from HopperBlockEntity#tryMoveInItem and added capability support
     */
    private static ItemStack insertStack(BlockEntity source, Object destination, IItemHandler destInventory, ItemStack stack, int slot) {
        ItemStack itemstack = destInventory.getStackInSlot(slot);

        if (destInventory.insertItem(slot, stack, true).m_41619_()) {
            boolean insertedItem = false;
            boolean inventoryWasEmpty = isEmpty(destInventory);

            if (itemstack.m_41619_()) {
                destInventory.insertItem(slot, stack, false);
                stack = ItemStack.f_41583_;
                insertedItem = true;
            } else if (ItemHandlerHelper.canItemStacksStack(itemstack, stack)) {
                int originalSize = stack.m_41613_();
                stack = destInventory.insertItem(slot, stack, false);
                insertedItem = originalSize < stack.m_41613_();
            }

            if (insertedItem) {
                if (inventoryWasEmpty && destination instanceof HopperBlockEntity dHopper) {
                    if (!dHopper.m_59409_()) {
                        int k = 0;
                        if (source instanceof HopperBlockEntity sHopper && dHopper.getLastUpdateTime() >= sHopper.getLastUpdateTime())
                            k = 1;
                        dHopper.m_59395_(8 - k);
                    }
                }
            }
        }

        return stack;
    }

    private static Optional<Pair<IItemHandler, Object>> getItemHandler(Level level, Hopper hopper, Direction hopperFacing) {
        double x = hopper.m_6343_() + (double) hopperFacing.m_122429_();
        double y = hopper.m_6358_() + (double) hopperFacing.m_122430_();
        double z = hopper.m_6446_() + (double) hopperFacing.m_122431_();
        return getItemHandler(level, x, y, z, hopperFacing.m_122424_());
    }

    private static boolean isFull(IItemHandler itemHandler) {
        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
            ItemStack stackInSlot = itemHandler.getStackInSlot(slot);
            if (stackInSlot.m_41619_() || stackInSlot.m_41613_() < itemHandler.getSlotLimit(slot))
                return false;
        }
        return true;
    }

    private static boolean isEmpty(IItemHandler itemHandler) {
        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
            ItemStack stackInSlot = itemHandler.getStackInSlot(slot);
            if (stackInSlot.m_41613_() > 0)
                return false;
        }
        return true;
    }

    /**
     * Gets the IItemHandler at the given position, checking for both BlockEntities
     * and Entities. If both are present, the BlockEntity is preferred.
     *
     * This is equivalent to HopperBlockEntity#getContainerAt
     *
     * @param level The level to check
     * @param pos   The position to check
     * @param side  The side to check from. Can be null.
     * @return An IItemHandler and the object it was obtained from, or an empty
     *         Optional if none was found.
     */
    public static Optional<IItemHandler> getItemHandler(Level level, BlockPos pos, @Nullable Direction side) {
        var handler = getItemHandlerBlock(level, pos, side);
        if (!handler.isPresent())
            handler = getItemHandlerEntity(level, pos, side);
        return handler;
    }

    public static Optional<IItemHandler> getItemHandlerEntity(Level level, BlockPos pos, @Nullable Direction side) {
        var entities = level.m_6249_((Entity)null, new AABB(pos), e -> e.getCapability(ForgeCapabilities.ITEM_HANDLER, side).isPresent());

        if (entities.isEmpty())
            return Optional.empty();

        var rand = level.f_46441_.m_188503_(entities.size());
        var entity = entities.get(rand);
        return entity.getCapability(ForgeCapabilities.ITEM_HANDLER, side).resolve();
    }

    public static Optional<IItemHandler> getItemHandlerBlock(Level level, BlockPos pos, @Nullable Direction side) {
        if (!level.m_8055_(pos).m_155947_())
            return Optional.empty();

        var entity = level.m_7702_(pos);
        if (entity == null)
            return Optional.empty();

        return entity.getCapability(ForgeCapabilities.ITEM_HANDLER, side).resolve();
    }

    /**
     *  I don't think anyone should be using this. If they are they probably want the non-pair version.
     *  As the only use for knowing the object holding the IItemHandler is for hopper cooldowns
     */
    @Deprecated(forRemoval = true, since = "1.21.8")
    public static Optional<Pair<IItemHandler, Object>> getItemHandler(Level worldIn, double x, double y, double z, final Direction side) {
        int i = Mth.m_14107_(x);
        int j = Mth.m_14107_(y);
        int k = Mth.m_14107_(z);
        return getItemHandlerPair(worldIn, new BlockPos(i, j, k), side);
    }

    private static Optional<Pair<IItemHandler, Object>> getItemHandlerPair(Level level, BlockPos pos, Direction side) {
        var state = level.m_8055_(pos);

        if (state.m_155947_()) {
            BlockEntity blockEntity = level.m_7702_(pos);
            if (blockEntity != null) {
                return blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, side)
                    .map(capability -> ImmutablePair.<IItemHandler, Object>of(capability, blockEntity));
            }
        }

        return Optional.empty();
    }
}
