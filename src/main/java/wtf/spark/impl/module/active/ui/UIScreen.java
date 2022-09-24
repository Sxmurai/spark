package wtf.spark.impl.module.active.ui;

import net.minecraft.client.gui.GuiScreen;
import wtf.spark.core.Spark;
import wtf.spark.impl.module.ModuleCategory;
import wtf.spark.impl.module.ToggableModule;
import wtf.spark.impl.module.active.ClickUI;

public class UIScreen extends GuiScreen {

    private static UIScreen instance;

    private UIScreen() {
        if (instance != null) {
            return;
        }

        for (ModuleCategory category : ModuleCategory.values()) {

        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        ((ToggableModule) Spark.getInstance().getModuleManager().moduleMap.get(ClickUI.class)).setRunning(false);
    }

    public static UIScreen getInstance() {
        return instance == null ? (instance = new UIScreen()) : instance;
    }
}
