package com.staehc_remerpus.emerpus.main.gui.settings.visuals;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class LuysovPSESettings extends SettingsPanel {

    public LuysovPSESettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int toggleX = panelX + 65;
        int lineHeight = 14;
        int y = currentY;

        // Players toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "players:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("players"));
        }
        y += lineHeight;

        // Mobs toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "mobs:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("mobs"));
        }
        y += lineHeight;

        // Invis toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "invis:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("invis"));
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int toggleX = panelX + 65;
        int y = currentY;

        checkToggle(mouseX, adjustedMouseY, y, panelX, "players", button);
        y += 14;
        checkToggle(mouseX, adjustedMouseY, y, panelX, "mobs", button);
        y += 14;
        checkToggle(mouseX, adjustedMouseY, y, panelX, "invis", button);
    }

    @Override
    public int getHeight() {
        return 3 * 14;
    }
}