package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.containers.base.AbstractFurnaceInventory;
import committee.nova.portablecraft.common.menus.SmokerContainer;
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
public class SmokerInventory extends AbstractFurnaceInventory {

    public SmokerInventory(CompoundTag nbt) {
        super(RecipeType.SMOKING, nbt, new TranslatableComponent("item.portablecraft.smoker1"));
    }

    public SmokerInventory() {
        super(RecipeType.SMOKING, new TranslatableComponent("item.portablecraft.smoker1"));
    }


    @Override
    protected AbstractContainerMenu createMenu(int pId, Inventory pPlayer) {
        return new SmokerContainer(pId, pPlayer, this, this.dataAccess);
    }
}
