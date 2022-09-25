package wtf.spark.asm.mixins.vanilla.render;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.spark.core.Spark;
import wtf.spark.impl.module.active.HUD;

@Mixin(GuiChat.class)
public class MixinGuiChat extends GuiScreen {

    @Shadow protected GuiTextField inputField;

    @Inject(method = "drawScreen", at = @At("TAIL"))
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo info) {
        String text = inputField.getText();
        if (text != null && !text.isEmpty()) {
            String prefix = Spark.getInstance().getCommandManager().getPrefix();
            if (text.startsWith(prefix)) {
                drawRect(2, height - 3, width - 2, height - 2, HUD.COLOR.getRGB());
            }
        }
    }
}
