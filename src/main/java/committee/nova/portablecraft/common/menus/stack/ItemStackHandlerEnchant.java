package committee.nova.portablecraft.common.menus.stack;

import committee.nova.portablecraft.common.menus.EnchantmentEditContainer;
import net.minecraft.world.item.ItemStack;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 8:25
 * Version: 1.0
 */
public class ItemStackHandlerEnchant extends ItemStackHandlerBasic {
    public ItemStackHandlerEnchant(EnchantmentEditContainer container) {
        super(1);
        this.container = container;
    }

    public ItemStack getEnchantingStack() {
        return this.getStackInSlot(0);
    }
}
