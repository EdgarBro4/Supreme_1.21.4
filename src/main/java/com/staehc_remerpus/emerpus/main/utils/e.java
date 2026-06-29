package com.staehc_remerpus.emerpus.main.utils;

import java.awt.Color;

public class e {
    public static final Color MAIN_RED = new Color(195, 31, 38);
    public static final Color MAIN_RED_DARKER = new Color(150, 20, 25);
    public static final Color MAIN_RED_LIGHTER = new Color(220, 40, 48);

    public static Color bgColor = new Color(25, 25, 25);
    public static Color accentColor = MAIN_RED;
    public static Color buttonBgColor = new Color(35, 35, 35);
    public static Color toggleColor = MAIN_RED;
    public static Color categoryHighlightColor = MAIN_RED;

    public static int textColor = Color.WHITE.getRGB();

    public static int getRedRGB() { return MAIN_RED.getRGB(); }
    public static int getRedDarkerRGB() { return MAIN_RED_DARKER.getRGB(); }
    public static int getRedLighterRGB() { return MAIN_RED_LIGHTER.getRGB(); }
    public static int getBgRGB() { return bgColor.getRGB(); }
    public static int getAccentRGB() { return accentColor.getRGB(); }
    public static int getButtonBgRGB() { return buttonBgColor.getRGB(); }
    public static int getToggleRGB() { return toggleColor.getRGB(); }

    private static int rainbowOffset = 0;

    public static Color rainbow(int delay) {
        rainbowOffset += delay;
        if (rainbowOffset > 360) rainbowOffset = 0;

        float hue = (System.currentTimeMillis() % 3600) / 3600f;
        return Color.getHSBColor(hue, 0.8f, 1.0f);
    }

    public static Color rainbow(float speed) {
        float hue = (System.currentTimeMillis() % (int)(3600 / speed)) / (3600f / speed);
        return Color.getHSBColor(hue, 0.8f, 1.0f);
    }

    public static Color getRainbow() {
        return rainbow(1);
    }

    public static int getRainbowRGB() {
        return rainbow(1).getRGB();
    }
}