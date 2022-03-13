package committee.nova.portablecraft.init;

import committee.nova.portablecraft.Portablecraft;
import committee.nova.portablecraft.common.items.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/6 20:05
 * Version: 1.0
 */
public class ModItems {
    public static CraftingItem Craft1 = new CraftingItem(new FabricItemSettings().group(ModTabs.MOD_GROUP).maxCount(1));
    public static FurnaceItem Furnace1 = new FurnaceItem(new FabricItemSettings().group(ModTabs.MOD_GROUP).maxCount(1));
    public static EnderChestItem EnderChest1 = new EnderChestItem(new FabricItemSettings().group(ModTabs.MOD_GROUP).maxCount(1));
    public static SmokerItem Smoker1 = new SmokerItem(new FabricItemSettings().group(ModTabs.MOD_GROUP).maxCount(1));
    public static BlastFurnaceItem BlastFurnace1 = new BlastFurnaceItem(new FabricItemSettings().group(ModTabs.MOD_GROUP).maxCount(1));
    public static SmithingTableItem SmithingTable1 = new SmithingTableItem(new FabricItemSettings().group(ModTabs.MOD_GROUP).maxCount(1));
    public static AnvilItem Anvil1 = new AnvilItem(new FabricItemSettings().group(ModTabs.MOD_GROUP).maxCount(1));
    public static BrewingStandItem BrewingStand1 = new BrewingStandItem(new FabricItemSettings().group(ModTabs.MOD_GROUP).maxCount(1));
    public static EnchantmentTableItem EnchantmentSable1 = new EnchantmentTableItem(new FabricItemSettings().group(ModTabs.MOD_GROUP).maxCount(1));
    public static StonecutterItem Stonecutter1 = new StonecutterItem(new FabricItemSettings().group(ModTabs.MOD_GROUP).maxCount(1));
    public static ChestItem Chest1 = new ChestItem(new FabricItemSettings().group(ModTabs.MOD_GROUP).maxCount(1));
    public static PortableItem Portable = new PortableItem(new FabricItemSettings().group(ModTabs.MOD_GROUP).maxCount(1));
    //public static Item EnchantmentEdit;

    public static Item Debug;

    public static void init() {
        Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "craft1"), Craft1);
        Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "furnace1"), Furnace1);
        Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "ender_chest1"), EnderChest1);
        Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "smoker1"), Smoker1);
        Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "blast_furnace1"), BlastFurnace1);
        Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "smithing_table1"), SmithingTable1);
        Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "anvil1"), Anvil1);
        Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "brewing_stand1"), BrewingStand1);
        Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "enchantment_table1"), EnchantmentSable1);
        Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "stone_cutter1"), Stonecutter1);
        Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "portable"), Portable);
        //Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "enchantment_edit"), EnchantmentEdit);
        Registry.register(Registry.ITEM, new Identifier(Portablecraft.MOD_ID, "chest1"), Chest1);


    }
}
