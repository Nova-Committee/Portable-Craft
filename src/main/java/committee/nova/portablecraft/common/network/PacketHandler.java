package committee.nova.portablecraft.common.network;

import committee.nova.portablecraft.PortableCraft;
import committee.nova.portablecraft.common.network.packets.ClickButtonPacket;
import committee.nova.portablecraft.common.network.packets.SendFurnacePacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.List;
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
        INSTANCE.registerMessage(nextID(), SendFurnacePacket.class, SendFurnacePacket::toBytes, SendFurnacePacket::new, SendFurnacePacket::handle);
        INSTANCE.registerMessage(nextID(), ClickButtonPacket.class, ClickButtonPacket::toBytes, ClickButtonPacket::new, ClickButtonPacket::handle);


    }

    public static int sendAround(World world, BlockPos pos, double radius, Object packet)
    {
        if (world.isClientSide)
        {
            throw new IllegalStateException("Can only be used on server side!");
        }

        List<PlayerEntity> players = world.getEntities(EntityType.PLAYER, getBoundingBox(pos, radius), (e) -> true);
        for (PlayerEntity e : players)
        {
            ServerPlayerEntity player = (ServerPlayerEntity) e;
            INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }

        return players.size();
    }

    public static void sendToAllPlayer(World level, Object packet){
        PlayerList playerList = level.getServer().getPlayerList();
        for (ServerPlayerEntity player : playerList.getPlayers()){
            INSTANCE.send(PacketDistributor.PLAYER.with(
                    () -> player
            ), packet);
        }
    }

    public static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer)
    {
        INSTANCE.registerMessage(nextID(), messageType, encoder, decoder, messageConsumer);
    }



    public static AxisAlignedBB getBoundingBox(BlockPos center, double radius)
    {
        return new AxisAlignedBB(center.offset(-radius, -radius, -radius), center.offset(radius, radius, radius));
    }


}
