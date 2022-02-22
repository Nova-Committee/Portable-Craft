package committee.nova.portablecraft.init;

import committee.nova.portablecraft.common.containers.RepairContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;

import javax.annotation.Nonnull;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 21:28
 * Version: 1.0
 */
public class ModHooks {

    public static boolean onAnvilChange(RepairContainer container, @Nonnull ItemStack left, @Nonnull ItemStack right, IInventory outputSlot, String name, int baseCost, PlayerEntity player)
    {
        AnvilUpdateEvent e = new AnvilUpdateEvent(left, right, name, baseCost, player);
        if (MinecraftForge.EVENT_BUS.post(e)) return false;
        if (e.getOutput().isEmpty()) return true;

        outputSlot.setItem(0, e.getOutput());
        container.setMaximumCost(e.getCost());
        container.repairItemCountCost = e.getMaterialCost();
        return false;
    }
}
