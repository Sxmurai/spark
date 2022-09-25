package wtf.spark.impl.command.impl;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import wtf.spark.impl.command.Command;
import wtf.spark.impl.command.argument.CommandContext;
import wtf.spark.impl.command.argument.impl.number.DoubleArgument;

public class HClip extends Command {
    public HClip() {
        super(new String[]{"hclip", "horizontalclip"}, new DoubleArgument("blocks"));
    }

    @Override
    public String dispatch(CommandContext ctx) {
        double blocks = (double) ctx.getArg("blocks").getValue();
        EnumFacing facing = mc.player.getHorizontalFacing();

        mc.player.setPosition(
                mc.player.posX + (blocks * facing.getXOffset()),
                mc.player.posY,
                mc.player.posZ + (blocks * facing.getZOffset()));

        return "Tried to clip " + TextFormatting.YELLOW + blocks + TextFormatting.GRAY + " blocks.";
    }
}
