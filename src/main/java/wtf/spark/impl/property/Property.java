package wtf.spark.impl.property;

import wtf.spark.util.core.Labeled;
import wtf.spark.util.render.text.TextUtil;

import java.util.function.Supplier;

public class Property<T> implements Labeled {

    private T value;
    private final String[] aliases;
    private final Number min, max;

    private Supplier<Boolean> visibility = null;

    public Property(T value, String... aliases) {
        this(value, null, null, aliases);
    }

    public Property(T value, Number min, Number max, String... aliases) {
        this.aliases = aliases;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public Property setVisibility(Supplier<Boolean> visibility) {
        this.visibility = visibility;
        return this;
    }

    public boolean isVisible() {
        return visibility == null || visibility.get();
    }

    public T getValue() {
        return value;
    }

    public String getDisplay() {
        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof Number) {
            return value.toString();
        } else if (value instanceof Enum<?>) {
            return TextUtil.formatEnumName((Enum<?>) value);
        }

        return value.toString();
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return aliases[0];
    }

    @Override
    public String[] getAliases() {
        return aliases;
    }

}
