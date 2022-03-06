package committee.nova.portablecraft.init;

import committee.nova.portablecraft.PortableCraft;
import committee.nova.portablecraft.common.configs.ModConfig;
import committee.nova.portablecraft.common.items.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 20:20
 * Version: 1.0
 */
@Mod.EventBusSubscriber(modid = PortableCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    public static Item Craft1;
    public static Item Furnace1;
    public static Item EnderChest1;
    public static Item Smoker1;
    public static Item BlastFurnace1;
    public static Item SmithingTable1;
    public static Item Anvil1;
    public static Item BrewingStand1;
    public static Item EnchantmentSable1;
    public static Item Stonecutter1;
    public static Item Chest1;
    public static Item Bed1;
    public static Item Portable;
    public static Item EnchantmentEdit;

    public static Item Debug;


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        if (ModConfig.COMMON.craft_workbench.get()) {
            registry.register(Craft1 = new CraftingItem());
        }
        if (ModConfig.COMMON.craft_blast_furnace.get()) {
            registry.register(BlastFurnace1 = new BlastFurnaceItem());
        }
        if (ModConfig.COMMON.craft_brewing_stand.get()) {
            registry.register(BrewingStand1 = new BrewingStandItem());
        }
        if (ModConfig.COMMON.craft_anvil.get()) {
            registry.register(Anvil1 = new AnvilItem());
        }
        if (ModConfig.COMMON.craft_chest.get()) {
            registry.register(Chest1 = new ChestItem());
        }
        if (ModConfig.COMMON.craft_enchantment_table.get()) {
            registry.register(EnchantmentSable1 = new EnchantmentTableItem());
        }
        if (ModConfig.COMMON.craft_enderchest.get()) {
            registry.register(EnderChest1 = new EnderChestItem());
        }
        if (ModConfig.COMMON.craft_smoker.get()) {
            registry.register(Smoker1 = new SmokerItem());
        }
        if (ModConfig.COMMON.craft_stone_cutter.get()) {
            registry.register(Stonecutter1 = new StonecutterItem());
        }
        if (ModConfig.COMMON.craft_smithing_table.get()) {
            registry.register(SmithingTable1 = new SmithingTableItem());
        }
        if (ModConfig.COMMON.craft_furnace.get()) {
            registry.register(Furnace1 = new FurnaceItem());
        }
        if (ModConfig.COMMON.craft_enchantment_edit.get()) {
            registry.register(EnchantmentEdit = new EnchantmentEditItem());
        }
        registry.registerAll(
                Portable = new PortableItem()
                //Bed1 = new BedItem()
        );

    }
}
