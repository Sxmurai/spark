package wtf.spark.impl.command.argument.impl;

import org.lwjgl.input.Keyboard;
import wtf.spark.impl.command.argument.Argument;

public class KeyArgument extends Argument {
    public KeyArgument(String name) {
        super(name);
    }

    @Override
    public boolean parse(String part) {
        try {

            int keyCode = Integer.parseInt(part);
            if (keyCode > 256 || keyCode < 0) {
                keyCode = Keyboard.KEY_NONE;
            }

            setValue(keyCode);
            return true;
        } catch (NumberFormatException ignored) {
            setValue(Keyboard.getKeyIndex(part.toUpperCase()));
            return true;
        }
    }
}
