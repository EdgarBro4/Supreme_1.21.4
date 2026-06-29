package com.staehc_remerpus.emerpus.main.gui.settings.misc;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class TriVriSettings extends SettingsPanel {

    public TriVriSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int y = currentY;

        // Speed slider
        graphics.drawString(mc.font, "Speed:", panelX + 5, y, e.textColor, false);
        drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                eludom.getFloat("speed"), 1.0f, 5.0f, panelX + 70, y + 8, "");
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int y = currentY;

        // Speed slider
        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            int sliderX = panelX + 5;
            if (mouseX >= sliderX - 2 && mouseX <= sliderX + 55 + 2) {
                startSliderDrag("speed", 1.0f, 5.0f);
            }
        }
    }

    @Override
    public int getHeight() {
        return 25;
    }
}