package committee.nova.portablecraft.common.menus.base;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 21:04
 * Version: 1.0
 */
public abstract class AbstractRepairedContainer extends AbstractContainerMenu {

    protected final ResultContainer resultSlots = new ResultContainer();
    protected final Level level;
    protected final Player player;

    protected AbstractRepairedContainer(@Nullable MenuType<?> pMenuType, int pContainerId, Inventory pInventory) {
        super(pMenuType, pContainerId);
        this.player = pInventory.player;
        this.level = pInventory.player.level;
        this.addSlot(new Slot(this.inputSlots, 0, 27, 47));
        this.addSlot(new Slot(this.inputSlots, 1, 76, 47));
        this.addSlot(new Slot(this.resultSlots, 2, 134, 47) {

            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            public boolean mayPickup(Player pPlayer) {
                return AbstractRepairedContainer.this.mayPickup(pPlayer, this.hasItem());
            }

            public void onTake(Player pPlayer, ItemStack pStack) {
                AbstractRepairedContainer.this.onTake(pPlayer, pStack);
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pInventory, k, 8 + k * 18, 142));
        }
    }

    protected final Container inputSlots = new SimpleContainer(2) {
        public void setChanged() {
            super.setChanged();
            AbstractRepairedContainer.this.slotsChanged(this);
        }
    };

    protected abstract boolean mayPickup(Player pPlayer, boolean pHasStack);

    protected abstract void onTake(Player pPlayer, ItemStack pInputItem);

    @Override
    public void removed(Player pPlayer) {
        super.removed(player);
        this.clearContainer(pPlayer, resultSlots);
        this.clearContainer(pPlayer, inputSlots);
        Inventory Inventory = pPlayer.getInventory();
        if (!Inventory.getSelected().isEmpty()) {
            pPlayer.drop(Inventory.getSelected(), false);
            Inventory.setPickedItem(ItemStack.EMPTY);
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public abstract void createResult();

    @Override
    public void slotsChanged(Container pInventory) {
        super.slotsChanged(pInventory);
        if (pInventory == this.inputSlots) {
            this.createResult();
        }

    }

    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack pStack) {
        return false;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex != 0 && pIndex != 1) {
                if (pIndex >= 3 && pIndex < 39) {
                    int i = this.shouldQuickMoveToAdditionalSlot(itemstack) ? 1 : 0;
                    if (!this.moveItemStackTo(itemstack1, i, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }

        return itemstack;
    }


}
