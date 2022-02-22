package committee.nova.portablecraft.common.inventorys;

import committee.nova.portablecraft.core.WorldSaveInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
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
 * Date: 2022/2/21 13:27
 * Version: 1.0
 */
public class ChestInventory extends Inventory implements INamedContainerProvider {

    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("item.portablecraft.chest1");
    protected NonNullList<ItemStack> items = NonNullList.withSize(9 * 6, ItemStack.EMPTY);
    private int inventoryNr;


    public ChestInventory(CompoundNBT nbt){
        super(9 * 6);
        this.load(nbt);
    }

    public ChestInventory(){
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
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new ChestContainer(ContainerType.GENERIC_9x6, p_createMenu_1_, p_createMenu_2_, this, 6);
    }


    @Override
    public ITextComponent getDisplayName() {
        return CONTAINER_TITLE;
    }

    public void load(CompoundNBT pCompound) {
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(pCompound, this.items);

        this.inventoryNr = pCompound.getInt("InventoryNr");

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
        WorldSaveInventory.getInstance().setDirty();

    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
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
        ItemStack itemStack = ItemStackHelper.removeItem(this.items, pIndex, pCount);
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
    public boolean stillValid(PlayerEntity pPlayer) {
        return true;
    }



    @Override
    public void clearContent() {
        this.items.clear();
    }

}
