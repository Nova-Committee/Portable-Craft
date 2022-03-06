package committee.nova.portablecraft.common.network.packets;

import committee.nova.portablecraft.common.menus.EnchantmentEditContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

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

    public ClickButtonPacket(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
    }


    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(type);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
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
