package committee.nova.portablecraft.init;


import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/24 9:49
 * Version: 1.0
 */
public class ModTabs {

    public static CreativeModeTab tab = new CreativeModeTab("tabPortableCraft") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.Craft1);
        }

    };
}
