package wtf.spark.impl.module.active;

import me.bush.eventbus.annotation.EventListener;
import wtf.spark.asm.SparkLoadingPlugin;
import wtf.spark.core.Spark;
import wtf.spark.impl.event.RenderHUDEvent;
import wtf.spark.impl.module.Module;
import wtf.spark.impl.module.ToggableModule;
import wtf.spark.impl.module.active.ui.UIScreen;
import wtf.spark.impl.property.Property;

import java.awt.*;

public class HUD extends Module {
    public static final Color COLOR = new Color(255, 255, 0);

    private final Property<Boolean> watermark = new Property<>(true, "Watermark");

    public HUD() {
        super("HUD", new String[]{"display", "aod"});
        register(watermark);
    }

    @EventListener
    public void onRenderHUD(RenderHUDEvent event) {

        // don't render if F3 menu is open or we're in our Click UI
        if (mc.gameSettings.showDebugInfo || mc.currentScreen instanceof UIScreen) {
            return;
        }

        if (watermark.getValue()) {
            int watermarkY = 2;
            if (SparkLoadingPlugin.FUTURE_LOADED) {
                watermarkY += mc.fontRenderer.FONT_HEIGHT + 1;
            }

            mc.fontRenderer.drawStringWithShadow(Spark.NAME + " v" + Spark.VERSION, 2, watermarkY, COLOR.getRGB());
        }
    }
}
