package com.staehc_remerpus.emerpus.main.gui.settings.visuals;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import net.minecraft.client.gui.GuiGraphics;

public class CHSettings extends SettingsPanel {

    public CHSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        if (isVisible(currentY)) {
            graphics.drawString(mc.font, "No settings", panelX + 5, currentY, 0x808080);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        // No settings
    }

    @Override
    public int getHeight() {
        return 14;
    }
}