package com.staehc_remerpus.emerpus.main.gui.settings.tabmoc;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class ACSettings extends SettingsPanel {

    public ACSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int toggleX = panelX + 65;
        int lineHeight = 14;
        int y = currentY;

        // Left click toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "left click:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("leftClick"));
        }
        y += lineHeight;

        // Left CPS slider
        if (isVisible(y)) {
            graphics.drawString(mc.font, "left CPS:", panelX + 5, y, e.textColor, false);
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("leftCPS"), 1.0f, 20.0f, panelX + 70, y + 8, "");
        }
        y += 25;

        // Right click toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "right click:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("rightClick"));
        }
        y += lineHeight;

        // Right CPS slider
        if (isVisible(y)) {
            graphics.drawString(mc.font, "right CPS:", panelX + 5, y, e.textColor, false);
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("rightCPS"), 1.0f, 20.0f, panelX + 70, y + 8, "");
        }
        y += 25;

        // Only sword toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "only sword:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("onlySword"));
        }
        y += lineHeight;

        // Block-hit toggle
        if (isVisible(y)) {
            graphics.drawString(mc.font, "block hit:", panelX + 5, y, e.textColor, false);
            drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("blockHit"));
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int toggleX = panelX + 65;
        int y = currentY;

        // Left click toggle
        checkToggle(mouseX, adjustedMouseY, y, panelX, "leftClick", button);
        y += 14;

        // Left CPS slider
        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            int sliderX = panelX + 5;
            if (mouseX >= sliderX - 2 && mouseX <= sliderX + 55 + 2) {
                startSliderDrag("leftCPS", 1.0f, 20.0f);
            }
        }
        y += 25;

        // Right click toggle
        checkToggle(mouseX, adjustedMouseY, y, panelX, "rightClick", button);
        y += 14;

        // Right CPS slider
        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            int sliderX = panelX + 5;
            if (mouseX >= sliderX - 2 && mouseX <= sliderX + 55 + 2) {
                startSliderDrag("rightCPS", 1.0f, 20.0f);
            }
        }
        y += 25;

        // Only sword toggle
        checkToggle(mouseX, adjustedMouseY, y, panelX, "onlySword", button);
        y += 14;

        // Block-hit toggle
        checkToggle(mouseX, adjustedMouseY, y, panelX, "blockHit", button);
    }

    @Override
    public int getHeight() {
        return (4 * 14) + (2 * 25);
    }
}