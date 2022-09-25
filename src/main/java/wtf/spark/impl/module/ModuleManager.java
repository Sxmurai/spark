package wtf.spark.impl.module;

import wtf.spark.core.Spark;
import wtf.spark.impl.module.active.ClickUI;
import wtf.spark.impl.module.active.HUD;
import wtf.spark.impl.module.miscellaneous.AntiCrash;
import wtf.spark.impl.module.movement.PacketFlight;
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
        add(new PacketFlight());
        add(new Sprint());
    }

    private void add(Module module) {
        moduleMap.put(module.getClass(), module);
        moduleList.add(module);

        if (module instanceof ToggableModule) {
            toggableModuleList.add((ToggableModule) module);
            ((ToggableModule) module).getKeybind().setAction((s) -> {
                if (mc.currentScreen == null) {
                    ((ToggableModule) module).setRunning(!((ToggableModule) module).isRunning());
                }
            });
        } else {

            // automatically register "always active" modules to the event bus
            if (module.getCategory().equals(ModuleCategory.ACTIVE)) {
                Spark.BUS.subscribe(module);
            }
        }
    }
}
