package wtf.spark.impl.command;

import wtf.spark.core.Spark;
import wtf.spark.impl.config.Config;
import wtf.spark.util.core.ClientImpl;
import wtf.spark.util.core.fs.FileSystemUtil;

public class CommandManager implements ClientImpl {

    private String prefix = "'";

    public CommandManager() {
        Spark.BUS.subscribe(this);

        new Config("command_prefix.txt") {

            @Override
            public void save() {
                FileSystemUtil.write(getFile(), prefix);
            }

            @Override
            public void load() {
                String content = FileSystemUtil.read(getFile());
                if (content == null || content.isEmpty()) {
                    return;
                }

                prefix = content
                        .trim()
                        .replaceAll("\n", "")
                        .replaceAll("\n\r", "");
            }
        };
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
