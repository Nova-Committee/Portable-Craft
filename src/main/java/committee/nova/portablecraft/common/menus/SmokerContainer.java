package committee.nova.portablecraft.common.menus;

import committee.nova.portablecraft.common.menus.base.AbstractFurnaceContainer;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.PropertyDelegate;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/12 21:05
 * Version: 1.0
 */
public class SmokerContainer extends AbstractFurnaceContainer {
    public SmokerContainer(int pContainerId, PlayerInventory playerInventory) {
        super(ModContainers.SMOKER, RecipeType.SMOKING, RecipeBookCategory.SMOKER, pContainerId, playerInventory);
    }

    public SmokerContainer(int pContainerId, PlayerInventory playerInventory, Inventory furnaceInventory, PropertyDelegate data) {
        super(ModContainers.SMOKER, RecipeType.SMOKING, RecipeBookCategory.SMOKER, pContainerId, playerInventory, furnaceInventory, data);
    }
}
