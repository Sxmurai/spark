package wtf.spark.impl.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wtf.spark.util.core.ClientImpl;
import wtf.spark.util.core.timing.Timer;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager implements ClientImpl {

    private final List<Config> configList = new ArrayList<>();
    private final Logger LOGGER = LogManager.getLogger("Config Manager");

    public ConfigManager() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Timer timer = new Timer();
            timer.resetTime();
            LOGGER.info("Saving configs...");
            save();
            LOGGER.info("Saved confis in {}ms", (timer.getTimePassed() / Timer.NS_MS));
        }, "ConfigManager Save Thread"));
    }

    public void save() {
        configList.forEach(Config::save);
    }

    public void load() {
        configList.forEach(Config::load);
    }

    public void add(Config config) {
        configList.add(config);
    }
}
