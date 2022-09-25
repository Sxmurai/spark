package wtf.spark.impl.command.impl;

import net.minecraft.util.text.TextFormatting;
import wtf.spark.impl.command.Command;
import wtf.spark.impl.command.argument.CommandContext;
import wtf.spark.impl.command.argument.impl.number.IntegerArgument;

public class ViewDistance extends Command {
    public ViewDistance() {
        super(new String[]{"viewdistance", "renderdistance"}, new IntegerArgument("distance"));
    }

    @Override
    public String dispatch(CommandContext ctx) {
        int distance = (int) ctx.getArg("distance").getValue();
        if (distance <= 0) {
            return "Please provide a render distance above 0.";
        }

        mc.gameSettings.renderDistanceChunks = distance;

        return "Set render distance to " + TextFormatting.YELLOW + distance + TextFormatting.GRAY + " chunks.";
    }
}
