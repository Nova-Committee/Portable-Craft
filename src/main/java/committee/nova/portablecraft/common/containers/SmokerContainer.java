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
 * Date: 2022/2/20 19:56
 * Version: 1.0
 */
public class SmokerContainer extends AbstractFurnaceContainer {
    public SmokerContainer( int pContainerId, PlayerInventory playerInventory) {
        super(ModContainers.SMOKER, IRecipeType.SMOKING, RecipeBookCategory.SMOKER , pContainerId, playerInventory);
    }

    public SmokerContainer(int pContainerId, PlayerInventory playerInventory, IInventory furnaceInventory, IIntArray data) {
        super(ModContainers.SMOKER, IRecipeType.SMOKING, RecipeBookCategory.SMOKER , pContainerId, playerInventory, furnaceInventory, data);
    }

    public static SmokerContainer create(int pContainerId, PlayerInventory playerInventory, PacketBuffer buffer){
        return new SmokerContainer(pContainerId, playerInventory);
    }
}
