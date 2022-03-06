package committee.nova.portablecraft.common.menus;

import com.google.common.collect.Lists;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/22 11:55
 * Version: 1.0
 */
public class StonecutterContainer extends AbstractContainerMenu {
    final Slot inputSlot;
    final Slot resultSlot;
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private final Level level;
    private final ResultContainer resultContainer = new ResultContainer();
    private List<StonecutterRecipe> recipes = Lists.newArrayList();
    private ItemStack input = ItemStack.EMPTY;
    private Runnable slotUpdateListener = () -> {
    };
    public final Container container = new SimpleContainer(1) {

        public void setChanged() {
            super.setChanged();
            StonecutterContainer.this.slotsChanged(this);
            StonecutterContainer.this.slotUpdateListener.run();
        }
    };

    public StonecutterContainer(int pContainerId, Inventory pPlayerInventory) {
        super(ModContainers.STONECUTTER, pContainerId);
        this.level = pPlayerInventory.player.level;
        this.inputSlot = this.addSlot(new Slot(this.container, 0, 20, 33));
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 1, 143, 33) {

            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            public void onTake(Player pPlayer, ItemStack pStack) {
                pStack.onCraftedBy(pPlayer.level, pPlayer, pStack.getCount());
                resultContainer.awardUsedRecipes(pPlayer);
                ItemStack itemstack = inputSlot.remove(1);
                if (!itemstack.isEmpty()) {
                    setupResultSlot();
                }


                super.onTake(pPlayer, pStack);
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlot(this.selectedRecipeIndex);
    }

    public static StonecutterContainer create(int pContainerId, Inventory playerInventory, FriendlyByteBuf buffer) {
        return new StonecutterContainer(pContainerId, playerInventory);
    }

    /**
     * Returns the index of the selected recipe.
     */
    @OnlyIn(Dist.CLIENT)
    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }

    @OnlyIn(Dist.CLIENT)
    public List<StonecutterRecipe> getRecipes() {
        return this.recipes;
    }

    @OnlyIn(Dist.CLIENT)
    public int getNumRecipes() {
        return this.recipes.size();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasInputItem() {
        return this.inputSlot.hasItem() && !this.recipes.isEmpty();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public boolean clickMenuButton(Player pPlayer, int pId) {
        if (this.isValidRecipeIndex(pId)) {
            this.selectedRecipeIndex.set(pId);
            this.setupResultSlot();
        }

        return true;
    }

    private boolean isValidRecipeIndex(int pRecipeIndex) {
        return pRecipeIndex >= 0 && pRecipeIndex < this.recipes.size();
    }

    @Override
    public void slotsChanged(Container pInventory) {
        ItemStack itemstack = this.inputSlot.getItem();
        if (itemstack.getItem() != this.input.getItem()) {
            this.input = itemstack.copy();
            this.setupRecipeList(pInventory, itemstack);
        }

    }

    private void setupRecipeList(Container pInventory, ItemStack pStack) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (!pStack.isEmpty()) {
            this.recipes = this.level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, pInventory, this.level);
        }

    }

    private void setupResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
            StonecutterRecipe stonecuttingrecipe = this.recipes.get(this.selectedRecipeIndex.get());
            this.resultContainer.setRecipeUsed(stonecuttingrecipe);
            this.resultSlot.set(stonecuttingrecipe.assemble(this.container));
        } else {
            this.resultSlot.set(ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }

    public MenuType<?> getType() {
        return ModContainers.STONECUTTER;
    }

    @OnlyIn(Dist.CLIENT)
    public void registerUpdateListener(Runnable pListener) {
        this.slotUpdateListener = pListener;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.container != this.resultContainer && super.canTakeItemForPickAll(pStack, pSlot);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 1) {
                item.onCraftedBy(itemstack1, pPlayer.level, pPlayer);
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.level.getRecipeManager().getRecipeFor(RecipeType.STONECUTTING, new SimpleContainer(itemstack1), this.level).isPresent()) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= 2 && pIndex < 29) {
                if (!this.moveItemStackTo(itemstack1, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= 29 && pIndex < 38 && !this.moveItemStackTo(itemstack1, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
            this.broadcastChanges();
        }

        return itemstack;
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.resultContainer.removeItemNoUpdate(1);
        this.clearContainer(pPlayer, this.container);

    }
}
