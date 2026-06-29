package com.staehc_remerpus.emerpus.main.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.DeltaTracker;
import net.minecraftforge.common.MinecraftForge;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class Eludom {

    public enum Category {
        COMBAT,
        VISUALS,
        MOVEMENT,
        MISC,
        CONFIGS
    }

    protected static final Minecraft mc = Minecraft.getInstance();

    private final String name;
    private final Category category;
    private boolean toggled;
    private int key;

    private final Map<String, Float> floatSettings = new HashMap<>();
    private final Map<String, Boolean> booleanSettings = new HashMap<>();
    private final Map<String, float[]> floatRanges = new HashMap<>();

    public Eludom(String name, Category category) {
        this.name = name;
        this.category = category;
        this.toggled = false;
        this.key = 0;
    }

    public abstract void onEnable();
    public abstract void onDisable();
    public abstract void onTick();
    public void onRender2D(net.minecraft.client.gui.GuiGraphics graphics, float pt) {}

    public void toggle() {
        toggled = !toggled;
        if (toggled) {
            MinecraftForge.EVENT_BUS.register(this);
            onEnable();
        } else {
            try {
                MinecraftForge.EVENT_BUS.unregister(this);
            } catch (Exception ignored) {}
            onDisable();
        }

        // Notifications
        try {
            NamaknerNotifier.notify(name, toggled);
        } catch (Exception ignored) {}
    }

    public void registerFloat(String name, float value) {
        floatSettings.put(name, value);
    }

    public void registerFloat(String name, float value, float min, float max) {
        floatSettings.put(name, value);
        floatRanges.put(name, new float[]{min, max});
    }

    public void registerBoolean(String name, boolean value) {
        booleanSettings.put(name, value);
    }

    public float getFloat(String name) {
        return floatSettings.getOrDefault(name, 0.0f);
    }

    public Map<String, Float> getFloatSettings() { return floatSettings; }
    public Map<String, Boolean> getBooleanSettings() { return booleanSettings; }

    public float[] getFloatRange(String name) {
        return floatRanges.getOrDefault(name, new float[]{0.0f, 10.0f});
    }

    public boolean getBoolean(String name) {
        return booleanSettings.getOrDefault(name, false);
    }

    public void setFloat(String name, float value) {
        floatSettings.put(name, value);
    }

    public void setBoolean(String name, boolean value) {
        booleanSettings.put(name, value);
    }

    public String getName() { return name; }
    public Category getCategory() { return category; }
    public boolean isToggled() { return toggled; }
    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }

    // Partial tick helper — Forge 1.21.4 has no public getter, so we use reflection once
    private static Field deltaTrackerField;
    private static boolean deltaTrackerResolved = false;

    protected static float getPartialTicks() {
        try {
            if (!deltaTrackerResolved) {
                deltaTrackerResolved = true;
                for (Field f : Minecraft.class.getDeclaredFields()) {
                    if (DeltaTracker.class.isAssignableFrom(f.getType())) {
                        f.setAccessible(true);
                        deltaTrackerField = f;
                        break;
                    }
                }
            }
            if (deltaTrackerField != null) {
                DeltaTracker dt = (DeltaTracker) deltaTrackerField.get(mc);
                return dt.getGameTimeDeltaPartialTick(true);
            }
        } catch (Exception ignored) {}
        return 1.0f;
    }

    // Notification bridge - will be set by MM when module system is ready
    public static class NamaknerNotifier {
        private static NotificationCallback callback;

        public interface NotificationCallback {
            void onToggle(String moduleName, boolean toggledOn);
        }

        public static void setCallback(NotificationCallback cb) {
            callback = cb;
        }

        public static void notify(String name, boolean toggled) {
            if (callback != null) {
                callback.onToggle(name, toggled);
            }
        }
    }
}
