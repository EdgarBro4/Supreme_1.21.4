package com.staehc_remerpus.emerpus.main.gui.settings.visuals;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class ErkarDzernerSettings extends SettingsPanel {

    public ErkarDzernerSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int y = currentY;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "right x:", panelX + 5, y, e.textColor, false);
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("rx"), -1.0f, 1.0f, panelX + 70, y + 8, "");
        }
        y += 25;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "right y:", panelX + 5, y, e.textColor, false);
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("ry"), -1.0f, 1.0f, panelX + 70, y + 8, "");
        }
        y += 25;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "right z:", panelX + 5, y, e.textColor, false);
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("rz"), -1.0f, 1.0f, panelX + 70, y + 8, "");
        }
        y += 25;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "left x:", panelX + 5, y, e.textColor, false);
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("lx"), -1.0f, 1.0f, panelX + 70, y + 8, "");
        }
        y += 25;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "left y:", panelX + 5, y, e.textColor, false);
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("ly"), -1.0f, 1.0f, panelX + 70, y + 8, "");
        }
        y += 25;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "left z:", panelX + 5, y, e.textColor, false);
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("lz"), -1.0f, 1.0f, panelX + 70, y + 8, "");
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int y = currentY;

        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20)
            if (mouseX >= panelX + 3 && mouseX <= panelX + 62)
                startSliderDrag("rx", -1.0f, 1.0f);
        y += 25;

        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20)
            if (mouseX >= panelX + 3 && mouseX <= panelX + 62)
                startSliderDrag("ry", -1.0f, 1.0f);
        y += 25;

        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20)
            if (mouseX >= panelX + 3 && mouseX <= panelX + 62)
                startSliderDrag("rz", -1.0f, 1.0f);
        y += 25;

        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20)
            if (mouseX >= panelX + 3 && mouseX <= panelX + 62)
                startSliderDrag("lx", -1.0f, 1.0f);
        y += 25;

        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20)
            if (mouseX >= panelX + 3 && mouseX <= panelX + 62)
                startSliderDrag("ly", -1.0f, 1.0f);
        y += 25;

        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20)
            if (mouseX >= panelX + 3 && mouseX <= panelX + 62)
                startSliderDrag("lz", -1.0f, 1.0f);
    }

    @Override
    public int getHeight() {
        return 6 * 25;
    }
}