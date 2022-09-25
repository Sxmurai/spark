package wtf.spark.impl.module.active.ui.component;

import wtf.spark.impl.module.Module;
import wtf.spark.util.render.RenderUtil;
import wtf.spark.util.render.component.Component;

import java.awt.*;
import java.util.List;

public class Panel extends Component {
    private final String name;

    public Panel(String name, List<Module> moduleList) {
        this.name = name;
        moduleList.forEach((module) -> getChildren().add(new ModuleComponent(module)));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(x, y, width, getTotalHeight(), new Color(37, 35, 35).getRGB());
        mc.fontRenderer.drawStringWithShadow(name, (float) (x + 2.3), (float) (y + (getHeight() / 2.0) - (mc.fontRenderer.FONT_HEIGHT / 2.0f)), -1);

        if (!children.isEmpty()) {
            double y = getY() + getHeight() + 0.5;
            for (Component component : children) {
                component.setX(getX() + 1.0);
                component.setY(y);
                component.setHeight(14.0);
                component.setWidth(getWidth() - 2.0);

                component.drawScreen(mouseX, mouseY, partialTicks);

                y += component.getHeight() + 0.5;
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isInBounds(mouseX, mouseY)) {

        } else {
            children.forEach((child) -> child.mouseClicked(mouseX, mouseY, button));
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void mouseScroll(int mouseX, int mouseY, int scroll) {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        children.forEach((child) -> child.keyTyped(typedChar, keyCode));
    }

    private double getTotalHeight() {
        double height = getHeight() + 0.5;
        for (Component component : children) {
            height += component.getHeight() + 0.5;
        }

        return height;
    }
}
