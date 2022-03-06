package committee.nova.portablecraft.common.menus;

import committee.nova.portablecraft.common.menus.stack.EnchantedBookLogic;
import committee.nova.portablecraft.common.menus.stack.ItemStackHandlerBookArray;
import committee.nova.portablecraft.common.menus.stack.ItemStackHandlerEnchant;
import committee.nova.portablecraft.common.slots.BookSlot;
import committee.nova.portablecraft.common.slots.EnchantSlot;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 8:19
 * Version: 1.0
 */
public class EnchantmentEditContainer extends AbstractContainerMenu {
    private final Player player;
    private final Level world;
    private final ItemStackHandlerEnchant tableInventory;
    private final ItemStackHandlerBookArray bookArrayInventory;
    private final EnchantedBookLogic logic;


    public EnchantmentEditContainer(int winId, Inventory inventory) {
        super(ModContainers.ENCHANTMENT_EDIT, winId);
        this.player = inventory.player;
        this.world = inventory.player.level;
        this.tableInventory = new ItemStackHandlerEnchant(this);
        this.bookArrayInventory = new ItemStackHandlerBookArray(this);
        this.addSlot(new EnchantSlot(this.tableInventory, 0, 25, 33));


        this.addBookArrayInventory(this.bookArrayInventory);
        this.addPlayerInventory(inventory);

        this.logic = new EnchantedBookLogic(this);
    }

    public static EnchantmentEditContainer create(int pContainerId, Inventory playerInventory, FriendlyByteBuf buffer) {
        return new EnchantmentEditContainer(pContainerId, playerInventory);
    }

    private void addBookArrayInventory(ItemStackHandlerBookArray inventory) {
        int index = 0;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new BookSlot(inventory, this, index++, 98 + j * 18, 15 + i * 18));
            }
        }

    }

    private void addPlayerInventory(Inventory playerInventory) {
        int k;
        for (k = 0; k < 3; ++k) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for (k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public void onCraftMatrixChanged(ItemStackHandler inventory) {
        if (!this.world.isClientSide) {
            if (inventory instanceof ItemStackHandlerEnchant) {
                ItemStack itemStack = this.tableInventory.getEnchantingStack();
                if (!itemStack.isEmpty()) {
                    this.logic.update();
                } else {
                    this.logic.close();
                }
            } else if (inventory instanceof ItemStackHandlerBookArray) {
                this.logic.bookArray();
            }
        }

    }

    public void previous() {
        this.logic.previous();
    }

    public void next() {
        this.logic.next();
    }

    public void take() {
        this.logic.take();
    }

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
        if (!this.world.isClientSide) {
            if (playerIn.isAlive() && (!(playerIn instanceof ServerPlayer) || !((ServerPlayer) playerIn).hasDisconnected())) {
                playerIn.getInventory().placeItemBackInInventory(this.tableInventory.getStackInSlot(0));
            } else {
                playerIn.drop(this.tableInventory.getStackInSlot(0), false);
            }
        }

    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        Slot transferSlot = this.slots.get(slot);
        if (transferSlot != null && transferSlot.hasItem()) {
            ItemStack transferStack = transferSlot.getItem();
            ItemStack copyItem = transferStack.copy();
            if (slot < 1) {
                if (!this.moveItemStackTo(transferStack, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                transferSlot.onQuickCraft(transferStack, copyItem);
            } else if (slot >= 10 && slot < 37) {
                if (!this.moveItemStackTo(transferStack, 0, 1, false) && !this.moveItemStackTo(transferStack, 37, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot >= 37 && slot < 46) {
                if (!this.moveItemStackTo(transferStack, 0, 1, false) && !this.moveItemStackTo(transferStack, 10, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(transferStack, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (transferStack.getCount() == 0) {
                transferSlot.set(ItemStack.EMPTY);
            } else {
                transferSlot.setChanged();
            }

            return transferStack.getCount() == copyItem.getCount() ? ItemStack.EMPTY : transferStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    public ItemStackHandlerEnchant getTableInventory() {
        return this.tableInventory;
    }


    public ItemStackHandlerBookArray getBookArrayInventory() {
        return this.bookArrayInventory;
    }

    public Player getPlayer() {
        return this.player;
    }
}
