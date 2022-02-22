package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.inventorys.SmokerInventory;
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
 * Date: 2022/2/19 20:26
 * Version: 1.0
 */
public class SmokerItem extends Item {


    public SmokerItem() {
        super(new Properties()
                .tab(ModTabs.tab)
                .stacksTo(1)
        );
        setRegistryName("smoker1");
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (!worldIn.isClientSide) {
            if (!stack.hasTag()) {
                stack.setTag(new CompoundNBT());
                stack.getOrCreateTag().putInt("TAG_SMOKER_INVENTORY", WorldSaveInventory.getInstance().addandCreateInvSmoker());
            } else {
                if (!stack.getOrCreateTag().contains("TAG_SMOKER_INVENTORY")) {
                    stack.getOrCreateTag().putInt("TAG_SMOKER_INVENTORY", WorldSaveInventory.getInstance().addandCreateInvSmoker());
                }

                if ( !WorldSaveInventory.getInstance().getInventoryFurnace(stack.getOrCreateTag().getInt("TAG_SMOKER_INVENTORY")).isHoldHeat()) {
                    WorldSaveInventory.getInstance().getInventoryFurnace(stack.getOrCreateTag().getInt("TAG_SMOKER_INVENTORY")).setHoldHeat(true);
                }
//                if ( WorldSaveInventory.getInstance().getInventoryFurnace(stack.getOrCreateTag().getInt("TAG_FURNACE_INVENTORY")).getIncreaseSpeed() != EnchantmentHelper.getEnchantmentLevel(EnchantInit.FurnaceSpeed, stack)) {
//                    WorldSaveInventory.getInstance().getInventoryFurnace(stack.getOrCreateTag().getInt("TAG_FURNACE_INVENTORY")).setIncreaseSpeed(EnchantmentHelper.getEnchantmentLevel(EnchantInit.FurnaceSpeed, stack));
//
                if (stack.getOrCreateTag().contains("TAG_SMOKER_HOLDHEAT")) {
                    //stack.addEnchantment(EnchantInit.HeadHold, 1);
                    stack.getOrCreateTag().remove("TAG_SMOKER_HOLDHEAT");
                }
//                if (stack.getTag().contains("TAG_FURNACE_INCREASE_SPEED")) {
//                    int nr = stack.getOrCreateTag().getInt("TAG_FURNACE_INCREASE_SPEED");
//                    switch(nr) {
//                        case 4:
//                            stack.enchant(EnchantInit.FurnaceSpeed, 1);
//                            break;
//                        case 12:
//                            stack.enchant(EnchantInit.FurnaceSpeed, 3);
//                            break;
//                        case 36:
//                            stack.enchant(EnchantInit.FurnaceSpeed, 5);
//                    }
//
//                    stack.getOrCreateTag().remove("TAG_FURNACE_INCREASE_SPEED");
//                }

                SmokerInventory inventory = WorldSaveInventory.getInstance().getInventorySmoker(((ServerPlayerEntity)pEntity).getMainHandItem().getOrCreateTag().getInt("TAG_SMOKER_INVENTORY"));

                if(!inventory.isEmpty() && inventory.getItem(0) != null && inventory.getItem(1) != null){
                    inventory.tick();
                }
            }

        }
    }

    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            SmokerInventory inventory = WorldSaveInventory.getInstance().getInventorySmoker(pPlayer.getMainHandItem().getOrCreateTag().getInt("TAG_SMOKER_INVENTORY"));
            pPlayer.openMenu(inventory);

        }
        return super.use(pLevel, pPlayer, pHand);
    }


}
