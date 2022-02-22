package committee.nova.portablecraft.common.containers.base;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 19:43
 * Version: 1.0
 */
public interface IContainerCraftingAction {

    ItemStack transferStack(PlayerEntity playerIn, int index);

    CraftingInventory getCraftMatrix();

    CraftResultInventory getCraftResult();
}
