package committee.nova.portablecraft.client;

import committee.nova.portablecraft.client.screen.*;
import committee.nova.portablecraft.init.ModContainers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/6 19:47
 * Version: 1.0
 */
@Environment(EnvType.CLIENT)
public class PortablecraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ScreenRegistry.register(ModContainers.FURNACE, FurnaceScreen::new);
        ScreenRegistry.register(ModContainers.SMOKER, SmokerScreen::new);
        ScreenRegistry.register(ModContainers.BLAST_FURNACE, BlastFurnaceScreen::new);
        ScreenRegistry.register(ModContainers.GENERIC_9x6, ChestScreen::new);
        ScreenRegistry.register(ModContainers.CRAFT, CraftingScreen::new);
        ScreenRegistry.register(ModContainers.ENCHANTMENT, EnchantmentScreen::new);
        ScreenRegistry.register(ModContainers.SMITHING, SmithingTableScreen::new);
        ScreenRegistry.register(ModContainers.ANVIL, AnvilScreen::new);
        ScreenRegistry.register(ModContainers.STONECUTTER, StonecutterScreen::new);


    }
}
