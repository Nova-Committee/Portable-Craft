package committee.nova.portablecraft.common.containers.base;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 21:04
 * Version: 1.0
 */
public abstract class AbstractRepairedContainer extends Container {

    protected final CraftResultInventory resultSlots = new CraftResultInventory();
    protected  final World level;
    protected final PlayerEntity player;    protected final IInventory inputSlots = new Inventory(2) {
        public void setChanged() {
            super.setChanged();
            AbstractRepairedContainer.this.slotsChanged(this);
        }
    };

    protected AbstractRepairedContainer(@Nullable ContainerType<?> pMenuType, int pContainerId, PlayerInventory pPlayerInventory) {
        super(pMenuType, pContainerId);
        this.player = pPlayerInventory.player;
        this.level = pPlayerInventory.player.level;
        this.addSlot(new Slot(this.inputSlots, 0, 27, 47));
        this.addSlot(new Slot(this.inputSlots, 1, 76, 47));
        this.addSlot(new Slot(this.resultSlots, 2, 134, 47) {

            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            public boolean mayPickup(PlayerEntity pPlayer) {
                return AbstractRepairedContainer.this.mayPickup(pPlayer, this.hasItem());
            }

            public ItemStack onTake(PlayerEntity pPlayer, ItemStack pStack) {
                return AbstractRepairedContainer.this.onTake(pPlayer, pStack);
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
        }
    }

    protected abstract boolean mayPickup(PlayerEntity pPlayer, boolean pHasStack);

    protected abstract ItemStack onTake(PlayerEntity pPlayer, ItemStack pInputItem);

    protected abstract boolean isValidBlock(BlockState pState);

    @Override
    public void removed(PlayerEntity pPlayer) {
        super.removed(player);
        this.clearContainer(pPlayer, level, resultSlots);
        this.clearContainer(pPlayer, level, inputSlots);
        PlayerInventory playerinventory = pPlayer.inventory;
        if (!playerinventory.getCarried().isEmpty()) {
            pPlayer.drop(playerinventory.getCarried(), false);
            playerinventory.setCarried(ItemStack.EMPTY);
        }
    }

    @Override
    public boolean stillValid(PlayerEntity pPlayer) {
        return true;
    }

    public abstract void createResult();

    @Override
    public void slotsChanged(IInventory pInventory) {
        super.slotsChanged(pInventory);
        if (pInventory == this.inputSlots) {
            this.createResult();
        }

    }

    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack pStack) {
        return false;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity pPlayer, int pIndex) {
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
