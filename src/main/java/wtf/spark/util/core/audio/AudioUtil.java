package wtf.spark.util.core.audio;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import wtf.spark.util.core.ClientImpl;

public class AudioUtil implements ClientImpl {

    public static void playClickSound() {
        mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }
}
