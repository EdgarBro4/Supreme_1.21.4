package com.staehc_remerpus.emerpus.main.gui.settings.misc;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;

public class TeleportationSettings extends SettingsPanel {

    public TeleportationSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int toggleX = panelX + 65;
        int y = currentY;

        // Delay slider
        graphics.drawString(mc.font, "Delay:", panelX + 5, y, e.textColor, false);
        drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                eludom.getFloat("delay"), 30.0f, 500.0f, panelX + 70, y + 8, "ms");
        y += 25;

        // Randomize toggle
        graphics.drawString(mc.font, "Randomize:", panelX + 5, y, e.textColor, false);
        drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("randomize"));
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int toggleX = panelX + 65;
        int y = currentY;

        // Delay slider
        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            int sliderX = panelX + 5;
            if (mouseX >= sliderX - 2 && mouseX <= sliderX + 55 + 2) {
                startSliderDrag("delay", 30.0f, 500.0f);
            }
        }
        y += 25;

        // Randomize toggle
        if (adjustedMouseY >= y - 4 && adjustedMouseY <= y + 6) {
            if (mouseX >= toggleX && mouseX <= toggleX + 25) {
                boolean current = eludom.getBoolean("randomize");
                eludom.registerBoolean("randomize", !current);
                if (mc.player != null) mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
    }

    @Override
    public int getHeight() {
        return 25 + 14; // Slider + toggle
    }
}