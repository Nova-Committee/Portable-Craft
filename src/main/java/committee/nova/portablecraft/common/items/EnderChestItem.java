package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 11:15
 * Version: 1.0
 */
public class EnderChestItem extends Item {

    private static final BaseComponent CONTAINER_TITLE = new TranslatableComponent("item.portablecraft.ender_chest1");


    public EnderChestItem() {
        super(new Item.Properties()
                .tab(ModTabs.tab)
                .stacksTo(1)
        );
        setRegistryName("ender_chest1");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        PlayerEnderChestContainer enderchestinventory = pPlayer.getEnderChestInventory();
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            pPlayer.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return CONTAINER_TITLE;
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player p_39956_) {
                    return ChestMenu.sixRows(id, playerInventory, enderchestinventory);
                }
            });
        }
        return super.use(pLevel, pPlayer, pHand);
    }
}
