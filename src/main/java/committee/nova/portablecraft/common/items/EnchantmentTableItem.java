package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.containers.EnchantmentContainer;
import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 21:31
 * Version: 1.0
 */
public class EnchantmentTableItem extends Item {
    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.enchant");

    public EnchantmentTableItem() {
        super(new Properties()
                .tab(ModTabs.tab)
                .stacksTo(1)
        );
        setRegistryName("enchantment_table1");
    }


    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            pPlayer.openMenu(new SimpleNamedContainerProvider((p_235576_2_, p_235576_3_, p_235576_4_) -> {
                return new EnchantmentContainer(p_235576_2_, p_235576_3_);
            }, CONTAINER_TITLE));
        }
        return super.use(pLevel, pPlayer, pHand);
    }
}
