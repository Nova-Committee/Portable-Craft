package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.menus.StonecutterContainer;
import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/21 13:13
 * Version: 1.0
 */
public class StonecutterItem extends Item {

    private static final BaseComponent CONTAINER_TITLE = new TranslatableComponent("item.portablecraft.stone_cutter1");

    public StonecutterItem() {
        super(new Item.Properties()
                .tab(ModTabs.tab)
                .stacksTo(1)
        );
        setRegistryName("stone_cutter1");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            pPlayer.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return CONTAINER_TITLE;
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player p_39956_) {
                    return new StonecutterContainer(id, playerInventory);
                }
            });
            pPlayer.awardStat(Stats.INTERACT_WITH_SMITHING_TABLE);
        }
        return super.use(pLevel, pPlayer, pHand);
    }
}
