package com.staehc_remerpus.emerpus.main.gui.settings.misc;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class HaqiTevertSettings extends SettingsPanel {

    public HaqiTevertSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int toggleX = panelX + 65;
        int y = currentY;

        // ── Delay slider ──────────────────────────────────────────────────
        if (isVisible(y)) {
            graphics.drawString(mc.font, "delay:", panelX + 5, y, e.textColor, false);

            float[] range = eludom.getFloatRange("delay");
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("delay"), range[0], range[1], panelX + 70, y + 8, "ms");
        }
        y += 25;

        // ── Legit toggle ──────────────────────────────────────────────────
        if (isVisible(y)) {
            graphics.drawString(mc.font, "legit:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("legit"));
        }
        y += 14;

        // ── AutoFirework toggle ───────────────────────────────────────────
        if (isVisible(y)) {
            graphics.drawString(mc.font, "autoFW:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("autoFW"));
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int toggleX = panelX + 65;
        int y = currentY;

        // ── Delay slider ──────────────────────────────────────────────────
        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            int sliderX = panelX + 5;
            if (mouseX >= sliderX - 2 && mouseX <= sliderX + 55 + 2) {
                float[] range = eludom.getFloatRange("delay");
                startSliderDrag("delay", range[0], range[1]);
            }
        }
        y += 25;

        // ── Legit toggle ──────────────────────────────────────────────────
        checkToggle(mouseX, adjustedMouseY, y, panelX, "legit", button);
        y += 14;

        // ── AutoFirework toggle ───────────────────────────────────────────
        checkToggle(mouseX, adjustedMouseY, y, panelX, "autoFW", button);
    }

    @Override
    public int getHeight() {
        // Delay slider (25) + legit toggle (14) + autoFW toggle (14)
        return 25 + 14 + 14;
    }
}