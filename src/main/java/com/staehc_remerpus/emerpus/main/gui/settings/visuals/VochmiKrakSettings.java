package com.staehc_remerpus.emerpus.main.gui.settings.visuals;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class VochmiKrakSettings extends SettingsPanel {

    public VochmiKrakSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int y = currentY;
        graphics.drawString(mc.font, "No settings", panelX + 5, y, e.textColor, false);
        graphics.drawString(mc.font, "Just toggle on/off", panelX + 5, y + 14, e.textColor, false);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        // No interactive elements
    }

    @Override
    public int getHeight() {
        return 28; // Two lines of text
    }
}