package com.staehc_remerpus.emerpus.main.gui.settings.tabmoc;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class ACrySettings extends SettingsPanel {

    public ACrySettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int y = currentY;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "explode:", panelX + 5, y, e.textColor, false);
            float[] range = eludom.getFloatRange("explode");
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("explode"), range[0], range[1], panelX + 70, y + 8, "b");
        }
        y += 25;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "delay:", panelX + 5, y, e.textColor, false);
            float[] range = eludom.getFloatRange("delay");
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("delay"), range[0], range[1], panelX + 70, y + 8, "t");
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int y = currentY;

        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            if (mouseX >= panelX + 3 && mouseX <= panelX + 62) {
                float[] range = eludom.getFloatRange("explode");
                startSliderDrag("explode", range[0], range[1]);
            }
        }
        y += 25;

        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            if (mouseX >= panelX + 3 && mouseX <= panelX + 62) {
                float[] range = eludom.getFloatRange("delay");
                startSliderDrag("delay", range[0], range[1]);
            }
        }
    }

    @Override
    public int getHeight() {
        return 25 + 25;
    }
}