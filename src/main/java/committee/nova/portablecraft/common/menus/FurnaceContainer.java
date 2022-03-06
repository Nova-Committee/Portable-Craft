package committee.nova.portablecraft.common.menus;

import committee.nova.portablecraft.common.menus.base.AbstractFurnaceContainer;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 13:40
 * Version: 1.0
 */
public class FurnaceContainer extends AbstractFurnaceContainer {

    public FurnaceContainer(int pContainerId, Inventory pPlayerInventory) {
        super(ModContainers.FURNACE, RecipeType.SMELTING, RecipeBookType.FURNACE, pContainerId, pPlayerInventory);
    }

    public FurnaceContainer(int pContainerId, Inventory pPlayerInventory, Container pFurnaceContainer, ContainerData pFurnaceData) {
        super(ModContainers.FURNACE, RecipeType.SMELTING, RecipeBookType.FURNACE, pContainerId, pPlayerInventory, pFurnaceContainer, pFurnaceData);
    }

    public static FurnaceContainer create(int pContainerId, Inventory playerInventory, FriendlyByteBuf buffer) {
        return new FurnaceContainer(pContainerId, playerInventory);
    }


}
