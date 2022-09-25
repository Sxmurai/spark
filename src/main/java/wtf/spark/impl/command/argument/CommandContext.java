package wtf.spark.impl.command.argument;

import wtf.spark.impl.command.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandContext {

    private final Map<String, Argument> argumentMap = new HashMap<>();
    private final Command command;
    private final String[] rawArguments;
    private final String rawMessage;

    public CommandContext(Command command, String[] rawArguments, String rawMessage) {
        this.command = command;
        this.rawArguments = rawArguments;
        this.rawMessage = rawMessage;
    }

    public void setArg(String name, Argument argument) {
        argumentMap.put(name, argument);
    }

    public <T extends Argument> T getArg(String name) {
        return (T) argumentMap.getOrDefault(name, null);
    }

    public boolean hasArg(String name) {
        return argumentMap.containsKey(name);
    }

    public String[] getRawArguments() {
        return rawArguments;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public Command getCommand() {
        return command;
    }
}
