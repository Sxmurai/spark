package wtf.spark.impl.command.argument;

import wtf.spark.util.core.Labeled;

public abstract class Argument implements Labeled {
    private final String name;
    private Object value;

    private boolean required = true;

    public Argument(String name) {
        this.name = name;
    }

    public abstract boolean parse(String part);

    public Object getValue() {
        return value;
    }

    public Argument setValue(Object value) {
        this.value = value;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public Argument setRequired(boolean required) {
        this.required = required;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}
