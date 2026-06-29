package com.staehc_remerpus.emerpus.main.gui.settings.misc;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class VekalDzertSettings extends SettingsPanel {

    public VekalDzertSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int toggleX = panelX + 65;
        int y = currentY;

        // Health slider
        if (isVisible(y)) {
            graphics.drawString(mc.font, "health:", panelX + 5, y, e.textColor, false);
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("health"), 2.0f, 20.0f, panelX + 70, y + 8, "");
        }
        y += 25;

        // Legit toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "legit:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("legit"));
        }
        y += 14;

        // Delay slider (only when legit is on)
        if (eludom.getBoolean("legit")) {
            if (isVisible(y)) {
                graphics.drawString(mc.font, "delay:", panelX + 5, y, e.textColor, false);
                drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                        eludom.getFloat("delay"), 1.0f, 10.0f, panelX + 70, y + 8, "t");
            }
            y += 25;
        }

        // AutoDisable toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "autoDisable:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("autoDisable"));
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int toggleX = panelX + 65;
        int y = currentY;

        // Health slider
        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            int sliderX = panelX + 5;
            if (mouseX >= sliderX - 2 && mouseX <= sliderX + 55 + 2) {
                startSliderDrag("health", 2.0f, 20.0f);
            }
        }
        y += 25;

        // Legit toggle
        checkToggle(mouseX, adjustedMouseY, y, panelX, "legit", button);
        y += 14;

        // Delay slider (only when legit is on)
        if (eludom.getBoolean("legit")) {
            if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
                if (mouseX >= panelX + 3 && mouseX <= panelX + 62)
                    startSliderDrag("delay", 1.0f, 10.0f);
            }
            y += 25;
        }

        // AutoDisable toggle
        checkToggle(mouseX, adjustedMouseY, y, panelX, "autoDisable", button);
    }

    @Override
    public int getHeight() {
        return 25 + (2 * 14) + (eludom.getBoolean("legit") ? 25 : 0);
    }
}