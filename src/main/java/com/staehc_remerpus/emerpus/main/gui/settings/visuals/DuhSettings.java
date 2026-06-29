package com.staehc_remerpus.emerpus.main.gui.settings.visuals;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class DuhSettings extends SettingsPanel {

    public DuhSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int toggleX = panelX + 65;
        int lineHeight = 14;
        int y = currentY;

        // Watermark toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "Watermark:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("showWatermark"));
        }
        y += lineHeight;

        // Module List toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "Module List:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("showModuleList"));
        }
        y += lineHeight;

        // Show Username toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "Show User:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("showUsername"));
        }
        y += lineHeight;

        // Show FPS toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "Show FPS:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("showFPS"));
        }
        y += lineHeight;

        // Rainbow toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "Rainbow:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("rainbow"));
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int toggleX = panelX + 65;
        int y = currentY;

        checkToggle(mouseX, adjustedMouseY, y, panelX, "showWatermark", button);
        y += 14;
        checkToggle(mouseX, adjustedMouseY, y, panelX, "showModuleList", button);
        y += 14;
        checkToggle(mouseX, adjustedMouseY, y, panelX, "showUsername", button);
        y += 14;
        checkToggle(mouseX, adjustedMouseY, y, panelX, "showFPS", button);
        y += 14;
        checkToggle(mouseX, adjustedMouseY, y, panelX, "rainbow", button);
    }

    @Override
    public int getHeight() {
        return 5 * 14;
    }
}