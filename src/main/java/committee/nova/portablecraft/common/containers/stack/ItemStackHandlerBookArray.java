package committee.nova.portablecraft.common.containers.stack;

import committee.nova.portablecraft.common.containers.EnchantmentEditContainer;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 8:24
 * Version: 1.0
 */
public class ItemStackHandlerBookArray extends ItemStackHandlerBasic {
    public ItemStackHandlerBookArray(EnchantmentEditContainer container) {
        super(9);
        this.container = container;
    }
}
