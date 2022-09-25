package wtf.spark.impl.command;

import com.google.common.collect.Lists;
import net.minecraft.util.text.TextFormatting;
import wtf.spark.core.Spark;
import wtf.spark.impl.command.argument.Argument;
import wtf.spark.impl.command.argument.CommandContext;
import wtf.spark.util.core.ClientImpl;
import wtf.spark.util.core.Labeled;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Command implements ClientImpl, Labeled {
    private final String[] aliases;
    private final List<Argument> arguments;

    public Command(String[] aliases, Argument... args) {
        this.aliases = aliases;
        this.arguments = Lists.newArrayList(args);
    }

    public abstract String dispatch(CommandContext ctx);

    public List<Argument> getArguments() {
        return arguments;
    }

    public boolean hasArguments() {
        return arguments != null && !arguments.isEmpty();
    }

    @Override
    public String getName() {
        return aliases[0];
    }

    @Override
    public String[] getAliases() {
        return aliases;
    }


    public String getSyntax() {
        String prefix = Spark.getInstance().getCommandManager().getPrefix();

        return prefix
                + TextFormatting.YELLOW + "[" + String.join("|", aliases) + "]"
                + TextFormatting.GRAY + " " +
                arguments
                        .stream()
                        .map((arg) ->
                                TextFormatting.YELLOW
                                        + (arg.isRequired() ? "[" : "(")
                                        + arg.getName()
                                        + (arg.isRequired() ? "]" : ")"))
                        .collect(Collectors.joining(", "));
    }
}
