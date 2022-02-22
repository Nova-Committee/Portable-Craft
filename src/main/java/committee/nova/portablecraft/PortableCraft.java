package committee.nova.portablecraft;

import committee.nova.portablecraft.common.network.PacketHandler;
import committee.nova.portablecraft.core.WorldSaveInventory;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("portablecraft")
public class PortableCraft {

    public static final String MOD_ID = "portablecraft";
    public static final Logger LOGGER = LogManager.getLogger();

    public static RecipeManager RECIPE_MANAGER;
    public static ServerWorld WORLD;
    public static MinecraftServer SERVER;


    public PortableCraft() {
        PacketHandler.registerMessage();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        SERVER = event.getServer();
        RECIPE_MANAGER = event.getServer().getRecipeManager();
        WorldSaveInventory.resetInstance();
        WorldSaveInventory.setINSTANCE(event.getServer().overworld());
    }

    @SubscribeEvent
    public void onServerStarted(FMLServerStartedEvent event){
        WORLD = event.getServer().overworld();

    }

    @SubscribeEvent
    public void onServerStopped(FMLServerStoppedEvent event) {
        WorldSaveInventory.resetInstance();
    }


}
