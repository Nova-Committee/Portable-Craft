package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.menus.CraftingContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/13 8:40
 * Version: 1.0
 */
public class CraftingInventory extends SimpleInventory implements NamedScreenHandlerFactory {
    private DefaultedList<ItemStack> stacks;
    private int inventoryNr;


    public CraftingInventory(NbtCompound nbt) {
        this.load(nbt);
    }

    public CraftingInventory() {
        this.stacks = DefaultedList.ofSize(10, ItemStack.EMPTY);
    }

    public int getInventoryNr() {
        return this.inventoryNr;
    }

    public void setInventoryNr(int inventoryNr) {
        this.inventoryNr = inventoryNr;
        this.markDirty();
    }

    public int getContainerSize() {
        return this.stacks.size();
    }

    public void load(NbtCompound p_230337_2_) {
        this.stacks = DefaultedList.ofSize(this.getContainerSize(), ItemStack.EMPTY);
        Inventories.readNbt(p_230337_2_, this.stacks);
        this.inventoryNr = p_230337_2_.getInt("InventoryNr");

    }

    public NbtCompound save(NbtCompound pCompound) {
        Inventories.writeNbt(pCompound, this.stacks);
        pCompound.putInt("InventoryNr", this.inventoryNr);
        return pCompound;
    }

    @Override
    public int size() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.stacks) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot >= this.size()) {
            return ItemStack.EMPTY;
        }
        return this.stacks.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.stacks, slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.stacks, slot, amount);
        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.stacks.set(slot, ItemStack.EMPTY);
            return itemStack;
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot >= 0 && slot < this.stacks.size()) {
            this.stacks.set(slot, stack);
        }

        this.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }


    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        for (ItemStack itemStack : this.stacks) {
            finder.addUnenchantedInput(itemStack);
        }
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("container.crafting");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CraftingContainer(syncId, inv);
    }
}
