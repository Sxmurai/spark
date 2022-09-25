package wtf.spark.impl.keybind;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Keybind {

    private KeybindType type;
    private int code;

    private KeybindAction action;

    public Keybind() {
        this(KeybindType.KEYBOARD, Keyboard.KEY_NONE);
    }

    public Keybind(KeybindType type, int code) {
        this.type = type;
        this.code = code;
    }

    public KeybindType getType() {
        return type;
    }

    public void setType(KeybindType type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setAction(KeybindAction action) {
        this.action = action;
    }

    public void run() {
        if (action != null && isDown()) {
            action.invoke(this);
        }
    }

    public boolean isDown() {
        if (code <= 0) {
            return false;
        }

        if (type.equals(KeybindType.MOUSE)) {
            return Mouse.isButtonDown(code) && !Mouse.getEventButtonState();
        }

        int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
        return code == i && !Keyboard.getEventKeyState();
    }
}
