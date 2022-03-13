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
 * Date: 2022/3/12 21:08
 * Version: 1.0
 */
public class BlastFurnaceContainer extends AbstractFurnaceContainer {
    public BlastFurnaceContainer(int pContainerId, PlayerInventory playerInventory) {
        super(ModContainers.BLAST_FURNACE, RecipeType.BLASTING, RecipeBookCategory.BLAST_FURNACE, pContainerId, playerInventory);
    }

    public BlastFurnaceContainer(int pContainerId, PlayerInventory playerInventory, Inventory furnaceInventory, PropertyDelegate data) {
        super(ModContainers.BLAST_FURNACE, RecipeType.BLASTING, RecipeBookCategory.BLAST_FURNACE, pContainerId, playerInventory, furnaceInventory, data);
    }
}
