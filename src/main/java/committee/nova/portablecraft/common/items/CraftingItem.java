package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.common.cap.CraftingCapabilityProvider;
import committee.nova.portablecraft.common.menus.CraftingContainer;
import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;

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
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            NetworkHooks.openGui((ServerPlayer) pPlayer, new CraftingContainer.CraftingContainerProvider(), pPlayer.blockPosition());
        }
        return super.use(pLevel, pPlayer, pHand);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CraftingCapabilityProvider();
    }
}
