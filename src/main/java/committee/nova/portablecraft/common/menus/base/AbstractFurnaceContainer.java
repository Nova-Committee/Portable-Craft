package committee.nova.portablecraft.common.menus.base;

import committee.nova.portablecraft.common.slots.FurnaceFuelSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 7:36
 * Version: 1.0
 */
public abstract class AbstractFurnaceContainer extends RecipeBookMenu<Container> {

    protected final Level level;
    private final ContainerData data;
    private final Container inventory;
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;
    private final RecipeBookType recipeBookType;


    public AbstractFurnaceContainer(MenuType<?> pMenuType, RecipeType<? extends AbstractCookingRecipe> pRecipeType, RecipeBookType pRecipeBookType, int pContainerId, Inventory playerInventory) {
        this(pMenuType, pRecipeType, pRecipeBookType, pContainerId, playerInventory, new SimpleContainer(3), new SimpleContainerData(4));
    }

    public AbstractFurnaceContainer(MenuType<?> pMenuType, RecipeType<? extends AbstractCookingRecipe> pRecipeType, RecipeBookType pRecipeBookType, int pContainerId, Inventory playerInventory, Container furnaceInventory, ContainerData data) {
        super(pMenuType, pContainerId);
        this.recipeType = pRecipeType;
        this.recipeBookType = pRecipeBookType;
        checkContainerSize(furnaceInventory, 3);
        checkContainerDataCount(data, 4);
        this.data = data;
        this.level = playerInventory.player.level;
        this.inventory = furnaceInventory;

        this.addSlot(new Slot(furnaceInventory, 0, 56, 17));
        this.addSlot(new FurnaceFuelSlot(this, furnaceInventory, 1, 56, 53));
        this.addSlot(new FurnaceResultSlot(playerInventory.player, furnaceInventory, 2, 116, 35));


        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlots(data);

    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents p_38976_) {
        if (this.inventory instanceof StackedContentsCompatible) {
            ((StackedContentsCompatible) this.inventory).fillStackedContents(p_38976_);
        }

    }


    @Override
    public void clearCraftingContent() {
        this.getSlot(0).set(ItemStack.EMPTY);
        this.getSlot(2).set(ItemStack.EMPTY);
    }


    @Override
    public boolean recipeMatches(Recipe<? super Container> p_38980_) {
        return p_38980_.matches(this.inventory, this.level);
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
    public boolean stillValid(Player pPlayer) {
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
    public RecipeBookType getRecipeBookType() {
        return this.recipeBookType;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int pIndex) {
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
        return this.level.getRecipeManager().getRecipeFor((RecipeType) this.recipeType, new SimpleContainer(pStack), this.level).isPresent();
    }

    public boolean isFuel(ItemStack pStack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(pStack, this.recipeType) > 0;
    }


    @Override
    public boolean shouldMoveToInventory(int p_150553_) {
        return p_150553_ != this.getResultSlotIndex();
    }

}
