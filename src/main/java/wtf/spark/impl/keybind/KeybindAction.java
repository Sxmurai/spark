package wtf.spark.impl.keybind;

@FunctionalInterface
public interface KeybindAction {
    void invoke(Keybind keybind);
}
