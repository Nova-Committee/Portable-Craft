package committee.nova.portablecraft.common.containers.stack;

import committee.nova.portablecraft.common.containers.EnchantmentEditContainer;
import net.minecraft.item.ItemStack;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 8:24
 * Version: 1.0
 */
public class ItemStackHandlerBook extends ItemStackHandlerBasic {
    public ItemStackHandlerBook(EnchantmentEditContainer container) {
        super(1);
        this.container = container;
    }

    public ItemStack getEnchantedBook() {
        return this.getStackInSlot(0);
    }
}
