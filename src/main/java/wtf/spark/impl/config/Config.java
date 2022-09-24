package wtf.spark.impl.config;

import wtf.spark.core.Spark;
import wtf.spark.util.core.ClientImpl;

import java.io.File;
import java.io.IOException;

public abstract class Config implements ClientImpl {

    private final File file;
    private final boolean isDirectory;

    public Config(String name) {
        isDirectory = name.endsWith("/");
        file = new File(Spark.DATA_DIR, (isDirectory ? name.replaceAll("/", "") : name));

        if (!file.exists()) {
            if (isDirectory) {
                file.mkdir();
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Spark.getInstance().getConfigManager().add(this);
    }

    public abstract void save();
    public abstract void load();

    public File getFile() {
        return file;
    }

    public boolean isDirectory() {
        return isDirectory;
    }
}
