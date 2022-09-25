package wtf.spark.impl.command.impl;

import wtf.spark.core.Spark;
import wtf.spark.impl.command.Command;
import wtf.spark.impl.command.argument.CommandContext;
import wtf.spark.impl.command.argument.impl.CommandArgument;

import java.util.List;
import java.util.stream.Collectors;

public class Help extends Command {
    public Help() {
        super(new String[]{"help", "h", "commands", "cmds", "halp"},
                new CommandArgument("name").setRequired(false));
    }

    @Override
    public String dispatch(CommandContext ctx) {
        Command command = (Command) ctx.getArg("name").getValue();
        if (command != null) {
            return command.getSyntax();
        }

        List<Command> commandList = Spark.getInstance().getCommandManager().getCommandList();
        return "Total of " + commandList.size() + " commands: "
                + commandList.stream().map((c) -> c.getAliases()[0]).collect(Collectors.joining(", "));
    }
}
