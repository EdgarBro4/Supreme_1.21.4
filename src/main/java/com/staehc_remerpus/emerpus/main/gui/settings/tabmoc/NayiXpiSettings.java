package com.staehc_remerpus.emerpus.main.gui.settings.tabmoc;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;

public class NayiXpiSettings extends SettingsPanel {

    public NayiXpiSettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    @Override
    public void render(GuiGraphics graphics) {
        int toggleX = panelX + 65;
        int lineHeight = 14;
        int y = currentY;

        // Range slider
        graphics.drawString(mc.font, "Range:", panelX + 5, y, e.textColor, false);
        drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                eludom.getFloat("range"), 3.0f, 6.0f, panelX + 70, y + 8, "");
        y += 25;

        // CPS slider (right under Range)
        if (!eludom.getBoolean("pvp19")) {
            graphics.drawString(mc.font, "cps:", panelX + 5, y, e.textColor, false);
            drawSlider(graphics, panelX + 5, y + 12, 55, 4,
                    eludom.getFloat("cps"), 1.0f, 20.0f, panelX + 70, y + 8, "");
            y += 25;
        }

        // Players toggle
        graphics.drawString(mc.font, "players:", panelX + 5, y, e.textColor, false);
        drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("players"));
        y += lineHeight;

        // Mobs toggle
        graphics.drawString(mc.font, "mobs:", panelX + 5, y, e.textColor, false);
        drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("mobs"));
        y += lineHeight;

        // OnlyCrits toggle
        graphics.drawString(mc.font, "onlyCrits:", panelX + 5, y, e.textColor, false);
        drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("onlyCrits"));
        y += lineHeight;

        // Only Sword toggle
        graphics.drawString(mc.font, "only sword:", panelX + 5, y, e.textColor, false);
        drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("onlySword"));
        y += lineHeight;

        // 1.9 PvP toggle
        graphics.drawString(mc.font, "pvp19:", panelX + 5, y, e.textColor, false);
        drawToggle(graphics, toggleX, y - 4, eludom.getBoolean("pvp19"));
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int toggleX = panelX + 65;
        int y = currentY;

        // Range slider
        if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
            int sliderX = panelX + 5;
            if (mouseX >= sliderX - 2 && mouseX <= sliderX + 55 + 2) {
                startSliderDrag("range", 3.0f, 6.0f);
            }
        }
        y += 25;

        // CPS slider
        if (!eludom.getBoolean("pvp19")) {
            if (adjustedMouseY >= y + 8 && adjustedMouseY <= y + 20) {
                int sliderX = panelX + 5;
                if (mouseX >= sliderX - 2 && mouseX <= sliderX + 55 + 2) {
                    startSliderDrag("cps", 1.0f, 20.0f);
                }
            }
            y += 25;
        }

        // Players toggle
        if (adjustedMouseY >= y - 4 && adjustedMouseY <= y + 6) {
            if (mouseX >= toggleX && mouseX <= toggleX + 25) {
                boolean current = eludom.getBoolean("players");
                eludom.registerBoolean("players", !current);
                if (mc.player != null) mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
        y += 14;

        // Mobs toggle
        if (adjustedMouseY >= y - 4 && adjustedMouseY <= y + 6) {
            if (mouseX >= toggleX && mouseX <= toggleX + 25) {
                boolean current = eludom.getBoolean("mobs");
                eludom.registerBoolean("mobs", !current);
                if (mc.player != null) mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
        y += 14;

        // OnlyCrits toggle
        if (adjustedMouseY >= y - 4 && adjustedMouseY <= y + 6) {
            if (mouseX >= toggleX && mouseX <= toggleX + 25) {
                boolean current = eludom.getBoolean("onlyCrits");
                eludom.registerBoolean("onlyCrits", !current);
                if (mc.player != null) mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
        y += 14;

        // Only Sword toggle
        if (adjustedMouseY >= y - 4 && adjustedMouseY <= y + 6) {
            if (mouseX >= toggleX && mouseX <= toggleX + 25) {
                boolean current = eludom.getBoolean("onlySword");
                eludom.registerBoolean("onlySword", !current);
                if (mc.player != null) mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
        y += 14;

        // 1.9 PvP toggle
        if (adjustedMouseY >= y - 4 && adjustedMouseY <= y + 6) {
            if (mouseX >= toggleX && mouseX <= toggleX + 25) {
                boolean current = eludom.getBoolean("pvp19");
                eludom.registerBoolean("pvp19", !current);
                if (mc.player != null) mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
    }

    @Override
    public int getHeight() {
        int height = 25; // Range
        if (!eludom.getBoolean("pvp19")) {
            height += 25; // CPS
        }
        height += (5 * 14); // 5 toggles (players, mobs, onlyCrits, onlySword, pvp19)
        return height;
    }
}