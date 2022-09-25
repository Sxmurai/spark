package wtf.spark.impl.module.active.ui.component.property;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import wtf.spark.impl.keybind.Keybind;
import wtf.spark.impl.keybind.KeybindType;
import wtf.spark.util.core.audio.AudioUtil;
import wtf.spark.util.render.RenderUtil;
import wtf.spark.util.render.component.Component;

import java.awt.*;

public class BindPropertyComponent extends Component {
    private final Keybind keybind;
    private boolean listening = false;

    public BindPropertyComponent(Keybind keybind) {
        this.keybind = keybind;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //RenderUtil.drawRect(x, y, width, height, new Color(16, 15, 15, 182).getRGB());
        mc.fontRenderer.drawStringWithShadow(listening ? "Listening..." : "Bind: ", (float) (x + 2.3), (float) (y + (height / 2.0) - (mc.fontRenderer.FONT_HEIGHT / 2.0f)), -1);

        if (!listening) {
            String s = "";
            if (keybind.getType().equals(KeybindType.MOUSE)) {
                s = Mouse.getButtonName(keybind.getCode());
            } else {
                s = Keyboard.getKeyName(keybind.getCode());
            }

            mc.fontRenderer.drawStringWithShadow(s, (float) (((x + width) - mc.fontRenderer.getStringWidth(s)) - 2.3), (float) (y + (height / 2.0) - (mc.fontRenderer.FONT_HEIGHT / 2.0f)), 8421504);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isInBounds(mouseX, mouseY)) {
            AudioUtil.playClickSound();
            listening = !listening;
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
        if (listening) {
            listening = false;
            keybind.setCode(keyCode == Keyboard.KEY_ESCAPE ? Keyboard.KEY_NONE : keyCode);
        }
    }
}
