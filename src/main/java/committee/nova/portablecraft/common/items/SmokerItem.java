package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.containers.SmokerInventory;
import committee.nova.portablecraft.core.WorldSaveInventory;
import committee.nova.portablecraft.init.ModEnchant;
import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

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
    public void inventoryTick(ItemStack stack, Level worldIn, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (!worldIn.isClientSide) {
            if (!stack.hasTag()) {
                stack.setTag(new CompoundTag());
                stack.getOrCreateTag().putInt("TAG_SMOKER_INVENTORY", WorldSaveInventory.getInstance().addandCreateInvSmoker());
            } else {
                if (!stack.getOrCreateTag().contains("TAG_SMOKER_INVENTORY")) {
                    stack.getOrCreateTag().putInt("TAG_SMOKER_INVENTORY", WorldSaveInventory.getInstance().addandCreateInvSmoker());
                }

                if (!WorldSaveInventory.getInstance().getInventoryFurnace(stack.getOrCreateTag().getInt("TAG_SMOKER_INVENTORY")).isHoldHeat()) {
                    WorldSaveInventory.getInstance().getInventoryFurnace(stack.getOrCreateTag().getInt("TAG_SMOKER_INVENTORY")).setHoldHeat(true);
                }
                if (WorldSaveInventory.getInstance().getInventoryFurnace(stack.getOrCreateTag().getInt("TAG_SMOKER_INVENTORY")).getIncreaseSpeed() != EnchantmentHelper.getItemEnchantmentLevel(ModEnchant.FurnaceSpeed, stack)) {
                    WorldSaveInventory.getInstance().getInventoryFurnace(stack.getOrCreateTag().getInt("TAG_SMOKER_INVENTORY")).setIncreaseSpeed(EnchantmentHelper.getItemEnchantmentLevel(ModEnchant.FurnaceSpeed, stack));

                    if (stack.getOrCreateTag().contains("TAG_SMOKER_HOLDHEAT")) {
                        stack.enchant(ModEnchant.HeadHold, 1);
                        stack.getOrCreateTag().remove("TAG_SMOKER_HOLDHEAT");
                    }
                    if (stack.getOrCreateTag().contains("TAG_SMOKER_INCREASE_SPEED")) {
                        int nr = stack.getOrCreateTag().getInt("TAG_SMOKER_INCREASE_SPEED");
                        switch (nr) {
                            case 4 -> stack.enchant(ModEnchant.FurnaceSpeed, 1);
                            case 12 -> stack.enchant(ModEnchant.FurnaceSpeed, 3);
                            case 36 -> stack.enchant(ModEnchant.FurnaceSpeed, 5);
                        }

                        stack.getOrCreateTag().remove("TAG_SMOKER_INCREASE_SPEED");
                    }

                    SmokerInventory inventory = WorldSaveInventory.getInstance().getInventorySmoker(((ServerPlayer) pEntity).getMainHandItem().getOrCreateTag().getInt("TAG_SMOKER_INVENTORY"));

                    if (!inventory.isEmpty() && inventory.getItem(0) != null && inventory.getItem(1) != null) {
                        inventory.tick();
                    }
                }
            }

        }
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            SmokerInventory inventory = WorldSaveInventory.getInstance().getInventorySmoker(pPlayer.getMainHandItem().getOrCreateTag().getInt("TAG_SMOKER_INVENTORY"));
            pPlayer.openMenu(inventory);

        }
        return super.use(pLevel, pPlayer, pHand);
    }


}
