package committee.nova.portablecraft.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 11:15
 * Version: 1.0
 */
public class EnderChestItem extends Item {

    private static final Text TITLE = new TranslatableText("item.portablecraft.ender_chest1");


    public EnderChestItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        EnderChestInventory enderChestInventory = user.getEnderChestInventory();
        if (!world.isClient && !user.isSneaking()) {
            user.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> GenericContainerScreenHandler.createGeneric9x3(syncId, inventory, enderChestInventory), TITLE));

        }
        return super.use(world, user, hand);
    }
}
