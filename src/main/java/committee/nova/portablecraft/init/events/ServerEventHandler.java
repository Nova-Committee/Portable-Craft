package committee.nova.portablecraft.init.events;

import committee.nova.portablecraft.core.WorldSaveInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 8:48
 * Version: 1.0
 */
@Mod.EventBusSubscriber
public class ServerEventHandler {

    @SubscribeEvent
    public void handleSleepLocationCheck(SleepingLocationCheckEvent event) {
        if (WorldSaveInventory.getInstance().isSleepingPlayer()) {
            event.setResult(Event.Result.ALLOW);
        }

    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event){
        if (event.phase == TickEvent.Phase.START) {


//            WorldSaveInventory.getInstance();
//            Iterator<IInventory> inventoryFurnaces = WorldSaveInventory.inventoryFurnaces.iterator();
//
//            CompoundNBT nbt;
//            while(inventoryFurnaces.hasNext()) {
//                FurnaceInventory furnace = (FurnaceInventory)inventoryFurnaces.next();
//                furnace.tick();
//                nbt = new CompoundNBT();
//                furnace.saveClient(nbt);
//                if (Objects.requireNonNull(event.world.getServer()).getPlayerCount() != 0) {
//                    PacketHandler.sendToAllPlayer(event.world, new SendFurnacePacket(nbt));
//                    //PacketHandler.INSTANCE.sendTo(new SendFurnacePacket(nbt), new NetworkManager(PacketDirection.SERVERBOUND), NetworkDirection.PLAY_TO_CLIENT);
//                }
//            }
        }
    }

    private void sleeping(ServerPlayerEntity player) {
        BlockPos pos = player.blockPosition();
        player.startSleeping(pos);
    }
}
