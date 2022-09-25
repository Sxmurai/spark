package wtf.spark.util.core.io;

import org.lwjgl.input.Keyboard;

public class InputUtil {
    public static int getKeyCodeForSpecial(String special) {
        // TODO: there HAS to be a better way (no, Keyboard.getKeyIndex does not work unless it's its actual name)

        switch (special) {
            case "`":
                return Keyboard.KEY_GRAVE;

            case "-":
                return Keyboard.KEY_MINUS;

            case "_":
                return Keyboard.KEY_UNDERLINE;

            case "=":
                return Keyboard.KEY_EQUALS;

            case "+":
                return Keyboard.KEY_ADD;

            case "[":
                return Keyboard.KEY_LBRACKET;

            case "]":
                return Keyboard.KEY_RBRACKET;

            case ";":
                return Keyboard.KEY_SEMICOLON;

            case ":":
                return Keyboard.KEY_COLON;

            case "'":
                return Keyboard.KEY_APOSTROPHE;

            case ",":
                return Keyboard.KEY_COMMA;

            case ".":
                return Keyboard.KEY_PERIOD;

            case "\\":
                return Keyboard.KEY_BACKSLASH;

            default:
                // else
                return Keyboard.getKeyIndex(special);
        }
    }
}
