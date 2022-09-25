package wtf.spark.impl.command.argument.impl.number;

import wtf.spark.impl.command.argument.Argument;

public class IntegerArgument extends Argument {
    public IntegerArgument(String name) {
        super(name);
    }

    @Override
    public boolean parse(String part) {

        try {
            setValue(Integer.parseInt(part));
            return true;
        } catch (NumberFormatException e) {
            setValue(null);
        }

        return false;
    }
}
