package committee.nova.portablecraft.common.containers.base;

import committee.nova.portablecraft.Portablecraft;
import committee.nova.portablecraft.core.WorldSaveInventory;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/12 19:47
 * Version: 1.0
 */
public abstract class AbstractFurnaceInventory extends SimpleInventory implements NamedScreenHandlerFactory {

    private final Object2IntOpenHashMap<Identifier> recipesUsed = new Object2IntOpenHashMap<>();
    private final Text name;
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;
    protected DefaultedList<ItemStack> items = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private int litTime;
    private int litDuration;
    private int cookingProgress;
    private int cookingTotalTime;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0: {
                    return litTime;
                }
                case 1: {
                    return litDuration;
                }
                case 2: {
                    return cookingProgress;
                }
                case 3: {
                    return cookingTotalTime;
                }
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: {
                    litTime = value;
                    break;
                }
                case 1: {
                    litDuration = value;
                    break;
                }
                case 2: {
                    cookingProgress = value;
                    break;
                }
                case 3: {
                    cookingTotalTime = value;
                    break;
                }
            }
        }

        @Override
        public int size() {
            return 4;
        }
    };
    private int inventoryNr;
    private boolean holdheat;
    private int increaseSpeed;
    private double rest;


    public AbstractFurnaceInventory(RecipeType<? extends AbstractCookingRecipe> recipeType, NbtCompound nbt, Text name) {
        super(3);
        this.recipeType = recipeType;
        this.holdheat = false;
        this.increaseSpeed = 0;
        this.rest = 0.0D;
        this.name = name;
        this.load(nbt);
    }

    public AbstractFurnaceInventory(RecipeType<? extends AbstractCookingRecipe> recipeType, Text name) {
        super(3);
        this.recipeType = recipeType;
        this.holdheat = false;
        this.increaseSpeed = 0;
        this.rest = 0.0D;
        this.name = name;
    }

    private static int getCookTime(World world, RecipeType<? extends AbstractCookingRecipe> recipeType, Inventory inventory) {
        return world.getRecipeManager().getFirstMatch(recipeType, inventory, world).map(AbstractCookingRecipe::getCookTime).orElse(200);
    }

    public double getRest() {
        return this.rest;
    }

    public void setRest(double newrest) {
        this.rest = newrest;
        this.markDirty();
    }

    public int getIncreaseSpeed() {
        return this.increaseSpeed;
    }

    public void setIncreaseSpeed(int increaseSpeed) {
        this.increaseSpeed = increaseSpeed;
        this.markDirty();
    }

    public int getInventoryNr() {
        return this.inventoryNr;
    }

    public void setInventoryNr(int inventoryNr) {
        this.inventoryNr = inventoryNr;
        this.markDirty();
    }

    public boolean isHoldHeat() {
        return this.holdheat;
    }

    public void setHoldHeat(boolean holdheat) {
        this.holdheat = holdheat;
        this.markDirty();
    }

    @Override
    public Text getDisplayName() {
        return name;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return this.createMenu(syncId, inv);
    }

    protected abstract ScreenHandler createMenu(int pId, PlayerInventory pPlayer);

    public void load(NbtCompound pCompound) {
        this.items = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(pCompound, this.items);
        this.litTime = pCompound.getInt("BurnTime");
        this.cookingProgress = pCompound.getInt("CookTime");
        this.cookingTotalTime = pCompound.getInt("CookTimeTotal");
        this.litDuration = this.getBurnDuration(this.items.get(1));
        NbtCompound nbtCompound = pCompound.getCompound("RecipesUsed");

        for (String string : nbtCompound.getKeys()) {
            this.recipesUsed.put(new Identifier(string), nbtCompound.getInt(string));
        }

        this.inventoryNr = pCompound.getInt("InventoryNr");
        this.holdheat = pCompound.getBoolean("holdheat");
        this.increaseSpeed = pCompound.getInt("increaseSpeed");
        this.rest = pCompound.getDouble("rest");

    }

    public NbtCompound save(NbtCompound pCompound) {
        pCompound.putInt("BurnTime", this.litTime);
        pCompound.putInt("CookTime", this.cookingProgress);
        pCompound.putInt("CookTimeTotal", this.cookingTotalTime);
        Inventories.writeNbt(pCompound, this.items);
        NbtCompound CompoundTag = new NbtCompound();
        this.recipesUsed.forEach((p_235643_1_, p_235643_2_) -> {
            CompoundTag.putInt(p_235643_1_.toString(), p_235643_2_);
        });
        pCompound.put("RecipesUsed", CompoundTag);

        pCompound.putInt("InventoryNr", this.inventoryNr);
        pCompound.putBoolean("holdheat", this.holdheat);
        pCompound.putInt("increaseSpeed", this.increaseSpeed);
        pCompound.putDouble("rest", this.rest);

        return pCompound;
    }

    public void tick() {
        double x = (double) (this.increaseSpeed * Portablecraft.CONFIG.enchantFurnaceSpeedLevelPercent) / 100.0D;
        int durchgang = (int) Math.floor(1.0D + x + this.rest);
        this.setRest(1.0D + x + this.rest - (double) durchgang);
        for (int a = 0; a < durchgang; ++a) {
            boolean flag = this.isLit();
            boolean flag1 = false;
            if (this.isLit()) {
                if (this.holdheat) {
                    if (this.cookingProgress > 0) {
                        --this.litTime;
                    }
                } else {
                    --this.litTime;
                }
            }

            if (!Portablecraft.SERVER.getOverworld().isClient) {
                ItemStack itemstack = this.items.get(1);
                if (this.isLit() || !itemstack.isEmpty() && !this.items.get(0).isEmpty()) {
                    Recipe<?> irecipe = Portablecraft.SERVER.getOverworld().getRecipeManager().getFirstMatch(this.recipeType, this, Portablecraft.SERVER.getOverworld()).orElse(null);
                    if (!this.isLit() && canBurn(irecipe)) {
                        this.litTime = getFuelTime(itemstack);
                        this.litDuration = this.litTime;
                        if (this.isLit()) {
                            flag1 = true;
                            if (!itemstack.isEmpty()) {
                                Item item = itemstack.getItem();
                                itemstack.decrement(1);
                                if (itemstack.isEmpty()) {
                                    Item item2 = item.getRecipeRemainder();
                                    this.items.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                                }
                            }
                        }
                    }

                    if (this.isLit() && this.canBurn(irecipe)) {
                        ++this.cookingProgress;
                        if (this.cookingProgress == this.cookingTotalTime) {
                            this.cookingProgress = 0;
                            this.cookingTotalTime = this.getTotalCookTime();
                            this.burn(irecipe, Portablecraft.SERVER.getOverworld());
                            flag1 = true;
                        }
                    } else {
                        this.cookingProgress = 0;
                    }
                } else if (!this.isLit() && this.cookingProgress > 0) {
                    this.cookingProgress = MathHelper.clamp(this.cookingProgress - 2, 0, this.cookingTotalTime);
                }

                if (flag != this.isLit()) {
                    flag1 = true;
                }
            }

            if (flag1) {
                this.markDirty();
            }
        }
    }

    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        Item item = fuel.getItem();
        return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0);
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    protected boolean canBurn(@javax.annotation.Nullable Recipe<?> p_214008_1_) {
        if (!this.items.get(0).isEmpty() && p_214008_1_ != null) {
            ItemStack itemstack = p_214008_1_.getOutput();
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.items.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.isItemEqualIgnoreDamage(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= 64 && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxCount()) {
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }

    private void burn(@javax.annotation.Nullable Recipe<?> recipe, ServerWorld level) {
        if (recipe != null && this.canBurn(recipe)) {
            ItemStack itemstack = this.items.get(0);
            ItemStack itemstack1 = recipe.getOutput();
            ItemStack itemstack2 = this.items.get(2);
            if (itemstack2.isEmpty()) {
                this.items.set(2, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.increment(itemstack1.getCount());
            }

            if (!level.isClient) {
                this.setRecipeUsed(recipe);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
                this.items.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.decrement(1);
        }
    }


    public void setRecipeUsed(@javax.annotation.Nullable Recipe<?> pRecipe) {
        if (pRecipe != null) {
            Identifier resourcelocation = pRecipe.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }

    }

    protected int getBurnDuration(ItemStack pFuel) {
        if (pFuel.isEmpty()) {
            return 0;
        }
        return getFuelTime(pFuel);
    }

    protected int getTotalCookTime() {
        return 200;
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
        for (ItemStack itemStack : this.items) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.items, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.items, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.items.get(slot);
        boolean bl = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(itemStack) && ItemStack.areNbtEqual(stack, itemStack);
        this.items.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        if (slot == 0 && !bl) {
            this.cookingTotalTime = getTotalCookTime();
            this.cookingProgress = 0;
            this.markDirty();
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        }
        if (slot == 1) {
            ItemStack itemStack = this.items.get(1);
            return AbstractFurnaceBlockEntity.canUseAsFuel(stack) || stack.isOf(Items.BUCKET) && !itemStack.isOf(Items.BUCKET);
        }
        return true;
    }

    @Override
    public void clear() {
        this.items.clear();
    }
}
