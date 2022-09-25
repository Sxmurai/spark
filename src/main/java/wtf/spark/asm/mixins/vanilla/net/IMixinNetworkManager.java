package wtf.spark.asm.mixins.vanilla.net;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(NetworkManager.class)
public interface IMixinNetworkManager {

    @Invoker("dispatchPacket")
    void dispatchPacket(Packet<?> inPacket, @Nullable GenericFutureListener<? extends Future<? super Void >>[] futureListeners);
}
