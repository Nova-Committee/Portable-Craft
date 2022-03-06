package committee.nova.portablecraft.common.menus;

import committee.nova.portablecraft.common.items.CraftingItem;
import committee.nova.portablecraft.common.menus.base.BaseContainer;
import committee.nova.portablecraft.common.menus.base.IContainerCraftingAction;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
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

    private final net.minecraft.world.inventory.CraftingContainer craftMatrix = new net.minecraft.world.inventory.CraftingContainer(this, 3, 3);
    private final ResultContainer craftResult = new ResultContainer();
    public ItemStack bag;
    public int slot;
    public int slots;
    //
    protected Player playerEntity;
    private IItemHandler handler;

    public CraftingContainer(int pContainerId, Inventory playerInventory, Player player) {
        super(ModContainers.CRAFT, pContainerId);
        this.playerEntity = player;
        this.playerInventory = playerInventory;
        this.endInv = 10;
        //result first
        this.addSlot(new ResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        //
        if (player.getMainHandItem().getItem() instanceof CraftingItem) {
            this.bag = player.getMainHandItem();
            this.slot = player.getInventory().selected;
        } else if (player.getMainHandItem().getItem() instanceof CraftingItem) {
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

    public static CraftingContainer create(int pContainerId, Inventory playerInventory, FriendlyByteBuf buffer) {
        return new CraftingContainer(pContainerId, playerInventory, playerInventory.player);
    }

    @Override
    public void removed(Player pPlayer) {
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
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void slotsChanged(Container pInventory) {
        Level world = playerInventory.player.level;
        if (!world.isClientSide) {
            ServerPlayer player = (ServerPlayer) playerInventory.player;
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftMatrix, world);
            if (optional.isPresent()) {
                CraftingRecipe icraftingrecipe = optional.get();
                if (craftResult.setRecipeUsed(world, player, icraftingrecipe)) {
                    itemstack = icraftingrecipe.assemble(craftMatrix);
                }
            }
            craftResult.setItem(0, itemstack);
            this.setRemoteSlot(0, itemstack);
            player.connection.send(new ClientboundContainerSetSlotPacket(this.containerId, this.incrementStateId(), 0, itemstack));
        }
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot slot) {
        return slot.container != craftResult && super.canTakeItemForPickAll(pStack, slot);
    }

    @Override
    public void clicked(int pSlotId, int pDragType, ClickType pClickType, Player pPlayer) {
        if (!(pSlotId < 0 || pSlotId >= this.getItems().size())) {
            ItemStack myBag = this.getItems().get(pSlotId).getContainerItem();
            if (myBag.getItem() instanceof CraftingItem) {
                return;
            }
        }
        super.clicked(pSlotId, pDragType, pClickType, pPlayer);
    }


    @Override
    public ItemStack transferStack(Player playerIn, int index) {
        return super.quickMoveStack(playerIn, index);
    }

    @Override
    public net.minecraft.world.inventory.CraftingContainer getCraftMatrix() {
        return this.craftMatrix;
    }

    @Override
    public ResultContainer getCraftResult() {
        return this.craftResult;
    }

    public static class CraftingContainerProvider implements MenuProvider {
        @Override
        public Component getDisplayName() {
            return new TranslatableComponent("item.portablecraft.craft1");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
            return new CraftingContainer(p_createMenu_1_, p_createMenu_2_, p_createMenu_3_);
        }
    }
}
