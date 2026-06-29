package com.staehc_remerpus.emerpus.main.gui.settings;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import com.staehc_remerpus.emerpus.main.utils.j;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;

import java.awt.Color;

public abstract class SettingsPanel {
    protected Minecraft mc = Minecraft.getInstance();
    protected Eludom eludom;
    protected ModuleButton button;

    protected int panelX, panelY, panelWidth, panelHeight;
    protected int contentStartY, contentEndY;
    protected int currentY;

    public SettingsPanel(Eludom eludom, ModuleButton button) {
        this.eludom = eludom;
        this.button = button;
    }

    public void updatePosition(int panelX, int panelY, int panelWidth, int panelHeight,
                               int contentStartY, int contentEndY, int currentY) {
        this.panelX = panelX;
        this.panelY = panelY;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.contentStartY = contentStartY;
        this.contentEndY = contentEndY;
        this.currentY = currentY;
    }

    public abstract void render(GuiGraphics graphics);
    public abstract void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY);
    public abstract int getHeight();

    protected boolean isVisible(int y) {
        return y + 20 > contentStartY && y < contentEndY;
    }

    protected void drawSlider(GuiGraphics graphics, int x, int y, int width, int height,
                              float value, float min, float max, int textX, int textY, String suffix) {
        float percent = (value - min) / (max - min);

        j.drawSmoothRect(graphics, x, y, x + width, y + height, new Color(60, 60, 60).getRGB());

        int fillWidth = (int) (width * percent);
        j.drawSmoothRect(graphics, x, y, x + fillWidth, y + height, e.accentColor.getRGB());

        int handleX = x + fillWidth - 2;
        j.drawSmoothRect(graphics, handleX, y - 2, handleX + 4, y + height + 2, Color.WHITE.getRGB());

        String text = String.format("%.1f%s", value, suffix);
        graphics.drawString(mc.font, text, textX, textY, e.accentColor.getRGB(), false);
    }

    protected void drawToggle(GuiGraphics graphics, int x, int y, boolean state) {
        int width = 25;
        int height = 10;

        j.drawSmoothRect(graphics, x, y, x + width, y + height,
                state ? e.accentColor.getRGB() : new Color(60, 60, 60).getRGB());

        int handleOffset = state ? width - 10 : 0;
        j.drawSmoothRect(graphics, x + handleOffset, y - 1,
                x + handleOffset + 10, y + height + 1, Color.WHITE.getRGB());
    }

    protected void checkToggle(double mouseX, double mouseY, int yPos, int panelX, String setting, int button) {
        int toggleX = panelX + 65;
        if (mouseX >= toggleX && mouseX <= toggleX + 25 &&
                mouseY >= yPos - 4 && mouseY <= yPos + 6) {
            boolean current = eludom.getBoolean(setting);
            eludom.registerBoolean(setting, !current);
            if (mc.player != null) {
                mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
    }

    protected void startSliderDrag(String key, float min, float max) {
        button.startDragging(eludom, key, min, max);
    }
}