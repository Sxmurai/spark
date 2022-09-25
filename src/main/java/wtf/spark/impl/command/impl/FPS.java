package wtf.spark.impl.command.impl;

import net.minecraft.util.text.TextFormatting;
import wtf.spark.impl.command.Command;
import wtf.spark.impl.command.argument.Argument;
import wtf.spark.impl.command.argument.CommandContext;
import wtf.spark.impl.command.argument.impl.number.IntegerArgument;

public class FPS extends Command {
    public FPS() {
        super(new String[]{"fps", "framespersec", "framelock", "fpslock"}, new IntegerArgument("frames"));
    }

    @Override
    public String dispatch(CommandContext ctx) {
        int fpsLock = (int) ctx.getArg("frames").getValue();
        if (fpsLock <= 0) {
            return "Please provide a frame limit above 0.";
        }

        mc.gameSettings.limitFramerate = fpsLock;
        return "Tried to lock your FPS to " + TextFormatting.YELLOW + fpsLock + TextFormatting.GRAY + " FPS.";
    }
}
