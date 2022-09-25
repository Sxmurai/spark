package wtf.spark.impl.command.argument.impl;

import wtf.spark.core.Spark;
import wtf.spark.impl.command.Command;
import wtf.spark.impl.command.argument.Argument;

public class CommandArgument extends Argument {
    public CommandArgument(String name) {
        super(name);
    }

    @Override
    public boolean parse(String part) {

        for (Command command : Spark.getInstance().getCommandManager().getCommandList()) {

            for (String alias : command.getAliases()) {
                if (alias.equalsIgnoreCase(part)) {
                    setValue(command);
                    return true;
                }
            }
        }

        setValue(null);
        return false;
    }
}
