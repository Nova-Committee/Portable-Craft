package committee.nova.portablecraft.init;

import committee.nova.portablecraft.PortableCraft;
import committee.nova.portablecraft.common.containers.*;
import committee.nova.portablecraft.utils.RegistryUtil;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = PortableCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainers {

    public static ContainerType<CraftingContainer> CRAFT;
    public static ContainerType<FurnaceContainer> FURNACE;
    public static ContainerType<SmokerContainer> SMOKER;
    public static ContainerType<BlastFurnaceContainer> BLAST_FURNACE;
    public static ContainerType<SmithingTableContainer> SMITHING;
    public static ContainerType<RepairContainer> ANVIL;
    public static ContainerType<EnchantmentContainer> ENCHANTMENT;
    public static ContainerType<StonecutterContainer> STONECUTTER;
    public static ContainerType<ChestContainer> GENERIC_9x6;


    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        final IForgeRegistry<ContainerType<?>> registry = event.getRegistry();

        registry.registerAll(
               CRAFT = RegistryUtil.registerContainer("craft1", CraftingContainer::create),
                FURNACE = RegistryUtil.registerContainer("furnace1", FurnaceContainer::create),
                SMOKER = RegistryUtil.registerContainer("smoker1", SmokerContainer::create),
                BLAST_FURNACE = RegistryUtil.registerContainer("blast_furnace1", BlastFurnaceContainer::create),
                SMITHING = RegistryUtil.registerContainer("smithing_table1", SmithingTableContainer::create),
                ANVIL = RegistryUtil.registerContainer("anvil1", RepairContainer::create),
                ENCHANTMENT = RegistryUtil.registerContainer("enchantment_table1", EnchantmentContainer::create),
                STONECUTTER = RegistryUtil.registerContainer("stone_cutter1", StonecutterContainer::create),
                GENERIC_9x6 = RegistryUtil.registerContainer("generic1_9x6", ChestContainer::sixRows)

        );
    }


}
