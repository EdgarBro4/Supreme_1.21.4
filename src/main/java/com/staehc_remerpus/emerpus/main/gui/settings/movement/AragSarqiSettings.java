package com.staehc_remerpus.emerpus.main.gui.settings.movement;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class AragSarqiSettings extends SettingsPanel {

    public AragSarqiSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int y = currentY;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "edgeOnly:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, panelX + 65, y - 4, eludom.getBoolean("edgeOnly"));
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        checkToggle(mouseX, adjustedMouseY, currentY, panelX, "edgeOnly", button);
    }

    @Override
    public int getHeight() {
        return 14;
    }
}