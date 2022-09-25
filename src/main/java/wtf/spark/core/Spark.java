package wtf.spark.core;

import me.bush.eventbus.bus.EventBus;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import wtf.spark.impl.account.AccountManager;
import wtf.spark.impl.command.CommandManager;
import wtf.spark.impl.config.ConfigManager;
import wtf.spark.impl.keybind.KeybindManager;
import wtf.spark.impl.manager.TickManager;
import wtf.spark.impl.module.ModuleManager;
import wtf.spark.util.core.timing.Timer;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Spark {

    private static Spark INSTANCE;

    public static final String NAME = "Spark";
    public static final String VERSION = "1.0.0";

    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final EventBus BUS = new EventBus();

    public static final Executor SPARK_EXECUTOR = Executors.newScheduledThreadPool(1);

    public static File DATA_DIR;

    private ConfigManager configManager;
    private KeybindManager keybindManager;
    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private AccountManager accountManager;
    private TickManager tickManager;

    public Spark() {
        if (INSTANCE != null) {
            LOGGER.fatal("Tried to create a new instance of Spark!");
            return;
        }

        // set our instance
        INSTANCE = this;
        LOGGER.info("Loading {} v{}", NAME, VERSION);

        Timer timer = new Timer();
        timer.resetTime();

        DATA_DIR = new File(Minecraft.getMinecraft().gameDir, "spark");
        if (!DATA_DIR.exists()) {
            LOGGER.info("Created directory {}", (DATA_DIR.mkdir() ? "successfully" : "unsuccessfully"));
        }

        configManager = new ConfigManager();
        keybindManager = new KeybindManager();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        accountManager = new AccountManager();
        tickManager = new TickManager();

        LOGGER.info("Loaded {} v{} in {}ms", NAME, VERSION, (timer.getTimePassed() / Timer.NS_MS));

        configManager.load();
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public KeybindManager getKeybindManager() {
        return keybindManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public TickManager getTickManager() {
        return tickManager;
    }

    public static Spark getInstance() {
        return INSTANCE;
    }
}
