package committee.nova.portablecraft.common.containers;

import committee.nova.portablecraft.common.containers.base.BaseContainer;
import committee.nova.portablecraft.common.containers.base.IContainerCraftingAction;
import committee.nova.portablecraft.common.items.CraftingItem;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 19:42
 * Version: 1.0
 */
public class CraftingContainer extends BaseContainer implements IContainerCraftingAction {

    private final CraftingInventory craftMatrix = new CraftingInventory(this, 3, 3);
    private final CraftResultInventory craftResult = new CraftResultInventory();
    public ItemStack bag;
    public int slot;
    public int slots;
    //
    protected PlayerEntity playerEntity;
    private IItemHandler handler;

    public CraftingContainer(int pContainerId, PlayerInventory playerInventory, PlayerEntity player) {
        super(ModContainers.CRAFT, pContainerId);
        this.playerEntity = player;
        this.playerInventory = playerInventory;
        this.endInv = 10;
        //result first
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        //
        if (player.getMainHandItem().getItem() instanceof CraftingItem) {
            this.bag = player.getMainHandItem();
            this.slot = player.inventory.selected;
        }
        else if (player.getMainHandItem().getItem() instanceof CraftingItem) {
            this.bag = player.getMainHandItem();
            this.slot = 40;
        }
        //grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                addSlot(new Slot(craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18) {

                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return !(stack.getItem() instanceof CraftingItem);
                    }
                });
            }
        }
        //
        bag.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            this.handler = h;
            this.slots = h.getSlots();
            for (int j = 0; j < h.getSlots(); j++) {
                ItemStack inBag = h.getStackInSlot(j);
                if (!inBag.isEmpty()) {
                    this.craftMatrix.setItem(j, h.getStackInSlot(j));
                }
            }
        });
        layoutPlayerInventorySlots(8, 84);
    }

    public static CraftingContainer create(int pContainerId, PlayerInventory playerInventory, PacketBuffer buffer){
        return new CraftingContainer(pContainerId, playerInventory, playerInventory.player);
    }

    @Override
    public void removed(PlayerEntity pPlayer) {
        super.removed(pPlayer);
        this.craftResult.setItem(0, ItemStack.EMPTY);
        //this is not the saving version
        if (!pPlayer.level.isClientSide) {
            if (this.handler == null) {
                this.handler = bag.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
                if (this.handler == null) {
                    return;
                }
            }
            for (int i = 0; i < 9; i++) {
                ItemStack crafty = this.craftMatrix.getItem(i);
                handler.extractItem(i, 64, false);
                ItemStack failtest = handler.insertItem(i, crafty, false);
                if (!failtest.isEmpty()) {
                    //
                }
            }
        }
    }

    @Override
    public boolean stillValid(PlayerEntity pPlayer) {
        return true;
    }

    @Override
    public void slotsChanged(IInventory pInventory) {
        World world = playerInventory.player.level;
        if (!world.isClientSide) {
            ServerPlayerEntity player = (ServerPlayerEntity) playerInventory.player;
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<ICraftingRecipe> optional = world.getServer().getRecipeManager().getRecipeFor(IRecipeType.CRAFTING, craftMatrix, world);
            if (optional.isPresent()) {
                ICraftingRecipe icraftingrecipe = optional.get();
                if (craftResult.setRecipeUsed(world, player, icraftingrecipe)) {
                    itemstack = icraftingrecipe.assemble(craftMatrix);
                }
            }
            craftResult.setItem(0, itemstack);
            player.connection.send(new SSetSlotPacket(containerId, 0, itemstack));
        }
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot slot) {
        return slot.container != craftResult && super.canTakeItemForPickAll(pStack, slot);
    }

    @Override
    public ItemStack clicked(int pSlotId, int pDragType, ClickType pClickType, PlayerEntity pPlayer) {
        if (!(pSlotId < 0 || pSlotId >= this.getItems().size())) {
            ItemStack myBag = this.getItems().get(pSlotId).getStack();
            if (myBag.getItem() instanceof CraftingItem) {
                return ItemStack.EMPTY;
            }
        }
        return super.clicked(pSlotId, pDragType, pClickType, pPlayer);
    }

    @Override
    public ItemStack transferStack(PlayerEntity playerIn, int index) {
        return super.quickMoveStack(playerIn, index);
    }

    @Override
    public CraftingInventory getCraftMatrix() {
        return this.craftMatrix;
    }

    @Override
    public CraftResultInventory getCraftResult() {
        return this.craftResult;
    }

    public static class CraftingContainerProvider implements INamedContainerProvider {
        @Override
        public ITextComponent getDisplayName() {
            return new TranslationTextComponent("item.portablecraft.craft1");
        }

        @Nullable
        @Override
        public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
            return new CraftingContainer(p_createMenu_1_, p_createMenu_2_, p_createMenu_3_);
        }
    }
}
