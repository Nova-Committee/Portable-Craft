package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.containers.base.AbstractFurnaceContainer;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 20:23
 * Version: 1.0
 */
public class BlastFurnaceContainer extends AbstractFurnaceContainer {
    public BlastFurnaceContainer( int pContainerId, PlayerInventory playerInventory) {
        super(ModContainers.BLAST_FURNACE, IRecipeType.BLASTING, RecipeBookCategory.BLAST_FURNACE, pContainerId, playerInventory);
    }

    public BlastFurnaceContainer( int pContainerId, PlayerInventory playerInventory, IInventory furnaceInventory, IIntArray data) {
        super(ModContainers.BLAST_FURNACE, IRecipeType.BLASTING, RecipeBookCategory.BLAST_FURNACE, pContainerId, playerInventory, furnaceInventory, data);
    }

    public static BlastFurnaceContainer create(int pContainerId, PlayerInventory playerInventory, PacketBuffer buffer){
        return new BlastFurnaceContainer(pContainerId, playerInventory);
    }
}
