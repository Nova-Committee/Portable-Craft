package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.PortableCraft;
import committee.nova.portablecraft.core.WorldSaveInventory;
import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

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

    @Override
    public ActionResultType useOn(ItemUseContext pContext) {
        World world = pContext.getLevel();
        if (!world.isClientSide && pContext.getPlayer() != null){
            PortableCraft.LOGGER.info(WorldSaveInventory.inventoryFurnaces.get(1).isEmpty());
            PortableCraft.LOGGER.info(WorldSaveInventory.inventoryFurnaces.get(1).getContainerSize());
            PortableCraft.LOGGER.info(WorldSaveInventory.inventoryFurnaces.get(1).getMaxStackSize());
            PortableCraft.LOGGER.info(WorldSaveInventory.inventoryFurnaces.get(1).getItem(0));


            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }
}
