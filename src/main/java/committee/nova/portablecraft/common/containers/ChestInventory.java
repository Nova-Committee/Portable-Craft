package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.menus.ChestContainer;
import committee.nova.portablecraft.core.WorldSaveInventory;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/12 21:37
 * Version: 1.0
 */
public class ChestInventory extends SimpleInventory implements NamedScreenHandlerFactory {
    private static final Text CONTAINER_TITLE = new TranslatableText("item.portablecraft.chest1");
    protected DefaultedList<ItemStack> items = DefaultedList.ofSize(9 * 6, ItemStack.EMPTY);
    private int inventoryNr;

    public ChestInventory(NbtCompound nbt) {
        super(9 * 6);
        this.load(nbt);
    }

    public ChestInventory() {
        super(9 * 6);
    }

    public int getInventoryNr() {
        return this.inventoryNr;
    }

    public void setInventoryNr(int inventoryNr) {
        this.inventoryNr = inventoryNr;
        this.markDirty();
    }

    @Override
    public Text getDisplayName() {
        return CONTAINER_TITLE;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ChestContainer(ModContainers.GENERIC_9x6, syncId, inv, this, 6);
    }

    public void load(NbtCompound pCompound) {
        this.items = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(pCompound, this.items);

        this.inventoryNr = pCompound.getInt("InventoryNr");

    }

    public NbtCompound save(NbtCompound pCompound) {

        Inventories.writeNbt(pCompound, this.items);
        pCompound.putInt("InventoryNr", this.inventoryNr);

        return pCompound;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        WorldSaveInventory.getInstance().markDirty();
    }


    @Override
    public int size() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return slot >= 0 && slot < this.items.size() ? this.items.get(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack itemstack = this.items.get(slot);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.items.set(slot, ItemStack.EMPTY);
            return itemstack;
        }
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.items, slot, amount);
        if (!itemStack.isEmpty()) {
            this.markDirty();
        }
        return itemStack;
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.items.set(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }

        this.markDirty();
    }


    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.items.clear();
    }
}
