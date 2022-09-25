package wtf.spark.impl.module;

import wtf.spark.core.Spark;
import wtf.spark.impl.keybind.Keybind;

public class ToggableModule extends Module {
    private boolean running = false;
    private boolean drawn = true;

    public ToggableModule(String name, String[] aliases, ModuleCategory category) {
        super(name, aliases, category);
        Spark.getInstance().getKeybindManager().create(name);
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

    public Keybind getKeybind() {
        return Spark.getInstance().getKeybindManager().get(getName());
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }
}
