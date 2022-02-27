package committee.nova.portablecraft.common.slots;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 8:39
 * Version: 1.0
 */
public class EnchantSlot extends SlotItemHandler {
    public EnchantSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    public void setChanged() {
        super.setChanged();
    }

    public int getMaxStackSize() {
        return 1;
    }

    public boolean mayPlace(ItemStack stack) {
        return stack.isEnchantable() || stack.isEnchanted() || stack.getItem() == Items.ENCHANTED_BOOK;
    }
}
