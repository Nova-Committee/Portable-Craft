package committee.nova.portablecraft.common.network.packets;

import committee.nova.portablecraft.common.containers.EnchantmentEditContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/27 8:58
 * Version: 1.0
 */
public class ClickButtonPacket extends IPacket {
    private final int type;

    public ClickButtonPacket(int type) {
        this.type = type;
    }

    public ClickButtonPacket(PacketBuffer buffer) {
        this.type = buffer.readInt();
    }


    @Override
    public void toBytes(PacketBuffer buffer) {
        buffer.writeInt(type);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null && player.containerMenu instanceof EnchantmentEditContainer) {
                int type = this.type;
                EnchantmentEditContainer container = (EnchantmentEditContainer) player.containerMenu;
                switch (type) {
                    case 0:
                        container.previous();
                        break;
                    case 1:
                        container.next();
                        break;
                    case 2:
                        container.take();
                }
            }

        });
        ctx.get().setPacketHandled(true);
    }
}
