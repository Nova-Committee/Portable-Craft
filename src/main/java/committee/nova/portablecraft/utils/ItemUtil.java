package committee.nova.portablecraft.utils;

import net.minecraft.item.ItemStack;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/10 10:48
 * Version: 1.0
 */
public class ItemUtil {


    public static boolean TagEquals(ItemStack stack1, ItemStack stack2) {
        return stack1.hasTag() == stack2.hasTag() && (!stack1.hasTag() && !stack2.hasTag() || stack1.getTag().equals(stack2.getTag()));
    }



}
