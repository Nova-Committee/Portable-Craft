package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.containers.CraftingInventory;
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
 * Date: 2022/2/19 17:39
 * Version: 1.0
 */
public class CraftingItem extends Item {
    public CraftingItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            if (!stack.hasNbt()) {
                stack.setNbt(new NbtCompound());
                stack.getOrCreateNbt().putInt("TAG_CRAFT_INVENTORY", WorldSaveInventory.getInstance().addandCreateInvCraft());
            } else {
                if (!stack.getOrCreateNbt().contains("TAG_CRAFT_INVENTORY")) {
                    stack.getOrCreateNbt().putInt("TAG_CRAFT_INVENTORY", WorldSaveInventory.getInstance().addandCreateInvCraft());
                }

            }

        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && !user.isSneaking()) {
            CraftingInventory inventory = WorldSaveInventory.getInstance().getInventoryCraft(user.getMainHandStack().getOrCreateNbt().getInt("TAG_CRAFT_INVENTORY"));
            user.openHandledScreen(inventory);
        }
        return super.use(world, user, hand);
    }

}
