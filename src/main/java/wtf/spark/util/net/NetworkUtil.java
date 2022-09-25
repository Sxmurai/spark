package wtf.spark.util.net;

import net.minecraft.network.Packet;
import wtf.spark.asm.mixins.vanilla.net.IMixinNetworkManager;
import wtf.spark.util.core.ClientImpl;

public class NetworkUtil implements ClientImpl {

    public static void sendNoEvent(Packet<?> packetIn) {
        ((IMixinNetworkManager) mc.player.connection.getNetworkManager()).dispatchPacket(packetIn, null);
    }

}
