package com.staehc_remerpus.emerpus.main.gui.settings.tabmoc;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class AASettings extends SettingsPanel {

    public AASettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int toggleX = panelX + 65;
        int y = currentY;

        // Distance slider
        if (isVisible(y)) {
            graphics.drawString(mc.font, "Distance:", panelX + 5, y, e.textColor, false);

            float[] range = eludom.getFloatRange("distance");
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("distance"), range[0], range[1], panelX + 70, y + 8, "");
        }
        y += 25;

        // Yaw speed slider
        if (isVisible(y)) {
            graphics.drawString(mc.font, "yaw speed:", panelX + 5, y, e.textColor, false);

            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("yawSpeed"), 1.0f, 200.0f, panelX + 70, y + 8, "");
        }
        y += 25;

        // Pitch speed slider
        if (isVisible(y)) {
            graphics.drawString(mc.font, "pitch speed:", panelX + 5, y, e.textColor, false);

            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("pitchSpeed"), 1.0f, 100.0f, panelX + 70, y + 8, "");
        }
        y += 25;

        // Invisibles toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "Invisibles:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("invisible"));
        }
        y += 25;

        // Only Sword toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "Only Sword:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("onlySword"));
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int toggleX = panelX + 65;
        int y = currentY;

        // Distance slider
        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            int sliderX = panelX + 5;
            if (mouseX >= sliderX - 2 && mouseX <= sliderX + 55 + 2) {
                float[] range = eludom.getFloatRange("distance");
                startSliderDrag("distance", range[0], range[1]);
            }
        }
        y += 25;

        // Yaw speed slider
        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            int sliderX = panelX + 5;
            if (mouseX >= sliderX - 2 && mouseX <= sliderX + 55 + 2) {
                startSliderDrag("yawSpeed", 1.0f, 200.0f);
            }
        }
        y += 25;

        // Pitch speed slider
        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            int sliderX = panelX + 5;
            if (mouseX >= sliderX - 2 && mouseX <= sliderX + 55 + 2) {
                startSliderDrag("pitchSpeed", 1.0f, 100.0f);
            }
        }
        y += 25;

        // Invisibles toggle
        checkToggle(mouseX, adjustedMouseY, y, panelX, "invisible", button);
        y += 25;

        // Only Sword toggle
        checkToggle(mouseX, adjustedMouseY, y, panelX, "onlySword", button);
    }

    @Override
    public int getHeight() {
        return 25 + 25 + 25 + 14 + 14 + 14; // 3 sliders + 3 toggles
    }
}