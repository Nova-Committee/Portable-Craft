package committee.nova.portablecraft.common.menus.base;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 19:43
 * Version: 1.0
 */
public interface IContainerCraftingAction {

    ItemStack transferStack(Player playerIn, int index);

    CraftingContainer getCraftMatrix();

    ResultContainer getCraftResult();
}
