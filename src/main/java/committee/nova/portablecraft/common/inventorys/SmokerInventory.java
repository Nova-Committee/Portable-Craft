package committee.nova.portablecraft.common.inventorys;

import committee.nova.portablecraft.common.containers.SmokerContainer;
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
public class SmokerInventory extends AbstractFurnaceInventory {

    public SmokerInventory(CompoundNBT nbt) {
        super(IRecipeType.SMOKING, nbt, new TranslationTextComponent("item.portablecraft.smoker1"));
    }

    public SmokerInventory() {
        super(IRecipeType.SMOKING, new TranslationTextComponent("item.portablecraft.smoker1"));
    }


    @Override
    protected Container createMenu(int pId, PlayerInventory pPlayer) {
        return new SmokerContainer(pId, pPlayer, this, this.dataAccess);
    }
}
