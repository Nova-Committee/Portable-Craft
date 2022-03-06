package committee.nova.portablecraft.common.slots;

import committee.nova.portablecraft.common.menus.base.AbstractFurnaceContainer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 9:45
 * Version: 1.0
 */
public class FurnaceFuelSlot extends Slot {
    private final AbstractFurnaceContainer menu;

    public FurnaceFuelSlot(AbstractFurnaceContainer pFurnaceMenu, Container pFurnaceContainer, int pSlot, int pXPosition, int pYPosition) {
        super(pFurnaceContainer, pSlot, pXPosition, pYPosition);
        this.menu = pFurnaceMenu;
    }

    public static boolean isBucket(ItemStack pStack) {
        return pStack.getItem() == Items.BUCKET;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean mayPlace(ItemStack pStack) {
        return this.menu.isFuel(pStack) || isBucket(pStack);
    }

    public int getMaxStackSize(ItemStack pStack) {
        return isBucket(pStack) ? 1 : super.getMaxStackSize(pStack);
    }
}
