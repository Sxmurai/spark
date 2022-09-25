package wtf.spark.impl.module;

public enum ModuleCategory {
    COMBAT("Combat"),
    MISCELLANEOUS("Miscellaneous"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    WORLD("World"),
    ACTIVE("Always Active");

    public final String displayName;

    ModuleCategory(String displayName) {
        this.displayName = displayName;
    }
}
