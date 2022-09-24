package wtf.spark.asm.mixins.vanilla.render;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.spark.impl.account.ui.AccountManagerScreen;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {

    @Inject(method = "initGui", at = @At("RETURN"))
    public void initGui(CallbackInfo info) {
        int j = this.height / 4 + 48;
        addButton(new GuiButton(69420, width / 2 - 100, j + 72 + 12, 98, 20, "Account Manager"));
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    public void actionPreformed(GuiButton button, CallbackInfo info) {
        if (button.id == 69420) {
            mc.displayGuiScreen(new AccountManagerScreen());
            info.cancel();
        }
    }
}
