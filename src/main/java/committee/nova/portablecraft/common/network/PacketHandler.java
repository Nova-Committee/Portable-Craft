package committee.nova.portablecraft.common.network;

import committee.nova.portablecraft.PortableCraft;
import committee.nova.portablecraft.common.network.packets.ClickButtonPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/20 20:41
 * Version: 1.0
 */
public class PacketHandler {
    public static final String VERSION = "1.0";
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(PortableCraft.MOD_ID, "network"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        INSTANCE.registerMessage(nextID(), ClickButtonPacket.class, ClickButtonPacket::toBytes, ClickButtonPacket::new, ClickButtonPacket::handle);


    }


    public static void sendToAllPlayer(Level level, Object packet) {
        PlayerList playerList = level.getServer().getPlayerList();
        for (ServerPlayer player : playerList.getPlayers()) {
            INSTANCE.send(PacketDistributor.PLAYER.with(
                    () -> player
            ), packet);
        }
    }

    public static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {
        INSTANCE.registerMessage(nextID(), messageType, encoder, decoder, messageConsumer);
    }


}
