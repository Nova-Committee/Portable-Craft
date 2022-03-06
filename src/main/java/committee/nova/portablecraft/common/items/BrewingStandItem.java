package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.containers.BrewingStandInventory;
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
 * Date: 2022/2/20 21:50
 * Version: 1.0
 */
public class BrewingStandItem extends Item {

    public BrewingStandItem() {
        super(new Properties()
                .tab(ModTabs.tab)
                .stacksTo(1)
        );
        setRegistryName("brewing_stand1");
    }


    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (!worldIn.isClientSide) {
            if (!stack.hasTag()) {
                stack.setTag(new CompoundTag());
                stack.getOrCreateTag().putInt("TAG_BREWING_STAND_INVENTORY", WorldSaveInventory.getInstance().addandCreateBrewingStand());
            } else {
                if (!stack.getOrCreateTag().contains("TAG_BREWING_STAND_INVENTORY")) {
                    stack.getOrCreateTag().putInt("TAG_BREWING_STAND_INVENTORY", WorldSaveInventory.getInstance().addandCreateBrewingStand());
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchant.BrewingStandSpeed, stack) > 0 && WorldSaveInventory.getInstance().getInventoryBrewingStand(stack.getOrCreateTag().getInt("TAG_BREWING_STAND_INVENTORY")).getIncreaseSpeed() != EnchantmentHelper.getItemEnchantmentLevel(ModEnchant.BrewingStandSpeed, stack)) {
                    WorldSaveInventory.getInstance().getInventoryBrewingStand(stack.getOrCreateTag().getInt("TAG_BREWING_STAND_INVENTORY")).setIncreaseSpeed(EnchantmentHelper.getItemEnchantmentLevel(ModEnchant.BrewingStandSpeed, stack));
                }

                if (stack.getOrCreateTag().contains("TAG_BREWING_STAND_INCREASESPEED")) {
                    int nr = stack.getOrCreateTag().getInt("TAG_BREWING_STAND_INCREASESPEED");
                    switch (nr) {
                        case 4 -> stack.enchant(ModEnchant.BrewingStandSpeed, 1);
                        case 12 -> stack.enchant(ModEnchant.BrewingStandSpeed, 3);
                        case 36 -> stack.enchant(ModEnchant.BrewingStandSpeed, 5);
                    }

                    stack.getOrCreateTag().remove("TAG_BREWING_STAND_INCREASESPEED");
                }

                BrewingStandInventory inventory = WorldSaveInventory.getInstance().getInventoryBrewingStand(((ServerPlayer) pEntity).getMainHandItem().getOrCreateTag().getInt("TAG_BREWING_STAND_INVENTORY"));

                if (!inventory.isEmpty() && inventory.isBrewable()) {
                    inventory.tick();
                }
            }

        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            BrewingStandInventory inventory = WorldSaveInventory.getInstance().getInventoryBrewingStand(pPlayer.getMainHandItem().getOrCreateTag().getInt("TAG_BREWING_STAND_INVENTORY"));
            pPlayer.openMenu(inventory);

        }
        return super.use(pLevel, pPlayer, pHand);
    }
}
