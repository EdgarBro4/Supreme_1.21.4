package com.staehc_remerpus.emerpus.main.gui.settings.misc;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import net.minecraft.client.gui.GuiGraphics;

public class YngersAxpersSettings extends SettingsPanel {

    public YngersAxpersSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        if (isVisible(currentY)) {
            graphics.drawString(mc.font, "Middle click players", panelX + 5, currentY, 0x55FF55);
        }
        if (isVisible(currentY + 14)) {
            graphics.drawString(mc.font, "to add/remove", panelX + 5, currentY + 14, 0x55FF55);
        }
        if (isVisible(currentY + 14)) {
            graphics.drawString(mc.font, "friends", panelX + 5, currentY + 24, 0x55FF55);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        // No settings
    }

    @Override
    public int getHeight() {
        return 28; // Two lines of text
    }
}