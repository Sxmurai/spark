package wtf.spark.impl.command.argument.impl.number;

import wtf.spark.impl.command.argument.Argument;

public class DoubleArgument extends Argument {
    public DoubleArgument(String name) {
        super(name);
    }

    @Override
    public boolean parse(String part) {

        try {
            setValue(Double.parseDouble(part));
            return true;
        } catch (NumberFormatException e) {
            setValue(null);
        }

        return false;
    }
}