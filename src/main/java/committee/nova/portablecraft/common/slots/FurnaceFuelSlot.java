package committee.nova.portablecraft.common.slots;

import committee.nova.portablecraft.common.menus.base.AbstractFurnaceContainer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/12 19:28
 * Version: 1.0
 */
public class FurnaceFuelSlot extends Slot {
    private final AbstractFurnaceContainer handler;

    public FurnaceFuelSlot(AbstractFurnaceContainer handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.isOf(Items.BUCKET);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return this.handler.isFuel(stack) || net.minecraft.screen.slot.FurnaceFuelSlot.isBucket(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return net.minecraft.screen.slot.FurnaceFuelSlot.isBucket(stack) ? 1 : super.getMaxItemCount(stack);
    }
}
