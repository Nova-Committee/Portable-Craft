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
 * Date: 2022/2/20 19:56
 * Version: 1.0
 */
public class SmokerContainer extends AbstractFurnaceContainer {
    public SmokerContainer(int pContainerId, Inventory playerInventory) {
        super(ModContainers.SMOKER, RecipeType.SMOKING, RecipeBookType.SMOKER, pContainerId, playerInventory);
    }

    public SmokerContainer(int pContainerId, Inventory playerInventory, Container furnaceInventory, ContainerData data) {
        super(ModContainers.SMOKER, RecipeType.SMOKING, RecipeBookType.SMOKER, pContainerId, playerInventory, furnaceInventory, data);
    }

    public static SmokerContainer create(int pContainerId, Inventory playerInventory, FriendlyByteBuf buffer) {
        return new SmokerContainer(pContainerId, playerInventory);
    }
}
