package committee.nova.portablecraft.common.containers.stack;

import committee.nova.portablecraft.common.containers.EnchantmentEditContainer;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 8:23
 * Version: 1.0
 */
public abstract class ItemStackHandlerBasic extends ItemStackHandler {
    protected EnchantmentEditContainer container;

    public ItemStackHandlerBasic(int size) {
        super(size);
    }

    public ItemStackHandlerBasic() {
    }

    public int getSlotLimit(int slot) {
        return 1;
    }

    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        this.container.onCraftMatrixChanged(this);
    }
}
