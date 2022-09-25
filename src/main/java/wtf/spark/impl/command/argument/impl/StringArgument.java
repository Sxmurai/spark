package wtf.spark.impl.command.argument.impl;

import wtf.spark.impl.command.argument.Argument;

public class StringArgument extends Argument {
    public StringArgument(String name) {
        super(name);
    }

    @Override
    public boolean parse(String part) {
        setValue(part);
        return true;
    }
}
