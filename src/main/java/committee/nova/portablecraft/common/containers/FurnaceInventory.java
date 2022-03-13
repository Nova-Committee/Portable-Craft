package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.containers.base.AbstractFurnaceInventory;
import committee.nova.portablecraft.common.menus.FurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.TranslatableText;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/12 21:00
 * Version: 1.0
 */
public class FurnaceInventory extends AbstractFurnaceInventory {
    public FurnaceInventory(NbtCompound nbt) {
        super(RecipeType.SMELTING, nbt, new TranslatableText("item.portablecraft.furnace1"));
    }

    public FurnaceInventory() {
        super(RecipeType.SMELTING, new TranslatableText("item.portablecraft.furnace1"));
    }

    @Override
    protected ScreenHandler createMenu(int pId, PlayerInventory pPlayer) {
        return new FurnaceContainer(pId, pPlayer, this, this.propertyDelegate);
    }
}
