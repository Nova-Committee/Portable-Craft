package committee.nova.portablecraft.common.containers.base;

import committee.nova.portablecraft.PortableCraft;
import committee.nova.portablecraft.common.configs.ModConfig;
import committee.nova.portablecraft.core.WorldSaveInventory;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 12:45
 * Version: 1.0
 */
public abstract class AbstractFurnaceInventory extends SimpleContainer implements MenuProvider {

    protected final RecipeType<? extends AbstractCookingRecipe> recipeType;
    private final BaseComponent name;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int litTime;
    private int litDuration;
    private int cookingProgress;
    private int cookingTotalTime;
    public final ContainerData dataAccess = new ContainerData() {
        public int get(int pIndex) {
            switch (pIndex) {
                case 0:
                    return litTime;
                case 1:
                    return litDuration;
                case 2:
                    return cookingProgress;
                case 3:
                    return cookingTotalTime;
                default:
                    return 0;
            }
        }

        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0:
                    litTime = pValue;
                    break;
                case 1:
                    litDuration = pValue;
                    break;
                case 2:
                    cookingProgress = pValue;
                    break;
                case 3:
                    cookingTotalTime = pValue;
            }

        }

        public int getCount() {
            return 4;
        }
    };
    private int inventoryNr;
    private boolean holdheat;
    private int increaseSpeed;
    private double rest;


    public AbstractFurnaceInventory(RecipeType<? extends AbstractCookingRecipe> recipeType, CompoundTag nbt, BaseComponent name) {
        super(3);
        this.recipeType = recipeType;
        this.holdheat = false;
        this.increaseSpeed = 0;
        this.rest = 0.0D;
        this.name = name;
        this.load(nbt);
    }

    public AbstractFurnaceInventory(RecipeType<? extends AbstractCookingRecipe> recipeType, BaseComponent name) {
        super(3);
        this.recipeType = recipeType;
        this.holdheat = false;
        this.increaseSpeed = 0;
        this.rest = 0.0D;
        this.name = name;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isLit(AbstractFurnaceInventory inventory) {
        return inventory.dataAccess.get(0) > 0;
    }

    public double getRest() {
        return this.rest;
    }

    public void setRest(double newrest) {
        this.rest = newrest;
        this.setChanged();
    }

    public int getIncreaseSpeed() {
        return this.increaseSpeed;
    }

    public void setIncreaseSpeed(int increaseSpeed) {
        this.increaseSpeed = increaseSpeed;
        this.setChanged();
    }

    public int getInventoryNr() {
        return this.inventoryNr;
    }

    public void setInventoryNr(int inventoryNr) {
        this.inventoryNr = inventoryNr;
        this.setChanged();
    }

    public boolean isHoldHeat() {
        return this.holdheat;
    }

    public void setHoldHeat(boolean holdheat) {
        this.holdheat = holdheat;
        this.setChanged();
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
        return this.createMenu(p_createMenu_1_, p_createMenu_2_);
    }

    protected abstract AbstractContainerMenu createMenu(int pId, Inventory pPlayer);

    @Override
    public BaseComponent getDisplayName() {
        return name;
    }

    public void load(CompoundTag pCompound) {
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pCompound, this.items);
        this.litTime = pCompound.getInt("BurnTime");
        this.cookingProgress = pCompound.getInt("CookTime");
        this.cookingTotalTime = pCompound.getInt("CookTimeTotal");
        this.litDuration = this.getBurnDuration(this.items.get(1));
        CompoundTag CompoundTag = pCompound.getCompound("RecipesUsed");

        for (String s : CompoundTag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), CompoundTag.getInt(s));
        }

        this.inventoryNr = pCompound.getInt("InventoryNr");
        this.holdheat = pCompound.getBoolean("holdheat");
        this.increaseSpeed = pCompound.getInt("increaseSpeed");
        this.rest = pCompound.getDouble("rest");

    }

    public CompoundTag save(CompoundTag pCompound) {
        pCompound.putInt("BurnTime", this.litTime);
        pCompound.putInt("CookTime", this.cookingProgress);
        pCompound.putInt("CookTimeTotal", this.cookingTotalTime);
        ContainerHelper.saveAllItems(pCompound, this.items);
        CompoundTag CompoundTag = new CompoundTag();
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

    public void loadClient(CompoundTag pCompound) {
        this.litTime = pCompound.getInt("BurnTime");
        this.cookingProgress = pCompound.getInt("CookTime");
        this.cookingTotalTime = pCompound.getInt("CookTimeTotal");
        this.litDuration = this.getBurnDuration(this.items.get(1));

        this.inventoryNr = pCompound.getInt("InventoryNr");
        this.holdheat = pCompound.getBoolean("holdheat");
        this.increaseSpeed = pCompound.getInt("increaseSpeed");
        this.rest = pCompound.getDouble("rest");

    }

    public CompoundTag saveClient(CompoundTag pCompound) {
        pCompound.putInt("BurnTime", this.litTime);
        pCompound.putInt("CookTime", this.cookingProgress);
        pCompound.putInt("CookTimeTotal", this.cookingTotalTime);

        pCompound.putInt("InventoryNr", this.inventoryNr);
        pCompound.putBoolean("holdheat", this.holdheat);
        pCompound.putInt("increaseSpeed", this.increaseSpeed);
        pCompound.putDouble("rest", this.rest);

        return pCompound;
    }

    public boolean canSmelt(ItemStack pStack) {
        return PortableCraft.SERVER.getRecipeManager().getRecipeFor((RecipeType) this.recipeType, new SimpleContainer(pStack), PortableCraft.WORLD).isPresent();
    }

    public boolean isFuel(ItemStack pStack) {
        return ForgeHooks.getBurnTime(pStack, this.recipeType) > 0;
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    public void tick() {
        double x = (double) (this.increaseSpeed * ModConfig.COMMON.enchantFurnaceSpeedLevelPercent.get()) / 100.0D;
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

            if (!PortableCraft.WORLD.isClientSide) {
                ItemStack itemstack = this.items.get(1);
                if (this.isLit() || !itemstack.isEmpty() && !this.items.get(0).isEmpty()) {
                    Recipe<?> irecipe = PortableCraft.RECIPE_MANAGER.getRecipeFor((RecipeType<AbstractCookingRecipe>) this.recipeType, this, PortableCraft.WORLD).orElse(null);
                    if (!this.isLit() && canBurn(irecipe)) {
                        this.litTime = ForgeHooks.getBurnTime(itemstack, this.recipeType);
                        this.litDuration = this.litTime;
                        if (this.isLit()) {
                            flag1 = true;
                            if (itemstack.hasContainerItem())
                                this.items.set(1, itemstack.getContainerItem());
                            else if (!itemstack.isEmpty()) {
                                itemstack.shrink(1);
                                if (itemstack.isEmpty()) {
                                    this.items.set(1, itemstack.getContainerItem());
                                }
                            }
                        }
                    }

                    if (this.isLit() && this.canBurn(irecipe)) {
                        ++this.cookingProgress;
                        if (this.cookingProgress == this.cookingTotalTime) {
                            this.cookingProgress = 0;
                            this.cookingTotalTime = this.getTotalCookTime();
                            this.burn(irecipe, PortableCraft.WORLD);
                            flag1 = true;
                        }
                    } else {
                        this.cookingProgress = 0;
                    }
                } else if (!this.isLit() && this.cookingProgress > 0) {
                    this.cookingProgress = Mth.clamp(this.cookingProgress - 2, 0, this.cookingTotalTime);
                }

                if (flag != this.isLit()) {
                    flag1 = true;
                }
            }

            if (flag1) {
                this.setChanged();
            }
        }
    }


    protected boolean canBurn(@Nullable Recipe<?> p_214008_1_) {
        if (!this.items.get(0).isEmpty() && p_214008_1_ != null) {
            ItemStack itemstack = ((Recipe<Container>) p_214008_1_).assemble(this);
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.items.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.sameItem(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= 64 && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private void burn(@Nullable Recipe<?> p_214007_1_, ServerLevel level) {
        if (p_214007_1_ != null && this.canBurn(p_214007_1_)) {
            ItemStack itemstack = this.items.get(0);
            ItemStack itemstack1 = ((Recipe<Container>) p_214007_1_).assemble(this);
            ItemStack itemstack2 = this.items.get(2);
            if (itemstack2.isEmpty()) {
                this.items.set(2, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (!level.isClientSide) {
                this.setRecipeUsed(p_214007_1_);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
                this.items.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
        }
    }


    public void setRecipeUsed(@Nullable Recipe<?> pRecipe) {
        if (pRecipe != null) {
            ResourceLocation resourcelocation = pRecipe.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }

    }

    protected int getBurnDuration(ItemStack pFuel) {
        if (pFuel.isEmpty()) {
            return 0;
        } else {
            Item item = pFuel.getItem();
            return ForgeHooks.getBurnTime(pFuel, this.recipeType);
        }
    }

    protected int getTotalCookTime() {
        return 200;
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
    public ItemStack getItem(int pIndex) {
        return this.items.get(pIndex);
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
        ItemStack itemstack = this.items.get(pIndex);
        boolean flag = !pStack.isEmpty() && pStack.sameItem(itemstack) && ItemStack.tagMatches(pStack, itemstack);
        this.items.set(pIndex, pStack);
        if (pStack.getCount() > 64) {
            pStack.setCount(64);
        }

        if (pIndex == 0 && !flag) {
            this.cookingTotalTime = this.getTotalCookTime();
            this.cookingProgress = 0;
            this.setChanged();
        }
    }


    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        if (pIndex == 2) {
            return false;
        } else if (pIndex != 1) {
            return true;
        } else {
            ItemStack itemstack = this.items.get(1);
            return ForgeHooks.getBurnTime(pStack, this.recipeType) > 0 || pStack.getItem() == Items.BUCKET && itemstack.getItem() != Items.BUCKET;
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

}
