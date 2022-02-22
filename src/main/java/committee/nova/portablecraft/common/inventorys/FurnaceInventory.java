package committee.nova.portablecraft.common.inventorys;

import committee.nova.portablecraft.common.containers.FurnaceContainer;
import committee.nova.portablecraft.common.inventorys.base.AbstractFurnaceInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 13:21
 * Version: 1.0
 */
public class FurnaceInventory extends AbstractFurnaceInventory {

    public FurnaceInventory(CompoundNBT nbt) {
        super(IRecipeType.SMELTING, nbt, new TranslationTextComponent("item.portablecraft.furnace1"));
    }

    public FurnaceInventory() {
        super(IRecipeType.SMELTING, new TranslationTextComponent("item.portablecraft.furnace1"));
    }

    @Override
    protected Container createMenu(int pId, PlayerInventory pPlayer) {
        return new FurnaceContainer(pId, pPlayer, this, this.dataAccess);
    }
}
