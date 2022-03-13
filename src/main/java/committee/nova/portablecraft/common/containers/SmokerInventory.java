package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.containers.base.AbstractFurnaceInventory;
import committee.nova.portablecraft.common.menus.SmokerContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.TranslatableText;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/12 21:04
 * Version: 1.0
 */
public class SmokerInventory extends AbstractFurnaceInventory {

    public SmokerInventory(NbtCompound nbt) {
        super(RecipeType.SMOKING, nbt, new TranslatableText("item.portablecraft.smoker1"));
    }

    public SmokerInventory() {
        super(RecipeType.SMOKING, new TranslatableText("item.portablecraft.smoker1"));
    }

    @Override
    protected ScreenHandler createMenu(int pId, PlayerInventory pPlayer) {
        return new SmokerContainer(pId, pPlayer, this, this.propertyDelegate);
    }
}
