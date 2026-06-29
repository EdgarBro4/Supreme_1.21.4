package com.staehc_remerpus.emerpus.main.gui.settings.visuals;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class VesheriPSESettings extends SettingsPanel {

    public VesheriPSESettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int toggleX = panelX + 65;
        int y = currentY;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "glow:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("glow"));
        }
        y += 14;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "text:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("text"));
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int y = currentY;

        checkToggle(mouseX, adjustedMouseY, y, panelX, "glow", button);
        y += 14;

        checkToggle(mouseX, adjustedMouseY, y, panelX, "text", button);
    }

    @Override
    public int getHeight() {
        return 2 * 14;
    }
}