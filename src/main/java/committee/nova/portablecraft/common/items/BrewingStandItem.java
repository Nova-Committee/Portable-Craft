package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.containers.BrewingStandInventory;
import committee.nova.portablecraft.core.WorldSaveInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 21:50
 * Version: 1.0
 */
public class BrewingStandItem extends Item {

    public BrewingStandItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            if (!stack.hasNbt()) {
                stack.setNbt(new NbtCompound());
                stack.getOrCreateNbt().putInt("TAG_BREWING_STAND_INVENTORY", WorldSaveInventory.getInstance().addandCreateBrewingStand());
            } else {
                if (!stack.getOrCreateNbt().contains("TAG_BREWING_STAND_INVENTORY")) {
                    stack.getOrCreateNbt().putInt("TAG_BREWING_STAND_INVENTORY", WorldSaveInventory.getInstance().addandCreateBrewingStand());
                }

//                if (EnchantmentHelper.getLevel(ModEnchants.BrewingStandSpeed, stack) > 0 && WorldSaveInventory.getInstance().getInventoryBrewingStand(stack.getOrCreateNbt().getInt("TAG_BREWING_STAND_INVENTORY")).getIncreaseSpeed() != EnchantmentHelper.getLevel(ModEnchants.BrewingStandSpeed, stack)) {
//                    WorldSaveInventory.getInstance().getInventoryBrewingStand(stack.getOrCreateNbt().getInt("TAG_BREWING_STAND_INVENTORY")).setIncreaseSpeed(EnchantmentHelper.getLevel(ModEnchants.BrewingStandSpeed, stack));
//                }
//
//                if (stack.getOrCreateNbt().contains("TAG_BREWING_STAND_INCREASESPEED")) {
//                    int nr = stack.getOrCreateNbt().getInt("TAG_BREWING_STAND_INCREASESPEED");
//                    switch (nr) {
//                        case 4 -> stack.addEnchantment(ModEnchants.BrewingStandSpeed, 1);
//                        case 12 -> stack.addEnchantment(ModEnchants.BrewingStandSpeed, 3);
//                        case 36 -> stack.addEnchantment(ModEnchants.BrewingStandSpeed, 5);
//                    }
//
//                    stack.getOrCreateNbt().remove("TAG_BREWING_STAND_INCREASESPEED");
//                }

                BrewingStandInventory inventory = WorldSaveInventory.getInstance().getInventoryBrewingStand(((ServerPlayerEntity) entity).getMainHandStack().getOrCreateNbt().getInt("TAG_BREWING_STAND_INVENTORY"));

                if (!inventory.isEmpty() && inventory.isBrewable()) {
                    inventory.tick();
                }
            }

        }
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && !user.isSneaking()) {
            BrewingStandInventory inventory = WorldSaveInventory.getInstance().getInventoryBrewingStand(user.getMainHandStack().getOrCreateNbt().getInt("TAG_BREWING_STAND_INVENTORY"));
            user.openHandledScreen(inventory);

        }
        return super.use(world, user, hand);
    }
}
