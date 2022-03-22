package committee.nova.portablecraft;

import committee.nova.portablecraft.common.network.PacketHandler;
import committee.nova.portablecraft.core.WorldSaveInventory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("portablecraft")
public class PortableCraft {

    public static final String MOD_ID = "portablecraft";
    public static final Logger LOGGER = LogManager.getLogger();


    public static RecipeManager RECIPE_MANAGER;
    public static ServerLevel WORLD;
    public static MinecraftServer SERVER;

    public PortableCraft() {
        PacketHandler.registerMessage();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);


        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        SERVER = event.getServer();
        RECIPE_MANAGER = event.getServer().getRecipeManager();
        WorldSaveInventory.resetInstance();
        WorldSaveInventory.setINSTANCE(event.getServer().overworld());
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        WORLD = event.getServer().overworld();
    }

    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        WorldSaveInventory.resetInstance();
    }

}
