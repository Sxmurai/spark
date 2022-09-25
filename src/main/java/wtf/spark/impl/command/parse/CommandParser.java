package wtf.spark.impl.command.parse;

import net.minecraft.util.text.TextFormatting;
import wtf.spark.impl.command.Command;
import wtf.spark.impl.command.CommandManager;
import wtf.spark.impl.command.argument.Argument;
import wtf.spark.impl.command.argument.CommandContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandParser {
    private static final String COMMAND_ARG_REGEX = " (?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
    private final CommandManager commandManager;

    public CommandParser(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public CommandParseResult parse(String message) {

        if (message.isEmpty()) {
            return null;
        }

        String[] splitArgs = message.trim()
                .substring(commandManager.getPrefix().length())
                .split(COMMAND_ARG_REGEX);

        String cmdName = splitArgs[0].replaceAll("\\s*", "");
        Command cmd = null;

        for (Command command : commandManager.getCommandList()) {
            for (String alias : command.getAliases()) {
                if (alias.equalsIgnoreCase(cmdName)) {
                    cmd = command;
                    break;
                }
            }
        }

        if (cmd == null) {
            return new CommandParseResult().setErrorMessage("Invalid command entered.");
        }

        // remove the first argument from our arg array (command name)
        splitArgs = Arrays.copyOfRange(splitArgs, 1, splitArgs.length);

        CommandContext ctx = new CommandContext(cmd, splitArgs, message);

        List<Argument> args = cmd.getArguments();
        args.forEach((a) -> a.setValue(null));

        if (args != null && !args.isEmpty()) {
            List<String> missingArgs = new ArrayList<>();

            for (int i = 0; i < args.size(); ++i) {
                Argument arg = args.get(i);

                try {
                    String part = splitArgs[i];
                    if (!arg.parse(part)) {
                        return new CommandParseResult().setErrorMessage(
                                "Invalid parse type at argument " + TextFormatting.YELLOW + arg.getName() + TextFormatting.GRAY + ".");
                    }
                } catch (IndexOutOfBoundsException ignored) {
                    if (arg.isRequired()) {
                        missingArgs.add(arg.getName());
                    }
                }

                ctx.setArg(arg.getName(), arg);
            }

            if (!missingArgs.isEmpty()) {

                return new CommandParseResult().setErrorMessage(
                        "Missing the following argument" + (missingArgs.size() > 1 ? "s" : "") + " " +
                                missingArgs.stream()
                                        .map((n) -> TextFormatting.YELLOW + n + TextFormatting.GRAY)
                                        .collect(Collectors.joining(", "))
                                + "."
                );
            }
        }

        return new CommandParseResult(ctx);
    }
}
