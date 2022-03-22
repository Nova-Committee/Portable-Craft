package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.menus.EnchantmentContainer;
import committee.nova.portablecraft.core.WorldSaveInventory;
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
 * Date: 2022/2/21 21:20
 * Version: 1.0
 */
public class EnchantmentInventory extends SimpleContainer implements MenuProvider {
    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int inventoryNr;

    private EnchantmentContainer enchantCont;


    public EnchantmentInventory(CompoundTag nbt) {
        this.load(nbt);
    }

    public EnchantmentInventory() {
    }

    public int getInventoryNr() {
        return this.inventoryNr;
    }

    public void setInventoryNr(int inventoryNr) {
        this.inventoryNr = inventoryNr;
        this.setChanged();
    }

    public int getContainerSize() {
        return this.items.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }


    public void load(CompoundTag p_230337_2_) {
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(p_230337_2_, this.items);
        this.inventoryNr = p_230337_2_.getInt("InventoryNr");

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
        if (this.enchantCont != null) {
            this.enchantCont.slotsChanged(this);
        }
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return pIndex >= 0 && pIndex < this.items.size() ? this.items.get(pIndex) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        ItemStack itemstack = ContainerHelper.removeItem(this.items, pIndex, pCount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {

        ItemStack itemstack = ContainerHelper.takeItem(this.items, pIndex);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.items.set(pIndex, ItemStack.EMPTY);
            return itemstack;
        }
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        if (pIndex >= 0 && pIndex < this.items.size()) {
            this.items.set(pIndex, pStack);
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

    @Override
    public BaseComponent getDisplayName() {
        return new TranslatableComponent("container.enchant");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
        return new EnchantmentContainer(p_createMenu_1_, p_createMenu_2_);
    }

    public void resetContainer() {
        this.enchantCont = null;
    }
}
