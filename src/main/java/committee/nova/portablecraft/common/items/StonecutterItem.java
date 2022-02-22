package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.containers.StonecutterContainer;
import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/21 13:13
 * Version: 1.0
 */
public class StonecutterItem extends Item {

    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("item.portablecraft.stone_cutter1");

    public StonecutterItem() {
        super(new Item.Properties()
                .tab(ModTabs.tab)
                .stacksTo(1)
        );
        setRegistryName("stone_cutter1");
    }

    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            pPlayer.openMenu(new SimpleNamedContainerProvider((p_235576_2_, p_235576_3_, p_235576_4_) -> {
                return new StonecutterContainer(p_235576_2_, p_235576_3_);
            }, CONTAINER_TITLE));
            pPlayer.awardStat(Stats.INTERACT_WITH_SMITHING_TABLE);
        }
        return super.use(pLevel, pPlayer, pHand);
    }
}
