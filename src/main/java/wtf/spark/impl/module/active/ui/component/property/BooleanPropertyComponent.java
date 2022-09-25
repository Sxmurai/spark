package wtf.spark.impl.module.active.ui.component.property;

import wtf.spark.impl.property.Property;
import wtf.spark.util.core.audio.AudioUtil;
import wtf.spark.util.render.RenderUtil;
import wtf.spark.util.render.component.Component;

import java.awt.*;

public class BooleanPropertyComponent extends Component {
    private final Property<Boolean> property;

    public BooleanPropertyComponent(Property<Boolean> property) {
        this.property = property;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if (property.getValue()) {
            RenderUtil.drawRect(x, y, width, height, new Color(143, 100, 229).getRGB());
        }

        mc.fontRenderer.drawStringWithShadow(property.getName(), (float) (x + 2.3), (float) (y + (height / 2.0) - (mc.fontRenderer.FONT_HEIGHT / 2.0f)), -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isInBounds(mouseX, mouseY) && button == 0) {
            AudioUtil.playClickSound();
            property.setValue(!property.getValue());
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

    }
}
