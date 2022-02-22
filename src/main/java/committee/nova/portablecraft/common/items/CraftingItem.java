package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.cap.CraftingCapabilityProvider;
import committee.nova.portablecraft.common.containers.CraftingContainer;
import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 17:39
 * Version: 1.0
 */
public class CraftingItem extends Item {
    public CraftingItem() {
        super(new Properties()
                .tab(ModTabs.tab)
                .stacksTo(1)
        );
        setRegistryName("craft1");
    }


    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            NetworkHooks.openGui((ServerPlayerEntity) pPlayer,  new CraftingContainer.CraftingContainerProvider(), pPlayer.blockPosition());
        }
        return super.use(pLevel, pPlayer, pHand);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CraftingCapabilityProvider();
    }
}
