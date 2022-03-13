package committee.nova.portablecraft.init;


import committee.nova.portablecraft.Portablecraft;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/24 9:49
 * Version: 1.0
 */
public class ModTabs {
    public static final ItemGroup MOD_GROUP = FabricItemGroupBuilder.build(
            new Identifier(Portablecraft.MOD_ID, "tab_portablecraft"),
            () -> new ItemStack(ModItems.Portable)
    );

}
