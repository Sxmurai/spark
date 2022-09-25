package wtf.spark.impl.command.parse;

import wtf.spark.impl.command.argument.CommandContext;

public class CommandParseResult {

    // valid parse
    private CommandContext ctx;

    // invalid parse
    private String errorMessage = null;

    public CommandParseResult(CommandContext ctx) {
        this.ctx = ctx;
    }

    public CommandParseResult() {

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public CommandParseResult setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public CommandContext getCommandContext() {
        return ctx;
    }
}
