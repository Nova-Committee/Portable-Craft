package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 11:15
 * Version: 1.0
 */
public class EnderChestItem extends Item{

    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("item.portablecraft.ender_chest1");


    public EnderChestItem() {
        super(new Item.Properties()
                .tab(ModTabs.tab)
                .stacksTo(1)
        );
        setRegistryName("ender_chest1");
    }


    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        EnderChestInventory enderchestinventory = pPlayer.getEnderChestInventory();
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            NetworkHooks.openGui((ServerPlayerEntity) pPlayer,  new SimpleNamedContainerProvider((p_226928_1_, p_226928_2_, p_226928_3_) -> ChestContainer.threeRows(p_226928_1_, p_226928_2_, enderchestinventory), CONTAINER_TITLE), pPlayer.blockPosition());
        }
        return super.use(pLevel, pPlayer, pHand);
    }
}
