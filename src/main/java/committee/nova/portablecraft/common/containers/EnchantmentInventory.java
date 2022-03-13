package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.menus.EnchantmentContainer;
import committee.nova.portablecraft.core.WorldSaveInventory;
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
 * Date: 2022/3/12 21:48
 * Version: 1.0
 */
public class EnchantmentInventory extends SimpleInventory implements NamedScreenHandlerFactory {

    private DefaultedList<ItemStack> items = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private int inventoryNr;

    private EnchantmentContainer enchantCont;


    public EnchantmentInventory(NbtCompound nbt) {
        this.load(nbt);
    }

    public EnchantmentInventory() {
    }

    public int getInventoryNr() {
        return this.inventoryNr;
    }

    public void setInventoryNr(int inventoryNr) {
        this.inventoryNr = inventoryNr;
        this.markDirty();
    }

    public int getContainerSize() {
        return this.items.size();
    }


    public void load(NbtCompound p_230337_2_) {
        this.items = DefaultedList.ofSize(this.getContainerSize(), ItemStack.EMPTY);
        Inventories.readNbt(p_230337_2_, this.items);
        this.inventoryNr = p_230337_2_.getInt("InventoryNr");

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
        if (this.enchantCont != null) {
            this.enchantCont.onContentChanged(this);
        }
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
        if (slot >= 0 && slot < this.items.size()) {
            this.items.set(slot, stack);
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

    @Override
    public Text getDisplayName() {
        return new TranslatableText("container.enchant");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new EnchantmentContainer(syncId, inv);
    }
}
