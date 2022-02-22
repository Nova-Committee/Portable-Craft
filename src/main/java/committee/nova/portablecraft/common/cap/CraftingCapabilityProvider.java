package committee.nova.portablecraft.common.cap;

import committee.nova.portablecraft.common.items.CraftingItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 19:40
 * Version: 1.0
 */
public class CraftingCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {

    private final int slots = 9;
    private final LazyOptional<ItemStackHandler> inventory = LazyOptional.of(() -> new ItemStackHandler(slots) {

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return !(stack.getItem() instanceof CraftingItem) && super.isItemValid(slot, stack);
        }
    });

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (inventory.isPresent()) {
            return inventory.resolve().get().serializeNBT();
        }
        return new CompoundNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        inventory.ifPresent(h -> h.deserializeNBT(nbt));
    }
}
