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
 * Date: 2022/2/20 20:23
 * Version: 1.0
 */
public class BlastFurnaceContainer extends AbstractFurnaceContainer {
    public BlastFurnaceContainer(int pContainerId, Inventory playerInventory) {
        super(ModContainers.BLAST_FURNACE, RecipeType.BLASTING, RecipeBookType.BLAST_FURNACE, pContainerId, playerInventory);
    }

    public BlastFurnaceContainer(int pContainerId, Inventory playerInventory, Container furnaceInventory, ContainerData data) {
        super(ModContainers.BLAST_FURNACE, RecipeType.BLASTING, RecipeBookType.BLAST_FURNACE, pContainerId, playerInventory, furnaceInventory, data);
    }

    public static BlastFurnaceContainer create(int pContainerId, Inventory playerInventory, FriendlyByteBuf buffer) {
        return new BlastFurnaceContainer(pContainerId, playerInventory);
    }
}
