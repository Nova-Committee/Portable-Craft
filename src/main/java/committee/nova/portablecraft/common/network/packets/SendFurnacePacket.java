package committee.nova.portablecraft.common.network.packets;

import committee.nova.portablecraft.core.WorldSaveInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 9:07
 * Version: 1.0
 */
public class SendFurnacePacket extends IPacket{
    final CompoundNBT nbt;

    public SendFurnacePacket(CompoundNBT nbt){
        this.nbt = nbt;
    }

    public SendFurnacePacket(PacketBuffer buffer){
        this.nbt = buffer.readNbt();
    }
    @Override
    public void toBytes(PacketBuffer buffer) {
        buffer.writeNbt(nbt);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            WorldSaveInventory.setInstance();
            WorldSaveInventory.setDataFurnace(this.nbt);
        });

        ctx.get().setPacketHandled(true);
    }
}
