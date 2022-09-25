package wtf.spark.impl.module;

import wtf.spark.impl.property.PropertyContainer;
import wtf.spark.util.core.ClientImpl;
import wtf.spark.util.core.Labeled;
import wtf.spark.util.core.Printable;

public class Module extends PropertyContainer implements Labeled, Printable, ClientImpl {
    private final String name;
    private final String[] aliases;
    private final ModuleCategory category;

    public Module(String name, String[] aliases, ModuleCategory category) {
        this.name = name;
        this.aliases = aliases;
        this.category = category;
    }

    public Module(String name, String[] aliases) {
        this.name = name;
        this.aliases = aliases;
        this.category = ModuleCategory.ACTIVE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getAliases() {
        return aliases;
    }

    public ModuleCategory getCategory() {
        return category;
    }
}
