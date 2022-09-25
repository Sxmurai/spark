package wtf.spark.impl.module.active.ui.component;

import wtf.spark.core.Spark;
import wtf.spark.impl.keybind.Keybind;
import wtf.spark.impl.module.Module;
import wtf.spark.impl.module.ToggableModule;
import wtf.spark.impl.module.active.ui.component.property.BindPropertyComponent;
import wtf.spark.impl.module.active.ui.component.property.BooleanPropertyComponent;
import wtf.spark.util.core.audio.AudioUtil;
import wtf.spark.util.render.RenderUtil;
import wtf.spark.util.render.component.Component;

import java.awt.*;

public class ModuleComponent extends Component {
    private final Module module;
    private boolean opened = false;

    public ModuleComponent(Module module) {
        this.module = module;

        Keybind keybind = Spark.getInstance().getKeybindManager().get(module.getName());
        if (keybind != null) {
            children.add(new BindPropertyComponent(keybind));
        }

        module.getPropertyList().forEach((property) -> {
            if (property.getValue() instanceof Boolean) {
                children.add(new BooleanPropertyComponent(property));
            }
        });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (module instanceof ToggableModule && ((ToggableModule) module).isRunning()) {
            RenderUtil.drawRect(x, y, width, height, new Color(143, 100, 229).getRGB());
        }

        mc.fontRenderer.drawStringWithShadow(module.getName(), (float) (x + 2.3), (float) (y + (height / 2.0) - (mc.fontRenderer.FONT_HEIGHT / 2.0f)), -1);

        String s = opened ? "-" : "+";
        mc.fontRenderer.drawStringWithShadow(s, (float) (((x + width) - mc.fontRenderer.getStringWidth(s)) - 2.3), (float) (y + (height / 2.0) - (mc.fontRenderer.FONT_HEIGHT / 2.0f)), -1);

        if (opened && !children.isEmpty()) {
            double y = getY() + height + 0.5;
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
        if (isInBounds(mouseX, mouseY, getX(), getY(), getWidth(), height)) {
            AudioUtil.playClickSound();

            if (button == 0) {
                if (module instanceof ToggableModule) {
                    ((ToggableModule) module).setRunning(!((ToggableModule) module).isRunning());
                }
            } else if (button == 1) {
                opened = !opened;
            }
        }

        if (opened) {
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
        if (opened) {
            children.forEach((child) -> child.keyTyped(typedChar, keyCode));
        }
    }

    @Override
    public double getHeight() {

        if (opened) {
            double height = 14.0 + 0.5;
            for (Component component : children) {
                height += component.getHeight() + 0.5;
            }

            return height;
        }

        return height;
    }
}
