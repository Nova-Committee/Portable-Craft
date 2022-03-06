package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.containers.ChestInventory;
import committee.nova.portablecraft.core.WorldSaveInventory;
import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 17:39
 * Version: 1.0
 */
public class ChestItem extends Item {


    public ChestItem() {
        super(new Properties()
                .tab(ModTabs.tab)
                .stacksTo(1)
        );
        setRegistryName("chest1");
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (!worldIn.isClientSide) {
            if (!stack.hasTag()) {
                stack.setTag(new CompoundTag());
                stack.getOrCreateTag().putInt("TAG_CHEST_INVENTORY", WorldSaveInventory.getInstance().addandCreateInvChest());
            } else {
                if (!stack.getOrCreateTag().contains("TAG_CHEST_INVENTORY")) {
                    stack.getOrCreateTag().putInt("TAG_CHEST_INVENTORY", WorldSaveInventory.getInstance().addandCreateInvChest());
                }

                ChestInventory inventory = WorldSaveInventory.getInstance().getInventoryChest(((ServerPlayer) pEntity).getMainHandItem().getOrCreateTag().getInt("TAG_CHEST_INVENTORY"));

            }

        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            ChestInventory inventory = WorldSaveInventory.getInstance().getInventoryChest(pPlayer.getMainHandItem().getOrCreateTag().getInt("TAG_CHEST_INVENTORY"));
            pPlayer.openMenu(inventory);

        }
        return super.use(pLevel, pPlayer, pHand);
    }
}
