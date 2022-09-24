package wtf.spark.asm.mixins.forge.render;

import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.spark.core.Spark;
import wtf.spark.impl.event.RenderHUDEvent;

@Mixin(GuiIngameForge.class)
@Pseudo
public class MixinGuiInGameForge {

    @Inject(method = "renderGameOverlay", at = @At("TAIL"))
    public void renderGameOverlay(float partialTicks, CallbackInfo info) {
        Spark.BUS.post(new RenderHUDEvent(partialTicks));
    }
}
