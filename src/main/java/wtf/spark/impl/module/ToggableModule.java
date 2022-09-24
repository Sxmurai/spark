package wtf.spark.impl.module;

import org.lwjgl.input.Keyboard;
import wtf.spark.core.Spark;

public class ToggableModule extends Module {
    private boolean running = false;
    private int keyBind = Keyboard.KEY_NONE;
    private boolean drawn = true;

    public ToggableModule(String name, String[] aliases, ModuleCategory category) {
        super(name, aliases, category);
    }

    protected void onEnable() {

    }

    protected void onDisable() {

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;

        if (running) {
            Spark.BUS.subscribe(this);
            onEnable();
        } else {
            Spark.BUS.unsubscribe(this);
            onDisable();
        }
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }
}
