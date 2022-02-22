package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.containers.base.AbstractRepairedContainer;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 21:06
 * Version: 1.0
 */
public class SmithingTableContainer extends AbstractRepairedContainer {

    private final World level;
    private final List<SmithingRecipe> recipes;
    @Nullable
    private SmithingRecipe selectedRecipe;



    public SmithingTableContainer( int pContainerId, PlayerInventory playerInventory) {
        super(ModContainers.SMITHING, pContainerId, playerInventory);
        this.level = playerInventory.player.level;
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(IRecipeType.SMITHING);
    }

    public static SmithingTableContainer create(int pContainerId, PlayerInventory playerInventory, PacketBuffer buffer){
       return new SmithingTableContainer(pContainerId, playerInventory);
    }

    @Override
    protected boolean isValidBlock(BlockState pState) {
        return pState.is(Blocks.SMITHING_TABLE);
    }
    @Override
    protected boolean mayPickup(PlayerEntity pPlayer, boolean pHasStack) {
        return this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level);
    }
    @Override
    protected ItemStack onTake(PlayerEntity pPlayer, ItemStack pInputItem) {
        pInputItem.onCraftedBy(pPlayer.level, pPlayer, pInputItem.getCount());
        this.resultSlots.awardUsedRecipes(pPlayer);
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        return pInputItem;
    }

    private void shrinkStackInSlot(int pIndex) {
        ItemStack itemstack = this.inputSlots.getItem(pIndex);
        itemstack.shrink(1);
        this.inputSlots.setItem(pIndex, itemstack);
    }


    @Override
    public void createResult() {
        List<SmithingRecipe> list = this.level.getRecipeManager().getRecipesFor(IRecipeType.SMITHING, this.inputSlots, this.level);
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
