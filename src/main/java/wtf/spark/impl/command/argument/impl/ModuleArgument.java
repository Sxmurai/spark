package wtf.spark.impl.command.argument.impl;

import wtf.spark.core.Spark;
import wtf.spark.impl.command.argument.Argument;
import wtf.spark.impl.module.Module;

public class ModuleArgument extends Argument {
    public ModuleArgument(String name) {
        super(name);
    }

    @Override
    public boolean parse(String part) {

        for (Module module : Spark.getInstance().getModuleManager().moduleList) {
            if (module.getName().equalsIgnoreCase(part)) {
                setValue(module);
                return true;
            }

            for (String alias : module.getAliases()) {
                if (alias.equalsIgnoreCase(part)) {
                    setValue(module);
                    return true;
                }
            }
        }

        setValue(null);
        return false;
    }
}
