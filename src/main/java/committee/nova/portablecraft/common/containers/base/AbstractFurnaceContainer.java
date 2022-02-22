package committee.nova.portablecraft.common.containers.base;

import committee.nova.portablecraft.common.slots.FurnaceFuelSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 7:36
 * Version: 1.0
 */
public abstract class AbstractFurnaceContainer extends RecipeBookContainer<IInventory> {

    protected final World level;
    private final IIntArray data;
    private final IInventory inventory;
    private final IRecipeType<? extends AbstractCookingRecipe> recipeType;
    private final RecipeBookCategory recipeBookType;


    public AbstractFurnaceContainer(ContainerType<?> pMenuType, IRecipeType<? extends AbstractCookingRecipe> pRecipeType, RecipeBookCategory pRecipeBookType, int pContainerId, PlayerInventory playerInventory) {
        this(pMenuType, pRecipeType, pRecipeBookType,pContainerId, playerInventory, new Inventory(3), new IntArray(4));
    }

    public AbstractFurnaceContainer(ContainerType<?> pMenuType, IRecipeType<? extends AbstractCookingRecipe> pRecipeType, RecipeBookCategory pRecipeBookType, int pContainerId, PlayerInventory playerInventory, IInventory furnaceInventory, IIntArray data) {
        super(pMenuType, pContainerId);
        this.recipeType = pRecipeType;
        this.recipeBookType = pRecipeBookType;
        checkContainerSize(furnaceInventory, 3);
        checkContainerDataCount(data, 4);
        this.data = data;
        this.level = playerInventory.player.level;
        this.inventory = furnaceInventory;

        this.addSlot(new Slot(furnaceInventory, 0, 56, 17));
        this.addSlot(new FurnaceFuelSlot(this ,furnaceInventory, 1, 56, 53));
        this.addSlot(new FurnaceResultSlot(playerInventory.player, furnaceInventory, 2, 116, 35));


        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlots(data);

    }

    @Override
    public void fillCraftSlotsStackedContents(RecipeItemHelper pItemHelper) {
        if (this.inventory instanceof IRecipeHelperPopulator) {
            ((IRecipeHelperPopulator)this.inventory).fillStackedContents(pItemHelper);
        }

    }

    @Override
    public void clearCraftingContent() {
        this.inventory.clearContent();
    }

    @Override
    public void handlePlacement(boolean pPlaceAll, IRecipe<?> pRecipe, ServerPlayerEntity pPlayer) {
        (new ServerRecipePlacerFurnace<>(this)).recipeClicked(pPlayer, (IRecipe<IInventory>) pRecipe, pPlaceAll);
    }

    @Override
    public boolean recipeMatches(IRecipe<? super IInventory> pRecipe) {
        return pRecipe.matches(this.inventory, this.level);
    }

    @Override
    public int getResultSlotIndex() {
        return 2;
    }

    @Override
    public int getGridWidth() {
        return 1;
    }

    @Override
    public int getGridHeight() {
        return 1;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getSize() {
        return 3;
    }

    @Override
    public boolean stillValid(PlayerEntity pPlayer) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnProgress() {
        int i = this.data.get(2);
        int j = this.data.get(3);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getLitProgress() {
        int i = this.data.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.data.get(0) * 13 / i;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isLit() {
        return this.data.get(0) > 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public RecipeBookCategory getRecipeBookType() {
        return this.recipeBookType;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex != 1 && pIndex != 0) {
                if (this.canSmelt(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= 3 && pIndex < 30) {
                    if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= 30 && pIndex < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    protected boolean canSmelt(ItemStack pStack) {
        return this.level.getRecipeManager().getRecipeFor((IRecipeType)this.recipeType, new Inventory(pStack), this.level).isPresent();
    }

    public boolean isFuel(ItemStack pStack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(pStack, this.recipeType) > 0;
    }

}
