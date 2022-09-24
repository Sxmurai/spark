package wtf.spark.impl.module.miscellaneous;

import me.bush.eventbus.annotation.EventListener;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ChatType;
import wtf.spark.impl.event.PacketEvent;
import wtf.spark.impl.module.ModuleCategory;
import wtf.spark.impl.module.ToggableModule;

import java.nio.charset.StandardCharsets;

public class AntiCrash extends ToggableModule {
    public AntiCrash() {
        super("Anti Crash", new String[]{"anticrash", "anticrasher"}, ModuleCategory.MISCELLANEOUS);
        setRunning(true);
        setDrawn(false);
    }

    @EventListener
    public void onPacket(PacketEvent event) {
        if (event.isServerBound() && event.getPacket() instanceof SPacketChat) {
            SPacketChat packet = event.getPacket();
            if (packet.getType().equals(ChatType.CHAT)) {
                String msg = packet.getChatComponent().getUnformattedText();
                byte[] b = msg.getBytes(StandardCharsets.UTF_8);

                // a full 256 message with normal characters had a size of around 268, so this should work? @todo
                if (b.length > 270) {
                    print("Canceled message because of its large size. (" + b.length + ").");
                    event.setCancelled(true);
                }
            }
        }
    }
}
