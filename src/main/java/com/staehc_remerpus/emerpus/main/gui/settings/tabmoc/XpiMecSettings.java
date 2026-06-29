package com.staehc_remerpus.emerpus.main.gui.settings.tabmoc;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class XpiMecSettings extends SettingsPanel {

    public XpiMecSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int y = currentY;

        if (isVisible(y)) {
            graphics.drawString(mc.font, "Size:", panelX + 5, y, e.textColor, false);
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("size"), 0.1f, 2.0f, panelX + 70, y + 8, "");
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int y = currentY;

        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            int sliderX = panelX + 5;
            if (mouseX >= sliderX - 2 && mouseX <= sliderX + 55 + 2) {
                startSliderDrag("size", 0.1f, 2.0f);
            }
        }
    }

    @Override
    public int getHeight() {
        return 25;
    }
}