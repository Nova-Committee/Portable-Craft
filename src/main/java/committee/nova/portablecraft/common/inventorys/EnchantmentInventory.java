package committee.nova.portablecraft.common.inventorys;

import committee.nova.portablecraft.common.containers.EnchantmentContainer;
import committee.nova.portablecraft.core.WorldSaveInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/21 21:20
 * Version: 1.0
 */
public class EnchantmentInventory extends Inventory implements INamedContainerProvider {
    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int inventoryNr;

    private EnchantmentContainer enchantCont;



    public EnchantmentInventory(CompoundNBT nbt){
        this.load(nbt);
    }

    public EnchantmentInventory(){
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
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }


    public void load(CompoundNBT p_230337_2_) {
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(p_230337_2_, this.items);
        this.inventoryNr = p_230337_2_.getInt("InventoryNr");

    }

    public CompoundNBT save(CompoundNBT pCompound) {
        ItemStackHelper.saveAllItems(pCompound, this.items);
        pCompound.putInt("InventoryNr", this.inventoryNr);
        return pCompound;
    }


    @Override
    public void setChanged() {
        List<IInventoryChangedListener> changedListeners = ObfuscationReflectionHelper.getPrivateValue(Inventory.class, this, "field_70480_d");
        if (changedListeners != null) {
            for(IInventoryChangedListener iinventorychangedlistener : changedListeners) {
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
        ItemStack itemstack = ItemStackHelper.removeItem(this.items, pIndex, pCount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {

        ItemStack itemstack = ItemStackHelper.takeItem(this.items, pIndex);
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
    public boolean stillValid(PlayerEntity pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.enchant");
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return null;
    }

    public void resetContainer() {
        this.enchantCont = null;
    }
}
