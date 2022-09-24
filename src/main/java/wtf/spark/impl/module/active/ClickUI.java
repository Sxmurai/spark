package wtf.spark.impl.module.active;

import org.lwjgl.input.Keyboard;
import wtf.spark.impl.module.ModuleCategory;
import wtf.spark.impl.module.ToggableModule;
import wtf.spark.impl.module.active.ui.UIScreen;

public class ClickUI extends ToggableModule {
    public ClickUI() {
        super("Click UI", new String[]{"clickui", "clickgui", "ui"}, ModuleCategory.ACTIVE);
        setKeyBind(Keyboard.KEY_EQUALS);
    }

    @Override
    protected void onEnable() {
        if (isNull()) {
            setRunning(false);
            return;
        }

        mc.displayGuiScreen(UIScreen.getInstance());
    }


}
