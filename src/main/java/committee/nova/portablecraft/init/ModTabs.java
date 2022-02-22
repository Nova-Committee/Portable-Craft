package committee.nova.portablecraft.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/24 9:49
 * Version: 1.0
 */
public class ModTabs {

    public static ItemGroup tab = new ItemGroup("tabPortableCraft") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.DIRT);
        }

    };
}
