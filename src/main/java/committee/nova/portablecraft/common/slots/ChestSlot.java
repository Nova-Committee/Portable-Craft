package committee.nova.portablecraft.common.slots;

import committee.nova.portablecraft.init.ModItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 9:45
 * Version: 1.0
 */
public class ChestSlot extends Slot {

    public ChestSlot(IInventory pFurnaceContainer, int pSlot, int pXPosition, int pYPosition) {
        super(pFurnaceContainer, pSlot, pXPosition, pYPosition);
    }

    public static boolean isChest(ItemStack pStack) {
        return pStack.getItem() != ModItems.Chest1;
    }

    @Override
    public boolean mayPlace(ItemStack pStack) {
        return isChest(pStack);
    }

}
