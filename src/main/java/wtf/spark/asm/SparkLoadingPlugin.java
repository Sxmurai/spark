package wtf.spark.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import wtf.spark.core.Spark;

import javax.annotation.Nullable;
import java.util.Map;

public class SparkLoadingPlugin implements IFMLLoadingPlugin {
    public static boolean FUTURE_LOADED = false;
    public static boolean FORGE_LOADED = false;

    public SparkLoadingPlugin() {
        Spark.LOGGER.info("Loading spark mixins...");

        MixinBootstrap.init();
        MixinEnvironment.getCurrentEnvironment().setObfuscationContext("searge");
        MixinEnvironment.getCurrentEnvironment().setSide(MixinEnvironment.Side.CLIENT);

        try {
            Class.forName("net.futureclient.loader.launch.launchwrapper.LaunchWrapperEntryPoint");
            FUTURE_LOADED = true;
            Spark.LOGGER.info("Detected Future Client");
        } catch (ClassNotFoundException ignored) {

        }

        try {
            Class.forName("net.minecraftforge.classloading.FMLForgePlugin");
            FORGE_LOADED = true;
            Spark.LOGGER.info("Detected FML");
            Mixins.addConfiguration("mixins.spark.forge.json");
        } catch (ClassNotFoundException ignored) {

        }

        Mixins.addConfiguration("mixins.spark.vanilla.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
