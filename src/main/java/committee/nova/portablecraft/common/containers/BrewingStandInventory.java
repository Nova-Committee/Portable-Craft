package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.configs.ModConfig;
import committee.nova.portablecraft.core.WorldSaveInventory;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 21:37
 * Version: 1.0
 */
public class BrewingStandInventory extends SimpleContainer implements MenuProvider {

    private static final int[] SLOTS_FOR_SIDES = new int[]{0, 1, 2, 4};
    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private int brewTime;
    private boolean[] lastPotionCount;
    private Item ingredient;
    private int fuel;
    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int pIndex) {
            switch (pIndex) {
                case 0:
                    return brewTime;
                case 1:
                    return fuel;
                default:
                    return 0;
            }
        }

        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0:
                    brewTime = pValue;
                    break;
                case 1:
                    fuel = pValue;
            }

        }

        public int getCount() {
            return 2;
        }
    };
    private int inventoryNr;
    private int increaseSpeed;
    private double rest;


    public BrewingStandInventory(CompoundTag nbt) {
        this.load(nbt);
    }

    public BrewingStandInventory() {
    }

    public int getInventoryNr() {
        return this.inventoryNr;
    }

    public void setInventoryNr(int inventoryNr) {
        this.inventoryNr = inventoryNr;
        this.setChanged();
    }

    public int getIncreaseSpeed() {
        return this.increaseSpeed;
    }

    public void setIncreaseSpeed(int increaseSpeed) {
        this.increaseSpeed = increaseSpeed;
        this.setChanged();
    }

    public double getRest() {
        return this.rest;
    }

    public void setRest(double newrest) {
        this.rest = newrest;
        this.setChanged();
    }

    public void tick() {
        double x = (double) (this.increaseSpeed * ModConfig.COMMON.enchantFurnaceSpeedLevelPercent.get()) / 100.0D;
        int durchgang = (int) Math.floor(1.0D + x + this.rest);
        this.setRest(1.0D + x + this.rest - (double) durchgang);


        for (int a = 0; a < durchgang; ++a) {
            ItemStack itemstack = this.items.get(4);
            if (this.fuel <= 0 && itemstack.getItem() == Items.BLAZE_POWDER) {
                this.fuel = 20;
                itemstack.shrink(1);
                this.setChanged();
            }

            boolean flag = this.isBrewable();
            boolean flag1 = this.brewTime > 0;
            ItemStack itemstack1 = this.items.get(3);
            if (flag1) {
                --this.brewTime;
                boolean flag2 = this.brewTime == 0;
                if (flag2 && flag) {
                    this.doBrew();
                    this.setChanged();
                } else if (!flag) {
                    this.brewTime = 0;
                    this.setChanged();
                } else if (this.ingredient != itemstack1.getItem()) {
                    this.brewTime = 0;
                    this.setChanged();
                }
            } else if (flag && this.fuel > 0) {
                --this.fuel;
                this.brewTime = 400;
                this.ingredient = itemstack1.getItem();
                this.setChanged();
            }


            boolean[] aboolean = this.getPotionBits();
            if (!Arrays.equals(aboolean, this.lastPotionCount)) {
                this.lastPotionCount = aboolean;

            }


        }
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


    public boolean[] getPotionBits() {
        boolean[] aboolean = new boolean[3];

        for (int i = 0; i < 3; ++i) {
            if (!this.items.get(i).isEmpty()) {
                aboolean[i] = true;
            }
        }

        return aboolean;
    }

    public boolean isBrewable() {
        ItemStack itemstack = this.items.get(3);
        if (!itemstack.isEmpty())
            return net.minecraftforge.common.brewing.BrewingRecipeRegistry.canBrew(items, itemstack, SLOTS_FOR_SIDES);
        if (itemstack.isEmpty()) {
            return false;
        } else if (!PotionBrewing.isIngredient(itemstack)) {
            return false;
        } else {
            for (int i = 0; i < 3; ++i) {
                ItemStack itemstack1 = this.items.get(i);
                if (!itemstack1.isEmpty() && PotionBrewing.hasMix(itemstack1, itemstack)) {
                    return true;
                }
            }

            return false;
        }
    }

    private void doBrew() {
        if (net.minecraftforge.event.ForgeEventFactory.onPotionAttemptBrew(items)) return;
        ItemStack itemstack = this.items.get(3);

        net.minecraftforge.common.brewing.BrewingRecipeRegistry.brewPotions(items, itemstack, SLOTS_FOR_SIDES);
        net.minecraftforge.event.ForgeEventFactory.onPotionBrewed(items);
        if (itemstack.hasContainerItem()) {
            ItemStack itemstack1 = itemstack.getContainerItem();
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                itemstack = itemstack1;
            }

        } else itemstack.shrink(1);

        this.items.set(3, itemstack);
    }

    public void load(CompoundTag p_230337_2_) {
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(p_230337_2_, this.items);
        this.brewTime = p_230337_2_.getShort("BrewTime");
        this.fuel = p_230337_2_.getByte("Fuel");
        this.inventoryNr = p_230337_2_.getInt("InventoryNr");
        this.increaseSpeed = p_230337_2_.getInt("increaseSpeed");
        this.rest = p_230337_2_.getDouble("rest");
    }

    public CompoundTag save(CompoundTag pCompound) {
        pCompound.putShort("BrewTime", (short) this.brewTime);
        ContainerHelper.saveAllItems(pCompound, this.items);
        pCompound.putByte("Fuel", (byte) this.fuel);
        pCompound.putInt("InventoryNr", this.inventoryNr);
        pCompound.putInt("increaseSpeed", this.increaseSpeed);
        pCompound.putDouble("rest", this.rest);
        return pCompound;
    }

    @Override
    public void setChanged() {
        WorldSaveInventory.getInstance().setDirty();
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return pIndex >= 0 && pIndex < this.items.size() ? this.items.get(pIndex) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.removeItem(this.items, pIndex, pCount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(this.items, pIndex);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        if (pIndex >= 0 && pIndex < this.items.size()) {
            this.items.set(pIndex, pStack);
        }

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        if (pIndex == 3) {
            return net.minecraftforge.common.brewing.BrewingRecipeRegistry.isValidIngredient(pStack);
        } else {
            Item item = pStack.getItem();
            if (pIndex == 4) {
                return item == Items.BLAZE_POWDER;
            } else {
                return net.minecraftforge.common.brewing.BrewingRecipeRegistry.isValidInput(pStack) && this.getItem(pIndex).isEmpty();
            }
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("item.portablecraft.brewing_stand1");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pId, Inventory pPlayer, Player p_createMenu_3_) {
        return new BrewingStandMenu(pId, pPlayer, this, this.dataAccess);
    }
}