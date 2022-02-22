package committee.nova.portablecraft.common.containers;

import com.google.common.collect.Lists;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.StonecuttingRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/22 11:55
 * Version: 1.0
 */
public class StonecutterContainer extends Container {
    final Slot inputSlot;
    final Slot resultSlot;
    private final IntReferenceHolder selectedRecipeIndex = IntReferenceHolder.standalone();
    private final World level;
    private final CraftResultInventory resultContainer = new CraftResultInventory();
    private List<StonecuttingRecipe> recipes = Lists.newArrayList();
    private ItemStack input = ItemStack.EMPTY;
    private Runnable slotUpdateListener = () -> {
    };
    public final IInventory container = new Inventory(1) {

        public void setChanged() {
            super.setChanged();
            StonecutterContainer.this.slotsChanged(this);
            StonecutterContainer.this.slotUpdateListener.run();
        }
    };

    public StonecutterContainer(int pContainerId, PlayerInventory pPlayerInventory) {
        super(ModContainers.STONECUTTER, pContainerId);
        this.level = pPlayerInventory.player.level;
        this.inputSlot = this.addSlot(new Slot(this.container, 0, 20, 33));
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 1, 143, 33) {

            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            public ItemStack onTake(PlayerEntity pPlayer, ItemStack pStack) {
                pStack.onCraftedBy(pPlayer.level, pPlayer, pStack.getCount());
                resultContainer.awardUsedRecipes(pPlayer);
                ItemStack itemstack = inputSlot.remove(1);
                if (!itemstack.isEmpty()) {
                    setupResultSlot();
                }


                return super.onTake(pPlayer, pStack);
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlot(this.selectedRecipeIndex);
    }

    public static StonecutterContainer create(int pContainerId, PlayerInventory playerInventory, PacketBuffer buffer){
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
    public List<StonecuttingRecipe> getRecipes() {
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
    public boolean stillValid(PlayerEntity pPlayer) {
        return true;
    }

    @Override
    public boolean clickMenuButton(PlayerEntity pPlayer, int pId) {
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
    public void slotsChanged(IInventory pInventory) {
        ItemStack itemstack = this.inputSlot.getItem();
        if (itemstack.getItem() != this.input.getItem()) {
            this.input = itemstack.copy();
            this.setupRecipeList(pInventory, itemstack);
        }

    }

    private void setupRecipeList(IInventory pInventory, ItemStack pStack) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (!pStack.isEmpty()) {
            this.recipes = this.level.getRecipeManager().getRecipesFor(IRecipeType.STONECUTTING, pInventory, this.level);
        }

    }

    private void setupResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
            StonecuttingRecipe stonecuttingrecipe = this.recipes.get(this.selectedRecipeIndex.get());
            this.resultContainer.setRecipeUsed(stonecuttingrecipe);
            this.resultSlot.set(stonecuttingrecipe.assemble(this.container));
        } else {
            this.resultSlot.set(ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }

    public ContainerType<?> getType() {
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
    public ItemStack quickMoveStack(PlayerEntity pPlayer, int pIndex) {
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
            } else if (this.level.getRecipeManager().getRecipeFor(IRecipeType.STONECUTTING, new Inventory(itemstack1), this.level).isPresent()) {
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
    public void removed(PlayerEntity pPlayer) {
        super.removed(pPlayer);
        this.resultContainer.removeItemNoUpdate(1);
        this.clearContainer(pPlayer, pPlayer.level, this.container);

    }
}
