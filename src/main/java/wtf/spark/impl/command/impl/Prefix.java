package wtf.spark.impl.command.impl;

import net.minecraft.util.text.TextFormatting;
import wtf.spark.core.Spark;
import wtf.spark.impl.command.Command;
import wtf.spark.impl.command.argument.Argument;
import wtf.spark.impl.command.argument.CommandContext;
import wtf.spark.impl.command.argument.impl.StringArgument;

public class Prefix extends Command {
    public Prefix() {
        super(new String[]{"prefix", "pfx", "commandprefix"}, new StringArgument("newPrefix"));
    }

    @Override
    public String dispatch(CommandContext ctx) {
        String prefix = (String) ctx.getArg("newPrefix").getValue();
        Spark.getInstance().getCommandManager().setPrefix(prefix);
        return "Set your command prefix to \"" + TextFormatting.YELLOW + prefix + TextFormatting.GRAY + "\".";
    }
}
