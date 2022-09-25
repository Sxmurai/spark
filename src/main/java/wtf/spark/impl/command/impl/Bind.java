package wtf.spark.impl.command.impl;

import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import wtf.spark.impl.command.Command;
import wtf.spark.impl.command.argument.Argument;
import wtf.spark.impl.command.argument.CommandContext;
import wtf.spark.impl.command.argument.impl.KeyArgument;
import wtf.spark.impl.command.argument.impl.ModuleArgument;
import wtf.spark.impl.module.Module;
import wtf.spark.impl.module.ToggableModule;

public class Bind extends Command {
    public Bind() {
        super(new String[]{"bind", "setbind", "keybind", "setkeybind"},
                new ModuleArgument("moduleName"),
                new KeyArgument("bind"));
    }

    @Override
    public String dispatch(CommandContext ctx) {

        Module module = (Module) ctx.getArg("moduleName").getValue();

        if (module instanceof ToggableModule) {
            ((ToggableModule) module).setKeyBind((int) ctx.getArg("bind").getValue());
            return "Bound "
                    + TextFormatting.YELLOW + module.getName() + TextFormatting.GRAY
                    + " to key "
                    + TextFormatting.YELLOW + Keyboard.getKeyName(((ToggableModule) module).getKeyBind()) + TextFormatting.GRAY + ".";
        }

        return "You cannot bind this module.";
    }
}
