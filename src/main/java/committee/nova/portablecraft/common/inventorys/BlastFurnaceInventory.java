package committee.nova.portablecraft.common.inventorys;

import committee.nova.portablecraft.common.containers.BlastFurnaceContainer;
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
public class BlastFurnaceInventory extends AbstractFurnaceInventory {

    public BlastFurnaceInventory(CompoundNBT nbt) {
        super(IRecipeType.BLASTING, nbt, new TranslationTextComponent("item.portablecraft.blast_furnace1"));
    }

    public BlastFurnaceInventory() {
        super(IRecipeType.BLASTING, new TranslationTextComponent("item.portablecraft.blast_furnace1"));
    }


    @Override
    protected Container createMenu(int pId, PlayerInventory pPlayer) {
        return new BlastFurnaceContainer(pId, pPlayer, this, this.dataAccess);
    }
}
