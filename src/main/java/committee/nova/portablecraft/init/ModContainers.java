package committee.nova.portablecraft.init;

import committee.nova.portablecraft.PortableCraft;
import committee.nova.portablecraft.common.menus.*;
import committee.nova.portablecraft.utils.RegistryUtil;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = PortableCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainers {

    public static MenuType<CraftingContainer> CRAFT;
    public static MenuType<FurnaceContainer> FURNACE;
    public static MenuType<SmokerContainer> SMOKER;
    public static MenuType<BlastFurnaceContainer> BLAST_FURNACE;
    public static MenuType<SmithingTableContainer> SMITHING;
    public static MenuType<RepairContainer> ANVIL;
    public static MenuType<EnchantmentContainer> ENCHANTMENT;
    public static MenuType<StonecutterContainer> STONECUTTER;
    public static MenuType<ChestContainer> GENERIC_9x6;
    public static MenuType<EnchantmentEditContainer> ENCHANTMENT_EDIT;


    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<MenuType<?>> event) {
        final IForgeRegistry<MenuType<?>> registry = event.getRegistry();

        registry.registerAll(
                CRAFT = RegistryUtil.registerContainer("craft1", CraftingContainer::create),
                FURNACE = RegistryUtil.registerContainer("furnace1", FurnaceContainer::create),
                SMOKER = RegistryUtil.registerContainer("smoker1", SmokerContainer::create),
                BLAST_FURNACE = RegistryUtil.registerContainer("blast_furnace1", BlastFurnaceContainer::create),
                SMITHING = RegistryUtil.registerContainer("smithing_table1", SmithingTableContainer::create),
                ANVIL = RegistryUtil.registerContainer("anvil1", RepairContainer::create),
                ENCHANTMENT = RegistryUtil.registerContainer("enchantment_table1", EnchantmentContainer::create),
                STONECUTTER = RegistryUtil.registerContainer("stone_cutter1", StonecutterContainer::create),
                GENERIC_9x6 = RegistryUtil.registerContainer("generic1_9x6", ChestContainer::sixRows),
                ENCHANTMENT_EDIT = RegistryUtil.registerContainer("enchantment_edit_table", EnchantmentEditContainer::create)

        );
    }


}
