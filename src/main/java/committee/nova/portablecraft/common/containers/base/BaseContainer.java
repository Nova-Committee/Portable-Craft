package committee.nova.portablecraft.common.containers.base;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 19:46
 * Version: 1.0
 */
public abstract class BaseContainer extends Container {

    public static final int PLAYERSIZE = 4 * 9;
    protected PlayerEntity playerEntity;
    protected PlayerInventory playerInventory;
    protected int startInv = 0;
    protected int endInv = 17; //must be set by extending class

    protected BaseContainer(@Nullable ContainerType<?> pMenuType, int pContainerId) {
        super(pMenuType, pContainerId);
    }

    @Override
    public boolean stillValid(PlayerEntity pPlayer) {
        return false;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        try {
            //if last machine slot is 17, endInv is 18
            int playerStart = endInv;
            int playerEnd = endInv + PLAYERSIZE; //53 = 17 + 36
            //standard logic based on start/end
            ItemStack itemstack = ItemStack.EMPTY;
            Slot slot = this.slots.get(index);
            if (slot != null && slot.hasItem()) {
                ItemStack stack = slot.getItem();
                itemstack = stack.copy();
                if (index < this.endInv) {
                    if (!this.moveItemStackTo(stack, playerStart, playerEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index <= playerEnd && !this.moveItemStackTo(stack, startInv, endInv, false)) {
                    return ItemStack.EMPTY;
                }
                if (stack.isEmpty()) {
                    slot.set(ItemStack.EMPTY);
                }
                else {
                    slot.setChanged();
                }
                if (stack.getCount() == itemstack.getCount()) {
                    return ItemStack.EMPTY;
                }
                slot.onTake(player, stack);
            }
            return itemstack;
        }
        catch (Exception e) {
            return ItemStack.EMPTY;
        }
    }

    private int addSlotRange(PlayerInventory handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(PlayerInventory handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    protected void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

}
