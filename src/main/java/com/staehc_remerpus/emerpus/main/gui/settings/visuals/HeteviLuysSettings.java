package com.staehc_remerpus.emerpus.main.gui.settings.visuals;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class HeteviLuysSettings extends SettingsPanel {

    public HeteviLuysSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int toggleX = panelX + 65;
        int y = currentY;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "players:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("players"));
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int y = currentY;
        checkToggle(mouseX, adjustedMouseY, y, panelX, "players", button);
    }

    @Override
    public int getHeight() {
        return 14;
    }
}