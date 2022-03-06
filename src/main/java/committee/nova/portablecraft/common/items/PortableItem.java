package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.world.item.Item;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 20:26
 * Version: 1.0
 */
public class PortableItem extends Item {


    public PortableItem() {
        super(new Properties()
                .tab(ModTabs.tab)
                .stacksTo(64)
        );
        setRegistryName("portable");
    }


}
