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
 * Date: 2022/2/20 13:40
 * Version: 1.0
 */
public class FurnaceContainer extends AbstractFurnaceContainer {

    public FurnaceContainer(int pContainerId, PlayerInventory pPlayerInventory) {
        super(ModContainers.FURNACE,IRecipeType.SMELTING, RecipeBookCategory.FURNACE, pContainerId, pPlayerInventory);
    }

    public FurnaceContainer(int pContainerId, PlayerInventory pPlayerInventory, IInventory pFurnaceContainer, IIntArray pFurnaceData) {
        super(ModContainers.FURNACE, IRecipeType.SMELTING, RecipeBookCategory.FURNACE, pContainerId, pPlayerInventory, pFurnaceContainer, pFurnaceData);
    }

    public static FurnaceContainer create(int pContainerId, PlayerInventory playerInventory, PacketBuffer buffer){
        return new FurnaceContainer(pContainerId, playerInventory);
    }


}
