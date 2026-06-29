package com.staehc_remerpus.emerpus.main.utils;

import com.staehc_remerpus.emerpus.main.modules.MM;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

public class ConfigManager {
    public static final File CONFIG_DIR = new File("C:\\WinSysData\\cfg");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void init() {
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        }
    }

    public static void saveConfig(String name) {
        init();
        File file = new File(CONFIG_DIR, name + ".json");
        try (FileWriter writer = new FileWriter(file)) {
            JsonObject root = new JsonObject();
            for (Eludom module : MM.getModules()) {
                if (module.getCategory() == Eludom.Category.CONFIGS) continue;

                JsonObject modJson = new JsonObject();
                modJson.addProperty("toggled", module.isToggled());
                modJson.addProperty("key", module.getKey());

                JsonObject floats = new JsonObject();
                for (Map.Entry<String, Float> entry : module.getFloatSettings().entrySet()) {
                    floats.addProperty(entry.getKey(), entry.getValue());
                }
                modJson.add("floats", floats);

                JsonObject bools = new JsonObject();
                for (Map.Entry<String, Boolean> entry : module.getBooleanSettings().entrySet()) {
                    bools.addProperty(entry.getKey(), entry.getValue());
                }
                modJson.add("bools", bools);

                root.add(module.getName(), modJson);
            }
            GSON.toJson(root, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig(String name) {
        File file = new File(CONFIG_DIR, name + ".json");
        if (!file.exists()) return;
        try (FileReader reader = new FileReader(file)) {
            JsonObject root = GSON.fromJson(reader, JsonObject.class);
            for (Eludom module : MM.getModules()) {
                if (root.has(module.getName())) {
                    JsonObject modJson = root.getAsJsonObject(module.getName());
                    
                    boolean shouldToggle = modJson.has("toggled") && modJson.get("toggled").getAsBoolean();
                    if (module.isToggled() != shouldToggle) {
                        module.toggle();
                    }
                    if (modJson.has("key")) {
                        module.setKey(modJson.get("key").getAsInt());
                    }
                    
                    if (modJson.has("floats")) {
                        JsonObject floats = modJson.getAsJsonObject("floats");
                        for (Map.Entry<String, com.google.gson.JsonElement> entry : floats.entrySet()) {
                            module.getFloatSettings().put(entry.getKey(), entry.getValue().getAsFloat());
                        }
                    }
                    
                    if (modJson.has("bools")) {
                        JsonObject bools = modJson.getAsJsonObject("bools");
                        for (Map.Entry<String, com.google.gson.JsonElement> entry : bools.entrySet()) {
                            module.getBooleanSettings().put(entry.getKey(), entry.getValue().getAsBoolean());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteConfig(String name) {
        File file = new File(CONFIG_DIR, name + ".json");
        if (file.exists()) {
            file.delete();
        }
    }

    public static void openFolder() {
        init();
        try {
            net.minecraft.Util.getPlatform().openFile(CONFIG_DIR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
