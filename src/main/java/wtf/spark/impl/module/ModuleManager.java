package wtf.spark.impl.module;

import me.bush.eventbus.annotation.EventListener;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;
import wtf.spark.core.Spark;
import wtf.spark.impl.event.KeyInputEvent;
import wtf.spark.impl.module.active.ClickUI;
import wtf.spark.impl.module.active.HUD;
import wtf.spark.impl.module.miscellaneous.AntiCrash;
import wtf.spark.impl.module.movement.Sprint;
import wtf.spark.util.core.ClientImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager implements ClientImpl {

    public final Map<Class<? extends Module>, Module> moduleMap = new HashMap<>();
    public final List<Module> moduleList = new ArrayList<>();
    public final List<ToggableModule> toggableModuleList = new ArrayList<>();

    public ModuleManager() {
        Spark.BUS.subscribe(this);

        add(new ClickUI());
        add(new HUD());
        add(new AntiCrash());
        add(new Sprint());


    }

    @EventListener
    public void onKeyInput(KeyInputEvent event) {
        if (mc.currentScreen == null && event.getKeyCode() != Keyboard.KEY_NONE && !event.isState()) {

            toggableModuleList.forEach((module) -> {

                if (module.getKeyBind() == event.getKeyCode()) {
                    module.setRunning(!module.isRunning());
                }
            });
        }
    }

    private void add(Module module) {
        moduleMap.put(module.getClass(), module);
        moduleList.add(module);

        if (module instanceof ToggableModule) {
            toggableModuleList.add((ToggableModule) module);
        } else {

            // automatically register "always active" modules to the event bus
            if (module.getCategory().equals(ModuleCategory.ACTIVE)) {
                Spark.BUS.subscribe(module);
            }
        }
    }
}
