package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.Portablecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/12 21:10
 * Version: 1.0
 */
public class BrewingStandInventory extends SimpleInventory implements NamedScreenHandlerFactory {


    private static final int[] SLOTS_FOR_SIDES = new int[]{0, 1, 2, 4};
    private DefaultedList<ItemStack> items = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private int brewTime;
    private boolean[] lastPotionCount;
    private Item ingredient;
    private int fuel;
    protected final PropertyDelegate dataAccess = new PropertyDelegate() {
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

        public int size() {
            return 2;
        }
    };
    private int inventoryNr;
    private int increaseSpeed;
    private double rest;


    public BrewingStandInventory(NbtCompound nbt) {
        this.load(nbt);
    }

    public BrewingStandInventory() {
    }

    public int getInventoryNr() {
        return this.inventoryNr;
    }

    public void setInventoryNr(int inventoryNr) {
        this.inventoryNr = inventoryNr;
        this.markDirty();
    }

    public int getIncreaseSpeed() {
        return this.increaseSpeed;
    }

    public void setIncreaseSpeed(int increaseSpeed) {
        this.increaseSpeed = increaseSpeed;
        this.markDirty();
    }

    public double getRest() {
        return this.rest;
    }

    public void setRest(double newrest) {
        this.rest = newrest;
        this.markDirty();
    }

    public void tick() {
        double x = (double) (this.increaseSpeed * Portablecraft.CONFIG.enchantFurnaceSpeedLevelPercent) / 100.0D;
        int durchgang = (int) Math.floor(1.0D + x + this.rest);
        this.setRest(1.0D + x + this.rest - (double) durchgang);


        for (int a = 0; a < durchgang; ++a) {
            ItemStack itemstack = this.items.get(4);
            if (this.fuel <= 0 && itemstack.getItem() == Items.BLAZE_POWDER) {
                this.fuel = 20;
                itemstack.decrement(1);
                this.markDirty();
            }

            boolean flag = this.isBrewable();
            boolean flag1 = this.brewTime > 0;
            ItemStack itemstack1 = this.items.get(3);
            if (flag1) {
                --this.brewTime;
                boolean flag2 = this.brewTime == 0;
                if (flag2 && flag) {
                    this.doBrew();
                    this.markDirty();
                } else if (!flag) {
                    this.brewTime = 0;
                    this.markDirty();
                } else if (this.ingredient != itemstack1.getItem()) {
                    this.brewTime = 0;
                    this.markDirty();
                }
            } else if (flag && this.fuel > 0) {
                --this.fuel;
                this.brewTime = 400;
                this.ingredient = itemstack1.getItem();
                this.markDirty();
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
        if (itemstack.isEmpty()) {
            return false;
        }
        if (!BrewingRecipeRegistry.isValidIngredient(itemstack)) {
            return false;
        }
        for (int i = 0; i < 3; ++i) {
            ItemStack itemStack2 = this.items.get(i);
            if (itemStack2.isEmpty() || !BrewingRecipeRegistry.hasRecipe(itemStack2, itemstack)) continue;
            return true;
        }
        return false;
    }

    private void doBrew() {
        ItemStack itemstack = this.items.get(3);
        for (int i = 0; i < 3; ++i) {
            this.items.set(i, BrewingRecipeRegistry.craft(itemstack, this.items.get(i)));
        }
        itemstack.decrement(1);
        if (itemstack.getItem().hasRecipeRemainder()) {
            ItemStack itemStack2 = new ItemStack(itemstack.getItem().getRecipeRemainder());
            if (itemstack.isEmpty()) {
                itemstack = itemStack2;
            }
        }
        this.items.set(3, itemstack);
    }

    public void load(NbtCompound p_230337_2_) {
        this.items = DefaultedList.ofSize(this.getContainerSize(), ItemStack.EMPTY);
        Inventories.readNbt(p_230337_2_, this.items);
        this.brewTime = p_230337_2_.getShort("BrewTime");
        this.fuel = p_230337_2_.getByte("Fuel");
        this.inventoryNr = p_230337_2_.getInt("InventoryNr");
        this.increaseSpeed = p_230337_2_.getInt("increaseSpeed");
        this.rest = p_230337_2_.getDouble("rest");
    }

    public NbtCompound save(NbtCompound pCompound) {
        pCompound.putShort("BrewTime", (short) this.brewTime);
        Inventories.writeNbt(pCompound, this.items);
        pCompound.putByte("Fuel", (byte) this.fuel);
        pCompound.putInt("InventoryNr", this.inventoryNr);
        pCompound.putInt("increaseSpeed", this.increaseSpeed);
        pCompound.putDouble("rest", this.rest);
        return pCompound;
    }


    @Override
    public Text getDisplayName() {
        return new TranslatableText("item.portablecraft.brewing_stand1");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new BrewingStandScreenHandler(syncId, inv, this, this.dataAccess);
    }
}
