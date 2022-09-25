package wtf.spark.impl.keybind;

import com.google.gson.*;
import me.bush.eventbus.annotation.EventListener;
import org.lwjgl.input.Keyboard;
import wtf.spark.core.Spark;
import wtf.spark.impl.config.Config;
import wtf.spark.impl.event.KeyInputEvent;
import wtf.spark.util.core.ClientImpl;
import wtf.spark.util.core.fs.FileSystemUtil;

import java.util.HashMap;
import java.util.Map;

public class KeybindManager implements ClientImpl {

    private final Map<String, Keybind> bindedFeatures = new HashMap<>();

    public KeybindManager() {
        Spark.BUS.subscribe(this);

        new Config("keybinds.json") {

            @Override
            public void save() {
                JsonArray object = new JsonArray();

                bindedFeatures.forEach((key, bind) -> {
                    JsonObject saved = new JsonObject();

                    saved.addProperty("type", bind.getType().name());
                    saved.addProperty("code", bind.getCode());
                    saved.addProperty("key", key);

                    object.add(saved);
                });

                FileSystemUtil.write(getFile(), new GsonBuilder().setPrettyPrinting().create().toJson(object));
            }

            @Override
            public void load() {
                String content = FileSystemUtil.read(getFile());
                if (content == null || content.isEmpty()) {
                    return;
                }

                JsonArray array = new JsonParser().parse(content).getAsJsonArray();
                if (array != null) {
                    array.forEach((element) -> {
                        if (element.isJsonObject()) {
                            JsonObject object = element.getAsJsonObject();

                            if (object.has("key")) {
                                int code = object.has("code") ? object.get("code").getAsInt() : Keyboard.KEY_NONE;
                                KeybindType type = object.has("type") ? KeybindType.valueOf(object.get("type").getAsString()) : KeybindType.KEYBOARD;

                                create(object.get("key").getAsString(), new Keybind(type, code));
                            }
                        }
                    });
                }
            }
        };
    }

    @EventListener
    public void onKeyInput(KeyInputEvent event) {
        bindedFeatures.forEach((k, b) -> b.run());
    }

    public void create(String key, Keybind keybind) {
        if (!bindedFeatures.containsKey(key)) {
            bindedFeatures.put(key, keybind);
        } else {
            Keybind k = bindedFeatures.get(key);
            k.setType(keybind.getType());
            k.setCode(keybind.getCode());

            bindedFeatures.put(key, k);
        }
    }

    public void create(String key) {
        create(key, new Keybind());
    }

    public Keybind get(String key) {
        return bindedFeatures.getOrDefault(key, null);
    }

    public Map<String, Keybind> getBindedFeatures() {
        return bindedFeatures;
    }
}
