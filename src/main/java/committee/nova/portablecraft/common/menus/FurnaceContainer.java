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
 * Date: 2022/3/12 19:30
 * Version: 1.0
 */
public class FurnaceContainer extends AbstractFurnaceContainer {
    public FurnaceContainer(int syncId, PlayerInventory playerInventory) {
        super(ModContainers.FURNACE, RecipeType.SMELTING, RecipeBookCategory.FURNACE, syncId, playerInventory);
    }

    public FurnaceContainer(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModContainers.FURNACE, RecipeType.SMELTING, RecipeBookCategory.FURNACE, syncId, playerInventory, inventory, propertyDelegate);
    }
}
