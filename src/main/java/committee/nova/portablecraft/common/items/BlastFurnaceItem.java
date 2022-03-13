package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.containers.BlastFurnaceInventory;
import committee.nova.portablecraft.core.WorldSaveInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 20:26
 * Version: 1.0
 */
public class BlastFurnaceItem extends Item {


    public BlastFurnaceItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            if (!stack.hasNbt()) {
                stack.setNbt(new NbtCompound());
                stack.getOrCreateNbt().putInt("TAG_BlAST_FURNACE_INVENTORY", WorldSaveInventory.getInstance().addandCreateInvBlastFurnace());
            } else {
                if (!stack.getOrCreateNbt().contains("TAG_BlAST_FURNACE_INVENTORY")) {
                    stack.getOrCreateNbt().putInt("TAG_BlAST_FURNACE_INVENTORY", WorldSaveInventory.getInstance().addandCreateInvBlastFurnace());
                }
//
//                if (!WorldSaveInventory.getInstance().getInventoryFurnace(stack.getOrCreateNbt().getInt("TAG_BlAST_FURNACE_INVENTORY")).isHoldHeat()) {
//                    WorldSaveInventory.getInstance().getInventoryFurnace(stack.getOrCreateNbt().getInt("TAG_BlAST_FURNACE_INVENTORY")).setHoldHeat(true);
//                }
//                if (WorldSaveInventory.getInstance().getInventoryFurnace(stack.getOrCreateNbt().getInt("TAG_FURNACE_INVENTORY")).getIncreaseSpeed() != EnchantmentHelper.getLevel(ModEnchants.FurnaceSpeed, stack)) {
//                    WorldSaveInventory.getInstance().getInventoryFurnace(stack.getOrCreateNbt().getInt("TAG_FURNACE_INVENTORY")).setIncreaseSpeed(EnchantmentHelper.getLevel(ModEnchants.FurnaceSpeed, stack));
//
//                    if (stack.getOrCreateNbt().contains("TAG_BlAST_FURNACE_HOLDHEAT")) {
//                        //stack.addEnchantment(EnchantInit.HeadHold, 1);
//                        stack.getOrCreateNbt().remove("TAG_BlAST_FURNACE_HOLDHEAT");
//                    }
//                    if (stack.getOrCreateNbt().contains("TAG_BlAST_FURNACE_INCREASE_SPEED")) {
//                        int nr = stack.getOrCreateNbt().getInt("TAG_BlAST_FURNACE_INCREASE_SPEED");
//                        switch (nr) {
//                            case 4 -> stack.addEnchantment(ModEnchants.FurnaceSpeed, 1);
//                            case 12 -> stack.addEnchantment(ModEnchants.FurnaceSpeed, 3);
//                            case 36 -> stack.addEnchantment(ModEnchants.FurnaceSpeed, 5);
//                        }
//
//                        stack.getOrCreateNbt().remove("TAG_FURNACE_INCREASE_SPEED");
//                    }
//
//                }

            }

        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && !user.isSneaking()) {
            BlastFurnaceInventory inventory = WorldSaveInventory.getInstance().getInventoryBlastFurnace(user.getMainHandStack().getOrCreateNbt().getInt("TAG_BlAST_FURNACE_INVENTORY"));
            user.openHandledScreen(inventory);
        }
        return super.use(world, user, hand);
    }


}
