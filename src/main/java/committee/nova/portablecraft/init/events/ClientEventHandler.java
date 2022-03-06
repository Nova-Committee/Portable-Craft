package committee.nova.portablecraft.init.events;


import committee.nova.portablecraft.PortableCraft;
import committee.nova.portablecraft.client.screen.*;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PortableCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {

        MenuScreens.register(ModContainers.CRAFT, CraftingScreen::new);
        MenuScreens.register(ModContainers.FURNACE, FurnaceScreen::new);
        MenuScreens.register(ModContainers.SMOKER, SmokerScreen::new);
        MenuScreens.register(ModContainers.BLAST_FURNACE, BlastFurnaceScreen::new);
        MenuScreens.register(ModContainers.SMITHING, SmithingTableScreen::new);
        MenuScreens.register(ModContainers.ANVIL, AnvilScreen::new);
        MenuScreens.register(ModContainers.ENCHANTMENT, EnchantmentScreen::new);
        MenuScreens.register(ModContainers.STONECUTTER, StonecutterScreen::new);
        MenuScreens.register(ModContainers.GENERIC_9x6, ChestScreen::new);
        MenuScreens.register(ModContainers.ENCHANTMENT_EDIT, EnchantmentEditScreen::new);

    }
}
