package wtf.spark.impl.module.active.ui;

import net.minecraft.client.gui.GuiScreen;
import wtf.spark.core.Spark;
import wtf.spark.impl.module.Module;
import wtf.spark.impl.module.ModuleCategory;
import wtf.spark.impl.module.ToggableModule;
import wtf.spark.impl.module.active.ClickUI;
import wtf.spark.impl.module.active.ui.component.Panel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UIScreen extends GuiScreen {

    private static UIScreen instance;
    private final List<Panel> panels = new ArrayList<>();

    private UIScreen() {
        if (instance != null) {
            return;
        }

        double x = 4.0;
        for (ModuleCategory category : ModuleCategory.values()) {
            List<Module> moduleList = Spark.getInstance().getModuleManager().moduleList
                    .stream()
                    .filter((module) -> module.getCategory().equals(category))
                    .collect(Collectors.toList());

            if (moduleList.isEmpty()) {
                continue;
            }

            Panel panel = new Panel(category.displayName, moduleList);
            panel.setX(x);
            panel.setY(12.0);
            panel.setHeight(15.0);
            panel.setWidth(110.0);

            x += panel.getWidth() + 4.0;

            panels.add(panel);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        panels.forEach((panel) -> panel.drawScreen(mouseX, mouseY, partialTicks));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        panels.forEach((panel) -> panel.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        panels.forEach((panel) -> panel.keyTyped(typedChar, keyCode));
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
