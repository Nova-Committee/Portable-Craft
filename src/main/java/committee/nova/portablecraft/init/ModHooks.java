package committee.nova.portablecraft.init;

import committee.nova.portablecraft.common.menus.RepairContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

    public static boolean onAnvilChange(RepairContainer container, @Nonnull ItemStack left, @Nonnull ItemStack right, Container outputSlot, String name, int baseCost, Player player) {
        AnvilUpdateEvent e = new AnvilUpdateEvent(left, right, name, baseCost, player);
        if (MinecraftForge.EVENT_BUS.post(e)) return false;
        if (e.getOutput().isEmpty()) return true;

        outputSlot.setItem(0, e.getOutput());
        container.setMaximumCost(e.getCost());
        container.repairItemCountCost = e.getMaterialCost();
        return false;
    }
}
