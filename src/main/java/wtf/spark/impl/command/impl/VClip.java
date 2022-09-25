package wtf.spark.impl.command.impl;

import net.minecraft.util.text.TextFormatting;
import wtf.spark.impl.command.Command;
import wtf.spark.impl.command.argument.Argument;
import wtf.spark.impl.command.argument.CommandContext;
import wtf.spark.impl.command.argument.impl.number.DoubleArgument;
import wtf.spark.impl.command.argument.impl.number.IntegerArgument;

public class VClip extends Command {
    public VClip() {
        super(new String[]{"vclip", "verticalclip"}, new DoubleArgument("blocks"));
    }

    @Override
    public String dispatch(CommandContext ctx) {
        double blocks = (double) ctx.getArg("blocks").getValue();
        mc.player.setPosition(mc.player.posX, mc.player.posY + blocks, mc.player.posZ);
        return "Tried to clip " + (blocks < 0.0 ? "down" : "up") + " " + TextFormatting.YELLOW + blocks + TextFormatting.GRAY + " blocks.";
    }
}
