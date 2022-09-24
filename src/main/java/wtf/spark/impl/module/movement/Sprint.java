package wtf.spark.impl.module.movement;

import me.bush.eventbus.annotation.EventListener;
import org.lwjgl.input.Keyboard;
import wtf.spark.impl.event.ClientTickEvent;
import wtf.spark.impl.module.ModuleCategory;
import wtf.spark.impl.module.ToggableModule;

public class Sprint extends ToggableModule {
    public Sprint() {
        super("Sprint", new String[]{"autosprint", "sprinter"}, ModuleCategory.MOVEMENT);
        setKeyBind(Keyboard.KEY_H);
    }

    @EventListener
    public void onTick(ClientTickEvent event) {
        if (!mc.player.isSprinting()) {

            mc.player.setSprinting(
                    !mc.player.isSneaking() &&
                            !mc.player.isHandActive() &&
                            mc.player.getFoodStats().getFoodLevel() > 6 &&
                            mc.player.movementInput.moveForward > 0.0f
            );
        }
    }
}
