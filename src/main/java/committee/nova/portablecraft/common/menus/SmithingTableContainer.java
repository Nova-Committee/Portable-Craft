package committee.nova.portablecraft.common.menus;

import committee.nova.portablecraft.common.menus.base.AbstractRepairedContainer;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 21:06
 * Version: 1.0
 */
public class SmithingTableContainer extends AbstractRepairedContainer {

    private final Level level;
    private final List<UpgradeRecipe> recipes;
    @Nullable
    private UpgradeRecipe selectedRecipe;


    public SmithingTableContainer(int pContainerId, Inventory Inventory) {
        super(ModContainers.SMITHING, pContainerId, Inventory);
        this.level = Inventory.player.level;
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(RecipeType.SMITHING);
    }

    public static SmithingTableContainer create(int pContainerId, Inventory Inventory, FriendlyByteBuf buffer) {
        return new SmithingTableContainer(pContainerId, Inventory);
    }

    @Override
    protected boolean mayPickup(Player pPlayer, boolean pHasStack) {
        return this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level);
    }

    @Override
    protected void onTake(Player pPlayer, ItemStack pInputItem) {
        pInputItem.onCraftedBy(pPlayer.level, pPlayer, pInputItem.getCount());
        this.resultSlots.awardUsedRecipes(pPlayer);
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
    }

    private void shrinkStackInSlot(int pIndex) {
        ItemStack itemstack = this.inputSlots.getItem(pIndex);
        itemstack.shrink(1);
        this.inputSlots.setItem(pIndex, itemstack);
    }


    @Override
    public void createResult() {
        List<UpgradeRecipe> list = this.level.getRecipeManager().getRecipesFor(RecipeType.SMITHING, this.inputSlots, this.level);
        if (list.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            this.selectedRecipe = list.get(0);
            ItemStack itemstack = this.selectedRecipe.assemble(this.inputSlots);
            this.resultSlots.setRecipeUsed(this.selectedRecipe);
            this.resultSlots.setItem(0, itemstack);
        }

    }

    @Override
    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack pStack) {
        return this.recipes.stream().anyMatch((p_241444_1_) -> {
            return p_241444_1_.isAdditionIngredient(pStack);
        });
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.container != this.resultSlots && super.canTakeItemForPickAll(pStack, pSlot);
    }
}
