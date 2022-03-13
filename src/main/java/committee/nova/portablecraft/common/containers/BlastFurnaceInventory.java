package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.containers.base.AbstractFurnaceInventory;
import committee.nova.portablecraft.common.menus.BlastFurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.TranslatableText;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/12 21:09
 * Version: 1.0
 */
public class BlastFurnaceInventory extends AbstractFurnaceInventory {
    public BlastFurnaceInventory(NbtCompound nbt) {
        super(RecipeType.BLASTING, nbt, new TranslatableText("item.portablecraft.blast_furnace1"));
    }

    public BlastFurnaceInventory() {
        super(RecipeType.BLASTING, new TranslatableText("item.portablecraft.blast_furnace1"));
    }

    @Override
    protected ScreenHandler createMenu(int pId, PlayerInventory pPlayer) {
        return new BlastFurnaceContainer(pId, pPlayer, this, this.propertyDelegate);
    }
}
