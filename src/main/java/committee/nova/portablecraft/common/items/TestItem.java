package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.world.item.Item;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 17:18
 * Version: 1.0
 */
public class TestItem extends Item {
    public TestItem() {
        super(new Properties().stacksTo(1).tab(ModTabs.tab)

        );
        setRegistryName("debug");
    }


}
