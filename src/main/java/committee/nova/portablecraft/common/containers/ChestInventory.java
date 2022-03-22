package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.menus.ChestContainer;
import committee.nova.portablecraft.core.WorldSaveInventory;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/21 13:27
 * Version: 1.0
 */
public class ChestInventory extends SimpleContainer implements MenuProvider {

    private static final BaseComponent CONTAINER_TITLE = new TranslatableComponent("item.portablecraft.chest1");
    protected NonNullList<ItemStack> items = NonNullList.withSize(9 * 6, ItemStack.EMPTY);
    private int inventoryNr;


    public ChestInventory(CompoundTag nbt) {
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
        this.setChanged();
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
        return new ChestContainer(ModContainers.GENERIC_9x6, p_createMenu_1_, p_createMenu_2_, this, 6);
    }


    @Override
    public BaseComponent getDisplayName() {
        return CONTAINER_TITLE;
    }

    public void load(CompoundTag pCompound) {
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pCompound, this.items);

        this.inventoryNr = pCompound.getInt("InventoryNr");

    }

    public CompoundTag save(CompoundTag pCompound) {

        ContainerHelper.saveAllItems(pCompound, this.items);
        pCompound.putInt("InventoryNr", this.inventoryNr);

        return pCompound;
    }

    @Override
    public void setChanged() {
        List<ContainerListener> changedListeners = ObfuscationReflectionHelper.getPrivateValue(SimpleContainer.class, this, "f_100398_");
        if (changedListeners != null) {
            for (ContainerListener iinventorychangedlistener : changedListeners) {
                iinventorychangedlistener.containerChanged(this);
            }
            WorldSaveInventory.getInstance().setDirty();
        }
        WorldSaveInventory.getInstance().setDirty();

    }

    @Override
    public int getContainerSize() {
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
    public ItemStack getItem(int index) {
        return index >= 0 && index < this.items.size() ? this.items.get(index) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        ItemStack itemStack = ContainerHelper.removeItem(this.items, pIndex, pCount);
        if (!itemStack.isEmpty()) {
            this.setChanged();
        }
        return itemStack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        ItemStack itemstack = this.items.get(pIndex);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.items.set(pIndex, ItemStack.EMPTY);
            return itemstack;
        }
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        this.items.set(pIndex, pStack);
        if (!pStack.isEmpty() && pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

}
