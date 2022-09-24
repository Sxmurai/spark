package wtf.spark.util.core;

import net.minecraft.client.Minecraft;

public interface ClientImpl {

    Minecraft mc = Minecraft.getMinecraft();

    default boolean isNull() {
        return mc.player == null || mc.world == null;
    }
}
