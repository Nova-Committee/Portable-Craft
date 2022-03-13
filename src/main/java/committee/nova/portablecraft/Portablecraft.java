package committee.nova.portablecraft;

import committee.nova.portablecraft.common.config.ModConfig;
import committee.nova.portablecraft.core.WorldSaveInventory;
import committee.nova.portablecraft.init.ModContainers;
import committee.nova.portablecraft.init.ModEnchants;
import committee.nova.portablecraft.init.ModItems;
import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/6 19:47
 * Version: 1.0
 */
public class Portablecraft implements ModInitializer {
    public static final String MOD_ID = "portablecraft";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ModConfig CONFIG = OmegaConfig.register(ModConfig.class);
    public static MinecraftServer SERVER = null;
    public static ServerWorld WORLD;


    static {
        ModContainers.init();

    }

    @Override
    public void onInitialize() {
        ModItems.init();
        ModEnchants.init();
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
        ServerWorldEvents.LOAD.register(this::onWorldLoad);
        ServerWorldEvents.UNLOAD.register(this::onWorldUnLoad);


    }

    public MinecraftServer getServer() {
        return SERVER;
    }


    private void onServerStarted(MinecraftServer server) {
        SERVER = server;
    }

    private void onWorldLoad(MinecraftServer server, ServerWorld world) {
        WorldSaveInventory.resetInstance();
        WorldSaveInventory.setINSTANCE(world);
    }

    private void onWorldUnLoad(MinecraftServer server, ServerWorld world) {
        WorldSaveInventory.resetInstance();
    }

}
