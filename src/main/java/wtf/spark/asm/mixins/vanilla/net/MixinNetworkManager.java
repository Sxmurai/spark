package wtf.spark.asm.mixins.vanilla.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.spark.core.Spark;
import wtf.spark.impl.event.PacketEvent;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "dispatchPacket", at = @At("HEAD"), cancellable = true)
    public void dispatchPacket(Packet<?> inPacket, GenericFutureListener<? extends Future<? super Void>>[] futureListeners, CallbackInfo info) {
        if (Spark.BUS.post(new PacketEvent(PacketEvent.Direction.CLIENT, inPacket))) {
            info.cancel();
        }
    }

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet<?> p_channelRead0_2_, CallbackInfo info) {
        if (Spark.BUS.post(new PacketEvent(PacketEvent.Direction.SERVER, p_channelRead0_2_))) {
            info.cancel();
        }
    }
}
