package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.inventorys.ChestInventory;
import committee.nova.portablecraft.core.WorldSaveInventory;
import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

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
    public void inventoryTick(ItemStack stack, World worldIn, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (!worldIn.isClientSide) {
            if (!stack.hasTag()) {
                stack.setTag(new CompoundNBT());
                stack.getOrCreateTag().putInt("TAG_CHEST_INVENTORY", WorldSaveInventory.getInstance().addandCreateInvChest());
            } else {
                if (!stack.getOrCreateTag().contains("TAG_CHEST_INVENTORY")) {
                    stack.getOrCreateTag().putInt("TAG_CHEST_INVENTORY", WorldSaveInventory.getInstance().addandCreateInvChest());
                }

                ChestInventory inventory = WorldSaveInventory.getInstance().getInventoryChest(((ServerPlayerEntity)pEntity).getMainHandItem().getOrCreateTag().getInt("TAG_CHEST_INVENTORY"));

            }

        }
    }

    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            ChestInventory inventory = WorldSaveInventory.getInstance().getInventoryChest(pPlayer.getMainHandItem().getOrCreateTag().getInt("TAG_CHEST_INVENTORY"));
            pPlayer.openMenu(inventory);

        }
        return super.use(pLevel, pPlayer, pHand);
    }
}
