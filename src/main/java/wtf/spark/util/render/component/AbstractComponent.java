package wtf.spark.util.render.component;

import wtf.spark.util.core.ClientImpl;

public abstract class AbstractComponent implements ClientImpl {

    public abstract void drawScreen(int mouseX, int mouseY, float partialTicks);
    public abstract void mouseClicked(int mouseX, int mouseY, int button);
    public abstract void mouseReleased(int mouseX, int mouseY, int state);
    public abstract void mouseScroll(int mouseX, int mouseY, int scroll);
    public abstract void keyTyped(char typedChar, int keyCode);

    public static boolean isInBounds(int mouseX, int mouseY, double x, double y, double w, double h) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }
}
