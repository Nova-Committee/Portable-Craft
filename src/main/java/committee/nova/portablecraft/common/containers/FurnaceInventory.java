package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.containers.base.AbstractFurnaceInventory;
import committee.nova.portablecraft.common.menus.FurnaceContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 13:21
 * Version: 1.0
 */
public class FurnaceInventory extends AbstractFurnaceInventory {

    public FurnaceInventory(CompoundTag nbt) {
        super(RecipeType.SMELTING, nbt, new TranslatableComponent("item.portablecraft.furnace1"));
    }

    public FurnaceInventory() {
        super(RecipeType.SMELTING, new TranslatableComponent("item.portablecraft.furnace1"));
    }

    @Override
    protected AbstractContainerMenu createMenu(int pId, Inventory pPlayer) {
        return new FurnaceContainer(pId, pPlayer, this, this.dataAccess);
    }
}
