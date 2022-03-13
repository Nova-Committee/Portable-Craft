package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.menus.AnvilContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 20:59
 * Version: 1.0
 */
public class SmithingTableItem extends Item {

    private static final Text TITLE = new TranslatableText("container.upgrade");

    public SmithingTableItem(Settings settings) {
        super(settings);
    }

    ;


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && !user.isSneaking()) {
            user.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new AnvilContainer(syncId, inventory), TITLE));
            user.incrementStat(Stats.INTERACT_WITH_SMITHING_TABLE);
        }

        return super.use(world, user, hand);
    }
}
