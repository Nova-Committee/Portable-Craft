package committee.nova.portablecraft.init.events;


import committee.nova.portablecraft.PortableCraft;
import committee.nova.portablecraft.client.screen.*;
import committee.nova.portablecraft.init.ModContainers;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PortableCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {

       ScreenManager.register(ModContainers.CRAFT, CraftingScreen::new);
        ScreenManager.register(ModContainers.FURNACE, FurnaceScreen::new);
        ScreenManager.register(ModContainers.SMOKER, SmokerScreen::new);
        ScreenManager.register(ModContainers.BLAST_FURNACE, BlastFurnaceScreen::new);
        ScreenManager.register(ModContainers.SMITHING, SmithingTableScreen::new);
        ScreenManager.register(ModContainers.ANVIL, AnvilScreen::new);
        ScreenManager.register(ModContainers.ENCHANTMENT, EnchantmentScreen::new);
        ScreenManager.register(ModContainers.STONECUTTER, StonecutterScreen::new);
        ScreenManager.register(ModContainers.GENERIC_9x6, ChestScreen::new);
        ScreenManager.register(ModContainers.ENCHANTMENT_EDIT, EnchantmentEditScreen::new);

    }
}
