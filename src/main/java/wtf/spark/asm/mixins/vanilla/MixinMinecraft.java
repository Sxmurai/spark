package wtf.spark.asm.mixins.vanilla;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.WorldClient;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.spark.core.Spark;
import wtf.spark.impl.event.ClientTickEvent;
import wtf.spark.impl.event.KeyInputEvent;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow @Final private static Logger LOGGER;
    @Shadow public WorldClient world;
    @Shadow public EntityPlayerSP player;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(GameConfiguration gameConfig, CallbackInfo info) {
        LOGGER.info("Loading " + Spark.NAME + " v" + Spark.VERSION);
        new Spark();
    }

    @Inject(
            method = "runTickKeyboard",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V"))
    public void runTickKeyboard$dispatchKeyPresses(CallbackInfo info) {
        int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
        Spark.BUS.post(new KeyInputEvent(i, Keyboard.getEventKeyState()));
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    public void runTick(CallbackInfo info) {
        if (world != null && player != null) {
            Spark.BUS.post(new ClientTickEvent());
        }
    }
}
